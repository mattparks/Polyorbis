package polyorbis.entities.instances;

import flounder.entities.*;
import flounder.entities.components.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
import flounder.models.*;
import flounder.resources.*;
import flounder.space.*;
import flounder.textures.*;
import polyorbis.entities.components.*;

public class InstancePlayer extends Entity {
	private static final Colour COLOUR_OFFSET = new Colour(Maths.randomInRange(0.0f, 1.0f), Maths.randomInRange(0.0f, 1.0f), Maths.randomInRange(0.0f, 1.0f));

	private static final ModelObject MODEL = ModelFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "player", "model.obj")).create();
	private static final TextureObject TEXTURE = TextureFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "player", "diffuse.png")).setNumberOfRows(1).create();

	public InstancePlayer(ISpatialStructure<Entity> structure, Vector3f position, Vector3f rotation) {
		super(structure, position, rotation);
		new ComponentPlayer(this);
		new ComponentModel(this, 0.23f, MODEL, TEXTURE, 1);
		((ComponentModel) getComponent(ComponentModel.class)).setColourOffset(COLOUR_OFFSET);
		new ComponentSurface(this, 1.0f, 0.0f, false, false);
		new ComponentCollision(this);
		new ComponentRemoveFade(this);
	}
}

