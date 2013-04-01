package com.example.taxi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity /*implements LocationListener*/ {
	private static final String TAG = "MainActivity";
	public String uid;
	Button btnGPS;
	Button btnTaxiCmd;
	TextView rsltTXT;
	EditText editGPSServer;
	EditText editGPSPort;
	EditText editTaxiServer;
	EditText editTaxiPort;
	EditText editTaxiCmd;
	String LGT="0000.0000";
	String LAT="0000.0000";
	String ALT="0000.0000";
    private LocationManager locationManager;
    private LocationListener mLocationListener;	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rsltTXT = (TextView) findViewById(R.id.rsltTXT);
		btnGPS = (Button) findViewById(R.id.btnGPS);
		btnTaxiCmd = (Button) findViewById(R.id.btnTaxiCmd);
		editGPSServer = (EditText) findViewById(R.id.editGPSServer);
		editGPSPort = (EditText) findViewById(R.id.editGPSPort);
		editTaxiServer = (EditText) findViewById(R.id.editTaxiServer);
		editTaxiPort = (EditText) findViewById(R.id.editTaxiPort);
		editTaxiCmd = (EditText) findViewById(R.id.editTaxiCmd);

		rsltTXT.setText("Отправляем GPS"+"\n");
		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		sysDictionary dic = new sysDictionary();
		try {
		 dic.setUid(tm.getDeviceId());
		 uid=tm.getDeviceId();
		} catch (Exception e) {
			dic.setUid("000000000000000");
			     //uid="353451047760580";
	    	rsltTXT.setText(rsltTXT.getText().toString().trim()+"\n"+e.toString());

	    } 
		
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new GPSLocationListener(dic); 
        
        //rsltTXT = new TextView(this);    
        //LinearLayout ll= new LinearLayout(this);
        //ll.addView(rsltTXT);        
        //setContentView(ll);
	}
    @Override
    protected void onResume() {
        // включаем отслеживание
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);

        // при использовании сетей типа GSM
        // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
        //        mLocationListener);
        
        super.onResume();
    }

    @Override
    protected void onPause() {
     // выключаем отслеживание
     locationManager.removeUpdates(mLocationListener);
     super.onPause();
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void onClickGPS(View v) {
     
		        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmm");
		        String strTime = simpleDateFormat.format(new Date());
		        String testconnect = "imei:"+uid+",tracker,"+strTime+",,F,"+ALT+",A,"+LAT+",N,"+LGT+",E,0;";
		        rsltTXT.setText(rsltTXT.getText().toString().trim()+"\n"+testconnect);
				try {
		            		            
		            /*PrintWriter in = null;
		            Socket clientSocketIn = null;
		            
		            String toServer = editGPSServer.getText().toString().trim();
                    int toServerPort = Integer.parseInt(editGPSPort.getText().toString().trim());
		            clientSocketIn = new Socket(toServer, toServerPort);

		            in = new PrintWriter(clientSocketIn.getOutputStream(), true);
  	                in.println(testconnect);
		            in.close();
		            clientSocketIn.close();*/
					SocketTAXI mSocket = new SocketTAXI();
					mSocket.ServerPutGPS(uid,editGPSServer.getText().toString().trim(),Integer.parseInt(editGPSPort.getText().toString().trim()),ALT,LAT,LGT);
					
		            
		    }catch (Exception e) {
		    	rsltTXT.setText(rsltTXT.getText().toString().trim()+"\n"+e.toString());

		    } 
	  }
	public void onClickTaxi(View v) {
		rsltTXT.setText("Обрабатываем команду"+"\n");		
		 try
	        {

			    String str_address = editTaxiServer.getText().toString().trim();
	            int str_port = Integer.parseInt(editTaxiPort.getText().toString().trim());
	            String str_command = editTaxiCmd.getText().toString().trim();
	            rsltTXT.setText(rsltTXT.getText().toString().trim()+"\n"+str_command);

	            Socket s = new Socket(str_address, str_port);

	            str_command = str_command+"\n"+s.getInetAddress().getHostAddress()+":"+s.getLocalPort();
	            s.getOutputStream().write(str_command.getBytes());

	           
	            byte buf[] = new byte[100];
	            int r = s.getInputStream().read(buf);
	            String data = new String(buf, 0, r);
	            //Socket s = new Socket("90.189.119.84", 35572);
			    //String message = "imei:353451047760580:orders_list,quit;";//+"\n"+s.getInetAddress().getHostAddress()+":"+s.getLocalPort();
	            //s.getOutputStream().write(message.getBytes());
	            //            byte buf[] = new byte[100];
	            //int r = s.getInputStream().read(buf);
	            //String data = new String(buf, 0, r);

	            rsltTXT.setText(rsltTXT.getText().toString().trim()+"\n"+data);
	            
	            
	        }
	        catch(Exception e)
	        {
	        	//System.out.println("init error: "+e);
	        	 Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
	        	rsltTXT.setText(rsltTXT.getText().toString().trim()+"\n"+e.toString());
	        	}
	  }

	public void rsltLOG(String valstr) {
		rsltTXT.setText(valstr);
	}
	/*
    public class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {
            loc.getLatitude();
            loc.getLongitude();
            String txt = "Текущее местоположение: " + "\nLatitud = "
                    + loc.getLatitude() + "\nLongitud = " + loc.getLongitude();
            rsltTXT.setText(txt);
    		LGT=""+loc.getLatitude();
    		LAT=""+loc.getLongitude();
    		ALT=""+loc.getAltitude();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps выключен",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps включен",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }*/


}
