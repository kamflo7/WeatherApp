package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;


public class DayWeatherRequest extends AsyncTask<String, String, DetailedDayWeather[]> {

	public interface OnDayWeatherRequestCompleted {
		void onDayWeatherRequestCompleted(DetailedDayWeather[] result);
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
	protected void onPostExecute(DetailedDayWeather[] result) {
		this.listener.onDayWeatherRequestCompleted(result);
	}

	@Override
	protected DetailedDayWeather[] doInBackground(String... arg0) {
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
			Log.d("test", "API: " + apiURL);
			
			int status = c.getResponseCode();
			
			Log.i(TAG, status + " STATUS " + status);
			
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
				
				DetailedDayWeather[] dayWeather = this.interpretJsonDataToArrayOfDayWeather(requestedJsonString);
				return dayWeather;
			}
		} catch (MalformedURLException ex) {
			Log.i("TAG", "MalformedURLException");
		} catch (IOException ex) {
			Log.i("TAG", "IOException");
		}
		return null;
	}
	
	private DetailedDayWeather[] interpretJsonDataToArrayOfDayWeather(String jsonData) {
		ArrayList<DetailedDayWeather> dayWeatherArrayList = new ArrayList<DetailedDayWeather>();
		try {
			JSONObject jObject = new JSONObject(jsonData);
			JSONArray jArray = jObject.getJSONArray("list");
			Log.d("test", "City: " + jObject.getJSONObject("city").getString("name"));
			
			for(int i = 0; i < jArray.length(); i++) {
				JSONObject row = jArray.getJSONObject(i);
				dayWeatherArrayList.add(this.parseDayWeather(row));
			}
		} catch (JSONException e) {
			Log.e("TAG", e.toString());
		}
		return dayWeatherArrayList.toArray(new DetailedDayWeather[dayWeatherArrayList.size()]);
	}
	

	private DetailedDayWeather parseDayWeather(JSONObject jsObject) {
		DetailedDayWeather requestedDay = new DetailedDayWeather();
		try {
			requestedDay.temp = (float) (jsObject.getJSONObject("temp").getDouble("day") - 273.15); //(uugly)
			
			// Getting type of weather - very wague - we don't use a lot of cases
			int typeId = jsObject.getJSONArray("weather").getJSONObject(0).getInt("id");
			
			DayWeather.Type typeOfWeather = null;
			if(typeId == 800) {
				typeOfWeather = DayWeather.Type.CLEAR_SKY;
			} else if(typeId == 801) {
				typeOfWeather = DayWeather.Type.FEW_CLOUDS;
			} else if(typeId == 802) {
				typeOfWeather = DayWeather.Type.SCATTERED_CLOUDS;
			} else if(typeId == 803) {
				typeOfWeather = DayWeather.Type.BROKEN_CLOUDS;
			} else if(typeId >= 804) {
				typeOfWeather = DayWeather.Type.FEW_CLOUDS;
			} else if(typeId >= 700) {
				typeOfWeather = DayWeather.Type.MIST;
			} else if(typeId >= 600) {
				typeOfWeather = DayWeather.Type.SNOW;
			} else if(typeId >= 300) {				// Also Drizzle
				typeOfWeather = DayWeather.Type.RAIN;
			} else if(typeId >= 200) {
				typeOfWeather = DayWeather.Type.THUNDERSTORM;
			}
			
			requestedDay.type = typeOfWeather;
			//Log.d("TAG",  "typeOfWeather" +typeId);
			// Getting the windSpeed
			requestedDay.timestamp = jsObject.getInt("dt");
 			
		} catch (JSONException e) {
			Log.d("TAG", "[DayWeatherRequest::parseDayWeather] Catch: " + e.getMessage());
		}
		
		requestedDay.windSpeed = (float) getDoubleJSON(jsObject, "speed");
		requestedDay.humidity  = (float) getDoubleJSON(jsObject, "humidity");
		requestedDay.cloudPercentage = (float) getDoubleJSON(jsObject, "clouds");
		requestedDay.pressure = (float) getDoubleJSON(jsObject, "pressure");
		requestedDay.rainMinimeters = (float) getDoubleJSON(jsObject, "rain");
		requestedDay.snowMinimeters = (float) getDoubleJSON(jsObject, "snow");

		
		return requestedDay;
	}
	
	private double getDoubleJSON(JSONObject object, String key) {
		double res = 0.0d;
		if(object.isNull(key)) res = 0.0d;
		else {
			try {
				res = object.getDouble(key);
			} catch (JSONException e) {
				Log.d("test", "[DayWeatherRequest::getDoubleJson] " + e.getMessage());
			}
		}
		return res;
	}
	
	public void requestWeatherForLocationForAmountOfDays(Location location, Integer daysCount) {
		
		String latitude = Double.toString(location.getLatitude()).replace(',', '.');
		String longitude = Double.toString(location.getLongitude()).replace(',', '.');
		
		String requestURL = 
				   String.format(SERVER + 
				  LATITUDE_PARAM_NAME  	+ "%s" + GET_DELIMITER + 
				  LONGITUDE_PARAM_NAME 	+ "%s" + GET_DELIMITER +  
				  CNT_DAYS				+ "%f" + GET_DELIMITER + 
				  MODE					+ "json", 
				  latitude,
				  longitude,
				  (float) daysCount);
		
		
		this.execute(requestURL);
	}
	
	
	
	

}

