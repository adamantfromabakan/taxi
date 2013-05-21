package iax.client.protocol.peer;

import iax.client.audio.AudioFactory;
import iax.client.audio.impl.NullAudioFactory;
import iax.client.protocol.call.Call;
import iax.client.protocol.call.command.send.CallCommandSendFacade;
import iax.client.protocol.connection.Connection;
import iax.client.protocol.connection.J2SEConnection;
import iax.client.protocol.frame.Frame;
import iax.client.protocol.frame.FullFrame;
import iax.client.protocol.frame.MiniFrame;
import iax.client.protocol.frame.ProtocolControlFrame;
import iax.client.protocol.peer.command.send.PeerCommandSendFacade;
import iax.client.protocol.peer.state.PeerState;
import iax.client.protocol.peer.state.Registered;
import iax.client.protocol.peer.state.Unregistered;
import iax.client.protocol.peer.state.Waiting;
import iax.client.protocol.util.FrameUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Peer that controls phone calls.
 */
public class Peer {

    /**
     * Peer's source call number
     */
    public final static int PEER_SRCCALLNO = 1;
    /**
     * Peer's register refresh in seconds
     */
    public final static int REGISTER_REFRESH = 60;

    //Peer's retry refresh in seconds
    private final int RETRY_REFRESH = 1;
    //Peer's retry max
    private final int RETRY_MAXCOUNT = 10;

    //Registration user name.
    private String userName;
    //Registration password.
    private String password;
    //Remote host.
    private String host;
    //Connection to send data to host.
    private Connection connection;
    // 
    private Call call;
    //HashMap with the frames that are waiting for an ack
    private HashMap<Long, FullFrame> framesWaitingAck;
    //HashMap with the frames that are waiting for a reply
    private HashMap<Integer, FullFrame> framesWaitingReply;
    //Local identifier for a given call.
    private int newLocalSrcCallNo;
    //Peer listener.
    private PeerListener peerListener = null;
    //Peer's state
    private PeerState state;
    //Source timestamp for full frames from the first full frame sent by this call
    private long srcTimestamp;
    //Register timer task
    private Timer registerTimer;
    //Retry timer task to retry frames not committed whit a specific frame or an ack frame
    private Timer retryTimer;
    //Flag for determining if the peer is processing the exit
    private boolean processingExit;
    //Max calls
//    private int maxCalls;

    private AudioFactory audioFactory;

    /**
     * Constructor. Initializes the peer with the given values.Using NullAudioFactory class and J2SEConnection class
     * @param peerListener Peer listener.
     * @param userName Registration user name.
     * @param password Registration password.
     * @param host Remote host.
     * @param register true if the peer is going to register, or false if not
     * @param maxCalls maximum number of concurrent calls(This parameter is not used now. )
     */
    public Peer(PeerListener peerListener, String userName, String password, String host, int port, boolean register, int maxCalls) {
    	this(peerListener, userName, password, host, port, register,maxCalls, new NullAudioFactory(), null);
    }

    /**
     * Constructor. Initializes the peer with the given values.Using J2SEConnection class
     * @param peerListener Peer listener.
     * @param userName Registration user name.
     * @param password Registration password.
     * @param host Remote host.
     * @param register true if the peer is going to register, or false if not
     * @param maxCalls maximum number of concurrent calls(This parameter is not used now. )
     */
    public Peer(PeerListener peerListener, String userName, String password, String host, int port, boolean register, int maxCalls, AudioFactory audioFactory) {
    	this(peerListener, userName, password, host, port, register,maxCalls, audioFactory, null);
    }

    /**
     * Constructor. Initializes the peer with the given values.
     * @param peerListener Peer listener.
     * @param userName Registration user name.
     * @param password Registration password.
     * @param host Remote host.
     * @param register true if the peer is going to register, or false if not
     * @param maxCalls maximum number of concurrent calls(This parameter is not used now. )
     * @param audioFactory AudioFactory class
     * @param connection Connection class
     */
    public Peer(PeerListener peerListener, String userName, String password, String host, int port, boolean register, int maxCalls, AudioFactory audioFactory,Connection connection) {
        this.userName = userName;
        this.password = password;
        this.host = host;
//        this.maxCalls = maxCalls;
        if (audioFactory == null){
        	this.audioFactory = new NullAudioFactory();
        }else{
            this.audioFactory = audioFactory;
        }
        this.newLocalSrcCallNo = 2;
        this.peerListener = peerListener;
        if (connection == null){
    		this.connection = new J2SEConnection();
        }else{
    		this.connection = connection;
        }
		this.call = null;
        this.framesWaitingAck = new HashMap<Long, FullFrame>();
        this.framesWaitingReply = new HashMap<Integer, FullFrame>();
        this.connection.start(this, host, port);
        this.state = Unregistered.getInstance();
        this.processingExit = false;
        this.retryTimer = new Timer();
        TimerTask retryTimerTask = new TimerTask() {
            public void run() {
                retryFramesWaiting();
            }
        };
        retryTimer.schedule(retryTimerTask, RETRY_REFRESH*1000, RETRY_REFRESH*1000);
        this.registerTimer = new Timer();
        if (register) {
            TimerTask registerTimerTask = new TimerTask() {
                public void run() {
                    register();
                }
            };
            registerTimer.schedule(registerTimerTask, 0, REGISTER_REFRESH*1000);
        }
    }

    /**
     * Gets peer user name.
     * @return String with user name.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets peer password.
     * @return String with password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets peer remote host.
     * @return String with remote host address.
     */
    public String getHost() {
        return host;
    }

    /**
     * Gets peer's state
     * @return peer's state
     */
    public PeerState getState() {
        return state;
    }


    /**
     * Gets the timestamp from the first full frame sent
     * @return the timestamp from the first full frame sent
     */
    public long getTimestamp() {
    	long now = System.currentTimeMillis();
        return now-srcTimestamp;
    }

    /**
     * Gets the processingExit.
     * @return the processingExit
     */
    public boolean isProcessingExit() {
        return processingExit;
    }
    
    /**
     * Sets peer's state and notifies this state to the phone controller
     * @param state peer's state
     */
    public void setState(PeerState state) {
        this.state = state;
        if (state instanceof Registered)
        	peerListener.registered();
        else if (state instanceof Waiting)
        	peerListener.waiting();
        else if (state instanceof Unregistered)
        	peerListener.unregistered();
    }
    

    public AudioFactory getAudioFactory() {
		return audioFactory;
	}

	public void setAudioFactory(AudioFactory audioFactory) {
		this.audioFactory = audioFactory;
	}

	/**
     * Ackes a full frame waiting for that removing it from the list of full frames waiting for ack
     * @param timeStamp the timestamp of the full frame acked
     */
    public synchronized void ackedFrame(long timeStamp) {
    	framesWaitingAck.remove(new Long(timeStamp));
    }

    /**
     * Replied a full frame waiting for that removing it from the list of full frames waiting for reply
     * @param id the id of the full frame reply
     */
    public synchronized void repliedFrame(int id) {
        framesWaitingReply.remove(new Integer(id));
    }
    
    /**
     * Registers the peer
     */
    public void register() {
        srcTimestamp = System.currentTimeMillis();
        PeerCommandSendFacade.regreq(this);
    }
    
    /**
     * Exits from the system and notifies the peer listener
     */
    public void exit() {
        //Ends all calls
        if (call != null){
        	CallCommandSendFacade.hangup(call);
        	call = null;
        }
        processingExit = true;
        registerTimer.cancel();
        retryTimer.cancel();
        PeerCommandSendFacade.regrel(this);
        int retry = 0;
        while(! (state instanceof Unregistered) && retry < RETRY_MAXCOUNT) {
        	try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	retry++;
        }
        	
        //Wait for responses
        if (this.connection != null){
        	this.connection.stop();
        }
        try {
           Thread.sleep(2000);
        } catch (Exception e) {}
        //Notifies the peer listener
    	peerListener.exited();
    }

    /**
     * Gets a local call from called number.
     * @return The call.
     * @throws PeerException
     */
    public Call getCall() throws PeerException {
   	 return call;
    }
    
    /**
     * Handles the reception of a frame from a call or a peer
     * 
     * @param buffer The frame data.
     */
    public synchronized void handleRecvFrame(byte buffer[]) {
        try {
            Frame frame = FrameUtil.deserialize(buffer);
            // Received a FullFrame
            if (frame != null){
                if (frame.getFull()) {
                    FullFrame fullFrame = (FullFrame)frame;
                    int destCallNo = fullFrame.getDestCallNo();
                    // FullFrame for a call
                    if ((destCallNo != 0) && (destCallNo != PEER_SRCCALLNO)) {
                        // Checks if there is any call that handles this FullFrame
                        if (call!=null)
                            call.handleRecvFrame(fullFrame);
                        else PeerCommandSendFacade.inval(this, frame);
                        // FullFrame for the peer
                    } else {
                        state.handleRecvFrame(this, frame); 
                    }
                    // Received a MiniFrame    
                } else {
                    // Checks if there is any call that handles this MiniFrame
                    if (call!=null)
                        call.handleRecvFrame((MiniFrame)frame);
                    else PeerCommandSendFacade.inval(this, frame);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the sending of a frame from a call.
     * @param frame The frame object.
     */
    public synchronized void handleSendFrame(Frame frame) {
        try {
            int srcCallNo = frame.getSrcCallNo();
            //Frame for a call
            if ((srcCallNo != 0) && (srcCallNo != PEER_SRCCALLNO)) {
                // Checks if there is any call that handles this Frame
                if (call!=null)
                    call.handleSendFrame(frame);
                else PeerCommandSendFacade.inval(this, frame);
                // Frame for the peer
            } else {
                state.handleSendFrame(this, frame);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a frame directly. 
     * @param frame The frame object.
     */
    public void sendFrame(Frame frame) {
        try {
            connection.send(frame.serialize());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a full frame that needs to be acked. For that added it to the list of frame waiting to be acked and after that
     * delegates in the method sendFrame() to send it
     * @param fullFrame the full frame to send
     */
    public synchronized void sendFullFrameAndWaitForAck(FullFrame fullFrame) {
        framesWaitingAck.put(new Long(fullFrame.getTimestamp()), fullFrame);
        sendFrame(fullFrame);
    }
    
    /**
     * Sends a full frame that needs to be replied. For that added it to the list of frame waiting to be replied and after that
     * delegates in the method sendFrame() to send it
     * @param fullFrame the full frame to send
     */
    public synchronized void sendFullFrameAndWaitForRep(FullFrame fullFrame) {
        framesWaitingReply.put(new Integer(fullFrame.getSubclass()), fullFrame);
        sendFrame(fullFrame);
    }

    /**
     * Notifies to the peer listener an answered
     * @param call the call
     */
    public void answeredCall(Call call) {
    	peerListener.answered(call.getCalledNumber());
    }
    
    /**
     * Notifies to the peer listener for playing wait tones
     * @param call the call
     */
    public void ringingCall(Call call) {
    	peerListener.playWaitTones(call.getCalledNumber());
    }
    
    
    /**
     * Maps the destination call number to the source call number and, if is a received call, notifies the
     * @param call The call to map.
     */
    public synchronized void bindCall(Call call) {
    }

    /**
     * Ends a call.
     * @param call The call to end.
     */
    public void endCall(Call call) {
    	peerListener.hungup(call.getCalledNumber());
        this.call = null;
    }

    /**
     * Starts a new call.
     * @param calledNumber The number to call.
     * @return The new call.
     * @throws PeerException
     */
    //public synchronized Call newCall(String calledNumber) throws PeerException {
      public Call newCall(String calledNumber) throws PeerException {
        if (call == null && peerListener.isEnabled()) {
            Call newCall = null;
            try {
                newCall = new Call(this, newLocalSrcCallNo,audioFactory);
                newCall.startCall(calledNumber);
            } catch (Exception e) {
                throw new PeerException(e);
            }
            call = newCall;
            newLocalSrcCallNo++;
            return newCall;
        } else throw new PeerException("Reached calls maximun");
    }


    /**
     * Starts a call received
     * @param recvCallFrame the new call frame received
     * @throws PeerException
     */
    public synchronized void recvCall(ProtocolControlFrame recvCallFrame) throws PeerException {
        if (call == null && peerListener.isEnabled()) {
            String callingName = "";
            String callingNumber = "";
            try {
                callingName = recvCallFrame.getCallingName();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                callingNumber = recvCallFrame.getCallingNumber();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Call newCall = null;
            try {
                newCall = new Call(this, newLocalSrcCallNo,audioFactory);
                newCall.startCall(callingNumber);
            } catch (Exception e) {
                throw new PeerException(e);
            }
            call = newCall;
            newLocalSrcCallNo++;
            call.handleRecvFrame(recvCallFrame);
        	peerListener.recvCall(callingName, callingNumber);
        } else {
            PeerCommandSendFacade.ack(this, recvCallFrame);
            PeerCommandSendFacade.busy(this, recvCallFrame);
        }
    }

    // Method to retry frames that haven't been commited whith a specific frame or an ack frame
    private synchronized void retryFramesWaiting() {
        try {
        	Iterator<FullFrame> iterator = framesWaitingAck.values().iterator();
            while (iterator.hasNext()) {
                FullFrame retryFullFrame = (FullFrame)iterator.next();
                retryFullFrame.incRetryCount();
                if (retryFullFrame.getRetryCount() < RETRY_MAXCOUNT) {
                    sendFrame(retryFullFrame);
                } else throw new PeerException("Reached retries maximun in the peer for full frame of type " + 
                        retryFullFrame.getFrameType() + ", subclass " + retryFullFrame.getSubclass());
            }
            iterator = framesWaitingReply.values().iterator();
            while (iterator.hasNext()) {
                FullFrame retryFullFrame = (FullFrame)iterator.next();
                retryFullFrame.incRetryCount();
                if (retryFullFrame.getRetryCount() < RETRY_MAXCOUNT) {
                    sendFrame(retryFullFrame);
                } else throw new PeerException("Reached retries maximun in the peer for full frame of type " + 
                        retryFullFrame.getFrameType() + ", subclass " + retryFullFrame.getSubclass());
            }
        } catch (PeerException e) {
            framesWaitingAck.clear();
            framesWaitingReply.clear();
            setState(Unregistered.getInstance());
            e.printStackTrace();
        }
    }
    
}