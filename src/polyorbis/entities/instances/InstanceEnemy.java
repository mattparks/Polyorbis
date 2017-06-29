package polyorbis.entities.instances;

import flounder.devices.*;
import flounder.entities.*;
import flounder.entities.components.*;
import flounder.lights.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
import flounder.models.*;
import flounder.resources.*;
import flounder.sounds.*;
import flounder.space.*;
import flounder.textures.*;
import polyorbis.entities.components.*;

public class InstanceEnemy extends Entity {
	private static final ModelObject MODEL = ModelFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "enemy", "model.obj")).create();
	private static final TextureObject TEXTURE = TextureFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "enemy", "diffuse.png")).setNumberOfRows(1).create();
	private static final TextureObject TEXTURE_GLOW = TextureFactory.newBuilder().setFile(new MyFile(FlounderEntities.ENTITIES_FOLDER, "enemy", "glow.png")).setNumberOfRows(1).create();
	protected final static Sound SOUND_DAMAGE = Sound.loadSoundInBackground(new MyFile(FlounderSound.SOUND_FOLDER, "damage.wav"), 0.8f, 1.0f);

	public InstanceEnemy(ISpatialStructure<Entity> structure, Vector3f rotation, float radius) {
		super(structure, Vector3f.rotate(new Vector3f(0.0f, radius, 0.0f), rotation, null), new Vector3f(0.0f, rotation.y, rotation.z));
		new ComponentEnemy(this, rotation, radius, 0.9f);
		new ComponentCollect(this, SOUND_DAMAGE, pc -> {
			pc.modifyHealth(-0.2f);
			pc.addKill();
		});
		new ComponentModel(this, 0.2f, MODEL, TEXTURE, 1);
		new ComponentSurface(this, 1.0f, 0.0f, false, false, true);
		new ComponentLight(this, new Vector3f(0.0f, 0.0f, 0.0f), new Colour(0.812f, 0.243f, 0.131f), new Attenuation(1.0f, 0.02f, 2.0f));
		new ComponentGlow(this, TEXTURE_GLOW);
		new ComponentCollision(this);
		new ComponentRemoveFade(this);
	}
}

