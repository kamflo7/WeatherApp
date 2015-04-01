package fragments;

import models.DetailedDayWeather;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    
    private ListView listView;
    private TextView city;
    private LongRowAdapter adapter;
    
    public void setModel(DetailedDayWeather[] arr, int start) {
    	model = arr;
    	startIndex = start;
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
        
        if(model != null) updateView();
        
        return rootView;
    }
    
    private void updateView() {
    	adapter = new LongRowAdapter(this.getActivity().getApplicationContext(), R.layout.long_item_layout, model, startIndex);
        listView.setAdapter(adapter);
    }
}
