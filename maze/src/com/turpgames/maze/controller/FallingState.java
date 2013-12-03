package com.turpgames.maze.controller;

import com.turpgames.framework.v0.ICollidable;
import com.turpgames.framework.v0.ICollisionListener;
import com.turpgames.framework.v0.util.CollisionEvent;
import com.turpgames.maze.model.Lokum;
import com.turpgames.maze.model.Objective;
import com.turpgames.maze.model.Trap;
import com.turpgames.maze.model.Wall;

public class FallingState extends State implements ICollisionListener {

	public FallingState(Controller parent) {
		super(parent);
	}

	@Override
	public void onCollide(CollisionEvent event) {
		ICollidable thisObj = event.getThisObj();
		ICollidable thatObj = event.getThatObj();
		if (thisObj instanceof Lokum) {
			if (thatObj instanceof Wall) {
				controller.lokumOnWall();
			} else if (thatObj instanceof Trap) {
				controller.lokumOnTrap();
			} else if (thatObj instanceof Objective) {
				controller.lokumOnObjective();
			}	
		}
	}

//	@Override
//	public void onNotCollide(CollisionEvent event) {
//		lokumUncollidedPortalDoor(event.getThatObj());
//	}


//	/***
//	 * Called by {@link com.blox.maze.controller.MazeController#lokumFalling
//	 * lokumFalling} to re-add exit {link com.turpgames.maze.model.PortalDoor
//	 * PortalDoor} back to the collision group
//	 * {@link com.blox.maze.controller.MazeController#lokumToPortals
//	 * lokumToPortals} and remove it from
//	 * {@link com.blox.maze.controller.MazeController#lokumNotCollide
//	 * lokumNotCollide}
//	 * 
//	 * @param obj
//	 */
//	public void lokumUncollidedPortalDoor(ICollidable obj) {
//		lokumToPortals.registerSecond(obj);
//		lokumNotCollide.unregisterSecond(obj);
//	}



//	/***
//	 * Called by {@link com.blox.maze.controller.MazeController#lokumFalling
//	 * lokumFalling} state when {@link com.turpgames.maze.model.Lokum Lokum} lands on
//	 * an {@link com.blox.maze.model.PortalDoor PortalDoor}. The collided
//	 * PortalDoor's are recorded. Lokum is unregistered from the
//	 * {@link com.turpgames.framework.v0.impl.Drawer DrawManager}. FSM is
//	 * advanced to the next state,
//	 * {@link com.blox.maze.controller.MazeController#lokumOnlokumOnPortal
//	 * lokumOnPortal}, which is registered to listen for animation-end of the
//	 * collided PortalDoor.
//	 * 
//	 * @param thisBound
//	 * @param thatBound
//	 * @param thatObj
//	 */
//	public void lokumFallOnPortal(PortalDoor door) {
//		this.door = door;
//		this.doorPair = this.door.getPair();
//
//		maze.collidedPortalDoor(this.door);
//		screen.unregisterDrawable(lokum);
//		this.door.registerAnimationEndListener(lokumOnPortal);
//		setCurrState(lokumOnPortal);
//	}
}
