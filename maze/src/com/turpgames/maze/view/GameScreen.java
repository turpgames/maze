package com.turpgames.maze.view;

import com.turpgames.box2d.Box2D;
import com.turpgames.box2d.IWorld;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.maze.mehmet.Maze;

public class GameScreen extends Screen {
	private IWorld world;
	private Maze maze;

	@Override
	public void init() {
		super.init();

		world = Box2D.createWorld();
		
		maze = new Maze(world);

		registerDrawable(new IDrawable() {
			@Override
			public void draw() {
				world.drawDebug();
			}
		}, Game.LAYER_GAME);
		
//		registerInputListener(this);
	}
	
	@Override
	public void update() {
		super.update();
		if (isActive())
			world.update();
	}
	
	@Override
	public void draw() {
		maze.drawSq();
		super.draw();
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		maze.beginRotate(x, y);
		return super.touchUp(x, y, pointer, button);
	}
	
	@Override
	public boolean touchDragged(float x, float y, int pointer) {
		maze.rotate(x, y);
		return super.touchDragged(x, y, pointer);
	}
	
	@Override
	public boolean touchUp(float x, float y, int pointer, int button) {
		maze.endRotate();
		return super.touchUp(x, y, pointer, button);
	}
}
