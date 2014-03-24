package com.pkuhci.hilog;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.pkuhci.hilog.R;
import com.pkuhci.hilog.action.InteractionService;

/**
 * 软件的开始界面
 * 
 * @author yue
 * 
 */

public class WelcomeScreen extends Activity {
	private static final String ACTION_INSTALL_SHORTCUT =    
	    "com.android.launcher.action.INSTALL_SHORTCUT";   
	static final String EXTRA_SHORTCUT_DUPLICATE = "duplicate";   
	SharedPreferences fileSendPerfs;  

	// private InteractionRecordDatabase db;
	//
	// private IntentFilter interactionRecevierFilter = new IntentFilter();
	// private InteractionBC iBC = new InteractionBC();
	
	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.welcome_screen_layout);// set the first view to
														// welcome the user

		// Create a start button to enter the application
	
		// Create a demo button to see sample
		Button demoButton = (Button) this.findViewById(R.id.show_record_button);
		demoButton.setOnClickListener(new OnClickListener() {
			// By pressing the button on this screen, user can go to the second
			// screen to input data.
			
			public void onClick(View arg0) {
				Intent i = new Intent(WelcomeScreen.this, ShowRecord.class);

				startActivity(i);
				Toast.makeText(getApplicationContext(), "查看我的时光记录",
						Toast.LENGTH_SHORT).show();

			}
		});

	

		Button aboutButton = (Button) this.findViewById(R.id.about_button);
		aboutButton.setOnClickListener(new OnClickListener() {
			// By pressing the button on this screen, user can go to the second
			// screen to input data.
			
			public void onClick(View arg0) {
				Intent i = new Intent(WelcomeScreen.this, About.class);

				startActivity(i);
				Toast.makeText(getApplicationContext(), "查看使用说明",
						Toast.LENGTH_SHORT).show();
			}
		});

		Button collectButton = (Button) this.findViewById(R.id.collect_button);
		collectButton.setOnClickListener(new OnClickListener() {
			// By pressing the button on this screen, user can go to the second
			// screen to input data.
			
			public void onClick(View arg0) {
				Intent i = new Intent(WelcomeScreen.this,
						ManualCollectItems.class);

				startActivity(i);
//				Toast.makeText(getApplicationContext(), "对各项采集的内容进行相关配置",
//						Toast.LENGTH_SHORT).show();
			}
		});

	

		// Button collectInteractionButton =(Button)
		// this.findViewById(R.id.collect_interaction);
		// collectInteractionButton.setOnClickListener(new OnClickListener(){
		// //By pressing the button on this screen, user can go to the second
		// screen to input data.
		// 
		// public void onClick(View arg0){
		// Intent i = new Intent(
		// WelcomeScreen.this,
		// CollectInteraction.class);
		//
		// startActivity(i);
		//
		// }
		// });

		// // 用于收集手机交互记录的广播
		// interactionRecevierFilter
		// .addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);// 检测开关飞行模式
		//
		// interactionRecevierFilter.addAction(Intent.ACTION_SCREEN_ON);
		// interactionRecevierFilter.addAction(Intent.ACTION_SCREEN_OFF);
		// interactionRecevierFilter.addAction(Intent.ACTION_HEADSET_PLUG);
		// //
		// interactionRecevierFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
		// interactionRecevierFilter.addAction(Intent.ACTION_POWER_CONNECTED);
		// interactionRecevierFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
		// interactionRecevierFilter.addAction(Intent.ACTION_CAMERA_BUTTON);
		// interactionRecevierFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		//
		// interactionRecevierFilter
		// .addAction(Intent.ACTION_CONFIGURATION_CHANGED);
		// interactionRecevierFilter.addAction(Intent.ACTION_DATE_CHANGED);
		// interactionRecevierFilter.addAction(Intent.ACTION_INPUT_METHOD_CHANGED);
		// interactionRecevierFilter.addAction(Intent.ACTION_LOCALE_CHANGED);
		// interactionRecevierFilter.addAction(Intent.ACTION_MEDIA_BAD_REMOVAL);
		// interactionRecevierFilter.addAction(Intent.ACTION_MEDIA_EJECT);
		// interactionRecevierFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
		// interactionRecevierFilter.addAction(Intent.ACTION_PACKAGE_DATA_CLEARED);
		//
		// /*
		// * 未测试成功
		// */
		// // interactionRecevierFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
		// //
		// interactionRecevierFilter.addAction(Intent.ACTION_PACKAGE_CHANGED);
		// //
		// interactionRecevierFilter.addAction(Intent.ACTION_PACKAGE_DATA_CLEARED);
		// //
		// //interactionRecevierFilter.addAction(Intent.ACTION_PACKAGE_FIRST_LAUNCH);
		// //
		// interactionRecevierFilter.addAction(Intent.ACTION_PACKAGE_INSTALL);
		// //
		// interactionRecevierFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		// //
		// interactionRecevierFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
		// //
		// interactionRecevierFilter.addAction(Intent.ACTION_PACKAGE_RESTARTED);
		// interactionRecevierFilter.addAction(Intent.ACTION_SHUTDOWN);
		// interactionRecevierFilter.addAction(Intent.ACTION_TIME_CHANGED);
		// interactionRecevierFilter.addAction(Intent.ACTION_UMS_CONNECTED);
		// interactionRecevierFilter.addAction(Intent.ACTION_UMS_DISCONNECTED);
		// interactionRecevierFilter.addAction(Intent.ACTION_USER_PRESENT);
		// interactionRecevierFilter
		// .addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		// // try {
		// // unregisterReceiver(iBC);
		// // Log.e("HelloBroadReciever", "????????");
		// // } catch (IllegalArgumentException e) {
		// // registerReceiver(iBC, interactionRecevierFilter);
		// // unregisterReceiver(iBC);
		// // Log.e("HelloBroadReciever", "!!!!!!!!");
		// // }
		// //
		// if(PublicSpace.countRegistedTime<3){
		// registerReceiver(iBC, interactionRecevierFilter);
		// PublicSpace.countRegistedTime++;
		// }
		// // getViews();
		// db = new InteractionRecordDatabase(this);
		// List<String> list = db.listInteractionRecord();
		startService(new Intent(WelcomeScreen.this, InteractionService.class));

		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// R.layout.adapter_item_1, list);
		// //listview.setAdapter(adapter);
		fileSendPerfs = getSharedPreferences("creat_shortcut_perfs", 0); 
		Log.e("short1", "short11");
		fileSendPerfs.edit().putBoolean("program_started", true).commit();  
		Log.e("short1", "short22");
		createShortCut();
	}
    
    public void createShortCut(){
    	Log.e("short1", "short33");
    	
    	if(!fileSendPerfs.getBoolean("shortcut_created", false)){
    		/**  
        	* 是否可以有多个快捷方式的副本  
        	*/  
    		Log.e("short1", "short44");
        	Intent shortcutIntent = new Intent(ACTION_INSTALL_SHORTCUT);     
        	        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,     
        	        		getString(R.string.app_name));     
        	        shortcutIntent.putExtra(EXTRA_SHORTCUT_DUPLICATE, false);     
        	        Intent intent2 = new Intent(Intent.ACTION_MAIN);     
        	        intent2.addCategory(Intent.CATEGORY_LAUNCHER);   
        	  
        	        intent2.setComponent(new ComponentName(this.getPackageName(),     
        	                ".WelcomeScreen"));     
        	             
        	        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent2);     
        	        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,     
        	                Intent.ShortcutIconResource.fromContext(this,     
        	                        R.drawable.applogo));     
        	        sendBroadcast(shortcutIntent); 
        	
        	    	fileSendPerfs.edit().putBoolean("shortcut_created", true).commit();  
        	        
    	}
    	
    	}
}
