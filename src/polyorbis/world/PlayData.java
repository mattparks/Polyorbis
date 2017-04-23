package polyorbis.world;

public class PlayData {
	private int experience;
	private float survivalTime;
	private int kills;

	public PlayData(int experience, float survivalTime, int kills) {
		this.experience = experience;
		this.survivalTime = survivalTime;
		this.kills = kills;
	}

	public int getExperience() {
		return experience;
	}

	public float getSurvivalTime() {
		return survivalTime;
	}

	public int getKills() {
		return kills;
	}
}
