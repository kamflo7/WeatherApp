package fragments;

import models.DetailedDayWeather;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;

public class ViewWeatherNow extends Fragment {
    public static final String ARG_OBJECT = "object";

    public ImageView imageViewNow;
    public TextView nowTemperature, nowWindSpeed, nowCloudPercentage, nowSnow, nowRain, nowPressure, nowHumidity;
	
    private DetailedDayWeather model;
    
    public void setModel(DetailedDayWeather detailedWeather) {
    	model = detailedWeather;
    	updateView();
    }
    
    private void updateView() {
		nowTemperature.setText(model.temp + "°C");
		
		nowWindSpeed.setText(Float.toString(model.windSpeed));
		nowCloudPercentage.setText(Float.toString(model.cloudPercentage));
		nowSnow.setText(Float.toString(model.snowMinimeters));
		nowRain.setText(Float.toString(model.rainMinimeters));
		nowPressure.setText(Float.toString(model.pressure));
		nowHumidity.setText(Float.toString(model.humidity));
		
		switch(model.type) {
		case CLEAR_SKY:	imageViewNow.setImageResource(R.drawable.clear_sky_day); break;
		case BROKEN_CLOUDS:	imageViewNow.setImageResource(R.drawable.broken_clouds_day); break;
		case FEW_CLOUDS: imageViewNow.setImageResource(R.drawable.few_clouds_day); break;
		case MIST: imageViewNow.setImageResource(R.drawable.mist_day); break;
		case RAIN: imageViewNow.setImageResource(R.drawable.rain_day); break;
		case SCATTERED_CLOUDS: imageViewNow.setImageResource(R.drawable.scattered_clouds_day); break;
		case SHOWER_RAIN: imageViewNow.setImageResource(R.drawable.show_rain_day); break;
		case SNOW: imageViewNow.setImageResource(R.drawable.snow_day); break;
		case THUNDERSTORM: imageViewNow.setImageResource(R.drawable.thunderstorm_day); break;
		}
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.activity_view_now, container, false);
        
        Intent intent = new Intent(MainActivity.INTENT_ACTION);
        intent.putExtra("fragmentName", MainActivity.FRAGMENT_WEATHER_NOW);
        getActivity().sendBroadcast(intent);
        
        imageViewNow = (ImageView) rootView.findViewById(R.id.weatherView);
		nowTemperature = (TextView) rootView.findViewById(R.id.nowTemperature);
		nowWindSpeed = (TextView) rootView.findViewById(R.id.nowWindSpeed);
		nowCloudPercentage = (TextView) rootView.findViewById(R.id.nowCloudPercentage);
		nowSnow = (TextView) rootView.findViewById(R.id.nowSnow);
		nowRain = (TextView) rootView.findViewById(R.id.nowRain);
		nowPressure = (TextView) rootView.findViewById(R.id.nowPressure);
		nowHumidity = (TextView) rootView.findViewById(R.id.nowHumidity);
		
		if(model != null) updateView();
        return rootView;
        
    }
}