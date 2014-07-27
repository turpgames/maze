package com.turpgames.maze.model;

import com.turpgames.box2d.IBody;
import com.turpgames.box2d.IBox2DObject;
import com.turpgames.box2d.IWorld;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Rotation;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.maze.utils.Maze;
import com.turpgames.maze.utils.R;

public class Lokum extends MazeAnimatedGameObject implements IBox2DObject {
	private Vector startLocation;
//	private List<ICollidable> collidedObjects;
	protected final IBody body;


	public Lokum(IWorld world, Rotation rotation, float x, float y) {
		this(world, rotation, x, y, Maze.BLOCK_WIDTH, Maze.BLOCK_HEIGHT);
	}
	
	public Lokum(IWorld world, Rotation rotation, float x, float y, float width, float height) {
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
		this.body.setData(this);
		syncWithObject();
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
		Vector bodyPos = body.getCenter();
		float x = bodyPos.x; // Box2D.worldToViewportX(bodyPos.x);
		float y = bodyPos.y; // Box2D.worldToViewportY(bodyPos.y);

		getLocation().x = x;
		getLocation().y = y;
		getRotation().setRotationZ(body.getRotation());
	}

	@Override
	public void syncWithObject() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GameObject getObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBody getBody() {
		// TODO Auto-generated method stub
		return null;
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