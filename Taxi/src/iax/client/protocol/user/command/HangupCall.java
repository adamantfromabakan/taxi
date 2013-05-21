package iax.client.protocol.user.command;

import iax.client.protocol.call.Call;
import iax.client.protocol.frame.ProtocolControlFrame;
import iax.client.protocol.peer.Peer;

/**
 * Implements the user command that hangs a given initiated call.
 */
public class HangupCall implements Runnable {
	//Current peer
    private Peer peer;
    //Number of the peer to hang the call.
    private String calledNumber;
  
	/**
	 * Implements the user command that hangs a given initiated call.
	 */
    /**
     * Constructor. Initializes the command with suitable values.
     * @param peer Current peer.
     * @param calledNumber Number of the peer to hang the call.
     */
    public HangupCall(Peer peer, String calledNumber) {
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

            if (call != null){
                ProtocolControlFrame hangupFrame = 
                        new ProtocolControlFrame(call.getSrcCallNo(), false, call.getDestCallNo(), call.getTimestamp(), call.getOseqno(),
                                call.getIseqno(), false, ProtocolControlFrame.HANGUP_SC);

                call.handleSendFrame(hangupFrame);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	  

}