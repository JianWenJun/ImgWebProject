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
	private EditText register_uesr;//ע����û���
	private EditText register_yzm;//ע�����֤��
	private EditText register_email;//ע�������
	private EditText register_password;//����
	private TextView register_login;//ֱ�ӵ�¼����
	private TextView register_getcode;//��ȡ����
	private ImageView register_finish;//��;����ע��ҳ��
	private ImageView register_getyzm;//��ȡ����ʾ��֤��
    private Button register_register;//ע�ᰴť
    
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
				//ͨ�����ص�������֤�Ƿ��Ѿ�ע��ɹ������ݲ�ͬ��״̬������ͬ�Ĳ���
				doRegister(JsonTool.getRegister((String) msg.obj));
			
				break;
			default:
				break;
			}
			
		}
    	
    };
  //���ضԻ���
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
			TaskApplication.showTip(R.drawable.tips_error, "û������!!!",this);
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
			//���˺�ֱ�ӵ���
			break;
		case R.id.register_finish:
			this.finish();
			this.overridePendingTransition(0, R.anim.anim_finish);
			break;
		case R.id.register_register:
			//ע���ز���
			loadDialog.show();
			register();
			break;
		case R.id.register_getcode:
			//��ȡ��֤�����
			if(!Constants.IsNetWorkAvaliable(getApplicationContext()))
				TaskApplication.showTip(R.drawable.tips_error, "û������!!!",this);
			else {
			 new Thread(codethread).start();}
			break;
		default:
			break;
		}
		
	}
	//ע��֮ǰ��׼������
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
	//ע��֮�󷵻����ݺ�ع���
	private void  doRegister(Map<String,Object>parames){
		if(parames==null) {
			
			TaskApplication.showTip(R.drawable.tips_error, "ע���쳣",this);
			
			
		}else{
			if("success".equals(parames.get("message"))){
				
				loadDialog.cancel();
				TaskApplication.showTip(R.drawable.tips_smile, "ע��ɹ�\n�����",this);	
		        this.finish();
				this.overridePendingTransition(0,R.anim.anim_finish);
			}
			else{
				//����ʧ��   ����ʾʧ��ԭ��
				//loadDialog.cancel();
				TaskApplication.showTip(R.drawable.tips_error, parames.get("message")+"",this);
			}
		}
			
}
}
