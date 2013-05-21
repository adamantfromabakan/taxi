package iax.client.audio.impl;

import iax.client.audio.AudioFactory;
import iax.client.audio.Player;
import iax.client.audio.PlayerException;
import iax.client.audio.Recorder;
import iax.client.audio.RecorderException;

public class NullAudioFactory implements AudioFactory {
    /**
     * ULAW codec.
     */
    public static final long ULAW_V = 4;
    /**
     * Voice frame with codec ULAW
     */
    public static final int ULAW_SC = 4;

	public Player createPlayer() throws PlayerException {
		return new NullPlayer();
	}

	public Recorder createRecorder() throws RecorderException {
		return new NullRecorder();
	}

	public long getCodec() {
		return ULAW_V;
	}

	public int getCodecSubclass() {
		return ULAW_SC;
	}

}
