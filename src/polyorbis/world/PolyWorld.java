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
	private static final PolyWorld INSTANCE = new PolyWorld();
	public static final String PROFILE_TAB_NAME = "Poly World";

	private static MyFile[] SKYBOX_TEXTURE_FILES = {
			new MyFile(FlounderSkybox.SKYBOX_FOLDER, "starsRight.png"),
			new MyFile(FlounderSkybox.SKYBOX_FOLDER, "starsLeft.png"),
			new MyFile(FlounderSkybox.SKYBOX_FOLDER, "starsTop.png"),
			new MyFile(FlounderSkybox.SKYBOX_FOLDER, "starsBottom.png"),
			new MyFile(FlounderSkybox.SKYBOX_FOLDER, "starsBack.png"),
			new MyFile(FlounderSkybox.SKYBOX_FOLDER, "starsFront.png")
	};

	public static final Colour SUN_COLOUR = new Colour(0.8f, 0.8f, 0.8f);

	public static final Colour MOON_COLOUR = new Colour(0.1f, 0.1f, 0.3f);

	public static final float DAY_NIGHT_CYCLE = 200.0f; // The day/night length (sec).

	private static final Vector3f LIGHT_DIRECTION = new Vector3f(0.3f, 0.05f, 0.3f); // The starting light direction.

	private Entity entityPlayer;
	private Entity entityPlanet;

	private LinearDriver dayDriver;
	private float dayFactor;

	private PlayData endGameData;
	private int highsore;

	private boolean atmosphere;

	public PolyWorld() {
		super(ModuleUpdate.UPDATE_PRE, PROFILE_TAB_NAME, FlounderEntities.class);
	}

	@Override
	public void init() {
		this.entityPlayer = null;
		this.entityPlanet = null;

		this.dayDriver = new LinearDriver(0.0f, 100.0f, DAY_NIGHT_CYCLE);
		this.dayFactor = 0.0f;

		this.endGameData = null;
		this.highsore = PolyConfigs.SAVE_HIGHSCORE.getInteger();

		FlounderShadows.setBrightnessBoost(PolyConfigs.BRIGHTNESS_BOOST.getFloat());
		FlounderShadows.setShadowSize(PolyConfigs.SHADOWMAP_SIZE.getInteger());
		FlounderShadows.setShadowPCF(PolyConfigs.SHADOWMAP_PCF.getInteger());
		FlounderShadows.setShadowBias(PolyConfigs.SHADOWMAP_BIAS.getFloat());
		FlounderShadows.setShadowDarkness(PolyConfigs.SHADOWMAP_DARKNESS.getFloat());
		FlounderSkybox.setCubemap(TextureFactory.newBuilder().setCubemap(SKYBOX_TEXTURE_FILES).create());
		FlounderShadows.setShadowBoxOffset(11.5f);
		FlounderShadows.setShadowBoxDistance(32.5f);

		this.atmosphere = PolyConfigs.ATMOSPHERE_ENABLED.setReference(() -> atmosphere).getBoolean();

		reset();
	}

	@Override
	public void update() {
		// Update the sky colours and sun position.
		dayFactor = dayDriver.update(Framework.getDelta()) / 100.0f;
		Vector3f.rotate(LIGHT_DIRECTION, FlounderSkybox.getRotation().set(0.0f, dayFactor * 360.0f, 0.0f), FlounderShadows.getLightPosition());
		FlounderSkybox.setBlendFactor(1.0f);

		int score = calculateScore(entityPlayer);

		if (score > highsore) {
			highsore = score;
		}
	}

	public static void reset() {
		for (Entity entity : FlounderEntities.getEntities().getAll()) {
			entity.forceRemove();
		}

		FlounderParticles.clear();

		INSTANCE.entityPlayer = new InstancePlayer(FlounderEntities.getEntities(), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f());
		INSTANCE.entityPlanet = new InstancePlanet(FlounderEntities.getEntities(), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f());
		INSTANCE.endGameData = null;
	}

	@Override
	public void profile() {
	}

	public static Entity getEntityPlayer() {
		return INSTANCE.entityPlayer;
	}

	public static Entity getEntityPlanet() {
		return INSTANCE.entityPlanet;
	}

	public static Entity getEntitySun() {
		return INSTANCE.entityPlanet == null ? null : ((ComponentPlanet) INSTANCE.entityPlanet.getComponent(ComponentPlanet.class)).getStar();
	}

	public static float getDayFactor() {
		return INSTANCE.dayFactor;
	}

	public static void fireProjectile(Vector3f rotation, float radius, int type, Vector2f direction, boolean playerSpawned) {
		switch (type) {
			case 1:
				new InstanceProjectile1(FlounderEntities.getEntities(), new Vector3f(rotation), radius, new Vector3f(0.0f, direction.x, direction.y), playerSpawned);
				FlounderSound.playSystemSound(ComponentProjectile.SOUND_SHOOT);
				break;
			case 2:
				new InstanceProjectile2(FlounderEntities.getEntities(), new Vector3f(rotation), radius, new Vector3f(0.0f, direction.x, direction.y), playerSpawned);
				FlounderSound.playSystemSound(ComponentProjectile.SOUND_SHOOT);
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
					new InstanceProjectile3(FlounderEntities.getEntities(), new Vector3f(rotation), radius, new Vector3f(0.0f, d.x, d.y), true);
				}

				FlounderSound.playSystemSound(ComponentProjectile.SOUND_SHOOT);
				break;
		}
	}

	public static int calculateScore(Entity player) {
		if (player == null) {
			return -1;
		}

		ComponentPlayer p = (ComponentPlayer) player.getComponent(ComponentPlayer.class);

		if (p != null) {
			return calculateScore(p.getExperience(), p.getSurvivalTime());
		}

		return -1;
	}

	public static int calculateScore(int experience, float survivalTime) {
		return experience + (int) (0.1862f * survivalTime);
	}

	public static PlayData getEndGameData() {
		return INSTANCE.endGameData;
	}

	public static void setEndGameData(PlayData lastData) {
		INSTANCE.endGameData = lastData;
	}

	public static int getHighsore() {
		return INSTANCE.highsore;
	}

	public static boolean hasAtmosphere() {
		return INSTANCE.atmosphere;
	}

	public static void setAtmosphere(boolean atmosphere) {
		INSTANCE.atmosphere = atmosphere;
	}

	@Override
	public Module getInstance() {
		return INSTANCE;
	}

	@Override
	public void dispose() {
	}
}
