package house.greenhouse.evergreen.dsl;

import house.greenhouse.evergreen.api.EvergreenPlatform;
import house.greenhouse.evergreen.util.ConfigurationsUtil;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;

public class EvergreenXplat extends EvergreenPlatform {

    public EvergreenXplat() {

    }

    @Override
    public void afterEvaluate(Project project, SourceSet sourceSet) {
        // Remaps are here for Archloom projects.
        ConfigurationsUtil.createModRemapConfigurations(sourceSet.getName(), project);
    }

    @Override
    public String sourceSetName() {
        return SourceSet.MAIN_SOURCE_SET_NAME;
    }
}
