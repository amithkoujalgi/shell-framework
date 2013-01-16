package com.koujalgi.shell.core;

import java.util.ArrayList;

public class CommandParser {
	private String command;

	public CommandParser(String command) {
		this.command = cleanText(command);
	}

	private String cleanText(String text) {
		return text.replaceAll("\\s+", " ").replaceAll("\\t+", " ").trim();
	}

	public int getParamCount() {
		if (command == null) {
			return -1;
		} else if (command.equals("")) {
			return -1;
		} else if (command.contains(" ")) {
			String[] cmds = command.split(" ");
			return cmds.length - 1;
		}
		return 0;
	}

	/**
	 * Get the command context. The first word of the command <br/>
	 * <b>Ex:</b> 'move' src target ('move' is the command)
	 * 
	 * @return The command issued in the shell.
	 */
	public String getBaseCommand() {
		if (getParamCount() == 0) {
			return command;
		} else if (getParamCount() > 0) {
			return command.split(" ")[0];
		} else
			return null;
	}

	public ArrayList<String> getParams() {
		if (getParamCount() > 0) {
			ArrayList<String> params = new ArrayList<String>();
			String[] tmp = command.split(" ");
			for (int i = 0; i < tmp.length; i++) {
				if (i == 0) {
					continue;
				}
				params.add(tmp[i]);
			}
			return params;
		} else
			return null;
	}

	public boolean hasParams() {
		return getParamCount() > 1;
	}
}
