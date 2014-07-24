package com.turpgames.maze.level;

import java.util.ArrayList;
import java.util.List;

public class LevelPack {
	private final LevelMeta[] levels;
	private final String title;

	private LevelPack(Builder builder) {
		this.title = builder.title;
		this.levels = builder.levels.toArray(new LevelMeta[0]);
	}

	public String getTitle() {
		return title;
	}

	public LevelMeta[] getLevels() {
		return levels;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {
		private String title;
		private final List<LevelMeta> levels;

		private Builder() {
			levels = new ArrayList<LevelMeta>();
		}

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder addLevel(LevelMeta level) {
			this.levels.add(level);
			return this;
		}

		public LevelPack build() {
			return new LevelPack(this);
		}
	}
}
