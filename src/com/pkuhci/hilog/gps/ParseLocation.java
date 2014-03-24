package com.pkuhci.hilog.gps;

public class ParseLocation {
private String location;
private double  longitude;
private double latitude;
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public double getLongitude() {
	return longitude;
}
public void setLongitude(double longitude) {
	this.longitude = longitude;
}
public double getLatitude() {
	return latitude;
}
public void setLatitude(double latitude) {
	this.latitude = latitude;
}

public ParseLocation( String location,double longitude  ,double latitude  ){
setLocation(location);
setLatitude(latitude);
setLongitude(longitude);
}

}
