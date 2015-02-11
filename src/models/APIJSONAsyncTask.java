package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;


public class APIJSONAsyncTask extends AsyncTask<String, String, Void> {

	@Override 
	protected void onPreExecute() {
		Log.d("TAG", "We've starte request");
	}

	@Override
	protected Void doInBackground(String... arg0) {
		String apiURL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&cnt=10&mode=json";
		
		
		try {
			URL u = new URL(apiURL);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			c.setRequestProperty("Content-length", "0");
			c.setUseCaches(false);
			c.connect();
			
			int status = c.getResponseCode();
			
			switch (status) {
			case 200:
			case 201:
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				
				while((line = br.readLine()) != null) {
					Log.d("TAG", line);
				}
				br.close();
			}
			
		} catch (MalformedURLException ex) {
			
		} catch (IOException ex) {
			
		}
		
		return null;
	}
	
	
	
	

}
