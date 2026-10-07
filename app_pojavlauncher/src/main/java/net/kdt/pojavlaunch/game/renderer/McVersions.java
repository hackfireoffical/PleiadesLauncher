package net.kdt.pojavlaunch.game.renderer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Small helper for reasoning about Minecraft version ids without touching Android APIs
 * (so it can be unit-tested on the JVM).
 *
 * Callers should pass the base Minecraft version id (e.g. "26.3", "1.20.1", "24w14a"),
 * not a loader-composed id such as "fabric-loader-0.15.11-1.20.1".
 */
public final class McVersions {
    private static final Pattern OLD_PREFIX = Pattern.compile("^(?:[abc]\\d|inf-|rd-)");
    private static final Pattern SNAPSHOT = Pattern.compile("^(\\d{2})w(\\d{2})[a-z]");
    private static final Pattern RELEASE = Pattern.compile("^(\\d+)\\.(\\d+)");

    private McVersions() {}

    /**
     * @return true if the version is 1.17 or newer (including the 26.x numbering scheme).
     * Unparsable ids ("latest_release", custom names) are treated as modern, so the launcher
     * never wrongly pushes a legacy-only renderer onto them.
     */
    public static boolean isModern(String version) {
        if (version == null) return true;
        String v = version.trim();
        if (OLD_PREFIX.matcher(v).find()) return false;

        Matcher snapshot = SNAPSHOT.matcher(v);
        if (snapshot.find()) {
            int year = Integer.parseInt(snapshot.group(1));
            int week = Integer.parseInt(snapshot.group(2));
            // 20w45a is the first 1.17 snapshot
            return year > 20 || (year == 20 && week >= 45);
        }

        Matcher release = RELEASE.matcher(v);
        if (release.find()) {
            int major = Integer.parseInt(release.group(1));
            int minor = Integer.parseInt(release.group(2));
            if (major >= 26) return true;
            if (major == 1) return minor >= 17;
            return false;
        }
        return true;
    }
}
