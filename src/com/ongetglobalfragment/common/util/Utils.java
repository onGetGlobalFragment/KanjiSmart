package com.ongetglobalfragment.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {

	public Utils() {

	}
	public String getTodayPlaneYmd(){
		Calendar c = Calendar.getInstance();
		//フォーマットパターンを指定して表示する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(c.getTime());
		return date;
	}
	public  String getTodsaySlashYmd(){
		Calendar c = Calendar.getInstance();
		//フォーマットパターンを指定して表示する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String date = sdf.format(c.getTime());
		return date;
	}
	public String getPlaneYmdFromSlashYmd(String slash){

		CharSequence cs = slash.subSequence(0, slash.length());
		StringBuilder sb = new StringBuilder();
		sb.append(cs.charAt(0)).append(cs.charAt(1)).append(cs.charAt(2)).append(cs.charAt(3))
		.append(cs.charAt(5)).append(cs.charAt(6)).append(cs.charAt(8)).append(cs.charAt(9));

		return sb.toString();
	}
	public String getSlashYmdFromPlaneYmd(String s){
		String strymd = "";
		if(s.length()==8){
			CharSequence cs = s.subSequence(0, s.length());
			StringBuilder sb = new StringBuilder();
			sb.append(cs.charAt(0)).append(cs.charAt(1)).append(cs.charAt(2)).append(cs.charAt(3))
			.append("/").append(cs.charAt(4)).append(cs.charAt(5)).append("/").append(cs.charAt(6)).append(cs.charAt(7));
			strymd =  sb.toString();
		}
		return strymd;
	}
	public int getIntYmdFromPlaneYmd(String s){
		int i = 0;
		try{
			i = Integer.parseInt(s);
		}catch(Exception e){
			e.printStackTrace();
		}	
		return i;
	}
	public int getIntYmdFromSlashYmd(String s){
		int i = 0;
		if(s.length()<9)return i;
		CharSequence cs = s.subSequence(0, s.length());
		StringBuilder sb = new StringBuilder();
		sb.append(cs.charAt(0)).append(cs.charAt(1)).append(cs.charAt(2)).append(cs.charAt(3))
		.append(cs.charAt(5)).append(cs.charAt(6)).append(cs.charAt(8)).append(cs.charAt(9));
		i = Integer.parseInt(sb.toString());
		return i;
	}
	
}
