package com.koujalgi.shell.test;

import com.koujalgi.shell.entities.Result;
import com.koujalgi.shell.interfaces.AbstractLogger;

public class TestLogger extends AbstractLogger {
	@Override
	public void logInfo(Object info) {
		System.out.println("[Info] - " + info);
	}

	@Override
	public void logError(Object error) {
		System.err.println("[Error] - " + error);
	}

	@Override
	public void logResult(Result previousResult) {
		System.out.println("[Result] - " + previousResult);
	}
}
