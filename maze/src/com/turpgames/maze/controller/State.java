package com.turpgames.maze.controller;

import com.turpgames.framework.v0.IAnimationEndListener;
import com.turpgames.framework.v0.ICollisionListener;
import com.turpgames.framework.v0.util.Animation;
import com.turpgames.framework.v0.util.CollisionEvent;
import com.turpgames.maze.controller.base.AbstractState;
import com.turpgames.maze.model.Level;
import com.turpgames.maze.view.MazeScreen;

public abstract class State extends AbstractState implements ICollisionListener, IAnimationEndListener {
	final Level model;
	final MazeScreen view;
	final Controller controller;
	
	public State(Controller controller) {
		this.controller = controller;
		this.model = controller.model;
		this.view = controller.view;
	}
	
	@Override
	public void draw() {
		model.draw();
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		
	}

	@Override
	public void onCollide(CollisionEvent event) {
		
	}

	@Override
	public void onNotCollide(CollisionEvent event) {
		
	}
}
