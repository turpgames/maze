package com.turpgames.maze.controller;

import com.turpgames.box2d.Box2D;
import com.turpgames.box2d.IWorld;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.maze.model.objects.Maze;
import com.turpgames.maze.model.objects.MazeRotator;
import com.turpgames.maze.view.IScreenView;

public class GameController2 {
	private final static int[][] testMaze = new int[][] {
			new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			new int[] { 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			new int[] { 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1 },
			new int[] { 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1 },
			new int[] { 1, 2, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1 },
			new int[] { 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1 },
			new int[] { 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1 },
			new int[] { 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1 },
			new int[] { 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1 },
			new int[] { 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
			new int[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
	};

	private final IScreenView view;
	private final IWorld world;

	private Maze maze;
	private MazeRotator rotator;

	public GameController2(IScreenView view) {
		this.view = view;
		this.world = Box2D.createWorld();

		view.registerDrawable(new IDrawable() {
			public void draw() {
				world.drawDebug();
			}
		}, Game.LAYER_DIALOG);
	}

	public void activate() {
		maze = new Maze(testMaze, 1, 1, world);
		rotator = new MazeRotator(maze);
		view.registerDrawable(maze, Game.LAYER_GAME);
		rotator.register(view);
	}

	public void deactivate() {
		view.unregisterDrawable(maze);
		rotator.unregister(view);
	}

	public void update() {
		if (rotator.isRotating())
			world.setGravity(0, 0);
		else
			world.setGravity(0, -1000);
		world.update();
	}

	public void resetGame() {
		
	}

	public void openDescriptionDialog() {
		
	}
}
