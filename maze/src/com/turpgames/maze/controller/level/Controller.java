package com.turpgames.maze.controller.level;

import java.util.List;

import com.turpgames.framework.v0.impl.AnimatedGameObject;
import com.turpgames.framework.v0.util.CollisionGroup;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.maze.controller.base.AbstractController;
import com.turpgames.maze.model.Level;
import com.turpgames.maze.view.MazeScreen;

public class Controller extends AbstractController {
	protected LevelState currentState;
	
	protected Level model;
	protected MazeScreen view;
	
	/**
	 * Keeps the rotation before the user input starts.
	 */
	private float mazeOldRotation;
	/**
	 * Values -1 or 1 for maze rotation direction (Right, Left).
	 */
	private int userRotation;
	/**
	 * Angle of rotation that completes user rotation to 90 degrees.
	 */
	private float mazeTempRotation;

	private float epsilon = 0.1f;
	
	// FSM States implementing IState
	private WaitingState waiting;
	private UserRotatingState userRotating;
	private MazeRotatingState mazeRotating;
	private FallingState falling;
	private LokumOnObjectiveState lokumOnObjective;
	private LokumOnTrapState lokumOnTrap;
	
	List<CollisionGroup> collisionGroups;
	List<AnimatedGameObject> animatedObjects;
	public Controller(MazeScreen screen) {
		this.view = screen;
		this.model = new Level(this);
		
		this.collisionGroups = model.getCollisionGroups();
		for (CollisionGroup g : collisionGroups) {
			view.registerCollisionGroup(g);
		}
		
		this.animatedObjects = model.getAnimatedObjects();
		
		waiting = new WaitingState(this);
		userRotating = new UserRotatingState(this);
		mazeRotating = new MazeRotatingState(this);
		falling = new FallingState(this);
		lokumOnObjective = new LokumOnObjectiveState(this);
		lokumOnTrap = new LokumOnTrapState(this);
		
		Game.getInputManager().register(this, Utils.LAYER_GAME);
		// State machine is started on 'waiting' state.
		setCurrentState(waiting);
	}
	
	private void setCurrentState(LevelState s) {
		if (currentState != null) {
			for (CollisionGroup g : collisionGroups)
				g.unregisterCollisionListener(currentState);
			for (AnimatedGameObject obj : animatedObjects)
				obj.unregisterAnimationEndListener(currentState);
		}
		currentState = s;
		for (CollisionGroup g : collisionGroups)
			g.registerCollisionListener(s);
		for (AnimatedGameObject obj : animatedObjects)
			obj.registerAnimationEndListener(s);
	}
	
	/***
	 * Called by {@link com.turpgames.maze.controller.level.Controller.maze.controller.MazeController#waiting waiting}
	 * state to signal start of user input. Old rotation value is recorded. The
	 * next state ({@link com.turpgames.maze.controller.level.Controller.maze.controller.MazeController#userRotating
	 * userRotating}) is started after the input start coordinates are sent to
	 * it.
	 * 
	 * @param rotateStartX
	 * @param rotateStartY
	 */
	public void beginUserRotating(float rotateStartX, float rotateStartY) {
		mazeOldRotation = model.getRotation().angle.z;
		userRotating.setStarts(rotateStartX, rotateStartY);
		setCurrentState(userRotating);
	}
	
	/***
	 * Called by {@link com.turpgames.maze.controller.level.Controller.maze.controller.MazeController#userRotating
	 * userRotating} state to update the rotation of the maze while user input
	 * is still being received (Slide is not finished).
	 * 
	 * @param userRotation
	 */
	public void userRotated(float userRotation) {
		model.getRotation().angle.z = (mazeOldRotation + userRotation);
	}
	
	/***
	 * Called by {@link com.turpgames.maze.controller.level.Controller.maze.controller.MazeController#userRotating
	 * userRotating} state to signal end of user input abortion. Maze rotation
	 * is restored to its old value and FSM is moved back to
	 * {@link com.turpgames.maze.controller.level.Controller.maze.controller.MazeController#waiting waiting} state.
	 */
	public void userAbortRotation() {
		model.getRotation().angle.z = mazeOldRotation;
		setCurrentState(waiting);
	}
	
	/***
	 * Called by {@link com.turpgames.maze.controller.level.Controller.maze.controller.MazeController#userRotating
	 * userRotating} state when user input ends. Needed rotation angle to
	 * complete user rotation to 90 degrees is calculated. FSM is moved to the
	 * next state, {@link com.turpgames.maze.controller.level.Controller.maze.controller.MazeController#mazeRotating
	 * mazeRotating}.
	 * 
	 * @param userRotation
	 * @param userAngle
	 */
	public void startMazeRotate(int userRotation, float userAngle) {
		this.userRotation = userRotation;
		mazeTempRotation = 90 - userRotation * userAngle;
		setCurrentState(mazeRotating);
	}
	
	/***
	 * Called by {@link com.turpgames.maze.controller.level.Controller.maze.controller.MazeController#mazeRotating
	 * mazeRotating} state on each update cycle to rotate the maze till 90
	 * degrees of rotation is reached. Upon reaching, MazeMover is informed
	 * about new rotation.
	 * state.
	 * 
	 * @param increment
	 */
	public void mazeRotate(float increment) {
		model.getRotation().angle.z += userRotation * increment;
		mazeTempRotation -= increment; // Keep track of remaining rotation.
		if (mazeTempRotation <= 0 + epsilon) { // MAZE_ROTATE FINISHED
			model.getRotation().angle.z = (mazeOldRotation + userRotation * 90);
			MazeMover.instance.turn(userRotation == 1);

			setCurrentState(falling);
		}
	}

	@Override
	public void draw() {
		currentState.draw();
	}

	@Override
	public boolean touchDragged(float x, float y, int pointer) {
		return currentState.touchDragged(x, y, pointer);
	}

	@Override
	public boolean touchUp(float x, float y, int pointer, int button) {
		return currentState.touchUp(x, y, pointer, button);
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return currentState.touchDown(x, y, pointer, button);
	}

	@Override
	public void work() {
		currentState.work();
	}

	public void lokumOnWall() {
		setCurrentState(waiting);
	}

	public void lokumOnTrap() {
		setCurrentState(lokumOnTrap);
	}

	public void lokumOnObjective() {
		setCurrentState(lokumOnObjective);
	}
	

	/***
	 * Called by
	 * {@link com.blox.maze.controller.MazeController#lokumOnObjective
	 * lokumOnObjective} state (when animation ends) to move on to the next map.
	 * TODO Currently only calls
	 * {@link com.blox.maze.controller.MazeController#resetMap resetMap}.
	 */
	public void finishMap() {
		// TODO Get next map falan
		resetMap();
	}
	/***
	 * Called by {@link com.blox.maze.controller.MazeController#lokumOnTrap
	 * lokumOnTrap} state (when animation ends) to restart the current map.
	 */
	public void resetMap() {
		// TODO: reset map and lokum
		MazeMover.instance.resetRotation();
		model.reset();
		setCurrentState(waiting);
	}
}
	
