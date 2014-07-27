package com.turpgames.maze.display.slider;

import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Game;

public abstract class SlideBrowserObject extends GameObject {

	@Override
	public void registerSelf() {
		Game.getInputManager().register(this, Game.LAYER_SCREEN);
	}
}
