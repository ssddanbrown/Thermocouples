package com.southerntemp.thermocouples;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.southerntemp.thermocouples.databinding.FragmentCalcBinding;

public class CalcFragment extends Fragment {

	private FragmentCalcBinding binding;
	
	SectionsPagerAdapter mSectionsPagerAdapter;

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

		// Set up the ViewPager with the sections adapter.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity());
		ViewPager2 mViewPager = binding.calcFragmentPager;
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// Tab handling
		TabLayout topTabs = binding.calcTopTabs;
		String[] tabTitles = new String[] {"Temp Conversions", "mV to °C", "°C to mV"};
		new TabLayoutMediator(topTabs, mViewPager,
				(tab, position) -> tab.setText(tabTitles[position])
		).attach();
	}

	public class SectionsPagerAdapter extends FragmentStateAdapter {

		private Fragment[] fragmentList = new Fragment[] {new CalcTempToTempFragment(), new CalcMvToTempFragment(), new CalcTempToMvFragment()};

		public SectionsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
			super(fragmentActivity);
		}

		@NonNull
		@Override
		public Fragment createFragment(int position) {
			return fragmentList[position];
		}

		@Override
		public int getItemCount() {
			return fragmentList.length;
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}

}
