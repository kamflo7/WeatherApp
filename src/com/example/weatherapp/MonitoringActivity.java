package com.example.weatherapp;

import java.util.Collection;

import org.altbeacon.beacon.*;

import com.example.weatherapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;



public class MonitoringActivity extends Activity implements BeaconConsumer {
    protected static final String TAG = "MonitoringActivity";
    private BeaconManager beaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        BeaconParser estimoteParser = new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24");
        
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(estimoteParser);
        beaconManager.bind(this);
        
      
        
    }
    @Override 
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }
    @Override
    public void onBeaconServiceConnect() {
    	
    	
    	beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override 
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    Log.i(TAG, "The first beacon I see is about "+beacons.iterator().next().getDistance()+" meters away.");        
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {    }
    	
    	
    	
    	
        beaconManager.setMonitorNotifier(new MonitorNotifier() {
        @Override
        public void didEnterRegion(Region region) {
            Log.e(TAG, "I just saw an beacon for the first time!");        
        }

        @Override
        public void didExitRegion(Region region) {
            Log.e(TAG, "I no longer see an beacon");
        }

        @Override
            public void didDetermineStateForRegion(int state, Region region) {
            Log.e(TAG, "I have just switched from seeing/not seeing beacons: "+state);        
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("b9407f30-f5f8-466e-aff9-25556b57fe6d", null, null, null));
        } catch (RemoteException e) {    }
    }

}