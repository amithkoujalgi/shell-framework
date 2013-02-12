package com.koujalgi.shell.core;

public class ParamParser {
	private String param;
	private AbstractCommand cmd;

	public ParamParser(String param, AbstractCommand c) {
		this.param = param;
		this.cmd = c;
	}

	/**
	 * Checks if the String is an env var. Env var starts with $
	 * 
	 * @return
	 */
	public boolean isEnvVar() {
		return param.startsWith("$");
	}

	/**
	 * Gets the env var string
	 * 
	 * @return
	 */
	public String getEnvVarString() {
		if (isEnvVar()) {
			return param.substring(1, param.length());
		} else {
			return null;
		}
	}

	/**
	 * Checks if the given env var present in the app's context
	 * 
	 * @param c
	 * @return
	 */
	public boolean isEnvVarPresent() {
		String envVarStr = getEnvVarString();
		if (envVarStr == null)
			return false;
		EnvironmentVariable v = cmd.getEnvVar(envVarStr);
		if (v != null) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the env var object from app's context if present
	 * 
	 * @return
	 */
	public EnvironmentVariable getEnvVar() {
		if (isEnvVarPresent()) {
			return cmd.getEnvVar(getEnvVarString());
		} else {
			return null;
		}
	}
}
