package com.blox.maze.util;

import com.blox.framework.v0.util.AnimationInfo;
import com.blox.maze.model.Mazeda;

public final class R {
	private R() {
	}

	public static final class animations {
		public static final class Block {
			public static final AnimationInfo def = new AnimationInfo("Block", "turnmaze/body_full40-2.png", 1, Mazeda.blockWidth, Mazeda.blockHeight, false);
		}

		public static final class Lokum {
			public static final AnimationInfo def = new AnimationInfo("Lokum", "turnmaze/lokum.png", 0.1f, Mazeda.blockWidth, Mazeda.blockHeight, false);
			public static final AnimationInfo onTrap = new AnimationInfo("FellOnTrap", "turnmaze/lokumtrap.png", 0.15f, Mazeda.blockWidth, Mazeda.blockHeight, false);
			public static final AnimationInfo onObjective = new AnimationInfo("FellOnObjective", "turnmaze/lokumobj.png", 0.1f, Mazeda.blockWidth, Mazeda.blockHeight, false);
			public static final AnimationInfo onPortal = new AnimationInfo("FellOnPortal", "turnmaze/lokum.png", 0.15f, Mazeda.blockWidth, Mazeda.blockHeight, false);
		}

		public static final class PortalDoor {
			private static final float frameDuration = 0.3f;

			public static final String defName = "Portal";
			public static final String enterName = "EnterDoor";
			public static final String exitName = "ExitDoor";

			public static final AnimationInfo defBlue = new AnimationInfo(defName, "turnmaze/portalblue.png", frameDuration, Mazeda.blockWidth, Mazeda.blockHeight, false);
			public static final AnimationInfo defGreen = new AnimationInfo(defName, "turnmaze/portalgreen.png", frameDuration, Mazeda.blockWidth, Mazeda.blockHeight, false);

			public static final AnimationInfo enterBlue = new AnimationInfo(enterName, "turnmaze/portalenterblue.png", frameDuration, Mazeda.blockWidth, Mazeda.blockHeight, false);
			public static final AnimationInfo enterGreen = new AnimationInfo(enterName, "turnmaze/portalentergreen.png", frameDuration, Mazeda.blockWidth, Mazeda.blockHeight, false);

			public static final AnimationInfo exitBlue = new AnimationInfo(exitName, "turnmaze/portalexitblue.png", frameDuration, Mazeda.blockWidth, Mazeda.blockHeight, false);
			public static final AnimationInfo exitGreen = new AnimationInfo(exitName, "turnmaze/portalexitgreen.png", frameDuration, Mazeda.blockWidth, Mazeda.blockHeight, false);
		}

		public static final class Objective {
			public static final AnimationInfo def = new AnimationInfo("Objective", "turnmaze/door.gif", 1, Mazeda.blockWidth, Mazeda.blockHeight, false);
		}

		public static final class Background {
			public static final AnimationInfo zeroth = new AnimationInfo("bg", "screen2.jpg", 1, 480, 800, false);
			public static final AnimationInfo first = new AnimationInfo("bg", "screen1.jpg", 1, 480, 800, false);
			public static final AnimationInfo second = new AnimationInfo("bg", "screen2.jpg", 1, 480, 800, false);
		}
	}

	public static final class menus {
		public static final class main {
			public static final String xmlPath = "menus/mainMenu.xml";
			public static final String btnNewGame = "btnNewGame";
		}
	}
	
	public static final class textures {
		public static final String buttonLightBlue = "btn-lightblue.png";
		public static final String buttonPink = "btn-pink.png";		
	}
}