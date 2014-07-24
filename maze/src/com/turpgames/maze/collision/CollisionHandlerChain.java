package com.turpgames.maze.collision;

import java.util.ArrayList;
import java.util.List;


public class CollisionHandlerChain implements ICollisionHandler {
	private final List<ICollisionHandler> handlerChain;

	public CollisionHandlerChain(ICollisionHandler... handlers) {
		this.handlerChain = new ArrayList<ICollisionHandler>();
		for (ICollisionHandler handler : handlers)
			add(handler);
	}

	public void add(ICollisionHandler handler) {
		handlerChain.add(handler);
	}

	public void remove(ICollisionHandler handler) {
		handlerChain.remove(handler);
	}

	@Override
	public boolean onBeginCollide(Object b1, Object b2) {
		for (ICollisionHandler handler : handlerChain) {
			if (handler.onBeginCollide(b1, b2)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onEndCollide(Object b1, Object b2) {
		for (ICollisionHandler handler : handlerChain) {
			if (handler.onEndCollide(b1, b2)) {
				return true;
			}
		}
		return false;
	}
}
