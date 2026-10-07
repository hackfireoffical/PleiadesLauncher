package net.kdt.pojavlaunch.game.renderer;

import static net.kdt.pojavlaunch.game.renderer.def.Renderers.FREEDRENO_RENDERER;
import static net.kdt.pojavlaunch.game.renderer.def.Renderers.GL4ES_RENDERER;
import static net.kdt.pojavlaunch.game.renderer.def.Renderers.LEGACYZINK_RENDERER;
import static net.kdt.pojavlaunch.game.renderer.def.Renderers.LTW_RENDERER;
import static net.kdt.pojavlaunch.game.renderer.def.Renderers.MESA_RENDERER;
import static net.kdt.pojavlaunch.game.renderer.def.Renderers.MESA_RENDERER_EXT;
import static net.kdt.pojavlaunch.game.renderer.def.Renderers.ZINK_RENDERER;

/**
 * Human-readable metadata for the built-in renderers, keyed by renderer tag.
 * Kept separate from {@link RenderSpec} so the launch path is untouched.
 */
public final class RendererMetadata {
    public enum Backend { OPENGL_ES, VULKAN }

    public static final class Info {
        public final String description;
        public final Backend backend;

        Info(String description, Backend backend) {
            this.description = description;
            this.backend = backend;
        }
    }

    private static final Info UNKNOWN = new Info("Unknown renderer", Backend.OPENGL_ES);

    private RendererMetadata() {}

    public static Info get(String tag) {
        if (tag == null) return UNKNOWN;
        switch (tag) {
            case GL4ES_RENDERER:
                return new Info("OpenGL 2.1 wrapper on top of OpenGL ES 2. Intended for older Minecraft versions.", Backend.OPENGL_ES);
            case LTW_RENDERER:
                return new Info("OpenLTW: OpenGL translation layer on top of OpenGL ES 3.", Backend.OPENGL_ES);
            case ZINK_RENDERER:
                return new Info("Mesa Zink: OpenGL implemented on top of Vulkan.", Backend.VULKAN);
            case LEGACYZINK_RENDERER:
                return new Info("Older Mesa Zink build: OpenGL on top of Vulkan.", Backend.VULKAN);
            case FREEDRENO_RENDERER:
                return new Info("Mesa using the Turnip Vulkan driver (Adreno GPUs).", Backend.VULKAN);
            case MESA_RENDERER:
            case MESA_RENDERER_EXT:
                return new Info("Mesa-based renderer.", Backend.VULKAN);
            default:
                return UNKNOWN;
        }
    }
}
