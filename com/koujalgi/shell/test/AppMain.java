package com.koujalgi.shell.test;

import com.koujalgi.shell.core.Application;

public class AppMain {
	public static void main(String[] a) throws Exception {
		Application app = new Application();
		app.setPrompt("shell>");
		app.interpret();
	}
}
