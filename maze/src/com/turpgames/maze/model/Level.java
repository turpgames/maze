package com.turpgames.maze.model;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.impl.AnimatedGameObject;
import com.turpgames.framework.v0.util.CollisionGroup;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.maze.display.RotationSign;
import com.turpgames.maze.model.blocks.BlockObject;
import com.turpgames.maze.model.blocks.Objective;
import com.turpgames.maze.model.blocks.Trap;
import com.turpgames.maze.model.blocks.Wall;
import com.turpgames.maze.utils.GameSettings;
import com.turpgames.maze.utils.R;

/***
 * Wrapper class for game objects in the Maze; {@link Wall}s, {@link Trap}s,
 * {@link Portal}s, and the {@link Objective}(s).
 * 
 * @author kadirello
 * 
 */
public class Level extends MazeGameObject {

	// Maze translations
	public float tx;
	public float ty;

	private List<BlockObject> blocks;
	private Lokum lokum;

	private IMazeLevelListener listener;
	
	private RotationSign rotationSign;

	private CollisionGroup lokumToBlocks;
	private List<CollisionGroup> collisionGroups;

	List<AnimatedGameObject> animatedObjects;
	public Level(IMazeLevelListener listener) {
		this.listener = listener;
		
		Map map = Map.loadCurrentLevel();
		
		int[][] data = map.getData();
//		int[][][] portalData = level.getPortalData();

		int cols = data[0].length;
		int rows = data.length;
		int mazeWidth = cols * GameSettings.blockWidth;
		int mazeHeight = rows * GameSettings.blockHeight;

		tx = (Game.getVirtualWidth() - mazeWidth) / 2;
		ty = (Game.getVirtualHeight() - mazeHeight) / 2;

		getRotation().origin.x = tx + mazeWidth / 2;
		getRotation().origin.y = ty + mazeHeight / 2;

		rotationSign = new RotationSign((Game.getVirtualWidth() - R.ui.rotationSignWidth) / 2, 
				ty + mazeWidth + R.ui.rotationSignWidth / 2);
		
		blocks = new ArrayList<BlockObject>();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (data[i][j] == BlockObject.WALL) {
					Wall block = new Wall(tx + j * GameSettings.blockWidth, ty + (rows - 1 - i) * GameSettings.blockHeight);
					block.anchorRotation(getRotation());
					
					blocks.add(block);
				} else if (data[i][j] == BlockObject.TRAP) {
					Trap trap = new Trap(tx + j * GameSettings.blockWidth, ty + (rows - 1 - i) * GameSettings.blockHeight);
					trap.anchorRotation(getRotation());

					blocks.add(trap);
				} else if (data[i][j] == BlockObject.OBJECTIVE) {
					Objective objective = new Objective(tx + j * GameSettings.blockWidth, ty + (rows - 1 - i) * GameSettings.blockHeight);
					objective.anchorRotation(getRotation());

					blocks.add(objective);
				}
			}
		}
		
		lokum = new Lokum(this, 1, 1);
//		List<ICollidable> l = new ArrayList<ICollidable>(blocks);
		
		lokumToBlocks = new CollisionGroup(lokum, (List)blocks);
		
		collisionGroups = new ArrayList<CollisionGroup>();
		collisionGroups.add(lokumToBlocks);
		
		
		animatedObjects = new ArrayList<AnimatedGameObject>();
		animatedObjects.add(lokum);
//		for (int i = 0; i < portalData.length; i++) {
//			float blueX = tx + portalData[i][0][0] * Mazeda.blockWidth;
//			float blueY = ty + portalData[i][0][1] * Mazeda.blockHeight;
//			float greenX = tx + portalData[i][1][0] * Mazeda.blockWidth;
//			float greenY = ty + portalData[i][1][1] * Mazeda.blockHeight;
//			Portal portal = new Portal(screen, blueX, blueY, greenX, greenY);
//			portal.setRotation(rotation);
//
//			portalDoors.addAll(portal.getDoors());
//		}
	}
	
	public List<CollisionGroup> getCollisionGroups() {
		return collisionGroups;
	}
	
	public List<AnimatedGameObject> getAnimatedObjects() {
		return animatedObjects;
	}

	@Override
	public void draw() {
		for(BlockObject block : blocks)
			block.draw();
		lokum.draw();
		rotationSign.draw();
	}

	/***
	 * Resets {@link Level} to its starting state.
	 */
	public void reset() {
		getRotation().angle.z = 0;
		lokum.reset();
	}

	public void setSignRotation(int rotation) {
		rotationSign.direction = rotation;
	}
	
	/***
	 * Signals the {@link Portal} of the given {@link PortalDoor} to start
	 * portal animations.
	 * 
	 * @param door
	 * @see {@link com.blox.maze.controller.MazeController#lokumFallOnPortal(PortalDoor)
	 *      lokumFallOnPortal(PortalDoor)}
	 */
//	public void collidedPortalDoor(PortalDoor door) {
//		door.getParent().enterPortal(door);
//	}

	/***
	 * Signals the {@link Portal} of the given {@link PortalDoor} to stop portal
	 * animations.
	 * 
	 * @param door
	 * @see {@link com.blox.maze.controller.MazeController#portalFinished()
	 *      portalFinished()}
	 */
//	public void finishedPortal(PortalDoor door) {
//		door.getParent().finishPortal();
//	}

	/***
	 * Registers given {@link com.blox.framework.v0.IAnimationEndListener
	 * IAnimationEndListener} implementer to listen for {@link PortalDoor}
	 * animation-ends.
	 * 
	 * @param listener
	 */
//	public void registerPortalsAnimationEndListener(IAnimationEndListener listener) {
//		for (ICollidable p : portalDoors) {
//			((MazeGameObject) p).registerAnimationEndListener(listener);
//		}
//	}

	/***
	 * Unregisters given {@link com.blox.framework.v0.IAnimationEndListener
	 * IAnimationEndListener} implementer to listen for {@link PortalDoor}
	 * animation-ends.
	 * 
	 * @param listener
	 */
//	public void unregisterPortalsAnimationEndListener(IAnimationEndListener listener) {
//		for (ICollidable p : portalDoors) {
//			((MazeGameObject) p).unregisterAnimationEndListener(listener);
//		}
//	}
}
