package com.turpgames.maze.controller.level;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.maze.controller.level.Controller;
import com.turpgames.maze.controller.level.LevelState;

public class MazeRotatingState extends LevelState {
	public MazeRotatingState(Controller parent) {
		super(parent);
	}

	private float increment;
	private float rotationSpeed = 120; // degrees per second

	@Override
	public void work() {
		increment = rotationSpeed * Game.getDeltaTime();
		controller.mazeRotate(increment);
	}
}
