package com.southerntemp.thermocouples;

import java.math.BigDecimal;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class CalcActivity extends ActionBarActivity {

	
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	InputMethodManager imm;
	static Typeface rLight;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        //Set content view, Keyborad imm and light font
        setContentView(R.layout.activity_calc_holder);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        rLight = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
		
		//ActionBarSherlock setup
        Toolbar toolbar = (Toolbar)findViewById(R.id.tcholder_toolbar);
        setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
        // Create comptible method with toolbar instead of actionbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) actionBar.setElevation(0f);


		// Set up the ViewPager with the sections adapter.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		mViewPager = (ViewPager)findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		private Fragment[] fragmentlist = new Fragment[] {new Calculator(), new TcCalc(), new TcCalc2()};
		
		@Override
		public Fragment getItem(int position) {
			Fragment fragment = fragmentlist[position];
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "TEMPERATURE CONVERTER";
			case 1:
				return "MILLIVOLTS TO CELCIUS CONVETER";
			case 2:
				return "COLD JUNCTION VOLTAGE CALCULATOR";
			}
			return null;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            this.finish();
            overridePendingTransition(0, R.anim.push_left_out);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
	}
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.removeItem(R.id.InfoItem);
		menu.removeItem(R.id.CalculatorItem);
	    return true;
	}
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
	public static class Calculator extends Fragment {

		Double temp;
		View v;
		
		
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			 v = inflater.inflate(R.layout.calcview, container, false);
			
			//////Load everything from layout//////
			final EditText calcinput =(EditText)v.findViewById(R.id.calcinput);
			
			final Button buttonc = (Button)v.findViewById(R.id.buttonc);
			final Button buttonf = (Button)v.findViewById(R.id.buttonf);
			final Button buttonk = (Button)v.findViewById(R.id.buttonk);
			
			final TextView tempc =(TextView)v.findViewById(R.id.tempc);
			final TextView tempf =(TextView)v.findViewById(R.id.tempf);
			final TextView tempk =(TextView)v.findViewById(R.id.tempk);
			//////////
			
			//If android verions >ICS a light font is set
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				tempc.setTypeface(rLight);
				tempf.setTypeface(rLight);
				tempk.setTypeface(rLight);
			}
			
			//Centigrade button setup
	        buttonc.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	            	imm.hideSoftInputFromWindow(calcinput.getApplicationWindowToken(), 0);
	            	if (calcinput.getText().toString().equals(".") || calcinput.getText().toString().equals("-") || calcinput.getText().toString().equals("-.") || calcinput.getText().toString().equals("")){
	            		temp = 0.0;
	            	}
	            	else {
	                temp = Double.parseDouble(calcinput.getText().toString());
	            	}
	            	
	                BigDecimal cc = new BigDecimal(temp).setScale(3, BigDecimal.ROUND_HALF_UP);
	                tempc.setText( cc + "°C");
	                
	                double f = temp*1.8;
	                double f2 = f+32.0;
	                BigDecimal cf = new BigDecimal(f2).setScale(3, BigDecimal.ROUND_HALF_UP);
	                tempf.setText(cf + "°F");
	                
	                double k = temp + 273.15;
	                BigDecimal ck = new BigDecimal(k).setScale(3, BigDecimal.ROUND_HALF_UP);
	                tempk.setText(ck + "K");
	            }
	        });
			//Farenheight button setup
	        buttonf.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	            	imm.hideSoftInputFromWindow(calcinput.getApplicationWindowToken(), 0);
	            	if (calcinput.getText().toString().equals(".") || calcinput.getText().toString().equals("-") || calcinput.getText().toString().equals("-.") || calcinput.getText().toString().equals("")){
	            		temp = 0.0;
	            	}
	            	else {
	                temp = Double.parseDouble(calcinput.getText().toString());
	            	}
	                double c = temp - 32.0;
	                double eq = 5.0/9;
	                double c2 = c*eq;
	                BigDecimal fc = new BigDecimal(c2).setScale(3, BigDecimal.ROUND_HALF_UP);
	                tempc.setText(fc + "°C");
	                
	                BigDecimal ff = new BigDecimal(temp).setScale(3, BigDecimal.ROUND_HALF_UP);
	                tempf.setText(ff + "°F");
	                
	                
	                double k = temp - 32.0;
	                double k2 = k*eq;
	                double k3 = k2 + 273.15 ;
	                BigDecimal fk = new BigDecimal(k3).setScale(3, BigDecimal.ROUND_HALF_UP);
	                tempk.setText(fk + "K");
	            }
	        });
			//Kelvin button setup
	        buttonk.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	            	imm.hideSoftInputFromWindow(calcinput.getApplicationWindowToken(), 0);
	            	if (calcinput.getText().toString().equals(".") || calcinput.getText().toString().equals("-") || calcinput.getText().toString().equals("-.") || calcinput.getText().toString().equals("")){
	            		temp = 0.0;
	            	}
	            	else {
	                temp = Double.parseDouble(calcinput.getText().toString());
	            	}
	                double c = temp - 273.15;
	                BigDecimal kc = new BigDecimal(c).setScale(3, BigDecimal.ROUND_HALF_UP);
	                tempc.setText(kc + "°C");
	                
	                double f = temp - 273.15;
	                double f2 = f*1.8;
	                double f3 = f2 + 32.0;
	                BigDecimal kf = new BigDecimal(f3).setScale(3, BigDecimal.ROUND_HALF_UP);
	                tempf.setText(kf + "°F");
	                
	                BigDecimal kk = new BigDecimal(temp).setScale(3, BigDecimal.ROUND_HALF_UP);
	                tempk.setText(kk + "K");
	            }
	        });
	        return v;
		}
		@Override
		public void onStart() {
			super.onStart();
			final TextView tempc =(TextView)v.findViewById(R.id.tempc);
			final TextView tempf =(TextView)v.findViewById(R.id.tempf);
			final TextView tempk =(TextView)v.findViewById(R.id.tempk);
			
			final Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_in);
		    animation.setDuration(800);
		    final Animation animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in);
		    animation2.setDuration(800);
			
			tempc.setAnimation(animation);
			tempk.setAnimation(animation);
			tempf.setAnimation(animation2);
			
		}
	}
	public static class TcCalc extends Fragment {
		
		double mv;
		View v;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			v = inflater.inflate(R.layout.activity_tc_calc, container, false);
			
			//Set Views
			final TextView tcresultb = (TextView)v.findViewById(R.id.tcresultb);
			final TextView tcresulte = (TextView)v.findViewById(R.id.tcresulte);
			final TextView tcresultk = (TextView)v.findViewById(R.id.tcresultk);
			final TextView tcresultj = (TextView)v.findViewById(R.id.tcresultj);
			final TextView tcresultn = (TextView)v.findViewById(R.id.tcresultn);
			final TextView tcresultr = (TextView)v.findViewById(R.id.tcresultr);
			final TextView tcresults = (TextView)v.findViewById(R.id.tcresults);
			final TextView tcresultt = (TextView)v.findViewById(R.id.tcresultt);
			final EditText tcinput = (EditText)v.findViewById(R.id.tcinput);
			final Button tccalcbuttonk = (Button)v.findViewById(R.id.tccalcbuttonk);
			
			//Set light font for ICS and above
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				tcresultb.setTypeface(rLight);
				tcresulte.setTypeface(rLight);
				tcresultk.setTypeface(rLight);
				tcresultj.setTypeface(rLight);
				tcresultn.setTypeface(rLight);
				tcresultr.setTypeface(rLight);
				tcresults.setTypeface(rLight);
				tcresultt.setTypeface(rLight);
			}

			//Calculate button is clicked on keyboard enter
			tcinput.setOnEditorActionListener(new OnEditorActionListener() {
		        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
		             tccalcbuttonk.performClick();
		            }    
		            return false;
		        }
		    });
			
			
			tccalcbuttonk.setOnClickListener(new Button.OnClickListener(){
				public void onClick(View k){
					EditText tcinput = (EditText)v.findViewById(R.id.tcinput);
	            	InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	            	imm.hideSoftInputFromWindow(tcinput.getApplicationWindowToken(), 0);
					if (tcinput.getText().toString().equals(".") || tcinput.getText().toString().equals("-") || tcinput.getText().toString().equals("-.") || tcinput.getText().toString().equals("")){
	            		mv = 0.0;
	            	} else {
	                mv = Double.parseDouble(tcinput.getText().toString());
	            	}
					double resultb = Bmvtoc(mv);
	            	if (resultb == 0.00 || mv == 0.0){tcresultb.setText("Out of range");}
	            	else {BigDecimal result = new BigDecimal(resultb).setScale(2, BigDecimal.ROUND_HALF_UP);
	                tcresultb.setText(result + "°C");}
	            	
					double resulte = Emvtoc(mv);
	            	if (resulte == 0.00 || mv == 0.0){tcresulte.setText("Out of range");}
	            	else {BigDecimal result = new BigDecimal(resulte).setScale(2, BigDecimal.ROUND_HALF_UP);
	                tcresulte.setText(result + "°C");}
	            	
					double resultj = Jmvtoc(mv);
	            	if (resultj == 0.00 || mv == 0.0){tcresultj.setText("Out of range");}
	            	else {BigDecimal result = new BigDecimal(resultj).setScale(2, BigDecimal.ROUND_HALF_UP);
	                tcresultj.setText(result + "°C");}
	            	
					double resultk = Kmvtoc(mv);
	            	if (resultk == 0.00 || mv == 0.0){tcresultk.setText("Out of range");}
	            	else {BigDecimal result = new BigDecimal(resultk).setScale(2, BigDecimal.ROUND_HALF_UP);
	                tcresultk.setText(result + "°C");}
	            	
					double resultn = Nmvtoc(mv);
	            	if (resultn == 0.00 || mv == 0.0){tcresultn.setText("Out of range");}
	            	else {BigDecimal result = new BigDecimal(resultn).setScale(2, BigDecimal.ROUND_HALF_UP);
	                tcresultn.setText(result + "°C");}
	            	
					double resultr = Rmvtoc(mv);
	            	if (resultr == 0.00 || mv == 0.0){tcresultr.setText("Out of range");}
	            	else {BigDecimal result = new BigDecimal(resultr).setScale(2, BigDecimal.ROUND_HALF_UP);
	                tcresultr.setText(result + "°C");}
	            	
					double results = Smvtoc(mv);
	            	if (results == 0.00 || mv == 0.0){tcresults.setText("Out of range");}
	            	else {BigDecimal result = new BigDecimal(results).setScale(2, BigDecimal.ROUND_HALF_UP);
	                tcresults.setText(result + "°C");}
	            	
					double resultt = Tmvtoc(mv);
	            	if (resultt == 0.00 || mv == 0.0){tcresultt.setText("Out of range");}
	            	else {BigDecimal result = new BigDecimal(resultt).setScale(2, BigDecimal.ROUND_HALF_UP);
	                tcresultt.setText(result + "°C");}
				}
			});
			return v;
			
		}
		@Override
		public void onStart() {
			super.onStart();
			final LinearLayout b = (LinearLayout)v.findViewById(R.id.tcidb);
			final LinearLayout e = (LinearLayout)v.findViewById(R.id.tcide);
			final LinearLayout j = (LinearLayout)v.findViewById(R.id.tcidj);
			final LinearLayout k = (LinearLayout)v.findViewById(R.id.tcidk);
			final LinearLayout n = (LinearLayout)v.findViewById(R.id.tcidn);
			final LinearLayout r = (LinearLayout)v.findViewById(R.id.tcidr);
			final LinearLayout s = (LinearLayout)v.findViewById(R.id.tcids);
			final LinearLayout t = (LinearLayout)v.findViewById(R.id.tcidt);
			
			final Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_in);
		    animation.setDuration(800);
		    final Animation animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in);
		    animation2.setDuration(800);
			
			b.setAnimation(animation);
			e.setAnimation(animation2);
			j.setAnimation(animation);
			k.setAnimation(animation2);
			n.setAnimation(animation);
			r.setAnimation(animation2);
			s.setAnimation(animation);
			t.setAnimation(animation2);
			
		}
		
		public double Bmvtoc (double mv){
			double[] ak;
	    	ak = new double[9];
	    	if (mv >= 0.29 && mv < 2.431){
	        	ak[0]=5.0000000E+02;
	        	ak[1]=1.2417900E+00;
	        	ak[2]=1.9858097E+02;
	        	ak[3]=2.4284248E+01;
	        	ak[4]=-9.7271640E+01;
	        	ak[5]=-1.5701178E+01;
	        	ak[6]=3.1009445E-01;
	        	ak[7]=-5.0880251E-01;
	        	ak[8]=-1.6163342E-01;
	    	} else if (mv >= 2.431 && mv < 13.820){
	        	ak[0]=1.2461474E+03;
	        	ak[1]=7.2701221E+00;
	        	ak[2]=9.4321033E+01;
	        	ak[3]=7.3899296E+00;
	        	ak[4]=-1.5880987E-01;
	        	ak[5]=1.2681877E-02;
	        	ak[6]=1.0113834E-01;
	        	ak[7]=-1.6145962E-03;
	        	ak[8]=-4.1086314E-06;
	    	} else {
	    		return 0.00;
	    	}
	    	return thermocoupleEquation(ak);
		}
		public double Emvtoc (double mv){
			double[] ak;
	    	ak = new double[9];
	    	if (mv >= -9.835 && mv < -5.237){
	        	ak[0]=-1.1721668E+02;
	        	ak[1]=-5.9901698E+00;
	        	ak[2]=2.3647275E+01;
	        	ak[3]=1.2807377E+01;
	        	ak[4]=2.0665069E+00;
	        	ak[5]=8.6513472E-02;
	        	ak[6]=5.8995860E-01;
	        	ak[7]=1.0960713E-01;
	        	ak[8]=6.1769588E-03;
	    	} else if (mv >= -5.237 && mv < 0.591){
	        	ak[0]=-5.0000000E+01;
	        	ak[1]=-2.7871777E+00;
	        	ak[2]=1.9022736E+01;
	        	ak[3]=-1.7042725E+00;
	        	ak[4]=-3.5195189E-01;
	        	ak[5]=4.7766102E-03;
	        	ak[6]=-6.5379760E-02;
	        	ak[7]=-2.1732833E-02;
	        	ak[8]=0.0;
	    	} else if (mv >= 0.591 && mv < 24.964){
	        	ak[0]=2.5014600E+02;
	        	ak[1]=1.7191713E+01;
	        	ak[2]=1.3115522E+01;
	        	ak[3]=1.1780364E+00;
	        	ak[4]=3.6422433E-02;
	        	ak[5]=3.9584261E-04;
	        	ak[6]=9.3112756E-02;
	        	ak[7]=2.9804232E-03;
	        	ak[8]=3.3263032E-05;
	    	} else if (mv >= 24.964 && mv < 53.112){
	        	ak[0]=6.0139890E+02;
	        	ak[1]=4.5206167E+01;
	        	ak[2]=1.2399357E+01;
	        	ak[3]=4.3399963E-01;
	        	ak[4]=9.1967085E-03;
	        	ak[5]=1.6901585E-04;
	        	ak[6]=3.4424680E-02;
	        	ak[7]=6.9741215E-04;
	        	ak[8]=1.2946992E-05;
	    	}else if (mv >= 53.112 && mv < 76.373){
	        	ak[0]=8.0435911E+02;
	        	ak[1]=6.1359178E+01;
	        	ak[2]=1.2759508E+01;
	        	ak[3]=-1.1116072E+00;
	        	ak[4]=3.5332536E-02;
	        	ak[5]=3.3080380E-05;
	        	ak[6]=-8.8196889E-02;
	        	ak[7]=2.8497415E-03;
	        	ak[8]=0.0;
	    	} else {
	    		return 0.00;
	    	}
	    	return thermocoupleEquation(ak);
		}
		public double Jmvtoc (double mv){
			double[] ak;
	    	ak = new double[9];
	    	if (mv >= -8.095 && mv < 0){
	        	ak[0]=-6.4936529E+01;
	        	ak[1]=-3.1169773E+00;
	        	ak[2]=2.2133797E+01;
	        	ak[3]=2.0476437E+00;
	        	ak[4]=-4.6867532E-01;
	        	ak[5]=-3.6673992E-02;
	        	ak[6]=1.1746348E-01;
	        	ak[7]=-2.0903413E-02;
	        	ak[8]=-2.1823704E-03;
	    	} else if (mv >= 0 && mv < 21.840){
	        	ak[0]=2.5066947E+02;
	        	ak[1]=1.3592329E+01;
	        	ak[2]=1.8014787E+01;
	        	ak[3]=-6.5218881E-02;
	        	ak[4]=-1.2179108E-02;
	        	ak[5]=2.0061707E-04;
	        	ak[6]=-3.9494552E-03;
	        	ak[7]=-7.3728206E-04;
	        	ak[8]=1.6679731E-05;
	    	} else if (mv >= 21.840 && mv < 45.494){
	        	ak[0]=6.4950262E+02;
	        	ak[1]=3.6040848E+01;
	        	ak[2]=1.6593395E+01;
	        	ak[3]=7.3009590E-01;
	        	ak[4]=2.4157343E-02;
	        	ak[5]=1.2787077E-03;
	        	ak[6]=4.9172861E-02;
	        	ak[7]=1.6813810E-03;
	        	ak[8]=7.6067922E-05;
	    	} else if (mv >= 45.494 && mv < 57.953){
	        	ak[0]=9.2510550E+02;
	        	ak[1]=5.3433832E+01;
	        	ak[2]=1.6243326E+01;
	        	ak[3]=9.2793267E-01;
	        	ak[4]=6.4644193E-03;
	        	ak[5]=2.0464414E-03;
	        	ak[6]=5.2541788E-02;
	        	ak[7]=1.3682959E-04;
	        	ak[8]=1.3454746E-04;
	    	}else if (mv >= 57.953 && mv < 69.553){
	        	ak[0]=1.0511294E+03;
	        	ak[1]=6.0956091E+01;
	        	ak[2]=1.7156001E+01;
	        	ak[3]=-2.5931041E+00;
	        	ak[4]=-5.8339803E-02;
	        	ak[5]=1.9954137E-02;
	        	ak[6]=-1.5305581E-01;
	        	ak[7]=-2.9523967E-03;
	        	ak[8]=1.1340164E-03;
	    	} else {
	    		return 0.00;
	    	}
	    	return thermocoupleEquation(ak);
		}
		public double Kmvtoc (double mv){
			double[] ak;
	    	ak = new double[9];
	    	if (mv >= -6.5 && mv < -3.554){
	        	ak[0]=-1.2147164E+02;
	        	ak[1]=-4.1790858E+00;
	        	ak[2]=3.6069513E+01;
	        	ak[3]=3.0722076E+01;
	        	ak[4]=7.7913860E+00;
	        	ak[5]=5.2593991E-01;
	        	ak[6]=9.3939547E-01;
	        	ak[7]=2.7791285E-01;
	        	ak[8]=2.5163349E-02;
	    	} else if (mv >= -3.554 && mv < 4.096){
	        	ak[0]=-8.7935962E+00;
	        	ak[1]=-3.4489914E-01;
	        	ak[2]=2.5678719E+01;
	        	ak[3]=-4.9887904E-01;
	        	ak[4]=-4.4705222E-01;
	        	ak[5]=-4.4869203E-02;
	        	ak[6]=2.3893439E-04;
	        	ak[7]=-2.0397750E-02;
	        	ak[8]=-1.8424107E-03;
	    	} else if (mv >= 4.096 && mv < 16.397){
	        	ak[0]=3.1018976E+02;
	        	ak[1]=1.2631386E+01;
	        	ak[2]=2.4061949E+01;
	        	ak[3]=4.0158622E+00;
	        	ak[4]=2.6853917E-01;
	        	ak[5]=-9.7188544E-03;
	        	ak[6]=1.6995872E-01;
	        	ak[7]=1.1413069E-02;
	        	ak[8]=-3.9275155E-04;
	    	} else if (mv >= 16.397 && mv < 33.275){
	        	ak[0]=6.0572562E+02;
	        	ak[1]=2.5148718E+01;
	        	ak[2]=2.3539401E+01;
	        	ak[3]=4.6547228E-02;
	        	ak[4]=1.3444400E-02;
	        	ak[5]=5.9236853E-04;
	        	ak[6]=8.3445513E-04;
	        	ak[7]=4.6121445E-04;
	        	ak[8]=2.5488122E-05;
	    	}else if (mv >= 33.275 && mv < 69.553){
	        	ak[0]=1.0184705E+03;
	        	ak[1]=4.1993851E+01;
	        	ak[2]=2.5783239E+01;
	        	ak[3]=-1.8363403E+00;
	        	ak[4]=5.6176662E-02;
	        	ak[5]=1.8532400E-04;
	        	ak[6]=-7.4803355E-02;
	        	ak[7]=2.3841860E-03;
	        	ak[8]=0.0;
	    	} else {
	    		return 0.00;
	    	}
	    	return thermocoupleEquation(ak);
		}
		public double Nmvtoc (double mv){
			double[] ak;
	    	ak = new double[9];
	    	if (mv >= -4.313 && mv < 0){
	        	ak[0]=-5.9610511E+01;
	        	ak[1]=-1.5000000E+00;
	        	ak[2]=4.2021322E+01;
	        	ak[3]=4.7244037E+00;
	        	ak[4]=-6.1153213E+00;
	        	ak[5]=-9.9980337E-01;
	        	ak[6]=1.6385664E-01;
	        	ak[7]=-1.4994026E-01;
	        	ak[8]=-3.0810372E-02;
	    	} else if (mv >= 0 && mv < 20.613){
	        	ak[0]=3.1534505E+02;
	        	ak[1]=9.8870997E+00;
	        	ak[2]=2.7988676E+01;
	        	ak[3]=1.5417343E+00;
	        	ak[4]=-1.4689457E-01;
	        	ak[5]=-6.8322712E-03;
	        	ak[6]=6.2600036E-02;
	        	ak[7]=-5.1489572E-03;
	        	ak[8]=-2.8835863E-04;
	    	} else if (mv >= 20.613 && mv < 47.513){
	        	ak[0]=1.0340172E+03;
	        	ak[1]=3.7565475E+01;
	        	ak[2]=2.6029492E+01;
	        	ak[3]=-6.0783095E-01;
	        	ak[4]=-9.7742562E-03;
	        	ak[5]=-3.3148813E-06;
	        	ak[6]=-2.5351881E-02;
	        	ak[7]=-3.8746827E-04;
	        	ak[8]=1.7088177E-06;
	    	} else {
	    		return 0.00;
	    	}
	    	return thermocoupleEquation(ak);
		}
		public double Rmvtoc (double mv){
			double[] ak;
	    	ak = new double[9];
	    	if (mv >= -0.226 && mv < 1.469){
	    		ak[0]=1.3054315E+02;
	    		ak[1]=8.8333090E-01;
	    		ak[2]=1.2557377E+02;
	    		ak[3]=1.3900275E+02;
	    		ak[4]=3.3035469E+01;
	    		ak[5]=-8.5195924E-01;
	    		ak[6]=1.2232896E+00;
	    		ak[7]=3.5603023E-01;
	    		ak[8]=0.0;
	    	} else if (mv >= 1.469 && mv < 7.461){
	    		ak[0]=5.4188181E+02;
	    		ak[1]=4.9312886E+00;
	    		ak[2]=9.0208190E+01;
	    		ak[3]=6.1762254E+00;
	    		ak[4]=-1.2279323E+00;
	    		ak[5]=1.4873153E-02;
	    		ak[6]=8.7670455E-02;
	    		ak[7]=-1.2906694E-02;
	    		ak[8]=0.0;
	    	} else if (mv >= 7.461 && mv < 14.277){
	    		ak[0]=1.0382132E+03;
	    		ak[1]=1.1014763E+01;
	    		ak[2]=7.4669343E+01;
	    		ak[3]=3.4090711E+00;
	    		ak[4]=-1.4511205E-01;
	    		ak[5]=6.3077387E-03;
	    		ak[6]=5.6880253E-02;
	    		ak[7]=-2.0512736E-03;
	    		ak[8]=0.0;
	    	} else if (mv >= 14.277 && mv < 21.101){
	    		ak[0]=1.5676133E+03;
	    		ak[1]=1.8397910E+01;
	    		ak[2]=7.1646299E+01;
	    		ak[3]=-1.0866763E+00;
	    		ak[4]=-2.0968371E+00;
	    		ak[5]=-7.6741168E-01;
	    		ak[6]=-1.9712341E-02;
	    		ak[7]=-2.9903595E-02;
	    		ak[8]=-1.0766878E-02;
	    	} else {
	    		return 0.00;
	    	}
	    	return thermocoupleEquation(ak);
		}
		public double Smvtoc (double mv){
			double[] ak;
	    	ak = new double[9];
	    	if (mv >= -0.236 && mv < 1.441){
	    		ak[0]=1.3792630E+02;
	    		ak[1]=9.3395024E-01;
	    		ak[2]=1.2761836E+02;
	    		ak[3]=1.1089050E+02;
	    		ak[4]=1.9898457E+01;
	    		ak[5]=9.6152996E-02;
	    		ak[6]=9.6545918E-01;
	    		ak[7]=2.0813850E-01;
	    		ak[8]=0.0;
	    	} else if (mv >= 1.441 && mv < 6.913){
	    		ak[0]=4.7673468E+02;
	    		ak[1]=4.0037367E+00;
	    		ak[2]=1.0174512E+02;
	    		ak[3]=-8.9306371E+00;
	    		ak[4]=-4.2942435E+00;
	    		ak[5]=2.0453847E-01;
	    		ak[6]=-7.1227776E-02;
	    		ak[7]=-4.4618306E-02;
	    		ak[8]=1.6822887E-03;
	    	} else if (mv >= 6.913 && mv < 12.856){
	    		ak[0]=9.7946589E+02;
	    		ak[1]=9.3508283E+00;
	    		ak[2]=8.7126730E+01;
	    		ak[3]=-2.3139202E+00;
	    		ak[4]=-3.2682118E-02;
	    		ak[5]=4.6090022E-03;
	    		ak[6]=-1.4299790E-02;
	    		ak[7]=-1.2289882E-03;
	    		ak[8]=0.0;
	    	} else if (mv >= 12.856 && mv < 18.693){
	    		ak[0]=1.6010461E+03;
	    		ak[1]=1.6789315E+01;
	    		ak[2]=8.4315871E+01;
	    		ak[3]=-1.0185043E+01;
	    		ak[4]=-4.6283954E+00;
	    		ak[5]=-1.0158749E+00;
	    		ak[6]=-1.2877783E-01;
	    		ak[7]=-5.5802216E-02;
	    		ak[8]=-1.2146518E-02;
	    	} else {
	    		return 0.00;
	    	}
	    	return thermocoupleEquation(ak);
		}
		public double Tmvtoc (double mv){
			double[] ak;
	    	ak = new double[9];
	    	if (mv >= -6.18 && mv < -4.648){
	    		ak[0]=-1.9243000E+02;
	    		ak[1]=-5.4798963E+00;
	    		ak[2]=5.9572141E+01;
	    		ak[3]=1.9675733E+00;
	    		ak[4]=-7.8176011E+01;
	    		ak[5]=-1.0963280E+01;
	    		ak[6]=2.7498092E-01;
	    		ak[7]=-1.3768944E+00;
	    		ak[8]=-4.5209805E-01;
	    	} else if (mv >= -4.648 && mv < 0){
	    		ak[0]=-6.0000000E+01;
	    		ak[1]=-2.1528350E+00;
	    		ak[2]=3.0449332E+01;
	    		ak[3]=-1.2946560E+00;
	    		ak[4]=-3.0500735E+00;
	    		ak[5]=-1.9226856E-01;
	    		ak[6]=6.9877863E-03;
	    		ak[7]=-1.0596207E-01;
	    		ak[8]=-1.0774995E-02;
	    	} else if (mv >= 0 && mv < 9.288){
	    		ak[0]=1.3500000E+02;
	    		ak[1]=5.9588600E+00;
	    		ak[2]=2.0325591E+01;
	    		ak[3]=3.3013079E+00;
	    		ak[4]=1.2638462E-01;
	    		ak[5]=-8.2883695E-04;
	    		ak[6]=1.7595577E-01;
	    		ak[7]=7.9740521E-03;
	    		ak[8]=0.0;
	    	} else if (mv >= 9.288 && mv < 20.872){
	    		ak[0]=3.0000000E+02;
	    		ak[1]=1.4861780E+01;
	    		ak[2]=1.7214707E+01;
	    		ak[3]=-9.3862713E-01;
	    		ak[4]=-7.3509066E-02;
	    		ak[5]=2.9576140E-04;
	    		ak[6]=-4.8095795E-02;
	    		ak[7]=-4.7352054E-03;
	    		ak[8]=0.00;
	    	} else {
	    		return 0.00;
	    	}
	    	return thermocoupleEquation(ak);
		}
		private double thermocoupleEquation(double[] ak){
	    	double vvo = mv-ak[1];
	    	double ek1 = ak[5]*vvo;
	    	ek1 = ek1+ak[4];
	    	ek1 = ek1*vvo;
	    	ek1 = ek1+ak[3];
	    	ek1 = ek1*vvo;
	    	ek1 = ek1+ak[2];
	    	ek1 = ek1*vvo;
	    	double ek2 = vvo*ak[8];
	    	ek2 = ek2+ak[7];
	    	ek2 = ek2*vvo;
	    	ek2 = ek2+ak[6];
	    	ek2 = ek2*vvo;
	    	ek2 = ek2+1.0;
	    	double ek3 = ek1/ek2;
	    	ek3 = ek3+ak[0];
	    	return ek3;
		}

	}
    public static class TcCalc2 extends Fragment {

        double temp;
        View v;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.activity_tc_calc2, container, false);

            //Set Views
            final TextView tcresultb = (TextView)v.findViewById(R.id.tcresultb);
            final TextView tcresulte = (TextView)v.findViewById(R.id.tcresulte);
            final TextView tcresultk = (TextView)v.findViewById(R.id.tcresultk);
            final TextView tcresultj = (TextView)v.findViewById(R.id.tcresultj);
            final TextView tcresultn = (TextView)v.findViewById(R.id.tcresultn);
            final TextView tcresultr = (TextView)v.findViewById(R.id.tcresultr);
            final TextView tcresults = (TextView)v.findViewById(R.id.tcresults);
            final TextView tcresultt = (TextView)v.findViewById(R.id.tcresultt);
            final EditText tcinput = (EditText)v.findViewById(R.id.tcinput);
            final Button tccalcbuttonk = (Button)v.findViewById(R.id.tccalcbuttonk);

            //Set light font for ICS and above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                tcresultb.setTypeface(rLight);
                tcresulte.setTypeface(rLight);
                tcresultk.setTypeface(rLight);
                tcresultj.setTypeface(rLight);
                tcresultn.setTypeface(rLight);
                tcresultr.setTypeface(rLight);
                tcresults.setTypeface(rLight);
                tcresultt.setTypeface(rLight);
            }

            //Calculate button is clicked on keyboard enter
            tcinput.setOnEditorActionListener(new OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        tccalcbuttonk.performClick();
                    }
                    return false;
                }
            });


            tccalcbuttonk.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View k){
                    EditText tcinput = (EditText)v.findViewById(R.id.tcinput);
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(tcinput.getApplicationWindowToken(), 0);
                    if (tcinput.getText().toString().equals(".") || tcinput.getText().toString().equals("-") || tcinput.getText().toString().equals("-.") || tcinput.getText().toString().equals("")){
                        temp = 0.0;
                    } else {
                        temp = Double.parseDouble(tcinput.getText().toString());
                    }
                    double resultb = Bmvtoc(temp);
                    if (resultb == 0.00 || temp == 0.0){tcresultb.setText("Out of range");}
                    else {BigDecimal result = new BigDecimal(resultb).setScale(5, BigDecimal.ROUND_HALF_UP);
                        tcresultb.setText(result + "mV");}

                    double resulte = Emvtoc(temp);
                    if (resulte == 0.00 || temp == 0.0){tcresulte.setText("Out of range");}
                    else {BigDecimal result = new BigDecimal(resulte).setScale(5, BigDecimal.ROUND_HALF_UP);
                        tcresulte.setText(result + "mV");}

                    double resultj = Jmvtoc(temp);
                    if (resultj == 0.00 || temp == 0.0){tcresultj.setText("Out of range");}
                    else {BigDecimal result = new BigDecimal(resultj).setScale(5, BigDecimal.ROUND_HALF_UP);
                        tcresultj.setText(result + "mV");}

                    double resultk = Kmvtoc(temp);
                    if (resultk == 0.00 || temp == 0.0){tcresultk.setText("Out of range");}
                    else {BigDecimal result = new BigDecimal(resultk).setScale(5, BigDecimal.ROUND_HALF_UP);
                        tcresultk.setText(result + "mV");}

                    double resultn = Nmvtoc(temp);
                    if (resultn == 0.00 || temp == 0.0){tcresultn.setText("Out of range");}
                    else {BigDecimal result = new BigDecimal(resultn).setScale(5, BigDecimal.ROUND_HALF_UP);
                        tcresultn.setText(result + "mV");}

                    double resultr = Rmvtoc(temp);
                    if (resultr == 0.00 || temp == 0.0){tcresultr.setText("Out of range");}
                    else {BigDecimal result = new BigDecimal(resultr).setScale(5, BigDecimal.ROUND_HALF_UP);
                        tcresultr.setText(result + "mV");}

                    double results = Smvtoc(temp);
                    if (results == 0.00 || temp == 0.0){tcresults.setText("Out of range");}
                    else {BigDecimal result = new BigDecimal(results).setScale(5, BigDecimal.ROUND_HALF_UP);
                        tcresults.setText(result + "mV");}

                    double resultt = Tmvtoc(temp);
                    if (resultt == 0.00 || temp == 0.0){tcresultt.setText("Out of range");}
                    else {BigDecimal result = new BigDecimal(resultt).setScale(5, BigDecimal.ROUND_HALF_UP);
                        tcresultt.setText(result + "mV");}
                }
            });
            return v;

        }
        @Override
        public void onStart() {
            super.onStart();
            final LinearLayout b = (LinearLayout)v.findViewById(R.id.tcidb);
            final LinearLayout e = (LinearLayout)v.findViewById(R.id.tcide);
            final LinearLayout j = (LinearLayout)v.findViewById(R.id.tcidj);
            final LinearLayout k = (LinearLayout)v.findViewById(R.id.tcidk);
            final LinearLayout n = (LinearLayout)v.findViewById(R.id.tcidn);
            final LinearLayout r = (LinearLayout)v.findViewById(R.id.tcidr);
            final LinearLayout s = (LinearLayout)v.findViewById(R.id.tcids);
            final LinearLayout t = (LinearLayout)v.findViewById(R.id.tcidt);

            final Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_in);
            animation.setDuration(800);
            final Animation animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in);
            animation2.setDuration(800);

            b.setAnimation(animation);
            e.setAnimation(animation2);
            j.setAnimation(animation);
            k.setAnimation(animation2);
            n.setAnimation(animation);
            r.setAnimation(animation2);
            s.setAnimation(animation);
            t.setAnimation(animation2);

        }

        public double Bmvtoc (double temp){
            double[] ak;
            ak = new double[8];
            if (temp >= 0 && temp <= 70){
                ak[0]=42;
                ak[1]=3.3933898E-04;
                ak[2]=0.000211966840978536;
                ak[3]=0.00000338012504461122;
                ak[4]=-0.000000147932893448519;
                ak[5]=-0.00000000335714235152534;
                ak[6]=-0.0109204104235899;
                ak[7]=-0.000497829320147356;
            } else {
                return 0.00;
            }
            return thermocoupleEquation(ak);
        }
        public double Emvtoc (double temp){
            double[] ak;
            ak = new double[8];
            if (temp >= -20 && temp <= 70){
                ak[0]=25;
                ak[1]=1.49505819808044;
                ak[2]=0.0609584430323512;
                ak[3]=-0.000273517892774302;
                ak[4]=-0.0000191301455052136;
                ak[5]=-0.0000000139488399503399;
                ak[6]=-0.00523823782546346;
                ak[7]=-0.000309701677615794;
            } else {
                return 0.00;
            }
            return thermocoupleEquation(ak);
        }
        public double Jmvtoc (double temp){
            double[] ak;
            ak = new double[8];
            if (temp >= -20 && temp <= 70){
                ak[0]=25;
                ak[1]=1.27734320749372;
                ak[2]=0.051744084343395;
                ak[3]=-0.0000541386630448239;
                ak[4]=-0.00000228957691754618;
                ak[5]=-0.000000000779471431421526;
                ak[6]=-0.00151733422011515;
                ak[7]=-0.0000423145140497174;
            } else {
                return 0.00;
            }
            return thermocoupleEquation(ak);
        }
        public double Kmvtoc (double temp){
            double[] ak;
            ak = new double[8];
            if (temp >= -20 && temp <= 70){
                ak[0]=25;
                ak[1]=1.0003453470161;
                ak[2]=0.0405148536182522;
                ak[3]=-0.0000387896383378116;
                ak[4]=-0.00000286084782705613;
                ak[5]=-0.000000000953670410293728;
                ak[6]=-0.00139486750341965;
                ak[7]=-0.0000679766265955419;
            } else {
                return 0.00;
            }
            return thermocoupleEquation(ak);
        }
        public double Nmvtoc (double temp){
            double[] ak;
            ak = new double[8];
            if (temp >= -20 && temp <= 70){
                ak[0]=7;
                ak[1]=0.182100239904779;
                ak[2]=0.0262282563003498;
                ak[3]=-0.00015485538676966;
                ak[4]=0.00000213660305580148;
                ak[5]=0.000000000920471045789394;
                ak[6]=-0.00640709323474923;
                ak[7]=0.0000821617805375071;
            } else {
                return 0.00;
            }
            return thermocoupleEquation(ak);
        }
        public double Rmvtoc (double temp){
            double[] ak;
            ak = new double[8];
            if (temp >= -20 && temp <= 70){
                ak[0]=25;
                ak[1]=0.140670158397482;
                ak[2]=0.00593303558822883;
                ak[3]=0.00002773690432834;
                ak[4]=-0.00000108196442544743;
                ak[5]=-0.00000000230983486448817;
                ak[6]=0.00261468708400149;
                ak[7]=-0.000186214868332857;
            } else {
                return 0.00;
            }
            return thermocoupleEquation(ak);
        }
        public double Smvtoc (double temp){
            double[] ak;
            ak = new double[8];
            if (temp >= -20 && temp <= 70){
                ak[0]=25;
                ak[1]=0.142691632773367;
                ak[2]=0.00598290574053624;
                ak[3]=0.00000452922591841043;
                ak[4]=-0.00000133802812584086;
                ak[5]=-0.00000000237425769266271;
                ak[6]=-0.00106504462176573;
                ak[7]=-0.000220424198826495;
            } else {
                return 0.00;
            }
            return thermocoupleEquation(ak);
        }
        public double Tmvtoc (double temp){
            double[] ak;
            ak = new double[8];
            if (temp >= -20 && temp <= 70){
                ak[0]=25;
                ak[1]=0.991982794192893;
                ak[2]=0.0407165636528211;
                ak[3]=0.000711702968676807;
                ak[4]=0.000000687826314743434;
                ak[5]=0.0000000000432950612014075;
                ak[6]=0.0164581016602501;
                ak[7]=0;
            } else {
                return 0.00;
            }
            return thermocoupleEquation(ak);
        }
        private double thermocoupleEquation(double[] ak){
            double tto = temp-ak[0];
            double e1 = tto*ak[5];
            e1 = e1+ak[4];
            e1 = e1*tto;
            e1 = e1+ak[3];
            e1 = e1*tto;
            e1 = e1+ak[2];
            e1 = e1*tto;
            double e2 = tto*ak[7];
            e2 = e2+ak[6];
            e2 = e2*tto;
            e2 = e2+1;
            double e3 = e1/e2;
            e3 = e3 + ak[1];
            return e3;
        }

    }
}
