package com.southerntemp.thermocouples;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.southerntemp.thermocouples.databinding.FragmentDetailsBinding;

public class DetailsFragment extends Fragment {
	public static LruCache<String, Bitmap> mMemoryCache;
    TcRepo tcRepo;

	SectionsPagerAdapter mSectionsPagerAdapter;
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

		// Viewpager setup
		mSectionsPagerAdapter = new SectionsPagerAdapter(this);
		ViewPager2 mViewPager = binding.detailsFragmentPager;
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// Tab handling
		TabLayout topTabs = binding.detailsTopTabs;
		new TabLayoutMediator(topTabs, mViewPager,
				(tab, position) -> tab.setText(tcRepo.getThermocoupleAt(position).getTypeFormatted())
		).attach();

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

    public class SectionsPagerAdapter extends FragmentStateAdapter {

		public SectionsPagerAdapter(@NonNull Fragment fragment) {
			super(fragment);
		}

		@NonNull
		@Override
		public Fragment createFragment(int position) {
			Fragment fragment = new TcDetailsFragment();
			Bundle args = new Bundle();
			args.putInt("ID", position);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getItemCount() {
			return tcRepo.count();
		}
	}
}

