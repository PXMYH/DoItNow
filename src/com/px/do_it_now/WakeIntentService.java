package com.px.do_it_now;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

public abstract class WakeIntentService extends IntentService {
	
		private static PowerManager.WakeLock lockStatic = null;
		public static final String LOCK_NAME_TAG = "alarm on";
	
		// constructor
	    public WakeIntentService(String name) {
	        super(name);
	    }
	    
	    @Override
	    final protected void onHandleIntent(Intent intent) {
	        try {
	        	activateAlarm(intent);
	        	activateNotification(intent);
	        	activateLED(intent);
	        } finally {
	        	// release the wake lock under any circumstances
	            getLock(this).release();
	        }
	    }
	    
	    // acquire a wake lock and force the device to stay on the level requested
	    public static void acquireStaticLock(Context context) {
	        getLock(context).acquire();
	    }
	    
	    
	    // get the wake lock status 
	    synchronized private static PowerManager.WakeLock getLock(Context c) {
	        if (lockStatic == null) {
	        	// get the power manager service
	            PowerManager powManager = (PowerManager) c.getSystemService(Context.POWER_SERVICE);
	            
	            // check the screen lock status
	            lockStatic = powManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,LOCK_NAME_TAG);
	            
	            // equal number of release calls to balance each lock acquire call
	            lockStatic.setReferenceCounted(true);
	        }
	        return (lockStatic);
	    }
	    
	    // execute the alarm service
	    abstract void activateAlarm(Intent intent);
	    
	    // execute notification service
	    abstract void activateNotification (Intent intent);
	    
	    // execute LED indication service
	    abstract void activateLED (Intent intent);
	    
	    
}