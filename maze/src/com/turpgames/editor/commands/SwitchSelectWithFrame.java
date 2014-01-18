package com.turpgames.editor.commands;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.editor.CanvasObject;
import com.turpgames.editor.commands.Commander.ICommand;

public class SwitchSelectWithFrame implements ICommand {

	private List<CanvasObject> insideFrame;
	private List<CanvasObject> selectedObjects;
	public SwitchSelectWithFrame(List<CanvasObject> insideFrame, List<CanvasObject> selectedObjects) {
		this.insideFrame = new ArrayList<CanvasObject>();
		this.insideFrame.addAll(insideFrame);
		this.selectedObjects = selectedObjects;
	}
	
	@Override
	public void doCommand() {
		for (CanvasObject obj : insideFrame) {
			if (obj.isSelected()) {
				obj.setSelected(false);
				selectedObjects.remove(obj);
			}
			else {
				obj.setSelected(true);
				selectedObjects.add(obj);
			}
		}
	}

	@Override
	public void undoCommand() {
		for (CanvasObject obj : insideFrame) {
			if (obj.isSelected()) {
				obj.setSelected(false);
				selectedObjects.remove(obj);
			}
			else {
				obj.setSelected(true);
				selectedObjects.add(obj);
			}
		}
	}

}
