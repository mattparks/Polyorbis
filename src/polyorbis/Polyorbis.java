package polyorbis;

import flounder.framework.*;
import flounder.framework.updater.*;
import flounder.helpers.*;
import flounder.lwjgl3.*;
import flounder.lwjgl3.devices.*;
import flounder.profiling.*;
import flounder.resources.*;
import flounder.textures.*;
import org.lwjgl.glfw.*;
import polyorbis.camera.*;

public class Polyorbis extends Framework {
	public static void main(String[] args) {
		new Polyorbis().run();
		System.exit(0);
	}

	public Polyorbis() {
		super("polyorbis", new UpdaterDefault(GLFW::glfwGetTime), -1,
				new Extension[]{new PlatformLwjgl(), new PolyInterface(), new PolyRenderer(), new PolyCamera(), new PolyPlayer(), new PolyGuis()},
				new Module[]{new LwjglDisplay(
						PolyConfigs.DISPLAY_WIDTH.getInteger(),
						PolyConfigs.DISPLAY_HEIGHT.getInteger(),
						"Polyorbis", new MyFile[]{new MyFile(MyFile.RES_FOLDER, "icon", "icon.png")},
						PolyConfigs.DISPLAY_VSYNC.getBoolean(),
						PolyConfigs.DISPLAY_ANTIALIAS.getBoolean(),
						0,
						PolyConfigs.DISPLAY_FULLSCREEN.getBoolean(),
						false
				), new LwjglJoysicks(), new LwjglKeyboard(), new LwjglMouse(), new LwjglSound()});
		FlounderProfiler.get().toggle(PolyConfigs.PROFILER_ENABLED.getBoolean());
		Framework.setFpsLimit(PolyConfigs.FRAMEWORK_FPS_LIMIT.getInteger());
		FlounderTextures.get().setAnisotropyLevel(PolyConfigs.TEXTURES_ANISOTROPY_MAX.getFloat());
		OpenGlUtils.goWireframe(PolyConfigs.WIREFRAME_ENABLED.getBoolean());
	}
}
