package com.southerntemp.thermocouples;

public class TcSet {
	private int tcId;
	private String jColor;
	private String pColor;
	private String nColor;
	private String tcStd;
	
	TcSet(int tcid, String tcstd, String jc, String pc, String nc){
		this.tcId = tcid;
		this.jColor = jc;
		this.pColor = pc;
		this.nColor = nc;
		this.tcStd = tcstd;
	}
	
	public String toString(){
		return tcStd;
	}
	public int getId(){
		return tcId;
	}
	String getStd(){
		return tcStd;
	}
	String getJcolor(){
		return jColor;
	}
	String[] getLegColors(){
		return new String[]{pColor, nColor};
	}

}
