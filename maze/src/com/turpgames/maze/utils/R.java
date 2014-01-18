package com.turpgames.maze.utils;

import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.FontManager;

public final class R {
	/**
	 * game.xml yapýsýndaki id ve key'ler
	 */
	public static final class game {
		public static final class controls {
			public static final class mainMenu {
				public static final String btnSound = "btnSound";
			}
		}

		public static final class forms {
			public static final String mainMenu = "mainMenu";
			public static final String playMenu = "playMenu";
		}

		public static final class screens {
			public static final String menu = "menu";
			public static final String practice = "practice";
			public static final String mazeTest = "maze-test";
		}

		public static final class sounds {
			public static final String error = "error";
			public static final String success = "success";
			public static final String timeUp = "time-up";
			public static final String wait = "wait";
			public static final String flip = "flip";
		}

		public static final class textures {

			public static final String trap = "trap";
			public static final String wall = "wall";
			public static final String objective = "objective";
			
			public static String rotationSignTemp = "rotationSignTemp";
			public static String rotationSignLeft = "rotationSignLeft";
			public static String rotationSignRight = "rotationSignRight";
			
			public static String lokum = "lokum";

		}
		
		public static final class animations {
			public static final String fellOnTrap = "fell_on_trap";
			public static final String fellOnObjective = "fell_on_objective";
			public static final String fellOnPortal = "fell_on_portal";
		}
	}

	public static final class settings {
		public static final String music = "music";
		public static final String sound = "sound";
		public static final String vibration = "vibration";

		public static final String language = "language";
		public static final String country = "country";
	}

	public static final class colors {
		public static final Color white = Color.fromHex("#ffffffff");
		public static final Color black = Color.fromHex("#000000ff");
		public static final Color red = Color.fromHex("#d0583bff");
		public static final Color green = Color.fromHex("#56bd89ff");
		public static final Color blue = Color.fromHex("#3974c1ff");
		public static final Color yellow = Color.fromHex("#f9b000ff");
		public static final Color cyan = Color.fromHex("#00f9b0ff");
		public static final Color magenta = Color.fromHex("#f900b0ff");

		public static final Color buttonDefault = white;
		public static final Color buttonTouched = yellow;
	}

	public static final class fontSize {
		public static final float xSmall = FontManager.defaultFontSize * 0.5f;
		public static final float small = FontManager.defaultFontSize * 0.625f;
		public static final float medium = FontManager.defaultFontSize * 0.75f;
		public static final float large = FontManager.defaultFontSize * 1f;
		public static final float xLarge = FontManager.defaultFontSize * 1.25f;
	}

	public static final class ui {
		public final static float imageButtonWidth = 64;
		public final static float imageButtonHeight = 64;
		public final static float flagButtonWidth = 128;
		public final static float flagButtonHeight = 128;
		public final static float flagControlButtonWidth = 64;
		public final static float flagControlButtonHeight = 64;
		public final static float libgdxLogoWidth = 200;
		public final static float libgdxLogoHeight = 33;
		public final static float rotationSignWidth = 64;
		public final static float rotationSignHeight = 64;
	}

	public static final class strings {
	
	}

	private R() {
	}
}