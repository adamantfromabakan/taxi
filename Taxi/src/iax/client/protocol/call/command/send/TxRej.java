package iax.client.protocol.call.command.send;

import iax.client.protocol.call.Call;
import iax.client.protocol.frame.ProtocolControlFrame;

public class TxRej implements CallCommandSend {
    private Call call;

    public TxRej(Call call, ProtocolControlFrame txreqFrame) {
    	this.call = call;
    }
    
    public void run() {
        call.handleSendFrame(new ProtocolControlFrame(call.getSrcCallNo(), 
                false, 
                call.getDestCallNo(), 
                call.getTimestamp(), 
                call.getOseqno(), 
                call.getIseqno(), 
                false, 
                ProtocolControlFrame.TXREJ_SC));
    }

	public void execute() {
        Thread t = new Thread(this);
        t.start();
	}
}
