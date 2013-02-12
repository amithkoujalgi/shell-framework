package com.koujalgi.shell.commands;

import com.koujalgi.shell.core.AbstractCommand;
import com.koujalgi.shell.core.Application;

public class History extends AbstractCommand {
	Application app;

	public History(String baseCommand, int params) {
		super(baseCommand, params);
	}

	@Override
	public void setAppContext(Application app) {
		this.app = app;
	}

	@Override
	public String getUsage() {
		return "history";
	}

	@Override
	public void execute() {
		String history = "\nCommand history\n------------------\n";
		for (int i = 0; i < app.getHistory().size() - 1; i++) {
			history += app.getHistory().get(i) + "\n";
		}
		System.out.println(history);
	}

	@Override
	public String getDescription() {
		return "shows the history of commands executed previously";
	}
}
