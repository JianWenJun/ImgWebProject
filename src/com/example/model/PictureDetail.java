package com.example.model;

import java.io.Serializable;
import java.util.List;

public class PictureDetail implements Serializable {
private static final long serialVersionUID = -1214706476076138293L;
private String url_picture;//图片地址
private String imgHeight;//图片高度
private String uploadData;//图片上传日期
private List<Tag> tags;//图片标签
private String Summary;//图片介绍
private String collectCount;//图片被收藏的次数
private UserInfo userinfo;//图片上传者信息
private List<Comments>comments;//图片评论信息
private boolean  iscollect;//当前用户是否收藏该图片,1为收藏了,0为没有
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
