package com.example.audioid;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class LoadPatientDataMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load_patient_data_menu);
//		TableLayout table = (TableLayout) findViewById(R.id.patientTable);
//
		List<String> patientNames = new FileReadWrite().givePatients(this);
		int patientNmb = patientNames.size();
//		
//		for (int i=0; i<patientNmb; i++) {
//		    LayoutInflater inflater = LayoutInflater.from(LoadPatientDataMenu.this);
//		    TableRow row = (TableRow) inflater.inflate(R.layout.patient_row, null);
//
//		    View view = LayoutInflater.from(getApplication()).inflate(R.layout.patient_row, null);
//		    TextView text = (TextView) view.findViewById(R.id.patientName);
//		    text.setText(patientNames.get(i));
//
//		    table.addView(row);
//		}
		
//	    TableLayout tl = (TableLayout)findViewById(R.id.patientTable);   
//	    
//	    for (int i=0;i<patientNmb;i++) {   
//		    TableRow tr = new TableRow(this);   
//		    tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));  
//		    Button b = new Button(this);   
//		    b.setText(patientNames.get(i));   
//		    b.setLayoutParams(new LayoutParams(   
//		    LayoutParams.FILL_PARENT,   
//		    LayoutParams.WRAP_CONTENT));   
//	        tr.addView(b);     
//		    tl.addView(tr,new TableLayout.LayoutParams(   
//		    LayoutParams.FILL_PARENT,   
//		    LayoutParams.WRAP_CONTENT));  
//	    }  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.load_patient_data_menu, menu);
		return true;
	}
	
    public void getBack(View view) {
    	Intent intent = new Intent(this, Audioid.class);
    	
    	finish();
    	startActivity(intent);
    }

}
