package polyorbis.world;

import flounder.entities.*;
import flounder.framework.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
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

	public static final Colour SUN_COLOUR = new Colour(0.7f, 0.7f, 0.7f);

	public static final Colour MOON_COLOUR = new Colour(0.1f, 0.1f, 0.3f);

	public static final float DAY_NIGHT_CYCLE = 200.0f; // The day/night length (sec).

	private static final Vector3f LIGHT_DIRECTION = new Vector3f(0.3f, 0.05f, 0.3f); // The starting light direction.

	private Entity entityPlayer;
	private Entity entityPlanet;

	private LinearDriver dayDriver;
	private float dayFactor;

	public PolyWorld() {
		super(ModuleUpdate.UPDATE_PRE, PROFILE_TAB_NAME, FlounderEntities.class);
	}

	@Override
	public void init() {
		this.entityPlayer = new InstancePlayer(FlounderEntities.getEntities(), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f());
		this.entityPlanet = new InstancePlanet(FlounderEntities.getEntities(), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f());

		this.dayDriver = new LinearDriver(0.0f, 100.0f, DAY_NIGHT_CYCLE);
		this.dayFactor = 0.0f;

		FlounderShadows.setBrightnessBoost(PolyConfigs.BRIGHTNESS_BOOST.getFloat());
		FlounderShadows.setShadowSize(PolyConfigs.SHADOWMAP_SIZE.getInteger());
		FlounderShadows.setShadowPCF(PolyConfigs.SHADOWMAP_PCF.getInteger());
		FlounderShadows.setShadowBias(PolyConfigs.SHADOWMAP_BIAS.getFloat());
		FlounderShadows.setShadowDarkness(PolyConfigs.SHADOWMAP_DARKNESS.getFloat());
		FlounderSkybox.setCubemap(TextureFactory.newBuilder().setCubemap(SKYBOX_TEXTURE_FILES).create());
		FlounderShadows.setShadowBoxOffset(20.0f);
		FlounderShadows.setShadowBoxDistance(30.0f);
	}

	@Override
	public void update() {
		// Update the sky colours and sun position.
		dayFactor = dayDriver.update(Framework.getDelta()) / 100.0f;
		Vector3f.rotate(LIGHT_DIRECTION, FlounderSkybox.getRotation().set(0.0f, dayFactor * 360.0f, 0.0f), FlounderShadows.getLightPosition());
		FlounderSkybox.setBlendFactor(1.0f);
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

	@Override
	public Module getInstance() {
		return INSTANCE;
	}

	@Override
	public void dispose() {
	}
}
