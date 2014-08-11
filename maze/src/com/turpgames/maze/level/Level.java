package com.turpgames.maze.level;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.box2d.IBody;
import com.turpgames.box2d.IBox2DObject;
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
	private final IBody lokumBody;

	private Mechanic mechanic;
	
	private RotationSign rotationSign;
	private List<Block> blocks;
	private Lokum lokum;
	
	public Level(LevelMeta levelMeta, IWorld world, IScreenView view) {
		mazeBody = Box2DBuilders.Body.kinematicBodyBuilder()
				.setCenter(center.x, center.y)
				.setAngularDamping(1)
				.build(world);

		rotationSign = new RotationSign((Game.getVirtualWidth() - R.ui.rotationSignWidth) / 2, 
				Game.getVirtualHeight() - R.ui.rotationSignHeight*2);
		
		blocks = new ArrayList<Block>();
		for (BlockMeta blockMeta : levelMeta.getBlocks()) {
			IShape blockShape = Box2DBuilders.Shape.buildBox(
					blockMeta.getWidth(), blockMeta.getHeight(),
					blockMeta.getX() - center.x + Maze.BLOCK_WIDTH / 2, blockMeta.getY() - center.y + Maze.BLOCK_HEIGHT / 2);

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

		lokumBody = Box2DBuilders.Body.dynamicBodyBuilder()
				.setCenter(center.x, center.y)
				.setAngularDamping(1)
				.build(world);

		IShape lokumShape = Box2DBuilders.Shape.buildBox(
				Maze.BLOCK_WIDTH, Maze.BLOCK_HEIGHT,
				levelMeta.getLokumX() - center.x + Maze.BLOCK_WIDTH / 2, levelMeta.getLokumY() - center.y + Maze.BLOCK_HEIGHT / 2);

		Box2DBuilders.Fixture.fixtureBuilder()
				.setDensity(5f)
				.setElasticity(0f)
				.setFriction(0f)
				.setShape(lokumShape)
				.build(lokumBody);

		lokumShape.dispose();
		
		lokum = new Lokum(lokumBody, levelMeta.getLokumX(), levelMeta.getLokumY());
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
		lokum.draw();
	}

	public float getRotationAngle() {
		return angle.z;
	}

	public void setBodyRotation(float rotation) {
		lokumBody.setTransform(center.x, center.y, rotation);
		lokum.syncWithObject();
		
		mazeBody.setTransform(center.x, center.y, rotation);
		angle.z = rotation;
	}

	public void addBodyRotation(float rotation) {
		angle.z += rotation;
		mazeBody.setTransform(center.x, center.y, angle.z);
		
		lokumBody.setTransform(center.x, center.y, angle.z);
		lokum.syncWithObject();
	}
	
	public void setSignRotation(int rotation) {
		rotationSign.direction = rotation;
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
	
	public class Lokum extends GameObject implements IBox2DObject {
		private IBody body;
		public Lokum(IBody lokumBody, float x, float y) {
			body = lokumBody;
			body.setData(this);
			setWidth(Maze.BLOCK_WIDTH);
			setHeight(Maze.BLOCK_HEIGHT);
			getLocation().set(x, y);
			
			getRotation().angle = new Vector(0,0,0);
			getRotation().origin = center;
			
			syncWithObject();
		}

		@Override
		public void draw() {
			Maze.drawLokum(this);
//			ShapeDrawer.drawRect(this, false);
		}
		@Override
		public void syncWithBody() {
			Vector bodyCenter = body.getCenter();
			float bodyRotation = body.getRotation();

			this.getLocation().set(
					bodyCenter.x,
					bodyCenter.y);

			this.getRotation().angle.z = bodyRotation;
		}

		@Override
		public void syncWithObject() {
			body.setTransform(center.x, center.y, this.getRotation().angle.z);
		}

		@Override
		public GameObject getObject() {
			return this;
		}

		@Override
		public IBody getBody() {
			return body;
		}
	}
}
