package com.koujalgi.shell.core;

import java.io.File;
import java.util.ArrayList;

import com.koujalgi.shell.core.utils.FileUtils;

public class BatchExecutor {
	private ArrayList<AbstractCommand> commands = new ArrayList<AbstractCommand>();
	private String prompt = "#";
	private Application appContext;
	private File batchFile;

	public BatchExecutor(File batchFile, ArrayList<AbstractCommand> commands,
			Application appContext) {
		this.batchFile = batchFile;
		this.commands = commands;
		this.appContext = appContext;
	}

	public void execute() {
		String[] cmdArray = getCmdArray();
		if (cmdArray == null) {
			return;
		} else {
			System.out.println("Executing batch file "
					+ batchFile.getAbsolutePath());
			for (String c : cmdArray) {
				c = c.replaceAll("\\t+", " ").replaceAll("\\s+", " ").trim();
				if (c.equals("")) {
					continue;
				} else {
					System.out.println(prompt + c);
					AbstractCommand cmd = null;
					try {
						cmd = find(c);
						exec(cmd);
					} catch (Exception e) {
						System.out.println("[Error]: Failed to run command '"
								+ c + "'");
						// System.out.println("Why: " + e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}
	}

	private String[] getCmdArray() {
		String[] cmds = null;
		try {
			cmds = FileUtils.getFileContents(this.batchFile.getAbsolutePath())
					.split("\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cmds;
	}

	private void exec(AbstractCommand command) throws Exception {
		if (command == null) {
			throw new Exception("Invalid command");
		} else if (command.isValid()) {
			command.execute();
		} else {
			System.out.println("Command format invalid");
			System.out.println("Usage: " + command.getUsage());
		}
	}

	private AbstractCommand find(String command) throws Exception {
		AbstractCommand execCmd = null;
		for (AbstractCommand cmd : commands) {
			cmd.setCommandString(command);
			cmd.setAppContext(this.appContext);
			if (cmd.hasBaseCommand()) {
				execCmd = cmd;
				break;
			}
		}
		return execCmd;
	}
}
