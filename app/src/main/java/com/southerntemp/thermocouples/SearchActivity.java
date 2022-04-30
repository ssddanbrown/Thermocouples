package com.southerntemp.thermocouples;

import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    public static TcColor[] colorList;
    public List<TcColor> filteredList;
    ArrayAdapter<TcColor> adapter;
    String jacketFilter;
    String leadAFilter;
    String leadBFilter;
    TcRepo tcRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thermocouple_search);

        // ActionBar Setup
        Toolbar toolbar = findViewById(R.id.tcholder_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setElevation(0f);

        tcRepo = TcRepo.getInstance(getApplicationContext());

        // Load Thermocouples
        colorList = tcRepo.getColorArray();
        filteredList = new ArrayList<>(Arrays.asList(colorList));

        // Listview setup
        ListView tcSearchList = findViewById(R.id.tcsearchlist);
        adapter = new TcArrayAdapter(this, R.layout.tcsearchlistitem, R.id.tcstvtype, filteredList);
        tcSearchList.setAdapter(adapter);

        // Spinner setup
        jacketFilter = "None";
        leadAFilter = "None";
        leadBFilter = "None";
        String[] colorArray = {"None", "grey", "purple", "brown", "black", "blue", "yellow", "green", "red", "pink", "orange", "white"};

        // Jacket spinner setup
        ArrayAdapter<String> jspinneradpt = new SpinnerArrayAdapter(this, R.layout.spinneritem, R.id.spinneritemtv, colorArray, "Jacket");
        Spinner jspinner = findViewById(R.id.jcolor);
        jspinner.setAdapter(jspinneradpt);
        jspinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                jacketFilter = parentView.getItemAtPosition(position).toString();
                filteredList = filterThermocoupleList();
                clearAndPopulateFilteredList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        ArrayAdapter<String> pSpinnerAdpt = new SpinnerArrayAdapter(this, R.layout.spinneritem, R.id.spinneritemtv, colorArray, "Lead");
        Spinner pSpinner = findViewById(R.id.pcolor);
        pSpinner.setAdapter(pSpinnerAdpt);
        pSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                leadAFilter = parentView.getItemAtPosition(position).toString();
                filteredList = filterThermocoupleList();
                clearAndPopulateFilteredList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        ArrayAdapter<String> nSpinnerAdpt = new SpinnerArrayAdapter(this, R.layout.spinneritem, R.id.spinneritemtv, colorArray, "Lead");
        Spinner nSpinner = findViewById(R.id.ncolor);
        nSpinner.setAdapter(nSpinnerAdpt);
        nSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                leadBFilter = parentView.getItemAtPosition(position).toString();
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
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            overridePendingTransition(0, R.anim.push_left_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.push_left_out);
    }

    private void clearAndPopulateFilteredList() {
        adapter.clear();
        for (TcColor color : filteredList) {
            adapter.add(color);
        }
        adapter.notifyDataSetChanged();
    }

    private List<TcColor> filterThermocoupleList() {
        List<TcColor> filteredList = new ArrayList<>(Arrays.asList(colorList));
        List<TcColor> toDelete = new ArrayList<>();

        for (int i = 0, s = filteredList.size(); i < s; i++) {
            TcColor a = filteredList.get(i);

            // Check Jacket color
            if (!jacketFilter.equals("None")  && !a.colorJacket.equals(jacketFilter)) {
                toDelete.add(a);
                continue;
            }

            // Filter leads
            String[] leadFilters = {leadAFilter, leadBFilter};
            for (String leadFilter : leadFilters) {
                if (!leadFilter.equals("None") && !a.colorPos.equals(leadFilter) && !a.colorNeg.equals(leadFilter)) {
                    toDelete.add(a);
                }
            }
        }

        filteredList.removeAll(toDelete);
        return filteredList;
    }

}
