package iax.client.audio;

/**
 * Exception thrown when recorder is unable to initialize.
 *
 */
public class RecorderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 258042188678481787L;

	/**
	 * Constructor. Creates a RecorderException from a String.
	 * @param message Exception message.
	 */
    public RecorderException(String message) {
        super(message);
    }
    
    /**
	 * Constructor. Creates a RecorderException from an Exception.
	 * @param e Inherited exception.
	 */
    public RecorderException(Exception e) {
        super(e.getMessage());
    }
    
}
