package polyorbis.uis;

import flounder.fonts.*;
import flounder.guis.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
import flounder.visual.*;
import polyorbis.world.*;

public class OverlayDeath extends ScreenObject {
	private TextObject textScore;
	private TextObject textTime;
	private TextObject textKills;
	private TextObject textHighscore;

	private PlayData lastData;

	public OverlayDeath(ScreenObject parent) {
		super(parent, new Vector2f(0.5f, 0.5f), new Vector2f(1.0f, 1.0f));
		super.setInScreenCoords(false);

		// Game Title.
		TextObject title = new TextObject(this, new Vector2f(0.5f, 0.10f), "Game Over!", 4.0f, FlounderFonts.CANDARA, 1.0f, GuiAlign.CENTRE);
		title.setInScreenCoords(true);
		title.setColour(new Colour(1.0f, 1.0f, 1.0f, 1.0f));
		title.setBorderColour(new Colour(0.0f, 0.0f, 0.0f));
		title.setBorder(new ConstantDriver(0.022f));

		TextObject textShare = new TextObject(this, new Vector2f(0.5f, 0.22f), "Share your high scores on our ldjam page along with a review. Thank you for playing Polyorbis!", 1.4f, FlounderFonts.SEGOE, 0.5f, GuiAlign.CENTRE);
		textShare.setInScreenCoords(true);
		textShare.setColour(new Colour(1.0f, 1.0f, 1.0f));

		this.textScore = new TextObject(this, new Vector2f(0.5f, 0.32f), "Score: 0", 1.4f, FlounderFonts.SEGOE, 0.5f, GuiAlign.CENTRE);
		this.textScore.setInScreenCoords(true);
		this.textScore.setColour(new Colour(1.0f, 1.0f, 1.0f));

		this.textTime = new TextObject(this, new Vector2f(0.5f, 0.37f), "Time: 0.0", 1.4f, FlounderFonts.SEGOE, 0.5f, GuiAlign.CENTRE);
		this.textTime.setInScreenCoords(true);
		this.textTime.setColour(new Colour(1.0f, 1.0f, 1.0f));

		this.textKills = new TextObject(this, new Vector2f(0.5f, 0.42f), "Kills: 0", 1.4f, FlounderFonts.SEGOE, 0.5f, GuiAlign.CENTRE);
		this.textKills.setInScreenCoords(true);
		this.textKills.setColour(new Colour(1.0f, 1.0f, 1.0f));

		this.textHighscore = new TextObject(this, new Vector2f(0.5f, 0.47f), "High Score: 0", 1.4f, FlounderFonts.SEGOE, 0.5f, GuiAlign.CENTRE);
		this.textHighscore.setInScreenCoords(true);
		this.textHighscore.setColour(new Colour(1.0f, 1.0f, 1.0f));

		// Restart.
		GuiButtonText exitToMenu = new GuiButtonText(this, new Vector2f(0.5f, 0.60f), "Try Again", GuiAlign.CENTRE);
		exitToMenu.addLeftListener(() -> PolyWorld.get().reset());

		this.lastData = null;
	}

	@Override
	public void updateObject() {
		PlayData data = PolyWorld.get().getEndGameData();

		if (lastData != data) {
			lastData = data;

			if (data != null) {
				textScore.setText("Score: " + PolyWorld.get().calculateScore(data.getExperience(), data.getSurvivalTime()));
				textTime.setText("Time: " + Maths.roundToPlace(data.getSurvivalTime(), 1));
				textKills.setText("Kills: " + data.getKills());
				textHighscore.setText("High Score: " + PolyWorld.get().getHighsore());
			}
		}
	}

	@Override
	public void deleteObject() {

	}
}
