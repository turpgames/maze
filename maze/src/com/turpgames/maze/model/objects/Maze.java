package com.turpgames.maze.model.objects;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.box2d.IBody;
import com.turpgames.box2d.IBox2DObject;
import com.turpgames.box2d.IFixture;
import com.turpgames.box2d.IShape;
import com.turpgames.box2d.IWorld;
import com.turpgames.box2d.builders.Box2DBuilders;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Vector;

public class Maze implements IBox2DObject, IDrawable {
	public final static float dx = 30f;
	public final static int cols = 12;
	public final static int rows = 12;
	public final static float blockSize = (Game.getVirtualWidth() - 2 * dx) / cols;
	public final static float dy = (Game.getVirtualHeight() - blockSize * rows) / 2;

	private final static float viewportCenterX = Game.getVirtualWidth() / 2;
	private final static float viewportCenterY = Game.getVirtualHeight() / 2;

	private final IBody mazeBody;
	private final MazeObject mazeObject;
	private final Block[] blocks;
	private final Lokum lokum;

	public Maze(int[][] blocksData, int lokumX, int lokumY, IWorld world) {
		this.mazeObject = new MazeObject();
		this.mazeObject.getRotation().origin.set(viewportCenterX, viewportCenterY);

		List<Block> blockList = new ArrayList<Block>();

		this.mazeBody = Box2DBuilders.Body.kinematicBodyBuilder()
				.setCenter(viewportCenterX, viewportCenterY)
				.build(world);

		Lokum tmpLokum = null;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (blocksData[i][j] == 0)
					continue;

				int row = rows - i - 1;
				int col = j;

				if (blocksData[i][j] == 1) {
					Block block = buildBlock(row, col);
					blockList.add(block);
				}
				if (blocksData[i][j] == 2) {
					tmpLokum = buildLokum(row, col, world);
				}
			}
		}

		this.mazeBody.setData(this);

		this.blocks = blockList.toArray(new Block[0]);
		this.lokum = tmpLokum;
	}

	private Block buildBlock(int row, int col) {
		float cx = (col - (cols - 1f) / 2f) * blockSize;
		float cy = (row - (rows - 1f) / 2f) * blockSize;

		IShape blockShape = Box2DBuilders.Shape.buildBox(blockSize, blockSize, cx, cy);

		IFixture blockFixture = Box2DBuilders.Fixture.fixtureBuilder()
				.setDensity(100)
				.setElasticity(0)
				.setFriction(0.5f)
				.setShape(blockShape)
				.build(mazeBody);

		blockShape.dispose();

		cx += viewportCenterX;
		cy += viewportCenterY;

		Block block = new Block(this, cx - blockSize / 2, cy - blockSize / 2, blockSize);
		blockFixture.setData(block);
		return block;
	}

	private Lokum buildLokum(int row, int col, IWorld world) {
		float size = blockSize - 5f;

		float cx = dx + (col + 0.5f) * blockSize;
		float cy = dy + (row + 0.5f) * blockSize;

		IBody lokumBody = Box2DBuilders.Body.dynamicBodyBuilder()
				.setCenter(cx, cy)
				.setAngularDamping(100f)
				.setBullet(true)
				.build(world);

		IShape lokumShape = Box2DBuilders.Shape.buildBox(size, size, 0, 0);

		IFixture lokumFixture = Box2DBuilders.Fixture.fixtureBuilder()
				.setDensity(100)
				.setElasticity(0)
				.setFriction(0.5f)
				.setShape(lokumShape)
				.build(lokumBody);

		lokumShape.dispose();

		Lokum lokum = new Lokum(lokumBody, cx - size / 2, cy - size / 2, size);
		lokumFixture.setData(lokum);
		return lokum;
	}

	float getRotation() {
		return mazeObject.getRotation().angle.z;
	}

	void setRotation(float degrees) {
		mazeObject.getRotation().angle.z = degrees;
		mazeBody.setTransform(viewportCenterX, viewportCenterY, degrees);
	}

	Vector getCenter() {
		return mazeObject.getRotation().origin;
	}

	@Override
	public void syncWithBody() {
		mazeObject.getRotation().angle.z = mazeBody.getRotation();
		for (IFixture fixture : mazeBody.getFixtures()) {
			Block block = (Block) fixture.getData();
			block.syncWithMaze();
		}
	}

	@Override
	public void syncWithObject() {

	}

	@Override
	public GameObject getObject() {
		return mazeObject;
	}

	@Override
	public IBody getBody() {
		return mazeBody;
	}

	@Override
	public void draw() {
		for (Block block : blocks)
			block.draw();
		lokum.draw();
	}

	private class MazeObject extends GameObject {
		@Override
		public void draw() {

		}
	}
}
