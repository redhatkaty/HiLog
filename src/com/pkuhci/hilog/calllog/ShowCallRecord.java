package com.pkuhci.hilog.calllog;

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
 * 将以获取的String类型的xml格式的通话记录存在文件中并存在程序包内或sd卡中
 */
public class ShowCallRecord extends Activity {
	public static String CallHistoryXMLStringRaw = "raw";

	public void onCreate(Bundle b) {

		super.onCreate(b);
		setContentView(R.layout.show_calllog_layout);

		TextView tv = (TextView) this.findViewById(R.id.user_info);
		tv.setText(ShowRecord.callLog);
		
		Button demoButton = (Button) this.findViewById(R.id.back_button);
		demoButton.setOnClickListener(new OnClickListener() {

			
			public void onClick(View arg0) {

				Intent i = new Intent(ShowCallRecord.this, ShowRecord.class);

				startActivity(i);
				//Toast.makeText(getApplicationContext(), "返回上一画面", Toast.LENGTH_SHORT).show();
			}
		});



	}

}
