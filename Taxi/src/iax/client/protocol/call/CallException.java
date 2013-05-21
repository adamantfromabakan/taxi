package iax.client.protocol.call;

/**
 * Call's exception
 */
public class CallException extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -3541431681435008525L;

	/**
     * Constructor from a message
     * @param message message of the exception
     */
    public CallException(String message) {
        super(message);
    }
    
    /**
     * Contructor from another exception
     * @param e another exception
     */
    public CallException(Exception e) {
        super(e.getMessage());
    }
}
