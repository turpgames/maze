package com.turpgames.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Input.Keys;
import com.turpgames.editor.commands.AddCanvasCenter;
import com.turpgames.editor.commands.ClearSelectedCanvasObjects;
import com.turpgames.editor.commands.Commander;
import com.turpgames.editor.commands.RemoveCanvasCenter;
import com.turpgames.editor.commands.SelectCanvasObject;
import com.turpgames.editor.commands.SetTypes;
import com.turpgames.editor.commands.SwitchSelectWithFrame;
import com.turpgames.editor.commands.UnselectCanvasObject;
import com.turpgames.editor.data.MapData;
import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.maze.utils.R;
import com.turpgames.maze.model.blocks.BlockObject;
import com.turpgames.maze.utils.GameSettings;

public class Editor extends GameObject {
	private static final int rows = 12;
	private static final int cols = 19;
	
	public static final float blockWidth = (float) (GameSettings.blockWidth * 0.6);
	public static final float blockHeight = (float) (GameSettings.blockHeight * 0.6);
	
	protected static final float tx = (Game.getVirtualWidth() - rows * Editor.blockWidth) / 2;
	protected static final float ty = (Game.getVirtualHeight() - cols * Editor.blockHeight) / 2;
	
	public static final float toolBarX = Editor.blockWidth;
	public static final float toolBarY = Editor.blockHeight;
	
	public static final int LAYER_TOOLBAR = Game.LAYER_GAME + 1;
	public static final int LAYER_CANVAS_GRID = Game.LAYER_GAME + 2;
	public static final int LAYER_CANVAS_CENTERS = Game.LAYER_GAME + 3;
	public static final int LAYER_ROTATOR = Game.LAYER_GAME + 4;
	
	private Commander commander;
	private Map<Coordinates, CanvasObject> canvas;
	private List<CanvasCenter> centers;
	
	private List<GameObject> toolbarObjects;
	private CentersButton centersButton;
	private ImageButton demoButton;
	
	private List<CanvasObject> selectedObjects;

	private List<CanvasObject> insideFrame;
	private List<CanvasObject> tempFrame;
	
	public Editor() {
		commander = new Commander();
		canvas = new HashMap<Coordinates, CanvasObject>();
		CanvasObject cue;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				cue = new CanvasObject(this, i, j);
				canvas.put(cue.getCoordinates(), cue);
			}
		}
		
		centers = new ArrayList<CanvasCenter>();
		for (int i = 0; i < rows-1; i++) {
			for (int j = 0; j < cols-1; j++) {
				centers.add(new CanvasCenter(this, i, j, false));
				if (i < rows - 2 && j < cols -2)
					centers.add(new CanvasCenter(this, i, j, true));
			}
		}
		
		toolbarObjects = new ArrayList<GameObject>();
		int i = 0;
		toolbarObjects.add(new ToolbarObject(this, BlockObject.WALL, toolBarX + i * GameSettings.blockWidth + 3 * i, toolBarY));
		i++;
		toolbarObjects.add(new ToolbarObject(this, BlockObject.TRAP, toolBarX + i * GameSettings.blockWidth + 3 * i, toolBarY));
		i++;
		toolbarObjects.add(new ToolbarObject(this, BlockObject.OBJECTIVE, toolBarX + i * GameSettings.blockWidth + 3 * i, toolBarY));
		i++;
		centersButton = new CentersButton(this, Game.getVirtualWidth() - 3 * CentersButton.radius, Editor.toolBarY);
		demoButton = new ImageButton(64f,64f,"demoButton");
		demoButton.setLocation(Button.AlignNE, 10, 10);
		demoButton.setListener(new IButtonListener() {
			
			@Override
			public void onButtonTapped() {
				MapData mapData = new MapData(canvas, centers);
				mapData.write("demo/demo.map");
				
				//Change to MazeTestScreen 
//				ScreenManager.instance.switchTo(R.game.screens.mazeTest, false);
			}
		});
		
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
		for (CanvasObject obj : canvas.values())
			obj.draw();
		for (CanvasCenter center : centers) {
			center.draw();
		}
		for (GameObject obj : toolbarObjects)
			obj.draw();
		centersButton.draw();
		demoButton.draw();
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
		for (CanvasObject obj : canvas.values()) {
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
		if (canvasCenter.isTurnedOn())
			commander.doCommand(new RemoveCanvasCenter(canvasCenter));
		else {
			if (centerIsAllowed(canvasCenter))
				commander.doCommand(new AddCanvasCenter(canvasCenter, selectedObjects));
		}
	}

	private boolean centerIsAllowed(CanvasCenter canvasCenter) {
		for (CanvasCenter existing : centers) {
			if (existing.isTurnedOn()) {
				for (CanvasObject obj : existing.getChildren()) {
					if (selectedObjects.contains(obj))
						return false;
				}
			}
		}
		
		int centerX = canvasCenter.getXIndex() + 1;
		int centerY = canvasCenter.getYIndex() + 1;
		
		int maxX = -1, minX = rows, maxY = -1, minY = cols;
		for (CanvasObject obj : selectedObjects) {
			if (obj.getCoordinates().getX() < minX)
				minX = obj.getCoordinates().getX();
			if (obj.getCoordinates().getX() > maxX)
				maxX = obj.getCoordinates().getX();
			if (obj.getCoordinates().getY() < minY)
				minY = obj.getCoordinates().getY();
			if (obj.getCoordinates().getY() > maxY)
				maxY = obj.getCoordinates().getY();
		}
		// 180 degree rotations
		if (indexXOutBounds(centerX - (maxX - centerX) - 1))
			return outOfBounds();
		if (indexXOutBounds(centerX - (minX - centerX) - 1))
			return outOfBounds();
		if (indexYOutBounds(centerY - (maxY - centerY) - 1))
			return outOfBounds();
		if (indexYOutBounds(centerY - (minY - centerY) - 1))
			return outOfBounds();
		
		// 90 degree rotations
		if (indexXOutBounds(centerX + (maxY - centerY)))
			return outOfBounds();
		if (indexXOutBounds(centerX - (maxY - centerY) - 1))
			return outOfBounds();
		if (indexYOutBounds(centerY + (maxX - centerX)))
			return outOfBounds();
		if (indexYOutBounds(centerY - (maxX - centerX) - 1))
			return outOfBounds();

		if (indexXOutBounds(centerX + (minY - centerY)))
			return outOfBounds();
		if (indexXOutBounds(centerX - (minY - centerY) - 1))
			return outOfBounds();
		if (indexYOutBounds(centerY + (minX - centerX)))
			return outOfBounds();
		if (indexYOutBounds(centerY - (minX - centerX) - 1))
			return outOfBounds();
		
		int reach = Math.max(maxX + 1 - centerX, Math.max(centerX - minX, Math.max(maxY + 1 - centerY, centerY - minY)));
		Coordinates cue = new Coordinates();
		CanvasObject canvasCue;
		for (int x = centerX - reach; x < centerX + reach; x++) {
			for (int y = centerY - reach; y < centerY + reach; y++) {
				cue.setX(x);
				cue.setY(y);
				canvasCue = canvas.get(cue);
				if (canvasCue.getType() != BlockObject.NONE && !selectedObjects.contains(canvasCue))
					return conflictsExistingBlocks();
			}
		}
		return true;
	}
	
	private boolean outOfBounds() {
		return false;
	}

	private boolean conflictsExistingBlocks() {
		return false;
	}
	
	private boolean indexXOutBounds(int x) {
		return !(x < rows && x >= 0);
	}
	
	private boolean indexYOutBounds(int y) {
		return !(y < cols && y >= 0);
	}
	
	public void centerButtonTapped() {
		for (CanvasCenter center : centers) {
			center.switchActivated();
		}
	}
}