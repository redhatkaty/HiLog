package com.pkuhci.hilog.gps;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pkuhci.hilog.R;
import com.pkuhci.hilog.WelcomeScreen;



public class GPSService extends Activity implements OnClickListener{
	private Button btn1,btn2,btn3,btn4,back_button;
	private TextView tv01;
	private Handler myHandler;
	private GPSDatabase db ;
	MyReceiver receiver;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("start onCreate~~~");
        setContentView(R.layout.gps_service_layout);
        getViews();
        db = new GPSDatabase(this);
        Bundle bundle = this.getIntent().getExtras();
        if(bundle!=null){
        	double lat = bundle.getDouble("lat");
        	double lng = bundle.getDouble("lng");
        	String time=bundle.getString("time");
        	String location = bundle.getString("location");
        	tv01.setText("time:"+time+lat+"-"+lng+location);
        }
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        back_button.setOnClickListener(this);
    }
    
    public void getViews(){
    	btn1 = (Button)findViewById(R.id.btn1);
    	btn2 = (Button)findViewById(R.id.btn2);
    	btn3 = (Button)findViewById(R.id.btn3);
    	btn4 = (Button)findViewById(R.id.btn4);
    	back_button = (Button)findViewById(R.id.back_button);
    	tv01 = (TextView)findViewById(R.id.tv01);
    }

	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btn1:
			//��������
			{Toast.makeText(GPSService.this, "����GPS��̨����", Toast.LENGTH_SHORT).show();
			startService(new Intent(GPSService.this,MyService.class));}
			break;
		case R.id.btn2:
			
//			//���ע�������
//			if(receiver!=null){
//				GPSService.this.unregisterReceiver(receiver);
//			}
			
			//ֹͣ����
			stopService(new Intent(GPSService.this,MyService.class));
			//Toast.makeText(GPSService.this, "��̨����ֹͣ", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn3:
		{Toast.makeText(GPSService.this, "��ʾ��¼��GPS����", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(GPSService.this,GPSServiceShow.class));}
			break;
		case R.id.btn4:
			db.deleteGPS();
			Toast.makeText(GPSService.this, "ԭ��GPS�����ѱ�ɾ��", Toast.LENGTH_SHORT).show();
		case R.id.back_button:
			startActivity(new Intent(GPSService.this,WelcomeScreen.class));
		default:
			break;
		}
	}
	
	public class MyReceiver extends BroadcastReceiver {
		//�Զ���һ���㲥������
		
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			System.out.println("OnReceiver");
			
			//�жϽ��ܵ��Ĺ㲥�Ƿ����Լ�Ҫ�Ĺ㲥������ǣ�������յ�������,ע������action������Ҫ�ͷ��͵�������ͬ����android.intent.action.test
			if(intent.getAction().equals("android.intent.action.test")){
				Bundle bundle=intent.getExtras();
				double lat = bundle.getDouble("lat");
				double lng = bundle.getDouble("lng");
				String time=bundle.getString("time");
				String location = bundle.getString("location");
				tv01.setText("ǰһ��: "+lat+"-"+lng+location);//����textview����ʾ��ϢΪ���յ��ľ�γ����Ϣ
			}

		}
		public MyReceiver(){
			System.out.println("MyReceiver");
			//���캯������һЩ��ʼ�����������������κ�����
		}

	}

	
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("start onDestroy~~~");
		if(receiver!=null){
			GPSService.this.unregisterReceiver(receiver);
		}
	}

	
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println("start onPause~~~");
	}

	
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		System.out.println("start onRestart~~~");
	}

	
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("start onResume~~~");
		//ע���������֮���԰�ע�������д��onResume�У�����Ϊ����Ļ��ת�󣨺������������������onDestroy������
		receiver=new MyReceiver();
		IntentFilter filter=new IntentFilter();
		filter.addAction("android.intent.action.test");
		GPSService.this.registerReceiver(receiver,filter);
	}

	
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		System.out.println("start onStart~~~");
	}

	
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.out.println("start onStop~~~");
	}
	
	
	
}