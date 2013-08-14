package com.koujalgi.shell.core;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import com.koujalgi.shell.commands.internal.Exit;
import com.koujalgi.shell.commands.internal.Help;
import com.koujalgi.shell.entities.EnvironmentVariable;
import com.koujalgi.shell.entities.Result;
import com.koujalgi.shell.exceptions.InvalidCommandException;
import com.koujalgi.shell.exceptions.NoCommandAddedException;
import com.koujalgi.shell.exceptions.NullResultException;
import com.koujalgi.shell.interfaces.AbstractCommand;
import com.koujalgi.shell.interfaces.AbstractLogger;
import com.koujalgi.shell.interfaces.IApplication;
import com.koujalgi.shell.util.DefaultLogger;
import com.koujalgi.shell.util.IOUtil;

public class Application implements IApplication {
	private ArrayList<Result> previousResults = new ArrayList<Result>();
	private ArrayList<EnvironmentVariable> environmentVariables = new ArrayList<EnvironmentVariable>();
	private ArrayList<AbstractCommand> commandList = new ArrayList<AbstractCommand>();
	private AbstractLogger logger = new DefaultLogger();
	private boolean terminate = false;
	private boolean haltOnError = true;
	private String prompt = ">";
	private float batchCompletionStatus = 0;
	private String instanceId = "";
	private String startUpInfo = "My Shell v0.1";

	public Application() throws Exception {
		instanceId = UUID.randomUUID().toString();
		init();
	}

	public void setStartUpInfo(String startUpInfo) {
		this.startUpInfo = startUpInfo;
	}

	private void showStartUpInfo() {
		System.out.println(startUpInfo);
	}

	public String getInstanceId() {
		return instanceId;
	}

	private void init() throws Exception {
		logger.setApplicationContext(this);
		addCommand(new Exit("exit", 0));
		addCommand(new Help("help", 0));
	}

	public void setPrompt(String prompt) {
		if (prompt != null) {
			if (!prompt.trim().equals("")) {
				this.prompt = prompt + ">";
			}
		}
	}

	public float getBatchCompletionStatus() {
		return batchCompletionStatus;
	}

	/**
	 * If set to true, the application will halt if Result object of the command
	 * being executed has 'halt'=true, else the app will continue to execute the
	 * further commands.
	 * 
	 * @param haltOnError
	 */
	public void setHaltOnError(boolean haltOnError) {
		this.haltOnError = haltOnError;
	}

	public void setLogger(AbstractLogger logger) {
		logger.setApplicationContext(this);
		this.logger = logger;
	}

	public ArrayList<EnvironmentVariable> getEnvironmentVariables() {
		return environmentVariables;
	}

	public void setEnvironmentVariables(
			ArrayList<EnvironmentVariable> environmentVariables) {
		this.environmentVariables = environmentVariables;
	}

	public void addEnvironmentVariable(EnvironmentVariable var)
			throws Exception {
		if (environmentVariables.isEmpty()) {
			environmentVariables.add(var);
		} else {
			for (EnvironmentVariable v : environmentVariables) {
				if (var.getKey().equals(v.getKey())) {
					// throw new Exception("Environment variable '" + v.getKey()
					// + "' already exists.");
					v.setValue(var.getValue());
					return;
				}
			}
			environmentVariables.add(var);
		}
	}

	public EnvironmentVariable getEnvironmentVariable(String key)
			throws Exception {
		for (EnvironmentVariable v : environmentVariables) {
			if (key.equals(v.getKey())) {
				return v;
			}
		}
		return null;
	}

	@Override
	public void startInterpreter() throws Exception {
		isGoodToGo();
		showStartUpInfo();
		String commandRead = "";
		while (!terminate) {
			commandRead = IOUtil.readFromStdIn(prompt).trim();
			try {
				processCommandString(commandRead);
			} finally {
				logger.logResult(getPreviousResult());
			}
		}
	}

	private void processCommandString(String command) throws Exception {
		try {
			command = command.trim();
			if (command.equals("")) {
				return;
			}
		} catch (Exception e1) {
			logger.logError(e1);
		}
		// process command from string read from inputstream
		AbstractCommand cmd = parseCommand(command);
		if (cmd != null) {
			Result r = executeCommand(cmd);
			if (r == null) {
				throw new NullResultException(command);
			}
			previousResults.add(r);
		} else {
			Result r = new Result(null);
			r.setError(new InvalidCommandException("'" + command
					+ "' is invalid."));
			previousResults.add(r);
			logger.logError("Invalid command: '" + command + "'\n");
			logger.logError("Type in 'help' to view the list of commands supported."
					+ "\n");
		}
	}

	private AbstractCommand parseCommand(String commandString) {
		AbstractCommand execCmd = null;
		for (AbstractCommand cmd : commandList) {
			cmd.setCommandString(commandString);
			// cmd.setAppContext(this);
			if (cmd.hasBaseCommand()) {
				execCmd = cmd;
				break;
			}
		}
		return execCmd;
	}

	private Result executeCommand(AbstractCommand cmd) throws Exception {
		// set application's context to the command so that the command can make
		// use of application's features and references
		cmd.setApplicationContext(this);
		// invoke command's execute method
		Result commandExecResult = null;
		try {
			commandExecResult = cmd.execute();
		} catch (Exception e) {
			commandExecResult = new Result(cmd);
			commandExecResult.setError(e);
		}
		if (commandExecResult != null) {
			if (commandExecResult.getError() != null) {
				logger.logError(commandExecResult.getError());
			}
			if (commandExecResult.getResult() != null) {
				logger.logInfo(commandExecResult.getResult());
			}
		} else {
			throw new Exception("Result cannot be null");
		}
		return commandExecResult;
	}

	@Override
	public void executeBatch(File batchFilePath) throws Exception {
		if (!batchFilePath.canRead()) {
			throw new Exception("Error reading batch file: "
					+ batchFilePath.getAbsolutePath());
		}
		String contents = IOUtil.readFile(batchFilePath);
		contents = contents.replaceAll("\r", "\n").replaceAll("\n+", "\n");
		String commandArray[] = contents.split("\n");
		ArrayList<String> cmdList = new ArrayList<String>();
		for (int i = 0; i < commandArray.length; i++) {
			if (!commandArray[i].trim().equals("")) {
				cmdList.add(commandArray[i].trim());
			}
		}
		int numCommands = cmdList.size();
		batchCompletionStatus = 0;
		for (int i = 0; i < cmdList.size(); i++) {
			if (terminate) {
				break;
			}
			int currentCommand = (i + 1);
			String runCmd = cmdList.get(i);
			try {
				logger.logInfo("-------------------------------------------"
						+ "\n" + "<<Running command " + currentCommand + ">>: "
						+ runCmd + "\n");
				processCommandString(runCmd);
			} finally {
				batchCompletionStatus = ((float) currentCommand / (float) numCommands) * 100;
				Result r = getPreviousResult();
				logger.logResult(r);
				if (haltOnError) {
					if (r != null) {
						if (r.isHalt()) {
							logger.logError("Encountered error. Breaking execution");
							break;
						}
					}
				} else {
					continue;
				}
			}
		}
	}

	public Result getPreviousResult() {
		if (previousResults == null) {
			return null;
		}
		if (previousResults.isEmpty()) {
			return null;
		} else {
			int idx = previousResults.size() - 1;
			return previousResults.get(idx);
		}
	}

	private boolean isGoodToGo() throws Exception {
		if (commandList == null) {
			throw new NoCommandAddedException(
					"No commands added to application. Not initialized?");
		}
		if (commandList.isEmpty()) {
			throw new NoCommandAddedException(
					"No commands added to application. Cannot proceed until a command is added");
		}
		return true;
	}

	@Override
	public void addCommand(AbstractCommand cmd) throws Exception {
		if (cmd == null) {
			throw new InvalidCommandException(
					"Null cannot be added as a command");
		} else {
			if (doesCommandExist(cmd)) {
				throw new Exception(
						"Command "
								+ cmd.getClass()
								+ " already exists. Another instance of the same command type cannot be added to the application context.");
			}
			commandList.add(cmd);
		}
	}

	private boolean doesCommandExist(AbstractCommand cmd) {
		for (AbstractCommand c : commandList) {
			if (c.getClass() == cmd.getClass()) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<AbstractCommand> getCommandList() {
		return commandList;
	}

	public void terminate() {
		this.terminate = true;
	}

	public void showHelp() {
		logger.logInfo("--------------------------------------");
		logger.logInfo("|              Commands              |");
		logger.logInfo("--------------------------------------");
		for (int i = 0; i < commandList.size(); i++) {
			AbstractCommand c = commandList.get(i);
			logger.logInfo((i + 1) + ". [" + c.getBaseCommand() + "]\n\t"
					+ c.getCommandDescription());
		}
	}

	public void showHelpForCommand(String commandString) {
		AbstractCommand cmd = parseCommand(commandString);
		for (AbstractCommand cm : commandList) {
			if (cm.getBaseCommand().equals(cmd.getBaseCommand())) {
				logger.logInfo("Command: " + cmd.getBaseCommand());
				logger.logInfo("\n\tDescription: " + cm.getCommandDescription());
				logger.logInfo("\n\tSyntax: " + cm.getCommandUsageSyntax());
				logger.logInfo("\n\tExamples: " + cm.getCommandUsageExamples());
				break;
			}
		}
	}

	public void showPreviousResults() {
		if (previousResults.size() == 0) {
			return;
		}
		logger.logInfo("-------------Previous results-------------");
		for (int i = (previousResults.size() - 1); i >= 0; i--) {
			Result r = previousResults.get(i);
			logger.logInfo((i + 1) + ".");
			if (r.getCommand() != null) {
				logger.logInfo("\t" + "Command String: "
						+ r.getCommand().getCommandString());
			}
			if (r.getResult() != null) {
				logger.logInfo("\t" + "Result: " + r.getResult());
			}
			if (r.getError() != null) {
				logger.logInfo("\t" + "Error: " + r.getError());
			}
		}
		logger.logInfo("-------------End of previous results-------------");
	}
}
