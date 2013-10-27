package com.px.do_it_now;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;


public class AlarmService extends WakeIntentService {
	
	// Constructor 
	public AlarmService () {
		super ("Activate Alarm");
	}

	@Override
	void activateNotification(Intent intent) {
		// get system notification manager service 
		NotificationManager notifier = (NotificationManager) getSystemService (NOTIFICATION_SERVICE);
		
		Intent notificationIntent = new Intent (this, MainActivity.class);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, notificationIntent, PendingIntent.FLAG_ONE_SHOT);
		
		// create notification content
		Notification note_content = new Notification(R.drawable.ic_launcher, "DO IT NOW!", System.currentTimeMillis());
		
		note_content.setLatestEventInfo(this, "Title", "Text", pendingIntent);
		note_content.defaults |= Notification.DEFAULT_ALL;
		note_content.flags |= Notification.FLAG_AUTO_CANCEL;
	    int id = 123456789;
	    notifier.notify(id, note_content);
	    Log.i("AlarmService", "Notification service started");
	}
	
	@Override
	void activateAlarm(Intent intent) {
		Log.i("AlarmService", "Empty");
	}

	@Override
	void activateLED(Intent intent) {
		// TODO Auto-generated method stub
		
	}
}