package com.example.audioid;

import java.io.IOException;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;

import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PTAandUCL extends Activity {
	String name; //name of actual procedure (PTA vs UCL)
	SoundPool soundPool = null;
	int id = -1;
	boolean ifStop = false; //temporarily to stop the procedure
	boolean ifRight = true; //which ear is analyzed now
	int whichHz = 0; //element from HzPoints table
	int whichDB = 40; //dB value
	int pointNmb = 9; //number of elements in HzPoints table
	double[] HzPoints = {1, 2, 3, 3.5, 4, 4.5, 5, 5.5, 6};
	int[] HzValues = {250, 500, 1000, 1500, 2000, 3000, 4000, 6000, 8000};
	double[][] leftEar = new double[pointNmb][2]; //9 measure points x 2 parameters (Hz, dB)
	double[][] rightEar = new double[pointNmb][2];
	GraphViewData[] axisHolder = new GraphViewData[] {new GraphViewData(0, -10), new GraphViewData(7, 100)};
	double pX = HzPoints[whichHz], pY = getDB(whichDB);
	
	private int getDB(int x)
	{
		return 90-x;
	}
	
	public void start()
	{
		for(int i=0; i<pointNmb; i++)
		{
			leftEar[i][0] = HzPoints[i];
			leftEar[i][1] = getDB(40);
			rightEar[i][0] = HzPoints[i];
			rightEar[i][1] = getDB(40);
		}
		errorMessg("Plug on your headphones - right ear first");
		createGraph();
		playMusic(String.valueOf(HzValues[whichHz])+"_"+whichDB+".wav");
	}
	
	private void createGraph()
	{
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
		
		GraphViewData[] pointNowData1 = new GraphViewData[2];
		pointNowData1[0] = new GraphViewData(pX-0.25, pY+5);
		pointNowData1[1] = new GraphViewData(pX+0.25, pY-5);
		GraphViewData[] pointNowData2 = new GraphViewData[2];
		pointNowData2[0] = new GraphViewData(pX-0.25, pY-5);
		pointNowData2[1] = new GraphViewData(pX+0.25, pY+5);
		
		GraphViewSeries axisHolderSerie = new GraphViewSeries("", new GraphViewSeriesStyle(Color.TRANSPARENT, 0), axisHolder);
		GraphViewSeries leftEarSerie = new GraphViewSeries("Left Ear", new GraphViewSeriesStyle(Color.BLUE, 3), leftEarData);
		GraphViewSeries rightEarSerie = new GraphViewSeries("Right Ear", new GraphViewSeriesStyle(Color.RED, 3), rightEarData);
		GraphViewSeries pointNowSerie1 = new GraphViewSeries("Point Now", new GraphViewSeriesStyle(Color.GREEN, 3), pointNowData1);
		GraphViewSeries pointNowSerie2 = new GraphViewSeries("Point Now", new GraphViewSeriesStyle(Color.GREEN, 3), pointNowData2);

		GraphView graphView = new LineGraphView(this, "PTA");
		graphView.addSeries(axisHolderSerie);
		graphView.addSeries(leftEarSerie);
		graphView.addSeries(rightEarSerie);
		graphView.addSeries(pointNowSerie1);
		graphView.addSeries(pointNowSerie2);
		
		graphView.setHorizontalLabels(new String[] {"125", "250", "500", "1000", "2000", "4000", "8000", "[Hz]"});
		graphView.setVerticalLabels(new String[] {"[dBHL]", "0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100"});
		//											100    90   80    70    60    50    40    30    20    10    0     -10   
		graphView.getGraphViewStyle().setGridColor(Color.GRAY);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
		graphView.getGraphViewStyle().setTextSize(10);
		graphView.getGraphViewStyle().setNumHorizontalLabels(8);
		graphView.getGraphViewStyle().setNumVerticalLabels(12);
		
		RelativeLayout layout = (RelativeLayout) (findViewById(R.id.HearingPlot));
		layout.addView(graphView);
	}
	
	public void hear(View view)
	{
		makeChange(-10);
	}
	
	public void cantHear(View view)
	{
		makeChange(10);
	}
	
	public void makeChange(int delta)
	{
		if(!ifStop)
		{
			if((delta == -10 && whichDB > 0) || (delta == 10 && whichDB < 90))
			{
				whichDB = whichDB+delta;
				if(ifRight)
				{
					rightEar[whichHz][1] = getDB(whichDB);
				}
				else
				{
					leftEar[whichHz][1] = getDB(whichDB);
				}
				pY = getDB(whichDB);
				RelativeLayout layout = (RelativeLayout) (findViewById(R.id.HearingPlot));
				layout.removeAllViews();
				createGraph();
				playMusic(String.valueOf(HzValues[whichHz])+"_"+whichDB+".wav");
			}
			else
			{
				hearingLimit(null);
			}
		}
	}
	
	public void hearingLimit(View view)
	{
		if(whichHz == pointNmb-1)
		{
			if(ifRight)
			{
				errorMessg("Left ear now");
				ifRight = false;
				whichHz = 0;
				whichDB = 40;
				pY = getDB(whichDB);
				pX = HzPoints[whichHz];
				RelativeLayout layout = (RelativeLayout) (findViewById(R.id.HearingPlot));
				layout.removeAllViews();
				createGraph();
				playMusic(String.valueOf(HzValues[whichHz])+"_"+whichDB+".wav");
			}
			else if(!ifStop)
			{
				ifStop = true;
				soundPool.stop(id);
				soundPool.release();
				Bundle extras = getIntent().getExtras();
				FileReadWrite frw = new FileReadWrite();
		    	frw.saveResults(this, extras.getString("patientName"), name, leftEar, rightEar);
		    	errorMessg("Results saved");
			}
		}
		else
		{
			whichHz++;
			whichDB = 40;
			pY = getDB(whichDB);
			pX = HzPoints[whichHz];
			RelativeLayout layout = (RelativeLayout) (findViewById(R.id.HearingPlot));
			layout.removeAllViews();
			createGraph();
			playMusic(String.valueOf(HzValues[whichHz])+"_"+whichDB+".wav");
		}
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
		        	Toast.makeText(getApplicationContext(), "Procedure start", Toast.LENGTH_SHORT);
		        }
		});
		alertDialog.show();
	}
	
    @SuppressLint("NewApi")
	private void playMusic(String filepath)
    {
    	if(id != -1)
    	{
    		soundPool.stop(id);
    		soundPool.release();
    	}
    	
    	final float leftVolume, rightVolume;
    	if(ifRight)
    	{
    		leftVolume = 1;
    		rightVolume = 0;
    	}
    	else
    	{
    		leftVolume = 0;
    		rightVolume = 1;
    	}
    	
    	soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

    	AssetFileDescriptor afd = null;
        try
        {
			afd = getAssets().openFd(filepath);
	        final int idLoad = soundPool.load(afd, 1);
	        soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener()
	        {
	            public void onLoadComplete(SoundPool arg0, int arg1, int arg2)
	            {
	                id = soundPool.play(idLoad, leftVolume, rightVolume, 1, -1, 1f);
	            };
	        });
	        afd.close();
        }
        catch (IOException e1)
        {
			e1.printStackTrace();
		}
    }
    
	public void getBack(View view)
    {
    	soundPool.stop(id);
    	soundPool.release();
    	finish();
    }
}