package exceptions;

/**
 * An exception thrown when an I/O exception prevents the loading of crucial Server files.
 * 
 * @author Daniel Eisenberg
 *
 */
public class ServerFileException extends RuntimeException {

	public ServerFileException(String string) {
		super(string);
	}

	private static final long serialVersionUID = 1L;

}
