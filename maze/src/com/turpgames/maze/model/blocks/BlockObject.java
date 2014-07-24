package com.turpgames.maze.model.blocks;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.turpgames.box2d.Box2D;
import com.turpgames.box2d.Box2DWorld;
import com.turpgames.box2d.IBox2DObject;
import com.turpgames.box2d.IUnlockedTask;
import com.turpgames.framework.v0.util.Rotation;
import com.turpgames.maze.level.BlockMeta;
import com.turpgames.maze.model.MazeGameObject;
import com.turpgames.maze.utils.Maze;

public class BlockObject extends MazeGameObject implements IBox2DObject {
	public static final int NONE = 0;
	public static final int WALL = 1;
	public static final int OBJECTIVE = 2;
	public static final int TRAP = 3;
	
	protected int type;
	protected final Body body;
	
	private BlockObject(BlockMeta meta, Box2DWorld world, Rotation rotation) {
		this.type = meta.getType();
		getLocation().x = meta.getX();
		getLocation().y = meta.getY();
		setWidth(meta.getWidth());
		setHeight(meta.getHeight());
		anchorRotation(rotation);
		
		this.body = createBodyBuilder().build(world);
		this.body.setUserData(this);
	}

	protected RectBodyBuilder createBodyBuilder() {
		return RectBodyBuilder.newBuilder(getLocation().x, getLocation().y, getWidth(), getHeight(), isDynamic());
	}

	public boolean isDynamic() {
		return false;
	}
	
	@Override
	public void draw() {
		if (type != NONE)
			Maze.drawBlock(this, type);
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
	
	public void syncWithObject() {
		if (body.getWorld().isLocked()) {
			Box2D.enqueueTask(new IUnlockedTask() {
				@Override
				public void run() {
					body.setTransform(
							Box2D.viewportToWorldX(getLocation().x),
							Box2D.viewportToWorldY(getLocation().y),
							MathUtils.degreesToRadians * getRotation().angle.z);
				}
			});
		}
		else {
			body.setTransform(
					Box2D.viewportToWorldX(getLocation().x),
					Box2D.viewportToWorldY(getLocation().y),
					MathUtils.degreesToRadians * getRotation().angle.z);
		}
	}
	
	public int getType() {
		return type;
	}
	
	public static BlockObject create(BlockMeta meta, Box2DWorld world, Rotation rotation) {
		BlockObject block = new BlockObject(meta, world, rotation);
		return block;
	}

	public void addBodyRotation(float angle) {
		float r = MathUtils.degreesToRadians * (MathUtils.radiansToDegrees * body.getAngle() + angle);
		rotate(r);
	}

	public void setBodyRotation(float angle) {
		float r = MathUtils.degreesToRadians * angle;
		rotate(r);
	}
	
	public void rotate(final float r) {
		if (body.getWorld().isLocked()) {
			Box2D.enqueueTask(new IUnlockedTask() {
				@Override
				public void run() {
					body.setTransform(
							Box2D.viewportToWorldX(getLocation().x),
							Box2D.viewportToWorldY(getLocation().y),
							r);
				}
			});
		}
		else {
			body.setTransform(
					Box2D.viewportToWorldX(getLocation().x),
					Box2D.viewportToWorldY(getLocation().y),
					r);
		}
	}
}
