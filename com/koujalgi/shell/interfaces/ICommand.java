package com.koujalgi.shell.interfaces;

import com.koujalgi.shell.core.Application;

public interface ICommand {

	public void setCommandString(String cmd);

	public String getBaseCommand();

	public String getUsage();

	public String getDescription();

	public boolean isValid();

	public boolean hasBaseCommand();

	public void setAppContext(Application app);

	public void terminate();

	public void execute();

	public void setEnvVar(Object variable, Object value);

	public Object getEnvVar(Object variable);
}
