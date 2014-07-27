package com.turpgames.maze.view;

import com.turpgames.framework.v0.component.Button2;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.effects.BreathEffect;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.impl.TexturedGameObject;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.maze.level.LevelManager;
import com.turpgames.maze.level.LevelMeta;
import com.turpgames.maze.utils.Global;
import com.turpgames.maze.utils.R;
import com.turpgames.maze.utils.Textures;

public class ResultScreen extends Screen {
	private final static float buttonWidth = 150f;
	private final static float buttonHeight = 50f;

	private Button2 levelSelectionButton;
	private Button2 retryButton;
	private Button2 nextButton;
	private StarImage star;

	private LevelMeta nextLevel;

	@Override
	public void init() {
		super.init();

		levelSelectionButton = initButton("Levels", (Game.getVirtualWidth() - buttonWidth) * 0.5f, 250f, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				ScreenManager.instance.switchTo(R.screens.levels, true);
			}
		});

		retryButton = initButton("Retry", 50f, 350f, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				ScreenManager.instance.switchTo(R.screens.game, true);
			}
		});

		nextButton = initButton("Next", Game.getVirtualWidth() - buttonWidth - 50f, 350f, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				LevelManager.startLevel(nextLevel);
			}
		});

		star = new StarImage();
	}

	@Override
	protected void onAfterActivate() {
		int stars = LevelManager.updateLevelState();
		
		star.setTexture(Textures.getStar(stars));
		registerDrawable(star, Game.LAYER_GAME);
		star.runEffect();

		nextLevel = LevelManager.unlockNextLevel();

		if (nextLevel == null) {
			nextButton.deactivate();
		}
		else if (nextLevel.getPack() == Global.currentLevel.getPack()) {
			nextButton.setText("Next");
			nextButton.activate();
		}
		else {
			nextButton.setText("Next Pack");
			nextButton.activate();
		}

		levelSelectionButton.activate();
		retryButton.activate();
	}

	@Override
	protected boolean onBeforeDeactivate() {
		levelSelectionButton.deactivate();
		retryButton.deactivate();
		nextButton.deactivate();

		unregisterDrawable(star);

		return super.onBeforeDeactivate();
	}

	private Button2 initButton(String text, float x, float y, IButtonListener listener) {
		Button2 btn = new Button2();

		btn.setText(text);
		btn.setSize(buttonWidth, buttonHeight);
		btn.setFontScale(0.6f);
		btn.setListener(listener);
		btn.setTexture(Textures.button_blue, Textures.button_green);
		btn.setLocation(x, y);

		return btn;
	}

	@Override
	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.screens.levels, true);
		return true;
	}

	private class StarImage extends TexturedGameObject {
		private final static float size = 128f;

		private final BreathEffect effect;

		public StarImage() {
			setWidth(size);
			setHeight(size);
			getLocation().set((Game.getVirtualWidth() - size) / 2f, 500f);
			getRotation().setOrigin(
					getLocation().x + size / 2,
					getLocation().y + size / 2);

			effect = new BreathEffect(this);
			effect.setDuration(0.5f);
			effect.setFinalScale(1.0f);
			effect.setMinFactor(0.8f);
			effect.setMaxFactor(1.2f);
			effect.setLooping(false);
		}

		void runEffect() {
			effect.start();
		}
	}
}
