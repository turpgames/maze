package com.blox.maze.model;

import com.turpgames.framework.v0.impl.RectangleBound;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.maze.utils.Maze;

/***
 * Trap elements of {@link Mazeda}.
 * 
 * @author kadirello
 * 
 */
public class Trap extends MazeGameObject {

	Trap(float x, float y) {
		getLocation().x = x;
		getLocation().y = y;
		setWidth(Mazeda.blockWidth);
		setHeight(Mazeda.blockHeight);
		addBound(new RectangleBound(this, new Vector(0, 0), Mazeda.blockWidth, Mazeda.blockHeight));
	}
	
	@Override
	public void draw() {
		Maze.drawTrap(this);
	}

	@Override
	public void registerSelf() {
		Game.getInputManager().register(this, Utils.LAYER_GAME);
	}
}