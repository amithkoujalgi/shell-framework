package com.koujalgi.shell.commands;

import com.koujalgi.shell.core.AbstractCommand;

public class Set extends AbstractCommand {

	public Set(String baseCommand, int params) {
		super(baseCommand, params);
	}

	@Override
	public String getUsage() {
		return "set";
	}

	@Override
	public void execute() {
		Object var = super.getCommandParser().getParams().get(0);
		Object val = super.getCommandParser().getParams().get(1);
		super.setEnvVar(var, val);
	}

	@Override
	public String getDescription() {
		return "sets env var";
	}

}
