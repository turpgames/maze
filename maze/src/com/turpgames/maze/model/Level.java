package com.turpgames.maze.model;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.maze.display.RotationSign;
import com.turpgames.maze.utils.R;

/***
 * Wrapper class for game objects in the Maze; {@link Block}s, {@link Trap}s,
 * {@link Portal}s, and the {@link Objective}(s).
 * 
 * @author kadirello
 * 
 */
public class Level extends MazeGameObject {

	private static enum DataType {
		EMPTY, BLOCK, TRAP, OBJECTIVE
	}; // make BlockType and collect all in one class?

	public static final int blockWidth = 40;
	public static final int blockHeight = 40;

	// Maze translations
	public float tx;
	public float ty;

	private List<Block> blocks;
//	private List<ICollidable> traps;
//	private List<ICollidable> objectives;
//	private List<ICollidable> portalDoors;

	private IMazeLevelListener listener;
	
	private RotationSign rotationSign;
	
	public Level(IMazeLevelListener listener) {
		this.listener = listener;
		
		Map map = Map.loadCurrentLevel();
		
		int[][] data = map.getData();
//		int[][][] portalData = level.getPortalData();

		int cols = data.length;
		int rows = data[0].length;
		int mazeWidth = cols * blockWidth;
		int mazeHeight = rows * blockHeight;

		tx = (Game.getVirtualWidth() - mazeWidth) / 2;
		ty = (Game.getVirtualHeight() - mazeHeight) / 2;

		getRotation().origin.x = tx + mazeWidth / 2;
		getRotation().origin.y = ty + mazeHeight / 2;

		rotationSign = new RotationSign((Game.getVirtualWidth() - R.ui.rotationSignWidth) / 2, 
				ty + mazeWidth + R.ui.rotationSignWidth / 2);
		
		blocks = new ArrayList<Block>();
//		traps = new ArrayList<ICollidable>();
//		objectives = new ArrayList<ICollidable>();
//		portalDoors = new ArrayList<ICollidable>();
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				if (data[i][j] == DataType.BLOCK.ordinal()) {
					Block block = new Block(tx + i * blockWidth, ty + j * blockHeight);
					block.anchorRotation(getRotation());
					
					blocks.add(block);
				} else if (data[i][j] == DataType.TRAP.ordinal()) {
//					Trap trap = new Trap(tx + i * blockWidth, ty + j * blockHeight);
//					trap.setRotation(rotation);
//
//					screen.registerDrawable(trap, 2);
//
//					traps.add(trap);
				} else if (data[i][j] == DataType.OBJECTIVE.ordinal()) {
//					Objective objective = new Objective(tx + i * blockWidth, ty + j * blockHeight);
//					objective.setRotation(rotation);
//
//					screen.registerDrawable(objective, 2);
//
//					objectives.add(objective);
				}
			}
		}

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

	@Override
	public void draw() {
		for(Block block : blocks)
			block.draw();
		rotationSign.draw();
	}

	@Override
	public void registerSelf() {
		Game.getInputManager().register(this, Utils.LAYER_GAME);
	}
	
	public List<Block> getBlocks() {
		return blocks;
	}

//	public List<ICollidable> getTraps() {
//		return traps;
//	}
//
//	public List<ICollidable> getObjectives() {
//		return objectives;
//	}
//
//	public List<ICollidable> getPortals() {
//		return portalDoors;
//	}

	/***
	 * Resets {@link Level} to its starting state.
	 */
	public void reset() {
		getRotation().angle.z = 0;
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
