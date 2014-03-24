package com.pkuhci.hilog.recordcalling;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneListener extends PhoneStateListener {
	private File audioFile;
	MediaRecorder mediaRecorder; // = new MediaRecorder();
	Context c;
	String fileName = "phone_call_record";
	String fileFormat = ".amr";
	//
	boolean iscall = false;

	//
	public PhoneListener(Context context) {
		c = context;
		iscall = false;
	}

	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		super.onCallStateChanged(state, incomingNumber);

		switch (state) {
		case TelephonyManager.CALL_STATE_OFFHOOK:
			iscall = true;
			mediaRecorder = new MediaRecorder();
			try {

				recordCallComment();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mediaRecorder.stop();
				// mediaRecorder.release();
			}
			//Toast.makeText(c, "正在进行电话录音", Toast.LENGTH_SHORT).show();
			break;
		case TelephonyManager.CALL_STATE_IDLE:
			if (mediaRecorder != null) {
				mediaRecorder.stop();
				Toast.makeText(c, "电话录音已保存在SD卡HiLog_LifeLog文件夹中",
						Toast.LENGTH_SHORT).show();

				mediaRecorder = null;
			}
			if (iscall) {
				// mediaRecorder.stop();
				// mediaRecorder.release();
				iscall = false;
			}
			break;
		}
	}

	//
	public void recordCallComment() throws IOException {
		System.out.println(mediaRecorder);
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		// mediaRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);

		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		// if (audioFile == null) {
		File rootDir = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "HiLog_LifeLog" + File.separator);

		if (!rootDir.exists())
			rootDir.mkdir();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy_MM_dd_ HH_mm_ss ");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		audioFile = File.createTempFile(fileName + str, fileFormat, rootDir);
		// }
		mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
		mediaRecorder.prepare();
		mediaRecorder.start();
	}

}
