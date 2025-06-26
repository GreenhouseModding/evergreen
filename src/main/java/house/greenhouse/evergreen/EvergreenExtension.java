package house.greenhouse.evergreen;

import house.greenhouse.evergreen.api.EvergreenPlatform;
import house.greenhouse.evergreen.dsl.EvergreenFabric;
import house.greenhouse.evergreen.dsl.EvergreenNeoForge;
import house.greenhouse.evergreen.dsl.EvergreenXplat;
import house.greenhouse.evergreen.util.ExtensionsUtil;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class EvergreenExtension {
    private final Project project;

    public EvergreenExtension(Project project) {
        this.project = project;
    }

    public String minecraftVersion = null;
    private final Map<String, EvergreenPlatform> platforms = new HashMap<>();

    /**
     * Creates a source set for a cross-platform environment, only depending on Minecraft, being brought into any other specified platform.
     * <br>
     * If other platforms are also specified, this will be considered the <mark>main</mark> source-set.
     * <br>
     * <i>This is effectively a shortcut to the {@link EvergreenExtension#xplat(Action)} method.</i>
     *
     * @param customizer An action to allow for customization to the configuration of the platform.
     */
    public EvergreenExtension common(Action<EvergreenXplat> customizer) {
        return xplat(customizer);
    }

    /**
     * Creates a source set for a cross-platform environment, only depending on Minecraft, being brought into any other specified platform.
     * <br>
     * If other platforms are also specified, this will be considered the <mark>main</mark> source-set.
     *
     * @param customizer An action to allow for customization to the configuration of the platform.
     */
    public EvergreenExtension xplat(Action<EvergreenXplat> customizer) {
        return platform(EvergreenXplat.class, customizer);
    }

    /**
     * Creates a source set for a Fabric environment, depending on Minecraft, Fabric Loader and if specified, Fabric API.
     * <br>
     * If a cross-platform module is specified, this will be considered the <mark>fabric</mark> source-set.
     *
     * @param customizer An action to allow for customization to the configuration of the platform.
     */
    public EvergreenExtension fabric(Action<EvergreenFabric> customizer) {
        return platform(EvergreenFabric.class, customizer);
    }

    /**
     * Creates a source set for a NeoForge environment, depending on Minecraft and NeoForge.
     * <br>
     * If a cross-platform module is specified, this will be considered the <mark>neoforge</mark> source-set.
     *
     * @param customizer An action to allow for customization to the configuration of the platform.
     */
    public EvergreenExtension neoForge(Action<EvergreenNeoForge> customizer) {
        return platform(EvergreenNeoForge.class, customizer);
    }

    /**
     * Creates a source set for the specified environment.
     *
     * @param clazz
     * @param customizer
     * @param args
     * @return
     * @param <T>
     */
    public <T extends EvergreenPlatform> EvergreenExtension platform(Class<T> clazz, Action<T> customizer, Object... args) {
        try {
            T obj = clazz.getConstructor(Arrays.stream(args)
                    .map(Object::getClass)
                    .toArray(Class[]::new)
            ).newInstance(args);
            customizer.execute(obj);
            platforms.put(obj.sourceSetName(), obj);
            return this;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
            throw new IllegalArgumentException("Evergreen failed to create platform object.", ex);
        }
    }

    protected void afterEvaluate() {
        project.afterEvaluate(project -> {
            if (minecraftVersion == null) {
                throw new NullPointerException("Evergreen does not have a specified Minecraft version.");
            }

            if (platforms.isEmpty()) {
                throw new NullPointerException("Evergreen does not have any platforms specified.");
            }

            SourceSet mainSource = ExtensionsUtil.getMainSourceSet(project);

            if (platforms.size() == 1) {
                EvergreenPlatform platform = platforms.values().toArray(EvergreenPlatform[]::new)[0];
                platform.afterEvaluate(project, mainSource);
            } else {
                if (!platforms.containsKey(SourceSet.MAIN_SOURCE_SET_NAME)) {
                    throw new UnsupportedOperationException("Evergreen does not have a platform defined under the 'main' source set. This would usually be the xplat module.");
                }
                for (EvergreenPlatform platform : platforms.values()) {
                    SourceSet sourceSet = mainSource;
                    if (!platform.sourceSetName().equals(SourceSet.MAIN_SOURCE_SET_NAME)) {
                        sourceSet = ExtensionsUtil.getSourceSets(project).create(platform.sourceSetName());
                        project.getDependencies().add(sourceSet.getCompileOnlyConfigurationName(), mainSource.getOutput());
                    }
                    platform.afterEvaluate(project, sourceSet);
                }
            }
        });
    }
}
