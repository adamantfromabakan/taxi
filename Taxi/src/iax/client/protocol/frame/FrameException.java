package iax.client.protocol.frame;

/**
 * Frame's exception
 */
public class FrameException extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -7674905203437074253L;

	/**
     * Constructor from a message
     * @param message message of the exception
     */
    public FrameException(String message) {
        super(message);
    }
    
    /**
     * Contructor from another exception
     * @param e another exception
     */
    public FrameException(Exception e) {
        super(e.getMessage());
    }
}
