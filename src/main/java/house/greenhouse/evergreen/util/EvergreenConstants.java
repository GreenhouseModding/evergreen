package house.greenhouse.evergreen.util;

public class EvergreenConstants {
    // Xplat
    public static final String BASE_LIBRARIES_URL = "https://libraries.minecraft.net/";
    public static final String BASE_RESOURCES_URL = "https://resources.download.minecraft.net/";
    public static final String VERSION_MANIFEST = "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json";

    // Fabric
    public static final String FABRIC_REPO = "https://maven.fabricmc.net/";

    private EvergreenConstants() {}

    public static final class Configurations {
        public static final String LOCAL_RUNTIME = "localRuntime";
    }

    public static final class SourceSets {
        public static final String FABRIC = "fabric";
        public static final String NEOFORGE = "neoforge";
    }
}
