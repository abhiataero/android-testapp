package com.bin.sqlconnectiotest;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ClipData.Item;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.GridView;

public class MainActivity extends Activity {
	
	GridView gridView;
	 ArrayList<Item> gridArray = new ArrayList<Item>();
	  CustomGridViewAdapter customGridAdapter;

	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);

	  //set grid view item
      String [] prgmNameList={"Book Complaints","View Complaints","Log Out"};
       int [] prgmImages={R.drawable.book_complaints,R.drawable.viewcomplaints,R.drawable.book_complaints}; 


	  gridView = (GridView) findViewById(R.id.gridView1);
	  customGridAdapter = new CustomGridViewAdapter(this, prgmNameList,prgmImages);
	  gridView.setAdapter(customGridAdapter);
	 }

	
}
	
/*	Connection connect;
	int pp,connecterrorflag=0,flag=0;
	 TextView restname;
	 ArrayAdapter<Object> adapter;
	 public static String salesmanname;
	 ArrayList<ArrayList<String>> list1=new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> listtosend=new ArrayList<ArrayList<String>>();
		int errorflag=0;

	
	public void initilize(){
	   
	    
	}
	@SuppressLint("NewApi")
//	 private ArrayList<ArrayList<String>> CONN(String user,String pass,String db,String server,String query){
		 private Connection CONN(){
	    StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
	     StrictMode.setThreadPolicy(policy);
	     Connection conn=null;
	     String connUrl=null;
	    String username,password,servername,dbname,instanceame;
	    	 
	     try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	     //connUrl="jdbc:jtds:sqlserver://192.168.1.7:1433/"+db+";";
	    // connUrl="jdbc:jtds:sqlserver://"+server+":1433/"+db+";user=" + user + ";password=" + pass + ";";
	    // connUrl="jdbc:jtds:sqlserver://" + server + ";" + "databaseName=" + db + ";user=" + user + ";password=" + pass + ";";
	      username ="sa";
	      password = "supriya123";
	      servername="192.168.1.100";
	      dbname="test";
	      instanceame="MSSQLSERVER";
	     //String username = "sups";
	     //String password = "123456";
	     // if(instanceame.equalsIgnoreCase("default"))
	      {
	    connUrl="jdbc:jtds:sqlserver://"+servername+"/"+dbname+";encrypt=false;user="+username+";password="+password+";";
	      }
	    try
	    {
	    conn=DriverManager.getConnection(connUrl,username,password);
	     Log.w("connopen","now");
	    }
	     Statement stmt = conn.createStatement();
	     ResultSet reset = stmt.executeQuery(query);
	     
	    
	     ResultSetMetaData metadata = reset.getMetaData();
	     int numberOfColumns = metadata.getColumnCount();
	     while (reset.next()) {              
	       //  int i = 0;
	    	 ArrayList<String> arrayList = new ArrayList<String>(); 
	         for(int i=1;i<=numberOfColumns;i++) {
	             arrayList.add(reset.getString(i));
	         }
	         list1.add(arrayList);
	        // arrayList.clear();
	     }
	   //  String[] salesmanarray=convertRStoArray(reset);
	   //  conn.close();
	     
	     
	     catch(SQLException se)
	     {
	         Log.e("ERROR", se.getMessage());
				Log.w("error",se.getMessage());

	     }
	    
	     catch(Exception e){
	         Log.e("ERROR", e.getMessage());
				Log.w("error",e.getMessage());

	     }
	//	return list1;
		return conn;

	   //  return conn;
		 
	    }
	 private ArrayList<ArrayList<String>> runquery(String q)
	{
		Statement stmt;
		try {

			stmt = connect.createStatement();
		
    ResultSet reset = stmt.executeQuery(q);
    
   
    ResultSetMetaData metadata = reset.getMetaData();
    int numberOfColumns = metadata.getColumnCount();
    while (reset.next()) {              
      //  int i = 0;
   	 ArrayList<String> arrayList = new ArrayList<String>(); 
        for(int i=1;i<=numberOfColumns;i++) {
            arrayList.add(reset.getString(i));
        }
        list1.add(arrayList);

		}
   // connect.close();
		}
		catch (SQLException e) {
			//alert1(e.getMessage());
			// TODO Auto-generated catch block
			Log.w("error",e.getMessage());

		}
       // arrayList.clear();
    
  //  String[] salesmanarray=convertRStoArray(reset);
   
	return list1;
    
}

	   	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Connection con=CONN() ;
		TextView hello = (TextView) findViewById(R.id.restname);
		String query = "select USER_NAME from USER_MASTER where USER_ID=' 1'";
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next())
			{
			hello.setText(rs.getString("USER_NAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	   	}
	   
	 }
*/