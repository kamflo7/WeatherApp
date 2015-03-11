package fragments;

import com.example.weatherapp.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewWeatherNow extends Fragment {
    public static final String ARG_OBJECT = "object";

    
    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.activity_view_now, container, false);
        return rootView;
        
    }
}