package com.rzt.shell.exceptions;

public class InvalidCommandFormatException extends Exception {

	private static final long serialVersionUID = -1579774996323620138L;

	public InvalidCommandFormatException() {
		super("Invalid command format");
	}

	public InvalidCommandFormatException(String usage) {
		super("Invalid command format.\nUsage: " + usage);
	}
}
