package com.polyorbis;

import com.flounder.events.*;
import com.flounder.guis.*;
import com.flounder.inputs.*;
import com.flounder.maths.*;
import com.flounder.visual.*;

import static com.flounder.platform.Constants.*;

import com.polyorbis.uis.*;
import com.polyorbis.world.*;

public class PolyGuis extends GuiMaster {
	private static final Colour COLOUR_PRIMARY = new Colour(0.1f, 0.8f, 0.2f); // Neon Green.

	public static final float SLIDE_TIME = 0.5f;

	private OverlayStartup overlayStartup;
	private OverlayHelp overlayHelp;
	private OverlayDebug overlayDebug;
	private OverlayHUD overlayHUD;
	private OverlaySlider overlaySlider;
	private OverlayDeath overlayDeath;

	private boolean gamehelp;

	public PolyGuis() {
		super();
	}

	@Override
	public void init() {
		this.overlayStartup = new OverlayStartup(FlounderGuis.get().getContainer());
		this.overlayHelp = new OverlayHelp(FlounderGuis.get().getContainer());
		this.overlayDebug = new OverlayDebug(FlounderGuis.get().getContainer());
		this.overlayHUD = new OverlayHUD(FlounderGuis.get().getContainer());
		this.overlaySlider = new OverlaySlider(FlounderGuis.get().getContainer());
		this.overlayDeath = new OverlayDeath(FlounderGuis.get().getContainer());

		this.overlayHelp.setAlphaDriver(new ConstantDriver(0.0f));
		this.overlayDebug.setAlphaDriver(new ConstantDriver(0.0f));
		this.overlayHUD.setAlphaDriver(new ConstantDriver(0.0f));
		this.overlaySlider.setAlphaDriver(new ConstantDriver(0.0f));
		this.overlayDeath.setAlphaDriver(new ConstantDriver(0.0f));

		FlounderGuis.get().getSelector().initJoysticks(0, 0, 1, 0, 1);

		FlounderEvents.get().addEvent(new EventStandard() {
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

		FlounderEvents.get().addEvent(new EventStandard() {
			private KeyButton toggleHelp = new KeyButton(GLFW_KEY_F1);

			@Override
			public boolean eventTriggered() {
				return toggleHelp.wasDown();
			}

			@Override
			public void onEvent() {
				toggleHelp();
			}
		});

		FlounderEvents.get().addEvent(new EventStandard() {
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

		FlounderEvents.get().addEvent(new EventStandard() {
			private KeyButton toggleHUD = new KeyButton(GLFW_KEY_F4);

			@Override
			public boolean eventTriggered() {
				return toggleHUD.wasDown();
			}

			@Override
			public void onEvent() {
				toggleHUD();
			}
		});
	}

	@Override
	public void update() {
		if (overlayStartup.getAlpha() == 0.0f && overlayStartup.isStarting()) {
			// Enable other GUI things.
			this.overlayHUD.setAlphaDriver(new SlideDriver(overlayHUD.getAlpha(), 1.0f, SLIDE_TIME));

			if (gamehelp = PolyConfigs.GAMEHELP_ENABLED.setReference(() -> gamehelp).getBoolean()) {
				toggleHelp();
			}

			overlayStartup.setAlphaDriver(new ConstantDriver(0.0f));
			overlayStartup.setStarting(false);
		}

		toggleDeath(PolyWorld.get().getEndGameData() != null);
	}

	@Override
	public void profile() {
	}

	@Override
	public boolean isGamePaused() {
		return overlayStartup.isStarting() || overlaySlider.getAlpha() != 0.0f || overlayDeath.getAlpha() != 0.0f || overlayHelp.getAlpha() != 0.0f;
	}

	@Override
	public float getBlurFactor() {
		if (overlayDeath.getAlpha() != 0.0f) {
			return overlayDeath.getAlpha();
		} else if (overlayHelp.getAlpha() != 0.0f) {
			return overlayHelp.getAlpha();
		}

		return overlaySlider.getBlurFactor();
	}

	@Override
	public Colour getPrimaryColour() {
		return COLOUR_PRIMARY;
	}

	public void toggleHelp() {
		if (overlayDeath.getAlpha() != 0.0f) {
			return;
		}

		if (overlayHelp.getAlpha() < 0.5f) {
			overlayHUD.setAlphaDriver(new SlideDriver(overlayHUD.getAlpha(), 0.0f, SLIDE_TIME));
			overlayDebug.setAlphaDriver(new SlideDriver(overlayDebug.getAlpha(), 0.0f, SLIDE_TIME));
			overlaySlider.setAlphaDriver(new SlideDriver(overlaySlider.getAlpha(), 0.0f, SLIDE_TIME));
			overlayHelp.setAlphaDriver(new SlideDriver(overlayHelp.getAlpha(), 1.0f, SLIDE_TIME));
		} else {
			overlayHUD.setAlphaDriver(new SlideDriver(overlayHUD.getAlpha(), 1.0f, SLIDE_TIME));
			overlayHelp.setAlphaDriver(new SlideDriver(overlayHelp.getAlpha(), 0.0f, SLIDE_TIME));
		}
	}

	public void togglePause(boolean force) {
		if (overlayDeath.getAlpha() != 0.0f || overlayHelp.getAlpha() != 0.0f) {
			return;
		}

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
		if (overlayDeath.getAlpha() != 0.0f || overlayHelp.getAlpha() != 0.0f) {
			return;
		}

		if (!isGamePaused()) {
			if (overlayDebug.getAlpha() < 0.5f) {
				overlayDebug.setAlphaDriver(new SlideDriver(overlayDebug.getAlpha(), 1.0f, SLIDE_TIME));
			} else {
				overlayDebug.setAlphaDriver(new SlideDriver(overlayDebug.getAlpha(), 0.0f, SLIDE_TIME));
			}
		}
	}

	public void toggleHUD() {
		if (overlayDeath.getAlpha() != 0.0f || overlayHelp.getAlpha() != 0.0f) {
			return;
		}

		if (!isGamePaused()) {
			if (overlayHUD.getAlpha() < 0.5f) {
				overlayHUD.setAlphaDriver(new SlideDriver(overlayHUD.getAlpha(), 1.0f, SLIDE_TIME));
			} else {
				overlayHUD.setAlphaDriver(new SlideDriver(overlayHUD.getAlpha(), 0.0f, SLIDE_TIME));
			}
		}
	}

	public void toggleDeath(boolean open) {
		if (open) {
			if (overlayDeath.getAlpha() == 0.0f) {
				overlayHUD.setAlphaDriver(new SlideDriver(overlayHUD.getAlpha(), 0.0f, SLIDE_TIME));
				overlayDebug.setAlphaDriver(new SlideDriver(overlayDebug.getAlpha(), 0.0f, SLIDE_TIME));
				overlaySlider.setAlphaDriver(new SlideDriver(overlaySlider.getAlpha(), 0.0f, SLIDE_TIME));
				overlayDeath.setAlphaDriver(new SlideDriver(overlayDeath.getAlpha(), 1.0f, SLIDE_TIME));
			}
		} else {
			if (overlayDeath.getAlpha() == 1.0f) {
				overlayHUD.setAlphaDriver(new SlideDriver(overlayHUD.getAlpha(), 1.0f, SLIDE_TIME));
				overlayDebug.setAlphaDriver(new SlideDriver(overlayDebug.getAlpha(), 0.0f, SLIDE_TIME));
				overlaySlider.setAlphaDriver(new SlideDriver(overlaySlider.getAlpha(), 0.0f, SLIDE_TIME));
				overlayDeath.setAlphaDriver(new SlideDriver(overlayDeath.getAlpha(), 0.0f, SLIDE_TIME));
			}
		}
	}

	public OverlayHUD getOverlayHUD() {
		return overlayHUD;
	}

	public OverlayDebug getOverlayDebug() {
		return overlayDebug;
	}

	public OverlaySlider getOverlaySlider() {
		return overlaySlider;
	}

	public OverlayDeath getOverlayDeath() {
		return overlayDeath;
	}

	public boolean isGameHelp() {
		return gamehelp;
	}

	public void setGamehelp(boolean gamehelp) {
		this.gamehelp = gamehelp;
	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isActive() {
		return true;
	}
}
