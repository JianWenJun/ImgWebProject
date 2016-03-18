package com.example.model;

import java.io.Serializable;

public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7880855424356599688L;
	/*Message string
	UploadTime string(UTC) ��Ϣ����ʱ�� 
	HowLongBefore string ��������Ϣ����ʱ�� 
	PId int ��Ϣ����ͼƬ��ͼƬ���� 
	UserName string ��Ϣ�����ߵ����� 
	UserFace string ��Ϣ�����ߵ�ͷ�� 
	UId int ��Ϣ�����ߵ����� 
	MsgObj int ��Ϣ�ظ�����,-1�������Ϣ������,����ֵ����ظ������۵�Id 
	PicDescribe string ����Ϣ���ڵ�ͼƬ��ͼƬ���� 
	IsRead bool �Ƿ��Ѷ� */

private String messageContent;// ��Ϣ���� 
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
