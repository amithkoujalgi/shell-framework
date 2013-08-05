# Shell Framework

## What is this?

A framework to create your own shell in Java. 
You can add your custom shell commands into the shell application easily.

## Features

* **Definition flexibility**: Define your own commands easily by extending the AbstractCommand class.
* **Simple configuration**: single line to add a new command to the your shell application.
* **Clear, simple structure**: Designed to add new commands to the shell without having to worry about the phases of execution
* **Environment variables**: Set/get environment variables to/from the application's context.
* **Batch file execution**: Throw a file with the commands which your shell app supports and the app shall take care of it.
* **Maintain history**: Get the history of commands executed previously.
* **Logging**: Flexible logging functionality. Define your own logger by extending the AbstractLogger class and add it to the app.

## Installation/Integration
	1. Download/clone the repo into an appropriate folder
	2. Add custom commands in the src directory.
	3. Add command/s to the application.
	4. Run the application's interpreter.
	5. Reward yourself with a refreshing beverage. 

## Sample command integration

Adding a custom command is as easy as pie. It goes like this:
* If you want a command called 'test', you create the Test class.
* It has to extend the class AbstractCommand.
* It has to have a constructor with 2 arguments:

		String baseCommand
		int minParamsNeeded
		
* The constructor has to invoke the superclass' constructor:
 
		super(baseCommand, minParamsNeeded)
		
* It has to override 3 methods:

		public Result execute()
		public String getCommandUsageSyntax()
		public String getCommandDescription()
		public String getCommandUsageExamples()


* Lets add a custom command called Test (which is just to demonstrate the functionality of the shell app). So, the class Test looks like this:

		public class Test extends AbstractCommand {

			public Test(String baseCommand, int minParamsNeeded) {
				super(baseCommand, minParamsNeeded);
			}

			@Override
			public Result execute() throws Exception {
				Result r = new Result(this);
				try {
					int params = super.getCommandParser().getParamCount();
					if (params > 0) {
						super.getApplicationContext().showHelp();
					} else {
						r.setResult("This is just a simple test command.");
					}
				} catch (Exception e) {
					r.setError(new Exception("Error while running test command."));
				}
				return r;
			}

			@Override
			public String getCommandUsageSyntax() {
				return "test";
			}

			@Override
			public String getCommandDescription() {
				return "Runs the test command." + "\n"
						+ "This is a mere dummy command.";
			}

			@Override
			public String getCommandUsageExamples() {
				return "test";
			}

		}

* Now that you have a command class Test, lets add this to the shell application.

		public class AppMain {
			public static void main(String[] args) throws Exception {
				Application app = new Application();
				app.addCommand(new Test("test", 0)); // Add the command to app. 'test' is the base command and number of params it takes is 0.
				app.setStartupInfo("My shell v0.01");
				app.setPrompt("shell>"); // set your prompt text
				app.startInterpreter();
			}
		}
		
* Running a batch file:

	Write a batch file with the commands supported by your app and pass the file to the application context.
	
		public class AppMain {
			public static void main(String[] args) throws Exception {
				Application app = new Application();
				app.addCommand(new Test("test", 0)); // Add the command to app. 'test' is the base command and number of params it takes is 0.
				app.setHaltOnError(false); // if you want the app to halt on error, set it to true (default)
				app.executeBatch(new File("/path/to/test.batch"));
			}
		}
	
* And you're done! 
* Run the entry class and voila! You have your shell with your custom commands.