package com.example.audioid;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;

public class PTA extends PTAandUCL
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pt);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		name = "PTA";
		start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.pt, menu);
		return true;
	}
}
