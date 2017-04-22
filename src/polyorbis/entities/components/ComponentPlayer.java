package polyorbis.entities.components;

import flounder.entities.*;
import flounder.framework.*;
import flounder.helpers.*;
import flounder.inputs.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
import polyorbis.world.*;

import javax.swing.*;

import static org.lwjgl.glfw.GLFW.*;

public class ComponentPlayer extends IComponentEntity implements IComponentEditor {
	private static final float PLAYER_HEIGHT = 0.15f;
	private static final float PLAYER_ACCELERATION = 10.0f;
	private static final float PLAYER_SPEED = 1.2f;
	private static final float PLAYER_JUMP = 0.12f;
	private static final float PLAYER_GRAVITY = -0.5f;

	private float currentY;
	private float currentZ;
	private float currentRadius;

	private float currentSpeedY;
	private float currentSpeedZ;
	private float currentSpeedUp;

	private IAxis inputY;
	private IAxis inputZ;
	private IButton inputJump;

	/**
	 * Creates a new ComponentPlayer.
	 *
	 * @param entity The entity this component is attached to.
	 */
	public ComponentPlayer(Entity entity) {
		super(entity);

		IButton leftKeyButtons = new KeyButton(GLFW_KEY_A, GLFW_KEY_LEFT);
		IButton rightKeyButtons = new KeyButton(GLFW_KEY_D, GLFW_KEY_RIGHT);
		IButton upKeyButtons = new KeyButton(GLFW_KEY_W, GLFW_KEY_UP);
		IButton downKeyButtons = new KeyButton(GLFW_KEY_S, GLFW_KEY_DOWN);
		IButton jumpButtons = new KeyButton(GLFW_KEY_SPACE);

		this.currentY = 0.0f;
		this.currentZ = 0.0f;

		this.currentRadius = PLAYER_HEIGHT;
		this.currentSpeedY = 0.0f;
		this.currentSpeedZ = 0.0f;
		this.currentSpeedUp = 0.0f;

		this.inputY = new CompoundAxis(new ButtonAxis(leftKeyButtons, rightKeyButtons), new JoystickAxis(0, 0));
		this.inputZ = new CompoundAxis(new ButtonAxis(downKeyButtons, upKeyButtons), new JoystickAxis(0, 1));
		this.inputJump = new CompoundButton(jumpButtons, new JoystickButton(0, 0));
	}

	@Override
	public void update() {
		float planetRadius = PolyWorld.getEntityPlanet() == null ? 0.0f : PolyWorld.getEntityPlanet().getScale();

		if (inputY.getAmount() == 0.0f) {
			currentSpeedY += (currentSpeedY <= 0.0f ? 1.0f : -1.0f) * Math.abs(currentSpeedY) * PLAYER_ACCELERATION * Framework.getDelta();
		} else {
			currentSpeedY += inputY.getAmount() * PLAYER_SPEED * PLAYER_ACCELERATION * Framework.getDelta();
			currentSpeedY = Maths.clamp(currentSpeedY, -PLAYER_SPEED, PLAYER_SPEED);
		}

		if (inputZ.getAmount() == 0.0f) {
			currentSpeedZ += (currentSpeedZ <= 0.0f ? 1.0f : -1.0f) * Math.abs(currentSpeedZ) * PLAYER_ACCELERATION * Framework.getDelta();
		} else {
			currentSpeedZ += -inputZ.getAmount() * PLAYER_SPEED * PLAYER_ACCELERATION * Framework.getDelta();
			currentSpeedZ = Maths.clamp(currentSpeedZ, -PLAYER_SPEED, PLAYER_SPEED);
		}

		currentY += currentSpeedY;
		currentZ += currentSpeedZ;

		currentSpeedUp = (inputJump.isDown() && currentSpeedUp == 0.0f && currentRadius <= planetRadius + PLAYER_HEIGHT) ? PLAYER_JUMP : currentSpeedUp;
		currentSpeedUp += PLAYER_GRAVITY * Framework.getDelta();
		currentRadius += currentSpeedUp;

		if (currentRadius < planetRadius + PLAYER_HEIGHT) {
			currentRadius = planetRadius + PLAYER_HEIGHT;
			currentSpeedUp = 0.0f;
		}

		// Normalizes angles and limits Y rotation to make movement easier.
		currentY = Maths.normalizeAngle(currentY);
		currentZ = Maths.clamp(currentZ, 2.0f, 178.0f);

		// The wacky rotation effect on run.
		float as = Math.min((Math.abs(currentSpeedY) + Math.abs(currentSpeedZ)) / PLAYER_SPEED, 1.0f) + Math.min(9.0f * Math.abs(currentSpeedUp), 1.0f);
		float rx = as * 15.0f * (float) (Math.sin(0.25 * 15.0f * Framework.getTimeSec()) - Math.sin(1.2 * 15.0f * Framework.getTimeSec()) + Math.cos(0.5 * 15.0f * Framework.getTimeSec()));
		float rz = as * 15.0f * (float) (Math.cos(0.25 * 15.0f * Framework.getTimeSec()) - Math.cos(1.2 * 15.0f * Framework.getTimeSec()) + Math.sin(0.5 * 15.0f * Framework.getTimeSec()));

		Vector3f.rotate(new Vector3f(0.0f, currentRadius, 0.0f), new Vector3f(0.0f, currentY, currentZ), getEntity().getPosition());
		getEntity().getRotation().set(rx, currentY, currentZ + rz);
		getEntity().setMoved();

	//	Vector3f dd = Vector3f.rotate(new Vector3f(0.0f, currentRadius, 0.0f), new Vector3f(0.0f, currentY, currentZ), null);
	//	Vector3f.subtract(dd, getEntity().getPosition(), dd);
	//	Vector3f dr = new Vector3f(rx, currentY, currentZ + rz);
	//	Vector3f.subtract(dr, getEntity().getRotation(), dr);
	//	getEntity().move(dd, dr);
	//	getEntity().setMoved();
	}

	public float getCurrentY() {
		return currentY;
	}

	public float getCurrentZ() {
		return currentZ;
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
