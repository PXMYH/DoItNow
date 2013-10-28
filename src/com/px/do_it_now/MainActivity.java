package com.px.do_it_now;

import java.util.GregorianCalendar;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements android.view.View.OnClickListener {

	static final int CONFIRM = 1;
	private Button confirm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		confirm = (Button) findViewById (R.id.confirm);
		confirm.setOnClickListener(this);
	
	}
	

	private void displayDialog (View v) {
		// get context
		final Context c = v.getContext();
		
		AlertDialog.Builder dialog_builder = new AlertDialog.Builder(this);
		dialog_builder.setMessage("Add this entry to your to do database?");
		
		dialog_builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				Utilities.showMessage("Data Canceling ... Waiting for your next order .. ", c);
				dialog.cancel();	
			}		

		});
		
		dialog_builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO transfer data entry to database for future actions
				Utilities.showMessage ("Data recording ... ", c);
				scheduleAlarm(c);
				Utilities.showMessage ("Data recorded.", c);
				dialog.dismiss();
			}
		});
		
		AlertDialog alert_dialog = dialog_builder.create();
		alert_dialog.show();
	}


	public void onClick(View v) {
		displayDialog(v);
	}
	
	public void scheduleAlarm (Context c) {
		
		// create alarm object
		AlarmManager alarmManager = (AlarmManager) c.getSystemService (Context.ALARM_SERVICE);
	
		// set the alarm time to be 20s after the data entry has been recorded
		long delay_time = new GregorianCalendar().getTimeInMillis()+7*1000;
		
		// create intent when alarm triggers
		Intent alarm_trigger = new Intent (this, AlarmReciever.class);
		
		// define the pending intent action
		PendingIntent pending_action = PendingIntent.getBroadcast(this, 1, alarm_trigger, PendingIntent.FLAG_UPDATE_CURRENT);
		
		// activate the alarm manager
        alarmManager.set(AlarmManager.RTC_WAKEUP,delay_time, pending_action);
        
        // report status 
        Utilities.showMessage ("BOMB is SET xD", this.getApplicationContext());
	}

	
//  this method is defined in Utilities.java to ensure accessibility for all classes
//	public void showMessage (CharSequence text) {
//		int duration = Toast.LENGTH_SHORT;
//		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
//		toast.show();    	
//    }

}
