package com.pkuhci.hilog.runapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class RunningAppDBHelper extends SQLiteOpenHelper {
	public static final String RunningAppInfo_TABLE_NAME = "RunningAppInfo_TABLE1";
	public static final String PACKAGE_NAME = "package_name";
	public static final String APP_NAME = "app_name";
	public static final String ACTION_TIME = "action_time";
	public static final String ACTION_TYPE = "action_type";
	public static final String IS_RUNNING = "is_running";

	private static final String DB_NAME = "runningappinfo1.database1";
	private static final int VERSION = 1;

	public RunningAppDBHelper(Context context) {
		this(context, DB_NAME, null, VERSION);
	}

	public RunningAppDBHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		createRunningAppInfoDB(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	// 创建运行程序管理表
	public void createRunningAppInfoDB(SQLiteDatabase db) {
		String sql = "create table "
				+ RunningAppInfo_TABLE_NAME
				+ "(_id integer primary key autoincrement,package_name varchar(100),app_name varchar(100),action_time varchar(100),action_type varchar(100),is_running varchar(40))";
		db.execSQL(sql);
	}

	// 判断表是否为空
	public boolean isEmpty() {
		Log.d("ZhouXingyue", "aaa");

		SQLiteDatabase db = this.getWritableDatabase();

		Cursor myCursor = null;
		myCursor = db.query(RunningAppInfo_TABLE_NAME, null, null, null, null,
				null, null, null);

		if (myCursor.getCount() < 1) {
			myCursor.close();
			db.close();
			return true;
		} else {
			myCursor.close();
			db.close();
			return false;
		}

	}

	// 初始化表
	public void initiDB(List<AppInfo> initiAppInfo) {
		for (AppInfo eachAppInfo : initiAppInfo) {
			ContentValues c = new ContentValues();

			c.put(PACKAGE_NAME, eachAppInfo.getPackage_name());
			c.put(APP_NAME, eachAppInfo.getApp_name());
			c.put(ACTION_TIME, eachAppInfo.getAction_time());
			c.put(ACTION_TYPE, "OPEN");
			c.put(IS_RUNNING, "YES");

			RunningAppDBHelper.this.getWritableDatabase().insert(
					RunningAppInfo_TABLE_NAME, null, c);

		}
		Log.d("ZhouXingyue", "initiation finished");

	}

	// 插入一条关闭信息
	public void addCloseAppItem(AppInfo closedApp, String actionTime) {
		ContentValues c = new ContentValues();

		c.put(PACKAGE_NAME, closedApp.getPackage_name());
		c.put(APP_NAME, closedApp.getApp_name());
		c.put(ACTION_TIME, actionTime);
		c.put(ACTION_TYPE, "CLOSE");
		c.put(IS_RUNNING, "NO");

		RunningAppDBHelper.this.getWritableDatabase().insert(
				RunningAppInfo_TABLE_NAME, null, c);

		Log.d("ZhouXingyue", "close add");

	}

	// 插入一条开启信息
	public void addOpenAppItem(AppInfo openedApp) {
		ContentValues c = new ContentValues();

		c.put(PACKAGE_NAME, openedApp.getPackage_name());
		c.put(APP_NAME, openedApp.getApp_name());
		c.put(ACTION_TIME, openedApp.getAction_time());
		c.put(ACTION_TYPE, "OPEN");
		c.put(IS_RUNNING, "YES");

		RunningAppDBHelper.this.getWritableDatabase().insert(
				RunningAppInfo_TABLE_NAME, null, c);

		Log.d("ZhouXingyue", "open add");

	}

	public void updateRunningToStop(AppInfo closedApp) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;

		try {
			c = db.query(RunningAppInfo_TABLE_NAME, null, null, null, null,
					null, null, null);
			while (c.moveToNext()) {
				if (c.getString(c.getColumnIndex("is_running")).contains("YES")
						&& c.getString(c.getColumnIndex("package_name"))
								.contains(closedApp.getPackage_name())) {
					String pName = c
							.getString(c.getColumnIndex("package_name"));
					ContentValues cv = new ContentValues();

					cv.put(IS_RUNNING, "NO");
					db.update(RunningAppInfo_TABLE_NAME, cv, "package_name=?",
							new String[] { pName });
					Log.d("ZhouXingyue", "改变为NO");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			c.close();
			db.close();
		}

		Log.d("ZhouXingyue", "一条记录由开始变停止");

	}

	public List<String> listAllRecordInDB() {
		List<String> list = new ArrayList<String>();

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;

		try {
			c = db.query(RunningAppInfo_TABLE_NAME, null, null, null, null,
					null, null, null);
			while (c.moveToNext()) {
				String package_name = c.getString(c
						.getColumnIndex("package_name"));
				String app_name = c.getString(c.getColumnIndex("app_name"));
				String action_time = c.getString(c
						.getColumnIndex("action_time"));
				String action_type = c.getString(c
						.getColumnIndex("action_type"));
				String comments = new String();
				if (action_type.contains("OPEN")){comments="打开该程序 _";}
				if (action_type.contains("CLOSE")){comments="关闭该程序_ ";}
				
				String is_running = c.getString(c.getColumnIndex("is_running"));
				list.add("序号：" + (c.getPosition()+1) + "\n" + "包名：" + package_name
						+ "\n" + "程序名：" + app_name + "\n" + "操作时间"
						+ action_time + "\n" + "操作类型：" + comments+action_type + "\n"
						+ "是否正在运行：" + is_running + "\n");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			c.close();
			db.close();
		}

		return list;
	}

	public List<AppInfo> getWasRunningApp() {
		List<AppInfo> allWasRunningAppInfo = new ArrayList<AppInfo>();

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;

		try {
			c = db.query(RunningAppInfo_TABLE_NAME, null, null, null, null,
					null, null, null);
			while (c.moveToNext()) {
				if (c.getString(c.getColumnIndex("is_running")).contains("YES")) {

					AppInfo eachWasRunningApp = new AppInfo();

					eachWasRunningApp.setPackage_name(c.getString(c
							.getColumnIndex("package_name")));
					eachWasRunningApp.setApp_name(c.getString(c
							.getColumnIndex("app_name")));
					eachWasRunningApp.setAction_time(c.getString(c
							.getColumnIndex("action_time")));
					eachWasRunningApp.setAction_type(c.getString(c
							.getColumnIndex("action_type")));
					eachWasRunningApp.setIs_running(c.getString(c
							.getColumnIndex("is_running")));
					allWasRunningAppInfo.add(eachWasRunningApp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			c.close();
			db.close();
		}
		return allWasRunningAppInfo;
	}

	public void updateDB(List<AppInfo> wasRunningApp, List<AppInfo> isRunningApp) {
		Log.d("ZhouXingyue", "update5");
		Log.d("ZhouXingyue", wasRunningApp.size() + "");
		// 判断是否有新关闭的程序
		for (AppInfo eachWasRunningApp : wasRunningApp) {
			boolean closePreviousApp = true;
			boolean openNewApp;

			for (AppInfo eachIsRunningApp : isRunningApp) {
				if (eachWasRunningApp.getPackage_name().contains(
						eachIsRunningApp.getPackage_name())) {
					closePreviousApp = false;
					break;
				}

			}
			if (closePreviousApp) {
				updateRunningToStop(eachWasRunningApp);
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd   HH:mm:ss");
				Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
				String actionTime = formatter.format(curDate);
				addCloseAppItem(eachWasRunningApp, actionTime);
			}

		}
		Log.d("ZhouXingyue", "update");
		// 判断是否有新打开的程序
		for (AppInfo eachIsRunningApp : isRunningApp) {
			boolean openNewApp = true;
			// boolean openNewApp;

			for (AppInfo eachWasRunningApp : wasRunningApp) {
				if (eachIsRunningApp.getPackage_name().contains(
						eachWasRunningApp.getPackage_name())) {
					openNewApp = false;
					break;
				}

			}
			if (openNewApp) {

				addOpenAppItem(eachIsRunningApp);
			}

		}
		Log.d("ZhouXingyue", "update open");
	}

	// public void updateOpenAction(List<AppInfo> wasRunningApp, List<AppInfo>
	// isRunningApp){
	// for (AppInfo eachIsRunningApp : isRunningApp) {
	// boolean openNewApp = true;
	// //boolean openNewApp;
	//
	// for (AppInfo eachWasRunningApp : wasRunningApp) {
	// if (eachIsRunningApp.getPackage_name().contains(eachWasRunningApp
	// .getPackage_name())) {
	// openNewApp = false;
	// break;
	// }
	//
	// }
	// if (openNewApp) {
	//
	// addOpenAppItem(eachIsRunningApp);
	// }
	//
	// }
	// Log.d("ZhouXingyue", "update open");
	// }

	// 存储程序操作信息，用于输出xml
	public List<String[]> saveAppOperationRecord() {
		List<String[]> allAppOperation = new ArrayList<String[]>();

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;

		try {
			// String[] eachGPS = { "", "", "", "", "" };
			c = db.query(RunningAppInfo_TABLE_NAME, null, null, null, null,
					null, null, null);

			while (c.moveToNext()) {
				String[] eachAppOperation = { "", "", "", "" };
				eachAppOperation[0] = "ID";

				String appName = c.getString(c.getColumnIndex(APP_NAME));
				eachAppOperation[1] = appName;

				String actionType = c.getString(c.getColumnIndex(ACTION_TYPE));
				eachAppOperation[2] = actionType;

				String actionTime = c.getString(c.getColumnIndex(ACTION_TIME));
				eachAppOperation[3] = actionTime;

				allAppOperation.add(eachAppOperation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			c.close();
			db.close();
		}

		return allAppOperation;
	}

}
