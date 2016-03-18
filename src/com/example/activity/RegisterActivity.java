package com.example.activity;


import java.util.HashMap;
import java.util.Map;

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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
public class RegisterActivity extends Activity implements OnClickListener{
	private String TAG="RegisterActivity---->>";
	private EditText register_uesr;//注册的用户名
	private EditText register_yzm;//注册的验证码
	private EditText register_email;//注册的邮箱
	private EditText register_password;//密码
	private TextView register_login;//直接登录文字
	private TextView register_getcode;//获取文字
	private ImageView register_finish;//中途结束注册页面
	private ImageView register_getyzm;//获取和显示验证码
    private Button register_register;//注册按钮
    
    private TaskTool taskhelp;
    private CodeThread codethread;
    private Handler handler=new Handler(){

		@SuppressLint("NewApi")
		@SuppressWarnings("deprecation")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 8:
				register_getyzm.setBackground(new BitmapDrawable((Bitmap)msg.obj)); break;
			case Constants.REGISTER:
				//通过返回的数据验证是否已经注册成功，根据不同的状态做出不同的步骤
				doRegister(JsonTool.getRegister((String) msg.obj));
			
				break;
			default:
				break;
			}
			
		}
    	
    };
  //加载对话框
  	private LoadDialog loadDialog;	
  	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);	
		register_uesr=(EditText) findViewById(R.id.register_user);
		register_yzm=(EditText) findViewById(R.id.register_yzm);
		register_email=(EditText) findViewById(R.id.register_eamil);
		register_password=(EditText) findViewById(R.id.register_mm);
		register_login= (TextView) findViewById(R.id.register_login);
		register_getcode=(TextView) findViewById(R.id.register_getcode);
		register_finish=(ImageView) findViewById(R.id.register_finish);
		register_getyzm=(ImageView) findViewById(R.id.register_getyzm);
		register_register=(Button) findViewById(R.id.register_register);
		register_login.setOnClickListener(this);
		register_finish.setOnClickListener(this);
		register_getcode.setOnClickListener(this);
		register_register.setOnClickListener(this);
		codethread=new CodeThread(handler);
		taskhelp=new TaskTool(handler);	
		loadDialog=new LoadDialog(this);
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
			//有账号直接登入
			break;
		case R.id.register_finish:
			this.finish();
			this.overridePendingTransition(0, R.anim.anim_finish);
			break;
		case R.id.register_register:
			//注册后地操作
			loadDialog.show();
			register();
			break;
		case R.id.register_getcode:
			//获取验证码操作
			if(!Constants.IsNetWorkAvaliable(getApplicationContext()))
				TaskApplication.showTip(R.drawable.tips_error, "没有联网!!!",this);
			else {
			 new Thread(codethread).start();}
			break;
		default:
			break;
		}
		
	}
	//注册之前的准备工作
	@SuppressWarnings("unchecked")
	private void register(){
	  Map parames=new HashMap<String,Object>();
	  parames.put("tasktype", Constants.REGISTER);
	  parames.put("username",register_uesr.getText().toString().trim() );
	  parames.put("password",register_password.getText().toString().trim() );
	  parames.put("register_email",register_email.getText().toString().trim() );
	  parames.put("register_yzm",register_yzm.getText().toString().trim() );
	  taskhelp.getWebJson(Constants.URL_REGISTER, parames);
	}
	//注册之后返回数据后地工作
	private void  doRegister(Map<String,Object>parames){
		if(parames==null) {
			
			TaskApplication.showTip(R.drawable.tips_error, "注册异常",this);
			
			
		}else{
			if("success".equals(parames.get("message"))){
				
				loadDialog.cancel();
				TaskApplication.showTip(R.drawable.tips_smile, "注册成功\n请登入",this);	
		        this.finish();
				this.overridePendingTransition(0,R.anim.anim_finish);
			}
			else{
				//登入失败   ，显示失败原因
				//loadDialog.cancel();
				TaskApplication.showTip(R.drawable.tips_error, parames.get("message")+"",this);
			}
		}
			
}
}
