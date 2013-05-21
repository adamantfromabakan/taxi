package iax.client.audio.impl;

import iax.client.audio.Player;
import iax.client.audio.PlayerException;


/**
 * GSM audio player.
 */
public class NullPlayer extends Player {

	/**
	 * Constructor. Initializes player.
	 * @throws PlayerException
	 */
	public NullPlayer() throws PlayerException {
		super(Player.JITTER_BUFFER);
	}
	
	public void play() {
	}

	public void stop() {
	}

	public void write(long timestamp, byte[] audioData, boolean absolute) {
	}

}
