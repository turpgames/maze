package com.turpgames.maze.controller.base;

public abstract class AbstractState implements IMazeController {

	@Override
	public void onScreenActivated() {

	}

	@Override
	public boolean onScreenDeactivated() {
		return false;
	}

	@Override
	public void draw() {
		
	}

	@Override
	public boolean touchDragged(float x, float y, int pointer) {
		return false;
	}

	@Override
	public boolean touchUp(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public void work() {
		
	}

}
