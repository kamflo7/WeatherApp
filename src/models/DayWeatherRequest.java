package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;




public class DayWeatherRequest extends AsyncTask<String, String, DayWeather[]> {

	public interface OnDayWeatherRequestCompleted {
		void onDayWeatherRequestCompleted(DayWeather[] result);
	}
	
	private OnDayWeatherRequestCompleted listener;
	static String TAG 					= "APIJSONAsyncTask";
	static String SERVER 				= "http://api.openweathermap.org/data/2.5/forecast/daily?";
	static String LATITUDE_PARAM_NAME 	= "lat=";
	static String LONGITUDE_PARAM_NAME 	= "lon=";
	static String CNT_DAYS 				= "cnt=";
	static String MODE					= "mode="; // always =json
	
	static String GET_DELIMITER = "&";
	
	
	public DayWeatherRequest(OnDayWeatherRequestCompleted listener) {
		this.listener = listener;
	}
	
	@Override 
	protected void onPreExecute() {
		Log.d("TAG", "We've starte request");
	}
	
	
	
	@Override
	protected void onPostExecute(DayWeather[] result) {
		this.listener.onDayWeatherRequestCompleted(result);
	}

	@Override
	protected DayWeather[] doInBackground(String... arg0) {
		//String apiURL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&cnt=10&mode=json";
		
		
		
		Log.d("TAG", "Do in background");
		String apiURL = arg0[0];
		
		
		String requestedJsonString = null;
		
		try {
			
			
			URL u = new URL(apiURL);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			c.setRequestProperty("Content-length", "0");
			c.setUseCaches(false);
			c.connect();
			
			int status = c.getResponseCode();
			
			Log.i(TAG, status + " STATUS " + status);
			
			InputStream inputStream = null;
			
			switch (status) {
			case 200:
			case 201:
				
				Log.d("TAG", "200 or 201");
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
				StringBuilder sb = new StringBuilder();
				
				String line = null;
			    while ((line = br.readLine()) != null)
			    {
			        sb.append(line + "\n");
			    }
			    
			    
			    requestedJsonString = sb.toString();
			    Log.e("TAG", sb.toString());
				br.close();
				
				DayWeather[] dayWeather = this.interpretJsonDataToArrayOfDayWeather(requestedJsonString);
				Log.i(TAG, dayWeather[1].temp + "");
				return dayWeather;
				
			}
			
		} catch (MalformedURLException ex) {
			
			Log.i("TAG", "MalformedURLException");
			
		} catch (IOException ex) {
			
			Log.i("TAG", "IOException");
			
		}
		
		return null;
	}
	
	private DayWeather[] interpretJsonDataToArrayOfDayWeather(String jsonData) {
		ArrayList<DayWeather> dayWeatherArrayList = new ArrayList<DayWeather>();
		try {
			JSONObject jObject = new JSONObject(jsonData);
			JSONArray jArray = jObject.getJSONArray("list");
			
			for(int i = 0; i < jArray.length(); i++) {
				JSONObject row = jArray.getJSONObject(i);
				dayWeatherArrayList.add(this.parseDayWeather(row));
			}
			
		} catch (JSONException e) {
			Log.e("TAG", e.toString());

		}
		
		return dayWeatherArrayList.toArray(new DayWeather[dayWeatherArrayList.size()]);
		
		
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
	
	
	
	private DayWeather parseDayWeather(JSONObject jsObject) {
		
		DayWeather requestedDay = new DayWeather();
		try {
			
			
			requestedDay.temp = (float) (jsObject.getJSONObject("temp").getDouble("day") - 273.0); //(uugly)
			
			String typeString = jsObject.getJSONObject("weather").getJSONObject("0").getString("main");
			
			DayWeather.Type typeOfWeather = null;
					
		
			requestedDay.type = DayWeather.Type.BROKEN_CLOUDS;
			
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return requestedDay;
	}
	
	
	public void requestWeatherForLocationForAmountOfDays(Location location, Integer daysCount) {
		
		String requestURL = 
				   String.format(SERVER + 
				  LATITUDE_PARAM_NAME  	+ "%f" + GET_DELIMITER + 
				  LONGITUDE_PARAM_NAME 	+ "%f" + GET_DELIMITER +  
				  CNT_DAYS				+ "%f" + GET_DELIMITER + 
				  MODE					+ "json", 
				  location.getLatitude(),
				  location.getLongitude(),
				  (float) daysCount);
		
		
		this.execute(requestURL);
	}
	
	public DayWeather requestBasicWeatherForToday(Location location) {
		
		/*
		
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
		*/
		///this.doInBackground(requestURL);
		
		
		return new DayWeather();
	}
	
	
	

}

