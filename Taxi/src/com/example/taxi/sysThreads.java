package com.example.taxi;

import android.widget.TableLayout;

public class sysThreads extends Thread implements Runnable  {
	sysDictionary dic; 
	sysLog LGWR;
	SocketTAXI mSocket;
	MainActivity MA;
	
    public sysThreads(MainActivity MA, sysDictionary dic,sysLog LGWR,SocketTAXI mSocket) {
    	this.MA=MA;
    	this.dic=dic;
    	this.LGWR=LGWR;
    	this.mSocket=mSocket;
	}
    
    @Override
	   public void run()		
	    {
	        System.out.println("Привет из побочного потока!");
	        MA.cmdOrderlist();
	        //TableLayout table = new TableLayout(MA);
			//table.setBackgroundResource(R.drawable.abakanmap);

	        //table.setStretchAllColumns(true);
	        //table.setShrinkAllColumns(true);
			
 		    //table.setBackgroundColor(getResources().getColor(com.example.taxi.R.color.CornflowerBlue));
	        //setContentView(table);
	    }
}
