package com.koujalgi.shell.utils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropFile {
	private String propFile;

	public PropFile(String propFile) {
		this.propFile = propFile;
	}

	public String getProperty(String key) throws FileNotFoundException,
			IOException {
		Properties prop = new Properties();
		prop.load(new FileInputStream(propFile));
		return prop.getProperty(key);
	}

	public void setProperty(String key, String value)
			throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		prop.setProperty(key, value);
		prop.store(new FileOutputStream(propFile), null);
	}
}
