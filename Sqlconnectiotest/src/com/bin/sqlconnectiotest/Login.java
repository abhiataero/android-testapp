package com.bin.sqlconnectiotest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends ActionBarActivity {
	EditText passwordedit,usernameedit;
	Button login;
	String username,pass;
	Connection con;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		 passwordedit = (EditText) findViewById(R.id.usernamedittext);
		 usernameedit = (EditText) findViewById(R.id.passwordedittext);
		 login = (Button) findViewById(R.id.loginbutton);
		

addListenerOnButton();
	}

	@SuppressLint("NewApi")
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
	public void callerror(String msg)
	{
		
		    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		    alertDialogBuilder.setMessage(msg);
		    alertDialogBuilder.setPositiveButton("Ok",
		        new DialogInterface.OnClickListener() {

		            @Override
		            public void onClick(DialogInterface arg0, int arg1) {
		            	finish();
		        }
		    });

		   
		    AlertDialog alertDialog = alertDialogBuilder.create();
		    alertDialog.show();
		
	}
	public void addListenerOnButton() {

		
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				 String success="";
				 username=usernameedit.getText().toString();
					pass=passwordedit.getText().toString();
					con=CONN();
			 if(username.length()==0 ||pass.length()==0)
				 callerror("username/pasword can not be empty");
			 else
			 {
			 if(con!=null)
			 {
				 String query = "select USER_CODE from USER_MASTER where USER_NAME='"+ username+"'and USER_PWD='"+pass+"'";
					Statement stmt;
					try {
						stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(query);
						if(rs.next())
						{
							Intent myIntent = new Intent(Login.this, MainActivity.class);
							Login.this.startActivity(myIntent);
						}
						else
							callerror("Invalid Credentials");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
			 	
			 }
			 else
			 {
				 
			 	callerror("There is problem with server connection..");
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
}
