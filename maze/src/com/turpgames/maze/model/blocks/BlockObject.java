package com.turpgames.maze.model.blocks;

import com.badlogic.gdx.math.MathUtils;
import com.turpgames.box2d.Box2D;
import com.turpgames.box2d.IBody;
import com.turpgames.box2d.IBox2DObject;
import com.turpgames.box2d.IWorld;
import com.turpgames.box2d.IWorldTask;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Rotation;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.maze.level.BlockMeta;
import com.turpgames.maze.model.MazeGameObject;
import com.turpgames.maze.utils.Maze;

public class BlockObject extends MazeGameObject implements IBox2DObject {
	public static final int NONE = 0;
	public static final int WALL = 1;
	public static final int OBJECTIVE = 2;
	public static final int TRAP = 3;

	protected int type;
	protected final IBody body;

	private BlockObject(BlockMeta meta, IWorld world, Rotation rotation) {
		this.type = meta.getType();
		getLocation().x = meta.getX();
		getLocation().y = meta.getY();
		setWidth(meta.getWidth());
		setHeight(meta.getHeight());
		anchorRotation(rotation);

		this.body = createBodyBuilder().build(world);
		this.body.setData(this);
		syncWithObject();
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
		Vector bodyPos = body.getCenter();
		float x = bodyPos.x;// Box2D.worldToViewportX(bodyPos.x);
		float y = bodyPos.y;// Box2D.worldToViewportY(bodyPos.y);

		getLocation().x = x;
		getLocation().y = y;
		getRotation().setRotationZ(body.getRotation());
	}

	@Override
	public void syncWithObject() {
		body.getWorld().runTask(new IWorldTask() {
			@Override
			public void run() {
				body.setTransform(
						getLocation().x,
						getLocation().y,
						getRotation().angle.z);
			}
		});
	}

	public int getType() {
		return type;
	}

	public static BlockObject create(BlockMeta meta, IWorld world, Rotation rotation) {
		BlockObject block = new BlockObject(meta, world, rotation);
		return block;
	}

	public void addBodyRotation(float angle) {
		float r = body.getRotation() + angle;
		rotate(r);
	}

	public void setBodyRotation(float angle) {
		float r = MathUtils.degreesToRadians * angle;
		rotate(r);
	}

	public void rotate(final float r) {
		body.getWorld().runTask(new IWorldTask() {
			@Override
			public void run() {
				body.setTransform(
						Box2D.viewportToWorldX(getLocation().x),
						Box2D.viewportToWorldY(getLocation().y),
						r);
			}
		});
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
}
