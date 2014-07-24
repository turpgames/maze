package com.turpgames.maze.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class DefaultContactListener implements ContactListener {
	private final ICollisionHandler collisionHandler;
	
	public DefaultContactListener(ICollisionHandler collisionHandler) {
		this.collisionHandler = collisionHandler;
	}
	
	@Override
	public void endContact(Contact contact) {
		Object o1 = contact.getFixtureA().getBody().getUserData();
		Object o2 = contact.getFixtureB().getBody().getUserData();

		if (o1 == null || o2 == null) {
			return;
		}

		collisionHandler.onEndCollide(o1, o2);
	}

	@Override
	public void beginContact(Contact contact) {
		Object o1 = contact.getFixtureA().getBody().getUserData();
		Object o2 = contact.getFixtureB().getBody().getUserData();

		if (o1 == null || o2 == null) {
			return;
		}

		collisionHandler.onBeginCollide(o1, o2);
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

}
