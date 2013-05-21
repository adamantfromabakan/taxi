package iax.client.protocol.call.command.send;

import iax.client.protocol.call.Call;
import iax.client.protocol.frame.FrameException;
import iax.client.protocol.frame.ProtocolControlFrame;

/**
 * Sends an accept
 */
public class Accept implements CallCommandSend {
    // Call for sending the frame
    private Call call;

    private ProtocolControlFrame recvCallFrame;
    /**
     * Constructor
     * @param call call for sending the frame
     */
    public Accept(Call call, ProtocolControlFrame recvCallFrame) {
        this.call = call;
        this.recvCallFrame = recvCallFrame;
    }

    public void execute() {
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
    	ProtocolControlFrame acceptFrame = new ProtocolControlFrame(call.getSrcCallNo(), 
                false, 
                call.getDestCallNo(), 
                recvCallFrame.getTimestamp(), 
                recvCallFrame.getOseqno(), 
                recvCallFrame.getIseqno(), 
                false, 
                ProtocolControlFrame.ACCEPT_SC);
    	try {
			acceptFrame.setCapability(call.getAudioFactory().getCodec());
	    	acceptFrame.setFormat(call.getAudioFactory().getCodec());
	    	call.handleSendFrame(acceptFrame);
		} catch (FrameException e) {
			e.printStackTrace();
		}
//        call.handleSendFrame(new ProtocolControlFrame(call.getSrcCallNo(), 
//                false, 
//                call.getDestCallNo(), 
//                recvCallFrame.getTimestamp(), 
//                recvCallFrame.getOseqno(), 
//                recvCallFrame.getIseqno(), 
//                false, 
//                ProtocolControlFrame.ACCEPT_SC));
    }
}
