package com.pkuhci.hilog.music;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Audio.Albums;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.pkuhci.hilog.R;
import com.pkuhci.hilog.WelcomeScreen;

/**
 * 本示例演示如何利用Android自带的Music来播放程序 和Camera一样，可以通过Intent来启动它。
 * 我们需要指定一个ACTION_VIEW的Action 同时一个Uri来指定我们要播放文件的路径 最后指定一个MIME类型，指定所要播放的文件类型
 * 每种文件类型对应的都有一个MIME，他一般是类似于audio/mp3格式 前部分是一个较大的类型，后面是更具体的类型
 * 
 * 同样的，对于Audio类型的多媒体，系统存储在MediaStore.Audio中 包括Media，Album，Genre等信息体
 * 
 * 本文将以列表的形式列出所有的Album信息，供用户选择 当用户选择某个Album时，系统将打开这个ALbum下的所有Audio
 * 
 * @author Administrator
 * 
 */
public class PlayMusic extends ListActivity {

	String contentType = "暂无";
	String musicid = "暂无";
	String albumName = "暂无";
	String artist = "暂无";
	String composer = "暂无";
	String duration = "暂无";
	String mediaUri = "暂无";;
	String type = "暂无";
	String playedName = "暂无";
	String playMusicTime = "暂无";
	int durationRaw = 0;
	int sizeRaw = 0;
	String size = "暂无";
	String title = "暂无";
	String year = "暂无";
	public static StringBuffer showAllMusicHistory = new StringBuffer();
	private Button btnMusic;
	private MusicHistoryDatabase db;
	private boolean isAlbum = true; // true时，说明当前列表的内容是Album，false时，说明是Media

	private Cursor cursor; // 游标对象，

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.music_play_layout);
		db = new MusicHistoryDatabase(this);
		btnMusic = (Button) this.findViewById(R.id.play);
		btnMusic.setOnClickListener(new View.OnClickListener() {

			
			public void onClick(View v) {

				getAlbums();
				isAlbum = true;
			}
		});

		Button deleteButton = (Button) this
				.findViewById(R.id.music_delete_button);
		deleteButton.setOnClickListener(new OnClickListener() {
			// By pressing the button on this screen, user can go to the second
			// screen to input data.
			
			public void onClick(View arg0) {

				db.deleteMusicRecord();
				Toast.makeText(PlayMusic.this, "原有音乐播放记录已被删除", Toast.LENGTH_SHORT)
						.show();
			}
		});

		Button backButton = (Button) this.findViewById(R.id.music_back_button);
		backButton.setOnClickListener(new OnClickListener() {
			// By pressing the button on this screen, user can go to the second
			// screen to input data.
			
			public void onClick(View arg0) {

				Intent i = new Intent(PlayMusic.this, WelcomeScreen.class);

				startActivity(i);
			}
		});

	}

	public void onListItemClick(ListView l, View v, int position, long id) {

		// 判断当前是哪个列表
		if (isAlbum) {
			// 如果是Album，当用户点击某一个时，获取该Album下的所有media
			// l.getChildAt(position);
			if (cursor.moveToPosition(position)) {
				getMedias(cursor.getInt(cursor
						.getColumnIndexOrThrow(Albums._ID)));
				isAlbum = false;
			}
		} else {
			// 如果是Media，则当用户点击某一个时，则播放该Media
			// 调用系统自带的MediaPlayer来播放
			if (cursor.moveToPosition(position)) {

				mediaUri = cursor.getString(cursor
						.getColumnIndex(Audio.Media.DATA));

				type = cursor.getString(cursor
						.getColumnIndex(Audio.Media.MIME_TYPE));
				playedName = cursor.getString(cursor
						.getColumnIndex(Audio.Media.DISPLAY_NAME));
				if (cursor.getColumnIndex(Audio.Media._ID) != -1)
					musicid = cursor.getString(cursor
							.getColumnIndex(Audio.Media._ID));
				if (cursor.getColumnIndex(Audio.Media.ARTIST) != -1)
					artist = cursor.getString(cursor
							.getColumnIndex(Audio.Media.ARTIST));

				// if(cursor.getColumnIndex(Audio.Media.CONTENT_TYPE)!=-1){contentType=
				// cursor.getString(cursor.getColumnIndex(Audio.Media.CONTENT_TYPE));
				// }
				if (cursor.getColumnIndex(Audio.Media.ALBUM) != -1)
					albumName = cursor.getString(cursor
							.getColumnIndex(Audio.Media.ALBUM));
				if (cursor.getColumnIndex(Audio.Media.TITLE) != -1)
					title = cursor.getString(cursor
							.getColumnIndex(Audio.Media.TITLE));
				if (cursor.getColumnIndex(Audio.Media.YEAR) != -1)
					year = cursor.getString(cursor
							.getColumnIndex(Audio.Media.YEAR));
				if (cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER) != -1)
					composer = cursor.getString(cursor
							.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
				if (cursor.getColumnIndex(MediaStore.Audio.Media.DURATION) != -1)
					durationRaw = cursor.getInt(cursor
							.getColumnIndex(MediaStore.Audio.Media.DURATION));
				if (cursor.getColumnIndex(MediaStore.Audio.Media.SIZE) != -1)
					sizeRaw = cursor.getInt(cursor
							.getColumnIndex(Audio.AudioColumns.SIZE));

				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd   HH:mm:ss");
				Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
				playMusicTime = formatter.format(curDate);
				duration = convertSecondsToDuration(durationRaw);
				size = convertByteToM(sizeRaw);
				// Toast.makeText(getApplicationContext(), contentType,
				// Toast.LENGTH_SHORT).show();
				// Toast.makeText(getApplicationContext(), musicid,
				// Toast.LENGTH_LONG).show();
				//
				// Toast.makeText(getApplicationContext(), albumName,
				// Toast.LENGTH_LONG).show();
				//
				// Toast.makeText(getApplicationContext(), artist,
				// Toast.LENGTH_LONG).show();
				// Toast.makeText(getApplicationContext(), composer,
				// Toast.LENGTH_LONG).show();
				// Toast.makeText(getApplicationContext(), duration,
				// Toast.LENGTH_LONG).show();
				// Toast.makeText(getApplicationContext(), mediaUri,
				// Toast.LENGTH_LONG).show();
				// Toast.makeText(getApplicationContext(), type,
				// Toast.LENGTH_LONG).show();
				// Toast.makeText(getApplicationContext(), playedName,
				// Toast.LENGTH_LONG).show();
				// Toast.makeText(getApplicationContext(), playMusicTime,
				// Toast.LENGTH_LONG).show();
				// Toast.makeText(getApplicationContext(), size,
				// Toast.LENGTH_LONG).show();
				// Toast.makeText(getApplicationContext(), title,
				// Toast.LENGTH_LONG).show();
				// Toast.makeText(getApplicationContext(), year,
				// Toast.LENGTH_LONG).show();

				saveToDatabase();

//				Toast.makeText(getApplicationContext(), "已保存",
//						Toast.LENGTH_SHORT).show();
				Uri data = Uri.fromFile(new File(mediaUri));
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(data, type);
				startActivity(intent);
			}
		}

		// super.onListItemClick(l, v, position, id);
	}

	// 获取所有Albums
	public void getAlbums() {
		String[] columns = new String[] { Albums._ID, Albums.ALBUM };
		String[] from = new String[] { Albums.ALBUM };
		int[] to = new int[] { android.R.id.text1 };
		cursor = this.managedQuery(Albums.EXTERNAL_CONTENT_URI, columns, null,
				null, Albums.DEFAULT_SORT_ORDER);
		// CursorAdapter adapter = new SimpleCursorAdapter(this,
		// android.R.layout.simple_list_item_1, cursor, from, to);
		CursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.music_adapter, cursor, from, to);

		this.setListAdapter(adapter);
		// this.isAlbum = true;

	}

	// 获取某个Albums下对应的medias
	public void getMedias(int albumId) {
		String[] columns = new String[] { Audio.Media._ID, Audio.Media.DATA,
				Audio.Media.DISPLAY_NAME,
				Audio.Media.MIME_TYPE,
				// Audio.Media.CONTENT_TYPE,
				Audio.Media.ALBUM, Audio.Media.COMPOSER, Audio.Media.DURATION,
				Audio.Media.SIZE, Audio.Media.TITLE, Audio.Media.YEAR,
				Audio.Media.ARTIST

		};
		String selection = Audio.Media.ALBUM_ID + "=?";
		String[] selectionArgs = new String[] { albumId + "" };

		String[] from = new String[] { Audio.Media.DISPLAY_NAME };
		int[] to = new int[] { android.R.id.text1 };

		cursor = this.managedQuery(Audio.Media.EXTERNAL_CONTENT_URI, columns,
				selection, selectionArgs, Audio.Media.TITLE);
		CursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.music_adapter, cursor, from, to);
		this.setListAdapter(adapter);
	}

	public static String convertSecondsToDuration(int miliSecond) {
		int h = 0, d = 0, s = 0;
		int second = miliSecond / 1000;
		s = second % 60;
		second = second / 60;
		d = second % 60;
		h = second / 60;
		return h + "时" + d + "分" + s + "秒";
	}

	public static String convertByteToM(int byteRaw) {

		double byteRaw2 = byteRaw * 1.0;
		double m = byteRaw2 / ((1024 * 1024) * 1.0);

		return m + "M";
	}

	protected void saveToDatabase()
	/*
	 * String playMusicTime, String mediaUri,String type, String playedName,
	 * String musicid, String albumName, String artist, String composer, String
	 * duration, String size,String title,String year
	 */
	/*
	 * String , String ,String , String , String , String , String , String ,
	 * String , String ,String ,String
	 */
	{

		db.insertMusicHistory(playMusicTime, playedName, musicid, duration,
				artist, title, type, albumName, composer, year, size, mediaUri);
//		Toast.makeText(getApplicationContext(), "播放记录已保存", Toast.LENGTH_SHORT)
//				.show();
	}

}