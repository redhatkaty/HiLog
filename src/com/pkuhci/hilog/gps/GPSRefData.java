package com.pkuhci.hilog.gps;

import java.util.ArrayList;
import java.util.List;

public class GPSRefData {
	static List<ParseLocation> list = new ArrayList<ParseLocation>();
	
	public static List<ParseLocation> initGPSRefData(){
		
		list.add(new ParseLocation("北京市",116.16,39.92));
		list.add(new ParseLocation("北京市 海淀区 北京大学东门",116.30,39.99));
		list.add(new ParseLocation("北京市 海淀区 中关村",116.31,39.98));
		list.add(new ParseLocation("北京市 海淀区 魏公村",116.32,39.96));
		list.add(new ParseLocation("北京市 海淀区 海淀黄庄",116.31,39.97));
		list.add(new ParseLocation("北京市 海淀区 清华大学",116.32,40.01));
		list.add(new ParseLocation("北京市 海淀区 北京大学西门",116.30,39.99));
		list.add(new ParseLocation("北京市 海淀区 北京航空航天大学",116.34,39.98));
		list.add(new ParseLocation("北京市 海淀区 北京科技大学",116.35,39.99));
		list.add(new ParseLocation("北京市 海淀区 国家图书馆",116.32,39.94));
		list.add(new ParseLocation("北京市 丰台区",116.29,39.86));
		list.add(new ParseLocation("北京市 朝阳区",116.44,39.92));
		list.add(new ParseLocation("北京市 东城区",116.40,39.93));
		list.add(new ParseLocation("北京市 大兴区",116.35,39.73));
		list.add(new ParseLocation("北京市 石景山区",116.19,39.90));
		list.add(new ParseLocation("北京市 以北",116.37,40.08));
		list.add(new ParseLocation("北京市 以南",116.35,39.63));
		list.add(new ParseLocation("北京市 以西",116.10,39.93));
		list.add(new ParseLocation("北京市 以东",116.72,39.93));
		list.add(new ParseLocation("北京市 海淀区",116.28,40.04));
		list.add(new ParseLocation("北京市 海淀区 紫竹院",116.31,39.94));
		
		return list;
	}
	
}
