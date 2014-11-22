package com.example.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WeekCalendarAdapter extends BaseAdapter {
	
	private Calendar currentMonth, previousMonth, nextMonth, countCalendar, selectedDay;
	private ArrayList<String> dayStringList;
	int currentMonthFirstDayOfWeek, intCurrentMonth;
	WeekSectionFragment weekFragment;

	Context viewContext;
	CalendarPreferences calendarPreferences;
	
	public WeekCalendarAdapter(Context viewContext, Calendar currentMonth,WeekSectionFragment fragment) {
		this.currentMonth = currentMonth;
		this.viewContext = viewContext;
		this.dayStringList = new ArrayList<String>();
		this.selectedDay = (Calendar)currentMonth.clone();
		this.weekFragment = fragment;
		
		calendarPreferences = new CalendarPreferences(viewContext);
		
		selectedDay = (Calendar) currentMonth.clone();
		
		previousMonth = (Calendar)currentMonth.clone();
		previousMonth.add(Calendar.MONTH, -1);
		
		nextMonth = (Calendar)currentMonth.clone();
		nextMonth.add(Calendar.MONTH, 1);

		countCalendar = (Calendar)currentMonth.clone();
		currentMonthFirstDayOfWeek = countCalendar.get(Calendar.DAY_OF_WEEK);
		countCalendar.add(Calendar.DAY_OF_MONTH, (1 - currentMonthFirstDayOfWeek));
		
		refreshCalendar();
	}
	
	@Override
	public Object getItem(int position) {
		return dayStringList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		TextView cellView;
		String strSelectedDate = getDateString(selectedDay);
		int intSelectedDay = Integer.parseInt(strSelectedDate.substring(0,2));
		
		
		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) viewContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.calendar_cell, null);
		}
		parent.setBackgroundColor(viewContext.getResources().getColor(R.color.black));
		v.setPadding(5, 5, 5, 5);
		cellView = (TextView) v.findViewById(R.id.date);
		cellView.setPadding(5, 5, 5, 5);
		String strMonth = dayStringList.get(position).substring(2,4);
		String strDay = dayStringList.get(position).substring(0,2);
		int intDayOfWeek = Integer.parseInt(dayStringList.get(position).substring(8));
		int intWeekendBackColorId = calendarPreferences.getHighlightWeekend_ColorId();
		if (calendarPreferences.getHighlightWeekend_Flag()) {
			if ((intDayOfWeek == 1) | (intDayOfWeek == 7))
				v.setBackgroundColor(viewContext.getResources().getColor(intWeekendBackColorId));
			else
				v.setBackgroundColor(viewContext.getResources().getColor(R.color.LightGrey));
		}
		else
			v.setBackgroundColor(viewContext.getResources().getColor(R.color.LightGrey));
		final int intListDay = Integer.parseInt(strDay);
		final int intListMonth = Integer.parseInt(strMonth);
		int intCurrentMonth = currentMonth.get(Calendar.MONTH);
		if (intListMonth == intCurrentMonth)
			cellView.setTextColor(viewContext.getResources().getColor(R.color.black));
		else
			cellView.setTextColor(viewContext.getResources().getColor(R.color.gray));     
		cellView.setText(Integer.toString(intListDay));

		cellView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				selectedDay.set(selectedDay.get(Calendar.YEAR), intListMonth, intListDay);
				notifyDataSetInvalidated();
				weekFragment.refresh();
			}
		});
		
		if (intSelectedDay == intListDay)
			v.setBackgroundColor(v.getResources().getColor(R.color.white));
		
		ImageView cellImage = (ImageView) v.findViewById(R.id.date_icon);
		if ((position % 2) == 0)
			cellImage.setVisibility(View.VISIBLE);
		else
			cellImage.setVisibility(View.INVISIBLE);
		
		return v;
	}

	@Override
	public int getCount() {
		return dayStringList.size();
	}
	
	private void refreshCalendar() {
		dayStringList.clear();
		
		for (int j = 1; j <= 7; j++) {
				dayStringList.add(getDateString(countCalendar));
				countCalendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	}
	
	private String getDateString(Calendar currentDate) {
		String strDate = null;
		String strDay = null;
		String strMonth = null;
		String strYear = null;
		String strDayOfWeek = null;
		int intDay, intMonth, intYear, intDayOfWeek;
		
		intDay = currentDate.get(Calendar.DAY_OF_MONTH);
		intMonth = currentDate.get(Calendar.MONTH);
		intYear = currentDate.get(Calendar.YEAR);
		intDayOfWeek = currentDate.get(Calendar.DAY_OF_WEEK);
		if (intDay < 10)
			strDay = "0" + Integer.toString(intDay);
		else
			strDay = Integer.toString(intDay);
		if (intMonth < 10)
			strMonth = "0" + Integer.toString(intMonth);
		else
			strMonth = Integer.toString(intMonth);
		strYear = Integer.toString(intYear);
		strDayOfWeek = Integer.toString(intDayOfWeek);
		strDate = strDay + strMonth + strYear + strDayOfWeek;
		return strDate;
	}
	
	public Calendar getSelectedDate() {
		return selectedDay;
	}
	
	public void NotifyDataSetChanged() {
		refreshCalendar();
	}
}
