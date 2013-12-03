package com.turpgames.maze.controller;

import com.turpgames.maze.controller.Controller;

public class WaitingState extends State {
	public WaitingState(Controller parent) {
		super(parent);
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		controller.beginUserRotating(x, y);
		return false;
	}
}
