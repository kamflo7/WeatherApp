package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;

public class ViewWeatherNow extends Fragment {
    public static final String ARG_OBJECT = "object";

    
    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.activity_view_now, container, false);
        
        Intent intent = new Intent(MainActivity.INTENT_ACTION);
        intent.putExtra("fragmentName", MainActivity.FRAGMENT_WEATHER_NOW);
        getActivity().sendBroadcast(intent);
        
        ((TextView) rootView.findViewById(R.id.textView3)).setText("TESTOWO!!");
        
        return rootView;
        
    }
}