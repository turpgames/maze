package com.turpgames.maze.collision;

public interface ICollisionHandler {
	boolean onBeginCollide(Object b1, Object b2);

	boolean onEndCollide(Object b1, Object b2);
}
