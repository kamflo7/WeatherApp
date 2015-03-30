package com.example.weatherapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.DayWeatherRequest;
import models.DetailedDayWeather;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import fragments.ViewWeatherHourly;
import fragments.ViewWeatherLong;
import fragments.ViewWeatherNow;

public class MainActivity extends FragmentActivity {

	private MyFragmentPageAdapter pagerAdapter;
	private ViewPager mViewPager;
	private List<Fragment> fragments = new ArrayList<Fragment>();
	
	private List<WeatherPlace> locations = new ArrayList<WeatherPlace>();
	private int selectedIndexLocation = 0;
	
	public static String INTENT_ACTION = "com.example.weatherapp.fragments";
	public static String FRAGMENT_WEATHER_NOW = "weatherNow";
	public static String FRAGMENT_WEATHER_HOURLY = "weatherHourly";
	public static String FRAGMENT_WEATHER_LONG = "weatherLong";
	private IntentFilter filter = new IntentFilter(INTENT_ACTION);
	private BroadcastReceiver broadcast = new BroadcastReceiver(){
		private int a = 0;
        @Override
        public void onReceive(Context context, Intent intent) {
        	Log.d("test", "onReceive [::onCreateView] " + intent.getStringExtra("fragmentName") + " " + (++a));    	
        }
    };
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initBarAndNavigation();

		// latitude longitude
		locations.add(new WeatherPlace("Krak�w", 50.08, 19.92));
		locations.add(new WeatherPlace("Szczecin", 53.4252, 14.5555));
		locations.add(new WeatherPlace("Los Angeles", 34.0535, 118.245));
		locations.add(new WeatherPlace("Miami", 25.7748, -80.1977));
		locations.add(new WeatherPlace("Kair", 30.05, 31.2486));
		locations.add(new WeatherPlace("Tokio", 35.689499, 139.691711));
		locations.add(new WeatherPlace("Bangkok", 13.75, 100.51667));
		
		requestWeatherData(locations.get(selectedIndexLocation));
	}

	
	private void requestWeatherData(WeatherPlace place) {
		Log.d("test", "Id� requesty o pogode do internet�w");
		Toast.makeText(getApplicationContext(), "Pobieram pogod� dla lokalizacji: "+locations.get(selectedIndexLocation).locationName, Toast.LENGTH_LONG).show();
		
//		DayWeatherRequest requestNowAndLong = new DayWeatherRequest(new DayWeatherRequest.OnDayWeatherRequestCompleted() {
//			@Override
//			public void onDayWeatherRequestCompleted(DetailedDayWeather[] result) {
//				debugPrintWeather(result);
//				
//				Time now = new Time();
//				now.setToNow();
//				Log.d("test", "Current time in smartphone is: " + String.format("%d-%d-%d %d:%d", now.year, now.month+1, now.monthDay, now.hour, now.minute));
//				
//				Calendar mydate = Calendar.getInstance();
//				int startIndexForWeatherToday = 0;
//				for(int i=0; i<result.length; i++) {
//					mydate.setTimeInMillis(result[i].timestamp*1000);
//					
//					if(mydate.get(Calendar.DAY_OF_MONTH) == now.monthDay) {
//						startIndexForWeatherToday = i;
//						break;
//					}
//				}
//				
//				Log.d("test", "Calculated index for weather today is " + startIndexForWeatherToday);
//				
//				((ViewWeatherNow) fragments.get(0)).setModel(result[startIndexForWeatherToday]);
//				((ViewWeatherLong) fragments.get(2)).setModel(result, startIndexForWeatherToday);
//			}
//		});
//		requestNowAndLong.requestWeatherForLocationForAmountOfDays(place.location, 5, DayWeatherRequest.RequestType.TYPE_DAILY);
		
		DayWeatherRequest requestHourly = new DayWeatherRequest(new DayWeatherRequest.OnDayWeatherRequestCompleted() {
			@Override
			public void onDayWeatherRequestCompleted(DetailedDayWeather[] result) {
				Log.d("test", "--------------------------\nTeraz request godzinowy");
				debugPrintWeather(result);
				
				Time now = new Time();
				now.setToNow();
				
				Log.d("test", "Current time in smartphone is: " + String.format("%d-%d-%d %d:%d", now.year, now.month+1, now.monthDay, now.hour, now.minute));
				
				Calendar mydate = Calendar.getInstance();
				int startIndexForWeatherToday = 0;
				for(int i=0; i<result.length; i++) {
					mydate.setTimeInMillis(result[i].timestamp*1000);
					
					if(mydate.get(Calendar.DAY_OF_MONTH) == now.monthDay) {
						startIndexForWeatherToday = i;
						break;
					}
				}
				
				Log.d("test", "Calculated index for weather today is " + startIndexForWeatherToday);
				((ViewWeatherNow) fragments.get(0)).setModel(result[startIndexForWeatherToday]);
				((ViewWeatherLong) fragments.get(2)).setModel(result, startIndexForWeatherToday);
				((ViewWeatherHourly) fragments.get(1)).setModel(result, startIndexForWeatherToday);
			}
		});
		requestHourly.requestWeatherForLocationForAmountOfDays(place.location, 40, DayWeatherRequest.RequestType.TYPE_HOURLY);
	}
	
	private void debugPrintWeather(DetailedDayWeather[] result) {
		long oneHour = 60*60*1000;
		Log.d("test", "Debugging receiving array weather, count = " + result.length);
		Log.d("test", "lp | timestamp | full date | �C  | wnd | hum");
		for(int i=0; i<result.length; i++) {
			Date d = new Date(result[i].timestamp * 1000l + oneHour);
			String dformat = d.toGMTString()+"+1";
			Log.d("test", String.format("%d | %d | %s | %.1f | %.1f | %.1f", i, result[i].timestamp, dformat, result[i].temp, result[i].windSpeed, result[i].humidity));
		}
	}
	
	@Override
	protected void onResume() {
		registerReceiver(broadcast, filter);
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		unregisterReceiver(broadcast);
		super.onPause();
	}
	
	private void initBarAndNavigation() {
		final ActionBar actionBar = getActionBar();
		mViewPager = (ViewPager) findViewById(R.id.pager);
		
		fragments.add(Fragment.instantiate(this, ViewWeatherNow.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewWeatherHourly.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewWeatherLong.class.getName()));
		
		pagerAdapter = new MyFragmentPageAdapter(getSupportFragmentManager(), fragments);
		
		mViewPager.setAdapter(pagerAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int i) {
				actionBar.setSelectedNavigationItem(i);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
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

		actionBar.addTab(actionBar.newTab().setText("Teraz")
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Godzinowa")
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("D�ugoterminowa")
				.setTabListener(tabListener));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_location:
			showDialogLocation();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showDialogLocation() {
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(
				MainActivity.this);

		builderSingle.setIcon(R.drawable.ic_launcher);
		builderSingle.setTitle("Select location");
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				MainActivity.this, android.R.layout.select_dialog_singlechoice);

		for (int i = 0; i < locations.size(); i++) {
			arrayAdapter.add(locations.get(i).locationName);
		}

		builderSingle.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builderSingle.setAdapter(arrayAdapter,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
//						Toast.makeText(getApplicationContext(),
//								"You selected item " + which, Toast.LENGTH_LONG)
//								.show();
						selectedIndexLocation = which;
						requestWeatherData(locations.get(selectedIndexLocation));
					}
				});
		builderSingle.show();
	}
}
