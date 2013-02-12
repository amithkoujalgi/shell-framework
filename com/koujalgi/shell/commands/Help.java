package com.koujalgi.shell.commands;

import java.util.ArrayList;

import com.koujalgi.shell.core.AbstractCommand;

public class Help extends AbstractCommand {

	private ArrayList<AbstractCommand> commands = new ArrayList<AbstractCommand>();

	public Help(String baseCommand, int params) {
		super(baseCommand, params);
	}

	public void setCommands(ArrayList<AbstractCommand> commands) {
		this.commands = commands;
	}

	@Override
	public String getUsage() {
		return "help";
	}

	@Override
	public void execute() {
		String help = "\nCommand Description\n--------------------\n";
		for (int i = 0; i < commands.size(); i++) {
			if (commands.get(i) instanceof Help) {
				continue;
			} else {
				help += commands.get(i).getBaseCommand() + "\t\t"
						+ commands.get(i).getDescription() + "\n";
			}
		}
		System.out.println(help);
	}

	@Override
	public String getDescription() {
		return "shows list of commands and their description";
	}
}
