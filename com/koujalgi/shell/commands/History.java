package com.koujalgi.shell.commands;

import com.koujalgi.shell.core.AbstractCommand;
import com.koujalgi.shell.core.Application;

public class History extends AbstractCommand {
	Application app;

	public History(String baseCommand, int params) {
		super(baseCommand, params);
	}

	public void setAppContext(Application app) {
		this.app = app;
	}

	@Override
	public String getUsage() {
		return "history";
	}

	@Override
	public void execute() {
		System.out.println("Command history\n------------------");
		for (String c : app.getHistory()) {
			System.out.println(c);
		}
	}

	@Override
	public String getDescription() {
		return "shows the history of commands executed previously";
	}
}
