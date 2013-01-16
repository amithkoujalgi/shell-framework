package com.koujalgi.shell.core;

import java.util.ArrayList;

import com.koujalgi.shell.interfaces.ICommand;

public abstract class AbstractCommand implements ICommand {
	private CommandParser parser;
	private String baseCommand = "";
	private int numParams = 0;
	private Application app;

	public AbstractCommand(String baseCommand, int params) {
		this.baseCommand = baseCommand;
		this.numParams = params;
	}

	public void setAppContext(Application app) {
		this.app = app;
	}

	public Application getAppContext() {
		return app;
	}

	public void setEnvVar(Object variable, Object value) {
		EnvironmentVariable oldVariable = getEnvVar(variable);
		if (oldVariable == null) {
			oldVariable = new EnvironmentVariable();
			oldVariable.setVariable(variable);
			oldVariable.setValue(value);
			this.app.getEnvVars().add(oldVariable);
		} else {
			oldVariable.setValue(value);
		}
	}

	public EnvironmentVariable getEnvVar(Object variable) {
		ArrayList<EnvironmentVariable> vars;
		try {
			vars = this.app.getEnvVars();
		} catch (Exception e) {
			return null;
		}
		EnvironmentVariable ret = null;
		for (EnvironmentVariable v : vars) {
			if (v.getVariable().equals(variable)) {
				ret = v;
			}
		}
		return ret;
	}

	public ArrayList<EnvironmentVariable> getAllEnvVars() {
		ArrayList<EnvironmentVariable> vars;
		try {
			vars = this.app.getEnvVars();
		} catch (Exception e) {
			return null;
		}
		return vars;
	}

	public void terminate() {
		app.terminate();
	}

	public boolean isValid() {
		return hasBaseCommand() && parser.getParamCount() == numParams;
	}

	public void setCommandString(String command) {
		parser = new CommandParser(command);
	}

	public String getBaseCommand() {
		return baseCommand;
	}

	public CommandParser getCommandParser() {
		return parser;
	}

	public ArrayList<AbstractCommand> getCommandList() {
		return this.app.getCommands();
	}

	public boolean hasBaseCommand() {
		return parser.getBaseCommand().equals(baseCommand);
	}
}
