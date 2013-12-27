package com.turpgames.maze.model.blocks;

import com.turpgames.framework.v0.ICollidable;
import com.turpgames.framework.v0.impl.RectangleBound;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.maze.model.MazeGameObject;
import com.turpgames.maze.utils.GameSettings;
import com.turpgames.maze.utils.Maze;

public abstract class BlockObject extends MazeGameObject implements ICollidable {
	
	public static final int NONE = 0;
	public static final int WALL = 1;
	public static final int OBJECTIVE = 2;
	public static final int TRAP = 3;
	
	protected int type;
	
	public BlockObject(int type, float x, float y) {
		this.type = type;
		getLocation().x = x;
		getLocation().y = y;
		setWidth(GameSettings.blockWidth);
		setHeight(GameSettings.blockHeight);
//		addBound(new RectangleBound(this, new Vector(0, 0), GameSettings.blockWidth, GameSettings.blockHeight));
	}
	
	@Override
	public void draw() {
		if (type != NONE)
			Maze.drawBlock(this, type);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
