package com.px.do_it_now;

import android.content.Context;
import android.widget.Toast;


public class Utilities {


	public static void showMessage (CharSequence text, Context c) {
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(c.getApplicationContext(), text, duration);
		toast.show();    	
	}

}