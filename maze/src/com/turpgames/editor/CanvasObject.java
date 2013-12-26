package com.turpgames.editor;

import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.maze.model.blocks.BlockObject;

public class CanvasObject extends BlockObject {
	private boolean selected;
	private Editor parent;
	
	public CanvasObject(Editor parent, int xIndex, int yIndex) {
		this(parent, BlockObject.NONE, xIndex, yIndex);
	}

	public CanvasObject(Editor parent, int type, int xIndex, int yIndex) {
		this(type, Editor.tx + xIndex * Editor.blockWidth, Editor.ty + yIndex * Editor.blockHeight);
		this.parent = parent;
	}
	
	private CanvasObject(int type, float x, float y) {
		super(type, x, y);
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
		Game.getInputManager().register(this, Game.LAYER_GAME);
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
}
