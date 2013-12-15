package com.turpgames.maze.view;

import com.turpgames.framework.v0.IResourceManager;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.maze.controller.level.Controller;

public class MazeTestScreen extends MazeScreen {
	Controller controller;
	
	@Override
	public void init() {
		super.init();
		IResourceManager resourceManager = Game.getResourceManager();
		
		while(resourceManager.isLoading()) {}
		
		controller = new Controller(this);
		setScreenListener(controller);
	}
	
	@Override
	public void update() {
		controller.work();
		super.update();
	}
}
