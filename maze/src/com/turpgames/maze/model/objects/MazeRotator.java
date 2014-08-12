package com.turpgames.maze.model.objects;

import com.turpgames.framework.v0.impl.InputListener;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.maze.view.IScreenView;

public class MazeRotator {
	private final static float maxTemp = 35;
	private final static float minTemp = 15;

	private final Maze maze;
	private boolean userRotating;
	private boolean mazeRotating;
	private float userTempRotation;
	private float mazeOldRotation;
	private float mazeTempRotation;
	private float rotateStartX, rotateStartY;

	private float mazeRotateSpeed;
	private float mazeTargetRotation;
	private final Timer rotateTimer;
	private float lastRemaining;

	public MazeRotator(Maze maze) {
		this.maze = maze;

		rotateTimer = new Timer();
		rotateTimer.setInterval(0.001f);
		rotateTimer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				rotate();
			}
		});
	}

	public boolean isRotating() {
		return userRotating || mazeRotating;
	}

	private float limitAngle(float angle) {
		if (angle > maxTemp)
			angle = maxTemp;
		else if (angle < -maxTemp)
			angle = -maxTemp;
		return angle;
	}

	private void rotate() {
		float dr = Game.getDeltaTime() * mazeRotateSpeed;
		mazeTempRotation += dr;

		float newRotation = mazeTempRotation + mazeOldRotation + userTempRotation;

		float remaining = Math.abs(maze.getRotation() - mazeTargetRotation);

		if (remaining > lastRemaining) {
			newRotation = mazeTargetRotation;
			mazeRotating = false;
			rotateTimer.stop();
		} else {
			lastRemaining = remaining;
		}

		maze.setRotation(newRotation);
	}

	private boolean onTouchDown(float x, float y) {
		userRotating = true;
		rotateStartX = x;
		rotateStartY = y;
		userTempRotation = 0;
		mazeOldRotation = maze.getRotation();
		return false;
	}

	private boolean onTouchDragged(float x, float y) {
		if (!userRotating) {
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

		maze.setRotation(mazeOldRotation + userTempRotation);

		return false;
	}

	private boolean onTouchUp(float x, float y) {
		if (!userRotating) {
			return false;
		}
		userRotating = false;

		if (Math.abs(userTempRotation) < minTemp) {
			mazeTargetRotation = mazeOldRotation;
			mazeRotateSpeed = -userTempRotation / 0.2f;
			lastRemaining = mazeTargetRotation - maze.getRotation();
		} else {
			if (userTempRotation > 0)
				mazeTargetRotation = mazeOldRotation + 90;
			else
				mazeTargetRotation = mazeOldRotation - 90;
			lastRemaining = mazeTargetRotation - maze.getRotation();
			mazeRotateSpeed = lastRemaining / 0.5f;
		}

		lastRemaining = Math.abs(lastRemaining);

		mazeTempRotation = 0;
		mazeRotating = true;
		rotateTimer.start();

		return false;
	}

	public void register(IScreenView view) {
		view.registerInputListener(listener);
	}

	public void unregister(IScreenView view) {
		view.unregisterInputListener(listener);
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
