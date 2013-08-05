package com.koujalgi.shell.commands.internal;

import com.koujalgi.shell.entities.Result;
import com.koujalgi.shell.interfaces.AbstractCommand;

public class Exit extends AbstractCommand {

	public Exit(String baseCommand, int minParamsNeeded) {
		super(baseCommand, minParamsNeeded);
	}

	@Override
	public Result execute() throws Exception {
		Result r = new Result(this);
		r.setResult("Terminating the shell...");
		//super.getApplicationContext().terminate();
		return r;
	}

	@Override
	public String getCommandUsageSyntax() {
		return "exit";
	}

	@Override
	public String getCommandDescription() {
		return "Exit from the shell";
	}

	@Override
	public String getCommandUsageExamples() {
		return "exit";
	}

}
