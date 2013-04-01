package com.example.taxi;

import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.telephony.TelephonyManager;
import android.util.Log;

public class SocketTAXI {
	private static final String TAG = "SocketTAXI";
	
	public void ServerPutGPS(String uid,String toServer,int toServerPort, String ALT, String LAT, String LGT)
	{
     
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmm");
		        String strTime = simpleDateFormat.format(new Date());
		        String strconnect = "imei:"+uid+",tracker,"+strTime+",,F,"+ALT+",A,"+LAT+",N,"+LGT+",E,0;";
		        Log.d(TAG, "Строка GPS:"+strconnect);

				try {
		            		            
		            PrintWriter in = null;
		            Socket clientSocketIn = null;

		            clientSocketIn = new Socket(toServer, toServerPort);

		            in = new PrintWriter(clientSocketIn.getOutputStream(), true);
  	                in.println(strconnect);
		            in.close();
		            clientSocketIn.close();
		            

		    }catch (Exception e) {
		    	System.out.println(e.toString());

		    }
	}

}
