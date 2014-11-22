package com.example.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DaySectionFragment extends MainFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
   
    public DaySectionFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_day, container, false);

        // Set calendar month header
        TextView textView = (TextView) rootView.findViewById(R.id.day_calendar_header);
        textView.setText(getFormattedDate(FORMAT_CALENDAR_HEADER, getToday()));

        TextView textEventHeader = (TextView) rootView.findViewById(R.id.day_event_header);
        textEventHeader.setText("Events - " + getDayOfWeek(getToday()) + ", " +
        		getFormattedDate(FORMAT_EVENT_HEADER, getToday()));

        return rootView;
    }

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void invalidate() {
		// TODO Auto-generated method stub
		
	}
}

