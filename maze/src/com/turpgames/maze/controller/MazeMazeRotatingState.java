package com.turpgames.maze.controller;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.maze.controller.MazeController;
import com.turpgames.maze.controller.MazeState;

public class MazeMazeRotatingState extends MazeState {
	public MazeMazeRotatingState(MazeController parent) {
		super(parent);
	}

	private float increment;
	private float rotationSpeed = 90; // degrees per second

	@Override
	public void work() {
		increment = rotationSpeed * Game.getDeltaTime();
		controller.mazeRotate(increment);
	}
}
