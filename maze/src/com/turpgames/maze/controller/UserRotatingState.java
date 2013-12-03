package com.turpgames.maze.controller;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.maze.controller.Controller;
import com.turpgames.maze.utils.Maze;

public class UserRotatingState extends State {
	private final static float maxTemp = 35;
	private final static float minTemp = 15;

	public UserRotatingState(Controller parent) {
		super(parent);
		
	}

	private float rotateStartX, rotateStartY; // used to keep track of
												// userRotation

	private float userTempRotation; // draw temp grayscale with this angle
	private int userRotation; // init -1 or 1 for maze rotation. add to
								// mazeOldRotation and set rotation when
								// animation is finished

	@Override
	public void draw() {
		if (userTempRotation == 0)
			model.setSignRotation(Maze.R_NONE);
		
		if (Math.abs(userTempRotation) < Math.abs(minTemp)) {
			model.setSignRotation(Maze.R_TEMP);
		}
		else if (userTempRotation > minTemp) {
			//userRotation = 1;
			model.setSignRotation(Maze.R_LEFT);
		}
		else if (userTempRotation < -minTemp) {
//			userRotation = -1;
			model.setSignRotation(Maze.R_RIGHT);
		}
		
		model.draw();
	}
	
	@Override
	public boolean touchDragged(float x, float y, int pointer) {		
		
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

		controller.userRotated(userTempRotation);
		return false;

	}

	private float limitAngle(float angle) {
		if (angle > maxTemp)
			angle = maxTemp;
		else if (angle < -maxTemp)
			angle = -maxTemp;
		return angle;
	}

	@Override
	public boolean touchUp(float x, float y, int pointer, int button) {
		model.setSignRotation(Maze.R_NONE);
		
		if (userTempRotation > minTemp)
			userRotation = 1;
		else if (userTempRotation < -minTemp)
			userRotation = -1;
		else {
			userTempRotation = 0;
			controller.userAbortRotation();
			return false;
		}
		
		controller.startMazeRotate(userRotation, userTempRotation);
		return false;
	}

	public void setStarts(float rotateStartX, float rotateStartY) {
		this.rotateStartX = rotateStartX;
		this.rotateStartY = rotateStartY;
		this.userTempRotation = 0;
	}
}
