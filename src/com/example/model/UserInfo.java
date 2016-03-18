package com.example.model;

import java.io.Serializable;

public class UserInfo implements Serializable{
private static final long serialVersionUID = 1430504390706716279L;
private String username;//
private boolean userstatus;//用户状态
private String user_img_url;//用户的头像获取连接
private String userId;



public UserInfo() {
	super();
	this.username = "";
	this.userstatus=false;
	this.user_img_url = "";
}
public String getUserId(){
	return userId;
}
public void setUserId(String id)
{
	userId=id;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public boolean isUserstatus() {
	return userstatus;
}
public void setUserstatus(boolean userstatus) {
	this.userstatus = userstatus;
}
public String getUser_img_url() {
	return user_img_url;
}
public void setUser_img_url(String user_img_url) {
	this.user_img_url = user_img_url;
}
public static long getSerialversionuid() {
	return serialVersionUID;
}

}
