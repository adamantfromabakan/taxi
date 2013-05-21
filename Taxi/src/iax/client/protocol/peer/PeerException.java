package iax.client.protocol.peer;

/**
 * Exception thrown when peer is unable to get a given call.
 *
 */
public class PeerException extends Exception {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 8071280439100516112L;

	/**
	 * Constructor. Creates a PeerException from a String.
	 * @param message Exception message.
	 */
    public PeerException(String message) {
        super(message);
    }
    
    /**
	 * Constructor. Creates a PeerException from an Exception.
	 * @param e Inherited exception.
	 */
    public PeerException(Exception e) {
        super(e.getMessage());
    }
}
