package com.koujalgi.shell.core;

import java.util.ArrayList;

import com.koujalgi.shell.interfaces.ICommand;

public abstract class AbstractCommand implements ICommand {
	private CommandParser parser;
	private String baseCommand = "";
	private int minParams = 0;
	private Application app;

	public AbstractCommand(String baseCommand, int params) {
		this.baseCommand = baseCommand;
		this.minParams = params;
	}

	@Override
	public void setAppContext(Application app) {
		this.app = app;
	}

	public Application getAppContext() {
		return app;
	}

	@Override
	public void setEnvVar(Object variable, Object value) {
		EnvironmentVariable oldVariable = getEnvVar(variable);
		if (oldVariable == null) {
			oldVariable = new EnvironmentVariable();
			oldVariable.setValue(value);
			oldVariable.setVariableName(variable);
			this.app.getEnvironmentVariables().add(oldVariable);
		} else {
			oldVariable.setValue(value);
		}
	}

	@Override
	public EnvironmentVariable getEnvVar(Object variable) {
		ArrayList<EnvironmentVariable> vars;
		try {
			vars = this.app.getEnvironmentVariables();
		} catch (Exception e) {
			return null;
		}
		EnvironmentVariable ret = null;
		for (EnvironmentVariable v : vars) {
			if (v.getVariableName().equals(variable)) {
				ret = v;
			}
		}
		return ret;
	}

	public ArrayList<EnvironmentVariable> getAllEnvVars() {
		ArrayList<EnvironmentVariable> vars;
		try {
			vars = this.app.getEnvironmentVariables();
		} catch (Exception e) {
			return null;
		}
		return vars;
	}

	@Override
	public void terminate() {
		app.terminate();
	}

	@Override
	public boolean isValid() {
		return hasBaseCommand() && parser.getParamCount() >= minParams
				&& parser.hasBalancedQuotes();
	}

	@Override
	public void setCommandString(String command) {
		parser = new CommandParser(command);
	}

	@Override
	public String getBaseCommand() {
		return baseCommand;
	}

	public CommandParser getCommandParser() {
		return parser;
	}

	public ArrayList<AbstractCommand> getCommandList() {
		return this.app.getCommands();
	}

	@Override
	public boolean hasBaseCommand() {
		return parser.getBaseCommand().equals(baseCommand);
	}
}
