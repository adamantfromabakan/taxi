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
	public int PaleTurquoise;	//#AFEEEE
	public int LightCyan;	//#E0FFFF
	public int Azure;	//#F0FFFF
	public int CadetBlue;	//#5F9EA0
	public int PowderBlue;	//#B0E0E6
	public int LightBlue;	//#ADD8E6
	public int SkyBlue;	//#87CEEB
	public int LightskyBlue;//	#87CEFA
	public int SteelBlue;	//#4682B4
	public int AliceBlue;	//#F0F8FF
	public int SlateGray;	//#708090
	public int LightSlateGray;	//#778899
	public int LightsteelBlue;	//#B0C4DE
	/*

PaleTurquoise	#AFEEEE
LightCyan	#E0FFFF
Azure	#F0FFFF
CadetBlue	#5F9EA0
PowderBlue	#B0E0E6
LightBlue	#ADD8E6
SkyBlue	#87CEEB
LightskyBlue	#87CEFA
SteelBlue	#4682B4
AliceBlue	#F0F8FF
SlateGray	#708090
LightSlateGray	#778899
LightsteelBlue	#B0C4DE
CornflowerBlue	#6495ED
	 */
	
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
