package com.turpgames.maze.view;

import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.maze.components.Toolbar;
import com.turpgames.maze.components.ToolbarListenerAdapter;
import com.turpgames.maze.controller.GameController;
import com.turpgames.maze.utils.R;

public class GameScreen extends Screen implements IScreenView {

	private GameController controller;

	public void init() {
		super.init();
		controller = new GameController(this);
		controller.activate();
	}

	@Override
	protected void onAfterActivate() {
		super.onAfterActivate();
		Toolbar.getInstance().activate();
		Toolbar.getInstance().activateResetButton();
		Toolbar.getInstance().setListener(new ToolbarListenerAdapter() {
			@Override
			public void onToolbarBack() {
				onBack();
			}
			
			@Override
			public void onResetGame() {
				controller.resetGame();
			}
			
			@Override
			public void onShowDescription() {
				controller.openDescriptionDialog();
			}
		});
	}

	@Override
	protected boolean onBeforeDeactivate() {
		controller.deactivate();
		Toolbar.getInstance().deactivateResetButton();
		Toolbar.getInstance().deactivate();
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
