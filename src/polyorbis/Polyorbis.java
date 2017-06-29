package polyorbis;

import flounder.framework.*;
import flounder.framework.updater.*;
import flounder.lwjgl3.*;
import flounder.resources.*;
import polyorbis.camera.*;

public class Polyorbis extends Framework {
	public static void main(String[] args) {
		new Polyorbis().run();
		System.exit(0);
	}

	public Polyorbis() {
		super(
				"polyorbis", new UpdaterDefault(), -1,
				new Extension[]{new PolyInterface(), new PolyRenderer(), new PolyCamera(), new PolyPlayer(), new PolyGuis()}
		);
		Framework.get().addOverrides(new PlatformLwjgl(
				PolyConfigs.DISPLAY_WIDTH.getInteger(),
				PolyConfigs.DISPLAY_HEIGHT.getInteger(),
				"Polyorbis", new MyFile[]{new MyFile(MyFile.RES_FOLDER, "icon", "icon.png")},
				PolyConfigs.DISPLAY_VSYNC.getBoolean(),
				PolyConfigs.DISPLAY_ANTIALIAS.getBoolean(),
				0,
				PolyConfigs.DISPLAY_FULLSCREEN.getBoolean(),
				false,
				false,
				PolyConfigs.TEXTURES_ANISOTROPY_MAX.getFloat()
		));
		Framework.get().setFpsLimit(PolyConfigs.FRAMEWORK_FPS_LIMIT.getInteger());
	}
}
