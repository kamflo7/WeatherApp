package com.example.weatherapp;

import models.APIJSONAsyncTask;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	private Button testButton;
	private APIJSONAsyncTask apiHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.testButton = (Button) this.findViewById(R.id.requestTestButton);
        
        this.testButton.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View view) {
        		
        		Toast.makeText(MainActivity.this, "Button clicked" , Toast.LENGTH_SHORT).show();
        	}
        }); 
        
        this.apiHandler = new APIJSONAsyncTask();
        
        Location location = new Location("provider");
        location.setLatitude(50.061389);
        location.setLongitude(19.938333);
        this.apiHandler.requestWeatherForLocationForAmountOfDays(location, 10);
        

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        return true;
    }
    
}
