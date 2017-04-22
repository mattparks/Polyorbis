package polyorbis.camera;

import flounder.camera.*;
import flounder.maths.vectors.*;
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
		if (PolyWorld.getEntityPlayer() != null) {
			this.position.set(PolyWorld.getEntityPlayer().getPosition());
			this.rotation.set(PolyWorld.getEntityPlayer().getRotation());
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
