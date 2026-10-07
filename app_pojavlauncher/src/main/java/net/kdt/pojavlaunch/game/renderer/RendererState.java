package net.kdt.pojavlaunch.game.renderer;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Persistent enable/disable state for renderers.
 *
 * A disabled renderer is hidden from the renderer list and is never recommended.
 * It does NOT block an instance that already has that renderer set explicitly.
 */
public final class RendererState {
    private static final String PREFS = "pleiades_renderers";
    private static final String KEY_DISABLED = "disabled";

    private RendererState() {}

    private static SharedPreferences prefs(Context context) {
        return context.getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public static boolean isEnabled(Context context, String tag) {
        Set<String> disabled = prefs(context).getStringSet(KEY_DISABLED, null);
        return disabled == null || !disabled.contains(tag);
    }

    public static void setEnabled(Context context, String tag, boolean enabled) {
        SharedPreferences p = prefs(context);
        Set<String> disabled = new HashSet<>(p.getStringSet(KEY_DISABLED, new HashSet<String>()));
        if (enabled) disabled.remove(tag);
        else disabled.add(tag);
        p.edit().putStringSet(KEY_DISABLED, disabled).apply();
        // The compatible-renderer list is cached; rebuild it on next access.
        RendererCache.releaseRendererCache();
    }
}
