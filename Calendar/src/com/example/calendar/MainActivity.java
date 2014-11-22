package com.example.calendar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements ActionBar.OnNavigationListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private static final int MONTH_SELECTED = 1;
    private static final int WEEK_SELECTED = 2;
    private static final int DAY_SELECTED = 3;
    

    // Create fragments for different views
    
    private MainFragment fragment = null;
    private MonthSectionFragment monthFragment = new MonthSectionFragment();
    private WeekSectionFragment weekFragment = new WeekSectionFragment();
    private DaySectionFragment dayFragment = new DaySectionFragment();

    private ArrayList<Event> eventList;
    private Event eventItem;
    private String eventFile;

    private CalendarPreferences calendarPreferences;
    
    private Category eventCategory;
    private String categoryFile;
    private ArrayList<Category> categoryList;
    private ArrayList<String> categoryNames;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        
        calendarPreferences = new CalendarPreferences(getApplicationContext());

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        getActionBarThemedContextCompat(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new String[] {
                                getString(R.string.title_section1),
                                getString(R.string.title_section2),
                                getString(R.string.title_section3),
                        }),
                this);
        
        eventList = new ArrayList<Event>();
        eventFile = getResources().getString(R.string.event_file);
        categoryList = new ArrayList<Category>();
        categoryFile = getResources().getString(R.string.category_file);
        
        ReadCategoryList();
        if (categoryList.size() == 0)
        	categoryList.add(new Category("None", R.color.white));

        ReadEventList();
    }

    /**
     * Backward-compatible version of {@link ActionBar#getThemedContext()} that
     * simply returns the {@link android.app.Activity} if
     * <code>getThemedContext</code> is unavailable.
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private Context getActionBarThemedContextCompat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return getActionBar().getThemedContext();
        } else {
            return this;
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getActionBar().getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.action_calendar_settings:
    		CalendarSettings("Calendar Settings", null);
    		break;
    	case R.id.action_add_event:
    		AddEvent("Event", null);
    		break;
    	}
    	
    	return true;
    }
    
    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
    	
    	boolean boolValidNavigation = true;
    	
    	switch (position + 1) {
    	case MONTH_SELECTED: 
    		fragment = monthFragment;            //new MonthSectionFragment();
    		break;
    	case WEEK_SELECTED:
    		fragment = weekFragment;                         // new WeekSectionFragment();
    		break;
    	case DAY_SELECTED:
    		fragment = dayFragment;                            //new DaySectionFragment();
    		break;
    		default:
    			boolValidNavigation = false;
    	}
    	if (boolValidNavigation) {
    		Bundle args = new Bundle();
    		fragment.setArguments(args);
    		getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    	}
        return boolValidNavigation;
    }
    
    public void CalendarSettings(String strTitle, String strMessage) {
    	LayoutInflater li = getLayoutInflater();
    	View viewSettings = li.inflate(R.layout.settings_layout, (ViewGroup) getCurrentFocus());

    	final SpinnerColorAdapter spinnerColorAdapter = new SpinnerColorAdapter(viewSettings.getContext(), R.layout.spinner_color_layout);    	
    	final Spinner spinnerHolidayColor = (Spinner) viewSettings.findViewById(R.id.setting_holiday_color);
    	final Spinner spinnerWeekendColor = (Spinner) viewSettings.findViewById(R.id.setting_weekend_color);
    	spinnerHolidayColor.setAdapter(spinnerColorAdapter);
    	spinnerWeekendColor.setAdapter(spinnerColorAdapter);

    	final CheckBox checkHolidayColor = (CheckBox) viewSettings.findViewById(R.id.checkbox_highlight_holiday);
    	final CheckBox checkWeekendColor = (CheckBox) viewSettings.findViewById(R.id.checkbox_highlight_weekend);
    	checkHolidayColor.setChecked(calendarPreferences.getShowHoliday_Flag());
    	checkWeekendColor.setChecked(calendarPreferences.getHighlightWeekend_Flag());

    	int intWeekendBackColorId = calendarPreferences.getHighlightWeekend_ColorId();
    	int intHolidayBackColorId = calendarPreferences.getShowHoliday_ColorId();
    	spinnerWeekendColor.setSelection(spinnerColorAdapter.getSpinnerPosition(intWeekendBackColorId));
    	spinnerHolidayColor.setSelection(spinnerColorAdapter.getSpinnerPosition(intHolidayBackColorId));
    	
    	new AlertDialog.Builder(fragment.getActivity())
    	.setTitle(strTitle)
    	.setView(viewSettings)
    	.setPositiveButton(R.string.apply, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				int intSelectedWeekendColorPosition = spinnerWeekendColor.getSelectedItemPosition();
				int intSelectedWeekendColorId = spinnerColorAdapter.getSelectedColorId(intSelectedWeekendColorPosition);
				calendarPreferences.setHighlightWeekend_ColorId(intSelectedWeekendColorId);
				calendarPreferences.setHighLightWeekend_Flag(checkWeekendColor.isChecked());
				fragment.invalidate();
			}
		})
		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}).show();
    }
    
    public void AddEvent(String strTitle, String strMessage) {
    	LayoutInflater li = getLayoutInflater();
    	final View viewSettings = li.inflate(R.layout.event_layout, (ViewGroup) getCurrentFocus());
    	
    	final EditText eventNameEdit = (EditText) viewSettings.findViewById(R.id.event_name);
    	
    	final DatePicker eventDateStartPicker = (DatePicker) viewSettings.findViewById(R.id.event_start_date);
    	final TimePicker eventTimeStartPicker = (TimePicker) viewSettings.findViewById(R.id.event_start_time);

    	final DatePicker eventDateEndPicker = (DatePicker) viewSettings.findViewById(R.id.event_end_date);
		final TimePicker eventTimeEndPicker = (TimePicker) viewSettings.findViewById(R.id.event_end_time);

		final EditText eventDescriptionEdit = (EditText) viewSettings.findViewById(R.id.event_description);

		final EditText eventLocationEdit = (EditText) viewSettings.findViewById(R.id.event_location);

		categoryNames = new ArrayList<String>();
		for (int i = 0; i < categoryList.size(); i++)
			categoryNames.add(categoryList.get(i).getName());
		final SpinnerCategoryAdapter categoryAdapter = new SpinnerCategoryAdapter(viewSettings.getContext(),
					R.layout.spinner_category_layout, categoryList, categoryNames);
		final Spinner eventCategorySpinner = (Spinner) viewSettings.findViewById(R.id.event_category);
		eventCategorySpinner.setAdapter(categoryAdapter);

		Button newCategoryButton = (Button) viewSettings.findViewById(R.id.new_category);
		newCategoryButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
				int listBefore = categoryList.size();
				AddCategory("Categories", null);
				int listAfter = categoryList.size();
				if (listBefore < listAfter) {
					categoryNames.clear();
					categoryAdapter.clear();
					for (int j = 0; j < categoryList.size(); j++)
						categoryNames.add(categoryList.get(j).getName());
					categoryAdapter.clear();
					categoryAdapter.addAll(categoryNames);
					categoryAdapter.notifyDataSetChanged();
					
				}
			}
		});
		new AlertDialog.Builder(fragment.getActivity())
    	.setTitle(strTitle)
    	.setView(viewSettings)
    	.setPositiveButton(R.string.apply, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String eventName = eventNameEdit.getText().toString();

				int intDay = eventDateStartPicker.getDayOfMonth();
				int intMonth = eventDateStartPicker.getMonth();
				int intYear = eventDateStartPicker.getYear();
				int intHour = eventTimeStartPicker.getCurrentHour();
				int intMinute = eventTimeStartPicker.getCurrentMinute();
				Calendar eventStart = (Calendar) Calendar.getInstance().clone();
				eventStart.set(intYear, intMonth, intDay, intHour, intMinute);

				intDay = eventDateEndPicker.getDayOfMonth();
				intMonth = eventDateEndPicker.getMonth();
				intYear = eventDateEndPicker.getYear();
				intHour = eventTimeEndPicker.getCurrentHour();
				intMinute = eventTimeEndPicker.getCurrentMinute();
				Calendar eventEnd = (Calendar) Calendar.getInstance().clone();
				eventEnd.set(intYear, intMonth, intDay, intHour, intMinute);
				
				String eventDescription = eventDescriptionEdit.getText().toString();
				
				String eventLocation = eventLocationEdit.getText().toString();
				
				int categoryInt = eventCategorySpinner.getSelectedItemPosition();
				
				eventItem = new Event();
				eventItem.setStartTime(eventStart);
				eventItem.setEndTime(eventEnd);
				eventItem.setName(eventName);
				eventItem.setDescription(eventDescription);
				eventItem.setLocation(eventLocation);
				eventItem.setCategory(categoryInt);
				eventList.add(eventItem);

				WriteEventList();
			}
		})
		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		}).show();
    	
    }
    
    private void AddCategory(String strTitle, String strMessage) {
    	LayoutInflater li = getLayoutInflater();
    	final View viewSettings = li.inflate(R.layout.category_layout, (ViewGroup) getCurrentFocus());
    	
    	final EditText categoryNameEdit = (EditText) viewSettings.findViewById(R.id.category_name_edit);
    	final Spinner categoryColor = (Spinner) viewSettings.findViewById(R.id.category_color_spinner);
    	final SpinnerColorAdapter spinnerAdapter = new SpinnerColorAdapter(viewSettings.getContext(), R.layout.spinner_color_layout);
    	categoryColor.setAdapter(spinnerAdapter);
    	
    	new AlertDialog.Builder(fragment.getActivity())
    	.setTitle(strTitle)
    	.setView(viewSettings)
    	.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String categoryName = categoryNameEdit.getText().toString();

				int intSelectedCategoryColorPosition = categoryColor.getSelectedItemPosition();
				int intSelectedCategoryColorId = spinnerAdapter.getSelectedImageId(intSelectedCategoryColorPosition);
				
				Category categoryItem = new Category(categoryName, intSelectedCategoryColorId);
				categoryList.add(categoryItem);

				WriteCategoryList();
			}
		})
		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		}).show();
    }

    // Read event list from data file
    private void ReadEventList() {
    	ObjectInput in = null;
    	Event eventTemp = null;
    	Event eventItem = null;
    	
    	try {
    		in = new ObjectInputStream(new FileInputStream(new File(new File(getFilesDir(), "")
    									+ File.separator + eventFile)));
    		while ((eventTemp = (Event) in.readObject()) != null) {
    			eventItem = new Event();
    			eventItem = eventTemp;
    			eventList.add(eventItem);
    		}
    		in.close();
    	}
    	catch (StreamCorruptedException e) {
    		e.printStackTrace();
    	}
    	catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    	catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    }
    
    private void WriteEventList() {
    	ObjectOutput out = null;
    	Event nullEvent = null;
    	
    	if (eventList.size() > 0) {
    		try {
    			out = new ObjectOutputStream(new FileOutputStream(new File(getFilesDir(), "")
    									+ File.separator + eventFile, false));
 
    			for (int i = 0; i < eventList.size(); i++)
    				out.writeObject(eventList.get(i));
    			out.writeObject(nullEvent);
    			out.close();
    		}
    		catch (FileNotFoundException e){
    			e.printStackTrace();
    		}
    		catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    private void ReadCategoryList() {
    	ObjectInput in = null;
    	Category categoryItem = null;
    	Category categoryTemp = null;
    	
    	try {
    		in = new ObjectInputStream(new FileInputStream(new File(new File(getFilesDir(), "")
    							+ File.separator + categoryFile)));
    		while ((categoryTemp = (Category) in.readObject()) != null) {
    			categoryItem = new Category(categoryTemp.getName(), categoryTemp.getColor());
    			categoryList.add(categoryItem);
    		}
    		in.close();
    	}
    	catch (StreamCorruptedException e) {
    		e.printStackTrace();
    	}
    	catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    	catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    }
    
    private void WriteCategoryList() {
    	ObjectOutput out = null;
    	Category nullCategory = null;
    	
    	if (categoryList.size() > 0) {
    		try {
    			out = new ObjectOutputStream(new FileOutputStream(new File(getFilesDir(), "")
    									+ File.separator + categoryFile, false));
 
    			for (int i = 0; i < categoryList.size(); i++)
    				out.writeObject(categoryList.get(i));
    			out.writeObject(nullCategory);
    			out.close();
    		}
    		catch (FileNotFoundException e){
    			e.printStackTrace();
    		}
    		catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    	
    }
    
    // Get event list for fragments and adapters
    public ArrayList<Event> getEventList() {
    	return eventList;
    }
}
