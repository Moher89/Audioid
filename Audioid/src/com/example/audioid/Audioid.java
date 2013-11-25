package com.example.audioid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;

public class Audioid extends Activity  {
	//public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audioid);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.audioid, menu);
        return true;
    }
    
//    public void setMessage(View view) {
//    	Intent intent = new Intent(this, DisplayMessageActivity.class);
//    	EditText editText = (EditText) findViewById(R.id.edit_message);
//    	String message = editText.getText().toString();
//    	intent.putExtra(EXTRA_MESSAGE, message);
//    	startActivity(intent);
//    }
    
    public void getPatientData(View view) {
    	Intent intent = new Intent(this, GetPatientDataMenu.class);
    	
    	finish();
    	startActivity(intent);
    }
    
    public void loadPatientData(View view) {
    	Intent intent = new Intent(this, LoadPatientDataMenu.class);
    	
    	finish();
    	startActivity(intent);
    }
    
    public void exit(View view) {
    	android.os.Process.killProcess(android.os.Process.myPid());
    }
}