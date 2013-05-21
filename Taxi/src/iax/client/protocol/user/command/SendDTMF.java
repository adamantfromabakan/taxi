package iax.client.protocol.user.command;

import iax.client.protocol.call.Call;
import iax.client.protocol.frame.DTMFFrame;
import iax.client.protocol.peer.Peer;

public class SendDTMF implements UserCommand {

	//	Current peer
	private Peer peer;
	//  Number of the peer to hang the call.
	private String calledNumber;
	//	DTMF tone to send.
	private char dtmfTone;

	/**
	 * Constructor. Initializes the command with suitable values.
	 * @param peer Current peer.
	 * @param calledNumber Number of the peer to hang the call.
	 */
	public SendDTMF(Peer peer, String calledNumber, char tone) {
		this.peer = peer;
		this.calledNumber = calledNumber;
		this.dtmfTone = tone;
	}

	public void execute() {
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
		try {
			Call call = peer.getCall();
            
			DTMFFrame dtmfFrame = new DTMFFrame(call.getSrcCallNo(), false, call.getDestCallNo(), call.getTimestamp(), call.getOseqno(), call.getIseqno(), (int)dtmfTone);
            call.handleSendFrame(dtmfFrame);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
