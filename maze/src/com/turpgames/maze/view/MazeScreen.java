package com.turpgames.maze.view;

import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.util.Game;

public class MazeScreen extends Screen {
	protected IMazeViewListener screenListener = IMazeViewListener.NULL;

	protected void notifyScreenActivated() {
		registerDrawable(screenListener, Game.LAYER_SCREEN);
		screenListener.onScreenActivated();
	}

	protected boolean notifyScreenDeactivated() {
		if (screenListener.onScreenDeactivated()) {
			unregisterDrawable(screenListener);
			return true;
		}
		return false;
	}

	protected void setScreenListener(IMazeViewListener listener) {
		this.screenListener = listener;
	}

	@Override
	protected void onAfterActivate() {
		notifyScreenActivated();
	}
	
	@Override
	protected boolean onBeforeDeactivate() {
		return notifyScreenDeactivated();
	}

	@Override
	public void init() {
		super.init();

		registerInputListener(this);
	}
}