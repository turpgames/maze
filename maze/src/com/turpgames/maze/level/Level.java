package com.turpgames.maze.level;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.box2d.IWorld;
import com.turpgames.framework.v0.impl.AnimatedGameObject;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.maze.controller.Global;
import com.turpgames.maze.display.RotationSign;
import com.turpgames.maze.model.ILevelListener;
import com.turpgames.maze.model.Lokum;
import com.turpgames.maze.model.MazeGameObject;
import com.turpgames.maze.model.blocks.BlockObject;
import com.turpgames.maze.utils.GameSettings;
import com.turpgames.maze.utils.R;
import com.turpgames.maze.view.IScreenView;

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

	private Mechanic mechanic;
	private List<BlockObject> blocks;
	private Lokum lokum;

	private ILevelListener listener;
	
	private RotationSign rotationSign;

//	private CollisionGroup lokumToBlocks;
//	private List<CollisionGroup> collisionGroups;

	List<AnimatedGameObject> animatedObjects;
	public Level(IWorld world, ILevelListener listener, IScreenView view) {
		this.listener = listener;
		
		rotationSign = new RotationSign((Game.getVirtualWidth() - R.ui.rotationSignWidth) / 2, 
				Game.getVirtualHeight() - R.ui.rotationSignHeight*2);

		getRotation().origin.x = Game.getVirtualWidth()/2 ;
		getRotation().origin.y = Game.getVirtualHeight()/2 ;
		LevelMeta levelMeta = Global.levelMeta;
		blocks = new ArrayList<BlockObject>();
		for (BlockMeta blockMeta : levelMeta.getBlocks()) {
			BlockObject obj = BlockObject.create(blockMeta, world, this.getRotation());
			blocks.add(obj);
		}

		this.mechanic = new Mechanic(this, view);
		int rowIndex = 8;
		int colIndex = 8;
		lokum = new Lokum(world, getRotation(), tx + rowIndex * GameSettings.blockWidth, ty + colIndex * GameSettings.blockHeight);

//		List<ICollidable> l = new ArrayList<ICollidable>(blocks);
		
//		lokumToBlocks = new CollisionGroup(lokum, (List)blocks);
//		
//		collisionGroups = new ArrayList<CollisionGroup>();
//		collisionGroups.add(lokumToBlocks);
		
		
//		animatedObjects = new ArrayList<AnimatedGameObject>();
//		animatedObjects.add(lokum);
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
	
//	public List<CollisionGroup> getCollisionGroups() {
//		return collisionGroups;
//	}
	
//	public List<AnimatedGameObject> getAnimatedObjects() {
//		return animatedObjects;
//	}
	
	public void activate() {
		this.mechanic.activate();
	}
	
	public void deactivate() {
		this.mechanic.deactivate();
	}

	@Override
	public void draw() {
		for(BlockObject block : blocks)
			block.draw();
		lokum.draw();
		rotationSign.draw();
	}

	public void setSignRotation(int rotation) {
		rotationSign.direction = rotation;
	}

	public void update() {
		this.mechanic.update();
	}

	public void addBodyRotation(float angle) {
		for (BlockObject obj : blocks) {
			obj.addBodyRotation(angle);
		}
	}
	
	public void setBodyRotation(float angle) {
		for (BlockObject obj : blocks) {
			obj.setBodyRotation(angle);
		}
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
