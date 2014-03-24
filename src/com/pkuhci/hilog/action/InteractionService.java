package com.pkuhci.hilog.action;

import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.widget.Toast;

import com.pkuhci.hilog.PublicSpace;

public class InteractionService extends Service {
	private InteractionRecordDatabase db;

	private IntentFilter interactionRecevierFilter = new IntentFilter();
	private InteractionBC iBC = new InteractionBC();
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// 用于收集手机交互记录的广播
		interactionRecevierFilter
					.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);// 检测开关飞行模式

			interactionRecevierFilter.addAction(Intent.ACTION_SCREEN_ON);
			interactionRecevierFilter.addAction(Intent.ACTION_SCREEN_OFF);
			interactionRecevierFilter.addAction(Intent.ACTION_HEADSET_PLUG);
			// interactionRecevierFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
			interactionRecevierFilter.addAction(Intent.ACTION_POWER_CONNECTED);
			interactionRecevierFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
			interactionRecevierFilter.addAction(Intent.ACTION_CAMERA_BUTTON);
			interactionRecevierFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

			interactionRecevierFilter
					.addAction(Intent.ACTION_CONFIGURATION_CHANGED);
			interactionRecevierFilter.addAction(Intent.ACTION_DATE_CHANGED);
			interactionRecevierFilter.addAction(Intent.ACTION_INPUT_METHOD_CHANGED);
			interactionRecevierFilter.addAction(Intent.ACTION_LOCALE_CHANGED);
			interactionRecevierFilter.addAction(Intent.ACTION_MEDIA_BAD_REMOVAL);
			interactionRecevierFilter.addAction(Intent.ACTION_MEDIA_EJECT);
			interactionRecevierFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
			interactionRecevierFilter.addAction(Intent.ACTION_PACKAGE_DATA_CLEARED);

			/*
			 * 未测试成功
			 */
			// interactionRecevierFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
			// interactionRecevierFilter.addAction(Intent.ACTION_PACKAGE_CHANGED);
			// interactionRecevierFilter.addAction(Intent.ACTION_PACKAGE_DATA_CLEARED);
			// //interactionRecevierFilter.addAction(Intent.ACTION_PACKAGE_FIRST_LAUNCH);
			// interactionRecevierFilter.addAction(Intent.ACTION_PACKAGE_INSTALL);
			// interactionRecevierFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
			// interactionRecevierFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
			// interactionRecevierFilter.addAction(Intent.ACTION_PACKAGE_RESTARTED);
			interactionRecevierFilter.addAction(Intent.ACTION_SHUTDOWN);
			interactionRecevierFilter.addAction(Intent.ACTION_TIME_CHANGED);
			interactionRecevierFilter.addAction(Intent.ACTION_UMS_CONNECTED);
			interactionRecevierFilter.addAction(Intent.ACTION_UMS_DISCONNECTED);
			interactionRecevierFilter.addAction(Intent.ACTION_USER_PRESENT);
			interactionRecevierFilter
					.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//			try {
//					unregisterReceiver(iBC);
//					 Log.e("HelloBroadReciever", "????????");
//			} catch (IllegalArgumentException e) {
//				registerReceiver(iBC, interactionRecevierFilter);
//				unregisterReceiver(iBC);
//				 Log.e("HelloBroadReciever", "!!!!!!!!");
//			}
	//	
			if(PublicSpace.countRegistedTime<3){
			registerReceiver(iBC, interactionRecevierFilter);
			PublicSpace.countRegistedTime++;
			}
			// getViews();
			db = new InteractionRecordDatabase(this);
			List<String> list = db.listInteractionRecord();
//			Toast.makeText(getApplicationContext(), "交互记录后台服务已经建立！",
//					Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		
		
			}

}
