package com.example.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SpinnerColorAdapter extends ArrayAdapter<String> {
	
	private int array_images[] = { R.drawable.white_icon, R.drawable.blue_icon, 
								R.drawable.gray_icon, R.drawable.green_icon, R.drawable.red_icon,
								R.drawable.yellow_icon };
	private int array_colors[] = { R.color.white, R.color.blue, R.color.gray, 
								R.color.green, R.color.red, R.color.yellow };
	private static String array_strings[] = { "White", "Blue", "Gray", "Green", "Red", "Yellow" };
	private Context localContext;

	public SpinnerColorAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId, array_strings);
		this.localContext=context;
			
		// TODO Auto-generated constructor stub
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
		View row = li.inflate(R.layout.spinner_color_layout, parent, false);
		
		ImageView icon = (ImageView) row.findViewById(R.id.spinner_color_image);
		icon.setImageResource(array_images[position]);
		
		TextView text = (TextView) row.findViewById(R.id.spinner_color_text);
		text.setText(array_strings[position]);

		return row;
	}
	
	public int getSelectedImageId(int intIndex)
	{
		return array_images[intIndex];
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
}
