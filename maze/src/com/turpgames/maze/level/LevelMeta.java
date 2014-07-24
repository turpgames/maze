package com.turpgames.maze.level;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.physics.box2d.ContactListener;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.maze.model.blocks.BlockObject;
import com.turpgames.maze.utils.GameSettings;

public class LevelMeta {

	public final static int Locked = -1;
	public final static int Unlocked = 0;
	public final static int Star1 = 1;
	public final static int Star2 = 2;
	public final static int Star3 = 3;

	private final String id;
	private final int index;
	private final BlockMeta[] blocks;
	private final ContactListener contactListener;
	private final int star1;
	private final int star2;
	private final int star3;
	private int state;

	private LevelMeta(Builder builder) {
		this.id = builder.id;
		this.index = builder.index;
		this.star1 = builder.star1;
		this.star2 = builder.star2;
		this.star3 = builder.star3;
		this.blocks = builder.blocks.toArray(new BlockMeta[0]);
		this.contactListener = builder.contactListener;
		this.state = Settings.getInteger(id, Locked);
	}

	public void updateState(int state) {
		this.state = state;
		Settings.putInteger(id, state);
	}

	public int getState() {
		return state;
	}

	public int getIndex() {
		return index;
	}

	public BlockMeta[] getBlocks() {
		return blocks;
	}

	public ContactListener getContactListener() {
		return contactListener;
	}

	public int getStar1() {
		return star1;
	}

	public int getStar2() {
		return star2;
	}

	public int getStar3() {
		return star3;
	}

	public static Builder newBuilder(String id) {
		return new Builder(id);
	}

	public static class Builder {
		private final List<BlockMeta> blocks;
		private final String id;
		private int index;
		private int star1;
		private int star2;
		private int star3;
		private ContactListener contactListener;

		private Builder(String id) {
			this.id = id;
			this.blocks = new ArrayList<BlockMeta>();
		}

		public Builder setIndex(int index) {
			this.index = index;
			return this;
		}

		public Builder setContactListener(ContactListener listener) {
			this.contactListener = listener;
			return this;
		}

		public Builder setScoreMeta(int star3, int star2, int star1) {
			this.star3 = star3;
			this.star2 = star2;
			this.star1 = star1;
			return this;
		}

		public Builder addBlocks(int[][] data) {
			int cols = data[0].length;
			int rows = data.length;
			int mazeWidth = cols * GameSettings.blockWidth;
			int mazeHeight = rows * GameSettings.blockHeight;

			float tx = (Game.getVirtualWidth() - mazeWidth) / 2;
			float ty = (Game.getVirtualHeight() - mazeHeight) / 2;
			
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					if (data[i][j] != BlockObject.NONE) {
						BlockMeta block = new BlockMeta(data[i][j], tx + j * GameSettings.blockWidth, ty + (rows - 1 - i) * GameSettings.blockHeight, GameSettings.blockWidth, GameSettings.blockHeight, tx + mazeWidth / 2, ty + mazeHeight / 2);
						blocks.add(block);
					}
				}
			}
			return this;
		}

		public LevelMeta build() {
			return new LevelMeta(this);
		}
	}
}
