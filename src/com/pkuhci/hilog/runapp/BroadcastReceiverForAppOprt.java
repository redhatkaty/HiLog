package com.pkuhci.hilog.runapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

public class BroadcastReceiverForAppOprt extends BroadcastReceiver {
	StringBuffer runningApps = new StringBuffer();
	List <AppInfo> allAppInfo=new ArrayList();


	// ������յ��¼�����
	@Override
	public void onReceive(Context context, Intent intent) {
		RunningAppDBHelper db =new RunningAppDBHelper(context);;
		
		// �Ա�Action�������ʲô��Ϣ
		/*
		 * �ɼ�����ģʽ�Ŀ���
		 */
		if (intent.getAction() != null) {

//			Toast.makeText(context, "��ʼ�ɼ���",
//					Toast.LENGTH_SHORT).show();
			
			ActivityManager mActivityManager = (ActivityManager) context
			.getSystemService("activity");

	List<ActivityManager.RunningAppProcessInfo> mRunningProcess = mActivityManager
			.getRunningAppProcesses();
	int i = 1;
	runningApps.setLength(0);

	for (ActivityManager.RunningAppProcessInfo amProcess : mRunningProcess) {
		AppInfo oneApp = new AppInfo();
		oneApp.setApp_name(getAppLabel(amProcess.processName,context));
		oneApp.setPackage_name(amProcess.processName);
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd   HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
		oneApp.setAction_time(formatter.format(curDate));
		runningApps.append(i++ + ":����" + oneApp.getPackage_name() + "�������ƣ�"
				+ oneApp.getApp_name() + "\n");
		allAppInfo.add(oneApp);
	}
	
	
	
			if(db.isEmpty()){
      			db.initiDB(allAppInfo);
      		}
      		else db.updateDB(db.getWasRunningApp(),allAppInfo);

		}
	

		
	}
	
	
	
	public String getAppLabel(String packageName,Context context) {
		String appname = "";
		try {
			appname = context.getPackageManager().getPackageInfo(packageName, 1).applicationInfo
					.loadLabel(context.getPackageManager()).toString();
		} catch (NameNotFoundException e1) {
			appname = packageName;
			e1.printStackTrace();
		}
		return appname;
	}
	
	
}