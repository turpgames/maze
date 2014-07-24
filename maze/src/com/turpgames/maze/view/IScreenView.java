package com.turpgames.maze.view;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.IInputListener;

public interface IScreenView {
	void registerDrawable(IDrawable idrawable, int i);

	void registerInputListener(IInputListener iinputlistener);

	void unregisterDrawable(IDrawable idrawable);

	void unregisterInputListener(IInputListener iinputlistener);
}
