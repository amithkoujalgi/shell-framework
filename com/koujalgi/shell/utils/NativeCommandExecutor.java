package com.koujalgi.shell.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NativeCommandExecutor {
	public static String execute(String cmd) throws IOException,
			InterruptedException {
		long timeNow = System.currentTimeMillis();
		Process p = Runtime.getRuntime().exec(cmd);
		p.waitFor();
		InputStreamReader is = new InputStreamReader(p.getInputStream());
		BufferedReader reader = new BufferedReader(is);
		String line = "", result = "";
		while ((line = reader.readLine()) != null) {
			result += line + "\n";
		}
		is.close();
		p.destroy();
		result += "Time taken: " + (System.currentTimeMillis() - timeNow)
				/ 1000 + " seconds.";
		return result;
	}
}
