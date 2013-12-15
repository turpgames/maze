package com.turpgames.maze.utils;

import com.turpgames.framework.v0.IDrawingInfo;
import com.turpgames.framework.v0.IResourceManager;
import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;
import com.turpgames.maze.model.BlockObject;

public final class Maze {

	public static final int R_NONE = 0;
	public static final int R_TEMP = 1;
	public static final int R_LEFT = 2;
	public static final int R_RIGHT = 3;
	
	private Maze() {
		
	}

	private static final ITexture[] blockTextures;
	
	private static final ITexture textureRotationSignTemp;
	private static final ITexture textureRotationSignLeft;
	private static final ITexture textureRotationSignRight;

	private static final ITexture textureLokum;

	static {
		IResourceManager r = Game.getResourceManager();

		blockTextures = new ITexture[3];
		blockTextures[BlockObject.WALL] = r.getTexture(R.game.textures.wall);
		blockTextures[BlockObject.OBJECTIVE] = r.getTexture(R.game.textures.objective);
		blockTextures[BlockObject.TRAP] = r.getTexture(R.game.textures.trap);
		
		textureRotationSignTemp = r.getTexture(R.game.textures.rotationSignTemp);
		textureRotationSignLeft = r.getTexture(R.game.textures.rotationSignLeft);
		textureRotationSignRight = r.getTexture(R.game.textures.rotationSignRight);

		textureLokum = r.getTexture(R.game.textures.lokum);
	}

	public static void drawBlock(IDrawingInfo info, int type) {
		TextureDrawer.draw(blockTextures[type], info);
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

	public static void drawLokum(IDrawingInfo info) {
		TextureDrawer.draw(textureLokum, info);
	}
	
	public static String getString(String resourceKey) {
		return Game.getLanguageManager().getString(resourceKey);
	}
}
