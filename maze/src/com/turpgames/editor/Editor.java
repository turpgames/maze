package com.turpgames.editor;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.turpgames.editor.commands.ClearSelectedCanvasObjects;
import com.turpgames.editor.commands.Commander;
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
	
	private Commander commander;
	private CanvasObject[][] canvas;
	private List<GameObject> toolbarObjects;
	
	private List<CanvasObject> selectedObjects;

	private List<CanvasObject> insideFrame;
	private List<CanvasObject> tempFrame;
	
	public Editor() {
		commander = new Commander();
		canvas = new CanvasObject[12][19];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				canvas[i][j] = new CanvasObject(this, i, j);
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
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				canvas[i][j].draw();
			}
		}
		for (GameObject obj : toolbarObjects)
			obj.draw();
		if (dragging)
			ShapeDrawer.drawRect(touchDownX, touchDownY, currentX - touchDownX, currentY - touchDownY, Color.green(), false, false);
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
		for (int i = Math.min(touchDownRow, currentRow); i <= Math.max(touchDownRow, currentRow); i++) {
			for (int j = Math.min(touchDownCol, currentCol); j <= Math.max(touchDownCol, currentCol); j++) {
				tempFrame.add(canvas[i][j]);
				canvas[i][j].setSelected(!canvas[i][j].isSelected());
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
}
