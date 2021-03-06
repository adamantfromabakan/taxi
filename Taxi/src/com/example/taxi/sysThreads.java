package com.example.taxi;

import java.util.List;

import com.example.taxi.MainActivity.TimeTask;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
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
	String cmdsock;
	public List<clsOrders> list;
    public List<clsDriverInfo> driver;
    public List<clsCarInfo> car;
	public LocationManager locationManager;
	public LocationListener mLocationListener;	
    public int vl=0; 
    
	
    public sysThreads(MainActivity MA, sysDictionary dic,sysLog LGWR,SocketTAXI mSocket, String cmd, String cmdsock) {
    	this.MA=MA;
    	this.dic=dic;
    	this.LGWR=LGWR;
    	this.mSocket=mSocket;
    	this.cmd=cmd;
    	this.cmdsock=cmdsock;
	}
    
    public void finish()
    {
        mFinish = true;
    }
    
    @Override
	   public void run()		
	    {

    			try {

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
    				
    				if (cmd=="refreshdataper") {
        				MainActivity.flg_refreshdata=0;
        				SocketTAXI mSocket = new SocketTAXI(dic, LGWR);
        				List<clsOrders> list = mSocket.ServerPutCmdOrders(dic.getUid(),dic.getServerTaxi(), dic.getServerTaxiPortCMD(),"imei:"+dic.getUid()+":orders_list,quit;");
        				//List<clsDriverInfo> driver = mSocket.ServerPutCmdDriverInfo(dic.getUid(),dic.getServerTaxi(), dic.getServerTaxiPortCMD(),"imei:"+dic.getUid()+":driver_info,quit;");
        				//List<clsCarInfo> car = mSocket.ServerPutCmdCarInfo(dic.getUid(),dic.getServerTaxi(), dic.getServerTaxiPortCMD(),"imei:"+dic.getUid()+":car_info,quit;");
        				MainActivity.list=list;
        				//MainActivity.driver=driver;
        				//MainActivity.car=car;
        				vl=1;
        				MainActivity.flg_refreshdata=1;
        				try {
        				Thread.sleep(dic.getOrderFreshPer());
        				}catch(InterruptedException e){}
        				}
    				
    				if (cmd=="refreshclock") {
    					if (MainActivity.flg_refreshclock<1) {
    						MainActivity.flg_refreshclock=1;
    					do {
    						try{

    							MainActivity.Sysdate=dic.getSysdate();
    
    	    	                Thread.sleep(1000);		
    	    	            }catch(InterruptedException e){}
    					} while(true);
    					}
    				}
    				
    				if (cmd=="cmdsocket"){
    							SocketTAXI mSocket = new SocketTAXI(dic, LGWR);
    							mSocket.ServerPutCMD(dic.getUid(),dic.getServerTaxi(),dic.getServerTaxiPortCMD(), cmdsock);
    				} 
    				

    			} catch (Exception e) {
    				Log.d(TAG, e.toString());
    				LGWR.logwriter(dic.logcom, dic.logpath, dic.getSysdate()+" - " + TAG + ":run sysThreads " + e.toString());
    			}

	    }
    
   
}
