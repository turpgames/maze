package com.turpgames.maze.model;

public interface IMazeLevelListener {
	boolean touchDragged(float x, float y, int pointer);
	
	boolean touchUp(float x, float y, int pointer, int button);
	
	boolean touchDown(float x, float y, int pointer, int button);
	
	void work();
}
