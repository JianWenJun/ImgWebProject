package com.example.model;

import java.io.Serializable;

/**
 * 我的上传的图片的封装类
 */
public class MyUpPictrue implements Serializable{
/**
	 * 
	 */
private static final long serialVersionUID = 1956852179809159942L;
private String url_img;
private int pid;

public String getUrl_img() {
	return url_img;
}
public void setUrl_img(String url_img) {
	this.url_img = url_img;
}
public int getPid() {
	return pid;
}
public void setPid(int pid) {
	this.pid = pid;
}
	

}
