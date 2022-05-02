package com.southerntemp.thermocouples;


import java.util.HashMap;
import java.util.Map;

class Thermocouple {

    String type, pLeg, nLeg, info;
    int minTemp, maxTemp;
    Map<String, TcColor> colors;

    Thermocouple(String type, String pLeg, String nLeg, String info, int minTemp, int maxTemp) {
        this.type = type;
        this.pLeg = pLeg;
        this.nLeg = nLeg;
        this.info = info;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.colors = new HashMap<>();
    }

    void setColors(TcColor[] colors) {
        for (TcColor color : colors) {
            this.colors.put(color.standard, color);
            color.setThermocouple(this);
        }
    }

    String getTypeFormatted() {
        return String.format("Type %s", type);
    }
}


