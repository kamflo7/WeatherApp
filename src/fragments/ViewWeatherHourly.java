package fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import models.DetailedDayWeather;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class ViewWeatherHourly extends Fragment {
	private DetailedDayWeather[] model = null;
	private boolean viewIsDestroy = true;
    
    private ListView listView;
    private TextView city;
    private HourlyRowAdapter adapter;
    
    
    public void setModel(DetailedDayWeather[] arr, int start) {
    	List<DetailedDayWeather> weathersHourly = new ArrayList<DetailedDayWeather>();
    	
    	Calendar mydate = Calendar.getInstance();
    	mydate.setTimeInMillis(arr[start].timestamp*1000);
    	
    	int dayToday = mydate.get(Calendar.DAY_OF_MONTH);
    	
    	for(int i=start; i<arr.length; i++) {
    		mydate.setTimeInMillis(arr[i].timestamp*1000);
    		
    		if(mydate.get(Calendar.DAY_OF_MONTH) == dayToday) {
    			weathersHourly.add(arr[i]);
    			Log.d("test", String.format("[hourly] lp:%d day:%d hour:%d", i, mydate.get(Calendar.DAY_OF_MONTH), mydate.get(Calendar.HOUR_OF_DAY)));
    		} else {
    			break;
    		}
    	}
    	
    	model = weathersHourly.toArray(new DetailedDayWeather[weathersHourly.size()]);
    	Log.d("test", "[Hourly] model.count="+model.length);
    	updateView();
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.activity_view_hourly, container, false);
        
        Intent intent = new Intent(MainActivity.INTENT_ACTION);
        intent.putExtra("fragmentName", MainActivity.FRAGMENT_WEATHER_HOURLY);
        getActivity().sendBroadcast(intent);
        
        listView = (ListView) rootView.findViewById(R.id.hourlyListView);
        city = (TextView) rootView.findViewById(R.id.hourlyCity);
        viewIsDestroy = false;
        
        if(model != null) updateView();
        
        return rootView;
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	viewIsDestroy = true;
    }
    
    private void updateView() {
    	if(viewIsDestroy) return;
    	
    	adapter = new HourlyRowAdapter(this.getActivity().getApplicationContext(), R.layout.long_item_layout, model);
        listView.setAdapter(adapter);
        city.setText(model[0].city);
        Log.d("test", "[hourly] updateView called");
    }
}
