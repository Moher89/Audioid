package com.example.audioid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

public class UCL extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ucl);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ucl, menu);
		return true;
	}
	
	public void addPoint(View view){
		EditText editText = (EditText) findViewById(R.id.GiveX);
    	String xS = editText.getText().toString();
    	editText = (EditText) findViewById(R.id.GiveY);
    	String yS = editText.getText().toString();
    	int x, y;
    	try
    	{
    		x = Integer.parseInt(xS);
    		y = Integer.parseInt(yS);
    		//x = 1;
    		//y = 2;
    		createGraph(x, y);
    	}
    	catch(NumberFormatException ex)
    	{
    		errorMessg();
    	}
	}

	private void errorMessg()
	{
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		// Setting Dialog Title
		alertDialog.setTitle("Alert");
		// Setting Dialog Message
		alertDialog.setMessage("X and Y must be  integers");
		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.abc_ab_bottom_solid_dark_holo);
		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		        // Write your code here to execute after dialog closed
		        Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT);
		        }
		});
		// Showing Alert Message
		alertDialog.show();
	}
	
	private void createGraph(int x, int y)
	{
		// init example series data
		GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
		      new GraphViewData(x, y)
		      , new GraphViewData(x+1, y+2)
		      , new GraphViewData(x+3, y-1)
		});
		 
		GraphView graphView = new LineGraphView(
		      this // context
		      , "GraphViewDemo" // heading
		);
		graphView.addSeries(exampleSeries); // data
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
		graphView.getGraphViewStyle().setTextSize(10);
		graphView.getGraphViewStyle().setNumHorizontalLabels(5);
		graphView.getGraphViewStyle().setNumVerticalLabels(4);
		//graphView.getGraphViewStyle().setVerticalLabelsWidth(300);
		
		RelativeLayout layout = (RelativeLayout) (findViewById(R.id.graph_image));
		layout.addView(graphView);
	}
	
}
