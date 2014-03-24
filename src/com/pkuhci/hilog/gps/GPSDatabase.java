package com.pkuhci.hilog.gps;



import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 *	用于存储GPS信息的数据实现类
 */
public class GPSDatabase extends SQLiteOpenHelper{

	private static final String DB_NAME = "gps.lifelog2";
	private static final int VERSION = 1;
	
	/**
	 * GPS表
	 */
	public static final String GPS_TABLE = "GPSTableLifelog2";
	
	public GPSDatabase(Context context)
	{
		this(context, DB_NAME, null, VERSION);
	}
	
	public GPSDatabase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		createGPS(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	//创建gps表
	public void createGPS(SQLiteDatabase db){
		String sql = "create table "+GPS_TABLE+"(_id integer primary key autoincrement,time varchr(40),longitude varchar(40),latitude varchar(40),location varchar(60))";
		db.execSQL(sql);
	}
	
	//插入gps表
	public void insertGPS(String longitude,String latitude,String time,String location){
		ContentValues c = new ContentValues();
		c.put("time", time);
		c.put("longitude", longitude);
		c.put("latitude", latitude);
		c.put("location", location);
		GPSDatabase.this.getWritableDatabase().insert(GPS_TABLE, null, c);
	}
	
	//查找gps信息
	public List<String> listGPS(){
		List<String> list = new ArrayList<String>();
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;

		try
		{
			c = db.query(GPS_TABLE, null, null, null,
					null, null, null, null);
			while (c.moveToNext())
			{
				
				String longitude = c.getString(c.getColumnIndex("longitude"));
				String latitude = c.getString(c.getColumnIndex("latitude"));
				String time = c.getString(c.getColumnIndex("time"));
				String location=c.getString(c.getColumnIndex("location"));
				list.add("时间:"+time+"\n"+"经度:"+longitude+"\n"+"纬度:"+latitude+"\n"+"参考位置："+location);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			c.close();
			db.close();
		}
		
		return list;
	}
	
	//删除gps信息
	public void deleteGPS(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(GPS_TABLE, null, null);
		db.close();
	}
	//存储gps信息，用于输出xml
	public List<String[]> saveGPS(){
		List<String[]> allGPS = new ArrayList<String[]>();
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;

		try
		{
			//String[] eachGPS = { "", "", "", "", "" };
			c = db.query(GPS_TABLE, null, null, null,
					null, null, null, null);
			
//			for(int i=0;i<20;i++){
//				c.moveToNext();
//			}

			
			while (c.moveToNext())
			{
				
				c.moveToNext();
				c.moveToNext();//moveToNext的使用次数可以控制采样频率的大小
			
				
				String[] eachGPS = { "", "", "", "", "" };
				String longitude = c.getString(c.getColumnIndex("longitude"));
				eachGPS[1]=longitude;
				
				String latitude = c.getString(c.getColumnIndex("latitude"));
				eachGPS[2]=latitude;
				
				String time = c.getString(c.getColumnIndex("time"));
				eachGPS[0]=time;
				
				String location=c.getString(c.getColumnIndex("location"));
				eachGPS[3]=location;;
				allGPS.add(eachGPS);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			c.close();
			db.close();
		}
		
		return allGPS;
	}
	
	
	
}
