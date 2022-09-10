package com.polyorbis.entities.instances;

import com.polyorbis.entities.components.*;

import com.flounder.entities.*;
import com.flounder.entities.components.*;
import com.flounder.maths.vectors.*;
import com.flounder.models.*;
import com.flounder.resources.*;
import com.flounder.space.*;
import com.flounder.textures.*;

public class InstanceRing extends Entity {
	private static final ModelObject MODEL = ModelFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "ring", "model.obj")).create();
	private static final TextureObject TEXTURE = TextureFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "ring", "diffuse.png")).setNumberOfRows(1).create();

	public InstanceRing(ISpatialStructure<Entity> structure, Vector3f position, Vector3f rotation) {
		super(structure, position, rotation);
		new ComponentRotate(this, rotation, 1.0f, new Vector3f(1.0f, 0.0f, 0.0f), 0.01f);
		new ComponentModel(this, 15.0f, MODEL, TEXTURE, 1);
		new ComponentSurface(this, 1.0f, 0.0f, false, false, true);
	}
}

