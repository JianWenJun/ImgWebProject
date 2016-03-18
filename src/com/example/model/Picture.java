package com.example.model;

import java.io.Serializable;

public class Picture implements Serializable {
/**
	 * 
	 */
private static final long serialVersionUID = 6371345690200437363L;
private String id;//Í¼Æ¬id
private String img_url=null;
private String username;
private String userface_url;
private String title;
private String uploaddata;
private boolean IsGIf;
private boolean IsCollect;
private String[]tags;//±êÇ©Êý×é
private String width;
private String higth;
public Picture(){
	img_url=null;
	username="";
	userface_url=null;
	title="";
	IsGIf=false;
	IsCollect=false;
	uploaddata="";
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getImg_url() {
	return img_url;
}
public void setImg_url(String img_url) {
	this.img_url = img_url;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getUserface_url() {
	return userface_url;
}
public void setUserface_url(String userface_url) {
	this.userface_url = userface_url;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getUploaddata() {
    
	return uploaddata;
}
public void setUploaddata(String uploaddata) {
	this.uploaddata = uploaddata;
}
public boolean isIsGIf() {
	return IsGIf;
}
public void setIsGIf(boolean isGIf) {
	IsGIf = isGIf;
}
public boolean isIsCollect() {
	return IsCollect;
}
public void setIsCollect(boolean isCollect) {
	IsCollect = isCollect;
}
public String[] getTags() {
	return tags;
}
public void setTags(String[] tags) {
	this.tags = tags;
}
public String getWidth() {
	return width;
}
public void setWidth(String width) {
	this.width = width;
}
public String getHigth() {
	return higth;
}
public void setHigth(String higth) {
	this.higth = higth;
}

}
