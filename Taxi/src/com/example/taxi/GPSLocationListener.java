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
	public String SPD="0";

	
    public GPSLocationListener(sysDictionary dic,sysLog LGWR,SocketTAXI mSocket) {
    	this.dic=dic;
    	this.LGWR=LGWR;
    	this.mSocket=mSocket;
	}

	@Override
    public void onLocationChanged(Location loc) {
		try {
		MainActivity.flg_gps_date_end=new Date();
        loc.getLatitude();
        loc.getLongitude();
        String txt = "Текущее местоположение: "+"\nLatitud = "+loc.getLatitude()+"\nLongitud = "+loc.getLongitude()+"\nAltitud = "+loc.getAltitude();
        Log.d(TAG, txt);
        LAT=""+loc.getLatitude();
		LGT=""+loc.getLongitude();
		ALT=""+loc.getAltitude();
		if (loc.hasSpeed()) {
            SPD = ""+ (int) ((loc.getSpeed() * 3600) / 1000); 
         } else {
        	SPD = "0";
        }

		
		double tmpLAT = new BigDecimal(Double.parseDouble(LAT)).setScale(4, RoundingMode.UP).doubleValue();
		double tmpLGT = new BigDecimal(Double.parseDouble(LGT)).setScale(4, RoundingMode.UP).doubleValue();
		double tmpALT = new BigDecimal(Double.parseDouble(ALT)).setScale(4, RoundingMode.UP).doubleValue();
        LAT=""+tmpLAT;
		LGT=""+tmpLGT;
		ALT=""+tmpALT;

		Thread gpsThready = new Thread(new Runnable()
        	{
            public void run()
            	{
            		SocketTAXI mSocket = new SocketTAXI(dic, LGWR);
            		LGWR.logwriter(dic.loggps+"-"+dic.getSysdateLog()+dic.logtype, dic.logpath, dic.getSysdate()+" - "+ TAG + ".." +"imei:"+dic.getUid()+",tracker,"+dic.getSysdateGps()+",,F,"+ALT+",A,"+LAT+",N,"+LGT+",E,"+SPD+";");
            		mSocket.ServerPutGPS(dic.getUid(),dic.getServerTaxi(),dic.getServerTaxiPortGPS(),ALT,LAT,LGT,SPD);
            	}
        	});
		
		long dtdiff=(MainActivity.flg_gps_date_end.getTime()-MainActivity.flg_gps_date_beg.getTime());
		if (LAT.equals(MainActivity.LAT) && LGT.equals(MainActivity.LGT)) {
			//System.out.println("idle");
			if (dtdiff>dic.getGpsIdleFresh()) {
				gpsThready.start();
				LGWR.logwriter(dic.loggps+"-"+dic.getSysdateLog()+dic.logtype, dic.logpath, dic.getSysdate()+" - "+ TAG + ".." +"initialized thread GPS:"+gpsThready.getId());
				MainActivity.flg_gps_date_beg=MainActivity.flg_gps_date_end;
			}
			} else {
			if (dtdiff>dic.getGpsPerFresh()) {
				gpsThready.start();
				LGWR.logwriter(dic.loggps+"-"+dic.getSysdateLog()+dic.logtype, dic.logpath, dic.getSysdate()+" - "+ TAG + ".." +"initialized thread GPS:"+gpsThready.getId());
				MainActivity.flg_gps_date_beg=MainActivity.flg_gps_date_end;
			}
		}	
		//System.out.println( ""+" "+dtdiff+" "+dic.getGpsPerFresh()+" "+LAT.trim()+" "+MainActivity.LAT.trim()+" "+LGT+" "+MainActivity.LGT );
		//System.out.println( ""+" "+LAT.trim().equals(MainActivity.LAT.trim()));
		MainActivity.LAT=LAT;
		MainActivity.LGT=LGT;
		MainActivity.ALT=ALT;
		} catch(Exception e)  {    
        	Log.d(TAG, e.toString());
        	LGWR.logwriter(dic.loggps+"-"+dic.getSysdateLog()+dic.logtype, dic.logpath, dic.getSysdate()+" - "+ TAG + ":onLocationChanged " + e.toString());
        	}
	    
    }

    @Override
    public void onProviderDisabled(String provider) {
    	LGWR.logwriter(dic.loggps+"-"+dic.getSysdateLog()+dic.logtype, dic.logpath, dic.getSysdate()+" - "+ TAG + ".." +"Gps Off!");
    }



	@Override
    public void onProviderEnabled(String provider) {
		LGWR.logwriter(dic.loggps+"-"+dic.getSysdateLog()+dic.logtype, dic.logpath, dic.getSysdate()+" - "+ TAG + ".." +"Gps On!");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    	//LGWR.logwriter(dic.loggps+"-"+dic.getSysdateLog()+dic.logtype, dic.logpath, dic.getSysdate()+" - "+ TAG + ".." +"Gps status change!");
    }
}
