package com.koujalgi.shell.main;

import java.io.File;

import com.koujalgi.shell.core.Application;

public class Main {
	public static void main(String[] args) throws Exception {
		Application app = new Application();
		System.out.println("App instance: " + app.getInstanceId());
		// app.addEnvironmentVariable(new EnvironmentVariable("testenv",
		// "abc"));
		// app.addEnvironmentVariable(new EnvironmentVariable("myenv", "123"));
		// for (EnvironmentVariable v : app.getEnvironmentVariables()) {
		// System.out.println(v.getKey() + " " + v.getValue());
		// }
		// app.setPrompt("RZT");
		// app.startInterpreter();
		app.setHaltOnError(false);
		app.executeBatch(new File("C:/Users/administrator/Desktop/test.batch"));
	}
}
