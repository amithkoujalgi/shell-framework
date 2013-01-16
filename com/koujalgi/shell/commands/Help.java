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
		System.out.println("Command Description\n--------------------");
		for (AbstractCommand c : commands) {
			if (c instanceof Help) {
				continue;
			} else
				System.out.println(c.getBaseCommand() + "\t"
						+ c.getDescription());
		}
	}

	@Override
	public String getDescription() {
		return "shows list of commands and their description";
	}
}
