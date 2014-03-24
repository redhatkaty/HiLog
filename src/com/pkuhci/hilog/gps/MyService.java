package com.pkuhci.hilog.gps;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * �������ʱ���޸İ汾 ��ȡgpsλ����Ϣ��service
 * 
 */
public class MyService extends Service implements LocationListener {
	private int count;
	private boolean myFlag;
	LocationManager locationManager;
	private GPSDatabase db;

	
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void onCreate() {
		super.onCreate();
		db = new GPSDatabase(MyService.this);// ��ʼ�����ݿ��ȡ��
		String serviceName = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) getSystemService(serviceName);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);// �Ƿ��ȡ�߶�
		criteria.setBearingRequired(false);// �Ƿ��ȡ��λ
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria, true);

		// try {
		// Thread.sleep(10000); //ֱ�ӵ���
		// } catch (InterruptedException e) {
		// return;
		// }
		if(provider!=null)
			{Location location = locationManager.getLastKnownLocation(provider);
		updateWithNewLocation(location);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				10000, 1l, this);
		Toast.makeText(MyService.this, "ˢ����", Toast.LENGTH_LONG).show();
		}
		else{
			
					Toast.makeText(MyService.this, "��Ǹ�������ֻ���ǰ������ʹ��GPS��λ���ܡ����������ֻ������п���ʹ��GPS���ǡ�", Toast.LENGTH_LONG).show();
					
			
			stopService(new Intent(MyService.this,MyService.class));
			
            }
	}

	/**
	 * ʵ��һ��λ�ñ仯�ļ�����
	 */
	private final LocationListener locationListener = new LocationListener() {
		// ��λ�øı�ʱ����
		
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			updateWithNewLocation(location);
		}

		// ��λ����Ϣ���ɻ�ȡʱ
		
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			updateWithNewLocation(null);
		}

		
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

	};

	// �����������Ҫ��ͨ��location�õ���γ����Ϣ��Ȼ��Ѿ�γ����Ϣͨ���㲥���͸�ǰ����ʾ��Activity,��Ϊserviceû��UI��������������
	private void updateWithNewLocation(Location location) {
		String latLongString;

		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			double alt = location.getAltitude();

			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd   HH:mm:ss");
			Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��

			String gpsTime = formatter.format(curDate);

			String currentLocation = "can't parse the location";
			currentLocation = parseLocationFromRefData(lat, lng);

			latLongString = "Time:" + gpsTime + "lat:" + lat + " &lng:" + lng
					+ "& alt:" + alt + "Location:" + currentLocation;
			System.out.println("latLongString=" + latLongString);

			db.insertGPS(lat + "", lng + "", gpsTime, currentLocation);

			// serviceͨ���㲥������Ϣ��activity
			Intent intent = new Intent();
			intent.putExtra("lat", lat);
			intent.putExtra("lng", lng);
			intent.putExtra("time", gpsTime);
			intent.putExtra("location", currentLocation);
			intent.setAction("android.intent.action.test");// action���������ͬ
			sendBroadcast(intent);
		} else {
			latLongString = "�޷���ȡ������Ϣ";
		}
	}

	
	public void onLocationChanged(Location location) {

		// TODO Auto-generated method stub

		// �ú���Ϊϵͳ������ÿ��һ����ʱ�����Զ����е���

		// location change

		// Log.e(TAG, "location change");

		updateWithNewLocation(location);

	}

	
	public void onProviderDisabled(String provider) {

		// TODO Auto-generated method stub

		// provider disable

		// Log.e(TAG,"providerdisabled");

	}

	
	public void onProviderEnabled(String provider) {

		// TODO Auto-generated method stub

		// providerEnabled

		// Log.e(TAG,"providerenabled");

	}

	
	public void onStatusChanged(String provider, int status, Bundle extras) {

		// TODO Auto-generated method stub

		// statuschanged

		// Log.e(TAG, "statusChanged");

	}

	
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.myFlag = false;
		locationManager.removeUpdates(this);
		Toast.makeText(MyService.this, "GPS��̨����ֹͣ", Toast.LENGTH_LONG).show();

	}

	public String parseLocation(double lat, double lng) {
		String addressString = "NONE";
		// Geocoder gc=new Geocoder(this,Locale.getDefault());
		// //�ڶ������������趨��Ϣ������,Ĭ��ΪLocale.getDefault()
		Geocoder gc = new Geocoder(this, Locale.CHINA); // �ڶ������������趨��Ϣ�����ԣ�����ΪLocale.CHINA
		try {
			// ȡ�õ�ַ��ص�һЩ��Ϣ�����ȡ�γ��
			// List<Address> addresses=gc.getFromLocation(latitude,
			// longitude,1);
			List<Address> addresses = gc.getFromLocation(lat, lng, 1);
			StringBuilder sb = new StringBuilder();
			if (addresses.size() > 0) {
				Address address = addresses.get(0);

				for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
					sb.append(address.getAddressLine(i)).append("\n");
				sb.append(address.getLocality()).append("\n");
				sb.append(address.getPostalCode()).append("\n");
				sb.append(address.getCountryName());
				if (sb == null) {
					addressString = "�޶�Ӧ��Ϣ";
				} else
					addressString = sb.toString();

			}

		} catch (IOException e) {
		}
		return addressString;

	}
	
	public String parseLocationFromRefData(double userLat, double userLng ) {
		String addressString = "NONE";
		List<ParseLocation> allRef=GPSRefData.initGPSRefData();
		int allRefNum=allRef.size();
		String nearestLocation;
		String tempLocation=allRef.get(0).getLocation();
		double tempLatErr=Math.abs(allRef.get(0).getLatitude()-userLat);
		double tempLngErr=Math.abs(allRef.get(0).getLongitude()-userLng);
		double tempTotalErr=tempLatErr+tempLngErr;
		String curLocation;
		double curLatErr=99999;
		double curLngErr=99999;
		double curTotalErr;
		for (int i=1;i<allRefNum;i++){
			 curLatErr=Math.abs(allRef.get(i).getLatitude()-userLat);
			 curLngErr=Math.abs(allRef.get(i).getLongitude()-userLng);
			 curTotalErr=curLatErr+curLngErr;
			 if( curTotalErr<=tempTotalErr){
				 tempTotalErr=curTotalErr;
				 tempLocation=allRef.get(i).getLocation();
			 }
		}
			
		nearestLocation=tempLocation;
		
		return nearestLocation;

	}

}

//
// import java.io.IOException;
// import java.text.SimpleDateFormat;
// import java.util.Date;
// import java.util.List;
// import java.util.Locale;
//
// import android.app.Service;
// import android.content.Context;
// import android.content.Intent;
// import android.location.Address;
// import android.location.Criteria;
// import android.location.Geocoder;
// import android.location.Location;
// import android.location.LocationListener;
// import android.location.LocationManager;
// import android.os.Bundle;
// import android.os.Handler;
// import android.os.IBinder;
//
//
//
// /**�������֮ǰ�İ汾:
// * ��ȡgpsλ����Ϣ��service
// *
// */
// public class MyService extends Service{
// private int count;
// private boolean myFlag;
// LocationManager locationManager;
// private GPSDatabase db;
//
// 
// public IBinder onBind(Intent intent) {
// // TODO Auto-generated method stub
// return null;
// }
//
// 
// public void onCreate() {
// super.onCreate();
// db = new GPSDatabase(MyService.this);//��ʼ�����ݿ��ȡ��
// String serviceName = Context.LOCATION_SERVICE;
// locationManager = (LocationManager)getSystemService(serviceName);
// Criteria criteria = new Criteria();
// criteria.setAccuracy(Criteria.ACCURACY_FINE);
// criteria.setAltitudeRequired(false);
// criteria.setBearingRequired(false);
// criteria.setCostAllowed(true);
// criteria.setPowerRequirement(Criteria.POWER_LOW);
// String provider = locationManager.getBestProvider(criteria, true);
// Location location = locationManager.getLastKnownLocation(provider);
// updateWithNewLocation(location);
// locationManager.requestLocationUpdates(provider, 2000, 10,locationListener);
// }
// /**
// * ʵ��һ��λ�ñ仯�ļ�����
// */
// private final LocationListener locationListener = new LocationListener(){
// //��λ�øı�ʱ����
// 
// public void onLocationChanged(Location location) {
// // TODO Auto-generated method stub
// updateWithNewLocation(location);
// }
//
// //��λ����Ϣ���ɻ�ȡʱ
// 
// public void onProviderDisabled(String provider) {
// // TODO Auto-generated method stub
// updateWithNewLocation(null);
// }
//
// 
// public void onProviderEnabled(String provider) {
// // TODO Auto-generated method stub
//
// }
//
// 
// public void onStatusChanged(String provider, int status, Bundle extras) {
// // TODO Auto-generated method stub
//
// }
//
// };
//
// //�����������Ҫ��ͨ��location�õ���γ����Ϣ��Ȼ��Ѿ�γ����Ϣͨ���㲥���͸�ǰ����ʾ��Activity,��Ϊserviceû��UI��������������
// private void updateWithNewLocation(Location location) {
// String latLongString;
// if (location != null) {
// double lat = location.getLatitude();
// double lng = location.getLongitude();
//
//
// SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd   hh:mm:ss");
// Date curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��
//
// String gpsTime = formatter.format(curDate);
//
// String currentLocation="can't parse the location";
// currentLocation=parseLocation(lat,lng);
//
// latLongString = "Time:"+gpsTime+"lat:" + lat + " &lng:" +
// lng+"Location:"+currentLocation;
// System.out.println("latLongString="+latLongString);
//
//
//
// db.insertGPS(lat+"", lng+"",gpsTime,currentLocation);
//
// //serviceͨ���㲥������Ϣ��activity
// Intent intent=new Intent();
// intent.putExtra("lat", lat);
// intent.putExtra("lng", lng);
// intent.putExtra("time", gpsTime);
// intent.putExtra("location", currentLocation);
// intent.setAction("android.intent.action.test");//action���������ͬ
// sendBroadcast(intent);
// }else{
// latLongString = "�޷���ȡ������Ϣ";
// }
// }
//
// 
// public void onDestroy() {
// // TODO Auto-generated method stub
// super.onDestroy();
// this.myFlag = false;
// }
//
// public String parseLocation(double lat,double lng){
// String addressString= "NONE";
// //Geocoder gc=new Geocoder(this,Locale.getDefault());
// //�ڶ������������趨��Ϣ������,Ĭ��ΪLocale.getDefault()
// Geocoder gc=new Geocoder(this,Locale.CHINA); //�ڶ������������趨��Ϣ�����ԣ�����ΪLocale.CHINA
// try{
// //ȡ�õ�ַ��ص�һЩ��Ϣ�����ȡ�γ��
// //List<Address> addresses=gc.getFromLocation(latitude, longitude,1);
// List<Address> addresses=gc.getFromLocation(lat, lng,1);
// StringBuilder sb=new StringBuilder();
// if(addresses.size()>0)
// {
// Address address=addresses.get(0);
//
// for(int i=0;i<address.getMaxAddressLineIndex();i++)
// sb.append(address.getAddressLine(i)).append("\n");
// sb.append(address.getLocality()).append("\n");
// sb.append(address.getPostalCode()).append("\n");
// sb.append(address.getCountryName());
// if(sb==null){
// addressString="�޶�Ӧ��Ϣ";
// }
// else addressString=sb.toString();
//
// }
//
// }catch(IOException e){}
// return addressString;
//
// }
//
// }
