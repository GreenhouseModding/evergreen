package house.greenhouse.evergreen.util;

import org.apache.commons.lang3.StringUtils;
import org.gradle.api.Project;
import org.gradle.api.artifacts.*;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.util.internal.GUtil;
import org.jetbrains.annotations.NotNull;

public class ConfigurationsUtil {
    public static String getModImplementationConfigurationName(SourceSet sourceSet) {
        return configurationNameOf(sourceSet, "modImplementation");
    }

    private static String configurationNameOf(SourceSet sourceSet, String baseName) {
        if (sourceSet.getName().equals(SourceSet.MAIN_SOURCE_SET_NAME))
            return StringUtils.uncapitalize(baseName);

        return StringUtils.uncapitalize(GUtil.toCamelCase(sourceSet.getName()) + StringUtils.capitalize(baseName));
    }

    public static void createModRemapConfigurations(String sourceSetName, @NotNull Project project) {
        ConfigurationsUtil.createModRemapConfig(sourceSetName, JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, project);
        ConfigurationsUtil.createModRemapConfig(sourceSetName, JavaPlugin.API_CONFIGURATION_NAME, project);
        ConfigurationsUtil.createModRemapConfig(sourceSetName, JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME, project);
        ConfigurationsUtil.createModRemapConfig(sourceSetName, JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME, project);
        ConfigurationsUtil.createModRemapConfig(sourceSetName, JavaPlugin.COMPILE_ONLY_API_CONFIGURATION_NAME, project);
    }

    private static Configuration createModRemapConfig(String sourceSetName, String parentName, Project project) {
        var configName = "mod" + StringUtils.capitalize(parentName);
        if (!sourceSetName.equals(SourceSet.MAIN_SOURCE_SET_NAME)) {
            configName = sourceSetName + StringUtils.capitalize(configName);
        }

        var parent = project.getConfigurations().getByName(parentName);
        var remapConfig = project.getConfigurations().create(configName, configuration -> {
            configuration.setCanBeConsumed(true);
            configuration.setCanBeResolved(false);
            configuration.setTransitive(false);
        });
        remapConfig.extendsFrom(parent);
        return remapConfig;
    }
}
