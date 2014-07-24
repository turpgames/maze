package com.turpgames.maze.components;

import com.turpgames.entity.Player;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.maze.utils.R;

public class ResultView implements IDrawable {
	public static interface IListener {
		public abstract void onRestartGame();

		public abstract void onShareScore();
	}

	private final static String loginWithFacebook = "Login with Facebook";
	private final static String shareScore = "Share Score";

	private final TextButton restartButton;
	private final TextButton shareScoreButton;
	private final TextButton aboutButton;
	private final TextButton hiScoresButton;
	private final GameLogo logo;

	private boolean hideShareScoreButton;

	public ResultView(final IListener listener) {
		float y = Game.getVirtualHeight() / 2.0F;
		shareScoreButton = createButton(shareScore, y + 90f, new IButtonListener() {
			public void onButtonTapped() {
				if (shareScoreButton.getText().equals(shareScore))
					listener.onShareScore();
				else
					TurpClient.loginWithFacebook(new ICallback() {
						@Override
						public void onSuccess() {
							shareScoreButton.setText(shareScore);
							setLocation(shareScoreButton, shareScoreButton.getLocation().y);
						}

						@Override
						public void onFail(Throwable t) {
							
						}
					});
			}
		});

		restartButton = createButton("Play Again", y - 180f, new IButtonListener() {
			public void onButtonTapped() {
				listener.onRestartGame();
			}
		});

		aboutButton = createButton("About", y, new IButtonListener() {
			public void onButtonTapped() {
				ScreenManager.instance.switchTo(R.screens.about, false);
			}
		});

		hiScoresButton = createButton("Hi Scores", y - 90f, new IButtonListener() {
			public void onButtonTapped() {
				ScreenManager.instance.switchTo(R.screens.hiscores, false);
			}
		});

		logo = new GameLogo();
	}

	private static TextButton createButton(String s, float f, IButtonListener ibuttonlistener) {
		TextButton textbutton = new TextButton(Color.white(), R.colors.yellow);
		textbutton.setListener(ibuttonlistener);
		textbutton.setText(s);
		textbutton.deactivate();

		setLocation(textbutton, f);
		
		return textbutton;
	}
	
	private static void setLocation(TextButton btn, float y) {
		btn.getLocation().set((Game.getVirtualWidth() - btn.getWidth()) / 2.0F, y);
	}

	public void activate() {
		restartButton.activate();
		shareScoreButton.activate();
		aboutButton.activate();
		hiScoresButton.activate();
		hideShareScoreButton = false;
		if (!TurpClient.isRegistered() || TurpClient.getPlayer().getAuthProvider() == Player.AuthAnonymous)
			shareScoreButton.setText(loginWithFacebook);
		else
			shareScoreButton.setText(shareScore);
		setLocation(shareScoreButton, shareScoreButton.getLocation().y);
	}

	public void deactivate() {
		restartButton.deactivate();
		shareScoreButton.deactivate();
		aboutButton.deactivate();
		hiScoresButton.deactivate();
	}

	public void draw() {
		restartButton.draw();
		if (!hideShareScoreButton)
			shareScoreButton.draw();
		aboutButton.draw();
		hiScoresButton.draw();
		logo.draw();
	}

	public void hideShareScoreButton() {
		shareScoreButton.deactivate();
		hideShareScoreButton = true;
	}
}
