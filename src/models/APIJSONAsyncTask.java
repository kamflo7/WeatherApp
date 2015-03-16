package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;



public class APIJSONAsyncTask extends AsyncTask<String, String, Void> {

	
	static String SERVER 				= "http://api.openweathermap.org/data/2.5/forecast/daily?";
	static String LATITUDE_PARAM_NAME 	= "lat=";
	static String LONGITUDE_PARAM_NAME 	= "lon=";
	static String CNT_DAYS 				= "cnt=";
	static String MODE					= "mode="; // always =json
	
	static String GET_DELIMITER = "&";
	
	@Override 
	protected void onPreExecute() {
		Log.d("TAG", "We've starte request");
	}

	@Override
	protected Void doInBackground(String... arg0) {
		
		
		
		//String apiURL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&cnt=10&mode=json";
		
		String apiURL = arg0[0];
		
		
		String result = null;
		
		try {
			URL u = new URL(apiURL);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			c.setRequestProperty("Content-length", "0");
			c.setUseCaches(false);
			c.connect();
			
			int status = c.getResponseCode();
			
			InputStream inputStream = null;
			switch (status) {
			case 200:
			case 201:
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
				StringBuilder sb = new StringBuilder();
				
				String line = null;
			    while ((line = br.readLine()) != null)
			    {
			        sb.append(line + "\n");
			    }
			    
			    Log.e("TAG", sb.toString());
			    result = sb.toString();
				
				br.close();

				this.readJSON(result);
				
			}
			
		} catch (MalformedURLException ex) {
			
		} catch (IOException ex) {
			
		}
		
		return null;
	}
	
	private void readJSON(String json) {
		
		try {
			JSONObject jObject = new JSONObject(json);
			JSONArray jArray = jObject.getJSONArray("list");
			
			Double doubleRain = jArray.getJSONObject(2).getDouble("rain");
			
			Log.e("MYTAG", "rain" + doubleRain);
		} catch (JSONException e) {
			Log.e("TAG", e.toString());
		}
		
	}
	
	
	public DayWeather requestBasicWeatherForToday(Location location) {
		
		
		
		String requestURL = 
				   String.format(SERVER + 
				  LATITUDE_PARAM_NAME  	+ "%f" + GET_DELIMITER + 
				  LONGITUDE_PARAM_NAME 	+ "%f" + GET_DELIMITER +  
				  CNT_DAYS				+ "%f" + GET_DELIMITER + 
				  MODE					+ "json", 
				  location.getLatitude(),
				  location.getLongitude(),
				  10.0);
		
		
		this.execute(requestURL);
		
		///this.doInBackground(requestURL);
		
		location.getLatitude();
		location.getLongitude();
		
		return new DayWeather();
	}
	
	
	

}
