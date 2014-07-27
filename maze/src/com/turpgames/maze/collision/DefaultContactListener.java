package com.turpgames.maze.collision;

import com.turpgames.box2d.IContact;
import com.turpgames.box2d.IContactListener;

public class DefaultContactListener implements IContactListener {
	private final ICollisionHandler collisionHandler;
	
	public DefaultContactListener(ICollisionHandler collisionHandler) {
		this.collisionHandler = collisionHandler;
	}
	
	@Override
	public void endContact(IContact contact) {
		Object o1 = contact.getFixtureA().getBody().getData();
		Object o2 = contact.getFixtureB().getBody().getData();

		if (o1 == null || o2 == null) {
			return;
		}

		collisionHandler.onEndCollide(o1, o2);
	}

	@Override
	public void beginContact(IContact contact) {
		Object o1 = contact.getFixtureA().getBody().getData();
		Object o2 = contact.getFixtureB().getBody().getData();

		if (o1 == null || o2 == null) {
			return;
		}

		collisionHandler.onBeginCollide(o1, o2);
	}
}
