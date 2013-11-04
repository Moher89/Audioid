package com.example.audioid;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class GetPatientDataMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_patient_data_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.get_patient_data_menu, menu);
		return true;
	}
	
    public void getBack(View view) {
    	finish();
    }

}
