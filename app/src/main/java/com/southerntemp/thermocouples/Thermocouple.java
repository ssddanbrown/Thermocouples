package com.southerntemp.thermocouples;

class Thermocouple {

    String type, pLeg, nLeg, info;
    int minTemp, maxTemp;
    TcColor[] colors;

    Thermocouple(String type, String pLeg, String nLeg, String info, int minTemp, int maxTemp) {
        this.type = type;
        this.pLeg = pLeg;
        this.nLeg = nLeg;
        this.info = info;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.colors = new TcColor[]{};
    }

    void setColors(TcColor[] colors) {
        this.colors = colors;
    }

    String getTypeFormatted() {
        return String.format("Type %s", type);
    }
}


