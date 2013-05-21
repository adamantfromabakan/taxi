package iax.client.protocol.peer;

/**
 * Interface to be implemented by classes that has to be notified from peer changes.
 */
public interface PeerListener {

    /**
     * Notifies that peer has hung up a call.
     * @param calledNumber The number of the peer that has hung up the call.
     */
    public void hungup(String calledNumber);
    
    /**
     * Notifies that peer has received a call
     * @param callingName The name of the peer that makes the call
     * @param callingNumber The number of the peer that makes the call
     */
    public void recvCall(String callingName, String callingNumber);
    
    /**
     * Notifies that peer has registered
     */
    public void registered();
    
    /**
     * Notifies that peer has waiting a call
     */
    public void waiting();
    
    /**
     * Notifies that peer has unregistered
     */
    public void unregistered();

    /**
     * Notifies that peer has exited
     */
    public void exited();
    
    /**
     * Notifies that peer has answered a call
     * @param callingNumber The number of the peer that makes the call
     */
    public void answered(String calledNumber);
    
    /**
     * Play wait tones
     * @param callingNumber The number of the peer that makes the call
     */
    public void playWaitTones(String calledNumber);
    
    /**
     * return instance is enable or not
     * @return 
     */
    public boolean isEnabled();
    
}
