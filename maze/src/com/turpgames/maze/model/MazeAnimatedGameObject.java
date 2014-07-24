package com.turpgames.maze.model;

import com.turpgames.framework.v0.impl.AnimatedGameObject;
import com.turpgames.framework.v0.util.Rotation;

public class MazeAnimatedGameObject extends AnimatedGameObject {

	public void anchorRotation(Rotation rotation) {
		getRotation().angle = rotation.angle;
		getRotation().origin = rotation.origin;
	}

//	public void deanchorRotation() {
//		getRotation().angle = new Vector(0, 0);
//		getRotation().origin = new Vector(getLocation().x + getWidth() / 2, getLocation().y + getHeight() / 2);
//	}
}
