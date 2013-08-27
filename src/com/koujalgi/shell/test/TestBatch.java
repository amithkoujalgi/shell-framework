package com.koujalgi.shell.test;

import java.io.File;

import com.koujalgi.shell.core.Application;

public class TestBatch {
	public static void main(String[] args) throws Exception {
		Application app = new Application();
		app.addCommand(new Test("test", 0));
		app.setPrompt("RZT");
		app.setLogger(new TestLogger());
		app.setHaltOnError(false);
		app.executeBatch(new File("./batch/test.batch"));
	}
}
