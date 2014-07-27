package com.turpgames.maze.components;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Drawer;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;

public class LevelResetEffect implements IDrawable {
	public static interface IListener {
		void onHalfCompleted();
	}

	private final static float duration = 1f;

	private final EffectObj obj;
	private final IListener listener;
	private float elapsed;
	private boolean passedHalf;

	public LevelResetEffect(IListener listener) {
		obj = new EffectObj();
		obj.setWidth(Game.getScreenWidth());
		obj.setHeight(Game.getScreenHeight());
		obj.getLocation().set(0, 0);
		obj.getColor().set(0);
		this.listener = listener;
	}

	public void start() {
		elapsed = 0;
		passedHalf = false;
		Drawer.getCurrent().register(this, Game.LAYER_INFO);
	}

	public void stop() {
		Drawer.getCurrent().unregister(this);
	}

	@Override
	public void draw() {
		elapsed += Game.getDeltaTime();

		float progress = elapsed / duration;
		if (progress <= 0.5f) {
			obj.getColor().a = (float) Math.sqrt(progress * 2);
		}
		else if (progress <= 1f) {
			obj.getColor().a = (float) Math.sqrt((1 - progress) * 2);
			if (!passedHalf) {
				passedHalf = true;
				listener.onHalfCompleted();
			}
		}
		else {
			Drawer.getCurrent().unregister(this);
		}
		
		obj.draw();
	}

	private class EffectObj extends GameObject {
		@Override
		public void draw() {
			ShapeDrawer.drawRect(this, true);
		}
		
		@Override
		public boolean ignoreViewport() {
			return true;
		}
	}
}
