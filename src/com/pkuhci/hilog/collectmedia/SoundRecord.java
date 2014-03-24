package com.pkuhci.hilog.collectmedia;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.pkuhci.hilog.R;
import com.pkuhci.hilog.WelcomeScreen;

public class SoundRecord extends Activity {

	private MediaRecorder mediaRecorder;
	private File file = null;
	static final String PREFIX = "LifelogRecord";
	static final String EXTENSION = ".3gpp";
	Button stopRecording;

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_audio_layout);

		mediaRecorder = new MediaRecorder();

		Button startRecording = (Button) this.findViewById(R.id.record);
		stopRecording = (Button) this.findViewById(R.id.convert);
		final Button saveButton = (Button) this.findViewById(R.id.recording);
		startRecording.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					startRecording();
					Toast.makeText(getApplicationContext(), "录音开始",
							Toast.LENGTH_SHORT).show();
					saveButton.setVisibility(View.VISIBLE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		stopRecording.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (mediaRecorder!=null){stopRecording();
				Toast.makeText(getApplicationContext(),
						"本次录音已保存在SD卡HiLog_LifeLog文件夹中", Toast.LENGTH_SHORT)
						.show();
				saveButton.setVisibility(View.INVISIBLE);
				saveToDB();}
				else	Toast.makeText(getApplicationContext(),
						"录音发生错误", Toast.LENGTH_SHORT)
						.show();
			}
			
		});
		
		Button homeButton = (Button) this.findViewById(R.id.home_button);
		homeButton.setOnClickListener(new OnClickListener() {
			// By pressing the button on this screen, user can go to the second
			// screen to input data.
			
			public void onClick(View arg0) {
				

				Intent i = new Intent(SoundRecord.this, WelcomeScreen.class);

				startActivity(i);

			}
		});
	}

	/**
	 * This method starts recording process
	 * 
	 * @throws Exception
	 */
	private void startRecording() throws Exception {
		mediaRecorder = new MediaRecorder();
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		if (file == null) {
			File rootDir = new File(Environment.getExternalStorageDirectory()
					+ File.separator + "HiLog_LifeLog" + File.separator);

			if (!rootDir.exists())
				rootDir.mkdir();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy_MM_dd_ HH_mm_ss ");
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			String str = formatter.format(curDate);
			file = File.createTempFile(PREFIX + str, EXTENSION, rootDir);
		}
		mediaRecorder.setOutputFile(file.getAbsolutePath());
		mediaRecorder.prepare();
		mediaRecorder.start();
	}

	/**
	 * This method stops recording
	 */
	private void stopRecording() {
		try {
			mediaRecorder.stop();
		} catch (IllegalStateException e1) {
			
			mediaRecorder.release();
		}
		mediaRecorder.release();

	}

	/**
	 * This method sets all metadata for audio file
	 */
	private void saveToDB() {
		if(file!=null){ContentValues values = new ContentValues(3);
		long current = System.currentTimeMillis();
		values.put(MediaColumns.TITLE, "My Audio record");
		values.put(MediaColumns.DATE_ADDED, (int) (current / 1000));
		values.put(MediaColumns.MIME_TYPE, "audio/mp3");
		values.put(MediaColumns.DATA, file.getAbsolutePath());
		ContentResolver contentResolver = getContentResolver();
		Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		Uri newUri = contentResolver.insert(base, values);
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
	}}

	// // private String strRecorderPath = "";//
	// 录音文件的绝对路径(!先保存在了SD卡中.若要存在别的地方需要做其他处理)
	//
	// private String strRecorderPath
	// =Environment.getExternalStorageDirectory()+File.separator+"HiLog_LifeLog"+File.separator;
	//
	//
	//
	// 
	// public void onCreate(Bundle savedInstanceState) {
	//
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.record_audio_layout);
	// Button recordButton =(Button) this.findViewById(R.id.record);
	// recordButton.setOnClickListener(new OnClickListener(){
	//
	// 
	// public void onClick(View arg0){
	//
	//
	// Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
	// intent.setType("audio/amr");
	// // Intent intent = new
	// Intent("android.provider.MediaStore.RECORD_SOUND");
	// //Intent intent = new
	// Intent("MediaStore.Audio.Media.RECORD_SOUND_ACTION");
	//
	// startActivityForResult(intent, 0);
	//
	// }
	// });
	//
	//
	// }
	//
	//
	// 
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	//
	// super.onActivityResult(requestCode, resultCode, data);
	// Button saveButton =(Button) this.findViewById(R.id.convert);
	// saveButton.setVisibility(View.VISIBLE);
	// if (resultCode == RESULT_OK) {
	// Uri uriRecorder = data.getData();
	// Cursor cursor=this.getContentResolver().query(uriRecorder, null, null,
	// null, null);
	// if (cursor.moveToNext()) {
	// // /× _data：文件的绝对路径 ，_display_name：文件名 */
	// //strRecorderPath = cursor.getString(cursor.getColumnIndex("_data"));
	// Toast.makeText(this, strRecorderPath, Toast.LENGTH_SHORT).show();
	// }
	// }
	//
	// }

}