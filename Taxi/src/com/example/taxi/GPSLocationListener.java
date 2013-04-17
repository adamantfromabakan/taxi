package com.example.taxi;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
		//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmm");
        //String strTime = simpleDateFormat.format(new Date());
        loc.getLatitude();
        loc.getLongitude();
        String txt = "Текущее местоположение: "+"\nLatitud = "+loc.getLatitude()+"\nLongitud = "+loc.getLongitude()+"\nAltitud = "+loc.getAltitude();
        Log.d(TAG, txt);
        LAT=""+loc.getLatitude();
		LGT=""+loc.getLongitude();
		ALT=""+loc.getAltitude();
		double tmpLAT = new BigDecimal(Double.parseDouble(LAT)).setScale(4, RoundingMode.UP).doubleValue();
		double tmpLGT = new BigDecimal(Double.parseDouble(LGT)).setScale(4, RoundingMode.UP).doubleValue();
		double tmpALT = new BigDecimal(Double.parseDouble(ALT)).setScale(4, RoundingMode.UP).doubleValue();
        LAT=""+tmpLAT;
		LGT=""+tmpLGT;
		ALT=""+tmpALT;
		//SocketTAXI mSocket = new SocketTAXI();

		if (LAT!=MainActivity.LAT && LGT!=MainActivity.LGT) {
		//LGWR.logwriter(dic.logcom, dic.logpath, dic.getSysdate()+" - "+ TAG + ".." +"imei:"+dic.getUid()+",tracker,"+strTime+",,F,"+ALT+",A,"+LAT+",N,"+LGT+",E,0;");
		//mSocket.ServerPutGPS(dic.getUid(),dic.getServerTaxi(),dic.getServerTaxiPortGPS(),ALT,LAT,LGT);
		Thread gpsThready = new Thread(new Runnable()
        	{
            public void run()
            	{
            	if (LAT!=MainActivity.LAT && LGT!=MainActivity.LGT) {
            		SocketTAXI mSocket = new SocketTAXI(dic, LGWR);
            		LGWR.logwriter(dic.logcom, dic.logpath, dic.getSysdate()+" - "+ TAG + ".." +"imei:"+dic.getUid()+",tracker,"+dic.getSysdateGps()+",,F,"+ALT+",A,"+LAT+",N,"+LGT+",E,0;");
            		mSocket.ServerPutGPS(dic.getUid(),dic.getServerTaxi(),dic.getServerTaxiPortGPS(),ALT,LAT,LGT);
            		MainActivity.LAT=LAT;
            		MainActivity.LGT=LGT;
            		MainActivity.ALT=ALT;
            		}
            	}
        	});

		gpsThready.start();
		LGWR.logwriter(dic.logcom, dic.logpath, dic.getSysdate()+" - "+ TAG + ".." +"initialized thread GPS:"+gpsThready.getId());
		MainActivity.LAT=LAT;
		MainActivity.LGT=LGT;
		MainActivity.ALT=ALT;
		
		}
		
		} catch(Exception e)  {    
        	Log.d(TAG, e.toString());
        	LGWR.logwriter(dic.logcom, dic.logpath, dic.getSysdate()+" - "+ TAG + ":onLocationChanged " + e.toString());
        	}
	    
    }

    @Override
    public void onProviderDisabled(String provider) {
    	LGWR.logwriter(dic.logcom, dic.logpath, dic.getSysdate()+" - "+ TAG + ".." +"Gps Off!");
    }



	@Override
    public void onProviderEnabled(String provider) {
		LGWR.logwriter(dic.logcom, dic.logpath, dic.getSysdate()+" - "+ TAG + ".." +"Gps On!");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    	LGWR.logwriter(dic.logcom, dic.logpath, dic.getSysdate()+" - "+ TAG + ".." +"Gps status change!");
    }
}
