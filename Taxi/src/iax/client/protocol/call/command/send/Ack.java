package iax.client.protocol.call.command.send;

import iax.client.protocol.call.Call;
import iax.client.protocol.frame.FullFrame;
import iax.client.protocol.frame.ProtocolControlFrame;

/**
 * Sends an ack.
 */
public class Ack implements CallCommandSend {

    // Call for sending the frame
	private Call call;
    // Full frame that needs an ack
	private FullFrame fullFrame;

    /**
     * Constructor
     * @param call call for sending the frame
     * @param fullFrame full frame that needs an ack
     */
	public Ack(Call call, FullFrame fullFrame) {
		this.call = call;
		this.fullFrame = fullFrame;
	}

	public void execute() {
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
        // ack's timestamp == timestamp of the full frame that need this ack
		call.handleSendFrame(new ProtocolControlFrame(call.getSrcCallNo(), 
				false, 
				call.getDestCallNo(), 
				fullFrame.getTimestamp(), 
				call.getOseqno(), 
                call.getIseqno(), 
				false, 
				ProtocolControlFrame.ACK_SC));
	}
}
