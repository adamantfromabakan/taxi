package com.example.taxi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class sysPrefs {
	private static final String TAG = "sysPrefs";
	sysDictionary dic;
	
	public sysPrefs(sysDictionary dic) {
    	this.dic=dic;
	}
	
	public sysDictionary getConfig()
	{
        //Context context = getApplicationContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.appcontext); 
    	try {
    		dic.setServerTaxi(prefs.getString("ServerTaxi","90.189.119.84"));
    		dic.setServerTaxiPortGPS(Integer.parseInt(prefs.getString("ServerTaxiPortGPS","35570")));
    		dic.setServerTaxiPortCMD(Integer.parseInt(prefs.getString("ServerTaxiPortCMD","35572")));
    		dic.setDefaultIMEI(prefs.getString("DefaultIMEI","354473050898478"));
    		dic.setInternalIMEI(prefs.getBoolean("InternalIMEI",false));
    		dic.setOrderFreshPer(Integer.parseInt(prefs.getString("orderfreshper","20000")));
    		dic.setGpsPerFresh(Integer.parseInt(prefs.getString("gpsperfresh","10000")));
    		dic.setGpsIdleFresh(Integer.parseInt(prefs.getString("gpsidlefresh","600000")));
    		dic.setOrderBySave(prefs.getString("OrderBySave","/mnt/sdcard2"));
    		dic.setAutoAnsIPATS(prefs.getBoolean("AutoAnsIPATS",false));
    		dic.setAutoGotoOrders(prefs.getBoolean("AutoGotoOrders",false));
    		dic.setServerIPATSIP(prefs.getString("ServerIPATSIP","90.189.119.84"));
    		dic.setServerIPATSPort(Integer.parseInt(prefs.getString("ServerIPATSPort","14570")));
    		dic.setServerIPATSLogin(prefs.getString("ServerIPATSLogin","201"));
    		dic.setServerIPATSPassword(prefs.getString("ServerIPATSPassword","q1kdid93"));
    		dic.setServerIPATSname1(prefs.getString("ServerIPATSname1","Оператор"));
    		dic.setServerIPATSnumber1(prefs.getString("ServerIPATSnumber1","101"));
    		dic.setServerIPATSname2(prefs.getString("ServerIPATSname2","Оператор"));
    		dic.setServerIPATSnumber2(prefs.getString("ServerIPATSnumber2","102"));
    		dic.setServerIPATSname3(prefs.getString("ServerIPATSname3",""));
    		dic.setServerIPATSnumber3(prefs.getString("ServerIPATSnumber3",""));
    		dic.setServerIPATSname4(prefs.getString("ServerIPATSname4",""));
    		dic.setServerIPATSnumber4(prefs.getString("ServerIPATSnumber4",""));
    		dic.setServerIPATSname5(prefs.getString("ServerIPATSname5",""));
    		dic.setServerIPATSnumber5(prefs.getString("ServerIPATSnumber5",""));
    		dic.setServerIPATSname6(prefs.getString("ServerIPATSname6","Шеф"));
    		dic.setServerIPATSnumber6(prefs.getString("ServerIPATSnumber6","83904122222"));
    		dic.setRingFile(prefs.getString("RingFile","Ringln.wav"));
    		dic.setOpenStreetMapsUpdate(prefs.getString("OpenStreetMapsUpdate","https://data.gis-lab.info/osm_dump/lates/RU-KK.osm.pbf"));

            //return dic;
    	} catch (Exception e) {
    		Log.d(TAG, e.toString());
        }
		return dic;		
	}
	
	public void putConfig() {
        //Context context = getApplicationContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.appcontext); 
        Editor e = prefs.edit();
		e.putString("ServerTaxi", dic.getServerTaxi());
		e.putString("ServerTaxiPortGPS", dic.getServerTaxiPortGPS()+"");
		e.putString("ServerTaxiPortCMD", dic.getServerTaxiPortCMD()+"");
		e.putString("DefaultIMEI", dic.getDefaultIMEI());
		e.putBoolean("InternalIMEI", dic.getInternalIMEI());
		e.putString("orderfreshper", dic.getOrderFreshPer()+"");
		e.putString("gpsperfresh", dic.getGpsPerFresh()+"");
		e.putString("gpsidlefresh", dic.getGpsIdleFresh()+"");
		e.putString("OrderBySave", dic.getOrderBySave()+"");
		e.putBoolean("AutoAnsIPATS", dic.getAutoAnsIPATS());
		e.putBoolean("AutoGotoOrders", dic.getAutoGotoOrders());
		e.putString("ServerIPATSIP", dic.getServerIPATSIP());
		e.putString("ServerIPATSPort", dic.getServerIPATSPort()+"");
		e.putString("ServerIPATSLogin", dic.getServerIPATSLogin());
		e.putString("ServerIPATSPassword", dic.getServerIPATSPassword());
		e.putString("ServerIPATSname1", dic.getServerIPATSname1());
		e.putString("ServerIPATSnumber1", dic.getServerIPATSnumber1());
		e.putString("ServerIPATSname2", dic.getServerIPATSname2());
		e.putString("ServerIPATSnumber2", dic.getServerIPATSnumber2());
		e.putString("ServerIPATSname3", dic.getServerIPATSname3());
		e.putString("ServerIPATSnumber3", dic.getServerIPATSnumber3());
		e.putString("ServerIPATSname4", dic.getServerIPATSname4());
		e.putString("ServerIPATSnumber4", dic.getServerIPATSnumber4());
		e.putString("ServerIPATSname5", dic.getServerIPATSname5());
		e.putString("ServerIPATSnumber5", dic.getServerIPATSnumber5());
		e.putString("ServerIPATSname6", dic.getServerIPATSname6());
		e.putString("ServerIPATSnumber6", dic.getServerIPATSnumber6());
		e.putString("RingFile", dic.getRingFile());
		e.putString("OpenStreetMapsUpdate", dic.getOpenStreetMapsUpdate());
		e.commit();
	}
	
}
