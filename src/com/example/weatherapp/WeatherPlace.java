package com.example.weatherapp;

import android.location.Location;

public class WeatherPlace {
	public String locationName;
	public Location location;

//	public WeatherPlace(String locationName, Location location) {
//		this.locationName = locationName;
//		this.location = location;
//	}
	
	public WeatherPlace(String locationName, double latitude, double longitude) {
		this.locationName = locationName;
		Location location = new Location("");
		location.setLatitude(latitude);
		location.setLongitude(longitude);
		this.location = location;
	}
}
