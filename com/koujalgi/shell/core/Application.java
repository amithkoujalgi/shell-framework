package com.koujalgi.shell.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.koujalgi.shell.commands.Batch;
import com.koujalgi.shell.commands.Cat;
import com.koujalgi.shell.commands.Echo;
import com.koujalgi.shell.commands.Env;
import com.koujalgi.shell.commands.Exit;
import com.koujalgi.shell.commands.Help;
import com.koujalgi.shell.commands.History;
import com.koujalgi.shell.commands.Now;
import com.koujalgi.shell.commands.Set;
import com.rzt.shell.exceptions.InvalidCommandException;
import com.rzt.shell.exceptions.InvalidCommandFormatException;
import com.rzt.shell.exceptions.NullCommandException;
import com.rzt.shell.exceptions.UnbalancedQuotesException;

public class Application {
	private ArrayList<AbstractCommand> commands = new ArrayList<AbstractCommand>();
	private ArrayList<EnvironmentVariable> envVars = new ArrayList<EnvironmentVariable>();
	private ArrayList<String> history = new ArrayList<String>();
	private String prompt = "Shell>";
	private String startupInfo = "Shell v.01" + "\n"
			+ "Type help to view usage";
	private boolean terminate = false;
	private boolean historyEnabled = true;

	/**
	 * add a command to shell
	 */
	public void addCommand(AbstractCommand cmd) throws Exception {
		if (cmd == null) {
			throw new NullCommandException("Cant add a null command");
		} else {
			commands.add(cmd);
		}
	}

	/**
	 * get application context
	 */
	public Application getAppContext() {
		return this;
	}

	/**
	 * get commands list
	 */
	public ArrayList<AbstractCommand> getCommands() {
		return commands;
	}

	/**
	 * get environment variables list
	 */
	public ArrayList<EnvironmentVariable> getEnvironmentVariables() {
		return envVars;
	}

	/**
	 * Disables logging of history
	 */
	public void disableHistory() {
		this.historyEnabled = false;
	}

	/**
	 * kill the shell interpreter
	 */
	public void terminate() {
		this.terminate = true;
	}

	/**
	 * set start up message
	 */
	public void setStartupInfo(String startupInfo) {
		this.startupInfo = startupInfo;
	}

	/**
	 * set prompt string
	 */
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	/**
	 * get command history list
	 */
	public ArrayList<String> getHistory() {
		return history;
	}

	private void writeHistory(String content) {
		File logDir = new File("logs");
		if (!logDir.exists()) {
			logDir.mkdir();
		}
		File histroyFile = new File(logDir.getAbsolutePath() + File.separator
				+ "command-history.txt");
		try {
			ShellIO.appendFile(histroyFile, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showStartUpInfo() {
		System.out.println(startupInfo + "\n");
	}

	/**
	 * start the shell interpreter
	 */
	public void interpret() throws IOException {
		init();
		showStartUpInfo();
		do {
			try {
				String read = ShellIO.read(prompt);
				if (read == null) {
					continue;
				}
				if (read.replace("\\s", "").replace("\\t", "").trim()
						.equals("")) {
					continue;
				}
				if (historyEnabled) {
					history.add(read);
					writeHistory(read);
				}
				AbstractCommand cmd = find(read);
				exec(cmd);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		} while (!terminate);
	}

	/**
	 * Add initial/mandatory commands here
	 */
	private void init() {
		commands.add(new Batch("batch", 1));
		History hist = new History("history", 0);
		hist.setAppContext(this);
		if (historyEnabled) {
			commands.add(hist);
		}
		commands.add(new Exit("exit", 0));
		Help help = new Help("help", 0);
		help.setCommands(commands);
		commands.add(new Now("now", 0));
		commands.add(new Set("set", 2));
		commands.add(new Env("env", 1));
		commands.add(new Echo("echo", 1));
		commands.add(new Cat("cat", 1));
		commands.add(help);
	}

	private void exec(AbstractCommand command) throws Exception {
		if (command == null) {
			throw new InvalidCommandException();
		} else if (!command.getCommandParser().hasBalancedQuotes()) {
			throw new UnbalancedQuotesException();
		} else if (command.isValid()) {
			command.execute();
		} else {
			throw new InvalidCommandFormatException(command.getUsage());
		}
	}

	private AbstractCommand find(String command) throws Exception {
		AbstractCommand execCmd = null;
		for (AbstractCommand cmd : commands) {
			cmd.setCommandString(command);
			cmd.setAppContext(this);
			if (cmd.hasBaseCommand()) {
				execCmd = cmd;
				break;
			}
		}
		return execCmd;
	}
}
