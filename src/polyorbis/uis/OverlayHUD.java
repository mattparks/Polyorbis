/*
 * Copyright (C) 2017, Equilibrium Games - All Rights Reserved
 *
 * This source file is part of New Kosmos
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package polyorbis.uis;

import flounder.fonts.*;
import flounder.guis.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
import flounder.resources.*;
import flounder.textures.*;
import polyorbis.entities.components.*;
import polyorbis.world.*;

import java.util.*;

public class OverlayHUD extends ScreenObject {
	private TextureObject hudTexture;
	private TextureObject hudProgress;

	private TextObject textScore;
	private TextObject textTime;
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

		this.updateText = false;

		this.statusHealth = new HudStatus(this, hudTexture, hudProgress, 2, 0.0f, new Colour(1.0f, 0.2f, 0.2f));
		this.statusPower1 = new HudStatus(this, hudTexture, hudProgress, 3, 0.15f, new Colour(0.8f, 0.8f, 0.8f));
		this.statusPower2 = new HudStatus(this, hudTexture, hudProgress, 4, 0.25f, new Colour(0.8f, 0.8f, 0.8f));
		this.statusPower3 = new HudStatus(this, hudTexture, hudProgress, 5, 0.35f, new Colour(0.8f, 0.8f, 0.8f));

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
		if (PolyWorld.getEntityPlayer() != null) {
			ComponentPlayer player = (ComponentPlayer) PolyWorld.getEntityPlayer().getComponent(ComponentPlayer.class);

			if (player != null) {
				this.statusHealth.persentage = player.getHealth();
				this.statusPower1.persentage = player.getCharge1();
				this.statusPower2.persentage = player.getCharge2();
				this.statusPower3.persentage = player.getCharge3();

				switch (player.getSelectedCharge()) {
					case 1:
						this.statusPower1.progress.getColourOffset().set(0.2f, 1.0f, 0.2f);
						this.statusPower2.progress.getColourOffset().set(0.8f, 0.8f, 0.8f);
						this.statusPower3.progress.getColourOffset().set(0.8f, 0.8f, 0.8f);
						break;
					case 2:
						this.statusPower1.progress.getColourOffset().set(0.8f, 0.8f, 0.8f);
						this.statusPower2.progress.getColourOffset().set(0.2f, 1.0f, 0.2f);
						this.statusPower3.progress.getColourOffset().set(0.8f, 0.8f, 0.8f);
						break;
					case 3:
						this.statusPower1.progress.getColourOffset().set(0.8f, 0.8f, 0.8f);
						this.statusPower2.progress.getColourOffset().set(0.8f, 0.8f, 0.8f);
						this.statusPower3.progress.getColourOffset().set(0.2f, 1.0f, 0.2f);
						break;
				}

					this.textScore.setText("Score: " + PolyWorld.calculateScore(player.getExperience(), player.getSurvivalTime()));
					this.textTime.setText("Time: " + Maths.roundToPlace(player.getSurvivalTime(), 2));

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

		@Override
		public void updateObject() {
			progress.setSelectedRow((int) Math.floor(persentage * Math.pow(progress.getTexture().getNumberOfRows(), 2)) - 1);
		}

		@Override
		public void deleteObject() {
		}
	}
}
