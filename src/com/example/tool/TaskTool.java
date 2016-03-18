package com.example.tool;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.example.activity.TaskApplication;
import com.example.db.LoginData;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class TaskTool {
private static String TAG="TaskHelp---->>";
private Handler handler;
public TaskTool(Handler handler){
	this.handler=handler;
}
public void getWebJson(String url, Map<String,Object>parame){
	
	new TaskThread(url,parame).start();	
}
/*
 * 开启新线程通过handler给各个界面发送更新UI的消息，附带更新数据
 */
class TaskThread extends Thread
{
private Map<String,Object> parame;
private String url;
HttpClient client;
public TaskThread(String url1,Map<String,Object>parame1) {
	parame=parame1;
	url=url1;
}
public void run() {
		client=TaskApplication.getClient();
		int TaskType=(Integer) parame.get("tasktype");
		switch (TaskType) {
		case Constants.LOGIN:{
			Message message=Message.obtain();
			message.what=Constants.LOGIN;
			message.obj=UseLogin();
			handler.sendMessage(message);
			break;}
        case Constants.UP_IMG_FRESH:{
        	Message message=Message.obtain();
			message.what=Constants.UP_IMG_FRESH;
			message.obj=ImgInfos();
			handler.sendMessage(message);
			break;}
        case Constants.UP_IMG_LOAD:
        {
        	Message message=Message.obtain();
			message.what=Constants.UP_IMG_LOAD;
			message.obj=ImgInfos();
			handler.sendMessage(message);
        	break;
        }
        case Constants.FIRST_SHOW_IMG:{
        	Message message=Message.obtain();
			message.what=Constants.FIRST_SHOW_IMG;
			message.obj=ImgInfos();
			handler.sendMessage(message);
        	break;
        }
        case Constants.GET_IMG_DETAIL:
        {
        	Message message=Message.obtain();
        	message.what=Constants.GET_IMG_DETAIL;
        	message.obj=getImgDeatil();
        	handler.sendMessage(message);
        }
			break;
        case Constants.REGISTER:
        {
        	Message message=handler.obtainMessage();
        	message.what=Constants.REGISTER;
        	message.obj=UseRegister();
        	handler.sendMessage(message);
        	break;
        }
        case Constants.GET_USERINFO:
        {
        	Message message=handler.obtainMessage();
        	message.what=Constants.GET_USERINFO;
        	message.obj=GetUserInfo();
        	handler.sendMessage(message);
        	break;	
        }
        case Constants.UPTOWEB:
        {
        	Message message=handler.obtainMessage();
        	message.what=Constants.UPTOWEB;
        	try {
				message.obj=UpToWeb();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	handler.sendMessage(message);       	
        	break;	
        }
        case Constants.MY_STORE_FRESH:
        {
        	Message message=handler.obtainMessage();
        	message.what=Constants.MY_STORE_FRESH;
        	message.obj=getStoreOrUp();
        	handler.sendMessage(message);   
        	break;
        }
        case Constants.MY_STORE_LOAD:
        {
        	Message message=handler.obtainMessage();
        	message.what=Constants.MY_STORE_LOAD;
        	message.obj=getStoreOrUp();
        	handler.sendMessage(message);   
        	break;
        }
        case Constants.MY_UP_FRESH:
        {
        	Message message=handler.obtainMessage();
    	    message.what=Constants.MY_UP_FRESH;
    	    message.obj=getStoreOrUp();
    	    handler.sendMessage(message); 
        	
        	break;
        }
        case Constants.MY_UP_LOAD:
        {
        	Message message=handler.obtainMessage();
    	    message.what=Constants.MY_UP_LOAD;
    	    message.obj=getStoreOrUp();
    	    handler.sendMessage(message); 
        	
        	break;
        }
        case Constants.STOREING:
        {
        	Message message=handler.obtainMessage();
    	    message.what=Constants.STOREING;
    	    message.obj=getStore();
    	    handler.sendMessage(message); 
        }
        	
        	break;
        case Constants.UNSTOREING:
        {
        	Message message=handler.obtainMessage();
    	    message.what=Constants.UNSTOREING;
    	    message.obj=getStore();
    	    handler.sendMessage(message); 
        }
        	          	
        	break;
        case Constants.UNSTOREING_MY:
        {
        	Message message=handler.obtainMessage();
    	    message.what=Constants.UNSTOREING_MY;
    	    message.obj=getStore();
    	    handler.sendMessage(message); 
        }
        	          	
        	break;
        case Constants.DELETE_UPTOWEB:
        {
        	Message message=handler.obtainMessage();
    	    message.what=Constants.DELETE_UPTOWEB;
    	    message.obj=getUp();
    	    handler.sendMessage(message); 
        }
        	          	
        	break;
        case Constants.COMMENT_SUBMIT:
        {
        	Message message=handler.obtainMessage();
    	    message.what=Constants.COMMENT_SUBMIT;
    	    message.obj=getComment();
    	    handler.sendMessage(message); 
        }
        	          	
        	break;
        case Constants.COMMENT_DELETE:
        {
        	Message message=handler.obtainMessage();
    	    message.what=Constants.COMMENT_DELETE;
    	    message.obj=getCommentdelete();
    	    handler.sendMessage(message); 
        }
        	          	
        	break;
        case Constants.COMMENT_SUBMIT_FRESH:
        {
        	Message message=handler.obtainMessage();
    	    message.what=Constants.COMMENT_SUBMIT_FRESH;
    	    message.obj=getImgDeatil();
    	    handler.sendMessage(message); 
        }
        	          	
        	break;
        case Constants.UNSTOREING_MY_FRESH:
        {
        	//更新我的收藏
        	Message message=handler.obtainMessage();
    	    message.what=Constants.UNSTOREING_MY_FRESH;
    	    message.obj=getStoreOrUp();
    	    handler.sendMessage(message); 
        }
    	    break;
        case Constants.DELETE_UPTOWEB_FRESH:
        {
        	//更新我的上传
        	Message message=handler.obtainMessage();
    	    message.what=Constants.DELETE_UPTOWEB_FRESH;
    	    message.obj=getStoreOrUp();
    	    handler.sendMessage(message); 
        }
        	break;
        case Constants.MESSAGE_GET:
        {
        	//获取消息
        	Message message=handler.obtainMessage();
    	    message.what=Constants.MESSAGE_GET;
    	    message.obj=getMessage();
    	    handler.sendMessage(message); 
        }
        	break;
        case Constants.AUTO_LOGIN:
        {
        Message message=handler.obtainMessage();
	    message.what=Constants.AUTO_LOGIN;
	    message.obj=getAutoLoginMessage();
	    handler.sendMessage(message); 
        }
	    break;
        case Constants.LOGIN_OUT:
        {
        Message message=handler.obtainMessage();
	    message.what=Constants.LOGIN_OUT;
	    message.obj=getAutoLoginMessage();
	    handler.sendMessage(message); 
        }
	    break;
		default:
			break;
		}
					
	}
/*
 * 自动登入结果
 */
private String getAutoLoginMessage() {
	String result="";
	HttpPost post=new HttpPost(url);
	post.addHeader("uCookie", LoginData.getUserCookies());
    post.addHeader("ASP.NET_SessionId",LoginData.getUserSession());	
    Log.e(TAG, "cookies:"+ LoginData.getUserCookies());
  //  Log.e(TAG, "keep:"+ LoginData.getClient());
   // Log.e(TAG,"client:"+client);
	HttpEntity en=null;
	try {
		HttpResponse respone=client.execute(post);
		
		if(respone.getStatusLine().getStatusCode()==200){
			en=respone.getEntity();
			result=EntityUtils.toString(en);	
		//	Log.e(TAG, "自动登录xx:"+result);
		}		
		
	} catch (ClientProtocolException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		Log.d(TAG, "respone异常");
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}finally{

		if(null!=en){
			try {
				en.consumeContent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	return result;


}
/*
 * 用户登入
 */
public String UseLogin(){
	String result="";
	String uCookies="",SessionId="";
	String username=(String) parame.get("username");
	String password=(String) parame.get("password");
	String code=(String) parame.get("verify");
	//System.out.println(TAG+code);
	List <NameValuePair> pairs=new ArrayList<NameValuePair>();
	pairs.add(new BasicNameValuePair("username", username));
	pairs.add(new BasicNameValuePair("password", password));
	pairs.add(new BasicNameValuePair("verify", code));
	pairs.add(new BasicNameValuePair("autoLogin", "true"));
	UrlEncodedFormEntity entity=null;	
	try {
		entity = new UrlEncodedFormEntity(pairs,"UTF-8");
	} catch (UnsupportedEncodingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		Log.d(TAG, "entity异常");
	}
	HttpPost post=new HttpPost(url);
	HttpEntity en=null;
	post.setEntity(entity);
	try {
		HttpResponse respone=client.execute(post);
		if(respone.getStatusLine().getStatusCode()==200){
	     
			List<Cookie>cookies=((AbstractHttpClient)client).getCookieStore().getCookies();
			if(cookies!=null){
				   for(Cookie co:cookies){
					  if(co.getName().equals("uCookie")) 
						  uCookies=co.getValue();
					  if(co.getName().equals("ASP.NET_SessionId")) 
						  SessionId=co.getValue();
					//  Log.w(TAG, "name:"+co.getName());
					 // Log.w(TAG, "values:"+co.getValue()) ; 
					
				   }
				   }
			LoginData.addUserCookies(SessionId, uCookies);
			en=respone.getEntity();
			result=EntityUtils.toString(en);
		
			/*post=new HttpPost(Constants.URL_AUTO_LOGIN);
			HttpResponse respone1=client.execute(post);
			if(respone.getStatusLine().getStatusCode()==200){
			Log.e(TAG,"自动---》》" +EntityUtils.toString(respone1.getEntity()))	;
			}*/
			//Log.e(TAG,"登入信息:"+ result);
		}
			
	} catch (ClientProtocolException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		Log.d(TAG, "respone异常");
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}finally{

		if(null!=en){
			try {
				en.consumeContent();
				post.abort();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//get.abort();
	
		//post.abort();
	}
	return result;	
}
/*
 * 获取消息
 */
private String getMessage() {
	String result="";
	String loadCount=(String) parame.get("loadCount");
	List<NameValuePair>pairs=new ArrayList<NameValuePair>();
	pairs.add(new BasicNameValuePair("loadCount", loadCount));

	UrlEncodedFormEntity entity=null;
	try {
		entity=new UrlEncodedFormEntity(pairs,"UTF-8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	HttpPost post=new HttpPost(url);
	post.setEntity(entity);
	HttpEntity en=null;
	try {
		HttpResponse respone=client.execute(post);
		if(respone.getStatusLine().getStatusCode()==200){
			en=respone.getEntity();
			result=EntityUtils.toString(en);
			//Log.w("获取消息结果",result);
		}		
		
	} catch (ClientProtocolException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		Log.d(TAG, "respone异常");
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}finally{

		if(null!=en){
			try {
				en.consumeContent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	return result;


}
/*
 * 删除评论
 */
private String getCommentdelete() {
	String result="";
	String cid=(String) parame.get("cId");
	List<NameValuePair>pairs=new ArrayList<NameValuePair>();
	pairs.add(new BasicNameValuePair("cId", cid));

	UrlEncodedFormEntity entity=null;
	try {
		entity=new UrlEncodedFormEntity(pairs,"UTF-8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	HttpPost post=new HttpPost(url);
	post.setEntity(entity);
	HttpEntity en=null;
	try {
		HttpResponse respone=client.execute(post);
		if(respone.getStatusLine().getStatusCode()==200){
			 en=respone.getEntity();
			result=EntityUtils.toString(en);
			//Log.w("删除评论结果",result);
		}		
		
	} catch (ClientProtocolException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		Log.d(TAG, "respone异常");
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}finally{

		if(null!=en){
			try {
				en.consumeContent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	return result;


}
/*
 * 提交评论
 */
private String getComment() {

	String result="";
	int pid=(Integer) parame.get("pId");
    String content= (String) parame.get("comment");
    int objuid=(Integer) parame.get("objUId");
    int pcid=(Integer) parame.get("pCId");
	if(pid==0) return null;
	else{
	List<NameValuePair>pairs=new ArrayList<NameValuePair>();
	pairs.add(new BasicNameValuePair("pId", pid+""));
	pairs.add(new BasicNameValuePair("comment", content));
	pairs.add(new BasicNameValuePair("objUId", objuid+""));
	pairs.add(new BasicNameValuePair("pCId", pcid+""));
	UrlEncodedFormEntity entity=null;
	try {
		entity=new UrlEncodedFormEntity(pairs,"UTF-8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	HttpPost post=new HttpPost(url);
	post.setEntity(entity);
	HttpEntity en=null;
	try {
		HttpResponse respone=client.execute(post);
		if(respone.getStatusLine().getStatusCode()==200){
			 en=respone.getEntity();
			result=EntityUtils.toString(en);
			//Log.w("提交评论",result);
		}		
		
	} catch (ClientProtocolException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		Log.d(TAG, "respone异常");
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}finally{

		if(null!=en){
			try {
				en.consumeContent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	}
	return result;


}
/*
 * 删除上传的图片
 */
private String getUp() {
	String result="";
	int pid=(Integer) parame.get("pId");

	if(pid==0) return null;
	else{
	List<NameValuePair>pairs=new ArrayList<NameValuePair>();
	pairs.add(new BasicNameValuePair("pId", pid+""));
	UrlEncodedFormEntity entity=null;
	try {
		entity=new UrlEncodedFormEntity(pairs,"UTF-8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	HttpPost post=new HttpPost(url);
	post.setEntity(entity);
	HttpEntity en=null;
	try {
		HttpResponse respone=client.execute(post);
		if(respone.getStatusLine().getStatusCode()==200){
			 en=respone.getEntity();
			result=EntityUtils.toString(en);
			//Log.w("sccccccccccc：",result);
		}		
		
	} catch (ClientProtocolException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		Log.d(TAG, "respone异常");
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}finally{

		if(null!=en){
			try {
				en.consumeContent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	}
	return result;

}
public String ImgInfos(){
	String result="";
	String cishu=(String) parame.get("cishu");
	List<NameValuePair>pairs=new ArrayList<NameValuePair>();
	pairs.add(new BasicNameValuePair("ciShu", cishu));
	url=url+"?"+URLEncodedUtils.format(pairs, "UTF-8");
	HttpGet get=new HttpGet(url);
	HttpEntity en=null;
	try {
		HttpResponse respone=client.execute(get);
		if(respone.getStatusLine().getStatusCode()==200){
			en=respone.getEntity();
			result=EntityUtils.toString(en);
			//Log.e(TAG, "tu:"+result);
		}
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally{
		if(null!=en){
			try {
				en.consumeContent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//get.abort();
	}
	return result;
	
}
/*
 * 获取所点击的图片的详情
 */
public String getImgDeatil(){
	String result="";
	int pid=(Integer) parame.get("pId");
	if(pid==0) return null;
	else{
	List<NameValuePair>pairs=new ArrayList<NameValuePair>();
	pairs.add(new BasicNameValuePair("pId", pid+""));
	UrlEncodedFormEntity entity=null;
	try {
		entity=new UrlEncodedFormEntity(pairs,"UTF-8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	HttpPost post=new HttpPost(url);
	post.setEntity(entity);
	HttpEntity en=null;
	try {
		HttpResponse respone=client.execute(post);
		if(respone.getStatusLine().getStatusCode()==200){
			 en=respone.getEntity();
			result=EntityUtils.toString(en);
			//Log.w("详情：",result);
		}		
		
	} catch (ClientProtocolException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		Log.d(TAG, "respone异常");
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}finally{

		if(null!=en){
			try {
				en.consumeContent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	}
	return result;
}

/*
 * 获取用户信息
 */
public String GetUserInfo(){
	String result="";
	HttpGet get=new HttpGet(url);
	if(LoginData.IsHavingData()){
		get.setHeader("uCookie", LoginData.getUserCookies());
		get.setHeader("ASP.NET_SessionId", LoginData.getUserSession());
		//HttpClientParams.setCookiePolicy(client.getParams(), CookiePolicy.BROWSER_COMPATIBILITY); 
	}
	HttpEntity en=null;
	try {
		
		HttpResponse respone=client.execute(get);
		//Log.e(TAG,"jjj--"+ respone.getStatusLine().getStatusCode());
		if(respone.getStatusLine().getStatusCode()==200){
			en=respone.getEntity();
			result=EntityUtils.toString(en);
			Log.e(TAG, "用户信息："+result);
		}
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally{
		if(null!=en){
			try {
				
				en.consumeContent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//get.abort();
	}
	return result;
	
}
/*
 * 用户注册
 */
public String UseRegister(){
	String result="";
	String username=(String) parame.get("username");
	String password=(String) parame.get("password");
	String code=(String) parame.get("register_yzm");
	String email= (String) parame.get("register_email");
	List<NameValuePair>pairs=new ArrayList<NameValuePair>();
	pairs.add(new BasicNameValuePair("username", username));
	pairs.add(new BasicNameValuePair("password", password));
	pairs.add(new BasicNameValuePair("verify", code));
	pairs.add(new BasicNameValuePair("email", email));
	UrlEncodedFormEntity entity=null;
	Log.w(TAG,username+" "+password+" "+code+" "+ email );
	try {
		entity = new UrlEncodedFormEntity(pairs,"UTF-8");
	} catch (UnsupportedEncodingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		Log.d(TAG, "entity异常");
	}
	
	HttpPost post=new HttpPost(url);
	post.setEntity(entity);
	/*HttpParams par=post.getParams();
	 HttpConnectionParams.setConnectionTimeout(par, 10000);*/
	HttpEntity en=null;
	try {
		HttpResponse respone=client.execute(post);
		if(respone.getStatusLine().getStatusCode()==200){
			 en=respone.getEntity();
			result=EntityUtils.toString(en);
			//Log.e(TAG, "注册:"+result);
		}
	
		
		
	} catch (ClientProtocolException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		Log.d(TAG, "respone异常");
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}finally{

		if(null!=en){
			try {
				en.consumeContent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//get.abort();
	
		//post.abort();
	}
	return result;
}
/*
 * 收藏图片
 */
public String getStore(){
	String result="";
	int pid=(Integer) parame.get("pId");
	boolean store=(Boolean) parame.get("Collect");
	if(pid==0) return null;
	else{
	List<NameValuePair>pairs=new ArrayList<NameValuePair>();
	pairs.add(new BasicNameValuePair("pId", pid+""));
	pairs.add(new BasicNameValuePair("isCollect", store+""));
	UrlEncodedFormEntity entity=null;
	try {
		entity=new UrlEncodedFormEntity(pairs,"UTF-8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	HttpPost post=new HttpPost(url);
	post.setEntity(entity);
	HttpEntity en=null;
	try {
		HttpResponse respone=client.execute(post);
		if(respone.getStatusLine().getStatusCode()==200){
			 en=respone.getEntity();
			result=EntityUtils.toString(en);
			//Log.w("sccccccccccc：",result);
		}		
		
	} catch (ClientProtocolException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		Log.d(TAG, "respone异常");
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}finally{

		if(null!=en){
			try {
				en.consumeContent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	}
	return result;
}

/*
 * 获取我的上传图片或者我的收藏图片的信息
 */
private String getStoreOrUp() {
	String result="";
	String loadCount= (String) parame.get("cishu");
	Log.e(TAG, "加载次数"+loadCount);
	List<NameValuePair>pairs=new ArrayList<NameValuePair>();
	pairs.add(new BasicNameValuePair("loadCount", loadCount));
	pairs.add(new BasicNameValuePair("loadSize", 10+""));
	UrlEncodedFormEntity entity=null;	
	try {
		entity = new UrlEncodedFormEntity(pairs,"UTF-8");
	} catch (UnsupportedEncodingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		Log.d(TAG, "entity异常");
	}	
	HttpPost post=new HttpPost(url);
	if(LoginData.IsHavingData()){
		post.setHeader("uCookie", LoginData.getUserCookies());
		post.setHeader("ASP.NET_SessionId", LoginData.getUserSession());
		
	}
	post.setEntity(entity);
	HttpEntity en=null;
	try {
		HttpResponse respone=client.execute(post);
		if(respone.getStatusLine().getStatusCode()==200){
			 en=respone.getEntity();
			result=EntityUtils.toString(en);
			Log.e(TAG, "收藏或上传："+result);
		}					
	} catch (ClientProtocolException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		Log.d(TAG, "respone异常");
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}finally{

		if(null!=en){
			try {
				en.consumeContent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	return result;
}

public String UpToWeb() throws UnsupportedEncodingException{
	String result="";
	String tags=(String) parame.get("tags");
	String summary=(String) parame.get("summary");
	File file=  (File) parame.get("file");
	MultipartEntityBuilder fileEntity=MultipartEntityBuilder.create();
	fileEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//浏览器设置兼容模式
	fileEntity.addTextBody("tags", new String(tags.getBytes("GBK"),"iso8859-1"));
	fileEntity.addTextBody("summary", new String(summary.getBytes("GBK"),"iso8859-1"));
	fileEntity.addPart("file", new FileBody(file));
	//fileEntity.addBinaryBody("file", file);
	HttpPost post=new HttpPost(url);
	if(LoginData.IsHavingData()){
		post.setHeader("uCookie", LoginData.getUserCookies());
		post.setHeader("ASP.NET_SessionId", LoginData.getUserSession());
	}
	post.setEntity(fileEntity.build());	
	HttpEntity en=null;
	try {
		HttpResponse respone=client.execute(post);
		if(respone.getStatusLine().getStatusCode()==200){
			 en=respone.getEntity();
			result=EntityUtils.toString(en);
			Log.w(TAG, "上传结果"+result);
		}		
		
	} catch (ClientProtocolException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		Log.d(TAG, "respone异常");
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}finally{

		if(null!=en){
			try {
				en.consumeContent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//get.abort();
	
		//post.abort();
	}
	return result;
}







}
}
