package com.turpgames.maze.level;

public class BlockMeta {
	private final int type;
	private final float x;
	private final float y;
	private final float width;
	private final float height;
	private final float rotationOriginX;
	private final float rotationOriginY;

	public BlockMeta(int type, float x, float y, float width, float height, float rotationOriginX, float rotationOriginY) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rotationOriginX = rotationOriginX;
		this.rotationOriginY = rotationOriginY;
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

	public float getRotationOriginX() {
		return rotationOriginX;
	}

	public float getRotationOriginY() {
		return rotationOriginY;
	}
}
