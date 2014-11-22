package com.example.calendar;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SpinnerCategoryAdapter extends ArrayAdapter<String> {
	
	private int array_images[] = { R.drawable.white_icon, R.drawable.blue_icon, 
								R.drawable.gray_icon, R.drawable.green_icon, R.drawable.red_icon,
								R.drawable.yellow_icon };
	private int array_colors[] = { R.color.white, R.color.blue, R.color.gray, 
								R.color.green, R.color.red, R.color.yellow };
//	private static String array_strings[] = { "White", "Blue", "Gray", "Green", "Red", "Yellow" };
	private Context localContext;
	
	private ArrayList<Category> categoryList = new ArrayList<Category>();
	private ArrayList<String> categoryNames = new ArrayList<String>();

	public SpinnerCategoryAdapter(Context context, int textViewResourceId, ArrayList<Category> categoryList,
					ArrayList<String> categoryNames) {
		
		super(context, textViewResourceId, categoryNames);
		
		// TODO Auto-generated constructor stub
		this.categoryList = categoryList;
		this.categoryNames = categoryNames;
		
		this.localContext=context;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}
	
	public View getCustomView(int position, View convertView, ViewGroup parent) {
		LayoutInflater li = (LayoutInflater) localContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = li.inflate(R.layout.spinner_category_layout, parent, false);
		
		ImageView icon = (ImageView) row.findViewById(R.id.spinner_category_image);
		if (categoryList.size() > 1)
			if (position == 0)
				icon.setImageResource(R.drawable.white_icon);
			else
				icon.setImageResource(categoryList.get(position).getColor());
		else
			icon.setImageResource(R.drawable.white_icon);
		
		TextView text = (TextView) row.findViewById(R.id.spinner_category_text);
		text.setText(categoryNames.get(position));

		return row;
	}
	
	public int getSelectedColorId(int intIndex){
		return array_colors[intIndex];
	}
	
	public int getSpinnerPosition(int intColorId) {
		int intSpinnerPosition = 0;
		
		for (int i = 0; i < array_colors.length; i++) {
			if (intColorId == array_colors[i])
				intSpinnerPosition = i;
		}
		return intSpinnerPosition;
	}
	
	public void addItem(Category categoryItem) {
		categoryNames.add(categoryItem.getName());
		categoryList.add(categoryItem);
		notifyDataSetChanged();
	}
}
