package com.turpgames.maze.controller;

import com.turpgames.framework.v0.util.Animation;

public class LokumOnTrapState extends State {

	public LokumOnTrapState(Controller parent) {
		super(parent);
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		controller.resetMap();
	}
}
