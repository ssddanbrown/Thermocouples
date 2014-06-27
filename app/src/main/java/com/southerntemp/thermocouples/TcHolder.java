package com.southerntemp.thermocouples;


import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class TcHolder extends FragmentActivity {
	ArrayAdapter<String> adapter;
	public static LruCache<String, Bitmap> mMemoryCache;
	static Typeface robotoSlab;
    private DrawerLayout homeDrawer;
    private ActionBarDrawerToggle drawerToggle;
    ListView drawerList;
    String[] thermoCouples;
	
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tcholder);

        //Get slab font for titles
        robotoSlab = Typeface.createFromAsset(getAssets(), "RobotoSlab-Bold.ttf");
        //Sidebar setup
		thermoCouples = getResources().getStringArray(R.array.tctitles);
        drawerList = (ListView)findViewById(R.id.left_drawer);
        adapter =  new ArrayAdapter<String>(this, R.layout.simple_list_item_1, thermoCouples);
        drawerList.setAdapter(adapter);
        homeDrawer = (DrawerLayout)findViewById(R.id.homedrawerlayout);
        drawerToggle = new ActionBarDrawerToggle(
                this, homeDrawer, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close
        ){};
        homeDrawer.setDrawerListener(drawerToggle);
		
		drawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
			                long id) {
				mViewPager.setCurrentItem(position);
                homeDrawer.closeDrawer(drawerList);
			}
			});


		//ActionBarSherlock setup
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
		
		//Viewpager setup
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager)findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		//MEMORY image cache setup
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
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			
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
            return thermoCouples.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return thermoCouples[position];
		}
	}

	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_thermocouple_list, menu);
		return true;
		
	}
	public boolean goToCalc(MenuItem item) {
        Intent intentCalc = new Intent(this, CalcHolder.class);
        startActivity(intentCalc);
        TcHolder.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        return true;
	}
	public boolean goToInfo(MenuItem item) {
        Intent intentInfo = new Intent(this, Info.class);
        startActivity(intentInfo);
        TcHolder.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        return true;
	}
	public boolean goToSearch(MenuItem item) {
        Intent intentSearch = new Intent(this, ThermocoupleSearch.class);
        startActivity(intentSearch);
        TcHolder.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            if(homeDrawer.isDrawerOpen(drawerList)){
                homeDrawer.closeDrawer(drawerList);
            } else {
                homeDrawer.openDrawer(drawerList);
            };
            return true;
        } else if (i == R.id.SearchItem) {
            goToSearch(item);
            return true;
        } else if (i == R.id.CalculatorItem) {
            goToCalc(item);
            return true;
        } else if (i == R.id.InfoItem) {
            goToInfo(item);
            return true;
        } else if (i==R.id.RtdItem){
            Intent intent = getPackageManager().getLaunchIntentForPackage("me.danb.retherm");
            if (intent != null) {
                // start the activity
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                // bring user to the market
                // or let them choose an app?
                intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("market://details?id=" + "me.danb.retherm"));
                startActivity(intent);
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
	}
}

