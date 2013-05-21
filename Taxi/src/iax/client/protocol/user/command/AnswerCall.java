package iax.client.protocol.user.command;

import iax.client.protocol.call.Call;
import iax.client.protocol.frame.ControlFrame;
import iax.client.protocol.peer.Peer;

/**
 * Implements the user command that accepts a call.
 */
public class AnswerCall implements UserCommand {
    private Peer peer;
    private String callingNumber;

    /**
     * Constructor. Initializes the command with suitable values.
     * @param peer Current peer.
     * @param callingNumber the calling number of the call that is going to be accepted
     */
    public AnswerCall(Peer peer, String callingNumber) {
        this.peer = peer;
        this.callingNumber = callingNumber;
    }
    
    public void execute(){
        Thread t = new Thread(this);
        t.start();
    }

    public void run(){
        try {
            Call call = peer.getCall();
            if (call != null) {
                ControlFrame answerFrame = 
                        new ControlFrame(call.getSrcCallNo(), false, call.getDestCallNo(), call.getTimestamp(), call.getOseqno(),
                                call.getIseqno(), false, ControlFrame.ANSWER, new byte[0]);
                    call.startRecorder();
                    call.handleSendFrame(answerFrame);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}