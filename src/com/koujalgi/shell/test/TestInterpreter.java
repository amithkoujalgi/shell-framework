package com.koujalgi.shell.test;

import com.koujalgi.shell.core.Application;
import com.koujalgi.shell.entities.EnvironmentVariable;

public class TestInterpreter {
	public static void main(String[] args) throws Exception {
		Application app = new Application();
		app.addCommand(new Test("test", 0));
		app.setLogger(new TestLogger());
		System.out.println("App instance: " + app.getInstanceId());
		app.addEnvironmentVariable(new EnvironmentVariable("testenv", "abc"));
		app.addEnvironmentVariable(new EnvironmentVariable("myenv", "123"));
		app.setPrompt("RZT");
		app.startInterpreter();
	}
}
