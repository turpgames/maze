package com.turpgames.maze.model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.turpgames.box2d.Box2D;
import com.turpgames.box2d.Box2DWorld;
import com.turpgames.box2d.IBox2DObject;
import com.turpgames.framework.v0.util.Rotation;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.maze.level.MazeMover;
import com.turpgames.maze.model.blocks.RectBodyBuilder;
import com.turpgames.maze.utils.GameSettings;
import com.turpgames.maze.utils.Maze;
import com.turpgames.maze.utils.R;

public class Lokum extends MazeAnimatedGameObject implements IBox2DObject {
	private Vector startLocation;
//	private List<ICollidable> collidedObjects;
	protected final Body body;


	public Lokum(Box2DWorld world, Rotation rotation, float x, float y) {
		this(world, rotation, x, y, GameSettings.blockWidth, GameSettings.blockHeight);
	}
	
	public Lokum(Box2DWorld world, Rotation rotation, float x, float y, float width, float height) {
		this.startLocation = new Vector();
		this.startLocation.x = x;
		this.startLocation.y = y;
		getLocation().x = this.startLocation.x;
		getLocation().y = this.startLocation.y;
		
		setWidth(width);
		setHeight(height);
		// bounds.add(new RectangleBound(this, new Vector(2, 2), Maze.blockWidth
		// - 4, Maze.blockHeight - 4));
//		addBound(new RectangleBound(this, new Vector(0, 0), GameSettings.blockWidth, GameSettings.blockHeight));
		addAnimation(R.game.animations.fellOnTrap);
		addAnimation(R.game.animations.fellOnObjective);
		
//		MazeMover.instance.register(this);
		anchorRotation(rotation);

		this.body = createBodyBuilder().build(world);
		this.body.setUserData(this);
//		collidedObjects = new ArrayList<ICollidable>();
	}

	protected RectBodyBuilder createBodyBuilder() {
		return RectBodyBuilder.newBuilder(getLocation().x, getLocation().y, getWidth(), getHeight(), isDynamic());
	}

	public boolean isDynamic() {
		return true;
	}
	
	@Override
	public void draw() {
		if (getAnimation() == null)
			Maze.drawLokum(this);
		else 
			super.draw();
	}

	/***
	 * Repositions {@link Lokum} to outside the colliding boundary.
	 * 
	 * @param thisBound
	 * @param thatBound
	 * @param obj
	 */
//	public void fellOnBlock(IBound thisBound, IBound thatBound, ICollidable obj) {
//		Vector a = getAcceleration();
//		Vector l = getLocation();
//		if (a.y < 0)
//			l.y = thatBound.getLocation().y + ((RectangleBound) thatBound).getHeight() - thisBound.getOffset().y;
//		else if (a.x < 0)
//			l.x = thatBound.getLocation().x + ((RectangleBound) thatBound).getWidth() - thisBound.getOffset().x;
//		else if (a.y > 0)
//			l.y = thatBound.getLocation().y - ((RectangleBound) thatBound).getHeight() + thisBound.getInvOffset().y;
//		else if (a.x > 0)
//			l.x = thatBound.getLocation().x - ((RectangleBound) thatBound).getWidth() + thisBound.getInvOffset().x;
//	}
//	
//	@Override
//	public void onCollide(ICollidable thatObj, IBound thisBound,
//			IBound thatBound) {
//		if (collidedObjects.contains(thatObj))
//			return;
//		if (thatObj instanceof Wall) {
//			fellOnBlock(thisBound, thatBound, thatObj);
//			stopLokum();
//		} else if (thatObj instanceof Trap) {
//			fellOnTrap();
//		} else if (thatObj instanceof Objective) {
//			fellOnObjective();
//		}
//		collidedObjects.add(thatObj);
//	}
//	
//	@Override
//	public void onNotcollide(ICollidable thatObj, IBound thisBound,
//			IBound thatBound) {
//		collidedObjects.remove(thatObj);
//	}

	public void stopLokum() {
		getAcceleration().set(0);
		getVelocity().set(0);
	}


	public void fellOnTrap() {
		startAnimation(R.game.animations.fellOnTrap);
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

	@Override
	public void syncWithBody() {
		Vector2 bodyPos = body.getPosition();
		float x = Box2D.worldToViewportX(bodyPos.x);
		float y = Box2D.worldToViewportY(bodyPos.y);

		getLocation().x = x;
		getLocation().y = y;
		getRotation().setRotationZ(MathUtils.radiansToDegrees * body.getAngle());
	}

	/***
	 * {@link Lokum} is teleported to position of the given
	 * {@link com.blox.framework.v0.ICollidable ICollidable} object.
	 * 
	 * @param obj
	 * @see {@link com.turpgames.maze.controller.level.Controller.maze.controller.MazeController#portalFinished()
	 *      portalFinished()}
	 */
//	public void teleport(ICollidable obj) {
//		getLocation().set(obj.getLocation());
//	}
}