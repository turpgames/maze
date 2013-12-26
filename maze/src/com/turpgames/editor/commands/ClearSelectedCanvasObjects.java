package com.turpgames.editor.commands;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.editor.CanvasObject;
import com.turpgames.editor.commands.Commander.ICommand;

public class ClearSelectedCanvasObjects implements ICommand {

	private List<CanvasObject> originalList;
	private List<CanvasObject> ownList;
	public ClearSelectedCanvasObjects(List<CanvasObject> originalList) {
		this.originalList = originalList;
		this.ownList = new ArrayList<CanvasObject>();
		this.ownList.addAll(originalList);
	}
	
	@Override
	public void doCommand() {
		for (CanvasObject obj : ownList)
			obj.setSelected(false);
		originalList.clear();
	}

	@Override
	public void undoCommand() {
		for (CanvasObject obj : ownList) {
			obj.setSelected(true);
			originalList.add(obj);
		}
	}
}
