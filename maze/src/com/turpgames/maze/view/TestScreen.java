package com.turpgames.maze.view;

import com.turpgames.framework.v0.IResourceManager;
import com.turpgames.framework.v0.util.Drawer;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.maze.controller.level.Controller;
import com.turpgames.maze.display.SlideBrowser;
import com.turpgames.maze.model.Objective;
import com.turpgames.maze.model.Trap;
import com.turpgames.maze.model.Wall;

public class TestScreen extends MazeScreen {
//	Controller controller;
	
	@Override
	public void init() {
		super.init();
		IResourceManager resourceManager = Game.getResourceManager();
		
		while(resourceManager.isLoading()) {}
		
//		controller = new Controller(this);
//		setScreenListener(controller);
		
		SlideBrowser slideBrowser = new SlideBrowser();
		slideBrowser.registerObject(new Wall(Game.getVirtualWidth()/2, Game.getVirtualHeight()/2));
		slideBrowser.registerObject(new Trap(Game.getVirtualWidth()/2, Game.getVirtualHeight()/2));
		slideBrowser.registerObject(new Objective(Game.getVirtualWidth()/2, Game.getVirtualHeight()/2));
		registerDrawable(slideBrowser, Utils.LAYER_GAME);
	}
	
	@Override
	public void update() {
//		controller.work();
		super.update();
	}
}
