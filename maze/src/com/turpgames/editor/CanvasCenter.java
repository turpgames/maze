package com.turpgames.editor;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;

public class CanvasCenter extends GameObject {	
	private static final float radius = 5f;
	
	private Editor parent;
	private int xIndex;
	private int yIndex;
	private List<CanvasObject> children;

	private boolean isActive;
	private boolean isTurnedOn;
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
	
	public void activate() {
		isActive = true;
		listenInput(true);
	}
	
	public void deactivate() {
		isActive = false;
		listenInput(false);
	}
	
	@Override
	public void draw() {
		if (isTouched())
			ShapeDrawer.drawCircle(getLocation().x, getLocation().y, radius + 3, Color.white(), true, false);
		else if (isTurnedOn)
			ShapeDrawer.drawCircle(getLocation().x, getLocation().y, radius + 2, Color.blue(), true, false);
		else if (isActive)
			ShapeDrawer.drawCircle(getLocation().x, getLocation().y, radius, Color.white(), true, false);
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

	public List<CanvasObject> getChildren() {
		return children;
	}

	public void turnOn() {
		if (children.size() == 0)
			return;
		isTurnedOn = true;
	}

	public void turnOff() {
		isTurnedOn = false;
	}

	public void switchActivated() {
		if (isActive)
			deactivate();
		else
			activate();
	}

	public boolean isTurnedOn() {
		return isTurnedOn;
	}
}
