package com.southerntemp.thermocouples;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.math.BigDecimal;

public class CalcTempToMvFragment extends Fragment {

    double temp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calc_temp_to_mv, container, false);

        //Set Views
        final TextView tcresultb = v.findViewById(R.id.tcresultb);
        final TextView tcresulte = v.findViewById(R.id.tcresulte);
        final TextView tcresultk = v.findViewById(R.id.tcresultk);
        final TextView tcresultj = v.findViewById(R.id.tcresultj);
        final TextView tcresultn = v.findViewById(R.id.tcresultn);
        final TextView tcresultr = v.findViewById(R.id.tcresultr);
        final TextView tcresults = v.findViewById(R.id.tcresults);
        final TextView tcresultt = v.findViewById(R.id.tcresultt);
        final EditText tcinput = v.findViewById(R.id.tcinput);
        final Button tccalcbuttonk = v.findViewById(R.id.tccalcbuttonk);

        //Calculate button is clicked on keyboard enter
        tcinput.setOnEditorActionListener((actionView, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                tccalcbuttonk.performClick();
            }
            return false;
        });


        tccalcbuttonk.setOnClickListener(k -> {
            EditText tcinput1 = v.findViewById(R.id.tcinput);
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(tcinput1.getApplicationWindowToken(), 0);
            if (tcinput1.getText().toString().equals(".") || tcinput1.getText().toString().equals("-") || tcinput1.getText().toString().equals("-.") || tcinput1.getText().toString().equals("")) {
                temp = 0.0;
            } else {
                temp = Double.parseDouble(tcinput1.getText().toString());
            }
            double resultb = Bmvtoc(temp);
            if (resultb == 0.00 || temp == 0.0) {
                tcresultb.setText("Out of range");
            } else {
                BigDecimal result = new BigDecimal(resultb).setScale(5, BigDecimal.ROUND_HALF_UP);
                tcresultb.setText(result + "mV");
            }

            double resulte = Emvtoc(temp);
            if (resulte == 0.00 || temp == 0.0) {
                tcresulte.setText("Out of range");
            } else {
                BigDecimal result = new BigDecimal(resulte).setScale(5, BigDecimal.ROUND_HALF_UP);
                tcresulte.setText(result + "mV");
            }

            double resultj = Jmvtoc(temp);
            if (resultj == 0.00 || temp == 0.0) {
                tcresultj.setText("Out of range");
            } else {
                BigDecimal result = new BigDecimal(resultj).setScale(5, BigDecimal.ROUND_HALF_UP);
                tcresultj.setText(result + "mV");
            }

            double resultk = Kmvtoc(temp);
            if (resultk == 0.00 || temp == 0.0) {
                tcresultk.setText("Out of range");
            } else {
                BigDecimal result = new BigDecimal(resultk).setScale(5, BigDecimal.ROUND_HALF_UP);
                tcresultk.setText(result + "mV");
            }

            double resultn = Nmvtoc(temp);
            if (resultn == 0.00 || temp == 0.0) {
                tcresultn.setText("Out of range");
            } else {
                BigDecimal result = new BigDecimal(resultn).setScale(5, BigDecimal.ROUND_HALF_UP);
                tcresultn.setText(result + "mV");
            }

            double resultr = Rmvtoc(temp);
            if (resultr == 0.00 || temp == 0.0) {
                tcresultr.setText("Out of range");
            } else {
                BigDecimal result = new BigDecimal(resultr).setScale(5, BigDecimal.ROUND_HALF_UP);
                tcresultr.setText(result + "mV");
            }

            double results = Smvtoc(temp);
            if (results == 0.00 || temp == 0.0) {
                tcresults.setText("Out of range");
            } else {
                BigDecimal result = new BigDecimal(results).setScale(5, BigDecimal.ROUND_HALF_UP);
                tcresults.setText(result + "mV");
            }

            double resultt = Tmvtoc(temp);
            if (resultt == 0.00 || temp == 0.0) {
                tcresultt.setText("Out of range");
            } else {
                BigDecimal result = new BigDecimal(resultt).setScale(5, BigDecimal.ROUND_HALF_UP);
                tcresultt.setText(result + "mV");
            }
        });
        return v;

    }

    public double Bmvtoc(double temp) {
        double[] ak;
        ak = new double[8];
        if (temp >= 0 && temp <= 70) {
            ak[0] = 42;
            ak[1] = 3.3933898E-04;
            ak[2] = 0.000211966840978536;
            ak[3] = 0.00000338012504461122;
            ak[4] = -0.000000147932893448519;
            ak[5] = -0.00000000335714235152534;
            ak[6] = -0.0109204104235899;
            ak[7] = -0.000497829320147356;
        } else {
            return 0.00;
        }
        return thermocoupleEquation(ak);
    }

    public double Emvtoc(double temp) {
        double[] ak;
        ak = new double[8];
        if (temp >= -20 && temp <= 70) {
            ak[0] = 25;
            ak[1] = 1.49505819808044;
            ak[2] = 0.0609584430323512;
            ak[3] = -0.000273517892774302;
            ak[4] = -0.0000191301455052136;
            ak[5] = -0.0000000139488399503399;
            ak[6] = -0.00523823782546346;
            ak[7] = -0.000309701677615794;
        } else {
            return 0.00;
        }
        return thermocoupleEquation(ak);
    }

    public double Jmvtoc(double temp) {
        double[] ak;
        ak = new double[8];
        if (temp >= -20 && temp <= 70) {
            ak[0] = 25;
            ak[1] = 1.27734320749372;
            ak[2] = 0.051744084343395;
            ak[3] = -0.0000541386630448239;
            ak[4] = -0.00000228957691754618;
            ak[5] = -0.000000000779471431421526;
            ak[6] = -0.00151733422011515;
            ak[7] = -0.0000423145140497174;
        } else {
            return 0.00;
        }
        return thermocoupleEquation(ak);
    }

    public double Kmvtoc(double temp) {
        double[] ak;
        ak = new double[8];
        if (temp >= -20 && temp <= 70) {
            ak[0] = 25;
            ak[1] = 1.0003453470161;
            ak[2] = 0.0405148536182522;
            ak[3] = -0.0000387896383378116;
            ak[4] = -0.00000286084782705613;
            ak[5] = -0.000000000953670410293728;
            ak[6] = -0.00139486750341965;
            ak[7] = -0.0000679766265955419;
        } else {
            return 0.00;
        }
        return thermocoupleEquation(ak);
    }

    public double Nmvtoc(double temp) {
        double[] ak;
        ak = new double[8];
        if (temp >= -20 && temp <= 70) {
            ak[0] = 7;
            ak[1] = 0.182100239904779;
            ak[2] = 0.0262282563003498;
            ak[3] = -0.00015485538676966;
            ak[4] = 0.00000213660305580148;
            ak[5] = 0.000000000920471045789394;
            ak[6] = -0.00640709323474923;
            ak[7] = 0.0000821617805375071;
        } else {
            return 0.00;
        }
        return thermocoupleEquation(ak);
    }

    public double Rmvtoc(double temp) {
        double[] ak;
        ak = new double[8];
        if (temp >= -20 && temp <= 70) {
            ak[0] = 25;
            ak[1] = 0.140670158397482;
            ak[2] = 0.00593303558822883;
            ak[3] = 0.00002773690432834;
            ak[4] = -0.00000108196442544743;
            ak[5] = -0.00000000230983486448817;
            ak[6] = 0.00261468708400149;
            ak[7] = -0.000186214868332857;
        } else {
            return 0.00;
        }
        return thermocoupleEquation(ak);
    }

    public double Smvtoc(double temp) {
        double[] ak;
        ak = new double[8];
        if (temp >= -20 && temp <= 70) {
            ak[0] = 25;
            ak[1] = 0.142691632773367;
            ak[2] = 0.00598290574053624;
            ak[3] = 0.00000452922591841043;
            ak[4] = -0.00000133802812584086;
            ak[5] = -0.00000000237425769266271;
            ak[6] = -0.00106504462176573;
            ak[7] = -0.000220424198826495;
        } else {
            return 0.00;
        }
        return thermocoupleEquation(ak);
    }

    public double Tmvtoc(double temp) {
        double[] ak;
        ak = new double[8];
        if (temp >= -20 && temp <= 70) {
            ak[0] = 25;
            ak[1] = 0.991982794192893;
            ak[2] = 0.0407165636528211;
            ak[3] = 0.000711702968676807;
            ak[4] = 0.000000687826314743434;
            ak[5] = 0.0000000000432950612014075;
            ak[6] = 0.0164581016602501;
            ak[7] = 0;
        } else {
            return 0.00;
        }
        return thermocoupleEquation(ak);
    }

    private double thermocoupleEquation(double[] ak) {
        double tto = temp - ak[0];
        double e1 = tto * ak[5];
        e1 = e1 + ak[4];
        e1 = e1 * tto;
        e1 = e1 + ak[3];
        e1 = e1 * tto;
        e1 = e1 + ak[2];
        e1 = e1 * tto;
        double e2 = tto * ak[7];
        e2 = e2 + ak[6];
        e2 = e2 * tto;
        e2 = e2 + 1;
        double e3 = e1 / e2;
        e3 = e3 + ak[1];
        return e3;
    }

}
