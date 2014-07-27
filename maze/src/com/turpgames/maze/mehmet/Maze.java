package com.turpgames.maze.mehmet;

import com.turpgames.box2d.IBody;
import com.turpgames.box2d.IFixture;
import com.turpgames.box2d.IShape;
import com.turpgames.box2d.IWorld;
import com.turpgames.box2d.builders.Box2DBuilders;
import com.turpgames.framework.v0.IDrawingInfo;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;

public class Maze {
	private final static int blockCount = 12;
	private final static float blockSize = Game.getVirtualWidth() / blockCount;

	private final static float cx = Game.getVirtualWidth() / 2;
	private final static float cy = Game.getVirtualHeight() / 2;

	private final IBody mazeBody;
	private final IBody lokumBody;
	private boolean isRotating;
	private float rotateStartX;
	private float rotateStartY;
	private float rotation;

	Square sq = new Square();

	public Maze(IWorld world) {
		mazeBody = Box2DBuilders.Body.kinematicBodyBuilder()
				.setCenter(cx, cy)
				.setAngularDamping(1)
				.build(world);

		for (int i = 0; i < blockCount; i++) {
			for (int j = 0; j < blockCount; j++) {
				if (i != 0 && i != blockCount - 1 && j != 0 && j != blockCount - 1)
					continue;
				
				float x = (j + 0.5f - blockCount / 2);
				float y = (i + 0.5f - blockCount / 2);

				IShape blockShape = buildBlockShape(x * blockSize, y * blockSize);

				IFixture blockFixture = Box2DBuilders.Fixture.fixtureBuilder()
						.setDensity(5f)
						.setElasticity(0f)
						.setFriction(0f)
						.setShape(blockShape)
						.build(mazeBody);

				blockShape.dispose();
			}
		}

		lokumBody = Box2DBuilders.Body.dynamicBodyBuilder()
				.setCenter(cx, cy)
				.setAngularDamping(1)
				.build(world);

		IShape lokumShape = buildBlockShape(0, 0);

		IFixture lokumFixture = Box2DBuilders.Fixture.fixtureBuilder()
				.setDensity(5f)
				.setElasticity(0f)
				.setFriction(0f)
				.setShape(lokumShape)
				.build(lokumBody);

		lokumShape.dispose();
	}

	private IShape buildBlockShape(float x, float y) {
		return Box2DBuilders.Shape.buildBox(blockSize, blockSize, x, y);
		// return Box2DBuilders.Shape.buildCircle(blockSize / 2, x, y);
	}

	public void drawSq() {
		// sq.draw();
		// ShapeDrawer.drawRect(IDrawingInfo.viewport, false);
	}

	public void endRotate() {
		if (!isRotating)
			return;
		isRotating = false;
	}

	public void beginRotate(float x, float y) {
		if (isRotating)
			return;

		isRotating = true;
		rotateStartX = x;
		rotateStartY = y;
	}

	public void rotate(float x, float y) {
		if (!isRotating)
			return;

		float sw = Game.getScreenWidth();
		float sh = Game.getScreenHeight();

		float dx = ((x - rotateStartX) / sw) * 90;
		float dy = ((y - rotateStartY) / sh) * 90;

		if (y > sh / 2)
			dx = -dx;
		if (x < sw / 2)
			dy = -dy;

		// rotation = limitAngle(rotation + dx + dy);
		rotation += dx + dy;

		rotateStartX = x;
		rotateStartY = y;

		mazeBody.setTransform(cx, cy, rotation);
	}

	private final static float maxTemp = 35;
	private final static float minTemp = 15;

	private float limitAngle(float angle) {
		if (angle > maxTemp)
			angle = maxTemp;
		else if (angle < -maxTemp)
			angle = -maxTemp;
		return angle;
	}

	private static class Square extends GameObject {
		public Square() {
			setWidth(blockSize);
			setHeight(blockSize);
			getLocation().set(cx - blockSize / 2, cy - blockSize / 2);
		}

		@Override
		public void draw() {
			ShapeDrawer.drawRect(this, false);
		}

	}
}
