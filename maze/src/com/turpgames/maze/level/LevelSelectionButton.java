package com.turpgames.maze.level;

import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.maze.utils.Textures;

public class LevelSelectionButton extends ImageButton {
	private final static int cols = 4;
	private final static int rows = 5;
	private final static float itemMargin = 20f;
	private final static float itemSize = (Game.getVirtualWidth() - (cols + 1) * itemMargin) / cols;
	private final static float yOffset = (Game.getVirtualHeight() - rows * itemSize - (rows + 1) * itemMargin) / 2f - 40f;

	private final LevelMeta level;

	public LevelSelectionButton(LevelMeta level) {
		this.level = level;

		int levelIndex = level.getIndex();

		setWidth(itemSize);
		setHeight(itemSize);

		int col = (levelIndex - 1) % cols;
		int row = (levelIndex - 1) / cols;
		row = rows - row - 1;

		float x = (col + 1) * itemMargin + col * itemSize;
		float y = yOffset + (row + 1) * itemMargin + row * itemSize;

		getLocation().set(x, y);

		updateView();

		listenInput(false);

		super.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				startLevel();
			}
		});
	}
	
	public void updateView() {
		if (level.getState() == LevelMeta.Locked)
			setTexture(Textures.locked);
		if (level.getState() == LevelMeta.Unlocked)
			setTexture(Textures.unlocked);
		if (level.getState() == LevelMeta.Star1)
			setTexture(Textures.star_empty);
		if (level.getState() == LevelMeta.Star2)
			setTexture(Textures.star_half);
		if (level.getState() == LevelMeta.Star3)
			setTexture(Textures.star_full);
	}

	@Override
	public boolean ignoreViewport() {
		return false;
	}

	private void startLevel() {
		if (level.getState() != LevelMeta.Locked) {
			LevelManager.startLevel(level);
		}
	}
}
