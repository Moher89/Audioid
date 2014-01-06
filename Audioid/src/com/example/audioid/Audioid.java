package com.example.audioid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;

public class Audioid extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audioid);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.audioid, menu);
        return true;
    }
    
    public void getPatientData(View view) //create a new patient
    {
    	Intent intent = new Intent(this, GetPatientDataMenu.class);
    	
    	finish();
    	startActivity(intent);
    }
    
    public void loadPatientData(View view) //load already created patient
    {
    	Intent intent = new Intent(this, LoadPatientDataMenu.class);
    	
    	finish();
    	startActivity(intent);
    }
    
    public void exit(View view) //leave the program
    {
    	android.os.Process.killProcess(android.os.Process.myPid());
    }
}