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

public class InstanceProjectile1 extends Entity {
	private static final ModelObject MODEL = ModelFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "projectile1", "model.obj")).create();
	private static final TextureObject TEXTURE = TextureFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "projectile1", "diffuse.png")).setNumberOfRows(1).create();
	private static final TextureObject TEXTURE_GLOW = TextureFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "projectile1", "glow.png")).setNumberOfRows(1).create();
	private static final ParticleType[] TEMPLATES = new ParticleType[]{
			new ParticleType("yellow", TextureFactory.newBuilder().setFile(new MyFile(FlounderParticles.PARTICLES_FOLDER, "yellowParticle.png")).setNumberOfRows(4).create(), 1.2f, 0.10f)
	};

	public InstanceProjectile1(ISpatialStructure<Entity> structure, Vector3f rotation, float radius, Vector3f direction, boolean playerSpawned) {
		super(structure, Vector3f.rotate(new Vector3f(0.0f, radius, 0.0f), rotation, null), new Vector3f(0.0f, rotation.y, rotation.z));

		if (!playerSpawned) {
			new ComponentCollect(this, pc -> pc.modifyHealth(-0.4f)); // Entity damage - 0.3.
		}

		new ComponentProjectile(this, rotation, radius, -0.7f, direction.normalize().scale(0.3f), 0.4f, 2.5f);
		new ComponentRotate(this, rotation, radius, new Vector3f(0.3f, 1.0f, 1.0f), 0.7f);
		new ComponentModel(this, 0.11f, MODEL, TEXTURE, 1);
		new ComponentGlow(this, TEXTURE_GLOW);
		new ComponentSurface(this, 1.0f, 0.0f, false, false);
		new ComponentLight(this, new Vector3f(0.0f, 0.0f, 0.0f), new Colour(0.898f, 1.0f, 0.04f), new Attenuation(1.0f, 0.02f, 2.0f));
		new ComponentCollision(this);
		new ComponentParticles(this, Arrays.asList(TEMPLATES), new SpawnPoint(), new Vector3f(), 12.0f, 0.5f, 0.0f);
		new ComponentRemoveFade(this);
	}
}

