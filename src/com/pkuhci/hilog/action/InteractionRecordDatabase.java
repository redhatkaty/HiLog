package com.pkuhci.hilog.action;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class InteractionRecordDatabase  extends SQLiteOpenHelper{

	private static final String DB_NAME = "interaction.database1";
	private static final int VERSION = 1;
	
	/**
	 * ������¼��
	 */
	public static final String InteractionRecord_TABLE = "InteractionRecordTable1";
	
	public InteractionRecordDatabase(Context context)
	{
		this(context, DB_NAME, null, VERSION);
	}
	
	public InteractionRecordDatabase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		createInteractionRecord(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	//����������¼��
	public void createInteractionRecord(SQLiteDatabase db){
		String sql = "create table "+InteractionRecord_TABLE+"(_id integer primary key autoincrement,NO varchar(60),deviceName varchar(90),interactTime varchar(90),interactionType varchar(90),interactAction varchar(90),interactObject varchar(90),objectName varchar(90),comments varchar(150))";
		db.execSQL(sql);
	}
	
	//���뽻����¼��
	public void insertInteractionRecord(String deviceName,String interactTime, String interactionType, String interactAction,String interactObject,String objectName,String comments){
		
		ContentValues c = new ContentValues();
		
		c.put("deviceName", deviceName);
		c.put("interactTime", interactTime);
		c.put("interactionType", interactionType);
		c.put("interactAction", interactAction);
		c.put("interactObject", interactObject);
		c.put("objectName", objectName);
		c.put("comments", comments);
		
		
		InteractionRecordDatabase.this.getWritableDatabase().insert(InteractionRecord_TABLE, null, c);
	}
	
	//�г�������¼��Ϣ
	public List<String> listInteractionRecord(){
		List<String> list = new ArrayList<String>();
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		int i=1;
		
		try
		{
			c = db.query(InteractionRecord_TABLE, null, null, null,
					null, null, null, null);
			while (c.moveToNext())
			{
				//String NO = c.getString(c.getColumnIndex("NO"));
				String deviceName = c.getString(c.getColumnIndex("deviceName"));
				String interactTime = c.getString(c.getColumnIndex("interactTime"));
				String interactionType=c.getString(c.getColumnIndex("interactionType"));
				String interactAction=c.getString(c.getColumnIndex("interactAction"));
				
				String interactObject = c.getString(c.getColumnIndex("interactObject"));
				String objectName = c.getString(c.getColumnIndex("objectName"));
				String comments = c.getString(c.getColumnIndex("comments"));
				
			
				list.add("���:"+i++ +"\n"+
						  //"�豸��ʶ:"+deviceName+"\n"+
						  "��������ʱ��:"+interactTime +"\n"+
						 // "������������:"+interactionType  +"\n"+
						  "������������:"+interactAction +"\n"
						 // "������������:"+interactObject  +"\n"+
						//  "������������:"+objectName +"\n"+
						 // "��ע:"+comments +"\n"

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
	
	//ɾ��������¼��Ϣ
	public void deleteInteractionRecord(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(InteractionRecord_TABLE, null, null);
		db.close();
	}

	
	//�洢������¼���������xml
	public List<String[]> saveInteractionRecord(){
		List<String[]> allInteractionRecord = new ArrayList<String[]>();
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;

		try
		{
			//String[] eachGPS = { "", "", "", "", "" };
			c = db.query(InteractionRecord_TABLE, null, null, null,
					null, null, null, null);
			while (c.moveToNext())
			{
				String[] eachInteractionRecord = { "", "", "", "", "" ,"", "", ""};
				//String musicid = c.getString(c.getColumnIndex("musicid"));
				eachInteractionRecord[0]="ID";
				
				String deviceName = c.getString(c.getColumnIndex("deviceName"));
				eachInteractionRecord[1]=deviceName;
				
				String interactTime = c.getString(c.getColumnIndex("interactTime"));
				eachInteractionRecord[2]=interactTime;
				
				String interactionType=c.getString(c.getColumnIndex("interactionType"));
				eachInteractionRecord[3]=interactionType;
				String interactAction=c.getString(c.getColumnIndex("interactAction"));
				eachInteractionRecord[4]=interactAction;
				String interactObject=c.getString(c.getColumnIndex("interactObject"));
				eachInteractionRecord[5]=interactObject;
				String objectName=c.getString(c.getColumnIndex("objectName"));
				eachInteractionRecord[6]=objectName;
				String comments=c.getString(c.getColumnIndex("comments"));
				eachInteractionRecord[7]=comments;
				
				allInteractionRecord.add(eachInteractionRecord);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			c.close();
			db.close();
		}
		
		return allInteractionRecord;
	}
	
	
	
}
