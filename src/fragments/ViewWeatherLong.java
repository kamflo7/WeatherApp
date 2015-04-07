package fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import models.DetailedDayWeather;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;

public class ViewWeatherLong extends Fragment {    
    private DetailedDayWeather[] model = null;
    private int startIndex;
    private boolean viewIsDestroy = true;
    
    private ListView listView;
    private TextView city;
    private LongRowAdapter adapter;
    
    public void setModel(DetailedDayWeather[] arr, int start) {
    	List<DetailedDayWeather> weathersDaily = new ArrayList<DetailedDayWeather>();
    	
    	Calendar mydate = Calendar.getInstance();
    	int prevDay = -1;
    	
    	for(int i=start; i<arr.length; i++) {
    		mydate.setTimeInMillis(arr[i].timestamp*1000);
    		if(mydate.get(Calendar.DAY_OF_MONTH) != prevDay) {
    			if(mydate.get(Calendar.HOUR_OF_DAY) >= 10) {
    				weathersDaily.add(arr[i]);
    				prevDay = mydate.get(Calendar.DAY_OF_MONTH);
    				Log.d("test", String.format("lp:%d day:%d hour:%d", i, mydate.get(Calendar.DAY_OF_MONTH), mydate.get(Calendar.HOUR_OF_DAY)));
    			}
    		}
    	}
    	
    	
    	model = weathersDaily.toArray(new DetailedDayWeather[weathersDaily.size()]);
    	startIndex = start;
    	updateView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.activity_view_long, container, false);
        
        Intent intent = new Intent(MainActivity.INTENT_ACTION);
        intent.putExtra("fragmentName", MainActivity.FRAGMENT_WEATHER_LONG);
        getActivity().sendBroadcast(intent);
        
        listView = (ListView) rootView.findViewById(R.id.longListView);
        city = (TextView) rootView.findViewById(R.id.longCity);
        viewIsDestroy = false;
        
        if(model != null) updateView();
        
        return rootView;
    }
    
    @Override
    public void onDestroyView() {
    	super.onDestroyView();
    	viewIsDestroy = true;
    }
    
    private void updateView() {
    	if(viewIsDestroy) return;
    	
    	adapter = new LongRowAdapter(this.getActivity().getApplicationContext(), R.layout.long_item_layout, model);
        listView.setAdapter(adapter);
        city.setText(model[0].city);
    }
}
