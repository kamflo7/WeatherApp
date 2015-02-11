package com.example.weatherapp;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherViewAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<WeekForecast> forecasts ; 




	public WeatherViewAdapter(Context context,LayoutInflater mInflater,
			List<WeekForecast> objects) {
		mInflater = LayoutInflater.from(context);
		forecasts = objects;
	}	
	
	@Override
	public int getCount() {
		return 0;
	}
	@Override
	public Object getItem(int position) {
		return  forecasts.get(position);
	}
	@Override
	public long getItemId(int position) {
		return 0;
	}
	@Override
	public View getView(int position, View convertView, 
			ViewGroup parent) {
		
		WHolder holder;
		View view;
		
		if(convertView== null){
			view  = mInflater.inflate(R.layout.activity_view_hourly, parent, false);
			holder = new WHolder();
			holder.cloudsView = (TextView)view.findViewById(R.id.cloudsView);
			holder.tempView = (TextView)view.findViewById(R.id.tempView);
			holder.image = (ImageView)view.findViewById(R.id.weatherView);
			holder.windView = (TextView) view.findViewById(R.id.windView);
			
			view.setTag(holder);
		}else{
			view = convertView;
			holder = (WHolder)view.getTag();
		}
		WeekForecast item = forecasts.get(position);
		holder.cloudsView.setText(item.getClouds()+"% ");
		holder.tempView.setTag(item.getTemp()+"Celcius ");
		holder.windView.setText(item.getWind()+"km/h ");
		holder.image.setImageBitmap(item.getImage());
		
		
		
		return view;
		
	}
	public class WHolder{
		public TextView  cloudsView , tempView, windView; 
	public ImageView image;
	}
}

