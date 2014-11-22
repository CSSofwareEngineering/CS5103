package com.example.calendar;

import java.util.Calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MonthSectionFragment extends MainFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private GridView gridView;
    private Calendar calendarMonth = (Calendar) Calendar.getInstance();
    private Calendar selectedMonth = (Calendar) calendarMonth.clone();
    private MonthCalendarAdapter calendarAdapter;
    private View root;
    
    public MonthSectionFragment() {

    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_month, container, false);
        this.root = rootView;
        
        // Set calendar grid view
        gridView = (GridView) rootView.findViewById(R.id.month_calendar_gridview);
        calendarAdapter = new MonthCalendarAdapter(gridView.getContext(), Calendar.getInstance(), this);
        gridView.setAdapter(calendarAdapter);

        // Set calendar month header
/*        TextView decreaseMonth = (TextView) root.findViewById(R.id.decrease_month);
        decreaseMonth.setText("<");
        decreaseMonth.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectedMonth.add(Calendar.MONTH, -1);
				invalidate();
				//				calendarAdapter.notifyDataSetChanged();
			}
        });
*/
        
        TextView textView = (TextView) rootView.findViewById(R.id.month_calendar_header);
        textView.setText(getFormattedDate(FORMAT_CALENDAR_HEADER, getToday()));
/*        
        TextView increaseMonth = (TextView) root.findViewById(R.id.increase_month);
        increaseMonth.setText(">");
        increaseMonth.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectedMonth.add(Calendar.MONTH, 1);
				invalidate();
//				calendarAdapter.notifyDataSetChanged();
			}
        });
*/        

        // Set calendar days of week header grid view
        gridView = (GridView) rootView.findViewById(R.id.month_days_gridview);
        ArrayAdapter<String> daysAdapter = new ArrayAdapter<String>(rootView.getContext(), 
        		R.layout.day_header, getDays());
        gridView.setAdapter(daysAdapter);


        TextView textEventHeader = (TextView) rootView.findViewById(R.id.month_event_header);
        textEventHeader.setText("Events - " + getDayOfWeek(calendarMonth) + ", " +
        		getFormattedDate(FORMAT_EVENT_HEADER, calendarMonth));
        return rootView;
    }
    
    @Override
    public void refresh() {
    	selectedMonth = calendarAdapter.getSelectedDate();
    	
    	TextView textEventHeader = (TextView) root.findViewById(R.id.month_event_header);
    	textEventHeader.setText("Events - " + getDayOfWeek(selectedMonth) + ", " +
    			getFormattedDate(FORMAT_EVENT_HEADER, selectedMonth));
 
    	TextView textView = (TextView) root.findViewById(R.id.month_calendar_header);
        textView.setText(getFormattedDate(FORMAT_CALENDAR_HEADER, selectedMonth));
    }
    
    @Override
    public void invalidate() {
    	calendarAdapter.notifyDataSetInvalidated();
    }
}

