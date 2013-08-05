package com.koujalgi.shell.interfaces;

import com.koujalgi.shell.core.Application;
import com.koujalgi.shell.entities.Result;

public abstract class AbstractLogger {
	private Application applicationContext;

	public void setApplicationContext(Application applicationContext) {
		this.applicationContext = applicationContext;
	}

	public Application getApplicationContext() {
		return applicationContext;
	}

	public void logInfo(Object info) {
		System.out.println(info);
	}

	public void logError(Object error) {
		if (error instanceof Exception) {
			Exception e = (Exception) error;
			e.printStackTrace();
		} else {
			System.err.println(error);
		}
	}

	/**
	 * Use this method to persist Result in a database or a file. Write the
	 * logging code here.
	 * 
	 * @param previousResult
	 */
	public void logResult(Result previousResult) {
		float progress = 0;
		progress = getApplicationContext().getBatchCompletionStatus();
		System.out.println("Progress: " + (int) progress + "%");
		if (previousResult == null) {
			return;
		}
		System.out.println("Saving previous result: [Cmd: "
				+ previousResult.getCommand() + "] [Result: "
				+ previousResult.getResult() + "] [Error: "
				+ previousResult.getError() + "]");
	}
}
