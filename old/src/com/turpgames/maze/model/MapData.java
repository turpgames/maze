package com.turpgames.maze.model;

import java.util.List;
import java.util.Map;

import com.turpgames.editor.CanvasCenter;
import com.turpgames.editor.CanvasObject;
import com.turpgames.editor.Coordinates;

public class MapData {
	Map<Coordinates, CanvasObject> objects;
	List<CanvasCenter> centers;
	
	public MapData(Map<Coordinates, CanvasObject> objects, List<CanvasCenter> centers) {
		this.objects = objects;
		this.centers = centers;
	}
}
