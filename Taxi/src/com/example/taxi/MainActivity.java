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
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener/*implements LocationListener*/ {
	private static final String TAG = "MainActivity";
	//public String uid;
	public String ServerTaxi;
	public int ServerTaxiPortGPS;
	public int ServerTaxiPortCMD;
	public int Measuredwidth = 0;  
	public int Measuredheight = 0; 
	public String OrderBusy="0";
	
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
    public sysDictionary dic ;
	public sysLog LGWR;
	public SocketTAXI mSocket;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		////LGWR.logwriter(dic.loggps, dic.logpath, dic.getSysdate()+" - "+"imei:"+dic.getUid())+",tracker,"+strTime+",,F,"+loc.getAltitude()+",A,"+loc.getLatitude()+",N,"+loc.getLongitude()+",E,0;");
		super.onCreate(savedInstanceState);
		
		
		
		//StrictMode.setVmPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork());
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = 
			        new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			}
		
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectDiskReads()
        .detectDiskWrites()
        .detectNetwork()   // or .detectAll() for all detectable problems
        .penaltyLog()
        .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        .detectLeakedSqlLiteObjects()
        .detectLeakedClosableObjects()
        .penaltyLog()
        .penaltyDeath()
        .build());

		
		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		 dic = new sysDictionary();
		 mSocket = new SocketTAXI(dic, LGWR);
		 LGWR = new sysLog();
		 LGWR.logwriter(dic.logcom, dic.logpath, dic.getSysdate()+" - Starting program Taxi1...");
		 LGWR.logwriter(dic.logcom, dic.logpath, dic.getSysdate()+" - android.os.Build.VERSION.SDK_INT:"+android.os.Build.VERSION.SDK_INT);
		//try {
		// dic.setUid(tm.getDeviceId());
		// uid=tm.getDeviceId();
		//} catch (Exception e) {
			dic.setUid("353451047760580");
		//	       uid="353451047760580";
	    //} 
		ServerTaxi=dic.getServerTaxi();
		ServerTaxiPortGPS=dic.getServerTaxiPortGPS();
		ServerTaxiPortCMD=dic.getServerTaxiPortCMD();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new GPSLocationListener(dic, LGWR, mSocket); 

        //setContentView(R.layout.activity_main);
        //setContentView(R.layout.main);
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
         LGWR.logwriter(dic.logcom, dic.logpath, dic.getSysdate()+" - Interface loaded!");
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
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
	     case 10000000://R.id.btnOk:
	    	 Toast.makeText(this, ""+"Нажата кнопка 'Текущее время'" , Toast.LENGTH_LONG).show();
	       break;
		case 10000001://R.id.btnOk:
	    	 Toast.makeText(this, ""+"Нажата кнопка 'Обновить заявки'" , Toast.LENGTH_LONG).show();
	    	 cmdOrderlist();
	       break;
	     case 10000002://R.id.btnCancel:
	    	 Toast.makeText(this, ""+"Нажата кнопка 'Звонить оператору'" , Toast.LENGTH_LONG).show();
	       break;
	     case 10000003://R.id.btnCancel:
	    	 Toast.makeText(this, ""+"Нажата кнопка 'Карта'" , Toast.LENGTH_LONG).show();
	       break;
	     case 10000004://R.id.btnCancel:
	    	 Toast.makeText(this, ""+"Нажата кнопка 'Выход'" , Toast.LENGTH_LONG).show();
		       break;
	     case 10000005://R.id.btnCancel:
	    	 Toast.makeText(this, ""+"Нажата кнопка 'Установить время подачи'" , Toast.LENGTH_LONG).show();
		       break;
	     case 10000006://R.id.btnCancel:
	    	 Toast.makeText(this, ""+"Нажата кнопка 'Установить время отъезда'" , Toast.LENGTH_LONG).show();
		       break;
	     case 10000007://R.id.btnCancel:
	    	 Toast.makeText(this, ""+"Нажата кнопка 'Установит время прибытия'" , Toast.LENGTH_LONG).show();
		       break;
	     case 10000008://R.id.btnCancel:
	    	 Toast.makeText(this, ""+"Нажата кнопка 'Километры'" , Toast.LENGTH_LONG).show();
		       break;
	     case 10000009://R.id.btnCancel:
	    	 Toast.makeText(this, ""+"Нажата кнопка 'Такси прибыло'" , Toast.LENGTH_LONG).show();
		       break;


		}
		
	}
	
	public void onClickGPS(View v) {
		//cmdOrderlist();
     
		        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmm");
		        String strTime = simpleDateFormat.format(new Date());
		        String testconnect = "imei:"+dic.getUid()+",tracker,"+strTime+",,F,"+ALT+",A,"+LAT+",N,"+LGT+",E,0;";
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
		    	LGWR.logwriter(dic.logcom, dic.logpath, dic.getSysdate()+" - "+ e.toString());

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
	            //SocketTAXI mSocket = new SocketTAXI();
	            List<clsOrders> list = mSocket.ServerPutCmdOrders(dic.getUid(),str_address, str_port,str_command);
	            //String list = mSocket.ServerPutCmdOrders(this.uid,str_address, str_port,str_command);
				TableLayout table = new TableLayout(this);
				addHead(table);
				addRowTitle(table);
	 		    for(clsOrders tmp : list) {
				//	 System.out.println(tmp.toString());
					addRowOrders(table, tmp.getStatus()+" "+tmp.getId(),tmp.getOrd_date(),tmp.getOrd_from(),tmp.getOrd_to(),tmp.getPrice(),tmp.getStatus());
					 rsltTXT.setText(rsltTXT.getText().toString().trim()+"\n"+tmp.getId()+"  "
				+tmp.getStatus()+"  "+tmp.getOrd_date()+"  "+tmp.getOrd_from()+"  "+tmp.getPrice());
					 //Toast.makeText(this, rsltTXT.getText().toString().trim(), Toast.LENGTH_LONG).show();

					 }
		        setContentView(table);

	            
	            
	        }
	        catch(Exception e)
	        {
	        	//System.out.println("init error: "+e);
	        	// Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
	        	rsltTXT.setText(rsltTXT.getText().toString().trim()+"\n"+e.toString());
	        	LGWR.logwriter(dic.logcom, dic.logpath, dic.getSysdate()+" - "+ e.toString());
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
		        Point size = new Point();
		        WindowManager w = getWindowManager();
		        //if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
		        //      w.getDefaultDisplay().getSize(size);
		        //      Measuredwidth = size.x;
		        //      Measuredheight = size.y; 
		        //    }else{
		              int stat=0;
		              String statstr="";
		              Display d = w.getDefaultDisplay(); 
		              Measuredwidth = d.getWidth(); 
		              Measuredheight = d.getHeight(); 
		              dic.setMsr(Measuredwidth);
		              //Toast.makeText(this, ""+Measuredwidth , Toast.LENGTH_LONG).show();
		        //    }
		              
	            SocketTAXI mSocket = new SocketTAXI(dic, LGWR);
	            List<clsOrders> list = mSocket.ServerPutCmdOrders(dic.getUid(),ServerTaxi, ServerTaxiPortCMD,"imei:"+dic.getUid()+":orders_list,quit;");
	            List<clsDriverInfo> driver = mSocket.ServerPutCmdDriverInfo(dic.getUid(),ServerTaxi, ServerTaxiPortCMD,"imei:"+dic.getUid()+":driver_info,quit;");
	            List<clsCarInfo> car = mSocket.ServerPutCmdCarInfo(dic.getUid(),ServerTaxi, ServerTaxiPortCMD,"imei:"+dic.getUid()+":car_info,quit;");
	            
				TableLayout table = new TableLayout(this);
				//table.setBackgroundResource(R.drawable.abakanmap);
	
		        table.setStretchAllColumns(true);
		        table.setShrinkAllColumns(true);
				addHead(table);
				addRowButton(table);
	 		    for(clsCarInfo tmp : car) {
	 		    	strcar=tmp.getCarName();
					 }
	 		    for(clsDriverInfo tmp : driver) {
	 		    	strdriver=tmp.getDriverName();
					 }
	 		    addRowCarDriver(table,strcar,strdriver);
				addRowTitle(table);
	 		    for(clsOrders tmp : list) {

	 		    	if (tmp.getStatus().trim().length()<5) {
	 		    		statstr="Взять";
	 		        } else {
	 		        	 statstr="Освободить";
	 		        	 OrderBusy=tmp.getId().trim();
	 		        }

					addRowOrders(table, statstr+" "+tmp.getId(),tmp.getOrd_date(),tmp.getOrd_from(),tmp.getOrd_to(),tmp.getPrice(),tmp.getStatus());
					 }
	 		    //table.setBackgroundDrawable(R.drawable.map);
		        setContentView(table);
		        //Toast.makeText(this, "Занята заявка под номером "+this.OrderBusy , Toast.LENGTH_LONG).show();
	        
				
	        }
	        catch(Exception e)
	        {
	        	 Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
	        	 LGWR.logwriter(dic.logcom, dic.logpath, dic.getSysdate()+" - "+ TAG + ":cmdOrderlist " + e.toString());
	        	}
	}
	
	
	public void addRowOrders(TableLayout table, String cell1, String cell2, String cell3, String cell4, String cell5, String iffcell) {
		
        TableRow rowOrders = new TableRow(this);
        rowOrders.setGravity(Gravity.CENTER);
        rowOrders.setBackgroundColor(Color.GRAY);
      
        //TextView empty = new TextView(this);

        TableRow.LayoutParams params = new TableRow.LayoutParams();
        //params.gravity = Gravity.CENTER;
        params.setMargins(1, 1, 1, 1);
        params.span = 4;
        params.height= dic.RowOrdersHeight;
        
        TableRow.LayoutParams paramsAdr = new TableRow.LayoutParams();
        //paramsAdr.gravity = Gravity.CENTER;
        paramsAdr.setMargins(1, 1, 1, 1);
        paramsAdr.span = 5;
        paramsAdr.height= dic.RowOrdersHeight;
        
        TableRow.LayoutParams paramsSum = new TableRow.LayoutParams();
        //paramsSum.gravity = Gravity.CENTER;
        paramsSum.setMargins(1, 1, 1, 1);
        paramsSum.span = 2;
        paramsSum.height= dic.RowOrdersHeight;
        
        TableRow.LayoutParams paramsB = new TableRow.LayoutParams();
        //params.gravity = Gravity.CENTER;
        paramsB.setMargins(1, 1, 1, 1);
        paramsB.span = 4;
        paramsB.height= dic.RowOrdersButtonHeight;
        
        TableRow.LayoutParams paramsFrame = new TableRow.LayoutParams();
        //params.gravity = Gravity.CENTER;
        paramsFrame.setMargins(10, 10, 10, 10);

        
        int nSize=dic.RowOrdersSize;
        int nSizebtn=dic.RowOrdersButtonSize;
        int nHeight=dic.RowOrdersHeight;
        int nHeightbtn=dic.RowOrdersButtonHeight;
        int nGravity=Gravity.CENTER;
        //String statstr="";

        TextView order = new TextView(this);
        order.setText(cell1);
        order.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSize);
        order.setGravity(nGravity);
        order.setPadding(1, 1, 1, 1);
        //order.setHeight(nHeight);
        //order.setTypeface(Typeface.DEFAULT_BOLD);

        TextView orderdata = new TextView(this);
        orderdata.setText(cell2);
        orderdata.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSize);
        orderdata.setGravity(nGravity);
        orderdata.setPadding(1, 1, 1, 1);
        //orderdata.setHeight(nHeight);
        //orderdata.setTypeface(Typeface.DEFAULT_BOLD);

        TextView orderfrom = new TextView(this);
        orderfrom.setText(cell3);
        orderfrom.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSize);
        orderfrom.setGravity(nGravity);
        orderfrom.setPadding(1, 1, 1, 1);
        //orderfrom.setHeight(nHeight);
        //orderfrom.setTypeface(Typeface.DEFAULT_BOLD);

        TextView orderto = new TextView(this);
        orderto.setText(cell4);
        orderto.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSize);
        orderto.setGravity(nGravity);
        orderto.setPadding(1, 1, 1, 1);
        //orderto.setHeight(nHeight);
        //orderto.setTypeface(Typeface.DEFAULT_BOLD);

        TextView orderprice = new TextView(this);
        orderprice.setText(cell5);
        orderprice.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSize);
        orderprice.setGravity(nGravity);
        orderprice.setPadding(1, 1, 1, 1);
        //orderprice.setHeight(nHeight);
        //orderprice.setTypeface(Typeface.DEFAULT_BOLD);
        if (iffcell.length()<5) {

            order.setTextColor(Color.WHITE);
            orderdata.setTextColor(Color.BLACK);
            orderfrom.setTextColor(Color.BLACK);
            orderto.setTextColor(Color.BLACK);
            orderprice.setTextColor(Color.BLACK);
            order.setBackgroundColor(Color.rgb(00, 80, 00));
            orderdata.setBackgroundColor(Color.WHITE);
            orderfrom.setBackgroundColor(Color.WHITE);
            orderto.setBackgroundColor(Color.WHITE);
            orderprice.setBackgroundColor(Color.WHITE); 
            
            rowOrders.addView(order,params);
            rowOrders.addView(orderdata,params);
            rowOrders.addView(orderfrom,paramsAdr);
            rowOrders.addView(orderto,paramsAdr);
            rowOrders.addView(orderprice,paramsSum);
            
            table.addView(rowOrders,paramsFrame);
        } else {
            order.setTextColor(Color.WHITE);
            orderdata.setTextColor(Color.WHITE);
            orderfrom.setTextColor(Color.WHITE);
            orderto.setTextColor(Color.WHITE);
            orderprice.setTextColor(Color.WHITE); 	
            order.setBackgroundColor(Color.rgb(139,00,00));
            orderdata.setBackgroundColor(Color.rgb(64,95,237));
            orderfrom.setBackgroundColor(Color.rgb(64,95,237));
            orderto.setBackgroundColor(Color.rgb(64,95,237));
            orderprice.setBackgroundColor(Color.rgb(64,95,237));
            
            
            TableRow rowBtnOrders = new TableRow(this);
            rowBtnOrders.setGravity(Gravity.CENTER);
            rowBtnOrders.setBackgroundColor(Color.YELLOW);
            
            rowOrders.setBackgroundColor(Color.YELLOW);

            Button Ordersbtn1 = new Button(this);
            Ordersbtn1.setText("Установить время подачи");
            Ordersbtn1.setBackgroundColor(Color.rgb(69,69,69));
            Ordersbtn1.setTextColor(Color.WHITE);
            Ordersbtn1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSizebtn);
            Ordersbtn1.setGravity(nGravity);
            Ordersbtn1.setPadding(1, 1, 1, 1);
            Ordersbtn1.setId(10000005);
            Ordersbtn1.setOnClickListener(this);
            //Ordersbtn1.setHeight(nHeightbtn);
            //Ordersbtn1.setTypeface(Typeface.DEFAULT_BOLD);
            //Ordersbtn1.setWidth(Measuredwidth/5);

            Button Ordersbtn2 = new Button(this);
            Ordersbtn2.setText("Установить время отъезда");
            Ordersbtn2.setBackgroundColor(Color.BLACK);
            Ordersbtn2.setTextColor(Color.WHITE);
            Ordersbtn2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSizebtn);
            Ordersbtn2.setGravity(nGravity);
            Ordersbtn2.setPadding(1, 1, 1, 1);
            Ordersbtn2.setId(10000006);
            Ordersbtn2.setOnClickListener(this);
            //Ordersbtn2.setHeight(nHeightbtn);
            //Ordersbtn2.setTypeface(Typeface.DEFAULT_BOLD);
            //Ordersbtn2.setWidth(Measuredwidth/5);
            

            Button Ordersbtn3 = new Button(this);
            Ordersbtn3.setText("Установить время прибытия");
            Ordersbtn3.setBackgroundColor(Color.rgb(69,69,69));
            Ordersbtn3.setTextColor(Color.WHITE);
            Ordersbtn3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSizebtn);
            Ordersbtn3.setGravity(nGravity);
            Ordersbtn3.setPadding(1, 1, 1, 1);
            Ordersbtn3.setId(10000007);
            Ordersbtn3.setOnClickListener(this);
            //Ordersbtn3.setHeight(nHeightbtn);
            //Ordersbtn3.setTypeface(Typeface.DEFAULT_BOLD);
            //Ordersbtn3.setWidth(Measuredwidth/5);

            Button Ordersbtn4 = new Button(this);
            Ordersbtn4.setText("Километры");
            Ordersbtn4.setBackgroundColor(Color.BLACK);
            Ordersbtn4.setTextColor(Color.WHITE);
            Ordersbtn4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSizebtn);
            Ordersbtn4.setGravity(nGravity);
            Ordersbtn4.setPadding(1, 1, 1, 1);
            Ordersbtn4.setId(10000008);
            Ordersbtn4.setOnClickListener(this);
            //Ordersbtn4.setHeight(nHeightbtn);
            //Ordersbtn4.setTypeface(Typeface.DEFAULT_BOLD);
            //Ordersbtn4.setWidth(Measuredwidth/5);

            Button Ordersbtn5 = new Button(this);
            Ordersbtn5.setText("Такси прибыло");
            Ordersbtn5.setBackgroundColor(Color.rgb(00, 80, 00));
            Ordersbtn5.setTextColor(Color.WHITE);
            Ordersbtn5.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSizebtn);
            Ordersbtn5.setGravity(nGravity);
            Ordersbtn5.setPadding(1, 1, 1, 1);
            Ordersbtn5.setId(10000009);
            Ordersbtn5.setOnClickListener(this);
            //Ordersbtn5.setHeight(nHeightbtn);
            //Ordersbtn5.setTypeface(Typeface.DEFAULT_BOLD);
            //Ordersbtn5.setWidth(Measuredwidth/5);
            

            
            rowOrders.addView(order,params);
            rowOrders.addView(orderdata,params);
            rowOrders.addView(orderfrom,paramsAdr);
            rowOrders.addView(orderto,paramsAdr);
            rowOrders.addView(orderprice,paramsSum);
            
            table.addView(rowOrders,paramsFrame);
            
            
            rowBtnOrders.addView(Ordersbtn1,paramsB);
            rowBtnOrders.addView(Ordersbtn2,paramsB);
            rowBtnOrders.addView(Ordersbtn3,paramsB);
            rowBtnOrders.addView(Ordersbtn4,paramsB);
            rowBtnOrders.addView(Ordersbtn5,paramsB);

            table.addView(rowBtnOrders,paramsFrame);
        }
        



	}
	
	public void addRowCarDriver(TableLayout table, String cell1, String cell2) {

		TableRow rowcardriver = new TableRow(this);
        rowcardriver.setGravity(Gravity.CENTER);
        //rowcardriver.setBackgroundColor(Color.rgb(00, 80, 00));
        rowcardriver.setBackgroundColor(Color.GRAY);
        //TextView empty = new TextView(this);

        TableRow.LayoutParams params = new TableRow.LayoutParams();
        TableRow.LayoutParams params2 = new TableRow.LayoutParams();
        //params.gravity = Gravity.CENTER;
        params.setMargins(1, 1, 1, 1);
        params.span = 8;
        params.height= dic.RowCarDriverHeight;
        params2.setMargins(1, 1, 1, 1);
        params2.span = 12;
        params2.height= dic.RowCarDriverHeight;
        
        int nSize=dic.RowCarDriverSize;
        int nHeight=dic.RowCarDriverHeight;
        int nGravity=Gravity.CENTER;

        TextView car = new TextView(this);
        car.setText(cell1);
        car.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSize);
        car.setBackgroundColor(Color.rgb(00, 80, 00)); //green
        car.setTextColor(Color.WHITE);
        car.setGravity(nGravity);
        car.setPadding(1, 1, 1, 1);
        //car.setTypeface(Typeface.DEFAULT_BOLD);
        //car.setWidth(Measuredwidth/2);

        TextView driver = new TextView(this);
        driver.setText(cell2);
        driver.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSize);
        driver.setBackgroundColor(Color.rgb(00, 80, 00));
        driver.setTextColor(Color.WHITE);
        driver.setGravity(nGravity);
        driver.setPadding(1, 1, 1, 1);
        //driver.setTypeface(Typeface.DEFAULT_BOLD);
        //driver.setWidth(Measuredwidth/2);
   
        rowcardriver.addView(car,params);
        rowcardriver.addView(driver,params2);

        
        table.addView(rowcardriver);

        //setContentView(table);
	}
	
	public void addRowTitle(TableLayout table) {
		
     
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        //params.gravity = Gravity.CENTER;
        params.setMargins(1, 1, 1, 1);
        params.span = 4;
        params.height= dic.RowTitleHeight;

        TableRow.LayoutParams paramsAdr = new TableRow.LayoutParams();
        //paramsAdr.gravity = Gravity.CENTER;
        paramsAdr.setMargins(1, 1, 1, 1);
        paramsAdr.span = 5;
        paramsAdr.height= dic.RowTitleHeight;
        
        TableRow.LayoutParams paramsSum = new TableRow.LayoutParams();
        //paramsSum.gravity = Gravity.CENTER;
        paramsSum.setMargins(1, 1, 1, 1);
        paramsSum.span = 2;
        paramsSum.height= dic.RowTitleHeight;

        TableRow rowTitle = new TableRow(this);
        rowTitle.setGravity(Gravity.CENTER);
        rowTitle.setBackgroundColor(Color.GRAY);
        
        int nSize=dic.RowTitleSize;
        int nHeight=dic.RowTitleHeight;
        int nGravity=Gravity.CENTER;

        TextView order = new TextView(this);
        order.setText("Заказ");
        order.setBackgroundColor(Color.BLACK);
        order.setTextColor(Color.WHITE);
        order.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSize);
        order.setGravity(nGravity);
        //order.setHeight(nHeight);
        //order.setTypeface(Typeface.DEFAULT_BOLD);
        //order.setWidth(Measuredwidth/5);

        TextView orderdata = new TextView(this);
        orderdata.setText("Время подачи");
        orderdata.setBackgroundColor(Color.BLACK);
        orderdata.setTextColor(Color.WHITE);
        orderdata.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSize);
        orderdata.setGravity(nGravity);
        //orderdata.setHeight(nHeight);
        //orderdata.setTypeface(Typeface.DEFAULT_BOLD);
        //orderdata.setWidth(Measuredwidth/5);

        TextView orderfrom = new TextView(this);
        orderfrom.setText("Адрес подачи");
        orderfrom.setBackgroundColor(Color.BLACK);
        orderfrom.setTextColor(Color.WHITE);
        orderfrom.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSize);
        orderfrom.setGravity(nGravity);
        //orderfrom.setHeight(nHeight);
        //orderfrom.setTypeface(Typeface.DEFAULT_BOLD);
        //orderfrom.setWidth(Measuredwidth/5);

        TextView orderto = new TextView(this);
        orderto.setText("Адрес назначения");
        orderto.setBackgroundColor(Color.BLACK);
        orderto.setTextColor(Color.WHITE);
        orderto.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSize);
        orderto.setGravity(nGravity);
        //orderto.setHeight(nHeight);
        //orderto.setTypeface(Typeface.DEFAULT_BOLD);
        //orderto.setWidth(Measuredwidth/5);

        TextView orderprice = new TextView(this);
        orderprice.setText("Сумма");
        orderprice.setBackgroundColor(Color.BLACK);
        orderprice.setTextColor(Color.WHITE);
        orderprice.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSize);
        orderprice.setGravity(nGravity);
        //orderprice.setHeight(nHeight);
        //orderprice.setTypeface(Typeface.DEFAULT_BOLD);
        //orderprice.setWidth(Measuredwidth/5);
        
        rowTitle.addView(order,params);
        rowTitle.addView(orderdata,params);
        rowTitle.addView(orderfrom,paramsAdr);
        rowTitle.addView(orderto,paramsAdr);
        rowTitle.addView(orderprice,paramsSum);

        table.addView(rowTitle);

	}
	
	public void addRowButton(TableLayout table) {
		
		//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy ");
        //String strTime = simpleDateFormat.format(new Date());
        
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        //params.gravity = Gravity.CENTER;
        params.setMargins(1, 1, 1, 1);
        params.span = 4;
        params.height= dic.RowButtonHeight;

        TableRow.LayoutParams params3 = new TableRow.LayoutParams();
        //params.gravity = Gravity.CENTER;
        params3.setMargins(1, 1, 1, 1);
        params3.span = 4;
        params3.height= dic.RowButtonHeight;
        
        TableRow.LayoutParams params5 = new TableRow.LayoutParams();
        //params.gravity = Gravity.CENTER;
        params5.setMargins(1, 1, 1, 1);
        params5.span = 5;
        params5.height= dic.RowButtonHeight;
        
        int nSize=dic.RowButtonSize;
        int nHeight=dic.RowButtonHeight;
        int nGravity=Gravity.CENTER;
        
        TableRow rowBtn = new TableRow(this);
        rowBtn.setGravity(Gravity.CENTER);
        rowBtn.setBackgroundColor(Color.GRAY);
 

        Button btn1 = new Button(this);
        btn1.setText(dic.getSysdate());
        btn1.setBackgroundColor(Color.BLACK);
        btn1.setTextColor(Color.WHITE);
        btn1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSize);
        btn1.setGravity(nGravity);
        //btn1.setHeight(nHeight);
        //btn1.setTypeface(Typeface.DEFAULT_BOLD);
        //btn1.setWidth(Measuredwidth/5);
        btn1.setPadding(1, 1, 1, 1);
        btn1.setId(10000000);
        btn1.setOnClickListener(this);



        Button btn2 = new Button(this);
        btn2.setText("Обновить\nзаявки");
        btn2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSize);
        btn2.setBackgroundColor(Color.BLACK);
        btn2.setTextColor(Color.WHITE);
        btn2.setGravity(nGravity);
        //btn2.setHeight(nHeight);
        //btn2.setTypeface(Typeface.DEFAULT_BOLD);
        //btn2.onClick="cmdOrderlist"
        //btn2.setWidth(Measuredwidth/5);
        btn2.setPadding(1, 1, 1, 1);
        btn2.setId(10000001);
        btn2.setOnClickListener(this);

        

        Button btn3 = new Button(this);
        btn3.setText("Звонить\nоператору");
        btn3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSize);
        btn3.setBackgroundColor(Color.BLACK);
        btn3.setTextColor(Color.WHITE);
        btn3.setGravity(nGravity);
        //btn3.setHeight(nHeight);
        //btn3.setTypeface(Typeface.DEFAULT_BOLD);
        //btn3.setWidth(Measuredwidth/5);
        btn3.setPadding(1, 1, 1, 1);
        btn3.setId(10000002);
        btn3.setOnClickListener(this);

        Button btn4 = new Button(this);
        btn4.setText("Карта");
        btn4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSize);
        btn4.setBackgroundColor(Color.rgb(64,95,237)); //blue
        btn4.setTextColor(Color.WHITE);
        btn4.setGravity(nGravity);
        //btn4.setHeight(nHeight);
        //btn4.setTypeface(Typeface.DEFAULT_BOLD);
        //btn4.setWidth(Measuredwidth/5);
        btn4.setPadding(1, 1, 1, 1);
        btn4.setId(10000003);
        btn4.setOnClickListener(this);        


        Button btn5 = new Button(this);
        btn5.setText("Выход");
        btn5.setTextSize(TypedValue.COMPLEX_UNIT_DIP, nSize);
        btn5.setBackgroundColor(Color.rgb(139,00,00));
        btn5.setTextColor(Color.WHITE);
        btn5.setGravity(nGravity);
        //btn5.setHeight(nHeight);
        //btn5.setTypeface(Typeface.DEFAULT_BOLD);
        //btn5.setWidth(Measuredwidth/5);
        btn5.setPadding(1, 1, 1, 1);
        btn5.setId(10000004);
        btn5.setOnClickListener(this);        

        
        rowBtn.addView(btn1,params);
        rowBtn.addView(btn2,params);
        rowBtn.addView(btn3,params);
        rowBtn.addView(btn4,params);
        rowBtn.addView(btn5,params);

        table.addView(rowBtn);

	}
	
	public void addHead(TableLayout table) {
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        //params.setMargins(1, 1, 1, 1);
        params.span = 1;
        params.height= 0;
		
        int ngrav=Gravity.CENTER;
        TableRow rowColHead = new TableRow(this);
        rowColHead.setGravity(ngrav);
        //rowColHead.setPadding(1, 1, 1, 1);
        
        

        TextView c1 = new TextView(this);
        c1.setText("1");
        c1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c1.setGravity(ngrav);
        c1.setBackgroundColor(Color.GRAY);
        c1.setTextColor(Color.WHITE);

        TextView c2 = new TextView(this);
        c2.setText("2");
        c2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c2.setGravity(ngrav);
        c2.setBackgroundColor(Color.GRAY);
        c2.setTextColor(Color.WHITE);
        
        TextView c3 = new TextView(this);
        c3.setText("3");
        c3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c3.setGravity(ngrav);
        c3.setBackgroundColor(Color.GRAY);
        c3.setTextColor(Color.WHITE);
        
        TextView c4 = new TextView(this);
        c4.setText("4");
        c4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c4.setGravity(ngrav);
        c4.setBackgroundColor(Color.GRAY);
        c4.setTextColor(Color.WHITE);
        
        TextView c5 = new TextView(this);
        c5.setText("5");
        c5.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c5.setGravity(ngrav);
        c5.setBackgroundColor(Color.GRAY);
        c5.setTextColor(Color.WHITE);
        
        TextView c6 = new TextView(this);
        c6.setText("6");
        c6.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c6.setGravity(ngrav);
        c6.setBackgroundColor(Color.GRAY);
        c6.setTextColor(Color.WHITE);
        
        TextView c7 = new TextView(this);
        c7.setText("7");
        c7.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c7.setGravity(ngrav);
        c7.setBackgroundColor(Color.GRAY);
        c7.setTextColor(Color.WHITE);
        
        TextView c8 = new TextView(this);
        c8.setText("8");
        c8.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c8.setGravity(ngrav);
        c8.setBackgroundColor(Color.GRAY);
        c8.setTextColor(Color.WHITE);
        
        TextView c9 = new TextView(this);
        c9.setText("9");
        c9.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c9.setGravity(ngrav);
        c9.setBackgroundColor(Color.GRAY);
        c9.setTextColor(Color.WHITE);
        
        TextView c10 = new TextView(this);
        c10.setText("10");
        c10.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c10.setGravity(ngrav);
        c10.setBackgroundColor(Color.GRAY);
        c10.setTextColor(Color.WHITE);

        TextView c11 = new TextView(this);
        c11.setText("11");
        c11.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c11.setGravity(ngrav);
        c11.setBackgroundColor(Color.GRAY);
        c11.setTextColor(Color.WHITE);

        TextView c12 = new TextView(this);
        c12.setText("12");
        c12.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c12.setGravity(ngrav);
        c12.setBackgroundColor(Color.GRAY);
        c12.setTextColor(Color.WHITE);

        TextView c13 = new TextView(this);
        c13.setText("13");
        c13.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c13.setGravity(ngrav);
        c13.setBackgroundColor(Color.GRAY);
        c13.setTextColor(Color.WHITE);

        TextView c14 = new TextView(this);
        c14.setText("14");
        c14.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c14.setGravity(ngrav);
        c14.setBackgroundColor(Color.GRAY);
        c14.setTextColor(Color.WHITE);

        TextView c15 = new TextView(this);
        c15.setText("15");
        c15.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c15.setGravity(ngrav);
        c15.setBackgroundColor(Color.GRAY);
        c15.setTextColor(Color.WHITE);

        TextView c16 = new TextView(this);
        c16.setText("16");
        c16.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c16.setGravity(ngrav);
        c16.setBackgroundColor(Color.GRAY);
        c16.setTextColor(Color.WHITE);

        TextView c17 = new TextView(this);
        c17.setText("17");
        c17.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c17.setGravity(ngrav);
        c17.setBackgroundColor(Color.GRAY);
        c17.setTextColor(Color.WHITE);

        TextView c18 = new TextView(this);
        c18.setText("18");
        c18.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c18.setGravity(ngrav);
        c18.setBackgroundColor(Color.GRAY);
        c18.setTextColor(Color.WHITE);

        TextView c19 = new TextView(this);
        c19.setText("19");
        c19.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c19.setGravity(ngrav);
        c19.setBackgroundColor(Color.GRAY);
        c19.setTextColor(Color.WHITE);

        TextView c20 = new TextView(this);
        c20.setText("20");
        c20.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        c20.setGravity(ngrav);
        c20.setBackgroundColor(Color.GRAY);
        c20.setTextColor(Color.WHITE);



        rowColHead.addView(c1,params);
        rowColHead.addView(c2,params);
        rowColHead.addView(c3,params);
        rowColHead.addView(c4,params);
        rowColHead.addView(c5,params);
        rowColHead.addView(c6,params);
        rowColHead.addView(c7,params);
        rowColHead.addView(c8,params);
        rowColHead.addView(c9,params);
        rowColHead.addView(c10,params);
        rowColHead.addView(c11,params);
        rowColHead.addView(c12,params);
        rowColHead.addView(c13,params);
        rowColHead.addView(c14,params);
        rowColHead.addView(c15,params);
        rowColHead.addView(c16,params);
        rowColHead.addView(c17,params);
        rowColHead.addView(c18,params);
        rowColHead.addView(c19,params);
        rowColHead.addView(c20,params);
        
        table.addView(rowColHead);

        
	}




}
