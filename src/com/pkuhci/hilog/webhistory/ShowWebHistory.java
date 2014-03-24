package com.pkuhci.hilog.webhistory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.pkuhci.hilog.R;
import com.pkuhci.hilog.ShowRecord;


public class ShowWebHistory extends Activity {

	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.show_webhistory_layout);//set the first view to welcome the user

		TextView tv =(TextView)this.findViewById(R.id.user_info);              
		 
		tv.setText(ShowRecord.showAllWebHistory);
		
		
			Button demoButton =(Button) this.findViewById(R.id.back_button);
			demoButton.setOnClickListener(new OnClickListener(){
				//By pressing the button on this screen, user can go to the second screen to input data.
				
				public void onClick(View arg0){
					
					
					Intent i = new Intent(
							ShowWebHistory.this,
							ShowRecord.class);

					startActivity(i);
					//Toast.makeText(getApplicationContext(), "·µ»Ø", Toast.LENGTH_SHORT).show();
				}
			});
			
		

		
	}
	
	
}
