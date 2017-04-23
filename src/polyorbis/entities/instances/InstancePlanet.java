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

	public InstancePlanet(ISpatialStructure<Entity> structure, Vector3f position, Vector3f rotation) {
		super(structure, position, rotation);
		TEXTURE.setHasAlpha(true);
		new ComponentPlanet(this,
				new InstanceSun(FlounderEntities.getEntities(), new Vector3f(-100.0f, -100.0f, -100.0f), new Vector3f()),
				new InstanceAtmosphere(FlounderEntities.getEntities(), new Vector3f(), new Vector3f()),
				new Entity[]{
						new InstanceMoon1(FlounderEntities.getEntities(), new Vector3f(10.0f, -20.0f, -10.0f), new Vector3f()),
						new InstanceMoon2(FlounderEntities.getEntities(), new Vector3f(18.0f, 26.0f, 18.0f), new Vector3f()),
						new InstanceMoon3(FlounderEntities.getEntities(), new Vector3f(-20.0f, 30.0f, 20.0f), new Vector3f()),
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
	}
}

