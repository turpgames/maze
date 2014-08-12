package com.turpgames.maze.model.objects;

import com.turpgames.box2d.Box2DObject;
import com.turpgames.box2d.IBody;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.TexturedGameObject;
import com.turpgames.maze.utils.Textures;

public class Lokum extends Box2DObject implements IDrawable {
	private final LokumObj lokumObj;

	public Lokum(IBody body, float x, float y, float size) {
		this.lokumObj = new LokumObj();
		this.lokumObj.setWidth(size);
		this.lokumObj.setHeight(size);
		this.lokumObj.getLocation().set(x, y);
		this.lokumObj.getRotation().origin.set(x + size / 2, y + size / 2);
		
		super.setContent(lokumObj, body);
	}

	@Override
	public void draw() {
		lokumObj.draw();
	}

	protected class LokumObj extends TexturedGameObject {
		public LokumObj() {
			super(Textures.lokum);
		}
	}
}
