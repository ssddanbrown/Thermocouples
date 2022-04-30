package com.southerntemp.thermocouples;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.collection.LruCache;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class DetailsActivity extends AppCompatActivity {
	public static LruCache<String, Bitmap> mMemoryCache;
    private DrawerLayout homeDrawer;
    private ActionBarDrawerToggle drawerToggle;
    NavigationView drawerMenu;
    TcRepo tcRepo;
	
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	

    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

        // Toolbar setup
//        Toolbar toolbar = findViewById(R.id.tcholder_toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.inflateMenu(R.menu.activity_thermocouple_list);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeButtonEnabled(true);
//        }

        tcRepo = TcRepo.getInstance(getApplicationContext());

        // Sidebar setup
        NavigationView sidebarNav = findViewById(R.id.left_drawer_view);
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
//        homeDrawer = findViewById(R.id.homedrawerlayout);
//        drawerToggle = new ActionBarDrawerToggle(
//                this, homeDrawer, toolbar, R.string.drawer_open, R.string.drawer_close
//        ){};
//        homeDrawer.addDrawerListener(drawerToggle);


        // Create compatible method with toolbar instead of actionbar
//		toolbar.setElevation(10f);

		// Viewpager setup
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		mViewPager = findViewById(R.id.pager);
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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
//        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        drawerToggle.onConfigurationChanged(newConfig);
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

	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.nav_menu, menu);
		return true;
		
	}

	protected boolean goToActivity(Class<? extends Activity> activity) {
        Intent intentSearch = new Intent(this, activity);
        startActivity(intentSearch);
        DetailsActivity.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            if (homeDrawer.isDrawerOpen(drawerMenu)){
                homeDrawer.closeDrawer(drawerMenu);
            } else {
                homeDrawer.openDrawer(drawerMenu);
            }
            return true;
        }
        if (i == R.id.SearchItem) return goToActivity(SearchActivity.class);
        if (i == R.id.CalculatorItem) return goToActivity(CalcActivity.class);
        if (i == R.id.InfoItem) return goToActivity(InfoActivity.class);
        return super.onOptionsItemSelected(item);
	}
}

