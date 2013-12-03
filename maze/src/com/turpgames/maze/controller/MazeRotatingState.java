package com.turpgames.maze.controller;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.maze.controller.Controller;
import com.turpgames.maze.controller.State;

public class MazeRotatingState extends State {
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
