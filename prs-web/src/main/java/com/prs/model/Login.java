package com.prs.model;

import jakarta.persistence.Entity;


public class Login {
private String userName;
private String passWord;




//Getters and setters
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getPassWord() {
	return passWord;
}
public void setPassWord(String passWord) {
	this.passWord = passWord;
}

	
}
