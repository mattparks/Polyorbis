package polyorbis.entities.instances;

import flounder.entities.*;
import flounder.entities.components.*;
import flounder.lights.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
import flounder.models.*;
import flounder.particles.*;
import flounder.particles.spawns.*;
import flounder.resources.*;
import flounder.space.*;
import flounder.textures.*;
import polyorbis.entities.components.*;

import java.util.*;

public class InstanceProjectile2 extends Entity {
	private static final ModelObject MODEL = ModelFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "projectile2", "model.obj")).create();
	private static final TextureObject TEXTURE = TextureFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "projectile2", "diffuse.png")).setNumberOfRows(1).create();
	private static final TextureObject TEXTURE_GLOW = TextureFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "projectile2", "glow.png")).setNumberOfRows(1).create();
	private static final ParticleType[] TEMPLATES = new ParticleType[]{
			new ParticleType("blue", TextureFactory.newBuilder().setFile(new MyFile(FlounderParticles.PARTICLES_FOLDER, "blueParticle.png")).setNumberOfRows(4).create(), 1.6f, 0.10f)
	};

	public InstanceProjectile2(ISpatialStructure<Entity> structure, Vector3f rotation, float radius, Vector3f direction, boolean playerSpawned) {
		super(structure, Vector3f.rotate(new Vector3f(0.0f, radius, 0.0f), rotation, null), new Vector3f(0.0f, rotation.y, rotation.z));

		if (!playerSpawned) {
			new ComponentCollect(this, pc -> pc.modifyHealth(-0.7f)); // Entity damage - 0.3.
		}

		new ComponentProjectile(this, rotation, radius, -1.0f, direction.normalize().scale(0.1f), 0.05f, 3.1f);
		new ComponentRotate(this, rotation, radius, new Vector3f(0.3f, 1.0f, 1.0f), 0.7f);
		new ComponentModel(this, 0.07f, MODEL, TEXTURE, 1);
		new ComponentGlow(this, TEXTURE_GLOW);
		new ComponentSurface(this, 1.0f, 0.0f, false, false);
		new ComponentLight(this, new Vector3f(0.0f, 0.0f, 0.0f), new Colour(0.0f, 0.0f, 0.898f), new Attenuation(1.0f, 0.02f, 2.0f));
		new ComponentCollision(this);
		new ComponentParticles(this, Arrays.asList(TEMPLATES), new SpawnPoint(), new Vector3f(), 10.0f, 0.5f, 0.0f);
		new ComponentRemoveFade(this);
	}
}

