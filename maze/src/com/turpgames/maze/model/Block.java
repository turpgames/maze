package com.turpgames.maze.model;

import com.turpgames.framework.v0.impl.RectangleBound;
import com.turpgames.framework.v0.util.Rotation;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.maze.utils.Maze;

/***
 * Block elements of {@link Level}.
 * 
 * @author kadirello
 * 
 */
public class Block extends MazeGameObject {

	Block(float x, float y) {
		getLocation().x = x;
		getLocation().y = y;
		setWidth(Level.blockWidth);
		setHeight(Level.blockHeight);
		addBound(new RectangleBound(this, new Vector(0, 0), Level.blockWidth, Level.blockHeight));
	}
	
	@Override
	public void draw() {
		Maze.drawBlock(this);
	}

	@Override
	public void registerSelf() {
//		Game.getInputManager().register(this, Utils.LAYER_GAME);
	}

	public void anchorRotation(Rotation rotation) {
		getRotation().angle = rotation.angle;
		getRotation().origin = rotation.origin;
	}
}