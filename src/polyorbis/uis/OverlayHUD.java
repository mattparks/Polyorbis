package polyorbis.uis;

import flounder.fonts.*;
import flounder.guis.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
import flounder.resources.*;
import flounder.textures.*;
import flounder.visual.*;
import polyorbis.entities.components.*;
import polyorbis.world.*;

import java.util.*;

public class OverlayHUD extends ScreenObject {
	private static final Colour POWER_DISABLED = new Colour(0.8f, 0.8f, 0.8f);
	private static final Colour POWER1_ENABLED = new Colour(0.9f, 1.0f, 0.1f);
	private static final Colour POWER2_ENABLED = new Colour(0.1f, 0.2f, 1.0f);
	private static final Colour POWER3_ENABLED = new Colour(1.0f, 0.1f, 0.2f);

	private TextureObject hudTexture;
	private TextureObject hudProgress;

	private TextObject textScore;
	private TextObject textTime;
	private TextObject textHighscore;
	private boolean updateText;

	private HudStatus statusHealth;
	private HudStatus statusPower1;
	private HudStatus statusPower2;
	private HudStatus statusPower3;

	public OverlayHUD(ScreenObject parent) {
		super(parent, new Vector2f(0.5f, 0.5f), new Vector2f(1.0f, 1.0f));
		super.setInScreenCoords(false);

		this.hudTexture = TextureFactory.newBuilder().setFile(new MyFile(FlounderGuis.GUIS_LOC, "hudSprites.png")).setNumberOfRows(3).create();
		this.hudProgress = TextureFactory.newBuilder().setFile(new MyFile(FlounderGuis.GUIS_LOC, "circularProgress.png")).setNumberOfRows(4).create();

		this.textScore = new TextObject(this, new Vector2f(0.01f, 0.02f), "Score: 0", 1.2f, FlounderFonts.SEGOE, 0.5f, GuiAlign.LEFT);
		this.textScore.setInScreenCoords(false);
		this.textScore.setColour(new Colour(1.0f, 1.0f, 1.0f));

		this.textTime = new TextObject(this, new Vector2f(0.01f, 0.06f), "Time: 0.0", 1.2f, FlounderFonts.SEGOE, 0.5f, GuiAlign.LEFT);
		this.textTime.setInScreenCoords(false);
		this.textTime.setColour(new Colour(1.0f, 1.0f, 1.0f));

		this.textHighscore = new TextObject(this, new Vector2f(0.01f, 0.10f), "High Score: 0", 1.2f, FlounderFonts.SEGOE, 0.5f, GuiAlign.LEFT);
		this.textHighscore.setInScreenCoords(false);
		this.textHighscore.setColour(new Colour(1.0f, 1.0f, 1.0f));

		this.updateText = false;

		this.statusHealth = new HudStatus(this, hudTexture, hudProgress, 2, 0.0f, new Colour(1.0f, 0.2f, 0.2f));
		this.statusPower1 = new HudStatus(this, hudTexture, hudProgress, 3, 0.13f, new Colour(0.8f, 0.8f, 0.8f));
		this.statusPower2 = new HudStatus(this, hudTexture, hudProgress, 4, 0.23f, new Colour(0.8f, 0.8f, 0.8f));
		this.statusPower3 = new HudStatus(this, hudTexture, hudProgress, 5, 0.33f, new Colour(0.8f, 0.8f, 0.8f));

		java.util.Timer timer = new java.util.Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				updateText = true;
			}
		}, 0, 100);
	}

	@Override
	public void updateObject() {
		if (updateText) {
			if (PolyWorld.get().getEntityPlayer() != null) {
				ComponentPlayer player = (ComponentPlayer) PolyWorld.get().getEntityPlayer().getComponent(ComponentPlayer.class);

				if (player != null) {
					this.statusHealth.changePersentage(player.getHealth());
					this.statusPower1.changePersentage(player.getCharge1());
					this.statusPower2.changePersentage(player.getCharge2());
					this.statusPower3.changePersentage(player.getCharge3());

					switch (player.getSelectedCharge()) {
						case 1:
							this.statusPower1.setColour(POWER1_ENABLED);
							this.statusPower2.setColour(POWER_DISABLED);
							this.statusPower3.setColour(POWER_DISABLED);
							break;
						case 2:
							this.statusPower1.setColour(POWER_DISABLED);
							this.statusPower2.setColour(POWER2_ENABLED);
							this.statusPower3.setColour(POWER_DISABLED);
							break;
						case 3:
							this.statusPower1.setColour(POWER_DISABLED);
							this.statusPower2.setColour(POWER_DISABLED);
							this.statusPower3.setColour(POWER3_ENABLED);
							break;
					}

					this.textScore.setText("Score: " + PolyWorld.get().calculateScore(player.getEntity()));
					this.textTime.setText("Time: " + Maths.roundToPlace(player.getSurvivalTime(), 2));
					int previousHighscore = (int) Float.parseFloat(this.textHighscore.getTextString().substring(12));
					this.textHighscore.setText("High Score: " + PolyWorld.get().getHighsore());
					if (previousHighscore != PolyWorld.get().getHighsore()) {
						this.textHighscore.setScaleDriver(new BounceDriver(1.2f, 1.4f, 0.8f));
					}
				}
			}
			this.updateText = false;
		}
	}

	@Override
	public void deleteObject() {
	}

	private static class HudStatus extends ScreenObject {
		private GuiObject background;
		private GuiObject foreground;
		private GuiObject progress;
		private GuiObject mainIcon;
		private float persentage;

		private HudStatus(ScreenObject parent, TextureObject hudTexture, TextureObject hudProgress, int main, float offset, Colour colour) {
			super(parent, new Vector2f(0.5f, 0.5f), new Vector2f(1.0f, 1.0f));

			this.background = new GuiObject(this, new Vector2f(0.06f + offset, 0.94f), new Vector2f(0.1f, 0.1f), hudTexture, 0);
			this.background.setInScreenCoords(false);

			this.foreground = new GuiObject(this, new Vector2f(0.06f + offset, 0.94f), new Vector2f(0.08f, 0.08f), hudTexture, 1);
			this.foreground.setInScreenCoords(false);

			this.progress = new GuiObject(this, new Vector2f(0.06f + offset, 0.94f), new Vector2f(0.08f, 0.08f), hudProgress, 1);
			this.progress.setInScreenCoords(false);
			this.progress.setColourOffset(colour);

			this.mainIcon = new GuiObject(this, new Vector2f(0.06f + offset, 0.94f), new Vector2f(0.06f, 0.06f), hudTexture, main);
			this.mainIcon.setInScreenCoords(false);

			this.persentage = 0.0f;
		}

		private void changePersentage(float newPer) {
			if (persentage != newPer) {
				bounce();
			}

			this.persentage = newPer;
		}

		private void setColour(Colour newColour) {
			if (progress.getColourOffset().lengthSquared() > newColour.lengthSquared()) {
				bounce();
			}

			this.progress.getColourOffset().set(newColour);
		}

		private void bounce() {
			this.background.setScaleDriver(new BounceDriver(1.0f, 1.2f, 0.8f));
			this.foreground.setScaleDriver(new BounceDriver(1.0f, 1.2f, 0.8f));
			this.progress.setScaleDriver(new BounceDriver(1.0f, 1.2f, 0.8f));
			this.mainIcon.setScaleDriver(new BounceDriver(1.0f, 1.2f, 0.8f));
		}

		@Override
		public void updateObject() {
			progress.setSelectedRow((int) Math.floor(persentage * (Math.pow(progress.getTexture().getNumberOfRows(), 2) - 1)));
			progress.setVisible(persentage != 0.0f);
		}

		@Override
		public void deleteObject() {
		}
	}
}
