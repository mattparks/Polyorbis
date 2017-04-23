package polyorbis.entities.components;

import flounder.entities.*;
import flounder.framework.*;
import flounder.guis.*;
import flounder.helpers.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
import polyorbis.world.*;

import javax.swing.*;

public class ComponentCloud extends IComponentEntity implements IComponentEditor {
	private Vector3f rotation;
	private float radius;

	private Vector3f direction;
	private float deviation;
	private float randomness;

	/**
	 * Creates a new ComponentCloud.
	 *
	 * @param entity The entity this component is attached to.
	 */
	public ComponentCloud(Entity entity) {
		super(entity);
	}

	public ComponentCloud(Entity entity, Vector3f rotation, float radius, Vector3f direction, float deviation) {
		super(entity);

		this.rotation = rotation;
		this.radius = radius;

		this.direction = direction.scale(Maths.randomInRange(0.8f, 1.2f));
		this.deviation = deviation;
		this.randomness = Maths.randomInRange(0.0f, 10.0f);
	}

	@Override
	public void update() {
		// Do not update on paused.
		if (FlounderGuis.getGuiMaster() == null || FlounderGuis.getGuiMaster().isGamePaused()) {
			return;
		}

		Vector3f right = new Vector3f(direction).scale(360.0f * Framework.getDelta());
		right.y -= deviation * Math.cos(Math.PI * randomness * Framework.getTimeSec());
		right.z += deviation * Math.sin(Math.PI * randomness * Framework.getTimeSec());
		Vector3f.add(rotation, right, rotation);
		Vector3f.rotate(new Vector3f(0.0f, radius, 0.0f), rotation, getEntity().getPosition());
		getEntity().setMoved();
	}

	@Override
	public void addToPanel(JPanel panel) {
	}

	@Override
	public void editorUpdate() {
	}

	@Override
	public Pair<String[], String[]> getSaveValues(String entityName) {
		return new Pair<>(
				new String[]{}, // Static variables
				new String[]{} // Class constructor
		);
	}

	@Override
	public void dispose() {
	}
}
