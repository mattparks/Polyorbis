package polyorbis;

import flounder.devices.FlounderDisplay;
import flounder.framework.*;
import flounder.framework.updater.*;
import flounder.resources.*;
import flounder.textures.*;
import org.lwjgl.glfw.GLFW;
import polyorbis.camera.*;

public class Polyorbis extends Framework {
	public static void main(String[] args) {
		new Polyorbis().run();
		System.exit(0);
	}

	public Polyorbis() {
		super(
				"polyorbis", new UpdaterDefault(GLFW::glfwGetTime), -1,
				new Extension[]{new PolyInterface(), new PolyRenderer(), new PolyCamera(), new PolyPlayer(), new PolyGuis()}
		);
	}
}
