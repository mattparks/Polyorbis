package com.polyorbis.entities.instances;

import com.polyorbis.entities.components.*;

import com.flounder.entities.*;
import com.flounder.entities.components.*;
import com.flounder.maths.vectors.*;
import com.flounder.models.*;
import com.flounder.resources.*;
import com.flounder.space.*;
import com.flounder.textures.*;

public class InstanceSpawn extends Entity {
	private static final ModelObject MODEL = ModelFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "spawn", "model.obj")).create();
	private static final TextureObject TEXTURE = TextureFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "spawn", "diffuse.png")).setNumberOfRows(1).create();
	private static final TextureObject TEXTURE_GLOW = TextureFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "spawn", "glow.png")).setNumberOfRows(1).create();
	private static final TextureObject TEXTURE_SWAY = TextureFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "spawn", "sway.png")).setNumberOfRows(1).create();

	public InstanceSpawn(ISpatialStructure<Entity> structure, Vector3f rotation, float radius) {
		super(structure, Vector3f.rotate(new Vector3f(0.0f, radius, 0.0f), rotation, null), new Vector3f(0.0f, rotation.y, rotation.z));
		new ComponentSpawn(this, rotation, radius);
		new ComponentModel(this, 0.20f, MODEL, TEXTURE, 1);
		new ComponentSurface(this, 1.0f, 0.0f, false, false, true);
		new ComponentGlow(this, TEXTURE_GLOW);
		// new ComponentSway(this, TEXTURE_SWAY);
		//	new ComponentLight(this, new Vector3f(0.0f, 0.0f, 0.0f), new Colour(0.243f, 0.812f, 0.631f), new Attenuation(1.0f, 0.02f, 2.0f));
		new ComponentCollision(this);
	}
}

