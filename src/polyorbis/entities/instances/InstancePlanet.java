package polyorbis.entities.instances;

import flounder.entities.*;
import flounder.entities.components.*;
import flounder.maths.vectors.*;
import flounder.models.*;
import flounder.resources.*;
import flounder.space.*;
import flounder.textures.*;
import polyorbis.entities.components.*;

public class InstancePlanet extends Entity {
	private static final ModelObject MODEL = ModelFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "planet", "model.obj")).create();
	private static final TextureObject TEXTURE = TextureFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "planet", "diffuse.png")).setNumberOfRows(1).create();

	private static final Vector3f[] PINE_SPAWNS = new Vector3f[]{
			new Vector3f(0.0f, 163.4f, 23.0f),
			new Vector3f(0.0f, 311.6f, 24.1f),
			new Vector3f(0.0f, 161.8f, 146.0f),
			new Vector3f(0.0f, 299.1f, 157.7f),
			new Vector3f(0.0f, 91.8f, 145.7f)
	};
	private static final Vector3f[] BIRCH_SPAWNS = new Vector3f[]{
			new Vector3f(0.0f, 287.9f, 68.1f),
			//	new Vector3f(0.0f, 315.5f, 84.0f),
			new Vector3f(0.0f, 313.9f, 115.9f),
			new Vector3f(0.0f, 26.4f, 95.8f),
			new Vector3f(0.0f, 36.2f, 70.7f),
			//	new Vector3f(0.0f, 72.0f, 68.0f),
			new Vector3f(0.0f, 180.4f, 99.9f),
	};
	private static final Vector3f[] CACTUS_SPAWNS = new Vector3f[]{
			new Vector3f(0.0f, 23.7f, 124.4f),
			new Vector3f(0.0f, 108.2f, 79.6f),
			new Vector3f(0.0f, 127.3f, 110.5f),
			//	new Vector3f(0.0f, 216.1f, 49.4f),
			//	new Vector3f(0.0f, 261.5f, 94.7f),
			new Vector3f(0.0f, 350.3f, 84.7f),
			//	new Vector3f(0.0f, 207.7f, 113.1f),
			new Vector3f(0.0f, 216.6f, 97.7f),
	};

	public InstancePlanet(ISpatialStructure<Entity> structure, Vector3f position, Vector3f rotation) {
		super(structure, position, rotation);
		TEXTURE.setHasAlpha(true);
		new ComponentPlanet(this,
				new InstanceSun(FlounderEntities.getEntities(), new Vector3f(-150.0f, -150.0f, -150.0f), new Vector3f()),
				new InstanceAtmosphere(FlounderEntities.getEntities(), new Vector3f(), new Vector3f()),
				new Entity[]{
						new InstanceMoon1(FlounderEntities.getEntities(), new Vector3f(-15.0f, -10.0f, 15.0f), new Vector3f()),
						new InstanceMoon2(FlounderEntities.getEntities(), new Vector3f(18.0f, 20.0f, 18.0f), new Vector3f()),
						new InstanceMoon3(FlounderEntities.getEntities(), new Vector3f(-20.0f, 40.0f, -20.0f), new Vector3f()),
						new InstanceRing(FlounderEntities.getEntities(), new Vector3f(), new Vector3f())
				},
				new Vector3f[]{
						new Vector3f(0.0f, 349.3f, 52.5f),
						new Vector3f(0.0f, 172.2f, 66.4f),
						new Vector3f(0.0f, 270.1f, 79.4f),
						new Vector3f(0.0f, 145.2f, 109.1f),
						new Vector3f(0.0f, 336.5f, 125.4f),
				}
		);
		new ComponentModel(this, 6.0f, false, MODEL, TEXTURE, 1);
		new ComponentSurface(this, 1.0f, 0.0f, false, false, true);

		for (int i = 0; i < PINE_SPAWNS.length; i++) {
			new InstancePine(FlounderEntities.getEntities(), PINE_SPAWNS[i], 6.0f);
		}

		for (int i = 0; i < BIRCH_SPAWNS.length; i++) {
			new InstanceBirch(FlounderEntities.getEntities(), BIRCH_SPAWNS[i], 6.0f);
		}

		for (int i = 0; i < CACTUS_SPAWNS.length; i++) {
			new InstanceCactus(FlounderEntities.getEntities(), CACTUS_SPAWNS[i], 6.0f);
		}
	}
}

