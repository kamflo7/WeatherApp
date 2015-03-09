package com.example.weatherapp;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	private MyFragmentPageAdapter pagerAdapter;
	private ViewPager mViewPager;
	private List<Location> locations = new ArrayList<Location>();
	private int selectedIndexLocation = 0;

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

		locations.add(new Location("Kraków", 50.0605, 19.9324));
		locations.add(new Location("Szczecin", 53.4252, 14.5555));
		locations.add(new Location("Los Angeles", 34.0535, 118.245));
		locations.add(new Location("Miami", 25.7748, -80.1977));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d("test", "onCreateOptionMenu siê odpali³o");
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
						Toast.makeText(getApplicationContext(),
								"You selected item " + which, Toast.LENGTH_LONG)
								.show();
					}
				});
		builderSingle.show();
	}

}
