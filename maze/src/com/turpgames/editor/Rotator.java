package com.turpgames.editor;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.maze.controller.level.MazeMover;

public class Rotator extends GameObject {
	private static final float width = 3 * Game.getVirtualWidth() / 4;
	private static final float height = 50;

	private final static float maxTemp = 35;
	private final static float minTemp = 15;
	
	private float oldRotation; 					// rotation before the user input starts
	private float rotateStartX, rotateStartY; 	// rotation start point coordinates
	private float tempRotation; 				// current temporary rotation
	private int rotationDirection; 				// -1 (right) or 1 (left)
	private float complementaryAngle; 			// angle of rotation that completes user rotation to 90 degrees.
	private float rotationSpeed = 120; 			// degrees per second
	
	private float epsilon = 0.1f;
	
	private List<CanvasCenter> centers;
	private boolean working;
	
	public static Rotator instance = new Rotator();
	
	private Rotator() {
		this.centers = new ArrayList<CanvasCenter>();
		this.working = false;
		setWidth(width);
		setHeight(height);
		getLocation().set((Game.getVirtualWidth() - getWidth()) / 2, 100);
		getColor().set(Color.blue());
	}

	@Override
	public void draw() {
		ShapeDrawer.drawRect(this, false);
		if (working)
			mazeRotate(rotationSpeed * Game.getDeltaTime());
	}
	
	public void registerCanvasCenter(CanvasCenter center) {
		centers.add(center);
	}
	
	public void unregisterCanvasCenter(CanvasCenter center) {
		centers.remove(center);
	}
	
	public void mazeRotate(float increment) {
		for (CanvasCenter c : centers)
			c.getRotation().angle.z += rotationDirection * increment;
		complementaryAngle -= increment; // Keep track of remaining rotation.
		if (complementaryAngle <= 0 + epsilon) { // MAZE_ROTATE FINISHED
			for (CanvasCenter c : centers)
				c.getRotation().angle.z = (oldRotation + rotationDirection * 90);
			MazeMover.instance.turn(rotationDirection == 1);

			working = false;
		}
	}
	
	@Override
	public boolean onTouchDown(float x, float y) {
		oldRotation = getRotation().angle.z;
		this.rotateStartX = x;
		this.rotateStartY = y;
		this.tempRotation = 0;
		return false;
	}
	
	@Override
	public boolean onTouchDragged(float x, float y) {

		float sw = Game.getScreenWidth();
		float sh = Game.getScreenHeight();

		float dx = ((x - rotateStartX) / sw) * 90;
		float dy = ((y - rotateStartY) / sh) * 90;

		if (y > sh / 2)
			dx = -dx;
		if (x < sw / 2)
			dy = -dy;

		tempRotation = limitAngle(tempRotation + dx + dy);

		rotateStartX = x;
		rotateStartY = y;

		for (CanvasCenter c : centers)
			c.getRotation().angle.z = (oldRotation + tempRotation);
		return false;

	}

	private float limitAngle(float angle) {
		if (angle > maxTemp)
			angle = maxTemp;
		else if (angle < -maxTemp)
			angle = -maxTemp;
		return angle;
	}

	@Override
	public boolean onTouchUp(float x, float y) {
		if (tempRotation > minTemp)
			rotationDirection = 1;
		else if (tempRotation < -minTemp)
			rotationDirection = -1;
		else {
			tempRotation = 0;
			for (CanvasCenter c : centers)
				c.getRotation().angle.z = oldRotation;
			return false;
		}

		complementaryAngle = 90 - rotationDirection * tempRotation;
		working = true;
		return false;
	}
}
