package com.koujalgi.shell.main;

import com.koujalgi.shell.entities.Result;
import com.koujalgi.shell.interfaces.AbstractCommand;

public class Test extends AbstractCommand {

	public Test(String baseCommand, int minParamsNeeded) {
		super(baseCommand, minParamsNeeded);
	}

	@Override
	public Result execute() throws Exception {
		Result r = new Result(this);
		r.appendResult("this is ok");
		r.appendResult("this is ok again");
		r.appendError("this went wrong");
		r.appendError("this went wrong again");
		r.appendError(new Exception("this is severe"));
		return r;
	}

	@Override
	public String getCommandUsageSyntax() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCommandDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCommandUsageExamples() {
		// TODO Auto-generated method stub
		return null;
	}

}
