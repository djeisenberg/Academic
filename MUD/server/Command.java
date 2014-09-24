package server;

/**
 * Abstract package access interface defines a command pattern for use by Server.
 * 
 * @author Daniel Eisenberg
 */
abstract class Command {

	public Command() {
	}

	/**
	 * Calls the implementing concrete Command object's execute method.
	 * 
	 */
	abstract void execute();
	
}
