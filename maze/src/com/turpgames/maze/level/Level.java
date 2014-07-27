package com.turpgames.maze.level;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.box2d.IBody;
import com.turpgames.box2d.IShape;
import com.turpgames.box2d.IWorld;
import com.turpgames.box2d.builders.Box2DBuilders;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.maze.display.RotationSign;
import com.turpgames.maze.utils.Maze;
import com.turpgames.maze.utils.R;
import com.turpgames.maze.view.IScreenView;

public class Level implements IDrawable {

	private static Vector center = new Vector(Game.getVirtualWidth() / 2, Game.getVirtualHeight() / 2);
	private static Vector angle = new Vector(0,0,0);

	private final IBody mazeBody;
//	private final IBody lokumBody;

	private Mechanic mechanic;
	
	private RotationSign rotationSign;
	private List<Block> blocks;
	
	public Level(LevelMeta levelMeta, IWorld world, IScreenView view) {
		mazeBody = Box2DBuilders.Body.kinematicBodyBuilder()
				.setCenter(center.x, center.y)
				.setAngularDamping(1)
				.build(world);

		rotationSign = new RotationSign((Game.getVirtualWidth() - R.ui.rotationSignWidth) / 2, 
				Game.getVirtualHeight() - R.ui.rotationSignHeight*2);
		
		blocks = new ArrayList<Block>();
		for (BlockMeta blockMeta : levelMeta.getBlocks()) {
			IShape blockShape = buildBlockShape(blockMeta.getX() - center.x + Maze.BLOCK_WIDTH / 2, blockMeta.getY() - center.y + Maze.BLOCK_HEIGHT / 2);

			Box2DBuilders.Fixture.fixtureBuilder()
					.setDensity(5f)
					.setElasticity(0f)
					.setFriction(0f)
					.setShape(blockShape)
					.build(mazeBody);

			blockShape.dispose();
			
			blocks.add(new Block(blockMeta.getType(), blockMeta.getX(), blockMeta.getY()));
		}
		
		mechanic = new Mechanic(this, view);

//		lokumBody = Box2DBuilders.Body.dynamicBodyBuilder()
//				.setCenter(center.x, center.y)
//				.setAngularDamping(1)
//				.build(world);
//
//		IShape lokumShape = buildBlockShape(0, 0);
//
//		IFixture lokumFixture = Box2DBuilders.Fixture.fixtureBuilder()
//				.setDensity(5f)
//				.setElasticity(0f)
//				.setFriction(0f)
//				.setShape(lokumShape)
//				.build(lokumBody);
//
//		lokumShape.dispose();
	}

	private IShape buildBlockShape(float x, float y) {
		return Box2DBuilders.Shape.buildBox(Maze.BLOCK_WIDTH, Maze.BLOCK_HEIGHT, x, y);
		// return Box2DBuilders.Shape.buildCircle(blockSize / 2, x, y);
	}

	public void activate() {
		this.mechanic.activate();
	}
	
	public void deactivate() {
		this.mechanic.deactivate();
	}
	
	public void update() {
		this.mechanic.update();
	}
	
	@Override
	public void draw() {
		for (Block block : blocks)
			block.draw();
		// ShapeDrawer.drawRect(IDrawingInfo.viewport, false);
		rotationSign.draw();
	}
	
	public class Block extends GameObject {
		protected int type;
		public Block() {
			this(Maze.BLOCK_TYPE_WALL, center.x - Maze.BLOCK_WIDTH / 2, center.y - Maze.BLOCK_HEIGHT / 2);
		}

		public Block(int type, float x, float y) {
			this.type = type;
			setWidth(Maze.BLOCK_WIDTH);
			setHeight(Maze.BLOCK_HEIGHT);
			getLocation().set(x, y);
			
			getRotation().angle = angle;
			getRotation().origin = center;
		}

		@Override
		public void draw() {
			Maze.drawBlock(this, type);
//			ShapeDrawer.drawRect(this, false);
		}
	}

	public float getRotationAngle() {
		return angle.z;
	}

	public void setBodyRotation(float rotation) {
		mazeBody.setTransform(center.x, center.y, rotation);
		angle.z = rotation;
	}

	public void addBodyRotation(float rotation) {
		angle.z += rotation;
		mazeBody.setTransform(center.x, center.y, angle.z);
	}
	
	public void setSignRotation(int rotation) {
		rotationSign.direction = rotation;
	}
}
