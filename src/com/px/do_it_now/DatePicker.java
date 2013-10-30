package com.px.do_it_now;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;


public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
	@Override
	public void onDateSet (android.widget.DatePicker v, int year, int month, int day) {
		// Do something with the date chosen by the user
		CharSequence text = "Bomb is set to " + year + "/" + month + "/" + day;
		Utilities.showMessage(text, v.getContext());
		MainActivity.set_year  = year;
		MainActivity.set_month = month;
		MainActivity.set_day   = day;
	}

}