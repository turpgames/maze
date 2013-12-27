package com.turpgames.editor.commands;

import com.turpgames.editor.CanvasCenter;
import com.turpgames.editor.commands.Commander.ICommand;

public class RemoveCanvasCenter implements ICommand {

	private CanvasCenter center;
	public RemoveCanvasCenter(CanvasCenter center) {
		this.center = center;
	}
	
	@Override
	public void doCommand() {
		center.deactivate();
	}

	@Override
	public void undoCommand() {
		center.activate();
	}
}