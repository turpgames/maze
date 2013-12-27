package com.turpgames.editor.commands;

import java.util.List;

import com.turpgames.editor.CanvasCenter;
import com.turpgames.editor.CanvasObject;
import com.turpgames.editor.commands.Commander.ICommand;

public class AddCanvasCenter implements ICommand {

	private CanvasCenter center;
	private List<CanvasObject> selectedObjects;
	public AddCanvasCenter(CanvasCenter center, List<CanvasObject> selectedObjects) {
		this.center = center;
		this.selectedObjects = selectedObjects;
	}
	
	@Override
	public void doCommand() {
		center.setChildren(selectedObjects);
		center.turnOn();
	}

	@Override
	public void undoCommand() {
		center.turnOff();
	}
}
