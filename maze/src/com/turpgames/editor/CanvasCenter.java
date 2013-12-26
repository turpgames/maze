package com.turpgames.editor;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Game;

public class CanvasCenter extends GameObject {
	private List<CanvasObject> children;
	
	public CanvasCenter(List<CanvasObject> children) {
		this(children, Game.getVirtualWidth() / 2, Game.getVirtualHeight());
	}
	
	public CanvasCenter(List<CanvasObject> children, float originX, float originY) {
		this.children = new ArrayList<CanvasObject>();
		this.children.addAll(children);
		
		getRotation().origin.x = originX;
		getRotation().origin.y = originY;
		
		for (CanvasObject obj : children)
			obj.anchorRotation(getRotation());
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}
}
