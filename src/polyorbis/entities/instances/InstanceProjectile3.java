package polyorbis.entities.instances;

import flounder.entities.*;
import flounder.entities.components.*;
import flounder.maths.vectors.*;
import flounder.models.*;
import flounder.particles.*;
import flounder.particles.spawns.*;
import flounder.resources.*;
import flounder.space.*;
import flounder.textures.*;
import polyorbis.entities.components.*;

import java.util.*;

public class InstanceProjectile3 extends Entity {
	private static final ModelObject MODEL = ModelFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "projectile3", "model.obj")).create();
	private static final TextureObject TEXTURE = TextureFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "projectile3", "diffuse.png")).setNumberOfRows(1).create();
	private static final TextureObject TEXTURE_GLOW = TextureFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "projectile3", "glow.png")).setNumberOfRows(1).create();
	private static final ParticleType[] TEMPLATES = new ParticleType[]{
			new ParticleType("red", TextureFactory.newBuilder().setFile(new MyFile(FlounderParticles.PARTICLES_FOLDER, "redParticle.png")).setNumberOfRows(4).create(), 0.8f, 0.10f)
	};

	public InstanceProjectile3(ISpatialStructure<Entity> structure, Vector3f rotation, float radius, Vector3f direction, boolean playerSpawned) {
		super(structure, Vector3f.rotate(new Vector3f(0.0f, radius, 0.0f), rotation, null), new Vector3f(0.0f, rotation.y, rotation.z));
		new ComponentProjectile(this, rotation, radius, -1.3f, direction.scale(0.1f), 0.6f, 0.4f, playerSpawned);
		new ComponentRotate(this, rotation, radius, new Vector3f(0.3f, 1.0f, 1.0f), 0.8f);
		new ComponentModel(this, 0.1f, MODEL, TEXTURE, 1);
		new ComponentGlow(this, TEXTURE_GLOW);
		new ComponentSurface(this, 1.0f, 0.0f, false, false, true);
		new ComponentCollision(this);
		new ComponentParticles(this, Arrays.asList(TEMPLATES), new SpawnPoint(), new Vector3f(), 5.0f, 0.5f, 0.0f);
		new ComponentRemoveFade(this);
	}
}

