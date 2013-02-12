package com.koujalgi.shell.exceptions;

public class UnbalancedQuotesException extends Exception {

	private static final long serialVersionUID = -5933222483404426605L;

	public UnbalancedQuotesException() {
		super("Quotes unbalanced");
	}
}
