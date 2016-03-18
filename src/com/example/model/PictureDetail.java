package com.example.model;

import java.io.Serializable;
import java.util.List;

public class PictureDetail implements Serializable {
private static final long serialVersionUID = -1214706476076138293L;
private String url_picture;//ͼƬ��ַ
private String imgHeight;//ͼƬ�߶�
private String uploadData;//ͼƬ�ϴ�����
private List<Tag> tags;//ͼƬ��ǩ
private String Summary;//ͼƬ����
private String collectCount;//ͼƬ���ղصĴ���
private UserInfo userinfo;//ͼƬ�ϴ�����Ϣ
private List<Comments>comments;//ͼƬ������Ϣ
private boolean  iscollect;//��ǰ�û��Ƿ��ղظ�ͼƬ,1Ϊ�ղ���,0Ϊû��
public PictureDetail() {
	super();
	this.url_picture = "";
	this.imgHeight = "";
	this.uploadData = "";
	this.tags =null;
	Summary ="";
	this.collectCount = "";
	this.userinfo = null;
	this.comments = null;
	this.iscollect = false;
}
public String getUrl_picture() {
	return url_picture;
}
public void setUrl_picture(String url_picture) {
	this.url_picture = url_picture;
}
public String getImgHeight() {
	return imgHeight;
}
public void setImgHeight(String imgHeight) {
	this.imgHeight = imgHeight;
}
public String getUploadData() {
	return uploadData;
}
public void setUploadData(String uploadData) {
	this.uploadData = uploadData;
}
public List<Tag> getTags() {
	return tags;
}
public void setTags(List<Tag> tags) {
	this.tags = tags;
}
public String getSummary() {
	return Summary;
}
public void setSummary(String summary) {
	Summary = summary;
}
public String getCollectCount() {
	return collectCount;
}
public void setCollectCount(String collectCount) {
	this.collectCount = collectCount;
}
public UserInfo getUserinfo() {
	return userinfo;
}
public void setUserinfo(UserInfo userinfo) {
	this.userinfo = userinfo;
}
public List<Comments> getComments() {
	return comments;
}
public void setComments(List<Comments> comments) {
	this.comments = comments;
}
public boolean isIscollect() {
	return iscollect;
}
public void setIscollect(boolean iscollect) {
	this.iscollect = iscollect;
}

}
