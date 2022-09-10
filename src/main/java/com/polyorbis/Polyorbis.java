package com.polyorbis;

import com.flounder.devices.FlounderDisplay;
import com.flounder.framework.*;
import com.flounder.framework.updater.*;
import com.flounder.resources.*;
import com.flounder.textures.*;

import com.polyorbis.camera.*;

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
	}
}
