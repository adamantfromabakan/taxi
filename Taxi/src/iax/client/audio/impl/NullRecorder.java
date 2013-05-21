package iax.client.audio.impl;

import iax.client.audio.AudioListener;
import iax.client.audio.Recorder;
import iax.client.audio.RecorderException;

/**
 * GSM audio recorder.
 */
public class NullRecorder extends Recorder {

	/**
	 * Constructor. Initializes recorder.
	 * @throws RecorderException
	 */
	public NullRecorder() throws RecorderException {
	}

	public void stop(){
	}

	public void record(AudioListener al) {
	}
}
