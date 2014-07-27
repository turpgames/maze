package com.turpgames.maze.view;

import com.turpgames.framework.v0.IResourceManager;
import com.turpgames.framework.v0.client.ConnectionManager;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Debug;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.maze.components.TurpLogo;
import com.turpgames.maze.updates.UpdateManager;
import com.turpgames.maze.utils.R;

public class SplashScreen extends Screen {
	private Color progressColor;
	private IResourceManager resourceManager;

	@Override
	public void draw() {
		super.draw();
		float f = (Game.getVirtualWidth() - 50F) * resourceManager.getProgress();
		ShapeDrawer.drawRect((Game.getVirtualWidth() - f) / 2.0F, 100F, f, 20F, progressColor, true, false);
	}

	@Override
	public void init() {
		ConnectionManager.init();
		super.init();
		registerDrawable(new TurpLogo(), 0);
		progressColor = new Color(R.colors.yellow);
		resourceManager = Game.getResourceManager();

		Debug.println(Game.getPhysicalScreenSize());
	}

	@Override
	public void update() {
		if (!resourceManager.isLoading()) {
			UpdateManager.runUpdates();
			switchToGame();
		}
	}

	private void switchToGame() {
		ScreenManager.instance.switchTo(R.screens.menu, false);
	}
}
