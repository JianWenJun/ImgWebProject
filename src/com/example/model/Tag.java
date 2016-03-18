package com.example.model;

import java.io.Serializable;

public class Tag implements Serializable {
private static final long serialVersionUID = -3392125150999367319L;
private String tid;//±êÇ©id
private String tagname;//±êÇ©Ãû×Ö
public Tag(){
	super();
	this.tid="";
	this.tagname="";
}
public String getTid() {
	return tid;
}
public void setTid(String tid) {
	this.tid = tid;
}
public String getTagname() {
	return tagname;
}
public void setTagname(String tagname) {
	this.tagname = tagname;
}
public static long getSerialversionuid() {
	return serialVersionUID;
}

}
