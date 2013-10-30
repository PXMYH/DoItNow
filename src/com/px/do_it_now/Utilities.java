package com.px.do_it_now;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


public class Utilities {


	public static void showMessage (CharSequence text, Context c) {
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(c.getApplicationContext(), text, duration);
		toast.show();    	
	}

	// calculate notification delay time
	public static long delay_time_calc (int set_year, int set_month, int set_day, int set_hour, int set_min) {
		GregorianCalendar gre_calendar = new GregorianCalendar();
		
		long delay = -1;
		int day_in_year_set  = 365;
		int day_in_year_curr = 365;
		int day_in_month = -1;
		
		// get current date and time
		Calendar calendar = Calendar.getInstance();
		int curr_year  = calendar.get(Calendar.YEAR);
		int curr_month = calendar.get(Calendar.MONTH);
		int curr_day   = calendar.get(Calendar.DAY_OF_MONTH);
		int curr_hour  = calendar.get(Calendar.HOUR_OF_DAY);
		int curr_min   = calendar.get(Calendar.MINUTE);
		Log.i("delay_calc", "set year is " + set_year);
		Log.i("delay_calc", "set month is " + set_month);
		Log.i("delay_calc", "set day is " + set_day);
		Log.i("delay_calc", "set hour is " + set_hour);
		Log.i("delay_calc", "set min is " + set_min);
		Log.i("delay_calc", "year is " + curr_year);
		Log.i("delay_calc", "month is " + curr_month);
		Log.i("delay_calc", "day is " + curr_day);
		Log.i("delay_calc", "hour is " + curr_hour);
		Log.i("delay_calc", "min is " + curr_min);
		
		// P.X. algorithm needs to improve and correction
		// encode the delay time in second
		
		// determine if a year is leap year
		boolean leap_year = gre_calendar.isLeapYear(set_year);
		if (leap_year)  	day_in_year_set = 366;
		leap_year = gre_calendar.isLeapYear(set_year);
		if (leap_year)  	day_in_year_curr = 366;
		
		switch (set_month) {
			case Calendar.JANUARY  : day_in_month = 31; break;
			case Calendar.FEBRUARY : if (leap_year) day_in_month = 29; else day_in_month = 28; break;
			case Calendar.MARCH    : day_in_month = 31; break;
			case Calendar.APRIL    : day_in_month = 30; break;
			case Calendar.MAY      : day_in_month = 31; break;
			case Calendar.JUNE     : day_in_month = 30; break;
			case Calendar.JULY     : day_in_month = 31; break;
			case Calendar.AUGUST   : day_in_month = 31; break;
			case Calendar.SEPTEMBER: day_in_month = 30; break;
			case Calendar.OCTOBER  : day_in_month = 31; break;
			case Calendar.NOVEMBER : day_in_month = 30; break;
			case Calendar.DECEMBER : day_in_month = 31; break;
			default                : break;
		}
		
		Log.i("delay_calc", "day in month is " + day_in_month);
		Log.i("delay_calc", "day in year is " + day_in_year_set);
		
		long encode_set_time = set_year   * day_in_year_set   * 24 * 60 * 60 + 
                			   set_month  * day_in_month      * 24 * 60 * 60 + 
                			   set_day                        * 24 * 60 * 60 +
                			   set_hour                            * 60 * 60 +
                			   set_min                                  * 60 ;
		Log.i("delay_calc", "encode set time " + encode_set_time);
		
		switch (curr_month) {
		case Calendar.JANUARY  : day_in_month = 31; break;
		case Calendar.FEBRUARY : if (leap_year) day_in_month = 29; else day_in_month = 28; break;
		case Calendar.MARCH    : day_in_month = 31; break;
		case Calendar.APRIL    : day_in_month = 30; break;
		case Calendar.MAY      : day_in_month = 31; break;
		case Calendar.JUNE     : day_in_month = 30; break;
		case Calendar.JULY     : day_in_month = 31; break;
		case Calendar.AUGUST   : day_in_month = 31; break;
		case Calendar.SEPTEMBER: day_in_month = 30; break;
		case Calendar.OCTOBER  : day_in_month = 31; break;
		case Calendar.NOVEMBER : day_in_month = 30; break;
		case Calendar.DECEMBER : day_in_month = 31; break;
		default                : break;
		}	
		
		
		long  encode_curr_time = curr_year  * day_in_year_curr    * 24 * 60 * 60 + 
				                 curr_month * day_in_month        * 24 * 60 * 60 + 
				                 curr_day                         * 24 * 60 * 60 +
				                 curr_hour                             * 60 * 60 +
				                 curr_min                                   * 60 ;
		Log.i("delay_calc", "encode curr time " + encode_curr_time);
		
		delay = encode_set_time - encode_curr_time;
		Log.i("delay_calc", "delay time is " + delay);
		
		return delay;
	}
}