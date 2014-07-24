package com.turpgames.editor;

import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;

public class CentersButton extends Button {

	public static final float radius = Editor.blockWidth;
	
	private Editor parent;
	public CentersButton(Editor parent, float x, float y) {
		this.parent = parent;
		setWidth(radius * 2);
		setHeight(radius * 2);
		getLocation().set(x, y);
		activate();
		
	}
	
	@Override
	protected void onDraw() {
		ShapeDrawer.drawCircle(getLocation().x + radius, getLocation().y + radius, radius, Color.blue(), true, false);	
	}
	
	@Override
	public void registerSelf() {
		Game.getInputManager().register(this, Editor.LAYER_TOOLBAR);
	}
	
	@Override
	protected boolean onTap() {
		parent.centerButtonTapped();
		return true;
	}
}
