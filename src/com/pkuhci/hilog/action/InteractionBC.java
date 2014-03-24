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

	// ������յ��¼�����
	@Override
	public void onReceive(Context context, Intent intent) {
		// �Ա�Action�������ʲô��Ϣ
		interactionDB = new InteractionRecordDatabase(context);
		/*
		 * �ɼ�����ģʽ�Ŀ���
		 */
		if (intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {

			if (intent.hasExtra("state")) {
				// Log.e("HelloBroadReciever",intent.getBooleanExtra("state",false)+""
				// );
				if (intent.getBooleanExtra("state", false) == false) {
					//Toast.makeText(context, "����ģʽ�ر�", Toast.LENGTH_LONG).show();
					String interactTime = GetTime.getTime();
					interactionDB.insertInteractionRecord("android", interactTime,
							"����ģʽ", "����ģʽ�ر�", "��", "��", "��");
					
					Log.e("LifelogBroadReciever", "����ģʽ�ر�");
				}
				if (intent.getBooleanExtra("state", false) == true) {
					//Toast.makeText(context, "����ģʽ����", Toast.LENGTH_LONG).show();
					String interactTime = GetTime.getTime();
					interactionDB.insertInteractionRecord("android", interactTime,
							"����ģʽ", "����ģʽ����", "��", "��", "��");
					
					Log.e("HelloBroadReciever", "����ģʽ����");
				}
			}
			//Toast.makeText(context, "�Ѿ�����", Toast.LENGTH_LONG).show();

		}
//		/*
//		 * �ɼ���صı仯
//		 */
//
//		if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
//
//			int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//
//			//Toast.makeText(context, "��ص����仯Ϊ" + level, Toast.LENGTH_LONG)
//			//		.show();
//			String interactTime = GetTime.getTime();
//			interactionDB.insertInteractionRecord("android", interactTime,
//					"��ص���", "�����仯", level + "%", "��", "��");
//			Log.e("LifelogBroadReciever", "��ص����仯Ϊ" + level);
//
//		}
		/*
		 * �ɼ���ʼ���Ķ���
		 */

		if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {

			//Toast.makeText(context, "��ʼ���", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"��ص���", "���г��", "��", "��", "��");
			Log.e("LifelogBroadReciever", "��ʼ���");

		}
		/*
		 * �ɼ�ֹͣ���Ķ���
		 */
		if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {

			//Toast.makeText(context, "ֹͣ���", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"��ص���", "��ֹ���", "��", "��", "��");
			Log.e("LifelogBroadReciever", "ֹͣ���");

		}

		/*
		 * �ɼ����յĶ���
		 */
		if (intent.getAction().equals(Intent.ACTION_CAMERA_BUTTON)) {
			//Toast.makeText(context, "���ն���", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"�������", "���ն���", "��", "��", "��");
			Log.e("LifelogBroadReciever", "���ն���");

		}

		/*
		 * �ɼ��˳�����Ķ���
		 */
		if (intent.getAction().equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
			//Toast.makeText(context, "Lifelog�ɼ�", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"��������", "����HOME��", "��", "��", "��");
			Log.e("LifelogBroadReciever", "����HOME��");

		}
		/*
		 * �ɼ��������ڵĶ���
		 */
		if (intent.getAction().equals(Intent.ACTION_DATE_CHANGED)) {
			//Toast.makeText(context, "�ı�����", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"ϵͳ���ò���", "�ı�����", "��", "��", "��");
			Log.e("LifelogBroadReciever", "�ı�����");

		}

		/*
		 * �ɼ��������뷨�Ķ���
		 */
		if (intent.getAction().equals(Intent.ACTION_INPUT_METHOD_CHANGED)) {
			//Toast.makeText(context, "�ı����뷨", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"ϵͳ���ò���", "�ı����뷨", "��", "��", "��");
			Log.e("LifelogBroadReciever", "�ı����뷨");

		}

//		/*
//		 * �ɼ��������ڵĶ���
//		 */
//		if (intent.getAction().equals(Intent.ACTION_DATE_CHANGED)) {
//			Toast.makeText(context, "�ı�����", Toast.LENGTH_LONG).show();
//			String interactTime = GetTime.getTime();
//			interactionDB.insertInteractionRecord("android", interactTime,
//					"ϵͳ���ò���", "�ı�����", "��", "��", "��");
//			Log.e("LifelogBroadReciever", "�ı�����");
//
//		}

		/*
		 * �ɼ��������ԵĶ���
		 */
		if (intent.getAction().equals(Intent.ACTION_LOCALE_CHANGED)) {
			//Toast.makeText(context, "�ı�����", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"ϵͳ���ò���", "�ı�����", "��", "��", "��");
			Log.e("LifelogBroadReciever", "�ı�����");

		}

		/*
		 * �ɼ�ж��SD���Ķ���
		 */
		if (intent.getAction().equals(Intent.ACTION_MEDIA_BAD_REMOVAL)
				|| intent.getAction().equals(Intent.ACTION_MEDIA_EJECT)) {
			//Toast.makeText(context, "ж��SD��", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"ϵͳ���ò���", "ж��SD��", "��", "��", "��");
			Log.e("LifelogBroadReciever", "ж��SD��");

		}

		/*
		 * �ɼ���װSD���Ķ���
		 */
		if (intent.getAction().equals(Intent.ACTION_MEDIA_MOUNTED)) {
			//Toast.makeText(context, "��װSD��", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"ϵͳ���ò���", "��װSD��", "��", "��", "��");
			Log.e("LifelogBroadReciever", "��װSD��");

		}

		// /*
		// * �ɼ���װӦ�ó���Ķ�����δ���Գɹ���
		// */
		// if
		// (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)||intent.getAction().equals(Intent.ACTION_PACKAGE_CHANGED)||intent.getAction().equals(Intent.ACTION_PACKAGE_DATA_CLEARED)||intent.getAction().equals(Intent.ACTION_PACKAGE_DATA_CLEARED)||intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)||intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)||intent.getAction().equals(Intent.ACTION_PACKAGE_RESTARTED))
		// {
		//
		// //String newPakage=intent.EXTRA_UID;
		//
		//
		// Toast.makeText(context, "��װ��Ӧ�ó���", Toast.LENGTH_LONG).show();
		// // String interactTime = GetTime.getTime();
		// // interactionDB.insertInteractionRecord("android", interactTime,
		// // "ϵͳ���ò���", "��װ��Ӧ�ó���", "", "��", "��");
		// Log.e("HelloBroadReciever", "��װ��Ӧ�ó���");
		//
		// }

		/*
		 * �ɼ���Ļ�Ĺر�
		 */

		if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
			Log.e("HelloBroadReciever", "��");
			//Toast.makeText(context, "��", 1000).show();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd   HH:mm:ss");
			Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
			String interactTime = formatter.format(curDate);
			interactionDB.insertInteractionRecord("android", interactTime,
					"������Ļ", "�ر���Ļ", "��", "��", "��");
			//Toast.makeText(context, "�Ѿ�����", Toast.LENGTH_LONG).show();

		}

		/*
		 * �ɼ���Ļ�Ŀ���
		 */

		if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {
			Log.e("HelloBroadReciever", "��");
			//Toast.makeText(context, "��", 1000).show();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd   HH:mm:ss");
			Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
			String interactTime = formatter.format(curDate);

			interactionDB.insertInteractionRecord("android", interactTime,
					"������Ļ", "������Ļ", "��", "��", "��");
			//Toast.makeText(context, "�Ѿ�����", Toast.LENGTH_LONG).show();

		}

		/*
		 * �ɼ������Ĳ��
		 */

		if (intent.getAction().equals("android.intent.action.HEADSET_PLUG")) {
//			Toast.makeText(context, "������¼�ɼ���������",
//					Toast.LENGTH_LONG).show();
			String headset = "����";
			String headsetName = "δ֪�豸";
			String headsetType = "δ֪�豸����";
		
			if (intent.hasExtra("name")) {
				headsetName = intent.getStringExtra("name");
			}
			if (intent.hasExtra("microphone")) {
				if (intent.getIntExtra("microphone", 0) == 0) {
					headsetType = "��˷�";

				} else if (intent.getIntExtra("microphone", 0) == 1) {
					headsetType = "����";

				}
			}
			if (intent.hasExtra("state")) {
//				if (intent.getIntExtra("state", 0) == 0) {
//					headset = "�����γ�";
//					Toast.makeText(context, "�����γ�",
//							Toast.LENGTH_LONG).show();
//					Log.e("HelloBroadReciever", "�����γ�");
//				} else 
					if (intent.getIntExtra("state", 0) == 1) {
					headset = "��������";

//					Toast.makeText(context, "��������",
//							Toast.LENGTH_LONG).show();
					Log.e("HelloBroadReciever", "��������");
				
					String interactTime = GetTime.getTime();
					interactionDB.insertInteractionRecord("android", interactTime,
							"����豸����", headset, headsetName, headsetType, "��");
					}
			}
			

			// Log.e("HelloBroadReciever", "���");
			// Toast.makeText(context, "���", 1000).show();
		}

		/*
		 * �ɼ��ֻ��ػ�
		 */

		if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {

			//Toast.makeText(context, "�ֻ��ػ�", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"�ֻ�ʹ�ò���", "�ֻ��ػ�", "��", "��", "��");
			Log.e("LifelogBroadReciever", "�ֻ��ػ�");
		}

		/*
		 * �ɼ��޸�ʱ�䶯��
		 */

		if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {

			//Toast.makeText(context, "ʱ���޸�", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"ϵͳ���ò���", "ʱ���޸�", "��", "��", "��");
			Log.e("LifelogBroadReciever", "ʱ���޸�");
		}

		/*
		 * �ɼ�����USB����
		 */

		if (intent.getAction().equals(Intent.ACTION_UMS_CONNECTED)) {

			//Toast.makeText(context, "����USB", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"�ֻ�ʹ�ò���", "����USB", "��", "��", "��");
			Log.e("LifelogBroadReciever", "����USB");
		}

		/*
		 * �ɼ��Ͽ�����USB����
		 */

		if (intent.getAction().equals(Intent.ACTION_UMS_DISCONNECTED)) {

			//Toast.makeText(context, "USB�Ͽ�����", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"�ֻ�ʹ�ò���", "USB�Ͽ�����", "��", "��", "��");
			Log.e("LifelogBroadReciever", "USB�Ͽ�����");
		}

		/*
		 * �ɼ��û�����Ļ�����Ķ���
		 */

		if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {

			//Toast.makeText(context, "����Ļ����", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			interactionDB.insertInteractionRecord("android", interactTime,
					"��������", "����Ļ����", "��", "��", "��");
			Log.e("LifelogBroadReciever", "USB�Ͽ�����");
		}

		
		/*
		 * �ɼ����ӻ�Ͽ�wifi�Ķ���
		 */

		if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
			 WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
			   if(wifiManager.isWifiEnabled()){
			//Toast.makeText(context, "����Wifi", Toast.LENGTH_LONG).show();
			String interactTime = GetTime.getTime();
			WifiInfo wifiInfo=wifiManager.getConnectionInfo();;
			String wifiName="wifi����:"+wifiInfo.getSSID();
			interactionDB.insertInteractionRecord("android", interactTime,
					"Wifi����", "����Wifi", wifiName, "��", "��");
			Log.e("LifelogBroadReciever", "����Wifi");
			}
			   if( !wifiManager.isWifiEnabled()){
					//Toast.makeText(context, "�Ͽ�Wifi", Toast.LENGTH_LONG).show();
					String interactTime = GetTime.getTime();
					interactionDB.insertInteractionRecord("android", interactTime,
							"Wifi����", "�Ͽ�Wifi", "��", "��", "��");
					Log.e("LifelogBroadReciever", "�Ͽ�Wifi");
					}
		}

		
	}
}