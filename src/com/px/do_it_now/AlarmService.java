package com.px.do_it_now;

//import android.app.Notification;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class AlarmService extends WakeIntentService {
	
	public static final int GREEN = 0xff00ff00;
	
	// Constructor 
	public AlarmService () {
		super ("Activate Alarm");
	}

	// activate notification bar service
	@Override
	void activateNotification(Intent intent) {
		// get system notification manager service 
		NotificationManager notifier = (NotificationManager) getSystemService (NOTIFICATION_SERVICE);
		
		// intent declaration
		// P.X. opens a fresh new main activity which is not intended, change later
		Intent notificationIntent = new Intent (this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		// create notification content
		NotificationCompat.Builder notifier_builder = new NotificationCompat.Builder(this);
		notifier_builder.setSmallIcon(R.drawable.ic_launcher);
		notifier_builder.setContentTitle("Do It NOW!");
		notifier_builder.setContentText("You have one initiative to finish NOW!");
		notifier_builder.setDefaults(Notification.DEFAULT_SOUND);
		notifier_builder.setContentIntent(pendingIntent);
		notifier_builder.setWhen(System.currentTimeMillis());

	    // set notification LED light color -- the screen must be locked and dimed to see the LED effect
	    notifier_builder.setLights(0xFF0000, 250, 200);
	    
//		Notification note_content = new Notification(R.drawable.ic_launcher, "DO IT NOW!", System.currentTimeMillis());
//		note_content.setLatestEventInfo(this, "Title", "Text", pendingIntent);
//		note_content.defaults |= Notification.DEFAULT_ALL;
//		note_content.flags |= Notification.FLAG_AUTO_CANCEL;
		
		// set notification ID
	    int note_id = 0x00000001;
	    
	    notifier.notify(note_id, notifier_builder.build());
	    Log.i("AlarmService", "Notification service started");
	}
	
	
	// activate alarm ringtong service
	@Override
	void activateAlarm(Intent intent) {
		Log.i("AlarmService", "Empty");
	}

}