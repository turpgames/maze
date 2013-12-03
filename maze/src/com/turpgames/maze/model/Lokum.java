package com.turpgames.maze.model;

import com.turpgames.framework.v0.IBound;
import com.turpgames.framework.v0.ICollidable;
import com.turpgames.framework.v0.impl.AnimatedGameObject;
import com.turpgames.framework.v0.impl.RectangleBound;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.maze.utils.Maze;
import com.turpgames.maze.utils.R;

public class Lokum extends AnimatedGameObject {
	private Vector startLocation;

	public Lokum(Level maze, float x, float y) {
		this.startLocation = new Vector();
		this.startLocation.x = maze.tx + x * Level.blockWidth;
		this.startLocation.y = maze.ty + y * Level.blockHeight;
		getLocation().x = this.startLocation.x;
		getLocation().y = this.startLocation.y;
		
		setWidth(Level.blockWidth);
		setHeight(Level.blockHeight);
		// bounds.add(new RectangleBound(this, new Vector(2, 2), Maze.blockWidth
		// - 4, Maze.blockHeight - 4));
		addBound(new RectangleBound(this, new Vector(0, 0), Level.blockWidth, Level.blockHeight));
		addAnimation(R.game.animations.fellOnTrap);
		addAnimation(R.game.animations.fellOnObjective);
		addAnimation(R.game.animations.fellOnPortal);
	}
	
	@Override
	public void draw() {
		if (getAnimation() == null)
			Maze.drawLokum(this);
		else 
			super.draw();
	}

	@Override
	public void registerSelf() {
		
	}

	/***
	 * Repositions {@link Lokum} to outside the colliding boundary.
	 * 
	 * @param thisBound
	 * @param thatBound
	 * @param obj
	 */
	public void fellOnBlock(IBound thisBound, IBound thatBound, ICollidable obj) {
		Vector a = getAcceleration();
		Vector l = getLocation();
		if (a.y < 0)
			l.y = thatBound.getLocation().y + ((RectangleBound) thatBound).getHeight() - thisBound.getOffset().y;
		else if (a.x < 0)
			l.x = thatBound.getLocation().x + ((RectangleBound) thatBound).getWidth() - thisBound.getOffset().x;
		else if (a.y > 0)
			l.y = thatBound.getLocation().y - ((RectangleBound) thatBound).getHeight() + thisBound.getInvOffset().y;
		else if (a.x > 0)
			l.x = thatBound.getLocation().x - ((RectangleBound) thatBound).getWidth() + thisBound.getInvOffset().x;
	}

	public void stopLokum() {
		getAcceleration().set(0);
		getVelocity().set(0);
	}


	public void fellOnTrap() {
		startAnimation(R.game.animations.fellOnTrap);
	}

	public void fellOnPortal() {
		startAnimation(R.game.animations.fellOnPortal);
	}

	public void fellOnObjective() {
		startAnimation(R.game.animations.fellOnObjective);
	}

	/***
	 * {@link Lokum} is reset to its starting state.
	 */
	public void reset() {
		getLocation().set(this.startLocation.x, this.startLocation.y);
		stopAnimation();
	}

	/***
	 * {@link Lokum} is teleported to position of the given
	 * {@link com.blox.framework.v0.ICollidable ICollidable} object.
	 * 
	 * @param obj
	 * @see {@link com.blox.maze.controller.MazeController#portalFinished()
	 *      portalFinished()}
	 */
	public void teleport(ICollidable obj) {
		getLocation().set(obj.getLocation());
	}
}