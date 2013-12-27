package com.turpgames.maze.model;

import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Rotation;
import com.turpgames.framework.v0.util.Vector;

public abstract class MazeGameObject extends GameObject {

	public void anchorRotation(Rotation rotation) {
		getRotation().angle = rotation.angle;
		getRotation().origin = rotation.origin;
	}
	
	public void deanchorRotation() {
		getRotation().angle = new Vector(getRotation().angle.x, getRotation().angle.y, getRotation().angle.z);
		getRotation().origin = new Vector(getRotation().origin.x, getRotation().origin.y);
	}
}
