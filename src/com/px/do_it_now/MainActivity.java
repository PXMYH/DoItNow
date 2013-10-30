package com.px.do_it_now;

import java.util.GregorianCalendar;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements android.view.View.OnClickListener {

	static final int CONFIRM = 1;
	// create public static variables year/month/day/hour/min to synchronize user date/time setting
	public static int set_year  = -1;
	public static int set_month = -1;
	public static int set_day   = -1;
	public static int set_hour  = -1;
	public static int set_min   = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// find buttons on the main GUI
		// find add reminder button
		Button confirm = (Button) findViewById (R.id.confirmButton);
		confirm.setOnClickListener(this);
		
		// add time picker button
		Button timePicker = (Button) findViewById(R.id.timePickerButton);
		timePicker.setOnClickListener(this);
		
		// add date picker button
		Button datePicker = (Button) findViewById (R.id.datePickerButton);
		datePicker.setOnClickListener(this);
	
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.confirmButton: 
			displayDialog(v);
			break;
		case R.id.timePickerButton:
			showTimePicker(v);
			break;
		case R.id.datePickerButton:
			showDatePicker(v);
		}
	}
	
	// display the confirmation dialog
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
				store_data_entry(c);
				scheduleAlarm(c);
				dialog.dismiss();
			}
		});
		
		AlertDialog alert_dialog = dialog_builder.create();
		alert_dialog.show();
	}
	
	
	// schedule an alert 
	public void scheduleAlarm (Context c) {
		
		// create alarm object
		AlarmManager alarmManager = (AlarmManager) c.getSystemService (Context.ALARM_SERVICE);
		
		// calculate alarm and notification delay time
		long delay_sec = Utilities.delay_time_calc(set_year, set_month, set_day, set_hour, set_min);
		Log.i("Delay info", "delay_sec is " + delay_sec);
		
		// set the alarm time to be 20s after the data entry has been recorded
		long delay_time = new GregorianCalendar().getTimeInMillis() + delay_sec*1000;
		
		// create intent when alarm triggers
		Intent alarm_trigger = new Intent (this, AlarmReciever.class);
		
		// define the pending intent action
		PendingIntent pending_action = PendingIntent.getBroadcast(this, 1, alarm_trigger, PendingIntent.FLAG_UPDATE_CURRENT);
		
		// activate the alarm manager
        alarmManager.set(AlarmManager.RTC_WAKEUP,delay_time, pending_action);
        
        // report status 
        Utilities.showMessage ("BOMB is SET xD", this.getApplicationContext());
	}

	// stores to do initiatives user typed in
	public void store_data_entry (Context c) {
		// locate the input text field and retrieve initiative info
		EditText editText = (EditText) findViewById (R.id.initiative);
		String initiative_content = editText.getText().toString();
		
		// locate the initiative list field and display the initiative user sets
		TextView initiative_list = (TextView) findViewById(R.id.Initiative_List);
		initiative_list.setText(initiative_content);
	
	}
	
	public void showTimePicker(View v) {
	    DialogFragment timePickerFragment = new TimePicker();
	    timePickerFragment.show(getFragmentManager(), "timePicker");
	}
	
	public void showDatePicker(View v) {
		DialogFragment datePickerFragment = new DatePicker();
		datePickerFragment.show(getFragmentManager(), "datePicker");
	}
	
//  this method is defined in Utilities.java to ensure accessibility for all classes
//	public void showMessage (CharSequence text) {
//		int duration = Toast.LENGTH_SHORT;
//		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
//		toast.show();    	
//    }

}
