package com.turpgames.maze.display;

import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Utils;

public abstract class SlideBrowserObject extends GameObject {

	@Override
	public void registerSelf() {
		Game.getInputManager().register(this, Utils.LAYER_SCREEN);
	}
}
