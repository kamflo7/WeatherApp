package fragments;

import java.util.Calendar;

import models.DetailedDayWeather;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;

public class HourlyRowAdapter extends ArrayAdapter<DetailedDayWeather> {
	
	private Context context;
	private int layoutResourceID;
	private DetailedDayWeather[] data;
	private Calendar calendar;
	
	public HourlyRowAdapter(Context context, int layoutResourceID, DetailedDayWeather[] data) {
		super(context, layoutResourceID, data);
		this.context = context;
		this.layoutResourceID = layoutResourceID;
		this.data = data;
		calendar = Calendar.getInstance();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RowHolder holder = null;
		
		if(row == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );//((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceID, parent, false);
			
			holder = new RowHolder();
			holder.image = (ImageView) row.findViewById(R.id.listItemImage);
			holder.titleView = (TextView) row.findViewById(R.id.listItemTitle);
			holder.tempView = (TextView) row.findViewById(R.id.listTempView);
			holder.rainView = (TextView) row.findViewById(R.id.listRainView);
			holder.windView = (TextView) row.findViewById(R.id.listWindView);
			holder.humidityView = (TextView) row.findViewById(R.id.listHumidityView);
			
			row.setTag(holder);
		} else {
			holder = (RowHolder) row.getTag();
		}
		
		
		DetailedDayWeather item = data[position];
		calendar.setTimeInMillis(item.timestamp*1000);
		
		holder.tempView.setText(String.format("%.1f\u2103", item.temp));
		holder.rainView.setText(item.rainMinimeters+"mm");
		holder.windView.setText(item.windSpeed+"km/h");
		holder.humidityView.setText(item.humidity+"%");
		
		holder.titleView.setText(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
		
		switch(item.type) {
		case CLEAR_SKY:	holder.image.setImageResource(R.drawable.clear_sky_day); break;
		case BROKEN_CLOUDS:	holder.image.setImageResource(R.drawable.broken_clouds_day); break;
		case FEW_CLOUDS: holder.image.setImageResource(R.drawable.few_clouds_day); break;
		case MIST: holder.image.setImageResource(R.drawable.mist_day); break;
		case RAIN: holder.image.setImageResource(R.drawable.rain_day); break;
		case SCATTERED_CLOUDS: holder.image.setImageResource(R.drawable.scattered_clouds_day); break;
		case SHOWER_RAIN: holder.image.setImageResource(R.drawable.show_rain_day); break;
		case SNOW: holder.image.setImageResource(R.drawable.snow_day); break;
		case THUNDERSTORM: holder.image.setImageResource(R.drawable.thunderstorm_day); break;
		}
		
		return row;
	}
	
	static class RowHolder {
		ImageView image;
		TextView titleView, tempView, rainView, windView, humidityView;
	}
}
