package iax.client.protocol.user.command;

import iax.client.protocol.call.Call;
import iax.client.protocol.peer.Peer;

/**
 * Implements the user command that unmutes a call.
 */
public class UnMuteCall implements UserCommand {

	//Current peer
	private Peer peer;
	//Number of the peer to hang the call.
	private String calledNumber;

	/**
	 * Constructor. Initializes the command with suitable values.
	 * @param peer Current peer.
	 * @param calledNumber Number of the peer to hang the call.
	 */
	public UnMuteCall(Peer peer, String calledNumber) {
		this.peer = peer;
		this.calledNumber = calledNumber;
	}

	public void execute() {
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
		try {
			Call call = peer.getCall();
			call.unMuteCall();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}