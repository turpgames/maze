package com.turpgames.editor;

import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.maze.model.blocks.BlockObject;

public class CanvasObject extends BlockObject {
	private boolean selected;
	private Editor parent;
	private Coordinates coordinates;
	
	public CanvasObject(Editor parent, int xIndex, int yIndex) {
		this(parent, BlockObject.NONE, xIndex, yIndex);
	}
	
	private CanvasObject(Editor parent, int type, int xIndex, int yIndex) {
		super(type, Editor.tx + xIndex * Editor.blockWidth, Editor.ty + yIndex * Editor.blockHeight);
		this.parent = parent;
		this.coordinates = new Coordinates(xIndex, yIndex);
		setWidth(Editor.blockWidth);
		setHeight(Editor.blockHeight);
		listenInput(true);
	}

	@Override
	public void draw() {
		super.draw();
		if (type == BlockObject.NONE)
			ShapeDrawer.drawRect(this, false);
		if (selected)
			ShapeDrawer.drawRect(
					getLocation().x + getWidth() / 4, 
					getLocation().y + getHeight() / 4, 
					getWidth() - getWidth() / 2, 
					getHeight() - getHeight() / 2, Color.green(), false, false);
	}
	
	@Override
	public void registerSelf() {
		Game.getInputManager().register(this, Editor.LAYER_CANVAS_GRID);
	}
	
	@Override
	protected boolean onTap() {
		parent.canvasObjectTapped(this);
		return true;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}
	
	public void updateCoordinates(int x, int y) {
		this.coordinates.setX(x);
		this.coordinates.setY(y);

		getLocation().x = Editor.tx + coordinates.getX() * Editor.blockWidth;
		getLocation().y = Editor.ty + coordinates.getY() * Editor.blockHeight;
	}

	public boolean indicesBetween(int minX, int maxX, int minY, int maxY) {
		return coordinates.getX() >= minX && coordinates.getX() <= maxX && coordinates.getY() >= minY && coordinates.getY() <= maxY;
	}
}
