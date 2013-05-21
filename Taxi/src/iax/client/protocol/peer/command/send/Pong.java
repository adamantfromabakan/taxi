package iax.client.protocol.peer.command.send;

import iax.client.protocol.frame.ProtocolControlFrame;
import iax.client.protocol.peer.Peer;

/**
 * Sends a pong
 */
public class Pong implements PeerCommandSend {

    private Peer peer;
    private ProtocolControlFrame pokeFrame;
    
    /**
     * Constructor
     * @param peer peer for sending the frame
     * @param pokeFrame poke frame that needs a pong
     */
    public Pong(Peer peer, ProtocolControlFrame pokeFrame) {
        this.peer = peer;
        this.pokeFrame = pokeFrame;
    }
    
    public void execute() {
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        peer.handleSendFrame(new ProtocolControlFrame(Peer.PEER_SRCCALLNO, 
                false, 
                pokeFrame.getSrcCallNo(), 
                pokeFrame.getTimestamp(), 
                pokeFrame.getIseqno(), 
                pokeFrame.getOseqno()+1, 
                false, 
                ProtocolControlFrame.PONG_SC));
    }

}
