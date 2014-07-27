package com.turpgames.maze.view;

import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.impl.FormScreen;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.maze.components.Toolbar;
import com.turpgames.maze.components.ToolbarListenerAdapter;
import com.turpgames.maze.utils.StatActions;

public class MenuScreen extends FormScreen {
	private boolean isFirstActivate;

	@Override
	public void init() {
		super.init();
		isFirstActivate = true;
		setForm("mainForm", false);
	}

	@Override
	protected void onAfterActivate() {
		super.onAfterActivate();
		
		if (isFirstActivate) {
			isFirstActivate = false;
//			TurpClient.init();

			if (Settings.getInteger("game-installed", 0) == 0) {
				TurpClient.sendStat(StatActions.GameInstalled, Game.getPhysicalScreenSize().toString());
				Settings.putInteger("game-installed", 1);
			}

			TurpClient.sendStat(StatActions.StartGame);
		}
		Toolbar.getInstance().activate();
		Toolbar.getInstance().deactivateBackButton();
		Toolbar.getInstance().setListener(new ToolbarListenerAdapter() {
			@Override
			public void onToolbarBack() {
				onBack();
			}
		});
	}
	
	@Override
	protected boolean onBeforeDeactivate() {
		Toolbar.getInstance().deactivate();
		return super.onBeforeDeactivate();
	}

	@Override
	protected boolean onBack() {
		TurpClient.sendStat(StatActions.ExitGame);
		Game.exit();
		return true;
	}
}
