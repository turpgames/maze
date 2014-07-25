package com.turpgames.maze;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSDictionary;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.foundation.NSURL;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.bindings.facebook.manager.FacebookManager;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.turpgames.framework.v0.impl.ios.IOSProvider;
import com.turpgames.framework.v0.impl.libgdx.GdxGame;
import com.turpgames.framework.v0.util.Game;

public class RobovmLauncher extends IOSApplication.Delegate {
	private IOSProvider provider;

	@Override
	protected IOSApplication createApplication() {
		IOSApplicationConfiguration config = new IOSApplicationConfiguration();
		config.orientationLandscape = false;
		config.orientationPortrait = true;
		config.allowIpod = true;

		provider = new IOSProvider();
		Game.setEnvironmentProvider(provider);

		return new IOSApplication(new GdxGame(), config);
	}

	public static void main(String[] argv) {
		NSAutoreleasePool pool = new NSAutoreleasePool();
		UIApplication.main(argv, null, RobovmLauncher.class);
		pool.close();
	}

	@Override
	public boolean openURL(UIApplication application, NSURL url, String sourceApplication, NSObject annotation) {
		return FacebookManager.getInstance().openURL(application, url, sourceApplication, annotation);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean didFinishLaunching(UIApplication application, NSDictionary launchOptions) {
		boolean res = super.didFinishLaunching(application, launchOptions);
	    provider.setRootViewController(UIApplication.getSharedApplication().getKeyWindow().getRootViewController());
		return res;
	}
}