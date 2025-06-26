package house.greenhouse.evergreen.dsl;

import house.greenhouse.evergreen.api.EvergreenPlatform;
import house.greenhouse.evergreen.util.ConfigurationsUtil;
import house.greenhouse.evergreen.util.EvergreenConstants;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;
import org.jetbrains.annotations.Nullable;

public class EvergreenFabric extends EvergreenPlatform {
    private static final String FABRIC_LOADER_DEPENDENCY = "net.fabricmc:fabric-loader:%s";
    private static final String FABRIC_API_DEPENDENCY = "net.fabricmc.fabric-api:fabric-api:%s";

    @Nullable
    public String loaderVersion = null;
    @Nullable
    public String apiVersion = null;

    public EvergreenFabric() {

    }

    @Override
    public void afterEvaluate(Project project, SourceSet sourceSet) {
        if (loaderVersion == null) {
            throw new NullPointerException("Evergreen does not have a specified Fabric Loader version.");
        }
        ConfigurationsUtil.createModRemapConfigurations(sourceSet.getName(), project);
        project.getDependencies().add(sourceSet.getImplementationConfigurationName(), FABRIC_LOADER_DEPENDENCY.formatted(loaderVersion));
        if (apiVersion != null) {
            project.getDependencies().add(ConfigurationsUtil.getModImplementationConfigurationName(sourceSet), FABRIC_API_DEPENDENCY.formatted(apiVersion));
        }
    }

    @Override
    public String sourceSetName() {
        return EvergreenConstants.SourceSets.FABRIC;
    }
}
