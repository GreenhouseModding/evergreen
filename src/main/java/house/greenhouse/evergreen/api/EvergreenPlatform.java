package house.greenhouse.evergreen.api;

import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;
import org.jetbrains.annotations.Nullable;

// TODO: Add mapping export related fields.
public abstract class EvergreenPlatform {

    public EvergreenPlatform() {

    }

    /**
     * A method that runs before evaluation, before Minecraft is added to the environment.
     *
     * @param project   The Gradle project.
     * @param sourceSet The source-set of this platform.
     */
    public void beforeEvaluate(Project project, SourceSet sourceSet) {

    }

    /**
     * A method that runs right before Minecraft is remapped.
     *
     * @param project   The Gradle project.
     * @param sourceSet The source-set of this platform.
     */
    public void beforeRemap(Project project, SourceSet sourceSet) {

    }

    /**
     * A method that runs after evaluation.
     *
     * @param project   The Gradle project.
     * @param sourceSet The source-set of this platform.
     */
    public void afterEvaluate(Project project, SourceSet sourceSet) {

    }

    /**
     * The name used for the source set of this project.
     */
    public abstract String sourceSetName();

    @Nullable
    public Object runtimeMappingOverride() {
        return null;
    }
}
