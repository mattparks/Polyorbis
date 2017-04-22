package polyorbis;

import flounder.guis.*;
import flounder.maths.*;
import flounder.visual.*;
import polyorbis.uis.*;

public class PolyGuis extends GuiMaster {
	private static final Colour COLOUR_PRIMARY = new Colour(0.90196078431f, 0.08235294117f, 0.08235294117f); // Charger Red.
	// private static final Colour COLOUR_PRIMARY = new Colour(0.1f, 0.8f, 0.2f); // Neon Green.
	// private static final Colour COLOUR_PRIMARY = new Colour(0.0824f, 0.396f, 0.753f); // Water Blue.

	private OverlayDebug overlayDebug;

	public PolyGuis() {
		super();
	}

	@Override
	public void init() {
		this.overlayDebug = new OverlayDebug(FlounderGuis.getContainer());

		this.overlayDebug.setAlphaDriver(new ConstantDriver(1.0f));

		FlounderGuis.getSelector().initJoysticks(0, 0, 1, 0, 1);
	}

	@Override
	public void update() {
	}

	@Override
	public void profile() {
	}

	@Override
	public boolean isGamePaused() {
		return false;
	}

	@Override
	public float getBlurFactor() {
		return 0.0f;
	}

	@Override
	public Colour getPrimaryColour() {
		return COLOUR_PRIMARY;
	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isActive() {
		return true;
	}
}
