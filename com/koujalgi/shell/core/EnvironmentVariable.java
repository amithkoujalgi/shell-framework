package com.koujalgi.shell.core;

public class EnvironmentVariable {
	private Object value, variableName;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getVariableName() {
		return variableName;
	}

	public void setVariableName(Object variableName) {
		this.variableName = variableName;
	}

}
