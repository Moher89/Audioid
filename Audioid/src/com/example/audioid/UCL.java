package com.example.audioid;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;

public class UCL extends PTAandUCL
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ucl);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		name = "UCL";
		start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.ucl, menu);
		return true;
	}	
}
