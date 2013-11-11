package com.example.audioid;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;

public class FileReadWrite extends Activity
{
	public void createPatientFile(Context ctx, String fileName) {
		try {
//			File mydir = ctx.getDir("data", Context.MODE_PRIVATE); //Creating an internal dir;
//			File fileWithinMyDir = new File(mydir, fileName+".txt"); //Getting a file within the dir.
//			FileOutputStream out = new FileOutputStream(fileWithinMyDir); //Use the stream as usual to write into the file
			
			//OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ctx.openFileOutput(fileName+".txt", Context.MODE_PRIVATE));
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ctx.openFileOutput(fileName+".txt", Context.MODE_PRIVATE));
			outputStreamWriter.close();
			
			outputStreamWriter = new OutputStreamWriter(ctx.openFileOutput("patients.txt", Context.MODE_PRIVATE));
			outputStreamWriter.append(fileName);
			outputStreamWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> givePatients(Context ctx){
		List<String> patientNames = new ArrayList<String>();
		InputStream inputStream;
		try {
			inputStream = ctx.openFileInput("patients.txt");
			if (inputStream != null) {
		          InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		          BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		          String line;
		          while ((line = bufferedReader.readLine()) != null) {
		        	  patientNames.add(line);
		          }
		          inputStream.close();
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return patientNames;
	}
	
//	public void writeFileFromInternalStorage(String fileName) {
//		BufferedWriter writer = null;
//		try {
//			writer = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, Context.MODE_WORLD_WRITEABLE)));
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (writer != null) {
//				try {
//					writer.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
	
//	public void readFileFromInternalStorage(String fileName) {
//		BufferedReader input = null;
//		try {
//			input = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
//			String line;
//			StringBuffer buffer = new StringBuffer();
//			while ((line = input.readLine()) != null) {
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (input != null) {
//				try {
//					input.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
}
