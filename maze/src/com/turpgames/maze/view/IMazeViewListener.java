package com.turpgames.maze.view;

import com.turpgames.framework.v0.IDrawable;

public interface IMazeViewListener extends IDrawable {
	public static final IMazeViewListener NULL = new IMazeViewListener() {
		@Override
		public boolean onScreenDeactivated() {
			return true;
		}

		@Override
		public void onScreenActivated() {

		}

		@Override
		public void draw() {

		}
	};

	void onScreenActivated();

	boolean onScreenDeactivated();
}
