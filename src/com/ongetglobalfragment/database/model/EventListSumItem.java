package com.ongetglobalfragment.database.model;

public class EventListSumItem {

	private int listSum;
	private int ymd;
	private String place;
	
	public EventListSumItem() {
		this.listSum=0;
		this.ymd=0;
	}
	public int getListSum() {
		return listSum;
	}
	public void setListSum(int listSum) {
		this.listSum = listSum;
	}
	public int getYmd() {
		return ymd;
	}
	public void setYmd(int ymd) {
		this.ymd = ymd;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
}
