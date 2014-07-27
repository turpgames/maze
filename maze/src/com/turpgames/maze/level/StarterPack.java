package com.turpgames.maze.level;

import com.turpgames.maze.collision.CollisionHandlerChain;
import com.turpgames.maze.collision.DefaultContactListener;

public class StarterPack {
	private final static String packTitle = "Starter Pack";
	
	private static LevelPack pack;
	
	public static LevelPack getPack() {
		if (pack == null)
			createPack();
		return pack;
	}
	
	public static void createPack() {
		LevelMeta level1 = level1();

		if (level1.getState() == LevelMeta.Locked)
			level1.setState(LevelMeta.Unlocked);

		pack = LevelPack.newBuilder()
				.setTitle(packTitle)
				.addLevel(level1)
				.build();

		for (int i = 0; i < pack.getLevels().length; i++)
			pack.getLevels()[i].setState(Math.max(LevelMeta.Star3 - i, LevelMeta.Unlocked));

		for (LevelMeta level : pack.getLevels()) {
			level.setPack(pack);
		}
	}

	private static LevelMeta level1() {
		return newBuilder(1)
				.setScoreMeta(0, 0, 0)
				.addBlocks(new int[][] { 
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 1, 2, 0, 0, 1 }, 
				{ 1, 1, 1, 1, 0, 1, 0, 0, 1, 1 },
				{ 1, 0, 0, 0, 0, 1, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 1, 0, 0, 0, 1 }, 
				{ 1, 0, 1, 1, 1, 1, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } 
			})
				.build();
	}

	private static LevelMeta.Builder newBuilder(int index) {
		return LevelMeta.newBuilder(packTitle + index)
				.setIndex(index)
				.setContactListener(new DefaultContactListener(
						new CollisionHandlerChain()));
	}
}
