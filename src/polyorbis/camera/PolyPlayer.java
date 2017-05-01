package polyorbis.camera;

import flounder.camera.*;
import flounder.maths.vectors.*;
import polyorbis.entities.components.*;
import polyorbis.world.*;

public class PolyPlayer extends Player {
	private Vector3f position;
	private Vector3f rotation;

	public PolyPlayer() {
		super();
	}

	@Override
	public void init() {
		this.position = new Vector3f();
		this.rotation = new Vector3f();
	}

	@Override
	public void update() {
		if (PolyWorld.get().getEntityPlayer() != null) {
			this.position.set(PolyWorld.get().getEntityPlayer().getPosition());
			this.rotation.set(PolyWorld.get().getEntityPlayer().getRotation());

			ComponentPlayer player = (ComponentPlayer) PolyWorld.get().getEntityPlayer().getComponent(ComponentPlayer.class);

			if (player != null) {
				rotation.set(0.0f, player.getCurrentY(), player.getCurrentZ());
			}
		}
	}

	@Override
	public Vector3f getPosition() {
		return position;
	}

	@Override
	public Vector3f getRotation() {
		return rotation;
	}

	@Override
	public boolean isActive() {
		return true;
	}
}
