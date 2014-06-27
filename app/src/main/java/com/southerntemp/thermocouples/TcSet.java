package com.southerntemp.thermocouples;

public class TcSet {
	private int tcId;
	private String jColor;
	private String pColor;
	private String nColor;
	private String tcStd;
	
	public TcSet(int tcid,String tcstd, String jc, String pc, String nc){
		this.tcId = tcid;
		this.jColor = jc;
		this.pColor = pc;
		this.nColor = nc;
		this.tcStd = tcstd;
	}
	
	public void setStd(String std){
		this.tcStd=std;
	}
	
	public String toString(){
		return tcStd;
	}
	public int getId(){
		return tcId;
	}
	public String getStd(){
		return tcStd;
	}
	public String getJcolor(){
		return jColor;
	}
	public String[] getLegColors(){
		String[] a = {pColor, nColor};
		return a;
	}

}
