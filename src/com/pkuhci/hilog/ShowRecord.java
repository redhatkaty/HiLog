package com.pkuhci.hilog;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Browser;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pkuhci.hilog.R;
import com.pkuhci.hilog.action.ShowInteractionRecord;
import com.pkuhci.hilog.calllog.ShowCallRecord;
import com.pkuhci.hilog.forlayout.AdapterForShow;
import com.pkuhci.hilog.gps.GPSServiceShow;
import com.pkuhci.hilog.gps.GPSServiceShow2;
import com.pkuhci.hilog.msg.ShowMsgRecord;
import com.pkuhci.hilog.music.ShowMusicRecord;
import com.pkuhci.hilog.runapp.ListAllRunningApps;
import com.pkuhci.hilog.webhistory.ShowWebHistory;

public class ShowRecord extends ListActivity {
	private List<String> items = null;
	private List<String> paths = null;
	// private String rootPath = "/";
	private String rootPath = Environment.getExternalStorageDirectory()
			+ File.separator + "HiLog_LifeLog" + File.separator;
	private String curPath = "/";
	//private TextView mPath;
	public static StringBuffer showAllWebHistory;
	public static StringBuffer msgRecord;
	public static StringBuffer callLog;

	private final static String TAG = "bb";

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fileselect);
		//mPath = (TextView) findViewById(R.id.mPath);
		// Button buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
		// buttonConfirm.setOnClickListener(new OnClickListener() {
		// public void onClick(View v) {
		// Intent data = new Intent(ShowRecord.this, WelcomeScreen.class);
		// Bundle bundle = new Bundle();
		// bundle.putString("file", curPath);
		// data.putExtras(bundle);
		// setResult(2, data);
		// finish();
		// }
		// });
		Button buttonCancle = (Button) findViewById(R.id.buttonCancle);
		buttonCancle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(ShowRecord.this, WelcomeScreen.class);

				startActivity(i);
			}
		});

		File rootDir = new File(rootPath);

		if (!rootDir.exists())
			rootDir.mkdir();
		getFileDir(rootPath);

		Button showInteraction = (Button) findViewById(R.id.show_interaction);
		showInteraction.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(ShowRecord.this,
						ShowInteractionRecord.class);

				startActivity(i);
			}
		});
		Button showMusic = (Button) findViewById(R.id.show_music);
		showMusic.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(ShowRecord.this, ShowMusicRecord.class);

				startActivity(i);
			}
		});

		Button showGPS = (Button) findViewById(R.id.show_gps);
		showGPS.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(ShowRecord.this, GPSServiceShow2.class);

				startActivity(i);
			}
		});

		Button showWeb = (Button) findViewById(R.id.show_web);
		showWeb.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				getWebHistory();
				Intent i = new Intent(ShowRecord.this, ShowWebHistory.class);

				startActivity(i);

			}
		});

		Button showMsg = (Button) findViewById(R.id.show_msg);
		showMsg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showMsgRecord();
				Intent i = new Intent(ShowRecord.this, ShowMsgRecord.class);

				startActivity(i);

			}
		});
		Button showCalllog = (Button) findViewById(R.id.show_calllog);
		showCalllog.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				collectCallLog();
				Intent i = new Intent(ShowRecord.this, ShowCallRecord.class);

				startActivity(i);

			}
		});
		
		Button showRunApp = (Button) findViewById(R.id.show_app);
		showRunApp.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				collectCallLog();
				Intent i = new Intent(ShowRecord.this, ListAllRunningApps.class);

				startActivity(i);

			}
		});
		
	}

	private void collectCallLog() {
		Date date;
		callLog = new StringBuffer("");
		List<String[]> allCall = new ArrayList<String[]>();
		String[] columns = new String[] { CallLog.Calls._ID,
				CallLog.Calls.DATE, CallLog.Calls.NUMBER,
				CallLog.Calls.CACHED_NAME, CallLog.Calls.TYPE,
				CallLog.Calls.DURATION };
		ContentResolver cr = getContentResolver();
		Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, columns, null,
				null, CallLog.Calls.DEFAULT_SORT_ORDER);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String[] eachCall = { "", "", "", "", "" };
			for (int i = 1; i < columns.length; ++i) {
				if(i==1){callLog.append("通话时间：" );}
				if(i==2){callLog.append("通话号码：" );}
				if(i==3){callLog.append("联系人姓名：" );}
				if(i==4){callLog.append("通话类型：" );}
				if(i==5){callLog.append("通话时长（秒）：" );}
				
				if (i == 1) {
					SimpleDateFormat sfd = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					date = new Date(Long.parseLong(cursor.getString(i)));
					eachCall[i - 1] = sfd.format(date);
					callLog.append(sfd.format(date));
				} else if(i == 4){
					if (cursor.getString(i).contains("1")){
						callLog.append("来电");
						
					}if (cursor.getString(i).contains("2")){
						callLog.append("去电");
						
					}if (cursor.getString(i).contains("3")){
						callLog.append("未接");
						
					}
				}
				else {
					callLog.append(cursor.getString(i));
					eachCall[i - 1] = cursor.getString(i);
					if (eachCall[i - 1] == null) {
						eachCall[i - 1] = "未知\n";
					}
				}
				if (i < (columns.length - 1)) {
					callLog.append("\n");
				}
			}
			allCall.add(eachCall);
			callLog.append("\n\n");
			cursor.moveToNext();
		}
		cursor.close();
		callLog.append("end");
	}

	private void showMsgRecord() {
		String str = getSmsInPhone();

		msgRecord = new StringBuffer("");
		msgRecord.append(str);

	}

	private String getSmsInPhone() {
		final String SMS_URI_ALL = "content://sms/";
		final String SMS_URI_INBOX = "content://sms/inbox";
		final String SMS_URI_SEND = "content://sms/sent";
		final String SMS_URI_DRAFT = "content://sms/draft";

		StringBuilder smsBuilder = new StringBuilder();

		try {

			List<String[]> allMsg = new ArrayList<String[]>();
			ContentResolver cr = getContentResolver();
			String[] projection = new String[] { "_id", "address", "person",
					"body", "date", "type" };
			Uri uri = Uri.parse(SMS_URI_ALL);
			Cursor cur = cr.query(uri, projection, null, null, "date desc");

			if (cur.moveToFirst()) {
				String nameID;
				String phoneNumber;
				String smsbody;
				String date;
				String type;

				int nameColumn = cur.getColumnIndex("person");
				int phoneNumberColumn = cur.getColumnIndex("address");
				int smsbodyColumn = cur.getColumnIndex("body");
				int dateColumn = cur.getColumnIndex("date");
				int typeColumn = cur.getColumnIndex("type");

				do {
					String[] eachMsg = { "", "", "", "", "", "" };
					nameID = cur.getString(nameColumn);
					phoneNumber = cur.getString(phoneNumberColumn);
					smsbody = cur.getString(smsbodyColumn);

					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					Date d = new Date(Long.parseLong(cur.getString(dateColumn)));
					date = dateFormat.format(d);

					int typeId = cur.getInt(typeColumn);
					if (typeId == 1) {
						type = "接收";
					} else if (typeId == 2) {
						type = "发送";
					} else {
						type = "";
					}

					//smsBuilder.append("[");
					if (nameID == null) {
						eachMsg[0] = "null未知";
						smsBuilder.append("姓名:"+nameID + "\n");

					} else {
						Cursor phones = cr
								.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
										null,
										ContactsContract.CommonDataKinds.Phone.CONTACT_ID
												+ "="
												+ nameID
												+ " and "
												+ ContactsContract.CommonDataKinds.Phone.TYPE
												+ "="
												+ ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
										null, null);
						String realName = "";
						if (phones.moveToFirst()) {
							realName = phones
									.getString(phones
											.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
						}

						// Uri uriForRealName =
						// ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,
						// Long.parseLong(name));
						// Cursor curForRealName = cr.query(uriForRealName,
						// null, null, null, null);
						// //curForRealName.moveToNext();
						// String realName =
						// curForRealName.getString(curForRealName.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
						//
						// // Cursor cursorForName =
						// cr.query(ContactsContract.Contacts.CONTENT_URI, null,
						// null, null, PhoneLookup.DISPLAY_NAME +
						// " COLLATE LOCALIZED ASC");
						// //
						// // String realName =
						// cursorForName.getString(cursorForName.getColumnIndex(name));
						// //
						eachMsg[0] = realName;
						smsBuilder.append("姓名:"+realName + "\n");
					}

					smsBuilder.append("号码:"+phoneNumber + "\n");
					eachMsg[1] = phoneNumber;

					smsBuilder.append("内容:"+smsbody + "\n");
					eachMsg[2] = smsbody;

					smsBuilder.append("时间:"+date + "\n");
					eachMsg[3] = date;

					smsBuilder.append("类型:"+type+"\n");
					eachMsg[4] = type;

					smsBuilder.append("\n\n");

					if (smsbody == null) {
						smsbody = "";
						eachMsg[2] = "";
					}
					allMsg.add(eachMsg);

				} while (cur.moveToNext());
			} else {
				smsBuilder.append("no result!");
			}
			smsBuilder.append("\n全部记录已显示");
		} catch (SQLiteException ex) {
			Log.d("SQLiteException in getSmsInPhone", ex.getMessage());
		}
		return smsBuilder.toString();

	}

	private void getWebHistory() {
		String str = getAllWebHistory();

		showAllWebHistory = new StringBuffer("");
		showAllWebHistory.append(str);

		Toast.makeText(getApplicationContext(), "显示手机所有网页访问记录",
				Toast.LENGTH_SHORT).show();

	}

	private String getAllWebHistory() {
		StringBuilder webHistoryBuilder = new StringBuilder();
		List<String[]> allWebHistory = new ArrayList<String[]>();

		String ID;
		String website;
		String URL;
		String date;
		String frequency;
		Cursor mCur = managedQuery(Browser.BOOKMARKS_URI,
				Browser.HISTORY_PROJECTION, null, null, null);
		if (mCur != null) {
			mCur.moveToFirst();
			if (mCur.moveToFirst() && mCur.getCount() > 0) {

				while (mCur.isAfterLast() == false) {
					String[] eachWebHistory = { "", "", "", "", "", "" };

					ID = mCur.getString(Browser.HISTORY_PROJECTION_ID_INDEX);
					website = mCur
							.getString(Browser.HISTORY_PROJECTION_TITLE_INDEX);
					URL = mCur.getString(Browser.HISTORY_PROJECTION_URL_INDEX);
					String dateRaw = mCur
							.getString(Browser.HISTORY_PROJECTION_DATE_INDEX);
					if (dateRaw != null) {
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Date d = new Date(Long.parseLong(dateRaw));

						date = dateFormat.format(d);
						eachWebHistory[3] = date;

					} else
						eachWebHistory[3] = "1900-01-01 00:00:00";

					frequency = mCur
							.getString(Browser.HISTORY_PROJECTION_VISITS_INDEX);

					eachWebHistory[0] = ID;
					eachWebHistory[1] = website;
					eachWebHistory[2] = URL;

					eachWebHistory[4] = frequency;
					// eachWebHistory[5]=mCur.getString(Browser.HISTORY_PROJECTION_TITLE_INDEX);
					// eachWebHistory[6]=mCur.getString(Browser.HISTORY_PROJECTION_TITLE_INDEX);

					// Toast.makeText(getApplicationContext(), mCur
					// .getString(Browser.HISTORY_PROJECTION_URL_INDEX),
					// Toast.LENGTH_SHORT).show();
					allWebHistory.add(eachWebHistory);
					webHistoryBuilder.append("ID:" + ID + "\n" + "域名:"
							+ website + "\n" + "URL:" + URL + "\n" + "访问时间:"
							+ eachWebHistory[3].toString() + "\n" + "访问次数:"
							+ frequency + "\n");
					mCur.moveToNext();
					webHistoryBuilder.append("\n");

				}

				// }

			}
		}
		return webHistoryBuilder.toString();
	}

	private void getFileDir(String filePath) {
		//mPath.setText("文件路径为："+filePath);
		items = new ArrayList<String>();
		paths = new ArrayList<String>();
		File f = new File(filePath);
		File[] files = f.listFiles();
		if (!filePath.equals(rootPath)) {
			items.add("b1");
			paths.add(rootPath);
			items.add("b2");
			paths.add(f.getParent());
		}
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			items.add(file.getName());
			paths.add(file.getPath());

		}
		setListAdapter(new AdapterForShow(this, items, paths));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		File file = new File(paths.get(position));
		if (file.isDirectory()) {
			curPath = paths.get(position);
			getFileDir(paths.get(position));
		} else {
			openFile(file);
		}
	}
	private void openFile(File f) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);

		String type = getMIMEType(f);
		intent.setDataAndType(Uri.fromFile(f), type);
		startActivity(intent);
	}
	private String getMIMEType(File f) {
		String type = "";
		String fName = f.getName();
		String end = fName
				.substring(fName.lastIndexOf(".") + 1, fName.length())
				.toLowerCase();
		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
				|| end.equals("xmf") || end.equals("ogg") || end.equals("wav")||end.equals("amr")||end.equals("3gpp")) {
			type = "audio";
		} else if (end.equals("3gp") || end.equals("mp4")) {
			type = "video";
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg") || end.equals("bmp")) {
			type = "image";
		} else {
			type = "*";
		}
		type += "/*";
		return type;
	}

}
