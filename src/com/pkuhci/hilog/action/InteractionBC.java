package com.pkuhci.hilog.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.util.Log;


public class InteractionBC extends BroadcastReceiver {
	private InteractionRecordDatabase interactionDB;

	// 如果接收的事件发生
	@Override
	public void onReceive(Context context, Intent intent) {
		// 对比Action决定输出什么信息
		interactionDB = new InteractionRecordDatabase(context);
		/*
		 * 采集飞行模式的开关
		 */
		if (intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {

			if (intent.hasExtra("state")) {
				// Log.e("HelloBroadReciever",intent.getBooleanExtra("state",false)+""
				// );
				if (intent.getBooleanExtra("state", false) == false) {
					//Toast.makeText(context, "飞行模式关闭", Toast.LENGTH_LONG).show();
					String interactTime = GetTime.getTime();
					interactionDB.insertInteractionRecord("android", interactTime,
							"飞行模式", "飞行模式关闭", "无", "无", "无");
					
					Log.e("LifelogBroadReciever", "飞行模式关闭");
				}
				if (intent.getBooleanExtra("state", false) == true) {
					//Toast.makeText(context, "飞行模式开启", Toast.LENGTH_LONG).show();
					String interactTime = GetTime.getTime();
					interactionDB.insertInteractionRecord("android", interactTime,
							"飞行模式", "飞行模式开启", "无", "无", "无");
					
					Log.e("HelloBroadReciever", "飞行模式开启");
				}
			}
			//Toast.makeText(context, "已经保存", Toast.LENGTH_LONG).show();

		}
//		/*
//		 * 采集电池的变化
//		 */
//
//		if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
//
//			int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//
//			//Toast.makeText(context, "电池电量变化为" + level, Toast.LENGTH_LONG)
//			//		.show();
//			String interactTime = GetTime.getTime();
//			interactionDB.insertInteractionRecord("android", interactTime,
//					"电池电量", "电量变化", level + "%", "无", "无");
//			Log.e("LifelogBroadReciever", "电池电量变化为" + level);
//
//		}
		/*
		 * 采集开始充电的动作
		 */

		if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {

			//Toast.makeText(context, "开始充电", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"电池电量", "进行充电", "无", "无", "无");
			Log.e("LifelogBroadReciever", "开始充电");

		}
		/*
		 * 采集停止充电的动作
		 */
		if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {

			//Toast.makeText(context, "停止充电", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"电池电量", "终止充电", "无", "无", "无");
			Log.e("LifelogBroadReciever", "停止充电");

		}

		/*
		 * 采集拍照的动作
		 */
		if (intent.getAction().equals(Intent.ACTION_CAMERA_BUTTON)) {
			//Toast.makeText(context, "拍照动作", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"相机操作", "拍照动作", "无", "无", "无");
			Log.e("LifelogBroadReciever", "拍照动作");

		}

		/*
		 * 采集退出画面的动作
		 */
		if (intent.getAction().equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
			//Toast.makeText(context, "Lifelog采集", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"触屏操作", "按下HOME键", "无", "无", "无");
			Log.e("LifelogBroadReciever", "按下HOME键");

		}
		/*
		 * 采集更改日期的动作
		 */
		if (intent.getAction().equals(Intent.ACTION_DATE_CHANGED)) {
			//Toast.makeText(context, "改变日期", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"系统配置操作", "改变日期", "无", "无", "无");
			Log.e("LifelogBroadReciever", "改变日期");

		}

		/*
		 * 采集更改输入法的动作
		 */
		if (intent.getAction().equals(Intent.ACTION_INPUT_METHOD_CHANGED)) {
			//Toast.makeText(context, "改变输入法", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"系统配置操作", "改变输入法", "无", "无", "无");
			Log.e("LifelogBroadReciever", "改变输入法");

		}

//		/*
//		 * 采集更改日期的动作
//		 */
//		if (intent.getAction().equals(Intent.ACTION_DATE_CHANGED)) {
//			Toast.makeText(context, "改变日期", Toast.LENGTH_LONG).show();
//			String interactTime = GetTime.getTime();
//			interactionDB.insertInteractionRecord("android", interactTime,
//					"系统配置操作", "改变日期", "无", "无", "无");
//			Log.e("LifelogBroadReciever", "改变日期");
//
//		}

		/*
		 * 采集更改语言的动作
		 */
		if (intent.getAction().equals(Intent.ACTION_LOCALE_CHANGED)) {
			//Toast.makeText(context, "改变语言", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"系统配置操作", "改变语言", "无", "无", "无");
			Log.e("LifelogBroadReciever", "改变语言");

		}

		/*
		 * 采集卸载SD卡的动作
		 */
		if (intent.getAction().equals(Intent.ACTION_MEDIA_BAD_REMOVAL)
				|| intent.getAction().equals(Intent.ACTION_MEDIA_EJECT)) {
			//Toast.makeText(context, "卸载SD卡", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"系统配置操作", "卸载SD卡", "无", "无", "无");
			Log.e("LifelogBroadReciever", "卸载SD卡");

		}

		/*
		 * 采集安装SD卡的动作
		 */
		if (intent.getAction().equals(Intent.ACTION_MEDIA_MOUNTED)) {
			//Toast.makeText(context, "安装SD卡", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"系统配置操作", "安装SD卡", "无", "无", "无");
			Log.e("LifelogBroadReciever", "安装SD卡");

		}

		// /*
		// * 采集安装应用程序的动作（未测试成功）
		// */
		// if
		// (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)||intent.getAction().equals(Intent.ACTION_PACKAGE_CHANGED)||intent.getAction().equals(Intent.ACTION_PACKAGE_DATA_CLEARED)||intent.getAction().equals(Intent.ACTION_PACKAGE_DATA_CLEARED)||intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)||intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)||intent.getAction().equals(Intent.ACTION_PACKAGE_RESTARTED))
		// {
		//
		// //String newPakage=intent.EXTRA_UID;
		//
		//
		// Toast.makeText(context, "安装新应用程序", Toast.LENGTH_LONG).show();
		// // String interactTime = GetTime.getTime();
		// // interactionDB.insertInteractionRecord("android", interactTime,
		// // "系统配置操作", "安装新应用程序", "", "无", "无");
		// Log.e("HelloBroadReciever", "安装新应用程序");
		//
		// }

		/*
		 * 采集屏幕的关闭
		 */

		if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
			Log.e("HelloBroadReciever", "关");
			//Toast.makeText(context, "关", 1000).show();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd   HH:mm:ss");
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			String interactTime = formatter.format(curDate);
			interactionDB.insertInteractionRecord("android", interactTime,
					"开关屏幕", "关闭屏幕", "无", "无", "无");
			//Toast.makeText(context, "已经保存", Toast.LENGTH_LONG).show();

		}

		/*
		 * 采集屏幕的开启
		 */

		if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {
			Log.e("HelloBroadReciever", "开");
			//Toast.makeText(context, "开", 1000).show();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd   HH:mm:ss");
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			String interactTime = formatter.format(curDate);

			interactionDB.insertInteractionRecord("android", interactTime,
					"开关屏幕", "开启屏幕", "无", "无", "无");
			//Toast.makeText(context, "已经保存", Toast.LENGTH_LONG).show();

		}

		/*
		 * 采集耳机的插拔
		 */

		if (intent.getAction().equals("android.intent.action.HEADSET_PLUG")) {
//			Toast.makeText(context, "交互记录采集正在运行",
//					Toast.LENGTH_LONG).show();
			String headset = "耳机";
			String headsetName = "未知设备";
			String headsetType = "未知设备类型";
		
			if (intent.hasExtra("name")) {
				headsetName = intent.getStringExtra("name");
			}
			if (intent.hasExtra("microphone")) {
				if (intent.getIntExtra("microphone", 0) == 0) {
					headsetType = "麦克风";

				} else if (intent.getIntExtra("microphone", 0) == 1) {
					headsetType = "其他";

				}
			}
			if (intent.hasExtra("state")) {
//				if (intent.getIntExtra("state", 0) == 0) {
//					headset = "耳机拔出";
//					Toast.makeText(context, "耳机拔出",
//							Toast.LENGTH_LONG).show();
//					Log.e("HelloBroadReciever", "耳机拔出");
//				} else 
					if (intent.getIntExtra("state", 0) == 1) {
					headset = "耳机插入";

//					Toast.makeText(context, "耳机插入",
//							Toast.LENGTH_LONG).show();
					Log.e("HelloBroadReciever", "耳机插入");
				
					String interactTime = GetTime.getTime();
					interactionDB.insertInteractionRecord("android", interactTime,
							"外接设备操作", headset, headsetName, headsetType, "无");
					}
			}
			

			// Log.e("HelloBroadReciever", "相机");
			// Toast.makeText(context, "相机", 1000).show();
		}

		/*
		 * 采集手机关机
		 */

		if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {

			//Toast.makeText(context, "手机关机", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"手机使用操作", "手机关机", "无", "无", "无");
			Log.e("LifelogBroadReciever", "手机关机");
		}

		/*
		 * 采集修改时间动作
		 */

		if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {

			//Toast.makeText(context, "时间修改", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"系统配置操作", "时间修改", "无", "无", "无");
			Log.e("LifelogBroadReciever", "时间修改");
		}

		/*
		 * 采集连接USB动作
		 */

		if (intent.getAction().equals(Intent.ACTION_UMS_CONNECTED)) {

			//Toast.makeText(context, "连接USB", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"手机使用操作", "连接USB", "无", "无", "无");
			Log.e("LifelogBroadReciever", "连接USB");
		}

		/*
		 * 采集断开连接USB动作
		 */

		if (intent.getAction().equals(Intent.ACTION_UMS_DISCONNECTED)) {

			//Toast.makeText(context, "USB断开连接", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"手机使用操作", "USB断开连接", "无", "无", "无");
			Log.e("LifelogBroadReciever", "USB断开连接");
		}

		/*
		 * 采集用户对屏幕解锁的动作
		 */

		if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {

			//Toast.makeText(context, "对屏幕解锁", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"触屏操作", "对屏幕解锁", "无", "无", "无");
			Log.e("LifelogBroadReciever", "USB断开连接");
		}

		
		/*
		 * 采集连接或断开wifi的动作
		 */

		if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
			 WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
			   if(wifiManager.isWifiEnabled()){
			//Toast.makeText(context, "连接Wifi", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			WifiInfo wifiInfo=wifiManager.getConnectionInfo();;
			String wifiName="wifi名称:"+wifiInfo.getSSID();
			interactionDB.insertInteractionRecord("android", interactTime,
					"Wifi操作", "连接Wifi", wifiName, "无", "无");
			Log.e("LifelogBroadReciever", "连接Wifi");
			}
			   if( !wifiManager.isWifiEnabled()){
					//Toast.makeText(context, "断开Wifi", Toast.LENGTH_LONG).show();
					String interactTime = GetTime.getTime();
					interactionDB.insertInteractionRecord("android", interactTime,
							"Wifi操作", "断开Wifi", "无", "无", "无");
					Log.e("LifelogBroadReciever", "断开Wifi");
					}
		}

		
	}
}