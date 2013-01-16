package com.koujalgi.shell.test;

import java.io.File;

import com.koujalgi.shell.core.Application;

public class AppMain {
	public static void main(String[] a) throws Exception {
		Application app = new Application();
		app.setTempDirectory(new File("C:\\Users\\Amith\\Desktop\\temp"));
		app.setPrompt("shell>");
		app.interpret();
	}
}
