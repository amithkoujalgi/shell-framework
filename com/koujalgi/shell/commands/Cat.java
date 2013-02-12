package com.koujalgi.shell.commands;

import java.io.File;

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
		if (super.getCommandParser().getParamCount() > 1) {
			System.out.println("Cat command cannot have more than one param");
			return;
		}
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
		if (!new File(filepath).exists()) {
			System.out.println("File '" + filepath + "' does not exist.");
			return;
		}
		if (new File(filepath).isDirectory()) {
			System.out
					.println("The given path is a directory. You cannot read a directory.");
			return;
		}
		try {
			System.out.println("Reading file: '" + filepath + "'\n"
					+ FileUtils.getFileContents(filepath));
		} catch (Exception e) {
			System.out.println("Error reading file: '" + filepath + "'");
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
