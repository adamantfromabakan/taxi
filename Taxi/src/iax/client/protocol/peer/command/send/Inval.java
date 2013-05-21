package iax.client.protocol.peer.command.send;

import iax.client.protocol.frame.Frame;
import iax.client.protocol.frame.FullFrame;
import iax.client.protocol.frame.MiniFrame;
import iax.client.protocol.frame.ProtocolControlFrame;
import iax.client.protocol.peer.Peer;

/**
 * Sends an inval
 */
public class Inval implements PeerCommandSend {

    private Peer peer;
    private Frame frame;

    /**
     * Constructor
     * @param peer peer for sending the frame
     * @param frame the frame that needs an inval
     */
    public Inval(Peer peer, Frame frame) {
        this.peer = peer;
        this.frame = frame;
    }
    
    public void execute() {
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        if (frame.getFull()) {
            FullFrame fullFrame = (FullFrame)frame;
            peer.sendFrame(new ProtocolControlFrame(fullFrame.getDestCallNo(), 
                false, 
                fullFrame.getSrcCallNo(), 
                fullFrame.getTimestamp(), 
                fullFrame.getIseqno(), 
                fullFrame.getOseqno(), 
                false, 
                ProtocolControlFrame.INVAL_SC));
        } else {
            MiniFrame miniFrame = (MiniFrame)frame;
            peer.sendFrame(new ProtocolControlFrame(0, 
                    false, 
                    miniFrame.getSrcCallNo(), 
                    miniFrame.getTimestamp(), 
                    0, 
                    0, 
                    false, 
                    ProtocolControlFrame.INVAL_SC));
        }
    }

}
