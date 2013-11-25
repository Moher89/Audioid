package com.example.audioid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class GetPatientDataMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_patient_data_menu);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.get_patient_data_menu, menu);
		return true;
	}
	
	public void createNewPatient(View view){
    	EditText editText = (EditText) findViewById(R.id.GiveLoginTextArea);
    	String login = editText.getText().toString();
    	FileReadWrite fr = new FileReadWrite();
    	fr.createPatientFile(this, login);
    	Intent intent = new Intent(this, PatientMenu.class);
    	intent.putExtra("patientName", login);
    	
    	finish();
    	startActivity(intent);
	}
	
    public void getBack(View view) {
    	Intent intent = new Intent(this, Audioid.class);
    	
    	finish();
    	startActivity(intent);
    }

}
