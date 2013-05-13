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

public class sysConfig extends Properties implements java.io.Serializable   {
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
		sysConfig prop = new sysConfig(this.dic);

	    //File sdPath = Environment.getExternalStorageDirectory();
	    //sdPath = new File(sdPath.getAbsolutePath() + "/" + "/taxi1/conf/");
    	File sdPath = new File("/mnt/sdcard/taxi1/conf");
	    sdPath.mkdirs();
	    //File sdFile = new File(sdPath, file);
	    
    	try {
    		String fileName = sdPath+"/"+"config.properties";
    		prop.put("ServerTaxi", dic.ServerTaxi);
    		prop.put("ServerTaxiPortGPS", dic.ServerTaxiPortGPS+"");
    		prop.put("ServerTaxiPortCMD", dic.ServerTaxiPortCMD+"");
    		prop.put("DefaultIMEI", dic.DefaultIMEI);
    		prop.put("InternalIMEI", dic.InternalIMEI+"");
    		prop.put("orderfreshper", dic.orderfreshper+"");
    		prop.put("gpsperfresh", dic.gpsperfresh+"");
    		prop.put("gpsidlefresh", dic.gpsidlefresh+"");
    		prop.put("OrderBySave", dic.OrderBySave+"");
    		prop.put("AutoAnsIPATS", dic.AutoAnsIPATS+"");
    		prop.put("AutoGotoOrders", dic.AutoGotoOrders+"");
    		prop.put("ServerIPATS.IP", dic.ServerIPATSIP);
    		prop.put("ServerIPATS.Port", dic.ServerIPATSPort+"");
    		prop.put("ServerIPATS.Login", dic.ServerIPATSLogin);
    		prop.put("ServerIPATS.Password", dic.ServerIPATSPassword);
    		prop.put("ServerIPATS.name1", dic.ServerIPATSname1);
    		prop.put("ServerIPATS.number1", dic.ServerIPATSnumber1);
    		prop.put("ServerIPATS.name2", dic.ServerIPATSname2);
    		prop.put("ServerIPATS.number2", dic.ServerIPATSnumber2);
    		prop.put("ServerIPATS.name3", dic.ServerIPATSname3);
    		prop.put("ServerIPATS.number3", dic.ServerIPATSnumber3);
    		prop.put("ServerIPATS.name4", dic.ServerIPATSname4);
    		prop.put("ServerIPATS.number4", dic.ServerIPATSnumber4);
    		prop.put("ServerIPATS.name5", dic.ServerIPATSname5);
    		prop.put("ServerIPATS.number5", dic.ServerIPATSnumber5);
    		prop.put("ServerIPATS.name6", dic.ServerIPATSname6);
    		prop.put("ServerIPATS.number6", dic.ServerIPATSnumber6);
    		prop.put("RingFile", dic.RingFile);
    		prop.put("OpenStreetMaps.Update", dic.OpenStreetMapsUpdate);
 		
 

    		prop.store(new FileOutputStream(fileName), "config file Taxi App");
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
	}
	
	public void readConfig()
	{
		Properties prop = new Properties();
		 
    	try {
    		String fileName = "config.properties";
    		prop.load(new FileInputStream(fileName));
 
               //get the property value and print it out
            System.out.println(prop.getProperty("database"));
    		System.out.println(prop.getProperty("dbuser"));
    		System.out.println(prop.getProperty("dbpassword"));
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }		
	}
	
}
