package com.turpgames.maze.components;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.effects.fading.FadeOutEffect;
import com.turpgames.framework.v0.effects.fading.IFadingEffectSubject;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.TextureDrawer;
import com.turpgames.maze.utils.Textures;

public class TapImage implements IDrawable, IFadingEffectSubject {
	private final float dx = 47f;
	private final float dy = 109f;

	private final TapHandImage hand;
	private final TapBallImage ball;
	private final FadeOutEffect fadeOut;

	public TapImage() {
		hand = new TapHandImage();
		hand.setWidth(128f);
		hand.setHeight(128f);
		hand.getColor().a = 0;

		ball = new TapBallImage();
		ball.setWidth(80f);
		ball.setHeight(80f);
		ball.getColor().a = 0;

		fadeOut = new FadeOutEffect(this);
		fadeOut.setDuration(3.0f);
		fadeOut.setLooping(false);
		fadeOut.setMaxAlpha(2.0f);
		fadeOut.setMinAlpha(0.0f);
	}

	public void displayAt(float x, float y, float d) {
		fadeOut.stop();

		ball.getLocation().set(x - 40f, y);

		hand.flip = d < 0;

		x += d;

		hand.getRotation().setRotationZ(d);
		hand.getRotation().setOrigin(x, y);
		
		if (hand.flip) {
			hand.getLocation().set(x - 2 * dx, y - dy);
		}
		else {
			hand.getLocation().set(x - dx, y - dy);
		}

		fadeOut.start();
	}

	@Override
	public void draw() {
		ball.draw();
		hand.draw();
	}

	@Override
	public void setAlpha(float alpha) {
		hand.getColor().a = alpha;
		ball.getColor().a = alpha;
	}

	private class TapHandImage extends GameObject {
		@Override
		public void draw() {
			TextureDrawer.draw(Textures.tap, this);
		}

		boolean flip;

		@Override
		public boolean isFlipX() {
			return flip;
		}
	}

	private class TapBallImage extends GameObject {
		@Override
		public void draw() {
			TextureDrawer.draw(Textures.ball_blue, this);
		}
	}
}
