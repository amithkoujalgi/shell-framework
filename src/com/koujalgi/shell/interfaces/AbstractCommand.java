package com.koujalgi.shell.interfaces;

import java.util.ArrayList;

import com.koujalgi.shell.core.Application;
import com.koujalgi.shell.core.CommandParser;
import com.koujalgi.shell.entities.Result;

public abstract class AbstractCommand {
	private Application applicationContext;
	private String baseCommand = "";
	private CommandParser parser;

	private int minParamsNeeded;

	public abstract Result execute() throws Exception;

	public abstract String getCommandUsageSyntax();

	public abstract String getCommandDescription();

	public abstract String getCommandUsageExamples();

	public AbstractCommand(String baseCommand, int minParamsNeeded) {
		this.baseCommand = baseCommand;
		this.minParamsNeeded = minParamsNeeded;
	}

	public int getMinParamsNeeded() {
		return minParamsNeeded;
	}

	public void setApplicationContext(Application applicationContext) {
		this.applicationContext = applicationContext;
	}

	public Application getApplicationContext() {
		return applicationContext;
	};

	public boolean isValid() {
		return hasBaseCommand() && parser.getParamCount() >= minParamsNeeded
				&& parser.hasBalancedQuotes();
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

	public String getCommandString() {
		return parser.getCommandString();
	}

	public ArrayList<AbstractCommand> getCommandList() {
		return this.applicationContext.getCommandList();
	}

	public boolean hasBaseCommand() {
		return parser.getBaseCommand().equals(baseCommand);
	}
}
