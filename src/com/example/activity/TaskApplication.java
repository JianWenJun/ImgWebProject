package com.example.activity;



import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.db.LoginData;
import com.example.tool.CrashTool;
import com.example.view.TipsToast;

import android.app.Application;
import android.content.Context;
import android.os.Build;




public class TaskApplication extends Application{
	private static boolean IsLogin=false;
	private static HttpClient client;
	private static CookieStore cookies;
	private static TipsToast tipsToast;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		LoginData.setContext(getApplicationContext());
		if(client==null){
			client=new DefaultHttpClient();
			/*
			if(LoginData.getClient().equals("")){
				   client=new DefaultHttpClient();
				   Log.w("jjjjj", "22");
				   ByteArrayOutputStream bo=new ByteArrayOutputStream();
				   try {
					   Log.w("jjjjj", "11");
					ObjectOutputStream oo=new ObjectOutputStream(bo);
					oo.writeObject(client);
					 Log.w("jjjjj", "3");
					String payCityMapBase64 = new String(bo.toByteArray());
					Log.w("jjjjj", payCityMapBase64);
					LoginData.setClient(payCityMapBase64);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				byte[] base64Bytes = LoginData.getClient().getBytes();
				ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
				ObjectInputStream ois;
				try {
					ois = new ObjectInputStream(bais);
					try {
						client=(HttpClient) ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (StreamCorruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		*/}
		/*
		 * ´¦ÀíÎ´Öª´íÎó
		 */
		   CrashTool crashHandler = CrashTool.getInstance();  
	        crashHandler.init(getApplicationContext());  
		
       
	}
  public static HttpClient getClient(){
	  return client;
  }  

  public static CookieStore getCookie(){  
	  
      return cookies;
  }
  public static void setCookie(CookieStore cks){
      cookies = cks;
  }
  public static void showTip(int resid,String tip,Context context){
		if (tipsToast != null) {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				tipsToast.cancel();
			}
		} else {
			tipsToast = TipsToast.makeText(context,
					tip, TipsToast.LENGTH_SHORT);
		}
		tipsToast.setText(tip);
		tipsToast.setIcon(resid);
		tipsToast.show();
		
	}
  public static boolean getIsLogin(){
	  return IsLogin;
  }
  public static void setIsLogin(boolean is){
	  IsLogin=is;
  }


}
