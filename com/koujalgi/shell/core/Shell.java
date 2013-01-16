package com.koujalgi.shell.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Shell {

	public static String read(String prompt) throws IOException {
		System.out.print(prompt);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String readString = null;
		try {
			readString = br.readLine();
		} catch (IOException ioe) {
			System.out.println("IO error. Terminating...");
			System.exit(1);
		}
		if (readString == null) {
			System.out.println("Nothing read! Terminating...");
			System.exit(1);
		}
		return readString;
	}

	/**
	 * @see <a
	 *      href="http://www.esus.com/docs/GetQuestionPage.jsp?uid=392">Shutdown
	 *      app by trapping ^C</a>
	 */
	public static void shutdown() {
		// Runtime.getRuntime().addShutdownHook(new Thread());
	}
	
	public static String readFileContents(String fileName) throws IOException {
		BufferedReader in = null;
		String ret = "";
		String str = "";
		try {
			in = new BufferedReader(new FileReader(fileName));
			while ((str = in.readLine()) != null) {
				ret += str + "\n";
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static void appendFile(File file, String content) throws IOException {
		if (file.exists()) {
			String filecontent = readFileContents(file.getAbsolutePath())
					+ "\n" + content;
			content = filecontent;
		} else {
			file.createNewFile();
		}
		PrintWriter out = new PrintWriter(new FileWriter(file), true);
		out.print(content);
		out.flush();
		out.close();
	}
}