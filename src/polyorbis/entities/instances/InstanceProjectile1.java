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
			new ParticleType("purple", TextureFactory.newBuilder().setFile(new MyFile(FlounderParticles.PARTICLES_FOLDER, "purpleParticle.png")).setNumberOfRows(4).create(), 1.2f, 0.10f),
			new ParticleType("green", TextureFactory.newBuilder().setFile(new MyFile(FlounderParticles.PARTICLES_FOLDER, "greenParticle.png")).setNumberOfRows(4).create(), 1.2f, 0.10f),
			new ParticleType("blue", TextureFactory.newBuilder().setFile(new MyFile(FlounderParticles.PARTICLES_FOLDER, "blueParticle.png")).setNumberOfRows(4).create(), 1.2f, 0.10f)
	};

	public InstanceProjectile1(ISpatialStructure<Entity> structure, Vector3f rotation, float radius, Vector3f direction) {
		super(structure, Vector3f.rotate(new Vector3f(0.0f, radius, 0.0f), rotation, null), new Vector3f(0.0f, rotation.y, rotation.z));
		//	new ComponentCollect(this, pc -> pc.modifyHealth(-0.3f));
		new ComponentProjectile(this, rotation, radius, -0.7f, direction.normalize().scale(0.3f), 3.0f);
		new ComponentRotate(this, new Vector3f(0.3f, 1.0f, 1.0f), 0.8f);
		new ComponentModel(this, 0.18f, MODEL, TEXTURE, 1);
		new ComponentGlow(this, TEXTURE_GLOW);
		new ComponentSurface(this, 1.0f, 0.0f, false, false);
		new ComponentLight(this, new Vector3f(0.0f, 0.0f, 0.0f), new Colour(0.243f, 0.131f, 0.812f), new Attenuation(1.0f, 0.02f, 2.0f));
		new ComponentCollision(this);
		new ComponentParticles(this, Arrays.asList(TEMPLATES), new SpawnPoint(), new Vector3f(), 2.0f, 0.5f, 0.0f);
	}
}

