package com.cs5103.project.calendar;

import java.util.GregorianCalendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CalendarWrapper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "Calendar.db";
	
	public  static final String EVENTS = "events";
	public  static final String HOLIDAYS = "holidays";
	public  static final String CATEGORIES = "categories";

	
	public static final String EVENT_ID = "id";
	public static final String EVENT_START = "eventstart";
	public static final String EVENT_END = "eventend";
	public static final String EVENT_TITLE = "title";
	public static final String EVENT_DESC = "desc";
	public static final String EVENT_WK_REPEAT = "weekrepeat";
	public static final String EVENT_MNTH_REPEAT = "monthrepeat";
	public static final String EVENT_YR_REPEAT = "yearrepeat";
	public static final String EVENT_LCTN = "location";
	public static final String EVENT_CTGRY = "category";
	
	public static final String CATEGORY_NAME = "_categoryname";
	public static final String CATEGORY_ID = "_categoryid";
	public static final String CATEGORY_COLOR = "_color";
	
	public static final String HOLIDAY_TITLE = "holidayname";
	public static final String HOLIDAY_DATE = "datestart";
	public static final String HOLIDAY_TYPE = "type";
	public static final String HOLIDAY_ID = "id";
	
	private String[] HOLIDAY_TABLE_COLUMNS = { CalendarWrapper.HOLIDAY_TITLE, 
			CalendarWrapper.HOLIDAY_DATE,
			CalendarWrapper.HOLIDAY_TYPE
			};
	
	
	private static final String EVENT_TABLE_CREATE = "create table " + EVENTS
			+ "(" + EVENT_ID + " integer primary key autoincrement, "
			+ EVENT_START + " bigint, "
			+ EVENT_END + " bigint, "
			+ EVENT_TITLE + " text not null, "
			+ EVENT_DESC + " text, "
			+ EVENT_WK_REPEAT + " integer default 0, "
			+ EVENT_MNTH_REPEAT + " integer default 0, "
			+ EVENT_YR_REPEAT + " integer default 0, "
			+ EVENT_LCTN + " text, "
			+ EVENT_CTGRY + " integer default -1 "
			+ ");";
	
	private static final String CATEGORY_TABLE_CREATE = "create table " + CATEGORIES
			+ "(" + CATEGORY_ID + " integer primary key autoincrement, "
			+ CATEGORY_NAME + " text unique, "
			+ CATEGORY_COLOR + " integer deafult null "
			+ ");";
	
	private static final String HOLIDAY_TABLE_CREATE = "create table " + HOLIDAYS
			+ "(" + HOLIDAY_ID + " integer primary key autoincrement, " 
			+ HOLIDAY_TITLE + " text, "
			+ HOLIDAY_DATE + " bigint, "
			+ HOLIDAY_TYPE + " integer);";
	
	private String HOLIDAY_TABLE_STRING = "(" + HOLIDAY_TITLE + "," +HOLIDAY_DATE+ "," +HOLIDAY_TYPE +")";
	private String EVENT_TABLE_STRING = "(" + EVENT_TITLE + "," 
			+ EVENT_START + "," 
			+ EVENT_END + ","
			+ EVENT_DESC + ","
			+ EVENT_WK_REPEAT + ","
			+ EVENT_MNTH_REPEAT + ","
			+ EVENT_YR_REPEAT + ","
			+ EVENT_LCTN + ","
			+ EVENT_CTGRY + ","
			+")";
	private String CATEGORY_TABLE_STRING = "(" + CATEGORY_NAME + "," + CATEGORY_COLOR + ")";
	
	
	public CalendarWrapper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, factory, version);
	}

	public CalendarWrapper(Context context){
		super(context, DATABASE_NAME, null, 1);
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(EVENT_TABLE_CREATE);
		db.execSQL(CATEGORY_TABLE_CREATE);
		db.execSQL(HOLIDAY_TABLE_CREATE);
		enter2014Holidays(db);	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS " + EVENTS);
		db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES);
		db.execSQL("DROP TABLE IF EXISTS " + HOLIDAYS);
		onCreate(db);
	}
	//Method to populate remaining 2014 USA Holidays
	private void enter2014Holidays(SQLiteDatabase db) {
		GregorianCalendar temp = new GregorianCalendar();
		long longDate;
		
		//Thanksgiving
		temp.set(2014, 11, 27);
		longDate = temp.getTimeInMillis();
		db.execSQL("INSERT INTO Holidays" + HOLIDAY_TABLE_STRING +" VALUES ('Thanksgiving','"+ longDate+"' , '-1')");
		
		//Christmas
		temp.set(2014, 12, 25);
		longDate = temp.getTimeInMillis();		
		db.execSQL("INSERT INTO Holidays" + HOLIDAY_TABLE_STRING +" VALUES ('Christmas','"+ longDate+"' , '-1')");
		
		//New Years
		temp.set(2015, 0, 01);
		longDate = temp.getTimeInMillis();
		db.execSQL("INSERT INTO Holidays" + HOLIDAY_TABLE_STRING +" VALUES ('New Years','"+ longDate+"' , '-1')");
				
	}
}
