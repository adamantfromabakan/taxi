package com.example.taxi;

import java.util.List;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TableLayout;

public class sysThreads extends Thread implements Runnable  {
	private static final String TAG = "sysThreads";
	private volatile boolean mFinish = false;
	sysDictionary dic; 
	sysLog LGWR;
	SocketTAXI mSocket;
	MainActivity MA;
	String cmd;
	public List<clsOrders> list;
    public List<clsDriverInfo> driver;
    public List<clsCarInfo> car;
	public LocationManager locationManager;
	public LocationListener mLocationListener;	
    public int vl=0; 
	
    public sysThreads(MainActivity MA, sysDictionary dic,sysLog LGWR,SocketTAXI mSocket, String cmd) {
    	this.MA=MA;
    	this.dic=dic;
    	this.LGWR=LGWR;
    	this.mSocket=mSocket;
    	this.cmd=cmd;
	}
    
    public void finish()
    {
        mFinish = true;
    }
    
    @Override
	   public void run()		
	    {
    	/*do {
    		if(!mFinish) {
    			try {
    				if (vl<1) {*/
    				if (cmd=="refreshdata") {
    				MainActivity.flg_refreshdata=0;
    				SocketTAXI mSocket = new SocketTAXI(dic, LGWR);
    				List<clsOrders> list = mSocket.ServerPutCmdOrders(dic.getUid(),dic.getServerTaxi(), dic.getServerTaxiPortCMD(),"imei:"+dic.getUid()+":orders_list,quit;");
    				List<clsDriverInfo> driver = mSocket.ServerPutCmdDriverInfo(dic.getUid(),dic.getServerTaxi(), dic.getServerTaxiPortCMD(),"imei:"+dic.getUid()+":driver_info,quit;");
    				List<clsCarInfo> car = mSocket.ServerPutCmdCarInfo(dic.getUid(),dic.getServerTaxi(), dic.getServerTaxiPortCMD(),"imei:"+dic.getUid()+":car_info,quit;");
    				MainActivity.list=list;
    				MainActivity.driver=driver;
    				MainActivity.car=car;
    				vl=1;
    				MainActivity.flg_refreshdata=1;
    				}
    				
    				if (cmd=="refreshclock") {
    					if (MainActivity.flg_refreshclock<1) {
    						MainActivity.flg_refreshclock=1;
    					do {
    						try{
    					    	// TableLayout tablehead = (TableLayout)findViewById(com.example.taxi.R.id.TaxiHeadLayout);
    					    	// Button btn = (Button) tablehead.findViewById(10000000);
    					    	// btn.setText(dic.getSysdate());
    							MainActivity.Sysdate=dic.getSysdate();
    							
    							//Button btn1 = (Button) MA.findViewById(10000000);
    							//btn1.setText(dic.getSysdate());
    							//Log.d(TAG, btn1.getText().toString());
    							//((Button) MA.findViewById(10000000)).setText(dic.getSysdate());
    								//	MA.btn1.setText("");
    									//
    	    	                Thread.sleep(1000);		
    	    	            }catch(InterruptedException e){}
    					} while(true);
    					}
    				}
    				
    				if (cmd=="gps"){
    					do {
    						try{
    							//MainActivity.btn1.setText(dic.getSysdate());
    							//MainActivity.Sysdate=dic.getSysdate();
    							//Button btn1 = (Button) MA.findViewById(10000000);
    							//btn1.setText(dic.getSysdate());
    							
    							
    	    	                Thread.sleep(1000);		
    	    	            }catch(InterruptedException e){}
    					} while(true);
    				} 
    					//SocketTAXI mSocket = new SocketTAXI(dic, LGWR);
        		        //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        		        //mLocationListener = new GPSLocationListener(dic, LGWR, mSocket); 


    				/*}
    				
    				try{
    	                Thread.sleep(1000);		
    	            }catch(InterruptedException e){}
    			} catch (Exception e) {
    				Log.d(TAG, e.toString());
    				LGWR.logwriter(dic.logcom, dic.logpath, dic.getSysdate()+" - " + TAG + ":run " + e.toString());
    			}
    		} else {
    			return;}
    	} while(true);*/
	    }
}
