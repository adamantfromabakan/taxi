package iax.client.protocol.util;

/**
 * Exception thrown when ByteBuffer is unable to initialize or out of bounds.
 *
 */
public class ByteBufferException extends Exception {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = -5817958080358557231L;

	/**
	 * Constructor. Creates a ByteBufferException from a String.
	 * @param message Exception message.
	 */
    public ByteBufferException(String message) {
        super(message);
    }
    
    /**
	 * Constructor. Creates a ByteBufferException from an Exception.
	 * @param e Inherited exception.
	 */
    public ByteBufferException(Exception e) {
        super(e.getMessage());
    }
    
}
