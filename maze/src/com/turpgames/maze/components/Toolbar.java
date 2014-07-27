package com.turpgames.maze.components;

import com.turpgames.framework.v0.component.Button2;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.maze.utils.Textures;

public class Toolbar {
	public final static float toolbarMargin = Game.scale(5f);
	public final static float menuButtonSize = 50f;
	public final static float menuButtonSizeToScreen = Game.scale(menuButtonSize);

	protected Button2 backButton;
	protected Button2 resetButton;
	protected Button2 soundButton;
	protected Button2 infoButton;

	private IToolbarListener listener;

	private static Toolbar instance;

	public static Toolbar getInstance() {
		if (instance == null)
			instance = new Toolbar();
		return instance;
	}

	private Toolbar() {
		addBackButton();
		addSoundButton();
		addResetButton();
		addInfoButton();
	}

	public void setListener(IToolbarListener listener) {
		this.listener = listener;
	}

	public void activateBackButton() {
		backButton.activate();
	}

	public void deactivateBackButton() {
		backButton.deactivate();
	}

	public void activateResetButton() {
		resetButton.activate();
	}

	public void deactivateResetButton() {
		resetButton.deactivate();
	}

	public void activateInfoButton() {
		infoButton.activate();
	}

	public void deactivateInfoButton() {
		infoButton.deactivate();
	}

	public void deactivate() {
		soundButton.deactivate();
		backButton.deactivate();
		resetButton.deactivate();
		infoButton.deactivate();
	}

	public void activate() {
		soundButton.activate();
		backButton.activate();
	}

	public Button2 getBackButton() {
		return backButton;
	}

	protected void addBackButton() {
		backButton = new Button2();
		backButton.setSize(menuButtonSize, menuButtonSize);
		backButton.setTexture(Game.getResourceManager().getTexture("tb_back"));
		backButton.setLocation(5f, Game.descale(Game.getScreenHeight()) - 5f - menuButtonSize);

		backButton.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				if (listener != null)
					listener.onToolbarBack();
			}
		});
	}

	protected void addResetButton() {
		resetButton = new Button2();
		resetButton.setSize(menuButtonSize, menuButtonSize);
		resetButton.setTexture(Game.getResourceManager().getTexture("tb_reset"));
		resetButton.setLocation(
				Game.descale(Game.getScreenWidth()) - 2 * (5f + menuButtonSize),
				Game.descale(Game.getScreenHeight()) - 5f - menuButtonSize);

		resetButton.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				if (listener != null)
					listener.onResetGame();
			}
		});
	}

	protected void addInfoButton() {
		infoButton = new Button2();
		infoButton.setSize(menuButtonSize, menuButtonSize);
		infoButton.setTexture(Textures.info);
		infoButton.setLocation(
				Game.descale(Game.getScreenWidth()) - 3 * (5f + menuButtonSize),
				Game.descale(Game.getScreenHeight()) - 5f - menuButtonSize);

		infoButton.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				if (listener != null)
					listener.onShowDescription();
			}
		});
	}

	protected void addSoundButton() {
		soundButton = new AudioButton();
	}
}
