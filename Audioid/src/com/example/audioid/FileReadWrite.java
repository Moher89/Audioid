package com.example.audioid;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Context;

public class FileReadWrite extends Activity
{
	public boolean createPatientFile(Context ctx, String patientName)
	{
		try
		{
			FileOutputStream outputStream = ctx.openFileOutput("patients", Context.MODE_APPEND);
			
			String line;
			FileInputStream fis = ctx.openFileInput("patients");
		    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		    if (fis!=null)
		    {                            
		        while ((line = reader.readLine()) != null)
		        {    
		            if(line.equals(patientName))
		            {
		            	fis.close();
		            	outputStream.close();
		            	return false;
		            }
		        }               
		    }       
		    fis.close();
			
			outputStream.write(patientName.getBytes());
			outputStream.write("\n".getBytes());
			outputStream.close();
			
			outputStream = ctx.openFileOutput(patientName, Context.MODE_PRIVATE);
			outputStream.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return true;
	}
	
	public void saveResults(Context ctx, String patientName, String procedureName, double[][] leftEar, double[][] rightEar)
	{
		FileOutputStream outputStream;
		Calendar c = Calendar.getInstance();
		String point, title;

		try
		{
			outputStream = ctx.openFileOutput(patientName, Context.MODE_APPEND);
			title = "Title: " + 
					String.valueOf(c.get(Calendar.HOUR)) + "/" +
					String.valueOf(c.get(Calendar.MINUTE)) + "/" +
					String.valueOf(c.get(Calendar.SECOND)) + "/" +
					String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + "/" +
					String.valueOf(c.get(Calendar.MONTH)) + "/" +
					String.valueOf(c.get(Calendar.YEAR)) + " - " +
					procedureName + "\n";
			outputStream.write(title.getBytes());
			
			outputStream.write("LeftEar: ".getBytes());
			for(int i=0; i<leftEar.length; i++)
			{
				point = String.valueOf(leftEar[i][0]) + ";" + String.valueOf(leftEar[i][1]) + "-";
				outputStream.write(point.getBytes());
			}
			outputStream.write("\n".getBytes());
			
			outputStream.write("RightEar: ".getBytes());
			for(int i=0; i<rightEar.length; i++)
			{
				point = String.valueOf(rightEar[i][0]) + ";" + String.valueOf(rightEar[i][1]) + "-";
				outputStream.write(point.getBytes());
			}
			outputStream.write("\n".getBytes());
			
			outputStream.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public List<String> getPatients(Context ctx)
	{
		List<String> patientNames = new ArrayList<String>();
		String line;
		try
		{
			FileInputStream fis = ctx.openFileInput("patients");
		    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		    if (fis!=null)
		    {                            
		        while ((line = reader.readLine()) != null)
		        {    
		        	patientNames.add(line);
		        }               
		    }       
		    fis.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return patientNames;
	}
	
	public List<String> getHistory(Context ctx, String patientName)
	{
		List<String> procedures = new ArrayList<String>();
		String line;
		try
		{
			FileInputStream fis = ctx.openFileInput(patientName);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		    if (fis!=null)
		    {                            
		        while ((line = reader.readLine()) != null)
		        {
		        	if(line.contains("Title:"))
		        	{
		        		line = line.substring(line.indexOf(":")+2);
			        	procedures.add(line);
		        	}
		        }               
		    }       
		    fis.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return procedures;
	}
}
