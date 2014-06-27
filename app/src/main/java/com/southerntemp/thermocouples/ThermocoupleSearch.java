package com.southerntemp.thermocouples;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class ThermocoupleSearch extends FragmentActivity {
	public static List<TcSet> tclist;
	public static List<TcSet> filteredList;
	ArrayAdapter<TcSet> adapter;
	String filter1;
	String filter2;
	String filter3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_thermocouple_search);
		// ActionBar Setup
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

        //Load tclists
		tclist = loadTcSets();
		filteredList = tclist;
		
		//Listview setup
		ListView tcSearchList = (ListView)findViewById(R.id.tcsearchlist);
		adapter = new TcArrayAdapter<TcSet>(this, R.layout.tcsearchlistitem, R.id.tcstvtype, filteredList);
		tcSearchList.setAdapter(adapter);
		
		//Spinner setup
		filter1 = "None";
		filter2 = "None";
		filter3 = "None";
		String[] colorarray = {"None", "grey", "purple", "brown", "black", "blue", "yellow", "green", "red", "pink", "orange", "white"};
			//Jacket spinner setup
		ArrayAdapter<String> jspinneradpt = new SpinnerArrayAdapter(this, R.layout.spinneritem, R.id.spinneritemtv, colorarray, "Jacket");
		Spinner jspinner = (Spinner)findViewById(R.id.jcolor);
		jspinner.setAdapter(jspinneradpt);
		jspinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        String color = parentView.getItemAtPosition(position).toString();
		        filter1 = color;
		        filteredList = filterThermocoupleList();
		        clearAndPopulateFilteredList();
		    }
		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }

		});
		ArrayAdapter<String> pspinneradpt = new SpinnerArrayAdapter(this, R.layout.spinneritem, R.id.spinneritemtv, colorarray, "Lead");
		Spinner pspinner = (Spinner)findViewById(R.id.pcolor);
		pspinner.setAdapter(pspinneradpt);
		pspinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        filter2 = parentView.getItemAtPosition(position).toString();
		        filteredList = filterThermocoupleList();
		        clearAndPopulateFilteredList();
		    }
		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }

		});
		
		ArrayAdapter<String> nspinneradpt = new SpinnerArrayAdapter(this, R.layout.spinneritem, R.id.spinneritemtv, colorarray, "Lead");
		Spinner nspinner = (Spinner)findViewById(R.id.ncolor);
		nspinner.setAdapter(nspinneradpt);
		nspinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        filter3 = parentView.getItemAtPosition(position).toString();
		        filteredList = filterThermocoupleList();
		        clearAndPopulateFilteredList();
		    }
		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }

		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			ThermocoupleSearch.this.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	private void clearAndPopulateFilteredList(){
        adapter.clear();
        int filteredcount = filteredList.size();
        for(int i = 0; i<filteredcount; i++){
        	adapter.add(filteredList.get(i));
        }
        adapter.notifyDataSetChanged();
	}
	
	private List<TcSet> filterThermocoupleList(){
		filteredList.clear();
		List<TcSet> freshList= new ArrayList<TcSet>();
		if(tclist.isEmpty()){
			tclist = loadTcSets();
		}
		freshList = tclist;
		List<TcSet> toDelete = new ArrayList<TcSet>();
		for(int i=0, s = freshList.size(); i<(s); i++){
			TcSet a = freshList.get(i);
			int c = 0;
			if(a.getJcolor().equals(filter1) || filter1.equals("None")){}else{toDelete.add(a); c = 1;}
			String[] b = a.getLegColors();
			if(b[0].equals(filter2) || b[1].equals(filter2) || filter2.equals("None")){}else{
				if(c==0){toDelete.add(a);}
				}
			if(b[0].equals(filter3) || b[1].equals(filter3) || filter3.equals("None")){}else{if(c==0){toDelete.add(a);}}
		}
		freshList.removeAll(toDelete);
		return freshList;
	}
	
	private List<TcSet> loadTcSets(){	
		List<TcSet> tcSetList = new ArrayList<TcSet>();
		tcSetList.add(new TcSet(0, "ANSI", "grey", "white", "red"));
		tcSetList.add(new TcSet(0, "NFE", "grey", "yellow", "grey"));
		tcSetList.add(new TcSet(0, "DIN", "grey", "red", "grey"));
		tcSetList.add(new TcSet(0, "JIS", "grey", "red", "grey"));
		tcSetList.add(new TcSet(1, "IEC", "purple", "purple", "white"));
		tcSetList.add(new TcSet(1, "BS", "brown", "brown", "blue"));
		tcSetList.add(new TcSet(1, "ANSI", "purple", "purple", "red"));
		tcSetList.add(new TcSet(1, "NFE", "purple", "yellow", "purple"));
		tcSetList.add(new TcSet(1, "DIN", "black", "red", "black"));
		tcSetList.add(new TcSet(1, "JIS", "purple", "red", "white"));
		tcSetList.add(new TcSet(2, "IEC", "black", "black", "white"));
		tcSetList.add(new TcSet(2, "BS", "black", "yellow", "blue"));
		tcSetList.add(new TcSet(2, "ANSI", "black", "white", "red"));
		tcSetList.add(new TcSet(2, "NFE", "black", "yellow", "black"));
		tcSetList.add(new TcSet(2, "DIN", "blue", "red", "blue"));
		tcSetList.add(new TcSet(2, "JIS", "yellow", "red", "white"));
		tcSetList.add(new TcSet(3, "IEC", "green", "green", "white"));
		tcSetList.add(new TcSet(3, "BS", "red", "brown", "blue"));
		tcSetList.add(new TcSet(3, "ANSI", "yellow", "yellow", "red"));
		tcSetList.add(new TcSet(3, "NFE", "yellow", "yellow", "purple"));
		tcSetList.add(new TcSet(3, "DIN", "green", "red", "green"));
		tcSetList.add(new TcSet(3, "JIS", "blue", "red", "white"));
		tcSetList.add(new TcSet(4, "IEC", "pink", "pink", "white"));
		tcSetList.add(new TcSet(4, "BS", "orange", "orange", "blue"));
		tcSetList.add(new TcSet(4, "ANSI", "orange", "orange", "red"));
		tcSetList.add(new TcSet(5, "IEC", "orange", "orange", "white"));
		tcSetList.add(new TcSet(5, "BS", "green", "white", "blue"));
		tcSetList.add(new TcSet(5, "ANSI", "green", "black", "red"));
		tcSetList.add(new TcSet(5, "NFE", "green", "yellow", "green"));
		tcSetList.add(new TcSet(5, "DIN", "white", "red", "white"));
		tcSetList.add(new TcSet(5, "JIS", "black", "red", "white"));
		tcSetList.add(new TcSet(6, "IEC", "orange", "orange", "white"));
		tcSetList.add(new TcSet(6, "BS", "green", "white", "blue"));
		tcSetList.add(new TcSet(6, "ANSI", "green", "black", "red"));
		tcSetList.add(new TcSet(6, "NFE", "green", "yellow", "green"));
		tcSetList.add(new TcSet(6, "DIN", "white", "red", "white"));
		tcSetList.add(new TcSet(6, "JIS", "black", "red", "white"));
		tcSetList.add(new TcSet(7, "IEC", "brown", "brown", "white"));
		tcSetList.add(new TcSet(7, "BS", "blue", "white", "blue"));
		tcSetList.add(new TcSet(7, "ANSI", "blue", "blue", "red"));
		tcSetList.add(new TcSet(7, "NFE", "blue", "yellow", "blue"));
		tcSetList.add(new TcSet(7, "DIN", "brown", "red", "brown"));
		tcSetList.add(new TcSet(7, "JIS", "brown", "red", "white"));
		tcSetList.add(new TcSet(8, "IEC", "orange", "orange", "white"));
		tcSetList.add(new TcSet(8, "BS", "green", "white", "blue"));
		tcSetList.add(new TcSet(8, "ANSI", "green", "black", "red"));
		tcSetList.add(new TcSet(8, "NFE", "green", "yellow", "green"));
		tcSetList.add(new TcSet(8, "DIN", "white", "red", "white"));
		tcSetList.add(new TcSet(8, "JIS", "black", "red", "white"));
		tcSetList.add(new TcSet(9, "IEC", "green", "green", "white"));
		tcSetList.add(new TcSet(9, "BS", "red", "white", "blue"));
		tcSetList.add(new TcSet(9, "ANSI", "red", "brown", "red"));
		tcSetList.add(new TcSet(9, "NFE", "red", "yellow", "brown"));
		tcSetList.add(new TcSet(9, "DIN", "green", "red", "green"));
		tcSetList.add(new TcSet(9, "JIS", "blue", "red", "white"));
		return tcSetList;
	}

}
