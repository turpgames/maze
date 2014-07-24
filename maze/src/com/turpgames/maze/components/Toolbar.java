package com.turpgames.maze.components;

import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.component.ToggleButton;
import com.turpgames.framework.v0.component.Toolbar.IToolbarListener;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;

public class Toolbar extends GameObject {
	public final static float toolbarMargin = Game.scale(15);
	public final static float menuButtonSizeToScreen = Game.scale(64f);
	
	protected ImageButton backButton;
	protected ToggleButton soundButton;
	
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
		listenInput(true);
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
	
	public void disable() {
		soundButton.deactivate();
		backButton.deactivate();
	}

	public void enable() {
		soundButton.activate();
		backButton.activate();
	}
	
	public ImageButton getBackButton() {
		return backButton;
	}

	protected void addBackButton() {
		backButton = new ImageButton(menuButtonSizeToScreen, menuButtonSizeToScreen, "tb_back", Color.white(), Color.white());
		backButton.setLocation(Button.AlignNW, toolbarMargin, toolbarMargin);
		backButton.deactivate();
		backButton.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				if (listener != null)
					listener.onToolbarBack();
			}
		});
	}

	protected void addSoundButton() {
		soundButton = new AudioButton();
		soundButton.setLocation(Button.AlignNE, toolbarMargin, toolbarMargin);
		soundButton.deactivate();
	}
	
	@Override
	public void draw() {
		soundButton.draw();
		backButton.draw();
	}

	@Override
	public boolean ignoreViewport() {
		return true;
	}
}
