package com.turpgames.maze.model;

import com.turpgames.box2d.Box2D;
import com.turpgames.box2d.IBody;
import com.turpgames.box2d.IShape;
import com.turpgames.box2d.IWorld;
import com.turpgames.box2d.builders.BodyBuilder;
import com.turpgames.box2d.builders.Box2DBuilders;
import com.turpgames.box2d.builders.FixtureBuilder;

public class RectBodyBuilder {
	private final IShape square;
	private final BodyBuilder bodyBuilder;
	private final FixtureBuilder fixtureBuilder;

	private RectBodyBuilder(float x, float y, float width, float height, boolean isDynamic) {
		this.square = Box2DBuilders.Shape.buildBox(width, height, 0, 0);

		this.bodyBuilder = isDynamic
				? Box2DBuilders.Body.dynamicBodyBuilder()
				: Box2DBuilders.Body.staticBodyBuilder();

		this.bodyBuilder.setCenter(
				Box2D.viewportToWorldX(x),
				Box2D.viewportToWorldY(y));

		this.fixtureBuilder = Box2DBuilders.Fixture.fixtureBuilder()
				.setElasticity(0.6f)
				.setDensity(1.2f)
				.setFriction(0.2f)
				.setShape(square);
	}

	public RectBodyBuilder setAsSensor() {
		fixtureBuilder.setSensor(true);
		return this;
	}

	public RectBodyBuilder setNonElastic() {
		fixtureBuilder.setElasticity(0);
		return this;
	}

	public IBody build(IWorld world) {
		IBody body = bodyBuilder.build(world, fixtureBuilder);
		square.dispose();
		return body;
	}

	public static RectBodyBuilder newBuilder(float x, float y, float width, float height, boolean isDynamic) {
		return new RectBodyBuilder(x, y, width, height, isDynamic);
	}
}
