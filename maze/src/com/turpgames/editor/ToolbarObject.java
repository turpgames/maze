package com.turpgames.editor;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.maze.model.blocks.BlockObject;

public class ToolbarObject extends BlockObject {

	private Editor parent;
	
	public ToolbarObject(Editor parent, int type, float x, float y) {
		super(type, x, y);
		this.parent = parent;
		listenInput(true);
	}

	@Override
	protected boolean onTap() {
		parent.toolbarTapped(type);
		return true;
	}
	
	@Override
	public void registerSelf() {
		Game.getInputManager().register(this, Editor.LAYER_TOOLBAR);
	}
}
