package com.turpgames.maze.controller;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.box2d.Box2DWorld;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.maze.level.Level;
import com.turpgames.maze.model.ILevelListener;
import com.turpgames.maze.view.IScreenView;

public class GameController implements ILevelListener {
	private final IScreenView view;
	private Box2DWorld world;

	private boolean isPlaying;
	private boolean isGameOver;
	private Text text;

	private final List<IDrawable> drawables = new ArrayList<IDrawable>();
	private Level level;
	
	public GameController(IScreenView view) {
		this.view = view;
	}
	
	private void updateText(String s) {
		text.setText(s);
	}

	public void activate() {
		world = new Box2DWorld();
		Global.currentGame = this;
		
		text = new Text();
		text.setLocation(Game.screenToViewportX(10f), Game.screenToViewportY(10f));
		text.setFontScale(0.5f);

		isPlaying = false;
		isGameOver = false;
		
		level = new Level(world, this, view);
		registerGameDrawable(level);
		
		updateText("touch to begin");
		
		level.activate();
		view.registerDrawable(text, Game.LAYER_INFO);
//		view.registerInputListener(listener);

		isPlaying = true;

//		world.getWorld().setContactListener(Global.levelMeta.getContactListener());
	}

	public void deactivate() {
		Global.currentGame = null;
		
		isPlaying = false;

		world.getWorld().dispose();
		world = null;
		for (IDrawable d : drawables)
			view.unregisterDrawable(d);
		drawables.clear();

		level.deactivate();
		view.unregisterDrawable(text);
//		view.unregisterInputListener(listener);
	}

	public void update() {
		if (isPlaying) {
			level.update();
			world.update();
//			if (hits == 0 && !ball.isMoving()) {
//				onHitCountEnd();
//			}
		}
	}

	private void registerGameDrawable(IDrawable drawable) {
		drawables.add(drawable);
		view.registerDrawable(drawable, Game.LAYER_GAME);
	}
}
