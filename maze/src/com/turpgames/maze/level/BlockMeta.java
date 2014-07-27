package com.turpgames.maze.level;

public class BlockMeta {
	private final int type;
	private final float x;
	private final float y;
	private final float width;
	private final float height;

	public BlockMeta(int type, float x, float y, float width, float height) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getType() {
		return type;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
}
