package com.pkuhci.hilog.gps;

import java.util.ArrayList;
import java.util.List;

public class GPSRefData {
	static List<ParseLocation> list = new ArrayList<ParseLocation>();
	
	public static List<ParseLocation> initGPSRefData(){
		
		list.add(new ParseLocation("������",116.16,39.92));
		list.add(new ParseLocation("������ ������ ������ѧ����",116.30,39.99));
		list.add(new ParseLocation("������ ������ �йش�",116.31,39.98));
		list.add(new ParseLocation("������ ������ κ����",116.32,39.96));
		list.add(new ParseLocation("������ ������ �����ׯ",116.31,39.97));
		list.add(new ParseLocation("������ ������ �廪��ѧ",116.32,40.01));
		list.add(new ParseLocation("������ ������ ������ѧ����",116.30,39.99));
		list.add(new ParseLocation("������ ������ �������պ����ѧ",116.34,39.98));
		list.add(new ParseLocation("������ ������ �����Ƽ���ѧ",116.35,39.99));
		list.add(new ParseLocation("������ ������ ����ͼ���",116.32,39.94));
		list.add(new ParseLocation("������ ��̨��",116.29,39.86));
		list.add(new ParseLocation("������ ������",116.44,39.92));
		list.add(new ParseLocation("������ ������",116.40,39.93));
		list.add(new ParseLocation("������ ������",116.35,39.73));
		list.add(new ParseLocation("������ ʯ��ɽ��",116.19,39.90));
		list.add(new ParseLocation("������ �Ա�",116.37,40.08));
		list.add(new ParseLocation("������ ����",116.35,39.63));
		list.add(new ParseLocation("������ ����",116.10,39.93));
		list.add(new ParseLocation("������ �Զ�",116.72,39.93));
		list.add(new ParseLocation("������ ������",116.28,40.04));
		list.add(new ParseLocation("������ ������ ����Ժ",116.31,39.94));
		
		return list;
	}
	
}
