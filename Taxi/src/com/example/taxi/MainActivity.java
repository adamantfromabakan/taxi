package com.example.taxi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity /*implements LocationListener*/ {
	private static final String TAG = "MainActivity";
	public String uid;
	public String ServerTaxi;
	public int ServerTaxiPortGPS;
	public int ServerTaxiPortCMD;
	
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
		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		sysDictionary dic = new sysDictionary();
		//try {
		// dic.setUid(tm.getDeviceId());
		// uid=tm.getDeviceId();
		//} catch (Exception e) {
			dic.setUid("353451047760580");
			       uid="353451047760580";
	    //} 
		ServerTaxi=dic.getServerTaxi();
		ServerTaxiPortGPS=dic.getServerTaxiPortGPS();
		ServerTaxiPortCMD=dic.getServerTaxiPortCMD();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new GPSLocationListener(dic); 
		//setContentView(R.layout.activity_main);
        setContentView(R.layout.main);
        cmdOrderlist();
		/*
		rsltTXT = (TextView) findViewById(R.id.rsltTXT);
		btnGPS = (Button) findViewById(R.id.btnGPS);
		btnTaxiCmd = (Button) findViewById(R.id.btnTaxiCmd);
		editGPSServer = (EditText) findViewById(R.id.editGPSServer);
		editGPSPort = (EditText) findViewById(R.id.editGPSPort);
		editTaxiServer = (EditText) findViewById(R.id.editTaxiServer);
		editTaxiPort = (EditText) findViewById(R.id.editTaxiPort);
		editTaxiCmd = (EditText) findViewById(R.id.editTaxiCmd);
*/

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
		//cmdOrderlist();
     
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
					
					//SocketTAXI mSocket = new SocketTAXI();
					//mSocket.ServerPutGPS(uid,editGPSServer.getText().toString().trim(),Integer.parseInt(editGPSPort.getText().toString().trim()),ALT,LAT,LGT);
					
					//subTable();
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
	            /*rsltTXT.setText(rsltTXT.getText().toString().trim()+"\n"+str_command);

	            Socket s = new Socket(str_address, str_port);

	            str_command = str_command+"\n";//+s.getInetAddress().getHostAddress()+":"+s.getLocalPort();
	            s.getOutputStream().write(str_command.getBytes());
          
	            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF8"),16384);
	            String line = null;
	            StringBuilder responseData = new StringBuilder();
	            while((line = in.readLine()) != null) {
	            	//System.out.println( line);
		            rsltTXT.setText(rsltTXT.getText().toString().trim()+"\n"+ line);
	            } */
	            rsltTXT.setText("");
	            SocketTAXI mSocket = new SocketTAXI();
	            List<clsOrders> list = mSocket.ServerPutCmdOrders(this.uid,str_address, str_port,str_command);
	            //String list = mSocket.ServerPutCmdOrders(this.uid,str_address, str_port,str_command);
				TableLayout table = new TableLayout(this);
				addHead(table);
				addRowTitle(table);
	 		    for(clsOrders tmp : list) {
				//	 System.out.println(tmp.toString());
					addRowOrders(table, tmp.getStatus()+" "+tmp.getId(),tmp.getOrd_date(),tmp.getOrd_from(),tmp.getOrd_to(),tmp.getPrice());
					 rsltTXT.setText(rsltTXT.getText().toString().trim()+"\n"+tmp.getId()+"  "
				+tmp.getStatus()+"  "+tmp.getOrd_date()+"  "+tmp.getOrd_from()+"  "+tmp.getPrice());
					 Toast.makeText(this, rsltTXT.getText().toString().trim(), Toast.LENGTH_LONG).show();

					 }
		        setContentView(table);

	            
	            
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
	
	public void subTable()
	{
		TableLayout table = new TableLayout(this);

        table.setStretchAllColumns(true);
        table.setShrinkAllColumns(true);

        TableRow rowTitle = new TableRow(this);
        rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);

        TableRow rowDayLabels = new TableRow(this);
        TableRow rowHighs = new TableRow(this);
        TableRow rowLows = new TableRow(this);
        TableRow rowConditions = new TableRow(this);
        rowConditions.setGravity(Gravity.CENTER);

        TextView empty = new TextView(this);

        // title column/row
        TextView title = new TextView(this);
        title.setText("Такси №1");

        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        title.setGravity(Gravity.LEFT);
        title.setTypeface(Typeface.SERIF, Typeface.BOLD);
        title.setBackgroundColor(Color.GRAY);
        title.setTextColor(Color.WHITE);
        //title.setCompoundDrawables(1, 1, 1, 1);

        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 6;


        rowTitle.addView(title, params);

        // labels column
        TextView highsLabel = new TextView(this);
        highsLabel.setText("11:22:33");
        highsLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        highsLabel.setTypeface(Typeface.DEFAULT_BOLD);

        TextView lowsLabel = new TextView(this);
        lowsLabel.setText("Day Low");
        lowsLabel.setTypeface(Typeface.DEFAULT_BOLD);

        TextView conditionsLabel = new TextView(this);
        conditionsLabel.setText("Conditions");
        conditionsLabel.setTypeface(Typeface.DEFAULT_BOLD);

        rowDayLabels.addView(empty);
        rowHighs.addView(highsLabel);
        rowLows.addView(lowsLabel);
        rowConditions.addView(conditionsLabel);

        // day 1 column
        TextView day1Label = new TextView(this);
        day1Label.setText("Feb 7");
        day1Label.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TextView day1High = new TextView(this);
        day1High.setText("28°F");
        day1High.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView day1Low = new TextView(this);
        day1Low.setText("15°F");
        day1Low.setGravity(Gravity.CENTER_HORIZONTAL);

        ImageView day1Conditions = new ImageView(this);
        day1Conditions.setImageResource(R.drawable.ic_launcher);

        rowDayLabels.addView(day1Label);
        rowHighs.addView(day1High);
        rowLows.addView(day1Low);
        rowConditions.addView(day1Conditions);

        // day2 column
        TextView day2Label = new TextView(this);
        day2Label.setText("Feb 8");
        day2Label.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TextView day2High = new TextView(this);
        day2High.setText("26°F");
        day2High.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView day2Low = new TextView(this);
        day2Low.setText("14°F");
        day2Low.setGravity(Gravity.CENTER_HORIZONTAL);

        ImageView day2Conditions = new ImageView(this);
        day2Conditions.setImageResource(R.drawable.ic_launcher);

        rowDayLabels.addView(day2Label);
        rowHighs.addView(day2High);
        rowLows.addView(day2Low);
        rowConditions.addView(day2Conditions);

        // day3 column
        TextView day3Label = new TextView(this);
        day3Label.setText("Feb 9");
        day3Label.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TextView day3High = new TextView(this);
        day3High.setText("23°F");
        day3High.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView day3Low = new TextView(this);
        day3Low.setText("3°F");
        day3Low.setGravity(Gravity.CENTER_HORIZONTAL);

        ImageView day3Conditions = new ImageView(this);
        day3Conditions.setImageResource(R.drawable.ic_launcher);

        rowDayLabels.addView(day3Label);
        rowHighs.addView(day3High);
        rowLows.addView(day3Low);
        rowConditions.addView(day3Conditions);

        // day4 column
        TextView day4Label = new TextView(this);
        day4Label.setText("Feb 10");
        day4Label.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TextView day4High = new TextView(this);
        day4High.setText("17°F");
        day4High.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView day4Low = new TextView(this);
        day4Low.setText("5°F");
        day4Low.setGravity(Gravity.CENTER_HORIZONTAL);

        ImageView day4Conditions = new ImageView(this);
        day4Conditions.setImageResource(R.drawable.ic_launcher);

        rowDayLabels.addView(day4Label);
        rowHighs.addView(day4High);
        rowLows.addView(day4Low);
        rowConditions.addView(day4Conditions);

        // day5 column
        TextView day5Label = new TextView(this);
        day5Label.setText("Feb 11");
        day5Label.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TextView day5High = new TextView(this);
        day5High.setText("19°F");
        day5High.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView day5Low = new TextView(this);
        day5Low.setText("6°F");
        day5Low.setGravity(Gravity.CENTER_HORIZONTAL);

        ImageView day5Conditions = new ImageView(this);
        day5Conditions.setImageResource(R.drawable.ic_launcher);

        rowDayLabels.addView(day5Label);
        rowHighs.addView(day5High);
        rowLows.addView(day5Low);
        rowConditions.addView(day5Conditions);

        table.addView(rowTitle);
        table.addView(rowDayLabels);
        table.addView(rowHighs);
        table.addView(rowLows);
        table.addView(rowConditions);

        setContentView(table);
	}
	
	public void cmdOrderlist() {
		 String strcar = null;
		 String strdriver = null;
		 try
	        {

	            SocketTAXI mSocket = new SocketTAXI();
	            List<clsOrders> list = mSocket.ServerPutCmdOrders(this.uid,ServerTaxi, ServerTaxiPortCMD,"imei:"+uid+":orders_list,quit;");
	            List<clsDriverInfo> driver = mSocket.ServerPutCmdDriverInfo(this.uid,ServerTaxi, ServerTaxiPortCMD,"imei:"+uid+":driver_info,quit;");
	            List<clsCarInfo> car = mSocket.ServerPutCmdCarInfo(this.uid,ServerTaxi, ServerTaxiPortCMD,"imei:"+uid+":car_info,quit;");
	            
				TableLayout table = new TableLayout(this);
				addHead(table);
	 		    for(clsCarInfo tmp : car) {
	 		    	strcar=tmp.getCarName();
					 }
	 		    for(clsDriverInfo tmp : driver) {
	 		    	strdriver=tmp.getDriverName();
					 }
	 		    addRowCarDriver(table,strcar,strdriver);
				addRowTitle(table);
	 		    for(clsOrders tmp : list) {
					addRowOrders(table, tmp.getStatus()+" "+tmp.getId(),tmp.getOrd_date(),tmp.getOrd_from(),tmp.getOrd_to(),tmp.getPrice());
					 }

		        setContentView(table);
				//Toast.makeText(this, rsltTXT.getText().toString().trim(), Toast.LENGTH_LONG).show();
	        }
	        catch(Exception e)
	        {
	        	//System.out.println("init error: "+e);
	        	 Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
	        	//rsltTXT.setText(rsltTXT.getText().toString().trim()+"\n"+e.toString());
	        	}
	}
	
	
	public void addRowOrders(TableLayout table, String cell1, String cell2, String cell3, String cell4, String cell5) {
		//TableLayout table = new TableLayout(this);
		//TableLayout table = (TableLayout) findViewById(R.layout.main);
		
        table.setStretchAllColumns(true);
        table.setShrinkAllColumns(true);

        TableRow rowserver = new TableRow(this);
        rowserver.setGravity(Gravity.CENTER_HORIZONTAL);
        //TextView empty = new TextView(this);

        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 6;

        TextView order = new TextView(this);
        order.setText(cell1);
        order.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        //order.setTypeface(Typeface.DEFAULT_BOLD);

        TextView orderdata = new TextView(this);
        orderdata.setText(cell2);
        orderdata.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        //orderdata.setTypeface(Typeface.DEFAULT_BOLD);

        TextView orderfrom = new TextView(this);
        orderfrom.setText(cell3);
        orderfrom.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        //orderfrom.setTypeface(Typeface.DEFAULT_BOLD);

        TextView orderto = new TextView(this);
        orderto.setText(cell4);
        orderto.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        //orderto.setTypeface(Typeface.DEFAULT_BOLD);

        TextView orderprice = new TextView(this);
        orderprice.setText(cell5);
        orderprice.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        //orderprice.setTypeface(Typeface.DEFAULT_BOLD);
        
        rowserver.addView(order);
        rowserver.addView(orderdata);
        rowserver.addView(orderfrom);
        rowserver.addView(orderto);
        rowserver.addView(orderprice);
        
        table.addView(rowserver,params);

        //setContentView(table);
	}
	
	public void addRowCarDriver(TableLayout table, String cell1, String cell2) {
		//TableLayout table = new TableLayout(this);
		//TableLayout table = (TableLayout) findViewById(R.layout.main);
		
        table.setStretchAllColumns(true);
        table.setShrinkAllColumns(true);

        TableRow rowserver = new TableRow(this);
        rowserver.setGravity(Gravity.CENTER_HORIZONTAL);
        //TextView empty = new TextView(this);

        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 6;

        TextView car = new TextView(this);
        car.setText(cell1);
        car.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        //car.setTypeface(Typeface.DEFAULT_BOLD);

        TextView driver = new TextView(this);
        driver.setText(cell2);
        driver.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        //driver.setTypeface(Typeface.DEFAULT_BOLD);
   
        rowserver.addView(car);
        rowserver.addView(driver);

        
        table.addView(rowserver,params);

        //setContentView(table);
	}
	
	public void addRowTitle(TableLayout table) {
		
        table.setStretchAllColumns(true);
        table.setShrinkAllColumns(true);
        
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 6;

        TableRow rowTitle = new TableRow(this);
        rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView order = new TextView(this);
        order.setText("Заказ");
        order.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        order.setBackgroundColor(Color.BLACK);
        order.setTextColor(Color.WHITE);
        order.setHeight(30);
        //order.setTypeface(Typeface.DEFAULT_BOLD);

        TextView orderdata = new TextView(this);
        orderdata.setText("Время подачи");
        orderdata.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        orderdata.setBackgroundColor(Color.BLACK);
        orderdata.setTextColor(Color.WHITE);
        orderdata.setHeight(30);
        //orderdata.setTypeface(Typeface.DEFAULT_BOLD);
        

        TextView orderfrom = new TextView(this);
        orderfrom.setText("Адрес подачи");
        orderfrom.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        orderfrom.setBackgroundColor(Color.BLACK);
        orderfrom.setTextColor(Color.WHITE);
        orderfrom.setHeight(30);
        //orderfrom.setTypeface(Typeface.DEFAULT_BOLD);

        TextView orderto = new TextView(this);
        orderto.setText("Адрес назначения");
        orderto.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        orderto.setBackgroundColor(Color.BLACK);
        orderto.setTextColor(Color.WHITE);
        orderto.setHeight(30);
        //orderto.setTypeface(Typeface.DEFAULT_BOLD);

        TextView orderprice = new TextView(this);
        orderprice.setText("Сумма");
        orderprice.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        orderprice.setBackgroundColor(Color.BLACK);
        orderprice.setTextColor(Color.WHITE);
        orderprice.setHeight(30);
        //orderprice.setTypeface(Typeface.DEFAULT_BOLD);
        
        rowTitle.addView(order);
        rowTitle.addView(orderdata);
        rowTitle.addView(orderfrom);
        rowTitle.addView(orderto);
        rowTitle.addView(orderprice);

        table.addView(rowTitle,params);

	}
	
	public void addHead(TableLayout table) {
		
        table.setStretchAllColumns(true);
        table.setShrinkAllColumns(true);

        TableRow rowTitle = new TableRow(this);
        rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView title = new TextView(this);
        title.setText("Такси №1");

        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        title.setGravity(Gravity.LEFT);
        title.setTypeface(Typeface.SERIF, Typeface.BOLD);
        title.setBackgroundColor(Color.GRAY);
        title.setTextColor(Color.WHITE);
        //title.setCompoundDrawables(1, 1, 1, 1);

        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 6;


        rowTitle.addView(title, params);
       // rowTitle.addView(title);

        table.addView(rowTitle);
        
	}



}
