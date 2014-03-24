package com.pkuhci.hilog.collectmedia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pkuhci.hilog.R;
import com.pkuhci.hilog.WelcomeScreen;

public class TakePhoto extends Activity {
	Bitmap receivedPhoto;
	String photoSavePath = Environment.getExternalStorageDirectory()
			+ File.separator + "HiLog_LifeLog" + File.separator;// 照片存储路径.空字符串为data/file路径
	String photoname = "LifelogPhoto";
	String photoSaveFormat = ".jpg";// 照片存储的后缀格式名称
	Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;// 照片存储格式
	int compressQuanlity = 100;// 压缩率

	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.take_photo_layout);

		Button captureButton = (Button) this.findViewById(R.id.capture);
		captureButton.setOnClickListener(new OnClickListener() {
			// By pressing the button on this screen, user can go to the second
			// screen to input data.
			
			public void onClick(View arg0) {
				try {
					Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
					startActivityForResult(i, Activity.DEFAULT_KEYS_DIALER);
				} catch (Exception e) {
				}
				Log.d("ZhouXingyue", "activity2 started");

			}
		});
		Button backButton = (Button) this.findViewById(R.id.home_button);
		backButton.setOnClickListener(new OnClickListener() {
			// By pressing the button on this screen, user can go to the second
			// screen to input data.
			
			public void onClick(View arg0) {
				Intent i = new Intent(TakePhoto.this, WelcomeScreen.class);

				startActivity(i);

			}
		});

	}

	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		final LinearLayout layout2 = new LinearLayout(this);
		if (resultCode == Activity.RESULT_CANCELED) {
			// Toast.makeText(getApplicationContext(), "back ",
			// Toast.LENGTH_SHORT);
			return;
		}
		Bundle extras = data.getExtras();
		receivedPhoto = (Bitmap) extras.get("data");

		ImageView img = (ImageView) this.findViewById(R.id.photo);
		img.setImageBitmap(receivedPhoto);

		Button saveButton = (Button) this.findViewById(R.id.save);
		saveButton.setVisibility(View.VISIBLE);
		saveButton.setHeight(30);

		Button captureButton = (Button) this.findViewById(R.id.capture);
		captureButton.setBackgroundResource(R.drawable.retake_photo_button);

		saveButton.setOnClickListener(new OnClickListener() {

			
			public void onClick(View arg0) {

				FileOutputStream fos = null;
				try {
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy_MM_dd_ HH_mm_ss ");
					Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
					String str = formatter.format(curDate);
					String allSavePath = photoSavePath + photoname + str
							+ photoSaveFormat;// 以Lifelog照片+系统时间为名存储图片
					File fileFolder = new File(Environment
							.getExternalStorageDirectory()
							+ File.separator
							+ "HiLog_LifeLog" + File.separator);

					if (!fileFolder.exists())
						fileFolder.mkdir();
					File file = new File(allSavePath);

					file.createNewFile();
					fos = new FileOutputStream(file);

					// fos = openFileOutput(allSavePath, Context.MODE_PRIVATE);
					receivedPhoto.compress(format, compressQuanlity, fos);
					Toast.makeText(getApplicationContext(),
							"照片已保存在SD卡中HiLog_LifeLog文件夹中", Toast.LENGTH_SHORT)
							.show();

				} catch (FileNotFoundException e) {
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (fos != null) {
						try {
							fos.flush();
							fos.close();
						} catch (IOException e) {
						}
					}
				}

			}

		});

	}

}