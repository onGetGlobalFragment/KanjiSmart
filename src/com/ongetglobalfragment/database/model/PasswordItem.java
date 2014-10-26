package com.ongetglobalfragment.database.model;

public class PasswordItem {

	private String passWord;
	private String mailAdress;
	private int lockFlag;
	
	public PasswordItem() {
		this.passWord="";
		this.mailAdress="";
		this.lockFlag=0;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getMailAdress() {
		return mailAdress;
	}

	public void setMailAdress(String mailAdress) {
		this.mailAdress = mailAdress;
	}

	public int getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(int lockFlag) {
		this.lockFlag = lockFlag;
	}

	

}
