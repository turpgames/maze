package com.turpgames.maze.controller.level;

import com.turpgames.maze.controller.level.Controller;

public class WaitingState extends LevelState {
	public WaitingState(Controller parent) {
		super(parent);
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		controller.beginUserRotating(x, y);
		return false;
	}
}
