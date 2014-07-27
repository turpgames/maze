package com.turpgames.maze.level;

import com.turpgames.framework.v0.impl.InputListener;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.maze.utils.Maze;
import com.turpgames.maze.view.IScreenView;

public class Mechanic {

	/**
	 * Values -1 or 1 for maze rotation direction (Right, Left).
	 */
	private int userRotation;
	/**
	 * Angle of rotation that completes user rotation to 90 degrees.
	 */
	private float mazeTempRotation;

	private float epsilon = 0.1f;

	private float rotateStartX, rotateStartY; // used to keep track of
	// userRotation

	private float userTempRotation;
	private Level level;
	private IScreenView view;
	
	public Mechanic(Level level, IScreenView view) {
		this.level = level;
		this.view = view;
	}
	
	public void activate() {
		this.view.registerInputListener(listener);
	}
	
	public void deactivate() {
		this.view.unregisterInputListener(listener);
	}
	
	private boolean userRotating;
	private float mazeOldRotation; // Keeps the rotation before the user input starts.
	public boolean onTouchDown(float x, float y) {
		// controller.beginUserRotating(x, y);
		mazeOldRotation = level.getRotationAngle();
		this.rotateStartX = x;
		this.rotateStartY = y;
		this.userTempRotation = 0;
//		setCurrState(userRotating);
		userRotating = true;
		return false;
	}

	public boolean onTouchDragged(float x, float y) {
		if(!userRotating) {
			return false;
		}
		float sw = Game.getScreenWidth();
		float sh = Game.getScreenHeight();

		float dx = ((x - rotateStartX) / sw) * 90;
		float dy = ((y - rotateStartY) / sh) * 90;

		if (y > sh / 2)
			dx = -dx;
		if (x < sw / 2)
			dy = -dy;

		userTempRotation = limitAngle(userTempRotation + dx + dy);

		rotateStartX = x;
		rotateStartY = y;

		// controller.userRotated(userTempRotation);
		level.setBodyRotation(mazeOldRotation + userTempRotation);
		return false;

	}

	private final static float maxTemp = 35;
	private final static float minTemp = 15;
	private float limitAngle(float angle) {
		if (angle > maxTemp)
			angle = maxTemp;
		else if (angle < -maxTemp)
			angle = -maxTemp;
		return angle;
	}

	public boolean onTouchUp(float x, float y) {
		if (!userRotating) {
			return false;
		}
		level.setSignRotation(Maze.R_NONE);

		if (userTempRotation > minTemp)
			userRotation = 1;
		else if (userTempRotation < -minTemp)
			userRotation = -1;
		else {
			userTempRotation = 0;
			// controller.userAbortRotation();
			level.setBodyRotation(mazeOldRotation);
//			setCurrState(waiting);

			return false;
		}

		// controller.startMazeRotate(userRotation, userTempRotation);
		mazeTempRotation = 90 - userRotation * userTempRotation;
//		setCurrState(mazeRotating);
		mazeRotating = true;
		userRotating = false;
		return false;
	}
	
	private float increment;
	private float rotationSpeed = 120; // degrees per second
	private boolean mazeRotating;
	public void update() {
		if (mazeRotating) {
			increment = rotationSpeed * Game.getDeltaTime();
			// controller.mazeRotate(increment);
			level.addBodyRotation(userRotation * increment);
			mazeTempRotation -= increment; // Keep track of remaining rotation.
			if (mazeTempRotation <= 0 + epsilon) { // MAZE_ROTATE FINISHED
				level.setBodyRotation(mazeOldRotation + userRotation * 90);
//				MazeMover.instance.turn(userRotation == 1);

//				setCurrState(lokumFalling);
				mazeRotating = false;
			}
		}
	}
	
	private final InputListener listener = new InputListener() {
		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			if (mazeRotating) {
				return false;
			}
			return onTouchDown(Game.screenToViewportX(x), Game.screenToViewportY(y));
		}
		
		@Override
		public boolean touchDragged(float x, float y, int pointer) {
			if (mazeRotating) {
				return false;
			}
			return onTouchDragged(Game.screenToViewportX(x), Game.screenToViewportY(y));
		}
		
		@Override
		public boolean touchUp(float x, float y, int pointer, int button) {
			if (mazeRotating) {
				return false;
			}
			return onTouchUp(Game.screenToViewportX(x), Game.screenToViewportY(y));
		}
	};
}
