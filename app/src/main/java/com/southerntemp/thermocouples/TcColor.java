package com.southerntemp.thermocouples;

class TcColor {
    String standard;
    String colorJacket;
    String colorPos;
    String colorNeg;
    int drawable;

    TcColor(String standard, String colorJacket, String colorPos, String colorNeg, int drawable) {
        this.standard = standard;
        this.colorJacket = colorJacket;
        this.colorPos = colorPos;
        this.colorNeg = colorNeg;
        this.drawable = drawable;
    }
}