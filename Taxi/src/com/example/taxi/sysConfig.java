package com.example.taxi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import android.os.Environment;

public class sysConfig extends Properties /*implements java.io.Serializable*/   {
	private static final String TAG = "sysConfig";
	sysDictionary dic;
	
	public sysConfig(sysDictionary dic) {
    	this.dic=dic;
	}

	@SuppressWarnings("unchecked")
	  public synchronized Enumeration keys() {
	     Enumeration keysEnum = super.keys();
	     Vector keyList = new Vector();
	     while(keysEnum.hasMoreElements()){
	       keyList.add(keysEnum.nextElement());
	     }
	     Collections.sort(keyList);
	     return keyList.elements();
	  }
	  
	public void writeConfig()
	{
		SortedProperties prop = new SortedProperties();

	    //File sdPath = Environment.getExternalStorageDirectory();
	    //sdPath = new File(sdPath.getAbsolutePath() + "/" + "/taxi1/conf/");
    	File sdPath = new File("/mnt/sdcard/taxi1/conf");
	    sdPath.mkdirs();
	    //File sdFile = new File(sdPath, file);
	    
    	try {
    		String fileName = sdPath+"/"+"config.properties";
    		prop.put("ServerTaxi", dic.getServerTaxi());
    		prop.put("ServerTaxiPortGPS", dic.getServerTaxiPortGPS()+"");
    		prop.put("ServerTaxiPortCMD", dic.getServerTaxiPortCMD()+"");
    		prop.put("DefaultIMEI", dic.getDefaultIMEI());
    		prop.put("InternalIMEI", dic.getInternalIMEI()+"");
    		prop.put("orderfreshper", dic.getOrderFreshPer()+"");
    		prop.put("gpsperfresh", dic.getGpsPerFresh()+"");
    		prop.put("gpsidlefresh", dic.getGpsIdleFresh()+"");
    		prop.put("OrderBySave", dic.getOrderBySave()+"");
    		prop.put("AutoAnsIPATS", dic.getAutoAnsIPATS()+"");
    		prop.put("AutoGotoOrders", dic.getAutoGotoOrders()+"");
    		prop.put("ServerIPATS.IP", dic.getServerIPATSIP());
    		prop.put("ServerIPATS.Port", dic.getServerIPATSPort()+"");
    		prop.put("ServerIPATS.Login", dic.getServerIPATSLogin());
    		prop.put("ServerIPATS.Password", dic.getServerIPATSPassword());
    		prop.put("ServerIPATS.name1", dic.getServerIPATSname1());
    		prop.put("ServerIPATS.number1", dic.getServerIPATSnumber1());
    		prop.put("ServerIPATS.name2", dic.getServerIPATSname2());
    		prop.put("ServerIPATS.number2", dic.getServerIPATSnumber2());
    		prop.put("ServerIPATS.name3", dic.getServerIPATSname3());
    		prop.put("ServerIPATS.number3", dic.getServerIPATSnumber3());
    		prop.put("ServerIPATS.name4", dic.getServerIPATSname4());
    		prop.put("ServerIPATS.number4", dic.getServerIPATSnumber4());
    		prop.put("ServerIPATS.name5", dic.getServerIPATSname5());
    		prop.put("ServerIPATS.number5", dic.getServerIPATSnumber5());
    		prop.put("ServerIPATS.name6", dic.getServerIPATSname6());
    		prop.put("ServerIPATS.number6", dic.getServerIPATSnumber6());
    		prop.put("RingFile", dic.getRingFile());
    		prop.put("OpenStreetMaps.Update", dic.getOpenStreetMapsUpdate());
    		prop.store(new FileOutputStream(fileName), "config file Taxi App");
 
    		sysConfig sp = new sysConfig(this.dic);
    	    sp.put("B", "value B");
    	    sp.put("C", "value C");
    	    sp.put("A", "value A");
    	    sp.put("D", "value D");
    	    FileOutputStream fos = new FileOutputStream("/mnt/sdcard/taxi1/conf/sp.props");
    	    sp.store(fos, "sorted props");
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
	}
	
	public sysDictionary readConfig()
	{
		Properties prop = new Properties();
		 
    	try {
        	File sdPath = new File("/mnt/sdcard/taxi1/conf");
    		String fileName = sdPath+"/"+"config.properties";
    		prop.load(new FileInputStream(fileName));
 
               //get the property value and print it out

    		dic.setServerTaxi(prop.getProperty("ServerTaxi"));
    		dic.setServerTaxiPortGPS(Integer.parseInt(prop.getProperty("ServerTaxiPortGPS")));
    		dic.setServerTaxiPortCMD(Integer.parseInt(prop.getProperty("ServerTaxiPortCMD")));
    		dic.setDefaultIMEI(prop.getProperty("DefaultIMEI"));
    		dic.setInternalIMEI(Integer.parseInt(prop.getProperty("InternalIMEI")));
    		dic.setOrderFreshPer(Integer.parseInt(prop.getProperty("orderfreshper")));
    		dic.setGpsPerFresh(Integer.parseInt(prop.getProperty("gpsperfresh")));
    		dic.setGpsIdleFresh(Integer.parseInt(prop.getProperty("gpsidlefresh")));
    		dic.setOrderBySave(prop.getProperty("OrderBySave"));
    		dic.setAutoAnsIPATS(Integer.parseInt(prop.getProperty("AutoAnsIPATS")));
    		dic.setAutoGotoOrders(Integer.parseInt(prop.getProperty("AutoGotoOrders")));
    		dic.setServerIPATSIP(prop.getProperty("ServerIPATS.IP"));
    		dic.setServerIPATSPort(Integer.parseInt(prop.getProperty("ServerIPATS.Port")));
    		dic.setServerIPATSLogin(prop.getProperty("ServerIPATS.Login"));
    		dic.setServerIPATSPassword(prop.getProperty("ServerIPATS.Password"));
    		dic.setServerIPATSname1(prop.getProperty("ServerIPATS.name1"));
    		dic.setServerIPATSnumber1(prop.getProperty("ServerIPATS.number1"));
    		dic.setServerIPATSname2(prop.getProperty("ServerIPATS.name2"));
    		dic.setServerIPATSnumber2(prop.getProperty("ServerIPATS.number2"));
    		dic.setServerIPATSname3(prop.getProperty("ServerIPATS.name3"));
    		dic.setServerIPATSnumber3(prop.getProperty("ServerIPATS.number3"));
    		dic.setServerIPATSname4(prop.getProperty("ServerIPATS.name4"));
    		dic.setServerIPATSnumber4(prop.getProperty("ServerIPATS.number4"));
    		dic.setServerIPATSname5(prop.getProperty("ServerIPATS.name5"));
    		dic.setServerIPATSnumber5(prop.getProperty("ServerIPATS.number5"));
    		dic.setServerIPATSname6(prop.getProperty("ServerIPATS.name6"));
    		dic.setServerIPATSnumber6(prop.getProperty("ServerIPATS.number6"));
    		dic.setRingFile(prop.getProperty("RingFile"));
    		dic.setOpenStreetMapsUpdate(prop.getProperty("OpenStreetMaps.Update"));

            //return dic;
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
		return dic;		
	}
	
}

class SortedProperties extends Properties {
	  public Enumeration keys() {
	     Enumeration keysEnum = super.keys();
	     Vector<String> keyList = new Vector<String>();
	     while(keysEnum.hasMoreElements()){
	       keyList.add((String)keysEnum.nextElement());
	     }
	     Collections.sort(keyList);
	     return keyList.elements();
	  }
	  
	}
