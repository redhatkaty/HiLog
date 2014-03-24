package com.pkuhci.hilog;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.pkuhci.hilog.R;
import com.pkuhci.hilog.collectmedia.SoundRecord;
import com.pkuhci.hilog.collectmedia.TakePhoto;
import com.pkuhci.hilog.gps.GPSService;
import com.pkuhci.hilog.music.PlayMusic;
import com.pkuhci.hilog.recordcalling.CallRecordService;

public class ManualCollectItems extends Activity {
	static CheckBox checkPhoneRecorderService = null;
	SharedPreferences fileSendPerfs;

	
	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.manual_collect_layout);// set the first view to
														// welcome the user

		Button GPSCollectButton = (Button) this.findViewById(R.id.gps_collect);
		GPSCollectButton.setOnClickListener(new OnClickListener() {
			// By pressing the button on this screen, user can go to the second
			// screen to input data.
			
			public void onClick(View arg0) {
				Intent i = new Intent(ManualCollectItems.this, GPSService.class);

				startActivity(i);

			}
		});
		Button musicCollectButton = (Button) this
				.findViewById(R.id.music_collect);
		musicCollectButton.setOnClickListener(new OnClickListener() {
			// By pressing the button on this screen, user can go to the second
			// screen to input data.
			
			public void onClick(View arg0) {
				Intent i = new Intent(ManualCollectItems.this, PlayMusic.class);

				startActivity(i);

			}
		});

		Button takePhotoButton = (Button) this.findViewById(R.id.takephoto);
		takePhotoButton.setOnClickListener(new OnClickListener() {

			
			public void onClick(View arg0) {
				Intent i = new Intent(ManualCollectItems.this, TakePhoto.class);

				startActivity(i);

			}
		});

		Button recordAudioButton = (Button) this
				.findViewById(R.id.record_button);
		recordAudioButton.setOnClickListener(new OnClickListener() {
			// By pressing the button on this screen, user can go to the second
			// screen to input data.
			
			public void onClick(View arg0) {

				Intent i = new Intent(ManualCollectItems.this,
						SoundRecord.class);

				startActivity(i);
//				Toast.makeText(getApplicationContext(), "进入录音功能",
//						Toast.LENGTH_SHORT).show();
			}
		});

		checkPhoneRecorderService = (CheckBox) findViewById(R.id.record_service);
		// // if
		// //
		// (PublicSpace.isPhoneServiceChecked==0)checkPhoneRecorderService.setChecked(false);
		// if (PublicSpace.isPhoneServiceChecked == 1)
		// checkPhoneRecorderService.setChecked(true);
		// if (PublicSpace.isPhoneServiceChecked == 2)
		// checkPhoneRecorderService.setChecked(false);

		fileSendPerfs = getSharedPreferences("creat_shortcut_perfs", 0);
		Log.e("zxy", "1获取pref");
		if (!fileSendPerfs.contains("record_phonecall")) {
			Log.e("zxy", "2pref不存在，创建");

			fileSendPerfs.edit().putBoolean("record_phonecall", true).commit();
			Log.e("zxy", "创建了true的pref");

		}

		Log.e("zxy", "获取pref值");
		if (fileSendPerfs.getBoolean("record_phonecall", false)) {
			checkPhoneRecorderService.setChecked(true);
			Intent serviceIntent = new Intent(getApplicationContext(),
					CallRecordService.class);
			getApplicationContext().startService(serviceIntent);
		} else
			checkPhoneRecorderService.setChecked(false);
		checkPhoneRecorderService
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						if (isChecked) {
							Intent serviceIntent = new Intent(
									getApplicationContext(),
									CallRecordService.class);
							getApplicationContext().startService(serviceIntent);
							fileSendPerfs.edit()
									.putBoolean("record_phonecall", true)
									.commit();
							Toast.makeText(ManualCollectItems.this,
									"电话录音功能已开启", Toast.LENGTH_SHORT).show();

							// FileSelected.recordSelectedList[0]=true;
						} else {
							Intent serviceIntent2 = new Intent(
									getApplicationContext(),
									CallRecordService.class);

							stopService(serviceIntent2);
							fileSendPerfs.edit()
									.putBoolean("record_phonecall", false)
									.commit();

							Toast.makeText(ManualCollectItems.this,
									"电话录音功能已关闭", Toast.LENGTH_SHORT).show();

						}
					}

				});

		Button homeButton = (Button) this.findViewById(R.id.home_button);
		homeButton.setOnClickListener(new OnClickListener() {
			// By pressing the button on this screen, user can go to the second
			// screen to input data.
			
			public void onClick(View arg0) {

				Intent i = new Intent(ManualCollectItems.this,
						WelcomeScreen.class);

				startActivity(i);

			}
		});

	}
}
