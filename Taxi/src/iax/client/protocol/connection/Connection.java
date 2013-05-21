package iax.client.protocol.connection;

import iax.client.protocol.peer.Peer;

public interface Connection {

	// Maximun length of the udp buffer for receiving frames
	static final int BUFFER_LENGTH = 4096;
    // Port udp of the asterisk host
//	static final int IAX_PORT = 4569;

	/**
	 * Starts the thread for receiving frames
	 */
	public abstract void start(Peer peer,String host, int port);

	/**
	 * Stops the thread for receiving frames
	 */
	public abstract void stop();

	/**
	 * Sends a packet udp from an array of bytes
	 * @param data the data to send
	 */
	public abstract void send(byte data[]);

}