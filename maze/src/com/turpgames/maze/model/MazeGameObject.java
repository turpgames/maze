package com.turpgames.maze.model;

import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Rotation;

public abstract class MazeGameObject extends GameObject {

	public void anchorRotation(Rotation rotation) {
		getRotation().angle = rotation.angle;
		getRotation().origin = rotation.origin;
	}
}
