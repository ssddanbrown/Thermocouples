package com.southerntemp.thermocouples;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.collection.LruCache;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import com.southerntemp.thermocouples.databinding.FragmentDetailsBinding;

public class DetailsFragment extends Fragment {
	public static LruCache<String, Bitmap> mMemoryCache;
    TcRepo tcRepo;

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	private FragmentDetailsBinding binding;

	@Override
    public View onCreateView(
    		LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
		) {

		binding = FragmentDetailsBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

        tcRepo = TcRepo.getInstance(getContext());

        // Sidebar setup
		DrawerLayout homeDrawer = binding.homedrawerlayout;
        NavigationView sidebarNav = binding.leftDrawerView;
		Menu drawerMenu = sidebarNav.getMenu().getItem(0).getSubMenu();
        String[] tcTypes = tcRepo.getTypesFormatted();
		for (int i = 0; i < tcTypes.length; i++) {
			String tcType = tcTypes[i];
			MenuItem sidebarNavItem = drawerMenu.add(Menu.NONE, i, i, tcType);
			sidebarNavItem.setCheckable(true);
			sidebarNavItem.setChecked(i == 0);

			sidebarNavItem.setOnMenuItemClickListener((MenuItem item) -> {
				mViewPager.setCurrentItem(item.getItemId());
				homeDrawer.closeDrawer(sidebarNav);
				return false;
			});
		}

		// Viewpager setup
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
		mViewPager = binding.pager;
		mViewPager.setAdapter(mSectionsPagerAdapter);
		// Set sidebar draw active item on page change
		mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				for (int i = 0; i < drawerMenu.size(); i++) {
					drawerMenu.getItem(i).setChecked(false);
				}
				drawerMenu.getItem(position).setChecked(true);
			}
		});


		// Setup top app toolbar
		MaterialToolbar topAppToolbar = binding.topAppToolbar;
		topAppToolbar.setNavigationOnClickListener(listerView -> {
			if (homeDrawer.isOpen()){
				homeDrawer.close();
			} else {
				homeDrawer.open();
			}
		});

		// MEMORY image cache setup
        // Get max available VM memory, exceeding this amount will throw an
	    // OutOfMemory exception. Stored in kilobytes as LruCache takes an
	    // int in its constructor.
	    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
	    // Use 1/4th of the available memory for this memory cache.
	    final int cacheSize = maxMemory / 4;
	    mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
	        @Override
	        protected int sizeOf(String key, Bitmap bitmap) {
	            // The cache size will be measured in kilobytes rather than
	            // number of items.
	        	int byteCount = bitmap.getHeight() * bitmap.getWidth() * 4;
	            return byteCount / 1024;
	        }
	    };
		
	}

	@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
		}

		@Override
	    public Fragment getItem(int position) {
			Fragment fragment = new TcDetailsFragment();
	        Bundle args = new Bundle();
	        args.putInt("ID", position);
	        fragment.setArguments(args);
	        return fragment;
	    }

		@Override
		public int getCount() {
            return tcRepo.count();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return tcRepo.getThermocoupleAt(position).getTypeFormatted();
		}
	}
}

