package com.turpgames.maze.controller;

import com.turpgames.maze.controller.MazeController;

public class MazeWaitingState extends MazeState {
	public MazeWaitingState(MazeController parent) {
		super(parent);
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		controller.beginUserRotating(x, y);
		return false;
	}
}
