package com.turpgames.maze.model;

import com.turpgames.framework.v0.impl.AnimatedGameObject;
import com.turpgames.framework.v0.util.Rotation;

public class MazeAnimatedGameObject extends AnimatedGameObject {

	public void anchorRotation(Rotation rotation) {
		getRotation().angle = rotation.angle;
		getRotation().origin = rotation.origin;
	}

	@Override
	public void registerSelf() {
		
	}
}
