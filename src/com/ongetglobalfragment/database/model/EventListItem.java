package com.ongetglobalfragment.database.model;

/** item for list view
 * @author Y.aratake
 * @since 2014/09/10
 * @version 1.0
 * @update 
 */
public class EventListItem {	
	
	
	private int listNumber;
	private String nameLa;
	private String nameFi;
	private String kanaLa;
	private String kanaFi;
	private int ymd;
	private int pay;
	private int sex;
	private int age;
	
	private String position;
	private int payFlg;
	private int attend;
	private String place;
	private int deleteFlg;
	private String phone;
	private String another;

	public EventListItem() {
		this.nameLa="";
		this.nameFi="";
		this.kanaLa="";
		this.kanaFi="";
		this.setYmd(0);
		this.pay = 0;
		this.sex = 0;
		this.age = 0;
		this.position = "";
		this.payFlg = 0;
		this.attend = 0;
		this.setPlace("");
		this.deleteFlg = 0;
	}


	public int getListNumber() {
		return listNumber;
	}


	public void setListNumber(int listNumber) {
		this.listNumber = listNumber;
	}


	public String getNameLa() {
		return nameLa;
	}


	public void setNameLa(String nameLa) {
		this.nameLa = nameLa;
	}


	public String getNameFi() {
		return nameFi;
	}


	public void setNameFi(String nameFi) {
		this.nameFi = nameFi;
	}


	public String getKanaLa() {
		return kanaLa;
	}


	public void setKanaLa(String kanaLa) {
		this.kanaLa = kanaLa;
	}


	public String getKanaFi() {
		return kanaFi;
	}


	public void setKanaFi(String kanaFi) {
		this.kanaFi = kanaFi;
	}


	public int getYmd() {
		return ymd;
	}

	public void setYmd(int ymd) {
		this.ymd = ymd;
	}

	public int getPay() {
		return pay;
	}

	public void setPay(int pay) {
		this.pay = pay;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getPayFlg() {
		return payFlg;
	}

	public void setPayFlg(int payFlg) {
		this.payFlg = payFlg;
	}

	public int getAttend() {
		return attend;
	}

	public void setAttend(int attend) {
		this.attend = attend;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}


	public int getDeleteFlg() {
		return deleteFlg;
	}


	public void setDeleteFlg(int deleteFlg) {
		this.deleteFlg = deleteFlg;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getAnother() {
		return another;
	}


	public void setAnother(String another) {
		this.another = another;
	}


}
