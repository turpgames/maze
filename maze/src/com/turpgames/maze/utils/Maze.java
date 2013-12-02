package com.turpgames.maze.utils;

import com.turpgames.framework.v0.IDrawingInfo;
import com.turpgames.framework.v0.IResourceManager;
import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;

public final class Maze {

	public static final int R_NONE = 0;
	public static final int R_TEMP = 1;
	public static final int R_LEFT = 2;
	public static final int R_RIGHT = 3;
	
	private Maze() {
		
	}

	private static final ITexture textureTrap;
	private static final ITexture textureBlock;
	
	private static final ITexture textureRotationSignTemp;
	private static final ITexture textureRotationSignLeft;
	private static final ITexture textureRotationSignRight;

	static {
		IResourceManager r = Game.getResourceManager();

		textureTrap = r.getTexture(R.game.textures.trap);
		textureBlock = r.getTexture(R.game.textures.block);
		
		textureRotationSignTemp = r.getTexture(R.game.textures.rotationSignTemp);
		textureRotationSignLeft = r.getTexture(R.game.textures.rotationSignLeft);
		textureRotationSignRight = r.getTexture(R.game.textures.rotationSignRight);
	}

	public static void drawTrap(IDrawingInfo info) {
		TextureDrawer.draw(textureTrap, info);
	}

	public static void drawBlock(IDrawingInfo info) {
		TextureDrawer.draw(textureBlock, info);
	}

	public static void drawRotationSign(IDrawingInfo info, int direction) {
		if(direction == Maze.R_NONE)
			return;
		if(direction == Maze.R_TEMP)
			TextureDrawer.draw(textureRotationSignTemp, info);
		else if(direction == Maze.R_LEFT)
			TextureDrawer.draw(textureRotationSignLeft, info);
		else if(direction == Maze.R_RIGHT)
			TextureDrawer.draw(textureRotationSignRight, info);
	}
	
	public static String getString(String resourceKey) {
		return Game.getLanguageManager().getString(resourceKey);
	}



}
