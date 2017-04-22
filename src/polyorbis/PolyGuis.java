package polyorbis;

import flounder.events.*;
import flounder.framework.*;
import flounder.guis.*;
import flounder.inputs.*;
import flounder.maths.*;
import flounder.visual.*;
import polyorbis.uis.*;

import static org.lwjgl.glfw.GLFW.*;

public class PolyGuis extends GuiMaster {
	private static final Colour COLOUR_PRIMARY = new Colour(0.1f, 0.8f, 0.2f); // Neon Green.

	public static final float SLIDE_TIME = 0.5f;

	private OverlayDebug overlayDebug;
	private OverlayHUD overlayHUD;
	private OverlaySlider overlaySlider;

	public PolyGuis() {
		super();
	}

	@Override
	public void init() {
		this.overlayDebug = new OverlayDebug(FlounderGuis.getContainer());
		this.overlayHUD = new OverlayHUD(FlounderGuis.getContainer());
		this.overlaySlider = new OverlaySlider(FlounderGuis.getContainer());

		this.overlayDebug.setAlphaDriver(new ConstantDriver(Framework.isRunningFromJar() ? 0.0f : 1.0f));
		this.overlayHUD.setAlphaDriver(new ConstantDriver(1.0f));
		this.overlaySlider.setAlphaDriver(new ConstantDriver(0.0f));

		FlounderGuis.getSelector().initJoysticks(0, 0, 1, 0, 1);

		FlounderEvents.addEvent(new IEvent() {
			private CompoundButton escape = new CompoundButton(new KeyButton(GLFW_KEY_ESCAPE), new JoystickButton(0, 7));

			@Override
			public boolean eventTriggered() {
				return escape.wasDown();
			}

			@Override
			public void onEvent() {
				togglePause(false);
			}
		});

		FlounderEvents.addEvent(new IEvent() {
			private KeyButton toggleDebug = new KeyButton(GLFW_KEY_F3);

			@Override
			public boolean eventTriggered() {
				return toggleDebug.wasDown();
			}

			@Override
			public void onEvent() {
				toggleDebug();
			}
		});
	}

	@Override
	public void update() {
	}

	@Override
	public void profile() {
	}

	@Override
	public boolean isGamePaused() {
		return overlaySlider.getAlpha() > 0.1f;
	}

	@Override
	public float getBlurFactor() {
		return overlaySlider.getBlurFactor();
	}

	@Override
	public Colour getPrimaryColour() {
		return COLOUR_PRIMARY;
	}

	public void togglePause(boolean force) {
		if (force) {
			overlayHUD.setAlphaDriver(new ConstantDriver(1.0f));
			overlayDebug.setAlphaDriver(new ConstantDriver(0.0f));
			overlaySlider.setAlphaDriver(new ConstantDriver(0.0f));
		} else {
			if (isGamePaused()) {
				overlayHUD.setAlphaDriver(new SlideDriver(overlayHUD.getAlpha(), 1.0f, SLIDE_TIME));
				overlaySlider.setAlphaDriver(new SlideDriver(overlaySlider.getAlpha(), 0.0f, SLIDE_TIME));
			} else {
				overlayHUD.setAlphaDriver(new SlideDriver(overlayHUD.getAlpha(), 0.0f, SLIDE_TIME));
				overlayDebug.setAlphaDriver(new SlideDriver(overlayDebug.getAlpha(), 0.0f, SLIDE_TIME));
				overlaySlider.setAlphaDriver(new SlideDriver(overlaySlider.getAlpha(), 1.0f, SLIDE_TIME));
			}
		}
	}

	public void toggleDebug() {
		if (!isGamePaused()) {
			if (overlayDebug.getAlpha() < 0.5f) {
				overlayDebug.setAlphaDriver(new SlideDriver(overlayDebug.getAlpha(), 1.0f, SLIDE_TIME));
			} else {
				overlayDebug.setAlphaDriver(new SlideDriver(overlayDebug.getAlpha(), 0.0f, SLIDE_TIME));
			}
		}
	}

	public OverlayDebug getOverlayDebug() {
		return overlayDebug;
	}

	public OverlaySlider getOverlaySlider() {
		return overlaySlider;
	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isActive() {
		return true;
	}
}
