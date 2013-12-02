package com.turpgames.maze.model;

public class Map {
	private int mapIndex;
	private int[][] data;
	private int[][][] portalData;

	private Map(int mapIndex) {
		this.mapIndex = mapIndex;
	}

	public int getLevel() {
		return mapIndex;
	}

	public int[][] getData() {
		return data;
	}

	public int[][][] getPortalData() {
		return portalData;
	}

	public static Map loadCurrentLevel() {
		Map map = new Map(GameOptions.currentLevel);

		map.data = new int[][] { 
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 1, 1 }, 
				{ 1, 0, 0, 0, 0, 1, 0, 0, 0, 1 }, 
				{ 1, 1, 1, 1, 0, 1, 0, 0, 1, 1 },
				{ 1, 0, 0, 0, 0, 1, 3, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 1, 0, 0, 0, 1 }, 
				{ 1, 0, 1, 1, 1, 1, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } 
			};

		map.portalData = new int[][][] { 
				{ { 2, 4 }, { 5, 1 } }, 
				{ { 5, 4 }, { 7, 1 } } 
			};

		return map;
	}
}
