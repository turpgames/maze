package com.turpgames.maze.controller.base;

import com.turpgames.maze.model.IMazeLevelListener;
import com.turpgames.maze.view.IMazeViewListener;

public interface IMazeController extends IMazeViewListener, IMazeLevelListener {
	boolean touchDragged(float x, float y, int pointer);
	
	boolean touchUp(float x, float y, int pointer, int button);
	
	boolean touchDown(float x, float y, int pointer, int button);
	
	void work();
}
