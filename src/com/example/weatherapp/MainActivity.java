package com.example.weatherapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	private MyFragmentPageAdapter pagerAdapter;
	private ViewPager mViewPager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final ActionBar actionBar = getActionBar();
        
        pagerAdapter = new MyFragmentPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int i) {
				actionBar.setSelectedNavigationItem(i);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) { }
			
			@Override
			public void onPageScrollStateChanged(int arg0) { }
		});
        
        
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				
			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				mViewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				
			}
        };
        
        actionBar.addTab(actionBar.newTab().setText("Teraz").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Godzinowa").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("D³ugoterminowa").setTabListener(tabListener));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	Log.d("test", "onCreateOptionMenu siê odpali³o");
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
