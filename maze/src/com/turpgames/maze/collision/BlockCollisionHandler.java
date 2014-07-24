package com.turpgames.maze.collision;

import com.turpgames.maze.model.blocks.BlockObject;

public abstract class BlockCollisionHandler implements ICollisionHandler {

	@Override
	public boolean onBeginCollide(Object b1, Object b2) {
		return handleBeginCollide((BlockObject)b1, (BlockObject)b2);
	}

	@Override
	public boolean onEndCollide(Object b1, Object b2) {
		return handleEndCollide((BlockObject)b1, (BlockObject)b2);
	}

	protected boolean handleBeginCollide(BlockObject b1, BlockObject b2) {
		return true;
	}

	protected boolean handleEndCollide(BlockObject b1, BlockObject b2) {
		return true;
	}
}
