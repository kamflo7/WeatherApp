package com.example.weatherapp;

import android.graphics.Bitmap;

public class WeekForecast {
	
	private String  clouds;
	private Bitmap image;
	private float temp, wind;
	
	
	
	public double getTemp() {
		return temp-273.15;
	}
	public void setTemp(float temp) {
		this.temp = temp;
	}
	public String getClouds() {
		return clouds;
	}
	public void setClouds(String clouds) {
		this.clouds = clouds;
	}
	public double getWind() {
		return wind*1.609344;
	}
	public void setWind(float wind) {
		this.wind = wind;
	}
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
}
