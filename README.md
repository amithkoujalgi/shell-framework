shell-framework
===============

A framework to create your own shell in Java.
You can add your custom shell commands into the shell application easily.

Adding a custom command is as easy as pie. It goes like this:

If you want a command called 'echo', you create the Echo class in this way.
It has to extend the class AbstractCommand.
It has to have a constructor with 2 params:
- String baseCommand
- int params
The constructor has to invoke the superclass' constructor: super(baseCommand, params)

It has to override 3 methods:
- execute
- getDescription
- getUsage

So, the class Echo looks like:

public class Echo extends AbstractCommand {
        // this constructor is, mandatory!
 public Echo (String baseCommand, int params) {
 super(baseCommand, params);
 }

 @Override
 public void execute() {
 String echoText = super.getCommandParser().getParams().get(0);
 System.out.println(echoText);
 }

 @Override
 public String getDescription() {
 return "echoes the text to the console";
 }

 @Override
 public String getUsage() {
 return "echo [text-to-be-echoed]";
 }
}

Now that you have a command class Echo, lets add this to the shell.

public class AppMain {
	public static void main(String[] args) throws Exception {
		Application app = new Application();
		// add the command to your shell app
		// 'echo' is the base command (the text with which the command should start)
		// 1 is the nummber of parameters it should have. Here, echo command takes one param (the text it should echo)
		app.addCommand(new Echo("echo", 1));
		// you can add more commands too
		// set the temp directory path. This is for saving the history of the previously executed commands
		app.setTempDirectory(new File("path/to/temporary/directory"));
		// set the prompt text. (Optional)
		app.setPrompt("shell>");
		// all set! start interpreting the commands right away!
		app.interpret();
	}
}

And you're done! 
Just run the main class and voila! You have your shell with your custom commands.
