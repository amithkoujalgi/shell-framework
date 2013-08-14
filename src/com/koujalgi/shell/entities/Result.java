package com.koujalgi.shell.entities;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.koujalgi.shell.interfaces.AbstractCommand;

public class Result {
	private AbstractCommand command;
	private String result;
	private String error;
	private boolean halt;

	public Result(AbstractCommand command) {
		this.command = command;
		this.result = "";
		this.error = "";
		this.halt = false;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(Object error) {
		String err = null;
		if (error == null) {
			err = "";
		}
		if (error instanceof Exception) {
			err = getExceptionAsString((Exception) error);
		} else if (error instanceof String) {
			err = (String) error;
		}
		this.error = err;
	}

	public AbstractCommand getCommand() {
		return command;
	}

	public void setCommand(AbstractCommand command) {
		this.command = command;
	}

	public void appendResult(String newResult) {
		if (newResult == null) {
			newResult = "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(this.result);
		if (!this.result.trim().isEmpty()) {
			sb.append("\n");
		}
		sb.append(newResult);
		this.result = sb.toString();
	}

	public void appendError(Object newError) {
		String err = null;
		if (newError == null) {
			err = "";
		}
		if (newError instanceof Exception) {
			err = getExceptionAsString((Exception) newError);
		} else if (newError instanceof String) {
			err = (String) newError;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(this.error);
		if (!this.error.trim().isEmpty()) {
			sb.append("\n");
		}
		sb.append(err);
		this.error = sb.toString();
	}

	public boolean isHalt() {
		return halt;
	}

	public void setHalt(boolean halt) {
		this.halt = halt;
	}

	private String getExceptionAsString(Exception e) {
		if (e != null) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			sw.flush();
			return sw.toString();
		} else {
			return "Null exception object: Can't get stacktrace as a string.";
		}
	}
}
