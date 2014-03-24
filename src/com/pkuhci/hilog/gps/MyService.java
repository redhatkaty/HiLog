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
 * 真机测试时的修改版本 获取gps位置信息的service
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
		db = new GPSDatabase(MyService.this);// 初始化数据库获取类
		String serviceName = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) getSystemService(serviceName);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);// 是否获取高度
		criteria.setBearingRequired(false);// 是否获取方位
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria, true);

		// try {
		// Thread.sleep(10000); //直接调用
		// } catch (InterruptedException e) {
		// return;
		// }
		if(provider!=null)
			{Location location = locationManager.getLastKnownLocation(provider);
		updateWithNewLocation(location);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				10000, 1l, this);
		Toast.makeText(MyService.this, "刷新中", Toast.LENGTH_LONG).show();
		}
		else{
			
					Toast.makeText(MyService.this, "抱歉！您的手机当前不允许使用GPS定位功能。请在您的手机设置中开启使用GPS卫星。", Toast.LENGTH_LONG).show();
					
			
			stopService(new Intent(MyService.this,MyService.class));
			
            }
	}

	/**
	 * 实现一个位置变化的监听器
	 */
	private final LocationListener locationListener = new LocationListener() {
		// 当位置改变时出发
		
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			updateWithNewLocation(location);
		}

		// 当位置信息不可获取时
		
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

	// 这个方法很重要，通过location得到经纬度信息，然后把经纬度信息通过广播发送给前段显示的Activity,因为service没有UI，所以这样做。
	private void updateWithNewLocation(Location location) {
		String latLongString;

		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			double alt = location.getAltitude();

			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd   HH:mm:ss");
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间

			String gpsTime = formatter.format(curDate);

			String currentLocation = "can't parse the location";
			currentLocation = parseLocationFromRefData(lat, lng);

			latLongString = "Time:" + gpsTime + "lat:" + lat + " &lng:" + lng
					+ "& alt:" + alt + "Location:" + currentLocation;
			System.out.println("latLongString=" + latLongString);

			db.insertGPS(lat + "", lng + "", gpsTime, currentLocation);

			// service通过广播发送信息给activity
			Intent intent = new Intent();
			intent.putExtra("lat", lat);
			intent.putExtra("lng", lng);
			intent.putExtra("time", gpsTime);
			intent.putExtra("location", currentLocation);
			intent.setAction("android.intent.action.test");// action与接收器相同
			sendBroadcast(intent);
		} else {
			latLongString = "无法获取地理信息";
		}
	}

	
	public void onLocationChanged(Location location) {

		// TODO Auto-generated method stub

		// 该函数为系统函数，每隔一定的时间便会自动进行调用

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
		Toast.makeText(MyService.this, "GPS后台服务停止", Toast.LENGTH_LONG).show();

	}

	public String parseLocation(double lat, double lng) {
		String addressString = "NONE";
		// Geocoder gc=new Geocoder(this,Locale.getDefault());
		// //第二个参量用于设定信息的语言,默认为Locale.getDefault()
		Geocoder gc = new Geocoder(this, Locale.CHINA); // 第二个参量用于设定信息的语言，中文为Locale.CHINA
		try {
			// 取得地址相关的一些信息、经度、纬度
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
					addressString = "无对应信息";
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
// /**真机测试之前的版本:
// * 获取gps位置信息的service
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
// db = new GPSDatabase(MyService.this);//初始化数据库获取类
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
// * 实现一个位置变化的监听器
// */
// private final LocationListener locationListener = new LocationListener(){
// //当位置改变时出发
// 
// public void onLocationChanged(Location location) {
// // TODO Auto-generated method stub
// updateWithNewLocation(location);
// }
//
// //当位置信息不可获取时
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
// //这个方法很重要，通过location得到经纬度信息，然后把经纬度信息通过广播发送给前段显示的Activity,因为service没有UI，所以这样做。
// private void updateWithNewLocation(Location location) {
// String latLongString;
// if (location != null) {
// double lat = location.getLatitude();
// double lng = location.getLongitude();
//
//
// SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd   hh:mm:ss");
// Date curDate = new Date(System.currentTimeMillis());//获取当前时间
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
// //service通过广播发送信息给activity
// Intent intent=new Intent();
// intent.putExtra("lat", lat);
// intent.putExtra("lng", lng);
// intent.putExtra("time", gpsTime);
// intent.putExtra("location", currentLocation);
// intent.setAction("android.intent.action.test");//action与接收器相同
// sendBroadcast(intent);
// }else{
// latLongString = "无法获取地理信息";
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
// //第二个参量用于设定信息的语言,默认为Locale.getDefault()
// Geocoder gc=new Geocoder(this,Locale.CHINA); //第二个参量用于设定信息的语言，中文为Locale.CHINA
// try{
// //取得地址相关的一些信息、经度、纬度
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
// addressString="无对应信息";
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
