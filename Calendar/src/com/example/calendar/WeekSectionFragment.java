package com.example.calendar;

import java.util.Calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class WeekSectionFragment extends MainFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    private GridView gridView;
    private Calendar calendarMonth = (Calendar) Calendar.getInstance();
    private Calendar selectedMonth = (Calendar) calendarMonth.clone();
    private WeekCalendarAdapter calendarAdapter;
    
    private View root;
    
    public WeekSectionFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_week, container, false);
        this.root = rootView;
        
        // Set calendar month header
        TextView textView = (TextView) rootView.findViewById(R.id.week_calendar_header);
        textView.setText(getFormattedDate(FORMAT_CALENDAR_HEADER, getToday()));
        
        // Set calendar days of week header grid view
        gridView = (GridView) rootView.findViewById(R.id.week_days_gridview);
        ArrayAdapter<String> daysAdapter = new ArrayAdapter<String>(rootView.getContext(), 
        				R.layout.day_header, getDays());
        gridView.setAdapter(daysAdapter);

        // Set calendar grid view
        gridView = (GridView) rootView.findViewById(R.id.week_calendar_gridview);
        calendarAdapter = new WeekCalendarAdapter(gridView.getContext(), getToday(), this);
        gridView.setAdapter(calendarAdapter);

        TextView textEventHeader = (TextView) rootView.findViewById(R.id.week_event_header);
        textEventHeader.setText("Events - " + getDayOfWeek(calendarMonth) + ", " +
        		getFormattedDate(FORMAT_EVENT_HEADER, calendarMonth));

        return rootView;
    }

    @Override
    public void refresh() {
    	selectedMonth = calendarAdapter.getSelectedDate();
    	
    	TextView textEventHeader = (TextView) root.findViewById(R.id.week_event_header);
    	textEventHeader.setText("Events - " + getDayOfWeek(selectedMonth) + ", " +
    			getFormattedDate(FORMAT_EVENT_HEADER, selectedMonth));
 
    	TextView textView = (TextView) root.findViewById(R.id.week_calendar_header);
        textView.setText(getFormattedDate(FORMAT_CALENDAR_HEADER, selectedMonth));
    }
    
    @Override
    public void invalidate() {
    	calendarAdapter.notifyDataSetInvalidated();
    }
}

