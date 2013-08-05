package com.koujalgi.shell.interfaces;

import java.io.File;

public interface IApplication {
	public void addCommand(AbstractCommand cmd) throws Exception;

	public void startInterpreter() throws Exception;

	public void executeBatch(File batchFilePath) throws Exception;
}
