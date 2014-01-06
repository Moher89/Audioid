package com.example.audioid;

import java.util.List;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

public class ShowResult extends Activity
{
	int pointNmb = 9; //number of elements in HzPoints table
	double[][] leftEar = new double[pointNmb][2]; //9 measure points x 2 parameters (Hz, dB)
	double[][] rightEar = new double[pointNmb][2];
	GraphViewData[] axisHolder = new GraphViewData[] {new GraphViewData(0, -10), new GraphViewData(7, 100)}; //keeps the constant size of the axis
	String patientName, procedureName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_result);
		Bundle extras = getIntent().getExtras();
		List<double[][]> earData = null;
		if (extras != null) //get data about procedure to show
		{
			patientName = extras.getString("patientName");
			procedureName = extras.getString("procedureName");
			earData = new FileReadWrite().getEarData(this, patientName, procedureName);
		}
		createGraph(earData);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.show_result, menu);
		return true;
	}
	
	/**
	 * Create graph.
	 * @param earData - list with matrixes of data for both ears
	 */
	private void createGraph(List<double[][]> earData)
	{
		leftEar = earData.get(0);
		rightEar = earData.get(1);
		
		GraphViewData[] leftEarData = new GraphViewData[pointNmb];
		for(int i=0; i<pointNmb; i++)
		{
			leftEarData[i] = new GraphViewData(leftEar[i][0], leftEar[i][1]);
		}
		
		GraphViewData[] rightEarData = new GraphViewData[pointNmb];
		for(int i=0; i<pointNmb; i++)
		{
			rightEarData[i] = new GraphViewData(rightEar[i][0], rightEar[i][1]);
		}
		
		GraphViewSeries axisHolderSerie = new GraphViewSeries("", new GraphViewSeriesStyle(Color.TRANSPARENT, 0), axisHolder);
		GraphViewSeries leftEarSerie = new GraphViewSeries("Left Ear", new GraphViewSeriesStyle(Color.BLUE, 3), leftEarData);
		GraphViewSeries rightEarSerie = new GraphViewSeries("Right Ear", new GraphViewSeriesStyle(Color.RED, 3), rightEarData);

		String[] splitter = procedureName.split("-"); //get procedure name from its title
		GraphView graphView = new LineGraphView(this, splitter[1]);
		graphView.addSeries(axisHolderSerie);
		graphView.addSeries(leftEarSerie);
		graphView.addSeries(rightEarSerie);
		
		graphView.setHorizontalLabels(new String[] {"125", "250", "500", "1000", "2000", "4000", "8000", "[Hz]"});
		graphView.setVerticalLabels(new String[] {"[dBHL]", "0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100"});
		//											100    90   80    70    60    50    40    30    20    10    0     -10   
		graphView.getGraphViewStyle().setGridColor(Color.GRAY);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
		graphView.getGraphViewStyle().setTextSize(10);
		graphView.getGraphViewStyle().setNumHorizontalLabels(8);
		graphView.getGraphViewStyle().setNumVerticalLabels(12);
		
		RelativeLayout layout = (RelativeLayout) (findViewById(R.id.SavedPlot));
		layout.addView(graphView);
		Display display = getWindowManager().getDefaultDisplay();
		@SuppressWarnings("deprecation")
		RelativeLayout.LayoutParams layout_description = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, (int) (display.getHeight()*0.7));
		layout.setLayoutParams(layout_description);
	}
	
    public void getBack(View view)
    {
    	Bundle extras = getIntent().getExtras();
    	Intent intent = new Intent(this, History.class);
		if (extras != null)//give back the patient name
		{
			intent.putExtra("patientName", extras.getString("patientName"));
		}
    	
    	finish();
    	startActivity(intent);
    }
}
