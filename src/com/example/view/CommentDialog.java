package com.example.view;
import java.util.HashMap;
import java.util.Map;

import com.example.imgweb.R;
import com.example.tool.Constants;
import com.example.tool.TaskTool;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CommentDialog extends Dialog implements android.view.View.OnClickListener {
	private Button bt_send,bt_cancell;
	private EditText dialog_comtent;
	private String uContent;
	private int pid,objuid,pcid;
	private TaskTool taskhelp;
	private String up_name;//图片上传者的名称
	private Map<String, Object>parame;//传递网络请求参数
	public CommentDialog(Context context) {
		super(context,R.style.comment_dialog_theme);
		
	}
	//pid为 图片id，objUId为回复者的id，pCId回复的评论id
	public CommentDialog(Context context,int pid, int objUId,int pCId,TaskTool taskhelp,String name){
		super(context, R.style.comment_dialog_theme);
        this.pid=pid;
        this.objuid=objUId;
        this.pcid=pCId;
        this.taskhelp=taskhelp;
        this.up_name=name;
	}	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_comment);
		bt_send=(Button) findViewById(R.id.dialog_send);
		bt_cancell=(Button) findViewById(R.id.dialog_cancel);
		dialog_comtent=(EditText) findViewById(R.id.dialog_content);
		bt_send.setOnClickListener(this);
		bt_cancell.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_cancel:
			this.cancel();			
			break;
		case R.id.dialog_send:
		{
			
			
			//利用Handler发送消息
			uContent=dialog_comtent.getText().toString().trim();
			parame=new HashMap<String, Object>();
		    parame.put("tasktype", Constants.COMMENT_SUBMIT);
		    parame.put("pId", pid);
		    parame.put("comment",uContent);
		    parame.put("objUId", objuid);
		    parame.put("pCId", pcid);
		   // Log.e("sssss", "objUId"+objuid+" pcid"+pcid);
		    taskhelp.getWebJson(Constants.URL_WEBCOMMNET, parame);
		    this.dismiss();
		}
		default:
			break;
		}
		
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
	
			dialog_comtent.setHint("@"+up_name);
		
		
	}
/*	public TaskTool getTaskhelp() {
		return taskhelp;
	}*/
}
