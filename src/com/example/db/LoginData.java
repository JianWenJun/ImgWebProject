package com.example.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LoginData {
private static SharedPreferences userSp;
private static Context c;
public LoginData(Context c){
	this.c=c;
	userSp=c.getSharedPreferences("usr_info", Context.MODE_APPEND);
}
public static void setContext(Context context){
				c=context;
				userSp=c.getSharedPreferences("usr_info", Context.MODE_APPEND);
}
/*
 * 测试
 */
/*@SuppressLint("CommitPrefEdits")
public static void addUserInfo(String name,String password){
	Editor editor=userSp.edit();
	editor.putString("username", name);
	editor.putString("password", password);
	//editor.putString(key, value)("cookie",cookie );
	editor.commit();
}*/
public static void addUserCookies(String SessionId,String ucookie){
	Editor editor=userSp.edit();	
	editor.putString("SessionId", SessionId);
	editor.putString("uCookie", ucookie);
	//editor.
	editor.commit();
}
public static String getUserCookies(){
	return userSp.getString("uCookie", "");
}
public static String getUserSession()
{
	return userSp.getString("SessionId", "");
}
public static void removeUserCookies()
{
	Editor editor=userSp.edit();
	editor.remove("uCookie");
	editor.remove("SessionId");
	editor.commit();
}
public static void setClient(String c){
	Editor editor=userSp.edit();	
	editor.putString("httpclient", c);
	editor.commit();
}
public static String getClient(){
	return userSp.getString("httpclient", "");
}
public static void removeClient()
{
	Editor editor=userSp.edit();
	editor.remove("httpclient");
	editor.commit();
}
/*
 * 判断手机是否存有资料
 */
public static boolean IsHavingData(){
	
return (userSp.getString("uCookie", "").equals("")||userSp.getString("uCookie","")==null)? false:true;
}
/*
 * 存放上次更新时间
 */
}
