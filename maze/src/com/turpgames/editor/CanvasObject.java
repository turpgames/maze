package com.turpgames.editor;

import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.maze.model.blocks.BlockObject;

public class CanvasObject extends BlockObject {
	private boolean selected;
	private Editor parent;
	private int xIndex;
	private int yIndex;
	
	public CanvasObject(Editor parent, int xIndex, int yIndex) {
		this(parent, BlockObject.NONE, xIndex, yIndex);
	}
	
	private CanvasObject(Editor parent, int type, int xIndex, int yIndex) {
		super(type, Editor.tx + xIndex * Editor.blockWidth, Editor.ty + yIndex * Editor.blockHeight);
		this.parent = parent;
		this.xIndex = xIndex;
		this.yIndex = yIndex;
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
					getLocation().x + getWidth() / 3, 
					getLocation().y + getHeight() / 3, 
					getWidth() - 2 * getWidth() / 3, 
					getHeight() - 2 * getHeight() / 3, Color.green(), true, false);
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
	
	public int getXIndex() {
		return xIndex;
	}
	
	public int getYIndex() {
		return yIndex;
	}
	
	public void updateIndices(int x, int y) {
		xIndex = x;
		yIndex = y;

		getLocation().x = Editor.tx + xIndex * Editor.blockWidth;
		getLocation().y = Editor.ty + yIndex * Editor.blockHeight;
	}

	public boolean indicesBetween(int minX, int maxX, int minY, int maxY) {
		return xIndex >= minX && xIndex <= maxX && yIndex >= minY && yIndex <= maxY;
	}
}
