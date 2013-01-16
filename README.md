# Shell Framework

## What is this?

A framework to create your own shell in Java. 
You can add your custom shell commands into the shell application easily.

## Features

* **Definition flexibility**: Define your own commands easily by extending Abstract command class.
* **Simple configuration**: 1 line to add a new command to the existing shell.
* **Clear, simple structure**: Designed to add new commands to the shell without having to worry about the phases of execution
* **Maintain history**: Get the history of commands executed previously.


## Installation/Integration
	1. Download/clone the repo into an appropriate folder
	2. Add custom commands in the src directory.
	3. Add command/s to the application.
	4. Run the application's interpreter.
	5. Reward yourself with a refreshing beverage. 

## Sample command integration

Adding a custom command is as easy as pie. It goes like this:
* If you want a command called 'curl', you create the Curl class in this way.
* It has to extend the class AbstractCommand.
* It has to have a constructor with 2 params:

		String baseCommand
		int params
		
* The constructor has to invoke the superclass' constructor:
 
		super(baseCommand, params)
		
* It has to override 3 methods:

		execute
		getDescription
		getUsage

	
* Lets add a custom command called Curl (which is a command-line http request tool in Linux). So, the class Curl looks like this:

		public class Curl extends AbstractCommand {
			public Curl(String baseCommand, int params) {
				super(baseCommand, params);
			}
			
			@Override
			public void execute() {
				System.out.println("running curl command...");
			}
	
			@Override
			public String getUsage() {
				return "curl [method] [host]";
			}
	
			@Override
			public String getDescription() {
				return "make http requests from command-line";
			}
		}

* Now that you have a command class Curl, lets add this to the shell application.

		public class AppMain {
			public static void main(String[] args) throws Exception {
				Application app = new Application();
				app.addCommand(new Curl("curl", 1)); // Add the command to app. Curl is the base command and numner of params it takes is 1
				app.setStartupInfo("Custom shell v0.01");
				app.setTempDirectory(new File("path\to\your temp\directory")); // set the temporary directory for saving history of previously executed commands
				app.setPrompt("shell>"); // set your prompt text
				app.interpret();
			}
		}
	
* And you're done! 
* Just run the main class and voila! You have your shell with your custom commands.

## Enhancements
* The shell app is not thread-safe. I shall start working on it soon.
* It doesn't allow you to pass variable number of params. While running a command, it has to have the number of params you had defined while creating the command