package com.turpgames;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.turpgames.framework.v0.IEnvironmentProvider;
import com.turpgames.framework.v0.impl.libgdx.GdxGame;
import com.turpgames.framework.v0.social.ISocializerFactory;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Version;

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

		Game.setEnvironmentProvider(new IEnvironmentProvider() {
			@Override
			public Version getAppVersion() {
				return new Version("0.0.1");
			}
			
			@Override
			public Version getOsVersion() {
				return new Version(System.getProperty("os.version"));
			}

			@Override
			public ISocializerFactory createSocializerFactory() {
				return null;
			}		
		});
		
		new LwjglApplication(new GdxGame(), cfg);
	}
}
