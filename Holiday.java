package com.cs5103.project.calendar;

public class Holiday {
	private int id;
	private String holiday;
	private long holiday_date;
	private int type;
	
	public void setID(int id){
		this.id = id;
	}
	public int getID(){
		return id;
	}
	
	public String getHoliday() {
		return holiday;
	}
	
	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}
	public long getHoliday_date() {
		return holiday_date;
	}
	
	public void setHoliday_date(long holiday_date) {
		this.holiday_date = holiday_date;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
