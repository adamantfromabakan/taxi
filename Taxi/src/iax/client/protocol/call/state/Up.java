package iax.client.protocol.call.state;

//import android.util.Log;
import iax.client.protocol.call.Call;
import iax.client.protocol.call.command.recv.CallCommandRecvFacade;
import iax.client.protocol.frame.ControlFrame;
import iax.client.protocol.frame.DTMFFrame;
import iax.client.protocol.frame.Frame;
import iax.client.protocol.frame.MiniFrame;
import iax.client.protocol.frame.ProtocolControlFrame;
import iax.client.protocol.frame.VoiceFrame;

/**
 * Call's state up. It's a singleton.
 */
public class Up extends CallState {
    // Singleton instance
	private static Up instance;
	static boolean recvAnswer = false;			// UpでのRINGINGを受け取りに関する暫定処置
    
    // Private constructor
	private Up() {
		instance = this;
	}
    
    /**
     * Gets an instance of this state
     * @return the instance of this state
     */
	public static Up getInstance() {
		if (instance != null) {
			return instance;
		} else return new Up();
	}
    
    public void handleRecvFrame(Call call, Frame frame) {
        try {
            if (frame.getType() == Frame.CONTROLFRAME_T) {
                // Received a control frame
                ControlFrame controlFrame = (ControlFrame) frame;
                switch (controlFrame.getSubclass()) {
                case ControlFrame.RINGING:				// ステート図ではUpはRINGINGを受け取らないことになっているが実際には受け取ることがある
//					Log.d("Up#handleRecvFrame","recv RINGING");
                    // Handles a ringing frame received
					recvAnswer = false;
                    CallCommandRecvFacade.ringing(call, controlFrame);
                    break;
                default:
                    break;
                }
            } else if (frame.getType() == Frame.PROTOCOLCONTROLFRAME_T) {
                ProtocolControlFrame protocolControlFrame = (ProtocolControlFrame) frame;
                switch (protocolControlFrame.getSubclass()) {
                case ProtocolControlFrame.VNAK_SC:
                	call.setFirstVoiceFrameWasSended(true);
                default:
                    // By default, delegates received frames in the super
                    super.handleRecvFrame(call, frame);
                    break;
                }
            } else if (frame.getType() == Frame.VOICEFRAME_T) {
                // Received a voice full frame
            	if (! recvAnswer) {			// UpでのRINGINGを受け取りに関する暫定処置
            		CallCommandRecvFacade.answer(call, null);
            		recvAnswer = true;
            	}
            	VoiceFrame voiceFrame = (VoiceFrame) frame;
				CallCommandRecvFacade.voiceFullFrame(call, voiceFrame);
            } else if (frame.getType() == Frame.DTMFFRAME_T) {
                // Received a DTMF full frame
            	DTMFFrame dtmfFrame = (DTMFFrame) frame;
				CallCommandRecvFacade.dtmfFullFrame(call, dtmfFrame);
            } else if (frame.getType() == Frame.MINIFRAME_T) {
                // Received a voice mini frame
            	MiniFrame miniFrame = (MiniFrame) frame;
				CallCommandRecvFacade.voiceMiniFrame(call, miniFrame);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void handleSendFrame(Call call, Frame frame) {
        try {
            if (frame.getType() == Frame.PROTOCOLCONTROLFRAME_T) {
                // Received a protocol control frame
                ProtocolControlFrame protocolControlFrame = (ProtocolControlFrame) frame;
                switch (protocolControlFrame.getSubclass()) {
                case ProtocolControlFrame.TRANSFER_SC:
//					Log.d("Up#handleSendFrame","send TRANSFER_SC");
                    call.sendFullFrameAndWaitForAck(protocolControlFrame);
                    break;
                default:
                    // By default, delegates frames to send in the super
                    super.handleSendFrame(call, frame);
                    break;
                }
            } else if (frame.getType() == Frame.VOICEFRAME_T) {
                // Sending a voice full frame
                VoiceFrame voiceFrame = (VoiceFrame) frame;
                call.sendFullFrameAndWaitForAck(voiceFrame);
            } else if (frame.getType() == Frame.MINIFRAME_T) {
                // Sending a voice mini frame
                MiniFrame miniFrame = (MiniFrame) frame;
                call.sendFrameAndNoWait(miniFrame);
            } else if (frame.getType() == Frame.DTMFFRAME_T) {
            	// Sending a DTMF frame
                DTMFFrame dtmfFrame = (DTMFFrame) frame;
                call.sendFullFrameAndWaitForAck(dtmfFrame);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}