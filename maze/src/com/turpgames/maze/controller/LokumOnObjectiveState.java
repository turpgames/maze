package com.turpgames.maze.controller;

import com.turpgames.framework.v0.util.Animation;

public class LokumOnObjectiveState extends State {
	public LokumOnObjectiveState(Controller parent) {
		super(parent);
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		controller.finishMap();
	}
}
