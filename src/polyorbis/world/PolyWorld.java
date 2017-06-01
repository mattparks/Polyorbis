package polyorbis.world;

import flounder.devices.*;
import flounder.entities.*;
import flounder.framework.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
import flounder.particles.*;
import flounder.resources.*;
import flounder.shadows.*;
import flounder.skybox.*;
import flounder.textures.*;
import flounder.visual.*;
import polyorbis.*;
import polyorbis.entities.components.*;
import polyorbis.entities.instances.*;

public class PolyWorld extends Module {
	private static MyFile[] SKYBOX_TEXTURE_FILES = {
			new MyFile(FlounderSkybox.SKYBOX_FOLDER, "starsRight.png"),
			new MyFile(FlounderSkybox.SKYBOX_FOLDER, "starsLeft.png"),
			new MyFile(FlounderSkybox.SKYBOX_FOLDER, "starsTop.png"),
			new MyFile(FlounderSkybox.SKYBOX_FOLDER, "starsBottom.png"),
			new MyFile(FlounderSkybox.SKYBOX_FOLDER, "starsBack.png"),
			new MyFile(FlounderSkybox.SKYBOX_FOLDER, "starsFront.png")
	};

	public static final Colour SUN_COLOUR = new Colour(0.75f, 0.75f, 0.75f);

	public static final Colour MOON_COLOUR = new Colour(0.2f, 0.2f, 0.4f);

	public static final float DAY_NIGHT_CYCLE = 175.0f; // The day/night length (sec).

	private static final Vector3f LIGHT_DIRECTION = new Vector3f(0.3f, 0.05f, 0.3f); // The starting light direction.

	private Entity entityPlayer;
	private Entity entityPlanet;

	private LinearDriver dayDriver;
	private float dayFactor;

	private PlayData endGameData;
	private int highsore;

	private boolean atmosphere;

	public PolyWorld() {
		super(FlounderEntities.class);
	}

	@Handler.Function(Handler.FLAG_INIT)
	public void init() {
		this.entityPlayer = null;
		this.entityPlanet = null;

		this.dayDriver = new LinearDriver(0.0f, 100.0f, DAY_NIGHT_CYCLE);
		this.dayFactor = 0.0f;

		this.endGameData = null;
		this.highsore = PolyConfigs.SAVE_HIGHSCORE.getInteger();

		FlounderShadows.get().setBrightnessBoost(PolyConfigs.BRIGHTNESS_BOOST.getFloat());
		FlounderShadows.get().setShadowSize(PolyConfigs.SHADOWMAP_SIZE.getInteger());
		FlounderShadows.get().setShadowPCF(PolyConfigs.SHADOWMAP_PCF.getInteger());
		FlounderShadows.get().setShadowBias(PolyConfigs.SHADOWMAP_BIAS.getFloat());
		FlounderShadows.get().setShadowDarkness(PolyConfigs.SHADOWMAP_DARKNESS.getFloat());
		FlounderShadows.get().setRenderUnlimited(PolyConfigs.SHADOWMAP_UNLIMITED.getBoolean());
		FlounderSkybox.get().setCubemap(TextureFactory.newBuilder().setCubemap(SKYBOX_TEXTURE_FILES).create());
		FlounderShadows.get().setShadowBoxOffset(13.0f);
		FlounderShadows.get().setShadowBoxDistance(33.0f);

		this.atmosphere = PolyConfigs.ATMOSPHERE_ENABLED.setReference(() -> atmosphere).getBoolean();

		reset();
	}

	@Handler.Function(Handler.FLAG_UPDATE_PRE)
	public void update() {
		// Update the sky colours and sun position.
		dayFactor = dayDriver.update(Framework.getDelta()) / 100.0f;
		Vector3f.rotate(LIGHT_DIRECTION, FlounderSkybox.get().getRotation().set(0.0f, dayFactor * 360.0f, 0.0f), FlounderShadows.get().getLightPosition()).normalize();
		FlounderSkybox.get().setBlendFactor(1.0f);

		int score = calculateScore(entityPlayer);

		if (score > highsore) {
			highsore = score;
		}
	}

	public void reset() {
		for (Entity entity : FlounderEntities.get().getEntities().getAll()) {
			entity.forceRemove();
		}

		FlounderParticles.get().clear();

		this.entityPlayer = new InstancePlayer(FlounderEntities.get().getEntities(), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f());
		this.entityPlanet = new InstancePlanet(FlounderEntities.get().getEntities(), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f());
		this.endGameData = null;
	}

	public Entity getEntityPlayer() {
		return this.entityPlayer;
	}

	public Entity getEntityPlanet() {
		return this.entityPlanet;
	}

	public Entity getEntitySun() {
		return this.entityPlanet == null ? null : ((ComponentPlanet) this.entityPlanet.getComponent(ComponentPlanet.class)).getStar();
	}

	public float getDayFactor() {
		return this.dayFactor;
	}

	public void fireProjectile(Vector3f rotation, float radius, int type, Vector2f direction, boolean playerSpawned) {
		switch (type) {
			case 1:
				new InstanceProjectile1(FlounderEntities.get().getEntities(), new Vector3f(rotation), radius, new Vector3f(0.0f, direction.x, direction.y), playerSpawned);
				FlounderSound.get().playSystemSound(ComponentProjectile.SOUND_SHOOT);
				break;
			case 2:
				new InstanceProjectile2(FlounderEntities.get().getEntities(), new Vector3f(rotation), radius, new Vector3f(0.0f, direction.x, direction.y), playerSpawned);
				FlounderSound.get().playSystemSound(ComponentProjectile.SOUND_SHOOT);
				break;
			case 3:
				float amount = 28.0f;

				for (int i = 0; i < amount; i++) {
					float theta = 360.0f * ((float) i / amount);
					Vector2f d = new Vector2f();
					Vector2f.rotate(new Vector2f(1.0f, 0.0f), theta, d);

					// Fixes any zero vectors.
					if (d.isZero()) {
						d.x += 0.03f;
					}

					d.normalize();
					new InstanceProjectile3(FlounderEntities.get().getEntities(), new Vector3f(rotation), radius, new Vector3f(0.0f, d.x, d.y), true);
				}

				FlounderSound.get().playSystemSound(ComponentProjectile.SOUND_SHOOT);
				break;
		}
	}

	public int calculateScore(Entity player) {
		if (player == null) {
			return -1;
		}

		ComponentPlayer p = (ComponentPlayer) player.getComponent(ComponentPlayer.class);

		if (p != null) {
			return calculateScore(p.getExperience(), p.getSurvivalTime());
		}

		return -1;
	}

	public int calculateScore(int experience, float survivalTime) {
		return experience + (int) (0.1862f * survivalTime);
	}

	public PlayData getEndGameData() {
		return this.endGameData;
	}

	public void setEndGameData(PlayData lastData) {
		this.endGameData = lastData;
	}

	public int getHighsore() {
		return this.highsore;
	}

	public boolean hasAtmosphere() {
		return this.atmosphere;
	}

	public void setAtmosphere(boolean atmosphere) {
		this.atmosphere = atmosphere;
	}


	@Handler.Function(Handler.FLAG_DISPOSE)
	public void dispose() {
	}

	@Module.Instance
	public static PolyWorld get() {
		return (PolyWorld) Framework.getInstance(PolyWorld.class);
	}

	@Module.TabName
	public static String getTab() {
		return "Poly World";
	}
}
