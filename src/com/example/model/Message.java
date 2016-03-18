package com.example.model;

import java.io.Serializable;

public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7880855424356599688L;
	/*Message string
	UploadTime string(UTC) 消息发送时间 
	HowLongBefore string 处理后的消息发送时间 
	PId int 消息所在图片的图片主键 
	UserName string 消息发送者的名字 
	UserFace string 消息发送者的头像 
	UId int 消息发送者的主键 
	MsgObj int 消息回复对象,-1代表该消息是评论,其他值代表回复的评论的Id 
	PicDescribe string 该消息所在的图片的图片描述 
	IsRead bool 是否已读 */

private String messageContent;// 消息内容 
private String HowLongBefore;
private int pid;
private String username;
private String userface;
private  int MsgObj;
private String PicDescribe;
private boolean Isread;
public String getMessageContent() {
	return messageContent;
}
public void setMessageContent(String messageContent) {
	this.messageContent = messageContent;
}
public String getHowLongBefore() {
	return HowLongBefore;
}
public void setHowLongBefore(String howLongBefore) {
	HowLongBefore = howLongBefore;
}
public int getPid() {
	return pid;
}
public void setPid(int pid) {
	this.pid = pid;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getUserface() {
	return userface;
}
public void setUserface(String userface) {
	this.userface = userface;
}
public int getMsgObj() {
	return MsgObj;
}
public void setMsgObj(int msgObj) {
	MsgObj = msgObj;
}
public String getPicDescribe() {
	return PicDescribe;
}
public void setPicDescribe(String picDescribe) {
	PicDescribe = picDescribe;
}
public boolean isIsread() {
	return Isread;
}
public void setIsread(boolean isread) {
	Isread = isread;
}


}
