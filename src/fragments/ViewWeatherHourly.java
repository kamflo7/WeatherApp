package fragments;

import models.DetailedDayWeather;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class ViewWeatherHourly extends Fragment {
	private DetailedDayWeather[] model = null;
    private int startIndex;
    
    public void setModel(DetailedDayWeather[] arr, int start) {
    	model = arr;
    	startIndex = start;
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.activity_view_hourly, container, false);
        
        Intent intent = new Intent(MainActivity.INTENT_ACTION);
        intent.putExtra("fragmentName", MainActivity.FRAGMENT_WEATHER_HOURLY);
        getActivity().sendBroadcast(intent);
        
        return rootView;
    }
}
