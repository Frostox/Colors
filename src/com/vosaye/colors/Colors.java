package com.vosaye.colors;


public class Colors extends android.app.Application{
	ColorsDatabase colorsDB;
	public void onCreate(){
		super.onCreate();
		colorsDB = new ColorsDatabase(this.getApplicationContext(),"colors",null,1);
		
	}
}
