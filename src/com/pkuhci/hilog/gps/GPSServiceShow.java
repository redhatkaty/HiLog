package com.pkuhci.hilog.gps;



import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.pkuhci.hilog.R;
import com.pkuhci.hilog.WelcomeScreen;



/**
 * 将gps信息保存在xml文件中的activity
 *
 */
public class GPSServiceShow extends Activity{
	
	private ListView listview ;
	private GPSDatabase db ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gps_service_show);
		getViews();
		db = new GPSDatabase(this);
		List<String> list = db.listGPS();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.adapter_for_gps,list);
		listview.setAdapter(adapter);
		
		final List<String[]> allGPS = db.saveGPS();
		
//		Button outputButton = (Button) this.findViewById(R.id.makeXML_button);
//		outputButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				/*
//				 * 将GPS记录xml文件保存在sd卡中
//				 */
//				 try {
//					 GPSToXML gx = new GPSToXML();
//						
//							AllRecord.gpsRecord=gx.getXMLString(allGPS);
//							SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyyMMdd_HHmmss");     
//							 Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间   
//							String   saveFileTime   =   formatter.format(curDate); 
//							 File fileFolder = new File(Environment.getExternalStorageDirectory()+File.separator+"HiLog_LifeLog"+File.separator);  
//								
//						        if(!fileFolder.exists()) 
//						        	fileFolder.mkdir(); 
//						        File file = new File(Environment.getExternalStorageDirectory()+File.separator+"HiLog_LifeLog"+File.separator+"GPSRecord.xml");  
//								
//							 file.createNewFile();  
//					 FileOutputStream fileOutputStream = new FileOutputStream(file);
//					fileOutputStream.write(AllRecord.gpsRecord.getBytes()); 
//					fileOutputStream.close(); 
//					Toast.makeText(getApplicationContext(), "GPS记录已保存在SD卡LifeLogTest文件夹中", Toast.LENGTH_SHORT).show();
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}  
//				
////				/*
////				 * 将GPSxml文件保存在系统默认路径程序包下
////				 */
////				try {
////					GPSToXML gx = new GPSToXML();
////				
////					AllRecord.gpsRecord=gx.getXMLString(allGPS);
////					OutputStream out = openFileOutput("GPSRecord1.xml",
////							Context.MODE_WORLD_WRITEABLE);
////					OutputStreamWriter outw = new OutputStreamWriter(out);
////					try {
////						outw.write(AllRecord.gpsRecord);
////						outw.close();
////						out.close();
////						Toast.makeText(getApplicationContext(), "GPS记录已保存在系统默认路径下", Toast.LENGTH_SHORT).show();
////
////					} catch (IOException e) {
////
////					}
////				} catch (FileNotFoundException e) {
////
////				}
//			}
//		});
	
		
        Button showMusic = (Button) findViewById(R.id.back_button);  
        showMusic.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {  
            	Intent i = new Intent(
            			GPSServiceShow.this,
            			WelcomeScreen.class);

				startActivity(i);
            }  
        });  
		
		
	}
	
	

	public void getViews(){
		listview = (ListView)findViewById(R.id.list01);
	}
}
