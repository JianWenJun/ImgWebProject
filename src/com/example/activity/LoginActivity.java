package com.example.activity;


import java.util.HashMap;
import java.util.Map;

import com.example.db.LoginData;
import com.example.imgweb.R;
import com.example.tool.CodeThread;
import com.example.tool.Constants;
import com.example.tool.JsonTool;
import com.example.tool.TaskTool;
import com.example.view.LoadDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;

import android.graphics.drawable.BitmapDrawable;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener{
	private String TAG="LoginActivity---->>";
	private EditText login_name; // 登录名
	private EditText login_password; // 登录密码
	private Button button_login;
	private TextView password_forget;
	private ImageView login_finish;
	private ImageView yzm_image;
	private EditText login_yzm;  //输入的验证码
	private Button yzm_change;
	private LoginHandler handler;
	private CodeThread codethread;
	private TaskTool taskhelp;
	//创建一个接口用于回传一个登入界面成功的信息给主界面刷新左边菜单
	private static LoginListener loginlisten;
	public interface LoginListener{
		public void IsLogin(boolean is);
	}
	public static void setLoginListener(LoginListener loginlistenl){
		loginlisten=loginlistenl;
	}
	
	//加载对话框
	private LoadDialog loadDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		loadDialog=new LoadDialog(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);	
		login_name = (EditText) findViewById(R.id.edit_name);
		login_password = (EditText) findViewById(R.id.edit_password);
		button_login = (Button) findViewById(R.id.button_login);
		password_forget = (TextView) findViewById(R.id.password_forget);
		login_finish = (ImageView) findViewById(R.id.login_finish);
		yzm_image = (ImageView) findViewById(R.id.yzm_img);
		login_yzm = (EditText) findViewById(R.id.edit_yzmsuiji);
		yzm_change = (Button) findViewById(R.id.yzm_change);
		button_login.setOnClickListener(this);
		password_forget.setOnClickListener(this);
		login_finish.setOnClickListener(this);
		yzm_change.setOnClickListener(this);
		handler=new LoginHandler();
		codethread=new CodeThread(handler);
		taskhelp=new TaskTool(handler);
		if(!Constants.IsNetWorkAvaliable(getApplicationContext()))
			TaskApplication.showTip(R.drawable.tips_error, "没有联网!!!",this);
		else {
		 new Thread(codethread).start();}
	}



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction()==KeyEvent.ACTION_DOWN&&keyCode==KeyEvent.KEYCODE_BACK){
			this.finish();
			this.overridePendingTransition(0, R.anim.anim_finish);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.button_login:
			loadDialog.show();
			login();			
			break;
		case R.id.password_forget:

			//找回密码代码
			break;
		case R.id.login_finish:
			this.finish();
			this.overridePendingTransition(0,R.anim.anim_finish);
			break;
			
		case R.id.yzm_change:
			//开启线程获取验证码图片
			if(!Constants.IsNetWorkAvaliable(getApplicationContext()))
				TaskApplication.showTip(R.drawable.tips_error, "没有联网!!!",this);
			else {
			 new Thread(codethread).start();}
              break;
        default:
            	  break;
		}		
	}
	public void login(){
		Map<String, Object>parame=new HashMap<String, Object>();
		String name=login_name.getText().toString().trim();
		String password=login_password.getText().toString().trim();
		String yzm=login_yzm.getText().toString().trim();
		parame.put("username", name);
		parame.put("password", password);
		parame.put("verify", yzm);
		parame.put("tasktype",Constants.LOGIN);		
		taskhelp.getWebJson(Constants.URL_LOGIN, parame);
		
	}
class LoginHandler extends  Handler{

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
	   
		switch (msg.what) {
		case Constants.LOGIN:
			//通过返回的数据验证是否已经登入，根据不同的状态做出不同的步骤
			doLogin(JsonTool.getLogin((String) msg.obj));
			break;
		case Constants.LOGIN_CODE:
			
			break;
		case 8:
			yzm_image.setBackground(new BitmapDrawable((Bitmap)msg.obj)); break;
		default:
			break;
		}
		
	}
	
}
@SuppressLint("ShowToast")
private void doLogin(Map<String,Object>parames){
	if(parames==null) {

		TaskApplication.showTip(R.drawable.tips_error, "登入异常",this);
		
		loginlisten.IsLogin(false);//回传给MainActivity没有登入		
	}else{
		if("success".equals(parames.get("message"))){
			//登入成功 1, 存信息到本地  2,跳转页面
			loadDialog.cancel();
	    //	    LoginData.addUserInfo(name, password);
			TaskApplication.showTip(R.drawable.tips_smile, "登入成功",this);	
	        this.finish();
			this.overridePendingTransition(0,R.anim.anim_finish);
			loginlisten.IsLogin(true);//回传给MainActivity已经登入
		}
		else{
			//登入失败   ，显示失败原因
			
			TaskApplication.showTip(R.drawable.tips_error, parames.get("message")+"",this);
	
		loginlisten.IsLogin(false);//回传给MainActivity没有登入		
		}
	}
		
}
}
