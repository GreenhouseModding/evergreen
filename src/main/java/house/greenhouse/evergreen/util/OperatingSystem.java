package house.greenhouse.evergreen.util;

import java.util.Locale;

public enum OperatingSystem {
    WINDOWS,
    OSX,
    LINUX;

    public static OperatingSystem getOS() {
        String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (osName.contains("win")) {
            return WINDOWS;
        }
        if (osName.contains("mac")) {
            return OSX;
        }
        return LINUX;
    }
}
