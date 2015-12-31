package com.southerntemp.thermocouples;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class SpinnerArrayAdapter extends ArrayAdapter<String> {
	
	private final Context context;
	private final String[] objects;
	private final String spinnerTitle;
	private Map<String, Integer> colorMap;

	public SpinnerArrayAdapter(Context context, int resource,
			int textViewResourceId, String[] objects, String sTitle) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.objects = objects;
		this.spinnerTitle = sTitle;
		setupColorMap();
	}
	
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		//Inflate layout to view
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.spinneritem, parent, false);

		TextView spinnerItem = (TextView)rowView.findViewById(R.id.spinneritemtv);
		String spinnerString = objects[position];
		if(spinnerString.equals("None")){
			spinnerItem.setText(this.spinnerTitle);
			spinnerItem.setTextColor(Color.WHITE);
		} else {
			spinnerItem.setText("");
			spinnerItem.setBackgroundColor(colorMap.get(spinnerString));
		}
		return rowView;
	}

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
       // This view starts when we click the spinner.
	    LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.spinneritem, parent, false);
		    TextView spinnerItem = (TextView)rowView.findViewById(R.id.spinneritemtv);
		    String spinnerString = objects[position];
            int height = (int)context.getResources().getDimension(R.dimen.listItem);
            spinnerItem.setHeight(height);

		    if(spinnerString.equals("None")){
				spinnerItem.setText("N/A");
			} else {
				spinnerItem.setText("");
				spinnerItem.setBackgroundColor(colorMap.get(spinnerString));
			}

		    return rowView;
    }

	/**
	 * Create a map of our colors
	 */
	public void setupColorMap() {
		colorMap = new HashMap<String, Integer>();
		colorMap.put("None", Color.rgb(0, 0, 0));
		colorMap.put("grey", Color.rgb(142, 144, 144));
		colorMap.put("purple", Color.rgb(143, 0, 145));
		colorMap.put("brown", Color.rgb(161, 92, 0));
		colorMap.put("black", Color.rgb(26, 26, 26));
		colorMap.put("blue", Color.rgb(0, 84, 255));
		colorMap.put("yellow", Color.rgb(255, 233, 0));
		colorMap.put("green", Color.rgb(0, 181, 0));
		colorMap.put("red", Color.rgb(239, 43, 0));
		colorMap.put("pink", Color.rgb(255, 135, 195));
		colorMap.put("orange", Color.rgb(255, 135, 0));
		colorMap.put("white", Color.rgb(255, 255, 255));
	}
}
