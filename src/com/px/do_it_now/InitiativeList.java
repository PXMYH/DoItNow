package com.px.do_it_now;

import java.util.ArrayList;


import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class InitiativeList extends ListActivity {
	// List of array strings will serve as a list items
	ArrayList<String> listItems = new ArrayList<String>();
	
	// string adapter which will handle the data of the listview
	ArrayAdapter<String> listAdapter;
	
	EditText editText = (EditText) findViewById (R.id.initiative);
	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
		// set up initative list in the main GUI
		ListView initiative_list = (ListView) findViewById (R.id.Initiatives);
        listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        initiative_list.setAdapter(listAdapter);
	}
	
	// dynamic list member addition method
    public void addItems(View v) {
        listItems.add(editText.getText().toString());
        listAdapter.notifyDataSetChanged();
    }
}