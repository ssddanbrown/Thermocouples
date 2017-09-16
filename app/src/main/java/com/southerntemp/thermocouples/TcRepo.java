package com.southerntemp.thermocouples;


import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TcRepo {
    private static TcRepo ourInstance = null;
    private static Thermocouple[] tcArray = {};

    static TcRepo getInstance(Context context) {
        if (ourInstance == null) ourInstance = new TcRepo(context);
        return ourInstance;
    }

    private TcRepo(Context context) {
        Resources res = context.getResources();
        String[] types = res.getStringArray(R.array.type);
        String[] pLegs = res.getStringArray(R.array.pLeg);
        String[] nLegs = res.getStringArray(R.array.nLeg);
        int[] minTemps = res.getIntArray(R.array.minTemp);
        int[] maxTemps = res.getIntArray(R.array.maxTemp);
        String[] infos = res.getStringArray(R.array.info);

        Map<String, TcColor[]> colorMap = getColors();
        tcArray = new Thermocouple[types.length];

        for (int i = 0; i < types.length; i++) {
            Thermocouple tc = new Thermocouple(types[i], pLegs[i], nLegs[i], infos[i], minTemps[i], maxTemps[i]);
            tcArray[i] = tc;
            TcColor[] colors = colorMap.get(tc.type);
            if (colors != null) tc.setColors(colors);
        }
    }

    Thermocouple[] getAll() {
        return tcArray;
    }

    TcColor[] getColorArray() {
        List<TcColor> cList = new ArrayList<>();
        for (Thermocouple aTcArray : tcArray) {
            cList.addAll(aTcArray.colors.values());
        }
        return cList.toArray(new TcColor[0]);
    }

    Thermocouple getThermocoupleAt(int index) {
        return tcArray[index];
    }

    int count() {
        return tcArray.length;
    }

    String[] getTypesFormatted() {
        String[] result = new String[tcArray.length];
        for (int i = 0; i < tcArray.length; i++) {
            result[i] = tcArray[i].getTypeFormatted();
        }
        return result;
    }

    private Map<String, TcColor[]> getColors() {
        Map<String, TcColor[]> map = new HashMap<>();
        TcColor[] bColors = {
            new TcColor("ANSI", "grey", "white", "red", R.drawable.ansib),
            new TcColor("NFE", "grey", "yellow", "grey", R.drawable.nfeb),
            new TcColor("DIN", "grey", "red", "grey", R.drawable.dinb),
            new TcColor("JIS", "grey", "red", "grey", R.drawable.jisb),
        };
        map.put("B", bColors);
        TcColor[] eColors = {
                new TcColor("IEC", "purple", "purple", "white", R.drawable.iece),
                new TcColor("BS", "brown", "brown", "blue", R.drawable.bse),
                new TcColor("ANSI", "purple", "purple", "red", R.drawable.ansie),
                new TcColor("NFE", "purple", "yellow", "purple", R.drawable.nfee),
                new TcColor("DIN", "black", "red", "black", R.drawable.dine),
                new TcColor("JIS", "purple", "red", "white", R.drawable.jise),
        };
        map.put("E", eColors);
        TcColor[] jColors = {
                new TcColor("IEC", "black", "black", "white", R.drawable.iecj),
                new TcColor("BS", "black", "yellow", "blue", R.drawable.bsj),
                new TcColor("ANSI", "black", "white", "red", R.drawable.ansij),
                new TcColor("NFE", "black", "yellow", "black", R.drawable.nfej),
                new TcColor("DIN", "blue", "red", "blue", R.drawable.dinj),
                new TcColor("JIS", "yellow", "red", "white", R.drawable.jisj),
        };
        map.put("J", jColors);
        TcColor[] kColors = {
                new TcColor("IEC", "green", "green", "white", R.drawable.ieck),
                new TcColor("BS", "red", "brown", "blue", R.drawable.bsk),
                new TcColor("ANSI", "yellow", "yellow", "red", R.drawable.ansik),
                new TcColor("NFE", "yellow", "yellow", "purple", R.drawable.nfek),
                new TcColor("DIN", "green", "red", "green", R.drawable.dink),
                new TcColor("JIS", "blue", "red", "white", R.drawable.jisk),
        };
        map.put("K", kColors);
        TcColor[] nColors = {
                new TcColor("IEC", "pink", "pink", "white", R.drawable.iecn),
                new TcColor("BS", "orange", "orange", "blue", R.drawable.bsn),
                new TcColor("ANSI", "orange", "orange", "red", R.drawable.ansin),
        };
        map.put("N", nColors);
        TcColor[] rColors = {
                new TcColor("IEC", "orange", "orange", "white", R.drawable.iecu),
                new TcColor("BS", "green", "white", "blue", R.drawable.bsu),
                new TcColor("ANSI", "green", "black", "red", R.drawable.ansiu),
                new TcColor("NFE", "green", "yellow", "green", R.drawable.nfeu),
                new TcColor("DIN", "white", "red", "white", R.drawable.dinu),
                new TcColor("JIS", "black", "red", "white", R.drawable.jisu),
        };
        map.put("R", rColors);
        TcColor[] sColors = {
                new TcColor("IEC", "orange", "orange", "white", R.drawable.iecu),
                new TcColor("BS", "green", "white", "blue", R.drawable.bsu),
                new TcColor("ANSI", "green", "black", "red", R.drawable.ansiu),
                new TcColor("NFE", "green", "yellow", "green", R.drawable.nfeu),
                new TcColor("DIN", "white", "red", "white", R.drawable.dinu),
                new TcColor("JIS", "black", "red", "white", R.drawable.jisu),
        };
        map.put("S", sColors);
        TcColor[] tColors = {
                new TcColor("IEC", "brown", "brown", "white", R.drawable.iect),
                new TcColor("BS", "blue", "white", "blue", R.drawable.bst),
                new TcColor("ANSI", "blue", "blue", "red", R.drawable.ansit),
                new TcColor("NFE", "blue", "yellow", "blue", R.drawable.nfet),
                new TcColor("DIN", "brown", "red", "brown", R.drawable.dint),
                new TcColor("JIS", "brown", "red", "white", R.drawable.jist),
        };
        map.put("T", tColors);
        TcColor[] uColors = {
                new TcColor("IEC", "orange", "orange", "white", R.drawable.iecu),
                new TcColor("BS", "green", "white", "blue", R.drawable.bsu),
                new TcColor("ANSI", "green", "black", "red", R.drawable.ansiu),
                new TcColor("NFE", "green", "yellow", "green", R.drawable.nfeu),
                new TcColor("DIN", "white", "red", "white", R.drawable.dinu),
                new TcColor("JIS", "black", "red", "white", R.drawable.jisu),
        };
        map.put("U", uColors);
        TcColor[] vColors = {
                new TcColor("IEC", "green", "green", "white", R.drawable.iecv),
                new TcColor("BS", "red", "white", "blue", R.drawable.bsv),
                new TcColor("ANSI", "red", "brown", "red", R.drawable.ansiv),
                new TcColor("NFE", "red", "yellow", "brown", R.drawable.nfev),
                new TcColor("DIN", "green", "red", "green", R.drawable.dinv),
                new TcColor("JIS", "blue", "red", "white", R.drawable.jisv),
        };
        map.put("Vx", vColors);

        return map;
    }

}
