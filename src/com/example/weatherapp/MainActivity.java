package com.example.weatherapp;

import java.util.ArrayList;
import java.util.List;

import models.DayWeather;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import fragments.ViewWeatherHourly;
import fragments.ViewWeatherLong;
import fragments.ViewWeatherNow;

public class MainActivity extends FragmentActivity implements DayWeatherRequest.OnDayWeatherRequestCompleted {

	private MyFragmentPageAdapter pagerAdapter;
	private ViewPager mViewPager;
	
	private List<WeatherPlace> locations = new ArrayList<WeatherPlace>();
	private int selectedIndexLocation = 0;
	
	private List<Fragment> fragments = new ArrayList<Fragment>();
	
	public static String INTENT_ACTION = "com.example.weatherapp.fragments";
	public static String FRAGMENT_WEATHER_NOW = "weatherNow";
	public static String FRAGMENT_WEATHER_HOURLY = "weatherHourly";
	public static String FRAGMENT_WEATHER_LONG = "weatherLong";
	private IntentFilter filter = new IntentFilter(INTENT_ACTION);
	private DayWeatherRequest requestWeather;

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

		locations.add(new WeatherPlace("Kraków", 50.0605, 19.9324));
		locations.add(new WeatherPlace("Szczecin", 53.4252, 14.5555));
		locations.add(new WeatherPlace("Los Angeles", 34.0535, 118.245));
		locations.add(new WeatherPlace("Miami", 25.7748, -80.1977));
		
		requestWeatherData(locations.get(selectedIndexLocation));
	}
	
	private void requestWeatherData(WeatherPlace place) {
		Log.d("test", "idzie request do internetow o pogode");
		Toast.makeText(getApplicationContext(), "Wysylam request o pogode dla "+locations.get(selectedIndexLocation).locationName, Toast.LENGTH_LONG).show();
		requestWeather = new DayWeatherRequest(this);
		requestWeather.requestWeatherForLocationForAmountOfDays(place.location, 1);
	}
	
	@Override
	public void onDayWeatherRequestCompleted(DetailedDayWeather[] result) {
		Log.d("test", "Odebrany request: " + (result==null?("null"):("not null")));
		Log.d("test", "Count DetailedDayWeather: " + result.length);
		
		String test = String.format("[Odebrane dane:] temp: %f\nweather type: %s\nwindSpeed: %f\nhumidity: :%f\ncloud: %f",
				result[0].temp, result[0].type.toString(), result[0].windSpeed, result[0].humidity, result[0].cloudPercentage);
		Log.d("test", test);
		
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
		actionBar.addTab(actionBar.newTab().setText("D³ugoterminowa")
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
