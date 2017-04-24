package polyorbis.entities.instances;

import flounder.entities.*;
import flounder.entities.components.*;
import flounder.maths.vectors.*;
import flounder.models.*;
import flounder.resources.*;
import flounder.space.*;
import flounder.textures.*;

public class InstanceAtmosphere extends Entity {
	private static final ModelObject MODEL = ModelFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "atmosphere", "model.obj")).create();
	private static final TextureObject TEXTURE = TextureFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "atmosphere", "diffuse.png")).setNumberOfRows(1).create();

	public InstanceAtmosphere(ISpatialStructure<Entity> structure, Vector3f position, Vector3f rotation) {
		super(structure, position, rotation);
		TEXTURE.setHasAlpha(true);
		new ComponentModel(this, 7.2f, MODEL, TEXTURE, 1);
		new ComponentAlpha(this, 0.16f);
		new ComponentSurface(this, 1.0f, 0.0f, true, false, false);
	}
}

