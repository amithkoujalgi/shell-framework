package com.koujalgi.shell.commands;

import com.koujalgi.shell.core.AbstractCommand;
import com.koujalgi.shell.core.EnvironmentVariable;
import com.koujalgi.shell.core.ParamParser;
import com.koujalgi.shell.utils.FileUtils;

public class Cat extends AbstractCommand {

	public Cat(String baseCommand, int params) {
		super(baseCommand, params);
	}

	@Override
	public void execute() {
		String filepath = super.getCommandParser().getParams().get(0);
		ParamParser p = new ParamParser(filepath, this);
		if (p.isEnvVar()) {
			EnvironmentVariable v = p.getEnvVar();
			if (v == null) {
				System.out.println("env var '" + p.getEnvVarString()
						+ "' not set");
				return;
			} else {
				filepath = (String) v.getValue();
			}
		}
		try {
			System.out.println(FileUtils.getFileContents(filepath));
		} catch (Exception e) {
			System.out.println("Error reading file");
		}
	}

	@Override
	public String getDescription() {
		return "view contents of the file";
	}

	@Override
	public String getUsage() {
		return "cat [filename]";
	}

}
