package com.turpgames.maze.model;

import com.turpgames.framework.v0.ICollidable;
import com.turpgames.framework.v0.impl.RectangleBound;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.maze.utils.Maze;

public abstract class BlockObject extends MazeGameObject implements ICollidable {
	
	public static final int WALL = 0;
	public static final int OBJECTIVE = 1;
	public static final int TRAP = 2;
	
	protected int type;
	
	public BlockObject(int type, float x, float y) {
		this.type = type;
		getLocation().x = x;
		getLocation().y = y;
		setWidth(Level.blockWidth);
		setHeight(Level.blockHeight);
		addBound(new RectangleBound(this, new Vector(0, 0), Level.blockWidth, Level.blockHeight));
	}
	
	@Override
	public void draw() {
		Maze.drawBlock(this, type);
	}
}
