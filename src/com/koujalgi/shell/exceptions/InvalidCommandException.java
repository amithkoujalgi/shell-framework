package com.koujalgi.shell.exceptions;

@SuppressWarnings("serial")
public class InvalidCommandException extends Exception {
	public InvalidCommandException(String msg) {
		super(msg);
	}
}
