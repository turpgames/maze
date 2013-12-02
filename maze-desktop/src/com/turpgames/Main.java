package com.turpgames;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.turpgames.framework.v0.impl.libgdx.GdxGame;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "blox";
		cfg.useGL20 = false;

		float w = 11;
		float h = 16;
		float x = 50;

		cfg.width = (int) (x * w);
		cfg.height = (int) (x * h);

		new LwjglApplication(new GdxGame(), cfg);
	}
}
