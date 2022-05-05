package com.southerntemp.thermocouples;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.math.BigDecimal;

public class CalcTempToTempFragment extends Fragment {

    Double temp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calc_temp_to_temp, container, false);

        // Load everything from layout
        final EditText calcinput = v.findViewById(R.id.calcinput);

        final Button buttonc = v.findViewById(R.id.buttonc);
        final Button buttonf = v.findViewById(R.id.buttonf);
        final Button buttonk = v.findViewById(R.id.buttonk);

        final TextView tempc = v.findViewById(R.id.tempc);
        final TextView tempf = v.findViewById(R.id.tempf);
        final TextView tempk = v.findViewById(R.id.tempk);

        //Centigrade button setup
        buttonc.setOnClickListener(clickView -> {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(calcinput.getApplicationWindowToken(), 0);
            if (calcinput.getText().toString().equals(".") || calcinput.getText().toString().equals("-") || calcinput.getText().toString().equals("-.") || calcinput.getText().toString().equals("")) {
                temp = 0.0;
            } else {
                temp = Double.parseDouble(calcinput.getText().toString());
            }

            BigDecimal cc = new BigDecimal(temp).setScale(3, BigDecimal.ROUND_HALF_UP);
            tempc.setText(cc + "°C");

            double f = temp * 1.8;
            double f2 = f + 32.0;
            BigDecimal cf = new BigDecimal(f2).setScale(3, BigDecimal.ROUND_HALF_UP);
            tempf.setText(cf + "°F");

            double k = temp + 273.15;
            BigDecimal ck = new BigDecimal(k).setScale(3, BigDecimal.ROUND_HALF_UP);
            tempk.setText(ck + "K");
        });

        // Fahrenheit button setup
        buttonf.setOnClickListener(clickView -> {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(calcinput.getApplicationWindowToken(), 0);
            if (calcinput.getText().toString().equals(".") || calcinput.getText().toString().equals("-") || calcinput.getText().toString().equals("-.") || calcinput.getText().toString().equals("")) {
                temp = 0.0;
            } else {
                temp = Double.parseDouble(calcinput.getText().toString());
            }
            double c = temp - 32.0;
            double eq = 5.0 / 9;
            double c2 = c * eq;
            BigDecimal fc = new BigDecimal(c2).setScale(3, BigDecimal.ROUND_HALF_UP);
            tempc.setText(fc + "°C");

            BigDecimal ff = new BigDecimal(temp).setScale(3, BigDecimal.ROUND_HALF_UP);
            tempf.setText(ff + "°F");


            double k = temp - 32.0;
            double k2 = k * eq;
            double k3 = k2 + 273.15;
            BigDecimal fk = new BigDecimal(k3).setScale(3, BigDecimal.ROUND_HALF_UP);
            tempk.setText(fk + "K");
        });
        //Kelvin button setup
        buttonk.setOnClickListener(clickView -> {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(calcinput.getApplicationWindowToken(), 0);
            if (calcinput.getText().toString().equals(".") || calcinput.getText().toString().equals("-") || calcinput.getText().toString().equals("-.") || calcinput.getText().toString().equals("")) {
                temp = 0.0;
            } else {
                temp = Double.parseDouble(calcinput.getText().toString());
            }
            double c = temp - 273.15;
            BigDecimal kc = new BigDecimal(c).setScale(3, BigDecimal.ROUND_HALF_UP);
            tempc.setText(kc + "°C");

            double f = temp - 273.15;
            double f2 = f * 1.8;
            double f3 = f2 + 32.0;
            BigDecimal kf = new BigDecimal(f3).setScale(3, BigDecimal.ROUND_HALF_UP);
            tempf.setText(kf + "°F");

            BigDecimal kk = new BigDecimal(temp).setScale(3, BigDecimal.ROUND_HALF_UP);
            tempk.setText(kk + "K");
        });
        return v;
    }
}
