package com.turpgames.editor.commands;

import java.util.List;

import com.turpgames.editor.CanvasObject;
import com.turpgames.editor.commands.Commander.ICommand;

public class UnselectCanvasObject implements ICommand {

	private List<CanvasObject> selectedObjects;
	private CanvasObject obj;
	public UnselectCanvasObject(CanvasObject obj, List<CanvasObject> selectedObjects) {
		this.selectedObjects = selectedObjects;
		this.obj = obj;
	}
	
	@Override
	public void doCommand() {
		obj.setSelected(false);
		selectedObjects.remove(obj);
	}

	@Override
	public void undoCommand() {
		obj.setSelected(true);
		selectedObjects.add(obj);
	}
}
