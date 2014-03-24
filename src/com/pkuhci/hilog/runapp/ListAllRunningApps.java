package com.pkuhci.hilog.runapp;

import java.util.List;

import com.pkuhci.hilog.About;
import com.pkuhci.hilog.R;
import com.pkuhci.hilog.ShowRecord;
import com.pkuhci.hilog.WelcomeScreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ListAllRunningApps extends Activity {
	private RunningAppDBHelper db =new RunningAppDBHelper(this);;
private ListView listview;
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.show_app_oprt_record);// set the first view to welcome the user
	
	
	 List<String> list = db.listAllRecordInDB();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.adapter_item_1, list);
		listview = (ListView) findViewById(R.id.list01);
		listview.setAdapter(adapter);

		 Button saveAppOperationBtn=(Button)this.findViewById(R.id.back);
		 saveAppOperationBtn.setOnClickListener(new OnClickListener(){
	      	
	        	 public void onClick(View arg0){
	        		 Intent i = new Intent(ListAllRunningApps.this, ShowRecord.class);

	 				startActivity(i);
	        			
	         		
	           	 }
	    		
	      	 
	        });
		
		
	}
}
