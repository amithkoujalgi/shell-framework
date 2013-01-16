package com.koujalgi.shell.commands;

import com.koujalgi.shell.core.AbstractCommand;

public class Exit extends AbstractCommand {

	public Exit(String baseCommand, int params) {
		super(baseCommand, params);
	}

	@Override
	public String getUsage() {
		return "exit";
	}

	@Override
	public void execute() {
		System.out.println("Terminating shell.\nbye.");
		super.terminate();
		//System.exit(1);
	}

	@Override
	public String getDescription() {
		return "exit from the shell";
	}

}
