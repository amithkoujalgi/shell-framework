package com.koujalgi.shell.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class IOUtil {
	public static String readFromStdIn(String prompt) throws Exception {
		System.out.print(prompt);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String readString = null;
		try {
			readString = br.readLine();
		} catch (IOException ioe) {
			throw new Exception("IO error. Terminating...");
		}
		if (readString == null) {
			throw new Exception("Recieved termination signal. Terminating...");
		}
		return (readString);
	}

	public static String readFile(File filepath) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(filepath
				.getAbsolutePath()));
		return Charset.defaultCharset().decode(ByteBuffer.wrap(encoded))
				.toString();
	}

	public void writeToFile(File filename, String contents) throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		String str = "";
		if (contents == null) {
			lines.add(str);
		} else {
			str = contents.replaceAll("\r", "\n");
			String linesArr[] = str.split("\n");
			for (String l : linesArr) {
				lines.add(l);
			}
		}
		Path path = Paths.get(filename.getAbsolutePath());
		try (BufferedWriter writer = Files.newBufferedWriter(path,
				StandardCharsets.UTF_8)) {
			for (String line : lines) {
				writer.write(line);
				writer.newLine();
			}
		}
	}
}