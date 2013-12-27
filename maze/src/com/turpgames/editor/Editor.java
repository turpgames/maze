package com.turpgames.editor;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.turpgames.editor.commands.AddCanvasCenter;
import com.turpgames.editor.commands.ClearSelectedCanvasObjects;
import com.turpgames.editor.commands.Commander;
import com.turpgames.editor.commands.RemoveCanvasCenter;
import com.turpgames.editor.commands.SelectCanvasObject;
import com.turpgames.editor.commands.SetTypes;
import com.turpgames.editor.commands.SwitchSelectWithFrame;
import com.turpgames.editor.commands.UnselectCanvasObject;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.maze.model.blocks.BlockObject;
import com.turpgames.maze.utils.GameSettings;

public class Editor extends GameObject {
	private static final int rows = 12;
	private static final int cols = 19;
	
	public static final float blockWidth = (float) (GameSettings.blockWidth * 0.6);
	public static final float blockHeight = (float) (GameSettings.blockHeight * 0.6);
	
	protected static final float tx = (Game.getVirtualWidth() - rows * Editor.blockWidth) / 2;
	protected static final float ty = (Game.getVirtualHeight() - cols * Editor.blockHeight) / 2;
	
	private static final float toolBarX = Editor.blockWidth;
	private static final float toolBarY = Editor.blockHeight;
	
	public static final int LAYER_TOOLBAR = Game.LAYER_GAME + 1;
	public static final int LAYER_CANVAS_GRID = Game.LAYER_GAME + 2;
	public static final int LAYER_CANVAS_CENTERS = Game.LAYER_GAME + 3;
	public static final int LAYER_ROTATOR = Game.LAYER_GAME + 4;
	
	private Commander commander;
	private List<CanvasObject> canvas;
	private CanvasCenter[][] centers;
	private List<GameObject> toolbarObjects;
	
	private List<CanvasObject> selectedObjects;

	private List<CanvasObject> insideFrame;
	private List<CanvasObject> tempFrame;
	
	
	public Editor() {
		commander = new Commander();
		canvas = new ArrayList<CanvasObject>();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				canvas.add(new CanvasObject(this, i, j));
			}
		}
		
		centers = new CanvasCenter[rows-1][cols-1];
		for (int i = 0; i < rows-1; i++) {
			for (int j = 0; j < cols-1; j++) {
				centers[i][j] = new CanvasCenter(this, i, j);
			}
		}
		
		
		toolbarObjects = new ArrayList<GameObject>();
		int i = 0;
		toolbarObjects.add(new ToolbarObject(this, BlockObject.WALL, toolBarX + i * GameSettings.blockWidth, toolBarY));
		i++;
		toolbarObjects.add(new ToolbarObject(this, BlockObject.TRAP, toolBarX + i * GameSettings.blockWidth, toolBarY));
		i++;
		toolbarObjects.add(new ToolbarObject(this, BlockObject.OBJECTIVE, toolBarX + i * GameSettings.blockWidth, toolBarY));
		i++;
		
		selectedObjects = new ArrayList<CanvasObject>();
		insideFrame = new ArrayList<CanvasObject>();
		tempFrame = new ArrayList<CanvasObject>();
		
		setWidth(Game.getVirtualWidth());
		setHeight(Game.getVirtualHeight());
		getLocation().set(0,0);
		listenInput(true);
	}

	@Override
	public void draw() {
		for (CanvasObject obj : canvas)
			obj.draw();
		for (int i = 0; i < rows-1; i++) {
			for (int j = 0; j < cols-1; j++) {
				centers[i][j].draw();
			}
		}
		for (GameObject obj : toolbarObjects)
			obj.draw();
		if (dragging)
			ShapeDrawer.drawRect(touchDownX, touchDownY, currentX - touchDownX, currentY - touchDownY, Color.green(), false, false);
		Rotator.instance.draw();
	}

	@Override
	public void registerSelf() {
		Game.getInputManager().register(this, Game.LAYER_SCREEN);
	}
	
	@Override
	protected boolean onTap() {
		commander.doCommand(new ClearSelectedCanvasObjects(selectedObjects));
		return true;
	}
	
	private float touchDownX;
	private float touchDownY;
	private float currentX;
	private float currentY;
	
	private int touchDownRow;
	private int touchDownCol;
	private int currentRow;
	private int currentCol;
	
	private boolean dragging;
	@Override
	protected boolean onTouchDown(float x, float y) {
		touchDownX = x;
		touchDownY = y;
		touchDownRow = Math.min(rows - 1, Math.max(0, (int) ((x - tx) / blockWidth)));
		touchDownCol = Math.min(cols - 1, Math.max(0, (int) ((y - ty) / blockHeight)));
		insideFrame.clear();
		dragging = false;
		return false;
	}
	
	@Override
	protected boolean onTouchDragged(float x, float y) {
		if (!dragging)
			dragging = true;
		
		currentX = x;
		currentY = y;
		
		currentRow = Math.min(rows - 1, Math.max(0, (int) ((x - tx) / blockWidth)));
		currentCol = Math.min(cols - 1, Math.max(0, (int) ((y - ty) / blockHeight)));;
		
		for (CanvasObject obj : insideFrame) {
			obj.setSelected(!obj.isSelected());
		}
		
		tempFrame.clear();
		for (CanvasObject obj : canvas) {
			if (obj.indicesBetween(Math.min(touchDownRow, currentRow), Math.max(touchDownRow, currentRow), Math.min(touchDownCol, currentCol), Math.max(touchDownCol, currentCol))) {
				tempFrame.add(obj);
				obj.setSelected(!obj.isSelected());	
			}
		}
		
		insideFrame.clear();
		insideFrame.addAll(tempFrame);
		
		return true;
	}
	
	@Override
	protected boolean onTouchUp(float x, float y) {
		if (dragging) {
			dragging = false;
			
			for (CanvasObject obj : insideFrame) {
				obj.setSelected(!obj.isSelected());
			}
			if (insideFrame.size() > 1) // if unchecked, the single cell gets select-switched also with canvasObjectTapped
				commander.doCommand(new SwitchSelectWithFrame(insideFrame, selectedObjects));
		}
		return false;
	}
	
	private boolean pressedControl;
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.CONTROL_LEFT || keycode == Keys.CONTROL_RIGHT)
			pressedControl = true;
		return true;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.Z && pressedControl)
			commander.undoCommand();
		else if (keycode == Keys.Y && pressedControl)
			commander.redoCommand();
		else if (keycode == Keys.CONTROL_LEFT || keycode == Keys.CONTROL_RIGHT)
			pressedControl = false;
		return true;
	}
	
	public void toolbarTapped(int type) {
		for (CanvasObject selected : selectedObjects) {
			if (selected.getType() != type) {
				commander.doCommand(new SetTypes(type, selectedObjects));
				return;
			}
		}
		
		commander.doCommand(new SetTypes(BlockObject.NONE, selectedObjects));
	}

	public void canvasObjectTapped(CanvasObject canvasObject) {
		if (canvasObject.isSelected())
			commander.doCommand(new UnselectCanvasObject(canvasObject, selectedObjects));
		else
			commander.doCommand(new SelectCanvasObject(canvasObject, selectedObjects));
	}

	public void canvasCenterTapped(CanvasCenter canvasCenter) {
		if (canvasCenter.isActive())
			commander.doCommand(new RemoveCanvasCenter(canvasCenter));
		else {
			if (centerIsAllowed(canvasCenter))
				commander.doCommand(new AddCanvasCenter(canvasCenter, selectedObjects));
		}
	}

	private boolean centerIsAllowed(CanvasCenter canvasCenter) {
//		int centerX = canvasCenter.getXIndex() + 1;
//		int centerY = canvasCenter.getYIndex() + 1;
//		int rotationDirection = 1;
//		
//		int newX, newY;
//		for (CanvasObject obj : selectedObjects) {
//			newX = obj.getXIndex();
//			newY = obj.getYIndex();
//			for (int i = 0; i < 3; i++) {
//				newX = centerX - rotationDirection*(newY - centerY) - (rotationDirection == 1 ? 1 : 0);
//				newY = centerY + rotationDirection*(newX - centerX) - (rotationDirection != 1 ? 1 : 0);
//				
//				if (indicesOutBounds(newX, newY)) {
//					return false;
//				}
//				if (canvas[newX][newY].getType() != BlockObject.NONE) {
//					if (!selectedObjects.contains(canvas[newX][newY])) {
//						return false;
//					}
//					return false;
//				}
//				
//			}
//		}
		
		return true;
	}

	private boolean indicesOutBounds(int newX, int newY) {
		return !(newX < rows && newX >= 0 && newY < cols && newY >= 0);
	}

	public void canvasCenterRotated(CanvasCenter canvasCenter, int rotationDirection) {
		int centerX = canvasCenter.getXIndex() + 1;
		int centerY = canvasCenter.getYIndex() + 1;
		
		List<CanvasObject> children = canvasCenter.getChildren();
		int newX, newY;
		canvas.removeAll(children);
		for (CanvasObject obj : children) {
			newX = centerX - rotationDirection*(obj.getYIndex() - centerY) - (rotationDirection == 1 ? 1 : 0);
			newY = centerY + rotationDirection*(obj.getXIndex() - centerX) - (rotationDirection != 1 ? 1 : 0);
			for (int i = canvas.size() - 1; i >= 0; i--){
				if (canvas.get(i).getXIndex() == newX && canvas.get(i).getYIndex() == newY) {
					canvas.remove(canvas.get(i));
					canvas.add(new CanvasObject(this, obj.getXIndex(), obj.getYIndex()));
				}
			}
			obj.updateIndices(newX, newY);
			canvas.add(obj);
		}
	}
}