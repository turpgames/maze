package com.turpgames.editor;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Rotation;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.maze.model.MazeGameObject;

public class CanvasCenter extends MazeGameObject {	
	private static final float radius = 5f;
	
	private Editor parent;
	private int xIndex;
	private int yIndex;
	private List<CanvasObject> children;

	private boolean isActive;
	public CanvasCenter(Editor parent, int xIndex, int yIndex) {
		this.parent = parent;
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		this.children = new ArrayList<CanvasObject>();
		
		float originX = Editor.tx + (xIndex + 1) * Editor.blockWidth;
		float originY = Editor.ty + (yIndex + 1) * Editor.blockHeight;
		
		getRotation().origin.x = originX;
		getRotation().origin.y = originY;
		getLocation().set(originX, originY);
		listenInput(true);
	}

	public int getXIndex() {
		return xIndex;
	}
	
	public int getYIndex() {
		return yIndex;
	}
	
	public void setChildren(List<CanvasObject> children) {
		this.children.clear();
		this.children.addAll(children);
	}
	
//	public void anchorChildren() {
//		for (CanvasObject obj : children)
//			obj.anchorRotation(getRotation());
//	}
//	
//	public void deanchorChildren() {
//		for (CanvasObject obj : children)
//			obj.deanchorRotation();
//	}
	
	public void activate() {
		if (children.size() == 0)
			return;
		Rotator.instance.registerCanvasCenter(this);
		isActive = true;
	}
	
	public void deactivate() {
		Rotator.instance.unregisterCanvasCenter(this);
		isActive = false;
	}
	
	@Override
	public void draw() {
		if (isActive)
			ShapeDrawer.drawCircle(getLocation().x, getLocation().y, radius + 3, Color.green(), true, false);
		else if (isTouched())
			ShapeDrawer.drawCircle(getLocation().x, getLocation().y, radius + 3, Color.white(), true, false);
		else
			ShapeDrawer.drawCircle(getLocation().x, getLocation().y, radius, Color.blue(), true, false);
	}
	
	@Override
	public void registerSelf() {
		Game.getInputManager().register(this, Editor.LAYER_CANVAS_CENTERS);
	}
	
	@Override
	public boolean tap(float x, float y, int count, int button) {
		float cx = getLocation().x;
		float cy = getLocation().y;
		if (!ignoreViewport()) {
			x = Game.screenToViewportX(x);
			y = Game.screenToViewportY(y);
		}

		return (x - cx) * (x - cx) + (y - cy) * (y - cy) < radius * radius ? onTap() : false;
	}
	
	@Override
	protected boolean onTap() {
		parent.canvasCenterTapped(this);
		return true;
	}

	public boolean isActive() {
		return isActive;
	}

	public void finishedRotating(int rotationDirection) {
		parent.canvasCenterRotated(this, rotationDirection);
	}

	public List<CanvasObject> getChildren() {
		return children;
	}
	
	@Override
	public void anchorRotation(Rotation rotation) {
		getRotation().angle = rotation.angle;
		for (CanvasObject obj : children)
			obj.anchorRotation(getRotation());
	}
	
	@Override
	public void deanchorRotation() {
		for (CanvasObject obj : children)
			obj.deanchorRotation();
		super.deanchorRotation();
	}
}
