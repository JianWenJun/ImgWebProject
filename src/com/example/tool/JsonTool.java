package com.example.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.model.Comments;
import com.example.model.Message;
import com.example.model.MyUpPictrue;
import com.example.model.Picture;
import com.example.model.PictureDetail;
import com.example.model.Tag;
import com.example.model.UserInfo;

import android.util.Log;
/**
 * 
 * @author JWJ1
 * 解析JSON类
 */
public class JsonTool {
	
private String TAG="---->>JsonTool";
/*
 * 解析消息
 */
public static List<Message> getMessaList(String text){
	List<Message>messages=new ArrayList<Message>();
	Message message=null;
	JSONObject data=JSON.parseObject(text);
	if(data!=null&&data.getIntValue("status")==1){		
		JSONArray messagearry=data.getJSONArray("data");
		if(messagearry!=null){
			for(int i=0;i<messagearry.size();i++){
				message=new Message();
				message.setUsername(((JSONObject)messagearry.get(i)).getString("UserName"));
				message.setUserface(((JSONObject)messagearry.get(i)).getString("UserFace"));
				message.setMessageContent(((JSONObject)messagearry.get(i)).getString("Message"));
				message.setIsread(((JSONObject)messagearry.get(i)).getBooleanValue("IsRead"));
				message.setHowLongBefore(((JSONObject)messagearry.get(i)).getString("HowLongBefore"));
				message.setMsgObj(((JSONObject)messagearry.get(i)).getIntValue("MsgObj"));
				message.setPicDescribe(((JSONObject)messagearry.get(i)).getString("PicDescribe"));
				message.setPid(((JSONObject)messagearry.get(i)).getIntValue("PId"));
				messages.add(message);
			}
		}
	}
	
	return messages;
}
/*
 * 解析大批图片的数据
 */
public static List<Picture> getList(String text){
List<Picture>pictures=new ArrayList<Picture>();
Picture pictrue=null;
JSONObject data=JSON.parseObject(text);
if(data!=null&&data.getIntValue("status")==1){			
JSONArray picturearry=data.getJSONArray("data");
if(picturearry!=null){
for(int i=0;i<picturearry.size();i++){
	pictrue=new Picture();
	pictrue.setImg_url(((JSONObject)picturearry.get(i)).getString("imgUrl"));
	pictrue.setUsername(((JSONObject)picturearry.get(i)).getString("userName"));
	pictrue.setUserface_url(((JSONObject)picturearry.get(i)).getString("userFace"));
	pictrue.setId(((JSONObject)picturearry.get(i)).getString("id"));
	pictrue.setTitle(((JSONObject)picturearry.get(i)).getString("title"));
	//Log.i("时间", msg);
	pictrue.setUploaddata(((JSONObject)picturearry.get(i)).getString("uploadDate"));
	pictrue.setTags(((JSONObject)picturearry.get(i)).getString("label").substring(1,
			((JSONObject)picturearry.get(i)).getString("label").length()-1).split(","));
	pictrue.setIsGIf(((JSONObject)picturearry.get(i)).getBooleanValue("isGif"));
	pictures.add(pictrue);
}
}
}
return pictures;	
}
/*
 * 解析某张图片的详细数据
 */
public static PictureDetail getPictureDetail(String text){
	PictureDetail picture_detail=null;
	int i=0;
	JSONObject data=JSON.parseObject(text);
	if(data!=null&&data.getIntValue("status")==1){	
		picture_detail=new PictureDetail();
		JSONObject jsonobjct=data.getJSONObject("data");
		if(jsonobjct!=null){
			picture_detail.setUrl_picture(jsonobjct.getString("url"));
			picture_detail.setUploadData(jsonobjct.getString("uploadDate"));
			picture_detail.setSummary(jsonobjct.getString("summary"));
			picture_detail.setIscollect(Boolean.parseBoolean(jsonobjct.getString("isCollect")));
			//标签列表
			JSONArray tag_array=jsonobjct.getJSONArray("tags");
			if(tag_array!=null){
			List<Tag>tags=new LinkedList<Tag>();
			for(i=0;i<tag_array.size();i++){
				Tag tag=new Tag();
				tag.setTagname(((JSONObject)tag_array.get(i)).getString("TagName"));
				tags.add(tag);
			}
			picture_detail.setTags(tags);
			}
		    //评论列表
			JSONArray comment_array=jsonobjct.getJSONArray("commentlist");
			if(comment_array!=null){
			List<Comments>comments=new LinkedList<Comments>();
			for(i=0;i<comment_array.size();i++){
				Comments comment=new Comments();
				comment.setCommenter_face(((JSONObject)comment_array.get(i)).getString("userFace"));
				comment.setCommenter_name(((JSONObject)comment_array.get(i)).getString("userName"));
				comment.setContent(((JSONObject)comment_array.get(i)).getString("content"));
				comment.setPostDate(((JSONObject)comment_array.get(i)).getString("postDate"));
				comment.setCid(((JSONObject)comment_array.get(i)).getString("cId"));
				comment.setIsMe(((JSONObject)comment_array.get(i)).getBoolean("isMe"));
				comment.setPcid(((JSONObject)comment_array.get(i)).getString("pCId"));
				comment.setObjuid(((JSONObject)comment_array.get(i)).getString("objUid"));
				comment.setuCid(((JSONObject)comment_array.get(i)).getString("cUid"));
				
				comments.add(comment);
			}
			picture_detail.setComments(comments);
			}
			//上传者信息
			UserInfo user=null;
			JSONObject userinfo_object=jsonobjct.getJSONObject("userInfo");
			if(userinfo_object!=null){
				user=new UserInfo();
				user.setUser_img_url(userinfo_object.getString("userFace"));
				user.setUsername(userinfo_object.getString("userName"));
				user.setUserId(userinfo_object.getString("uid"));
				picture_detail.setUserinfo(user);
			}
			
		return picture_detail;
		}
	}
	return null;
}
/*解析自动登录信息
 * 
*/
public static boolean getAutoLogin(String text){
	

	JSONObject data=JSON.parseObject(text);
	if(data!=null&&data.getIntValue("status")==1)
	{
		return true;
	}
	return false;
}
/*
 * 解析登入信息的数据
 */
public static Map<String,Object>getLogin(String text){
	Map<String,Object>map=new HashMap<String, Object>();
	JSONObject data=JSON.parseObject(text);
	if(data!=null&&data.getIntValue("status")==1)
	{	
	String login_message=data.getString("msg");
	if(login_message!=null)
	{	
	   map.put("message", "success");
	   return map;
    }
	}
	else if(data.getIntValue("status")==2)
	{
		 map.put("message",data.getString("msg"));
		 return map;
	}
	return map;
}
/*
 * 解析当前用户的JSON数据
 */
public static UserInfo getCurrent(String text){  
	
	 UserInfo user_current=null;
	 JSONObject data=JSON.parseObject(text);
	if(data!=null&&data.getIntValue("status")==1)
	{	
	user_current=new UserInfo();
	JSONObject user_info=data.getJSONObject("data");	
	user_current.setUser_img_url(user_info.getString("UserFacePathSmall"));
	user_current.setUsername(user_info.getString("UserName"));
	user_current.setUserstatus(user_info.getBoolean("UserStatus"));	
	user_current.setUserId(user_info.getString("UId"));
	}
	return user_current;
	}
/*
 * 解析注册信息的数据
 */
public static Map<String,Object>getRegister(String text){
	Map<String,Object>map=new HashMap<String, Object>();
	JSONObject data=JSON.parseObject(text);
	if(data!=null&&data.getIntValue("status")==1)
	{	
	   map.put("message", "success");
	   return map;
   }else{
	   map.put("message", data.getString("msg"));	  
	   return map;
   } 
	

}
/**
 * 解析我的收藏和我的上传的数据
 * 
 * 
 */
public static List<MyUpPictrue>getMyUPorStore(String text){
	List<MyUpPictrue>pictures=new ArrayList<MyUpPictrue>();
	MyUpPictrue pictrue=null;
	JSONObject data=JSON.parseObject(text);
	
	if(data!=null&&data.getIntValue("status")==1)
	{	
    JSONArray picturearry=data.getJSONArray("data");
    if(picturearry!=null){
	for(int i=0;i<picturearry.size();i++){
		pictrue=new MyUpPictrue();
		pictrue.setUrl_img(((JSONObject)picturearry.get(i)).getString("imgUrl"));
		pictrue.setPid(((JSONObject)picturearry.get(i)).getInteger("pId"));		
		pictures.add(pictrue);
	}
   }}	
	return pictures;	
}
/*
 * 评论提交结果
 */
public static boolean getCommentSubmit(String text){
	boolean is=false;
  JSONObject data=JSON.parseObject(text);	
	if(data!=null&&data.getIntValue("status")==1)
	{	
		is=data.getJSONObject("data").getBooleanValue("isSubmit");	
	}
	return is;
	
}
/*
 * 评论删除结果
 */
public static boolean getCommentDelete(String text){
	boolean is=false;
  JSONObject data=JSON.parseObject(text);	
	if(data!=null&&data.getIntValue("status")==1)
	{	
		is=true;	
	}
	return is;
	
}
/*
 * 解析上传图片的数据
 */
public static boolean getUpImg(String text){
	boolean is=false;
	  JSONObject data=JSON.parseObject(text);	
		if(data!=null&&data.getIntValue("status")==1)
		{	
			is=true;	
		}
		return is;
		
}

}
