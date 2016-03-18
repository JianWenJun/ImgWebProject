package com.example.model;

import java.io.Serializable;

public class Comments implements Serializable {
	private static final long serialVersionUID = -7623751889998725031L;
 private String commenter_face;
 private String content;
 private String postDate;
 private String commenter_name;
 private String uid;//评论者的id
 private String cid;//当前评论的id
 private String Pcid;//父评论的id
 private String objuid;//要回复某个人的id
 private boolean Isme;
public Comments() {
	super();
	this.commenter_face ="";
	this.content ="";
	this.postDate ="";
	this.commenter_name ="";
	this.Pcid="-1";
	Isme=false;
}
public boolean getIsMe(){
	return Isme;
}
public void setIsMe(boolean is){
	Isme=is;
}
public String getuCid() {
	return uid;
}
public void setuCid(String id) {
	this.uid = id;
}
public String getCommenter_face() {
	return commenter_face;
}
public void setCommenter_face(String commenter_face) {
	this.commenter_face = commenter_face;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getPostDate() {
	return postDate;
}
public void setPostDate(String postDate) {
	this.postDate = postDate;
}
public String getCommenter_name() {
	return commenter_name;
}
public void setCommenter_name(String commenter_name) {
	this.commenter_name = commenter_name;
}
public String getCid() {
	return cid;
}
public void setCid(String cid) {
	this.cid = cid;
}
public String getPcid() {
	return Pcid;
}
public void setPcid(String pcid) {
	Pcid = pcid;
}
public String getObjuid() {
	return objuid;
}
public void setObjuid(String objuid) {
	this.objuid = objuid;
}
public static long getSerialversionuid() {
	return serialVersionUID;
}

}
