package com.example.calendar;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.text.DateFormatSymbols;

// Abstract class for all view fragments (month, week, day) in the Calendar App

public abstract class MainFragment extends Fragment {

    public static final String ARG_EVENT_HEADER = "event_header";
    public static final String ARG_CALENDAR_HEADER = "calendar_header";
    public static final String FORMAT_CALENDAR_HEADER = "MMMM yyyy";
    public static final String FORMAT_EVENT_HEADER = "d MMM yyyy";
    
    private Calendar todayCalendar = Calendar.getInstance();
   
/*    
    private String[] gridData = new String[] {
			"A","B","C","D","E","F","G",
			"H","I","J","K","L","M","N"
    };

NO LONGER USED - UI development and testing only */

    private String [] daysOfWeekFull = new DateFormatSymbols().getWeekdays();
    private List<String> gridDays = new ArrayList<String>();
	
    public MainFragment() {
    	for (int i = 1; i < daysOfWeekFull.length; i++) {
    		gridDays.add(daysOfWeekFull[i].substring(0,2));
    	}
    }
    
    public CharSequence getFormattedDate(String strFormat, Calendar date) {
    	return android.text.format.DateFormat.format(strFormat, date);
    }
    
    public String getDayOfWeek(Calendar date) {
    	int intDayOfWeek = date.get(Calendar.DAY_OF_WEEK);
    	String strReturn = daysOfWeekFull[intDayOfWeek];
    	    	
    	return strReturn;
    }
    
    public Calendar getToday() {
    	return todayCalendar;
    }
    
  
 /*   
    public String[] getData() {
    	return gridData;
    }

NO LONGER USED - UI development and testing only */    
    
    public List<String> getDays() {
    	return gridDays;
    }
    
    public abstract void invalidate();
    public abstract void refresh();
}
