package com.ongetglobalfragment.database.validation;

import com.ongetglobalfragment.database.model.EventListItem;
import com.ongetglobalfragment.database.model.EventListSumItem;

public class ValidateEventListItem {

	private EventListItem mEventListItem;
	private EventListSumItem mEventListSumItem;
	
	public ValidateEventListItem(EventListItem i) {
		mEventListItem = new EventListItem();
		mEventListItem = i;
	}
	public ValidateEventListItem(EventListSumItem i) {
		mEventListSumItem = new EventListSumItem();
		mEventListSumItem = i;
	}
	public EventListItem getNotNullEventListItem(){
		if(mEventListItem==null)return null;
		if(mEventListItem.getKanaLa()==null)mEventListItem.setKanaLa("");
		if(mEventListItem.getKanaFi()==null)mEventListItem.setKanaFi("");
		if(mEventListItem.getNameLa()==null)mEventListItem.setNameLa("");
		if(mEventListItem.getNameFi()==null)mEventListItem.setNameFi("");
		if(mEventListItem.getPlace()==null)mEventListItem.setPlace("");
		if(mEventListItem.getAnother()==null)mEventListItem.setAnother("");
		if(mEventListItem.getPosition()==null)mEventListItem.setPosition("");
		return mEventListItem;
	}
	public EventListSumItem getNotNullEventListSumItem(){
		if(mEventListSumItem==null)return null;
		if(mEventListSumItem.getPlace()==null)mEventListSumItem.setPlace("");	
		return mEventListSumItem;
	}

}
