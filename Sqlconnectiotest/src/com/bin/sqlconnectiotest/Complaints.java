package com.bin.sqlconnectiotest;

import android.graphics.Bitmap;

public class Complaints {





	private String complaintid;
	private String complainddate,complainttime,complaintdescription;
	private Bitmap image;
 
	public Complaints(Bitmap image2, String complaintid, String complainddate,String complainttime,
			String complaintdescription) {
		this.complaintid = complaintid;
		this.complainddate = complainddate;
		this.complainttime=complainttime;
		this.complaintdescription = complaintdescription;
		this.image = image2;
	}
 
	public String getcomplaintid() {
		return complaintid;
	}
 
	public void setcomplaintid(String complaintid) {
		this.complaintid = complaintid;
	}
 
	public String getcomplaintdescription() {
		return complaintdescription;
	}
 
	public void setcomplaintdescription(String complaintdescription) {
		this.complaintdescription = complaintdescription;
	}
 
	public String getcomplainddate() {
		return complainddate;
	}
 
	public void setPopulation(String complainddate) {
		this.complainddate = complainddate;
	}
 
	public Bitmap getimge() {
		return image;
	}
 
	public void setFlag(Bitmap image) {
		this.image = image;
	}
	public String getcomplainttime() {
		return complainttime;
	}
 
	public void setcomplainttime(String complainttime) {
		this.complainttime = complainttime;
	}
	
}