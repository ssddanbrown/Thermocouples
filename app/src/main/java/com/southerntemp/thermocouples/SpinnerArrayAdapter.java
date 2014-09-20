package com.southerntemp.thermocouples;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerArrayAdapter extends ArrayAdapter<String> {
	
	private final Context context;
	private final String[] objects;
	private final String spinnerTitle;

	public SpinnerArrayAdapter(Context context, int resource,
			int textViewResourceId, String[] objects, String sTitle) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.objects = objects;
		this.spinnerTitle = sTitle;
	}
	
	@Override
	  public View getView(final int position, View convertView, final ViewGroup parent) {
		//Inflate layout to view
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.spinneritem, parent, false);
	    
	    TextView spinnerItem = (TextView)rowView.findViewById(R.id.spinneritemtv);
	    String spinnerString = objects[position];
	    if(spinnerString.equals("None")){spinnerItem.setText(spinnerTitle);spinnerItem.setTextColor(Color.WHITE);}
	    else if(spinnerString.equals("grey")){spinnerItem.setBackgroundColor(Color.rgb(139, 139, 139));spinnerItem.setText("");}
	    else if(spinnerString.equals("purple")){spinnerItem.setBackgroundColor(Color.rgb(143, 0, 255));spinnerItem.setText("");}
	    else if(spinnerString.equals("brown")){spinnerItem.setBackgroundColor(Color.rgb(122, 47, 27));spinnerItem.setText("");}
	    else if(spinnerString.equals("black")){spinnerItem.setBackgroundColor(Color.rgb(22, 19, 18));spinnerItem.setText("");}
	    else if(spinnerString.equals("blue")){spinnerItem.setBackgroundColor(Color.rgb(0, 51, 255));spinnerItem.setText("");}
	    else if(spinnerString.equals("yellow")){spinnerItem.setBackgroundColor(Color.rgb(220, 236, 30));spinnerItem.setText("");}
	    else if(spinnerString.equals("green")){spinnerItem.setBackgroundColor(Color.rgb(24, 175, 11));spinnerItem.setText("");}
	    else if(spinnerString.equals("red")){spinnerItem.setBackgroundColor(Color.rgb(224, 24, 24));spinnerItem.setText("");}
	    else if(spinnerString.equals("pink")){spinnerItem.setBackgroundColor(Color.rgb(255, 100, 255));spinnerItem.setText("");}
	    else if(spinnerString.equals("orange")){spinnerItem.setBackgroundColor(Color.rgb(255, 143, 0));spinnerItem.setText("");}
	    else if(spinnerString.equals("white")){spinnerItem.setBackgroundColor(Color.rgb(255, 255, 255));spinnerItem.setText("");}
	    return rowView;
	  }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {   // This view starts when we click the spinner.
	    LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.spinneritem, parent, false);
		    TextView spinnerItem = (TextView)rowView.findViewById(R.id.spinneritemtv);
		    String spinnerString = objects[position];
            int height = (int)context.getResources().getDimension(R.dimen.listItem);
            spinnerItem.setHeight(height);
		    if(spinnerString.equals("None")){spinnerItem.setText("N/A");}
		    else if(spinnerString.equals("grey")){spinnerItem.setBackgroundColor(Color.rgb(139, 139, 139));spinnerItem.setText("");}
		    else if(spinnerString.equals("purple")){spinnerItem.setBackgroundColor(Color.rgb(143, 0, 255));spinnerItem.setText("");}
		    else if(spinnerString.equals("brown")){spinnerItem.setBackgroundColor(Color.rgb(122, 47, 27));spinnerItem.setText("");}
		    else if(spinnerString.equals("black")){spinnerItem.setBackgroundColor(Color.rgb(22, 19, 18));spinnerItem.setText("");}
		    else if(spinnerString.equals("blue")){spinnerItem.setBackgroundColor(Color.rgb(0, 51, 255));spinnerItem.setText("");}
		    else if(spinnerString.equals("yellow")){spinnerItem.setBackgroundColor(Color.rgb(220, 236, 30));spinnerItem.setText("");}
		    else if(spinnerString.equals("green")){spinnerItem.setBackgroundColor(Color.rgb(24, 175, 11));spinnerItem.setText("");}
		    else if(spinnerString.equals("red")){spinnerItem.setBackgroundColor(Color.rgb(224, 24, 24));spinnerItem.setText("");}
		    else if(spinnerString.equals("pink")){spinnerItem.setBackgroundColor(Color.rgb(255, 100, 255));spinnerItem.setText("");}
		    else if(spinnerString.equals("orange")){spinnerItem.setBackgroundColor(Color.rgb(255, 143, 0));spinnerItem.setText("");}
		    else if(spinnerString.equals("white")){spinnerItem.setBackgroundColor(Color.rgb(255, 255, 255));spinnerItem.setText("");}
		    

		    
		    return rowView;
    }

}
