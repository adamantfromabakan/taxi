package com.example.taxi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;



import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

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
	
	public /*String*/ List<clsOrders> ServerPutCmdOrders(String uid,String toServer,int toServerPort, String cmdOrders)
	{  List<clsOrders> list = new ArrayList<clsOrders>();
	//String list = "";
		try
        {
			
            Socket s = new Socket(toServer, toServerPort);
        	String delims = "[|]";
        	String[] tokens ;

            String str_command = cmdOrders+"\n";
            s.getOutputStream().write(str_command.getBytes());
               
            String line = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF8"),16384);
            StringBuilder responseData = new StringBuilder();
            while((line = in.readLine()) != null) {
                clsOrders obj = new clsOrders();
            	//list=list+"\n"+ line;
            	 tokens = line.split(delims);
            	 Log.d(TAG, "line: "+line);
            	 Log.d(TAG, ""+tokens[1]);
            	 obj.setOrder(tokens[0]);
            	 obj.setId(tokens[1]);
            	 obj.setStatus(tokens[2]);
            	 obj.setOrd_date(tokens[3]);
            	 obj.setOrd_date_beg(tokens[4]);
            	 obj.setOrd_date_out(tokens[5]);
            	 obj.setOrd_date_end(tokens[6]);
            	 obj.setPrice(tokens[7]);
            	 obj.setOrd_from(tokens[8]);
            	 obj.setOrd_to(tokens[9]);
            	
            	 Log.d(TAG, "obj: "+obj.toString());
            	 list.add(obj);
           	 	
            	//System.out.println( line);
	            //rsltTXT.setText(rsltTXT.getText().toString().trim()+"\n"+ line);
            } 
            


            Log.d(TAG, "Данные от сервера по ServerPutCmdOrders получены");
            //return list;
            
        }
        catch(Exception e)
        {    
        	Log.d(TAG, e.toString());
        	//System.out.println("init error: "+e);
        	 //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        	//rsltTXT.setText(rsltTXT.getText().toString().trim()+"\n"+e.toString());
        	}
		
		return list;
  }
	

}
