package com.koujalgi.shell.commands;

import com.koujalgi.shell.core.AbstractCommand;
import com.koujalgi.shell.core.EnvironmentVariable;
import com.koujalgi.shell.core.ParamParser;

public class Echo extends AbstractCommand {

	public Echo(String baseCommand, int params) {
		super(baseCommand, params);
	}

	@Override
	public void execute() {
		String str = super.getCommandParser().getParams().get(0);
		ParamParser p = new ParamParser(str, this);
		if (p.isEnvVar()) {
			EnvironmentVariable v = p.getEnvVar();
			if (v == null) {
				System.out.println("env var '" + p.getEnvVarString()
						+ "' not set");
				return;
			} else {
				System.out.println((String) v.getValue());
			}
		} else {
			System.out.println(str);
		}
	}

	@Override
	public String getDescription() {
		return "echoes to console";
	}

	@Override
	public String getUsage() {
		return "echo [string/env var]";
	}

}
