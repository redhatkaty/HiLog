package com.pkuhci.hilog.recordcalling;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

//import com.pkuhci.hilog.PublicSpace;

public class CallRecordService extends Service {
	/*
	 * 电话录音的后台服务
	 */
	TelephonyManager telephonymanager;
	PhoneListener phonelistener;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.e("HelloBroadReciever", "电话录音服务已经创建!!!!!!!!");
		//Toast.makeText(getApplicationContext(), "电话录音服务已经创建!", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("HelloBroadReciever", "电话录音服务已经销毁!!!!!!!");
		
		Toast.makeText(getApplicationContext(), "电话录音功能已经关闭!", Toast.LENGTH_LONG).show();
		telephonymanager.listen(phonelistener, 0);//0:unregister the listener!
	//PublicSpace.isPhoneServiceChecked=2;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.e("HelloBroadReciever", "电话录音服务已经启动!!!!!!!!");
		
		//Toast.makeText(getApplicationContext(), "电话录音功能已经启动!", Toast.LENGTH_LONG).show();
		//
		 telephonymanager=(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		 phonelistener =new PhoneListener(getApplicationContext());
		telephonymanager.listen(phonelistener, PhoneStateListener.LISTEN_CALL_STATE);
		//PublicSpace.isPhoneServiceChecked=1;
		
			}

}
