package com.example.audioid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class PatientMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_menu);
		TextView patientName = (TextView) findViewById(R.id.PatientName);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    patientName.setText("Welcome " + extras.getString("patientName") + "!"); 
		}
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient_menu, menu);
		return true;
	}
	
	public void runPTA(View view){
		Intent intent = new Intent(this, PTA.class);
    	
    	//finish();
    	startActivity(intent);
	}
	
	public void runUCL(View view){
		Intent intent = new Intent(this, UCL.class);
    	
    	//finish();
    	startActivity(intent);
	}
	
    public void getBack(View view){
    	Intent intent = new Intent(this, Audioid.class);
    	
    	finish();
    	startActivity(intent);
    }
}
