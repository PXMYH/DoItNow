package com.px.do_it_now;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

	@Override
	public void onTimeSet(android.widget.TimePicker v, int hourOfDay, int minute) {
		// Do something with the time chosen by the user
		//CharSequence text = "Time is set to " + hourOfDay + " : " + minute;
		//Utilities.showMessage(text, v.getContext());
		MainActivity.set_hour = hourOfDay;
		MainActivity.set_min = minute;
	}
}