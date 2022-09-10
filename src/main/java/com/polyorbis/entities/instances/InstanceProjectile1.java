package com.polyorbis.entities.instances;

import com.flounder.entities.*;
import com.flounder.entities.components.*;
import com.flounder.lights.*;
import com.flounder.maths.*;
import com.flounder.maths.vectors.*;
import com.flounder.models.*;
import com.flounder.particles.*;
import com.flounder.particles.spawns.*;
import com.flounder.resources.*;
import com.flounder.space.*;
import com.flounder.textures.*;

import java.util.*;

import com.polyorbis.entities.components.*;

public class InstanceProjectile1 extends Entity {
	private static final ModelObject MODEL = ModelFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "projectile1", "model.obj")).create();
	private static final TextureObject TEXTURE = TextureFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "projectile1", "diffuse.png")).setNumberOfRows(1).create();
	private static final TextureObject TEXTURE_GLOW = TextureFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "projectile1", "glow.png")).setNumberOfRows(1).create();
	private static final ParticleType[] TEMPLATES = new ParticleType[]{
			new ParticleType("yellow", TextureFactory.newBuilder().setFile(new MyFile(FlounderParticles.PARTICLES_FOLDER, "yellowParticle.png")).setNumberOfRows(4).create(), 1.24f, 0.10f)
	};

	public InstanceProjectile1(ISpatialStructure<Entity> structure, Vector3f rotation, float radius, Vector3f direction, boolean playerSpawned) {
		super(structure, Vector3f.rotate(new Vector3f(0.0f, radius, 0.0f), rotation, null), new Vector3f(0.0f, rotation.y, rotation.z));
		new ComponentProjectile(this, rotation, radius, -0.7f, direction.scale(0.25f), 0.45f, 1.3f, playerSpawned);
		new ComponentRotate(this, rotation, radius, new Vector3f(0.3f, 1.0f, 1.0f), 0.7f);
		new ComponentModel(this, 0.1f, MODEL, TEXTURE, 1);
		new ComponentGlow(this, TEXTURE_GLOW);
		new ComponentSurface(this, 1.0f, 0.0f, false, false, true);
		new ComponentLight(this, new Vector3f(0.0f, 0.0f, 0.0f), new Colour(0.898f, 1.0f, 0.04f), new Attenuation(1.0f, 0.02f, 2.0f));
		new ComponentCollision(this);
		new ComponentParticles(this, Arrays.asList(TEMPLATES), new SpawnPoint(), new Vector3f(), 12.0f, 0.5f, 0.0f);
		new ComponentRemoveFade(this);
	}
}

