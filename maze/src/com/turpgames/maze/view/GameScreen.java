package com.turpgames.maze.view;

import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.maze.components.Toolbar;
import com.turpgames.maze.controller.GameController;
import com.turpgames.maze.controller.Global;
import com.turpgames.maze.level.LevelPack;
import com.turpgames.maze.level.StarterPack;
import com.turpgames.maze.utils.R;

public class GameScreen extends Screen implements IScreenView {

	private GameController controller;

	public void init() {
		super.init();
		
		LevelPack pack = StarterPack.createPack();
		Global.levelMeta = pack.getLevels()[0];
		controller = new GameController(this);
		registerDrawable(Toolbar.getInstance(), Game.LAYER_INFO);
	}

	@Override
	protected void onAfterActivate() {
		super.onAfterActivate();
		controller.activate();
		Toolbar.getInstance().enable();
		Toolbar.getInstance().setListener(new com.turpgames.framework.v0.component.Toolbar.IToolbarListener() {
			@Override
			public void onToolbarBack() {
				onBack();
			}
		});
	}

	@Override
	protected boolean onBeforeDeactivate() {
		controller.deactivate();
		Toolbar.getInstance().disable();
		return super.onBeforeDeactivate();
	}

	@Override
	public void update() {
		super.update();
		controller.update();
	}

	@Override
	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.screens.levels, true);
		return true;
	}
}
