package house.greenhouse.evergreen;

import house.greenhouse.evergreen.util.EvergreenConstants;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaLibraryPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvergreenPlugin implements Plugin<Project> {
    public static final Logger LOG = LoggerFactory.getLogger(EvergreenPlugin.class);

    @Override
    public void apply(@NotNull Project project) {
        project.getPlugins().apply(JavaLibraryPlugin.class);

        project.getExtensions().create("evergreen", EvergreenExtension.class, project)
                .afterEvaluate();

        project.getRepositories().maven(repo -> {
            repo.setName("Mojang");
            repo.setUrl(EvergreenConstants.MINECRAFT_LIBRARIES_URL);
        });
        project.getRepositories().maven(repo -> {
            repo.setName("Fabric");
            repo.setUrl(EvergreenConstants.FABRIC_REPO);
        });
        project.getRepositories().mavenCentral();
    }
}
