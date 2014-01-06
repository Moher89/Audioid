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
	/**
	 * Create data files for new patient.
	 * @param ctx - application context
	 * @param patientName - name of the patient to create
	 * @return if creation was done properly (if patient didn't exist)
	 */
	public boolean createPatientFile(Context ctx, String patientName)
	{
		try
		{	
			//add patient to the list of patients:
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
			
			//create patient's file:
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
	
	/**
	 * Save results of the procedure for specific patient.
	 * @param ctx - application context
	 * @param patientName - patient name
	 * @param procedureName - procedure name
	 * @param leftEar - data for the left ear
	 * @param rightEar - data for the right ear
	 */
	public void saveResults(Context ctx, String patientName, String procedureName, double[][] leftEar, double[][] rightEar)
	{
		FileOutputStream outputStream;
		Calendar c = Calendar.getInstance();
		String point, title;

		try
		{
			//Every data entry has the same template
			outputStream = ctx.openFileOutput(patientName, Context.MODE_APPEND);
			//Firstly the title: "Title: hour/minute/second/day/month/year - procedureName\n"
			title = "Title: " + 
					String.valueOf(c.get(Calendar.HOUR)) + "/" +
					String.valueOf(c.get(Calendar.MINUTE)) + "/" +
					String.valueOf(c.get(Calendar.SECOND)) + "/" +
					String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + "/" +
					String.valueOf(c.get(Calendar.MONTH)) + "/" +
					String.valueOf(c.get(Calendar.YEAR)) + " - " +
					procedureName + "\n";
			outputStream.write(title.getBytes());
			//Secondly the data for the left ear: "Left ear: x1;y1-x2;y2-x3;y3-...\n"
			outputStream.write("LeftEar: ".getBytes());
			for(int i=0; i<leftEar.length; i++)
			{
				point = String.valueOf(leftEar[i][0]) + ";" + String.valueOf(leftEar[i][1]) + "-";
				outputStream.write(point.getBytes());
			}
			outputStream.write("\n".getBytes());
			//Thirdly the data for the right ear: "Right ear: x1;y1-x2;y2-x3;y3-...\n"
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
	
	/**
	 * Get list of patients.
	 * @param ctx -application context
	 * @return list of patients
	 */
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
	
	/**
	 * Get procedures history of chosen patient.
	 * @param ctx - application context
	 * @param patientName - name of the chosen patient
	 * @return List of procedures titles.
	 */
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

	/**
	 * Get results of the chosen procedure of the chosen patient.
	 * @param ctx - application context
	 * @param patientName - name of the chosen patient
	 * @param procedureName - title of the chosen procedure
	 * @return List of data for left and right ears
	 */
	public List<double[][]> getEarData(Context ctx, String patientName, String procedureName)
	{
		List<double[][]> earData = new ArrayList<double[][]>();
		double[][] tempEar;
		String line;
		int pointNmb;
		String[] splitter, splitter2;
		try
		{
			FileInputStream fis = ctx.openFileInput(patientName);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		    if (fis!=null)
		    {                            
		        while ((line = reader.readLine()) != null)
		        {
		        	if(line.contains(procedureName)) //if the chosen title was found
		        	{
		        		//left ear first:
		        		line = reader.readLine();
		        		line = line.substring(line.indexOf(":")+2);
		        		//split by data points:
		        		splitter = line.split("-");
		        		pointNmb = splitter.length;
		        		tempEar = new double[pointNmb][2];
		        		for(int i=0; i<pointNmb; i++)
		        		{
		        			//split by x and y:
		        			splitter2 = splitter[i].split(";");
		        			tempEar[i][0] = Double.parseDouble(splitter2[0]);
		        			tempEar[i][1] = Double.parseDouble(splitter2[1]);
		        		}
		        		earData.add(tempEar);
		        		
		        		//right ear second:
		        		line = reader.readLine();
		        		line = line.substring(line.indexOf(":")+2);
		        		//split by data points:
		        		splitter = line.split("-");
		        		pointNmb = splitter.length;
		        		tempEar = new double[pointNmb][2];
		        		for(int i=0; i<pointNmb; i++)
		        		{
		        			//split by x and y:
		        			splitter2 = splitter[i].split(";");
		        			tempEar[i][0] = Double.parseDouble(splitter2[0]);
		        			tempEar[i][1] = Double.parseDouble(splitter2[1]);
		        		}
		        		earData.add(tempEar);
		        		
		        		break;	
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
		
		return earData;
	}
}
