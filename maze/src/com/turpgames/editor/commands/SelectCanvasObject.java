package com.turpgames.editor.commands;

import java.util.List;

import com.turpgames.editor.CanvasObject;
import com.turpgames.editor.commands.Commander.ICommand;

public class SelectCanvasObject implements ICommand {

	private List<CanvasObject> selectedObjects;
	private CanvasObject obj;
	public SelectCanvasObject(CanvasObject obj, List<CanvasObject> selectedObjects) {
		this.selectedObjects = selectedObjects;
		this.obj = obj;
	}
	
	@Override
	public void doCommand() {
		obj.setSelected(true);
		selectedObjects.add(obj);
	}

	@Override
	public void undoCommand() {
		obj.setSelected(false);
		selectedObjects.remove(obj);
	}
}
