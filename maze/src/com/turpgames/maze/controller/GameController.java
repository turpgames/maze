package com.turpgames.maze.controller;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.box2d.Box2D;
import com.turpgames.box2d.IWorld;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.InputListener;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.maze.components.LevelResetEffect;
import com.turpgames.maze.components.Toolbar;
import com.turpgames.maze.level.Level;
import com.turpgames.maze.level.LevelMeta;
import com.turpgames.maze.model.ILevelListener;
import com.turpgames.maze.utils.Global;
import com.turpgames.maze.view.IScreenView;

public class GameController implements ILevelListener {
	
	private final static int StateWaitingStart = 0;
	private final static int StatePlaying = 1;
	private final static int StateGameOver = 2;
	private final static int StateReseting = 3;
	private final static int StateGameEnd = 4;
	private final static int StateReadingDescription = 5;

	private final IScreenView view;
	private final IWorld world;
	private final LevelResetEffect resetEffect;
	private final Timer restartTimer;

	private int state;

	private final List<IDrawable> drawables = new ArrayList<IDrawable>();

	private Level level;
	
	public GameController(IScreenView view) {
		this.view = view;
		this.world = Box2D.createWorld();
		this.resetEffect = new LevelResetEffect(new LevelResetEffect.IListener() {
			@Override
			public void onHalfCompleted() {
				endGame();
				initGame();
			}
		});

		this.restartTimer = new Timer();
		this.restartTimer.setInterval(1f);
		this.restartTimer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				resetGame();
			}
		});
		
//		registerGameDrawable(new IDrawable(){
//			public void draw(){
//			world.drawDebug();
//			}
//			});
//		
	}

	public void activate() {
		Global.currentController = this;
		
		initGame();

		view.registerInputListener(listener);
	}

	private void initGame() {
		LevelMeta levelMeta = Global.currentLevel;

		if (levelMeta.hasDescription()) {
			Toolbar.getInstance().activateInfoButton();
			if (levelMeta.isDescriptionRead()) {
				state = StateWaitingStart;
			} else {
				openDescriptionDialog();
			}
		}
		else {
			state = StateWaitingStart;
		}

		world.reset();

		level = new Level(levelMeta, world, view);
		registerGameDrawable(level);
		level.activate();

		world.setContactListener(Global.currentLevel.getContactListener());
		Global.levelPackViewId = Global.currentLevel.getPack().getTitle();
	}
	
	public void deactivate() {
		Global.currentController = null;

		world.dispose();
		for (IDrawable d : drawables)
			view.unregisterDrawable(d);
		drawables.clear();

		level.deactivate();
//		view.unregisterInputListener(listener);
	}

	public void update() {
			level.update();
			world.update();
//			if (hits == 0 && !ball.isMoving()) {
//				onHitCountEnd();
//			}
	}

	private void registerGameDrawable(IDrawable drawable) {
		drawables.add(drawable);
		view.registerDrawable(drawable, Game.LAYER_GAME);
	}

	private void startPlaying() {
		state = StatePlaying;
	}

	private void endGame() {
		state = StateGameEnd;

		for (IDrawable d : drawables)
			view.unregisterDrawable(d);
		drawables.clear();
	}
	
	public void resetGame() {
		if (state == StateReseting)
			return;
		state = StateReseting;
		restartTimer.stop();
		resetEffect.start();
	}
	
	public void openDescriptionDialog() {
		state = StateReadingDescription;
		
		String description = Global.currentLevel.getDescription();
		
		Dialog dialog = new Dialog();
		dialog.addButton("ok", "Ok");
		dialog.setFontScale(0.6f);
		dialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogClosed() {
				state = StateWaitingStart;
			}

			@Override
			public void onDialogButtonClicked(String id) {
				state = StateWaitingStart;
				Global.currentLevel.setDescriptionAsRead();
			}
		});
		dialog.open(description);
	}
	
	private boolean onTouchDown(float x, float y) {
		if (state == StateGameOver) {
			resetGame();
		} else if (state == StateWaitingStart) {
			startPlaying();
		}
		return false;
	}
	
	private final InputListener listener = new InputListener() {
		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			return onTouchDown(Game.screenToViewportX(x), Game.screenToViewportY(y));
		}
	};
}
