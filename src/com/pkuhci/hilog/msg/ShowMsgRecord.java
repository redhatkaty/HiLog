package com.pkuhci.hilog.msg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.pkuhci.hilog.R;
import com.pkuhci.hilog.ShowRecord;

/*
 * 显示短信记录并可以将短信记录以xml格式保存在sd卡中或系统默认路径下的画面
 */

public class ShowMsgRecord extends Activity {

	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.user_info_layout);//set the first view to welcome the user

		TextView tv =(TextView)this.findViewById(R.id.user_info);              
		 
		tv.setText(ShowRecord.msgRecord); 
		
		
			Button demoButton =(Button) this.findViewById(R.id.back_button);
			demoButton.setOnClickListener(new OnClickListener(){
				//By pressing the button on this screen, user can go to the second screen to input data.
				
				public void onClick(View arg0){
					
					
					Intent i = new Intent(
							ShowMsgRecord.this,
							ShowRecord.class);

					startActivity(i);
					//Toast.makeText(getApplicationContext(), "返回", Toast.LENGTH_SHORT).show();
				}
			});
			


		
	}
	
	
}
