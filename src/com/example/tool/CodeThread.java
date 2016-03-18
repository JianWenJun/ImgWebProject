package com.example.tool;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.example.activity.TaskApplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

public class CodeThread implements Runnable{
	Handler handler;
	Bitmap bitmap = null;
	HttpGet get=null;
	HttpResponse response;
	public CodeThread(Handler handler){	
		    this.handler=handler;
			get=new HttpGet(Constants.URL_CONFIGCODE);					
	}
	
	@Override
	public void run() {
		/*
		 * 获取验证码
		 */
		try {
			response = TaskApplication.getClient().execute(get);
		
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   HttpEntity entit=null;
			if(response.getStatusLine().getStatusCode()==200){
				try {
					entit=response.getEntity();
					byte[] 	b = EntityUtils.toByteArray(entit);
					bitmap=BitmapFactory.decodeStream(new ByteArrayInputStream(b,0,b.length));
					Message mes=Message.obtain();
					mes.what=8;
					mes.obj=bitmap;
					handler.sendMessage(mes);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
				      if(null!=entit)
						try {
							
							entit.consumeContent();//释放HttpEntity对象，因为每次联网操作该对象只能使用一次
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
				
			}		
	}
	
}
