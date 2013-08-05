package com.koujalgi.shell.entities;

import com.koujalgi.shell.interfaces.AbstractCommand;

public class Result {
	private AbstractCommand command;
	private String result;
	private Exception error;

	public Result(AbstractCommand command) {
		this.command = command;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Exception getError() {
		return error;
	}

	public void setError(Exception error) {
		this.error = error;
	}

	public AbstractCommand getCommand() {
		return command;
	}

	public void setCommand(AbstractCommand command) {
		this.command = command;
	}

}
