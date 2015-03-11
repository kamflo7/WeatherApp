package fragments;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewWeatherLong extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.activity_view_long, container, false);
        
        Intent intent = new Intent(MainActivity.INTENT_ACTION);
        intent.putExtra("fragmentName", this.getClass().getName());
        getActivity().sendBroadcast(intent);
        
        return rootView;
    }
}
