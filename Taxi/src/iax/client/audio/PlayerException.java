package iax.client.audio;

/**
 * Exception thrown when player is unable to initialize.
 *
 */
public class PlayerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3788584884960983608L;

	/**
	 * Constructor. Creates a PlayerException from a String.
	 * @param message Exception message.
	 */
	public PlayerException(String message) {
		super(message);
	}

	/**
	 * Constructor. Creates a PlayerException from an Exception.
	 * @param e Inherited exception.
	 */
	public PlayerException(Exception e) {
		super(e.getMessage());
	}


}
