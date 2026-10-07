package net.kdt.pojavlaunch.game.renderer;

import static net.kdt.pojavlaunch.game.renderer.def.Renderers.GL4ES_RENDERER;
import static net.kdt.pojavlaunch.game.renderer.def.Renderers.LTW_RENDERER;
import static net.kdt.pojavlaunch.game.renderer.def.Renderers.ZINK_RENDERER;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Picks a renderer for a Minecraft version from what the device actually offers.
 *
 * The preference order below is a heuristic, NOT a benchmark result. It only chooses among
 * renderers that are enabled and whose device check ({@link RenderSpec#compatibleDevice}) passes.
 * Users can always pick any listed renderer manually.
 */
public final class RendererRecommender {
    /** Vulkan 1.1 */
    private static final int VULKAN_1_1 = 0x00401000;

    public static final class Recommendation {
        /** Recommended renderer tag, or null if nothing usable was found. */
        public final String tag;
        public final String reason;
        /** Non-null when the recommendation (or lack of one) comes with a caveat. */
        public final String warning;

        Recommendation(String tag, String reason, String warning) {
            this.tag = tag;
            this.reason = reason;
            this.warning = warning;
        }
    }

    private RendererRecommender() {}

    public static boolean hasVulkan(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return false;
        return context.getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_VULKAN_HARDWARE_VERSION, VULKAN_1_1);
    }

    /**
     * @return a warning like "may not support Minecraft 26.3" for this renderer, or null
     * if there is no known problem.
     */
    public static String warningFor(String tag, String mcVersion) {
        if (GL4ES_RENDERER.equals(tag) && McVersions.isModern(mcVersion)) {
            return "This renderer may not support Minecraft " + mcVersion + ".";
        }
        return null;
    }

    public static Recommendation recommend(Context context, String mcVersion) {
        RendererCache available = RendererCache.getCompatibleRenderers(context);
        boolean modern = McVersions.isModern(mcVersion);
        boolean vulkan = hasVulkan(context);

        String[] order;
        String why;
        if (!modern) {
            order = new String[]{GL4ES_RENDERER, LTW_RENDERER};
            why = "Older Minecraft version.";
        } else if (vulkan) {
            order = new String[]{ZINK_RENDERER, LTW_RENDERER, GL4ES_RENDERER};
            why = "Vulkan 1.1 is available on this device.";
        } else {
            order = new String[]{LTW_RENDERER, GL4ES_RENDERER};
            why = "Vulkan 1.1 was not detected on this device.";
        }

        for (String tag : order) {
            if (!available.rendererIds.contains(tag)) continue;
            String warning = warningFor(tag, mcVersion);
            if (warning != null) continue;
            return new Recommendation(tag, why, null);
        }

        // Nothing in the preferred list is usable: fall back to whatever is available, with a caveat.
        for (String tag : available.rendererIds) {
            return new Recommendation(tag, why, warningFor(tag, mcVersion));
        }
        return new Recommendation(null, why, "No compatible renderer is available on this device.");
    }
}
