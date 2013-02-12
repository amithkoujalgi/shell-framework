package com.koujalgi.shell.exceptions;

public class InvalidCommandException extends Exception {

	private static final long serialVersionUID = 3091628197410513101L;

	public InvalidCommandException(String cmd) {
		super("Invalid command: '" + cmd + "'");
	}

	public InvalidCommandException() {
		super("Invalid command");
	}
}
