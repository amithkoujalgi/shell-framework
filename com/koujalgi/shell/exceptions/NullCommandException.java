package com.koujalgi.shell.exceptions;

public class NullCommandException extends Exception {

	private static final long serialVersionUID = 3091628197410513101L;

	public NullCommandException(String msg) {
		super(msg);
	}

	public NullCommandException() {
		super("Null command");
	}
}
