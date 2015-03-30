package fragments;

import models.DetailedDayWeather;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class LongRowAdapter extends ArrayAdapter<DetailedDayWeather> {
	
	private Context context;
	private int layoutResourceID;
	private DetailedDayWeather[] data;
	private int startIdx;
	
	public LongRowAdapter(Context context, int layoutResourceID, DetailedDayWeather[] data, int startIdx) {
		super(context, layoutResourceID, data);
		this.context = context;
		this.layoutResourceID = layoutResourceID;
		this.data = data;
		this.startIdx = startIdx;
	}
	
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		View row = convertView;
//		RowHolder holder = null;
//		
//		if(row == null) {
//			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//			row = inflater.inflate(layoutResourceID, parent, false);
//			
//			holder = new RowHolder();
//			holder.image = (ImageView) row.findViewById(R.id.imageView1);
//			holder.textView = (TextView) row.findViewById(R.id.textView1);
//			
//			row.setTag(holder);
//		} else {
//			holder = (RowHolder) row.getTag();
//		}
//		
//		ListRecordModel item = data[position];
//		holder.textView.setText(item.title);
//		holder.image.setImageResource(item.icon);
//		
//		return row;
//	}
//	
//	static class RowHolder {
//		ImageView image;
//		TextView tempView, rainView, windView;
//	}
}
