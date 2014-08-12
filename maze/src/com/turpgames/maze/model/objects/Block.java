package com.turpgames.maze.model.objects;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.TexturedGameObject;
import com.turpgames.maze.utils.Textures;

public class Block implements IDrawable {
	private final BlockObj blockObj;
	private final Maze maze;

	public Block(Maze maze, float x, float y, float size) {
		this.maze = maze;
		this.blockObj = new BlockObj();
		this.blockObj.setWidth(size);
		this.blockObj.setHeight(size);
		this.blockObj.getLocation().set(x, y);
		this.blockObj.getRotation().origin.set(maze.getCenter());
	}
	
	void syncWithMaze() {
		this.blockObj.getRotation().setRotationZ(maze.getRotation());
	}

	@Override
	public void draw() {
		blockObj.draw();
	}

	private class BlockObj extends TexturedGameObject {
		public BlockObj() {
			super(Textures.wall);
		}
	}
}
