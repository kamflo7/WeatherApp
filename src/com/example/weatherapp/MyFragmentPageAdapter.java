package com.example.weatherapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPageAdapter extends FragmentPagerAdapter {

	public MyFragmentPageAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		switch(i) {
		case 0: return new ViewWeatherNow();
		case 1: return new ViewWeatherHourly();
		case 2: return new ViewWeatherLong();
		}
		return null;
	}

	@Override
	public int getCount() {
		return 3;
	}
	
	@Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }

}