package com.turpgames.maze.controller;

import com.turpgames.maze.controller.base.AbstractState;
import com.turpgames.maze.model.Level;
import com.turpgames.maze.view.MazeScreen;

public abstract class MazeState extends AbstractState {
	final Level model;
	final MazeScreen view;
	final MazeController controller;
	
	public MazeState(MazeController controller) {
		this.controller = controller;
		this.model = controller.model;
		this.view = controller.view;
	}
	
	@Override
	public void draw() {
		model.draw();
	}
}
