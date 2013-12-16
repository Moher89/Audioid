package com.example.audioid;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
    	if(!fr.createPatientFile(this, login))
    	{
    		errorMessg("Patient already exists");
    		return;
    	}
    	else
    	{
    		errorMessg("Patient created");
    	}
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
    
	@SuppressWarnings("deprecation")
	private void errorMessg(String text)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Alert");
		alertDialog.setMessage(text);
		alertDialog.setIcon(R.drawable.abc_ab_bottom_solid_dark_holo);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener()
		{
		        @SuppressLint("ShowToast")
				public void onClick(DialogInterface dialog, int which)
		        {
		        	Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT);
		        }
		});
		alertDialog.show();
	}

}
