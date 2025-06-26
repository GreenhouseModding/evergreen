package house.greenhouse.evergreen.util;

import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

public class ExtensionsUtil {
    public static SourceSetContainer getSourceSets(Project project) {
        JavaPluginExtension javaPluginExtension = project.getExtensions().getByType(JavaPluginExtension.class);
        return javaPluginExtension.getSourceSets();
    }

    public static SourceSet getMainSourceSet(Project project) {
        return getSourceSet(project, SourceSet.MAIN_SOURCE_SET_NAME);
    }

    public static SourceSet getSourceSet(Project project, String sourceSet) {
        return getSourceSets(project).getByName(sourceSet);
    }
}
