package iax.client.protocol.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import iax.client.protocol.peer.Peer;

/**
 * Makes the udp connection with the asterisk host for sending and receiving frames
 */
public class J2SEConnection implements Runnable, Connection {

    // Flag to determine if the thread is running or not
	private boolean running = true;
	// Peer that handle the received frames (and send the sending frames)
	private Peer peer;
	// Inet address of the asterisk host
    private InetAddress hostIAddr;
	// port of the asterisk server
    private int port;
    // Socket udp to send and receive frames
	private DatagramSocket socket;

	/**
     * Constructor. 
	 */
	public J2SEConnection() {
	}

    /* (non-Javadoc)
	 * @see iax.protocol.connection.IConnection#start()
     * @param peer peer that handle the received frames (and send the sending frames)
     * @param host ip of the asterisk host
	 */
	public void start(Peer peer,String host, int port) {
		this.peer = peer;
		try {
            hostIAddr = InetAddress.getByName(host);
            this.port = port; 
			socket = new DatagramSocket();
			Thread t = new Thread(this);
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /* (non-Javadoc)
	 * @see iax.protocol.connection.IConnection#stop()
	 */
	public synchronized void stop() {
		this.running = false;
	}

	/* (non-Javadoc)
	 * @see iax.protocol.connection.IConnection#run()
	 */
	public void run() {
		while ( running ) {
			try {
                // Create a packet for receiving the data of a frame
				DatagramPacket packet = new DatagramPacket(new byte[BUFFER_LENGTH], BUFFER_LENGTH);
                // Wait until receiving the data of a packet
				socket.receive( packet );
				recv(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    /*
     * Handles the packets received by delegating in the peer
     */
	private void recv(DatagramPacket packet) {
        // Data length received (is less or equal than the total length of the packet used for receiving data)
		int length = packet.getLength();
		byte[] buffer = new byte[length];
		System.arraycopy(packet.getData(), 0, buffer, 0, length);
		peer.handleRecvFrame(buffer);
	}
    
    /* (non-Javadoc)
	 * @see iax.protocol.connection.IConnection#send(byte[])
	 */
    public void send(byte data[]) {
        try {
            DatagramPacket packet = new DatagramPacket(data, data.length, hostIAddr, port);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}