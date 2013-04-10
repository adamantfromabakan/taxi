package com.example.taxi;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.content.*;

public class GPSLocationListener implements LocationListener {
	sysDictionary dic; 
	sysLog LGWR;
	SocketTAXI mSocket;
	private static final String TAG = "GPSLocationListener";

	//TextView rsltTXT;
	public String LGT="0000.0000";
	public String LAT="0000.0000";
	public String ALT="0000.0000";

	
    public GPSLocationListener(sysDictionary dic,sysLog LGWR,SocketTAXI mSocket) {
    	this.dic=dic;
    	this.LGWR=LGWR;
    	this.mSocket=mSocket;
	}

	@Override
    public void onLocationChanged(Location loc) {
		try {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmm");
        String strTime = simpleDateFormat.format(new Date());
        loc.getLatitude();
        loc.getLongitude();
        String txt = "Текущее местоположение: " + "\nLatitud = "
                + loc.getLatitude() + "\nLongitud = " + loc.getLongitude() 
                + "\nAltitud = " + loc.getAltitude();
        Log.d(TAG, txt);
        LAT=""+loc.getLatitude();
		LGT=""+loc.getLongitude();
		ALT=""+loc.getAltitude();
		
		//SocketTAXI mSocket = new SocketTAXI();
		//LGWR.logwriter(dic.logcom, dic.logpath, dic.getSysdate()+" - "+ e.toString());
	    LGWR.logwriter(dic.loggps, dic.logpath, dic.getSysdate()+" - "+"imei:"+dic.getUid()+",tracker,"+strTime+",,F,"+loc.getAltitude()+",A,"+loc.getLatitude()+",N,"+loc.getLongitude()+",E,0;");
		mSocket.ServerPutGPS(dic.getUid(),dic.getServerTaxi(),dic.getServerTaxiPortGPS(),ALT,LAT,LGT);
        
		} catch(Exception e)  {    
        	Log.d(TAG, e.toString());
        	LGWR.logwriter(dic.logcom, dic.logpath, dic.getSysdate()+" - "+ TAG + ":onLocationChanged " + e.toString());
        	}
	    
    }

    @Override
    public void onProviderDisabled(String provider) {
        //Toast.makeText(getApplicationContext(), "Gps выключен",Toast.LENGTH_LONG).show();
    	LGWR.logwriter(dic.loggps, dic.logpath, dic.getSysdate()+" - "+"Gps Off!");
    }



	@Override
    public void onProviderEnabled(String provider) {
        //Toast.makeText(getApplicationContext(), "Gps включен",Toast.LENGTH_LONG).show();
		LGWR.logwriter(dic.loggps, dic.logpath, dic.getSysdate()+" - "+"Gps On!");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}
