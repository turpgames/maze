package com.turpgames.editor.commands;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.editor.CanvasObject;
import com.turpgames.editor.commands.Commander.ICommand;

public class SetTypes implements ICommand {
	
	private int type;
	private List<CanvasObject> selectedObjects;
	private List<Integer> oldTypes;
	
	public SetTypes(int type, List<CanvasObject> selectedObjects) {
		this.type = type;
		this.selectedObjects = selectedObjects;
		this.oldTypes = new ArrayList<Integer>();
		for (int i = 0; i < selectedObjects.size(); i++)
			oldTypes.add(selectedObjects.get(i).getType());
	}
	
	@Override
	public void doCommand() {
		for (CanvasObject obj : selectedObjects) {
			obj.setType(type);
		}
	}

	@Override
	public void undoCommand() {
		for (int i = 0; i < selectedObjects.size(); i++)
			selectedObjects.get(i).setType(oldTypes.get(i));
	}
}
