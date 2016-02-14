package com.bin.sqlconnectiotest;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

@SuppressLint("NewApi")
public class BookComplaints extends ActionBarActivity {
EditText complid,complno,compldate,compltime,complloca,complbuild,complunit,complcontctno,complname,compldesc;
Button save,upload,capture;
byte[] array;
Connection con;
String clickflag="notclicked";
private static final int CAMERA_REQUEST = 1888; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_complaints);
	//	complid = (EditText) findViewById(R.id.compl_id);
		complno = (EditText) findViewById(R.id.compl_no);
		compldate = (EditText) findViewById(R.id.compl_date);
		compltime = (EditText) findViewById(R.id.compl_time);
		complloca = (EditText) findViewById(R.id.compl_location);
		complbuild = (EditText) findViewById(R.id.compl_building);
		complunit = (EditText) findViewById(R.id.compl_unit);
		complcontctno = (EditText) findViewById(R.id.contact_no);
		complname = (EditText) findViewById(R.id.compl_name);
		compldesc = (EditText) findViewById(R.id.description);
		save = (Button) findViewById(R.id.save);
		upload = (Button) findViewById(R.id.upload);
		capture = (Button) findViewById(R.id.capture);
		addListenerOnuploadButton();
		addListenerOncaptureButton();
		addListenerOnButton();
		
	}
	private Connection CONN(){
	    StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
	     StrictMode.setThreadPolicy(policy);
	     Connection conn=null;
	     String connUrl=null;
	    String username,password,servername,dbname,instanceame;
	    	 
	      
	      username ="sa";
	      password = "supriya123";
	      servername="192.168.1.100";
	      dbname="test";
	      instanceame="MSSQLSERVER";
	     
	      
	    connUrl="jdbc:jtds:sqlserver://"+servername+"/"+dbname+";encrypt=false;user="+username+";password="+password+";";
	      
	    try
	    {
			Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
	    conn=DriverManager.getConnection(connUrl,username,password);
	     Log.w("connopen","now");
	    }
	  
	    catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	     
	     catch(SQLException se)
	     {
	         Log.e("ERROR", se.getMessage());
				Log.w("error",se.getMessage());

	     }
	    
	     catch(Exception e){
	         Log.e("ERROR", e.getMessage());
				Log.w("error",e.getMessage());

	     }
		return conn;

	    
	    }
	public void callerror(String msg,final int flag)
	{
		
		    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		    alertDialogBuilder.setMessage(msg);
		    alertDialogBuilder.setPositiveButton("Ok",
		        new DialogInterface.OnClickListener() {

		            @Override
		            public void onClick(DialogInterface arg0, int arg1) {
		            	if(flag==0)
		            	finish();
		        }
		    });

		   
		    AlertDialog alertDialog = alertDialogBuilder.create();
		    alertDialog.show();
		
	}
public void addListenerOnButton() {

		
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				 String success="";
				 Date date1 = null,date2=null;
					con=CONN();
					 ;
			 if(TextUtils.isEmpty(complno.getText())
					 ||TextUtils.isEmpty(complunit.getText())
					 ||TextUtils.isEmpty(complbuild.getText())
					 ||TextUtils.isEmpty(compldate.getText())
					 ||TextUtils.isEmpty(compltime.getText())
					 ||TextUtils.isEmpty(complloca.getText())
					 ||TextUtils.isEmpty(complname.getText())
					 ||TextUtils.isEmpty(compldesc.getText())
					 ||TextUtils.isEmpty(complcontctno.getText())==true)
				 
				 callerror("above fields  can not be empty",1);
			 else if(clickflag.equalsIgnoreCase("notclicked"))
				 callerror("please upload photo before saving",1);
			 else
			 {
			 if(con!=null)
			 {
				 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				 try {
					 date1 = dateFormat.parse(compldate.getText().toString()+" 00:00:00");
					 date2=dateFormat.parse(compldate.getText().toString()+" "+compltime.getText().toString());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 String query = "insert into COMPLAINT_BOOKING ("+
						// "[CMLT_ID]"+ 
          "[CMLT_NO]"+
           ",[CMLT_DATE]"+
           ",[CMLT_TIME]"+
           ",[CMLT_LOCATION]"+
           ",[CMLT_BUILDING]"+
           ",[CMLT_UNIT]"+
           ",[CMLT_CONTACTNO]"+
           ",[CMLT_CONTACTNAME]"+
        //   ",[CMLT_DESCRIPTION]) values ("+ Integer.parseInt(complid.getText().toString())+","
        ",[CMLT_DESCRIPTION]) values ("
           		+ "'"+complno.getText().toString()+"',"
           		+ "'"+compldate.getText().toString()+" 00:00:00"+"',"
           		+ "'"+compldate.getText().toString()+" "+compltime.getText().toString()+"',"
           		+ "'"+complloca.getText().toString()+"',"
           		+ "'"+complbuild.getText().toString()+"',"
           		+ "'"+complunit.getText().toString()+"',"
           		+ "'"+complcontctno.getText().toString()+"',"
           		+ "'"+complname.getText().toString()+"',"
           		+ "'"+compldesc.getText().toString()+"'"
           		+ ")";
					Statement stmt1,stmt2;
					PreparedStatement stmt;
					try {
						stmt1 = con.createStatement();
						int t=stmt1.executeUpdate(query);
						String query1=
"SELECT [CMLT_ID] FROM COMPLAINT_BOOKING WHERE [CMLT_ID] not in (SELECT TOP (SELECT COUNT(1)-1 FROM COMPLAINT_BOOKING) [CMLT_ID] FROM COMPLAINT_BOOKING)";
						stmt2= con.createStatement();
						
						
						ResultSet rs = stmt2.executeQuery(query1);
						String cmltid="";
						while(rs.next())
						{
							cmltid=(rs.getString("CMLT_ID"));
							

						}
						
						
						
						stmt = con.prepareStatement("INSERT INTO COMPLAINT_IMAGE (CMLT_ID,COMLTIM_IMAGE) VALUES(?,?)");
						ByteArrayInputStream bais = new ByteArrayInputStream(array);
						stmt.setInt(1, Integer.parseInt(cmltid));
				         stmt.setBinaryStream(2, bais, array.length);
				          stmt.executeUpdate();
				          stmt.close();
						System.out.print(t);
						callerror("Record Inserted Successfully..!!",0);
					} catch (SQLException e) {
						callerror(e.getMessage(),1);
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
			 	
			 }
			 else
			 {
				 
			 	callerror("There is problem with server connection..",0);
			 }

			 try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 }
			
			}//onclick

		});

	}
public void addListenerOncaptureButton() {

	
	capture.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			
			clickflag="clicked";
			
			Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
            startActivityForResult(cameraIntent, CAMERA_REQUEST); 
			
				
		
		 
		 
		
		}//onclick

	});

}
public void addListenerOnuploadButton() {

	
	upload.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			
			clickflag="clicked";
			
			Intent pickPhoto = new Intent(
					Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
			startActivityForResult(pickPhoto , 1);
				
		
		 
		 
		
		}//onclick

	});

}

protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
    if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
        Bitmap photo = (Bitmap) data.getExtras().get("data"); 
       // byte[] immAsBytes = 
        int bytes = photo.getByteCount();
      //or we can calculate bytes this way. Use a different value than 4 if you don't use 32bit images.
      //int bytes = b.getWidth()*b.getHeight()*4; 

      ByteBuffer buffer = ByteBuffer.allocate(bytes); //Create a new buffer
      photo.copyPixelsToBuffer(buffer); //Move the byte data to the buffer
      array=buffer.array();
    }
   
      if (requestCode == 1 && resultCode == RESULT_OK){  
    	  Bitmap photo1 = (Bitmap) data.getExtras().get("data"); 
          // byte[] immAsBytes = 
           int bytes1 = photo1.getByteCount();
         //or we can calculate bytes this way. Use a different value than 4 if you don't use 32bit images.
         //int bytes = b.getWidth()*b.getHeight()*4; 

         ByteBuffer buffer1 = ByteBuffer.allocate(bytes1); //Create a new buffer
         photo1.copyPixelsToBuffer(buffer1); //Move the byte data to the buffer
         array=buffer1.array();
         
        }
      /*byte[] array = buffer.array();
      con=CONN();
		
      if(con!=null)
		 {
    	  PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement("INSERT INTO COMPLAINT_IMAGE (COMLTIM_ID,CMLT_ID,COMLTIM_IMAGE) VALUES(?,?,?)");
			ByteArrayInputStream bais = new ByteArrayInputStream(array);
	          
			pstmt.setInt(1, 10);
			pstmt.setInt(2, 45);
	          pstmt.setBinaryStream(3, bais, array.length);
	          pstmt.executeUpdate();
	          pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
		 }
			
		 else
		 {
			 
		 	callerror("There is problem with server connection..",0);
		 }

		 try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     */
    
} 

}
