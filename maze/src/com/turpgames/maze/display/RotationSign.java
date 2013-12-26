package com.turpgames.maze.display;

import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.maze.utils.Maze;
import com.turpgames.maze.utils.R;

public class RotationSign extends GameObject {

	public int direction;
	
	public RotationSign(float x, float y) {
		getLocation().x = x;
		getLocation().y = y;
		setWidth(R.ui.rotationSignWidth);
		setHeight(R.ui.rotationSignHeight);
	}

	@Override
	public void draw() {
		Maze.drawRotationSign(this, direction);
	}
}
