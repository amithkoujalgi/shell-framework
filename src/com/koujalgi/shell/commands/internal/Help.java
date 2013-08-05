package com.koujalgi.shell.commands.internal;

import com.koujalgi.shell.entities.Result;
import com.koujalgi.shell.interfaces.AbstractCommand;

public class Help extends AbstractCommand {

	public Help(String baseCommand, int minParamsNeeded) {
		super(baseCommand, minParamsNeeded);
	}

	@Override
	public Result execute() throws Exception {
		Result r = new Result(this);
		//r.setResult("");
		int params = super.getCommandParser().getParamCount();
		if (params > 0) {
			super.getApplicationContext().showHelpForCommand(
					super.getCommandParser().getParams().get(0));
		} else {
			super.getApplicationContext().showHelp();
		}
		return r;
	}

	@Override
	public String getCommandUsageSyntax() {
		return "help [command]";
	}

	@Override
	public String getCommandDescription() {
		return "Shows help";
	}

	@Override
	public String getCommandUsageExamples() {
		return "help" + "\nhelp copy";
	}

}
