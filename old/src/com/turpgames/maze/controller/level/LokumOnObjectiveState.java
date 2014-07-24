package com.turpgames.maze.controller.level;

import com.turpgames.framework.v0.util.Animation;

public class LokumOnObjectiveState extends LevelState {
	public LokumOnObjectiveState(Controller parent) {
		super(parent);
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		controller.objectiveReached();
	}
}
