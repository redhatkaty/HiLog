package com.pkuhci.hilog.music;

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
public class MusicHistoryDatabase extends SQLiteOpenHelper{

	private static final String DB_NAME = "musichistory3.database3";
	private static final int VERSION = 1;
	
	/**
	 * GPS表
	 */
	public static final String MusicHistory_TABLE = "MusicHistoryTable3";
	
	public MusicHistoryDatabase(Context context)
	{
		this(context, DB_NAME, null, VERSION);
	}
	
	public MusicHistoryDatabase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		createMusicHistory(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	//创建gps表
	public void createMusicHistory(SQLiteDatabase db){
		String sql = "create table "+MusicHistory_TABLE+"(_id integer primary key autoincrement,NO varchar(40),playMusicTime varchar(40),playedName varchar(40),musicid varchar(40),duration varchar(40),artist varchar(40),title varchar(40),type varchar(40),albumName varchar(40),composer varchar(40),year varchar(40),size varchar(40),mediaUri varchar(40))";
		db.execSQL(sql);
	}
	
	//插入gps表
	public void insertMusicHistory(String playMusicTime,String playedName, String musicid, String duration,String artist,String title,String type,String albumName,String composer,String year,String size,String mediaUri){
		
		ContentValues c = new ContentValues();
		
		c.put("playMusicTime", playMusicTime);
		c.put("mediaUri", mediaUri);
		c.put("type", type);
		c.put("playedName", playedName);
		c.put("musicid", musicid);
		c.put("albumName", albumName);
		c.put("artist", artist);
		c.put("composer", composer);
		c.put("duration", duration);
		c.put("size", size);
		c.put("title", title);
		c.put("year", year);
		
		MusicHistoryDatabase.this.getWritableDatabase().insert(MusicHistory_TABLE, null, c);
	}
	
	//查找gps信息
	public List<String> listMusicHistory(){
		List<String> list = new ArrayList<String>();
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
int i=1;
		try
		{
			c = db.query(MusicHistory_TABLE, null, null, null,
					null, null, null, null);
			while (c.moveToNext())
			{
				String musicid = c.getString(c.getColumnIndex("musicid"));
				String mediaUri = c.getString(c.getColumnIndex("mediaUri"));
				String type = c.getString(c.getColumnIndex("type"));
				String playedName=c.getString(c.getColumnIndex("playedName"));
				String artist=c.getString(c.getColumnIndex("artist"));
				
				String year = c.getString(c.getColumnIndex("year"));
				String title = c.getString(c.getColumnIndex("title"));
				String size = c.getString(c.getColumnIndex("size"));
				String duration=c.getString(c.getColumnIndex("duration"));
				String composer = c.getString(c.getColumnIndex("composer"));
				String albumName = c.getString(c.getColumnIndex("albumName"));
				String playMusicTime = c.getString(c.getColumnIndex("playMusicTime"));
				
				
				list.add("序号:"+i+++"\n"+
						  "播放时间:"+playMusicTime+"\n"+
						  "文件名称:"+playedName +"\n"+
						   "音频时长:"+duration +"\n"+
						  "歌手名称:"+artist  +"\n"+
						   "专辑名称:"+albumName +"\n"
						 

							);
				
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
	public void deleteMusicRecord(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(MusicHistory_TABLE, null, null);
		db.close();
	}

	
	//存储music信息，用于输出xml
	public List<String[]> saveMusicRecord(){
		List<String[]> allMusic = new ArrayList<String[]>();
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;

		try
		{
			//String[] eachGPS = { "", "", "", "", "" };
			c = db.query(MusicHistory_TABLE, null, null, null,
					null, null, null, null);
			while (c.moveToNext())
			{
				String[] eachMusic = { "", "", "", "", "" ,"", "", "", "", "" ,"", "", ""};
				//String musicid = c.getString(c.getColumnIndex("musicid"));
				eachMusic[0]="ID";
				
				String playMusicTime = c.getString(c.getColumnIndex("playMusicTime"));
				eachMusic[1]=playMusicTime;
				
				String playedName = c.getString(c.getColumnIndex("playedName"));
				eachMusic[2]=playedName;
				
				String musicid=c.getString(c.getColumnIndex("musicid"));
				eachMusic[3]=musicid;
				String duration=c.getString(c.getColumnIndex("duration"));
				eachMusic[4]=duration;
				String artist=c.getString(c.getColumnIndex("artist"));
				eachMusic[5]=artist;
				String title=c.getString(c.getColumnIndex("title"));
				eachMusic[6]=title;
				String type=c.getString(c.getColumnIndex("type"));
				eachMusic[7]=type;
				String albumName=c.getString(c.getColumnIndex("albumName"));
				eachMusic[8]=albumName;
				String composer=c.getString(c.getColumnIndex("composer"));
				eachMusic[9]=composer;
				String year=c.getString(c.getColumnIndex("year"));
				eachMusic[10]=year;
				String size=c.getString(c.getColumnIndex("size"));
				eachMusic[11]=size;
				String mediaUri=c.getString(c.getColumnIndex("mediaUri"));
				eachMusic[12]=mediaUri;
				allMusic.add(eachMusic);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			c.close();
			db.close();
		}
		
		return allMusic;
	}
	
	
	
}
