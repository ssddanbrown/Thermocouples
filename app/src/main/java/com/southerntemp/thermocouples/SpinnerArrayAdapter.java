package com.southerntemp.thermocouples;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
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
    public View getView(final int position, View convertView, @NonNull final ViewGroup parent) {
        //Inflate layout to view
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.spinneritem, parent, false);

        TextView spinnerItem = rowView.findViewById(R.id.spinneritemtv);
        String spinnerString = objects[position];

        if (spinnerString.equals("None")) {
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
        TextView spinnerItem = rowView.findViewById(R.id.spinneritemtv);
        String spinnerString = objects[position];
        int height = (int) context.getResources().getDimension(R.dimen.listItem);
        spinnerItem.setHeight(height);

        if (spinnerString.equals("None")) {
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
    private void setupColorMap() {
        colorMap = new HashMap<>();
        colorMap.put("None", Color.rgb(0, 0, 0));
        colorMap.put("grey", Color.rgb(158, 158, 158));
        colorMap.put("purple", Color.rgb(94, 53, 177));
        colorMap.put("brown", Color.rgb(93, 64, 55));
        colorMap.put("black", Color.rgb(26, 26, 26));
        colorMap.put("blue", Color.rgb(30, 136, 229));
        colorMap.put("yellow", Color.rgb(255, 235, 59));
        colorMap.put("green", Color.rgb(67, 160, 71));
        colorMap.put("red", Color.rgb(229, 57, 53));
        colorMap.put("pink", Color.rgb(224, 64, 251));
        colorMap.put("orange", Color.rgb(251, 140, 0));
        colorMap.put("white", Color.rgb(255, 255, 255));
    }
}
