package com.example.taxi;

import android.content.Context;
import android.telephony.TelephonyManager;

public class sysDictionary {
	public String ServerTaxi="90.189.119.84";
	public int ServerTaxiPortGPS=35570;
	public int ServerTaxiPortCMD=35572;
	public String Uid;
	public String logpath = "/data/logs/";
	public String loggps = "taxi-log-gps.log";
	public String logcmd = "taxi-log-cmd.log";
	
	public void setUid(String vl) {
		Uid=vl;
	}
	public String getUid() {
		return Uid;
	}
	
	public void setServerTaxi(String vl) {
		ServerTaxi=vl;
	}
	public String getServerTaxi() {
		return ServerTaxi;
	}
	
	public void setServerTaxiPortGPS(int vl) {
		ServerTaxiPortGPS=vl;
	}
	public int getServerTaxiPortGPS() {
		return ServerTaxiPortGPS;
	}
	
	public void setServerTaxiPortCMD(int vl) {
		ServerTaxiPortCMD=vl;
	}
	public int getServerTaxiPortCMD() {
		return ServerTaxiPortCMD;
	}
	

}
