package com.turpgames.maze.collision;

import com.turpgames.maze.level.Level.Block;

public abstract class BlockCollisionHandler implements ICollisionHandler {

	@Override
	public boolean onBeginCollide(Object b1, Object b2) {
		return handleBeginCollide((Block)b1, (Block)b2);
	}

	@Override
	public boolean onEndCollide(Object b1, Object b2) {
		return handleEndCollide((Block)b1, (Block)b2);
	}

	protected boolean handleBeginCollide(Block b1, Block b2) {
		return true;
	}

	protected boolean handleEndCollide(Block b1, Block b2) {
		return true;
	}
}
