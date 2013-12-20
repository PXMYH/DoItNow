/**
 * App main GUI components:
 * one entry line
 * one "Energize" button
 */

package com.px.do_it_now;

import java.util.ArrayList;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	/* ********************
	 * Variable declaration
	 * ********************/
	
	static final int CONFIRM = 1;
	// create public static variables year/month/day/hour/min to synchronize user date/time setting
	// TODO: change the public variable structure later, not good practice
	public static int set_year  = -1;
	public static int set_month = -1;
	public static int set_day   = -1;
	public static int set_hour  = -1;
	public static int set_min   = -1;
	
	// List of array strings will serve as a list items
	ArrayList<String> listItems = new ArrayList<String>();
	
	// string adapter which will handle the data of the listview
	ArrayAdapter<String> listAdapter;
	
	// instance variables
	private Button energize;
	private EditText type_in;
	private ListView initiative_list;
	
	// class variables
	
	
	/* ********
	 * OnCreate
	 * ********/
	
	// creates user view once the app is launched
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// initialization
		init();
		
		// set up "ENERGIZE" button functionality
		setEnergButton();
	}

	// assign functionalities to button "Energize"
	@Override
	public void onClick(View v) {
		
		// pick date
		showDatePicker(v);
		
		// pick time
		showTimePicker(v);
		
		// ask user for confirmation
		displayDialog(v);
	}
	
	/* ***************
	 * Private methods 
	 * ***************/
	
	// Function: locate all resources on the main GUI
	private void init() {
		
		// find the ENERGIZE button
		energize = (Button) findViewById (R.id.energizeButton);
		
		// locate initiative entry bar
		type_in = (EditText) findViewById (R.id.typeIn);		
		
		// locate initiative list 
		initiative_list  = (ListView) findViewById (R.id.initiativeList);	
		//initiative_list.setBackgroundColor(FF1493);
	}
	
	
	// Function: set up  "ENERGIZE" button main functions: 
	// 1. invoke Date/Time picker dialogue
	// 2. confirmation dialogue --> add entry to list
	//                          --> abandon entry
	private void setEnergButton () {
		energize.setOnClickListener(this);
	}
	
	
	// display the confirmation dialog
	private void displayDialog (View v) {
		// get context
		final Context c = v.getContext();
		
		// configure dialogue builder
		AlertDialog.Builder dialog_builder = new AlertDialog.Builder(this);
		dialog_builder.setMessage("Save initiative?");
		
		dialog_builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
			// cancel operation
			@Override
			public void onClick(DialogInterface dialog, int id) {
				Utilities.showMessage("Cencelled", c);
				dialog.cancel();	
			}		
		});
		
		dialog_builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
			// confirm saving
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO transfer data entry to database for future actions
				//Utilities.showMessage ("Saved", c);
				setInitiativeList();
				scheduleAlarm(c);
				dialog.dismiss();
			}
		});
		
		// create and display dialogue builder
		AlertDialog alert_dialog = dialog_builder.create();
		alert_dialog.show();
	}
	
	
	// Function: transfer user input initiative to initiative list
	private void setInitiativeList() {

		// define list adapter
        listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        initiative_list.setAdapter(listAdapter);
        
        // save entry to list
        String entry = type_in.getText().toString() + "    " + set_year + "/" + set_month + "/" + set_day + "  " + set_hour + ":" + set_min;
		listItems.add(entry);
		listAdapter.notifyDataSetChanged();
		
		// clear entry
		clearEntry();
	}
	
	// clear the entry input field
	private void clearEntry () {
		type_in.setText("");
	}
	
	
	// schedule an alert 
	private void scheduleAlarm (Context c) {
		
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
        Utilities.showMessage ("Alarm is SET", this.getApplicationContext());
	}

	

	// construct time picker dialog
	private void showTimePicker(View v) {
	    DialogFragment timePickerFragment = new TimePicker();
	    timePickerFragment.show(getFragmentManager(), "timePicker");
	}
	
	// construct date picker dialog
	private void showDatePicker(View v) {
		DialogFragment datePickerFragment = new DatePicker();
		datePickerFragment.show(getFragmentManager(), "datePicker");
	}
	

}
