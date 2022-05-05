package com.southerntemp.thermocouples;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.southerntemp.thermocouples.databinding.FragmentCalcBinding;

public class CalcFragment extends Fragment {

	private FragmentCalcBinding binding;
	
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	InputMethodManager imm;

	@Override
	public View onCreateView(
			LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState
	) {
		binding = FragmentCalcBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

        // Keyboard imm
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

		// Set up the ViewPager with the sections adapter.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
		mViewPager = binding.calcFragmentPager;
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		private Fragment[] fragmentlist = new Fragment[] {new CalcTempToTempFragment(), new CalcMvToTempFragment(), new CalcTempToMvFragment()};
		
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
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}

}
