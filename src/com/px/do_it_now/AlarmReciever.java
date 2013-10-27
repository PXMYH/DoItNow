package com.px.do_it_now;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReciever extends BroadcastReceiver
{
         	@Override
            public void onReceive(Context context, Intent intent)
            {
                   	Utilities.showMessage("Alarm Triggered", context);
                    WakeIntentService.acquireStaticLock(context);
                    Log.i("AlarmReciever", "After wake Intent Service");
                    Intent alert = new Intent(context, AlarmService.class);
                    Log.i("AlarmReciever", "After intent alert");
                    context.startService(alert);
                    Log.i("AlarmReciever", "Service is started");
            }
      
}