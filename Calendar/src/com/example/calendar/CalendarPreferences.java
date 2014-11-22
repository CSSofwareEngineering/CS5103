package com.example.calendar;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class CalendarPreferences {

	public static final String strPreferencesName = "Calendar_Preferences";
	private static final String APP_SHARED_PREFERECES = CalendarPreferences.class.getSimpleName();
	private SharedPreferences sharedPreferences;
	private Editor editor;
	private Context calendarActivity;
	
	public CalendarPreferences(Context calendarActivity) {
		this.sharedPreferences = calendarActivity.getSharedPreferences(APP_SHARED_PREFERECES, Activity.MODE_PRIVATE);
		this.editor = sharedPreferences.edit();
		this.calendarActivity = calendarActivity;
	}
	
	public boolean getHighlightWeekend_Flag() {
		return sharedPreferences.getBoolean(calendarActivity.getString(R.string.highlight_weekend_flag), false);
	}
	
	public void setHighLightWeekend_Flag(boolean boolFlag) {
		editor.putBoolean(calendarActivity.getString(R.string.highlight_weekend_flag), boolFlag);
		editor.commit();
	}
	
	public int getHighlightWeekend_ColorId() {
		return sharedPreferences.getInt(calendarActivity.getString(R.string.highlight_weekend_color), 
						R.color.white);
	}
	
	public void setHighlightWeekend_ColorId(int intColor) {
		editor.putInt(calendarActivity.getString(R.string.highlight_weekend_color), intColor);
		editor.commit();
	}
	
	public boolean getShowHoliday_Flag() {
		return sharedPreferences.getBoolean(calendarActivity.getString(R.string.show_holiday_flag), false);
	}
	
	public void setShowHoliday_Flag(boolean boolFlag) {
		editor.putBoolean(calendarActivity.getString(R.string.show_holiday_flag), boolFlag);
		editor.commit();
	}
	
	public int getShowHoliday_ColorId() {
		return sharedPreferences.getInt(calendarActivity.getString(R.string.show_holiday_color),
						R.color.white);
	}
	
	public void setShowHoliday_ColorId(int intColor) {
		editor.putInt(calendarActivity.getString(R.string.show_holiday_color), intColor);
		editor.commit();
	}
}
