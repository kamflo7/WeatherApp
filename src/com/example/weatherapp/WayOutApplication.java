package com.example.weatherapp;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class WayOutApplication extends Application implements BootstrapNotifier {

	
	 private static final String TAG = ".MyApplicationName";
	    private RegionBootstrap regionBootstrap;
	    private BeaconManager beaconManager;
	    private Region region;
	    private BackgroundPowerSaver backgroundPowerSaver;

	    @Override
	    public void onCreate() {
	        super.onCreate();
	        Log.d(TAG, "App started up");
	        
	        
	        Log.d(TAG, "App started up");
	        beaconManager = BeaconManager.getInstanceForApplication(this);
	        beaconManager.setAndroidLScanningDisabled(true);
	        beaconManager.setBackgroundScanPeriod(5000l); 
	        beaconManager.setBackgroundBetweenScanPeriod(6000l);
	        beaconManager.setDebug(true);
	        
	        // Add AltBeacons Parser for iBeacon
	        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
	        // wake up the app when any beacon is seen (you can specify specific id filers in the parameters below)
	        region = new Region("com.rp_ds.chequeplease.bootstrapRegion", Identifier.parse("b9407f30-f5f8-466e-aff9-25556b57fe6d"), null, null);
	        regionBootstrap = new RegionBootstrap(this, region);
	        //backgroundPowerSaver = new BackgroundPowerSaver(this);
	       
	        //backgroundPowerSaver.
	        //_appPrefs = new AppPreferences(this);
	        
	        
	        // wake up the app when any beacon is seen (you can specify specific id filers in the parameters below)
	        //Region region = new Region("" , null, null, null);
	        //regionBootstrap = new RegionBootstrap(this, region);
	        
	    }

	    @Override
	    public void didDetermineStateForRegion(int arg0, Region arg1) {
	    	
	    	Log.d(TAG, "didDetermineStateForRegion");
	        // Don't care
	    }

	    @Override
	    public void didEnterRegion(Region arg0) {
	        Log.d(TAG, "Got a didEnterRegion call");
	    	this.showRainNotification(); // We just show notification, that when clicked will 
	    	
	        // This call to disable will make it so the activity below only gets launched the first time a beacon is seen (until the next time the app is launched)
	        // if you want the Activity to launch every single time beacons come into view, remove this call.  
	        //regionBootstrap.disable();
	        //Intent intent = new Intent(this, MonitoringActivity.class);
	        // IMPORTANT: in the AndroidManifest.xml definition of this activity, you must set android:launchMode="singleInstance" or you will get two instances
	        // created when a user launches the activity manually and it gets launched from here.
	        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        //this.startActivity(intent);
	    }

	    @Override
	    public void didExitRegion(Region arg0) {
	    	
	    	Log.d(TAG, "didExitRegion");
	        // Don't care
	    }    
	    
	    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	    
		private void showRainNotification() {
	    	NotificationCompat.Builder mBuilder =
	    	        new NotificationCompat.Builder(this)
	    	        .setSmallIcon(R.drawable.rain_day)
	    	        .setContentTitle(getString(R.string.take_umbrella_with_you))
	    	        .setContentText(getString(R.string.it_looks_like_its_going_to_rain));
	    	
	    	
	    	mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
	    	mBuilder.setDefaults(Notification.DEFAULT_SOUND);
	    	// Creates an explicit intent for an Activity in your app
	    	Intent resultIntent = new Intent(this, MonitoringActivity.class);

	    	// The stack builder object will contain an artificial back stack for the
	    	// started Activity.
	    	// This ensures that navigating backward from the Activity leads out of
	    	// your application to the Home screen.
	    	TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	    	// Adds the back stack for the Intent (but not the Intent itself)
	    	stackBuilder.addParentStack(MonitoringActivity.class);
	    	// Adds the Intent that starts the Activity to the top of the stack
	    	stackBuilder.addNextIntent(resultIntent);
	    	PendingIntent resultPendingIntent =
	    	        stackBuilder.getPendingIntent(
	    	            0,
	    	            PendingIntent.FLAG_UPDATE_CURRENT
	    	        );
	    	mBuilder.setContentIntent(resultPendingIntent);
	    	NotificationManager mNotificationManager =
	    	    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	    	// mId allows you to update the notification later on.
	    	mNotificationManager.notify(12, mBuilder.build());
	    }
	
	
	

}
