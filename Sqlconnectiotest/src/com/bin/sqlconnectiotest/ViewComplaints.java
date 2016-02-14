package com.bin.sqlconnectiotest;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ListView;

public class ViewComplaints extends Activity {
	 ListView listitem;
	 ListViewAdapter listviewadapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_complaints);
		
	/*}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_complaints, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}*/
		
		List<Complaints> complaintslist = new ArrayList<Complaints>();
		List<String>complaintidlist = new ArrayList<String>();
		List<String> complaintdatelist = new ArrayList<String>();
		List<String> complainttimelist = new ArrayList<String>();
		List<String> descriptionlist = new ArrayList<String>();
		List<Bitmap> imagelist = new ArrayList<Bitmap>();
		String[] complaintid;
		String[] complaintdate;
		String[] complainttime;
		String[] description;
		Bitmap[]image;
		Connection conn;
		//int[] image;
//complaintid = new String[] { "1", "2"};
		
//complaintdate = new String[] { "2-12-19", "3-3-12"};
//complainttime = new String[] { "4:00:00", "6:00:8"};
//description = new String[] { "1,354,040,000", "1,210,193,422" };

//image = new int[] { R.drawable.book_complaints, R.drawable.book_complaints,R.drawable.book_complaints, R.drawable.book_complaints,R.drawable.book_complaints, R.drawable.book_complaints,R.drawable.book_complaints, R.drawable.book_complaints};

listitem = (ListView) findViewById(R.id.listview);
conn=CONN();
if(conn!=null)
{
	 String query = "select * from COMPLAINT_BOOKING";
		Statement stmt;
		
		Statement stmt1;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
			{
				complaintidlist.add(rs.getString("CMLT_ID"));
				complaintdatelist.add(rs.getString("CMLT_DATE"));
				complainttimelist.add(rs.getString("CMLT_TIME"));
				descriptionlist.add(rs.getString("CMLT_DESCRIPTION"));
			}
			for(int i=0;i<complaintidlist.size();i++)
			{
				String query1 = "select * from COMPLAINT_IMAGE where CMLT_ID ="+complaintidlist.get(i)+"";		
				stmt1 = conn.createStatement();
				ResultSet rs1 = stmt.executeQuery(query1);
				while(rs1.next())
			{
				Blob immAsBlob = rs1.getBlob("COMLTIM_IMAGE");
				byte[] immAsBytes = immAsBlob.getBytes(1, (int)immAsBlob.length());
				  BitmapFactory.Options options = new BitmapFactory.Options();
				  Bitmap bitmap = BitmapFactory.decodeByteArray(immAsBytes, 0, immAsBytes.length, options); //Convert bytearray to bitmap
	        //for performance free the memmory allocated by the bytearray and the blob variable
				//  immAsBlob.free();
				
				//Bitmap bmp = BitmapFactory.decodeByteArray(immAsBytes, 0, immAsBytes.length);
				imagelist.add(bitmap);
			}
			}
			if(complaintidlist.size()==0)
				callerror("No Complaints to Show");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		complaintid = complaintidlist.toArray(new String[complaintidlist.size()]);
		
		complaintdate = complaintdatelist.toArray(new String[complaintdatelist.size()]) ;
		complainttime = complainttimelist.toArray(new String[complainttimelist.size()]) ;
		description = descriptionlist.toArray(new String[descriptionlist.size()]);
		image=imagelist.toArray(new Bitmap[imagelist.size()]);
		for (int i = 0; i < complaintid.length; i++) {
	Complaints com = new Complaints(image[i],
			complaintid[i], complaintdate[i],complainttime[i], description[i]);
	complaintslist.add(com);
}
listviewadapter = new ListViewAdapter(this, R.layout.listview_item,
		complaintslist);

// Binds the Adapter to the ListView
listitem.setAdapter(listviewadapter);
listitem.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

listitem.setMultiChoiceModeListener(new MultiChoiceModeListener()
{

	@Override
	public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
		// TODO Auto-generated method stub
		mode.getMenuInflater().inflate(R.menu.view_complaints, menu);
		return true;
		
	}

	@Override
	public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onActionItemClicked(android.view.ActionMode mode,
			MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.delete:
			// Calls getSelectedIds method from ListViewAdapter Class
			SparseBooleanArray selected = listviewadapter
					.getSelectedIds();
			// Captures all selected ids with a loop
			for (int i = (selected.size() - 1); i >= 0; i--) {
				if (selected.valueAt(i)) {
					Complaints selecteditem = listviewadapter
							.getItem(selected.keyAt(i));
					// Remove selected items following the ids
					listviewadapter.remove(selecteditem);
					String cmplid=selecteditem.getcomplaintid();
					Connection connnew=CONN();
					if(connnew!=null)
					{
						String query1 = "delete from COMPLAINT_IMAGE where[CMLT_ID]="+cmplid+"";
						String query = "delete from COMPLAINT_BOOKING where[CMLT_ID]="+cmplid+"";
					
					Statement stmt,stmt1;
					
					try {

						stmt = connnew.createStatement();
stmt1 = connnew.createStatement();
					 stmt.executeUpdate(query1);
					 stmt1.executeUpdate(query);
					 
						
					
					
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
				}
			}
			// Close CAB
			mode.finish();
			return true;
		default:
			return false;
		}
		
	
	}

	@Override
	public void onDestroyActionMode(android.view.ActionMode mode) {
		// TODO Auto-generated method stub
		listviewadapter.removeSelection();
	}

	@Override
	public void onItemCheckedStateChanged(android.view.ActionMode mode,
			int position, long id, boolean checked) {
		// TODO Auto-generated method stub
		final int checkedCount = listitem.getCheckedItemCount();
		// Set the CAB title according to total checked items
		mode.setTitle(checkedCount + " Selected");
		// Calls toggleSelection method from ListViewAdapter Class
		listviewadapter.toggleSelection(position);
		
	}
	
});

// Capture ListView item click
/*listitem.setMultiChoiceModeListener(new MultiChoiceModeListener()  {
	  @Override
      public void onItemCheckedStateChanged(ActionMode mode,
              int position, long id, boolean checked)
	{
		// Capture total checked items
		final int checkedCount = listitem.getCheckedItemCount();
		// Set the CAB title according to total checked items
		mode.setTitle(checkedCount + " Selected");
		// Calls toggleSelection method from ListViewAdapter Class
		listviewadapter.toggleSelection(position);
	}

	  @Override
      public void onDestroyActionMode(ActionMode mode) {
         // Not used.
      }
	@Override
	public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
		// TODO Auto-generated method stub
	mode.getMenuInflater().inflate(R.menu.view_complaints, menu);
	return true;
	}

	@Override
	public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onActionItemClicked(android.view.ActionMode mode,
			MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.delete:
			// Calls getSelectedIds method from ListViewAdapter Class
			SparseBooleanArray selected = listviewadapter
					.getSelectedIds();
			// Captures all selected ids with a loop
			for (int i = (selected.size() - 1); i >= 0; i--) {
				if (selected.valueAt(i)) {
					Complaints selecteditem = listviewadapter
							.getItem(selected.keyAt(i));
					// Remove selected items following the ids
					listviewadapter.remove(selecteditem);
					String cmplid=selecteditem.getcomplaintid();
					Connection connnew=CONN();
					if(connnew!=null)
					{
						String query1 = "delete from COMPLAINT_IMAGE where[CMLT_ID]="+cmplid+"";
						String query = "delete from COMPLAINT_BOOKING where[CMLT_ID]="+cmplid+"";
					
					Statement stmt;
					
					try {
						stmt = connnew.createStatement();
					 stmt.executeQuery(query1);
					 stmt.executeQuery(query);
					 
						
					
					
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
				}
			}
			// Close CAB
			mode.finish();
			return true;
		default:
			return false;
		}
		
	}

	@Override
	public void onDestroyActionMode(android.view.ActionMode mode) {
		// TODO Auto-generated method stub
		listviewadapter.removeSelection();
	}

	@Override
	public void onItemCheckedStateChanged(android.view.ActionMode mode,
			int position, long id, boolean checked) {
		// TODO Auto-generated method stub
		
	}
});*/

}
else
{
	
 	callerror("There is problem with server connection..");
}
try {
	conn.close();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
// Locate the ListView in listview_main.xml

// Pass results to ListViewAdapter Class
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

}