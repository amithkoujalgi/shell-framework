package com.koujalgi.shell.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

public class ShellIO {

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
			System.out.println("Recieved termination signal. Terminating...");
			System.exit(1);
		}
		String transformed = readString.replace("\\\\", "\\")
				.replace("\\N", "\\\\N").replace("\\T", "\\\\T")
				.replace("\\B", "\\\\B").replace("\\F", "\\\\F")
				.replace("\\R", "\\\\R").replace("\\U", "\\\\U")
				.replace("\\i", "\\\\i").replace("\\'", "\\\\'");
		return (transformed);
		// return readString;
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
		FileOutputStream fop = new FileOutputStream(file);
		byte[] contentInBytes = Files.readAllBytes(file.toPath());
		fop.write(contentInBytes);
		fop.flush();
		fop.close();
	}
}