package fragments;

import com.example.weatherapp.R;

import models.DetailedDayWeather;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LongRowAdapter extends ArrayAdapter<DetailedDayWeather> {
	
	private Context context;
	private int layoutResourceID;
	private DetailedDayWeather[] data;
	private int startIdx = 0;
	
	public LongRowAdapter(Context context, int layoutResourceID, DetailedDayWeather[] data, int startIdx) {
		super(context, layoutResourceID, data);
		this.context = context;
		this.layoutResourceID = layoutResourceID;
		this.data = data;
		this.startIdx = startIdx;
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
		holder.titleView.setText(item.city);
		holder.tempView.setText(item.temp+"C");
		holder.rainView.setText(item.rainMinimeters+"mm");
		holder.windView.setText(item.windSpeed+"km/h");
		holder.humidityView.setText(item.humidity+"%");
		
//		holder.image.setImageResource(item.icon);
		
		return row;
	}
	
	static class RowHolder {
		ImageView image;
		TextView titleView, tempView, rainView, windView, humidityView;
	}
}
