package com.southerntemp.thermocouples;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.southerntemp.thermocouples.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {
    public static TcColor[] colorList;
    public List<TcColor> filteredList;
    ArrayAdapter<TcColor> adapter;
    String jacketFilter;
    String leadAFilter;
    String leadBFilter;
    TcRepo tcRepo;

    private FragmentSearchBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context ctx = getContext();

        tcRepo = TcRepo.getInstance(ctx);

        // Load Thermocouples
        colorList = tcRepo.getColorArray();
        filteredList = new ArrayList<>(Arrays.asList(colorList));

        // Listview setup
        ListView tcSearchList = binding.tcsearchlist;
        adapter = new TcArrayAdapter(ctx, R.layout.spinner_item_search_result, R.id.tcstvtype, filteredList);
        tcSearchList.setAdapter(adapter);

        // Spinner setup
        jacketFilter = "None";
        leadAFilter = "None";
        leadBFilter = "None";
        String[] colorArray = {"None", "grey", "purple", "brown", "black", "blue", "yellow", "green", "red", "pink", "orange", "white"};

        // Jacket spinner setup
        ArrayAdapter<String> jspinneradpt = new SpinnerArrayAdapter(ctx, R.layout.spinner_item_search_selection, R.id.spinneritemtv, colorArray, "Jacket");
        Spinner jspinner = binding.jcolor;
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
        ArrayAdapter<String> pSpinnerAdpt = new SpinnerArrayAdapter(ctx, R.layout.spinner_item_search_selection, R.id.spinneritemtv, colorArray, "Lead");
        Spinner pSpinner = binding.pcolor;
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

        ArrayAdapter<String> nSpinnerAdpt = new SpinnerArrayAdapter(ctx, R.layout.spinner_item_search_selection, R.id.spinneritemtv, colorArray, "Lead");
        Spinner nSpinner = binding.ncolor;
        nSpinner.setAdapter(nSpinnerAdpt);
        nSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                leadBFilter = parentView.getItemAtPosition(position).toString();
                filteredList = filterThermocoupleList();
                clearAndPopulateFilteredList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
