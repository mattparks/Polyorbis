package com.polyorbis.entities.instances;

import com.flounder.entities.*;
import com.flounder.entities.components.*;
import com.flounder.maths.vectors.*;
import com.flounder.models.*;
import com.flounder.resources.*;
import com.flounder.space.*;
import com.flounder.textures.*;

public class InstancePine extends Entity {
	private static final ModelObject MODEL = ModelFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "pine", "model.obj")).create();
	private static final TextureObject TEXTURE = TextureFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "pine", "diffuse.png")).setNumberOfRows(1).create();
	private static final TextureObject TEXTURE_SWAY = TextureFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "pine", "sway.png")).setNumberOfRows(1).create();

	public InstancePine(ISpatialStructure<Entity> structure, Vector3f rotation, float radius) {
		super(structure, Vector3f.rotate(new Vector3f(0.0f, radius, 0.0f), rotation, null), new Vector3f(0.0f, rotation.y, rotation.z));
		new ComponentModel(this, 0.2f, MODEL, TEXTURE, 1);
		new ComponentSurface(this, 1.0f, 0.0f, false, false, true);
		new ComponentSway(this, TEXTURE_SWAY);
		new ComponentCollision(this);
	}
}

