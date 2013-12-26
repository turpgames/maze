package com.turpgames.editor.commands;

import java.util.ArrayList;
import java.util.List;

public class Commander {
	protected interface ICommand {
		void doCommand();
		
		void undoCommand();
	}
	
	private List<ICommand> doneCommands;
	private int commandIndex;
	
	public Commander() {
		doneCommands = new ArrayList<ICommand>();
		commandIndex = -1;
	}
	
	public void doCommand(ICommand command) {
		command.doCommand();
		if (commandIndex < doneCommands.size() - 1) {
			if (commandIndex == -1)
				doneCommands.clear();
			else
				doneCommands = doneCommands.subList(0, commandIndex + 1);
		}
		doneCommands.add(command);
		commandIndex++;
	}
	
	public void undoCommand() {
		if (commandIndex == -1)
			return;
		doneCommands.get(commandIndex).undoCommand();
		commandIndex--;
	}
	
	public void redoCommand() {
		if (commandIndex == doneCommands.size() - 1)
			return;
		commandIndex++;
		doneCommands.get(commandIndex).doCommand();
	}
}
