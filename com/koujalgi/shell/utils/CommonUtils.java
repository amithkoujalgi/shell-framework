package com.koujalgi.shell.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class CommonUtils {
	public static String getExceptionAsString(Exception e) {
		if (e != null) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			sw.flush();
			return sw.toString();
		} else {
			return "Null exception object: Can't get stacktrace as a string.";
		}
	}
}
