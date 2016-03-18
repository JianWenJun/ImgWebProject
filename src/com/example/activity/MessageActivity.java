package com.example.activity;


import java.util.HashMap;
import java.util.Map;

import com.example.adapter.MessageAdapter;
import com.example.bitmapfun.util.ImageFetcher;
import com.example.imgweb.R;
import com.example.tool.Constants;
import com.example.tool.JsonTool;
import com.example.tool.TaskTool;
import com.example.waterfallview.XListView;
import com.example.waterfallview.XListView.IXListViewListener;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.huewu.pla.lib.internal.PLA_AdapterView.OnItemClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MessageActivity extends Activity implements OnClickListener,IXListViewListener{
private String TAG="MessageActivity-->";
private Button message_back,message_fresh;
private XListView messageListview;
private MessageAdapter messageAdpater;
private int loadCount=1;
private TaskTool tasktool;
private ImageFetcher mImageFetcher;//加载瀑布流里面的图片
private Map<String,Object>taskparames=new HashMap<String, Object>();
private Handler handler=new Handler(){	
	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case Constants.MESSAGE_GET:
			messageListview.stopLoadMore();
			messageListview.stopRefresh();
			messageAdpater.addItemLast(JsonTool.getMessaList((String) msg.obj));
			messageAdpater.notifyDataSetChanged();
			break;
	
		default:
			break;
		}
		
			
	}
};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.my_message);
		mImageFetcher=new ImageFetcher(this, 200);
		tasktool=new TaskTool(handler);
		initview();
		messageListview.setAdapter(messageAdpater);
		taskparames.put("loadCount",loadCount+"");
		taskparames.put("tasktype", Constants.MESSAGE_GET);
		tasktool.getWebJson(Constants.URL_GET_MESSAGE, taskparames);
		messageListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(PLA_AdapterView<?> parent, View view, int position, long id) {
			 //postion从1开始算，所以得减一
				Intent in=new Intent(MessageActivity.this,ImgDetailActivity.class);
				in.putExtra("Pid", messageAdpater.getMessagePid(position-1));			
				startActivity(in);
				
			}
		});
	} 
	public void initview(){
		message_back=(Button) findViewById(R.id.mymessage_back);
		message_fresh=(Button) findViewById(R.id.mymessage_fresh);
		messageListview=(XListView) findViewById(R.id.mymessage_list);
		message_back.setOnClickListener(this);
		message_fresh.setOnClickListener(this);
		messageListview.setPullLoadEnable(true);
		messageListview.setPullRefreshEnable(true);
		messageListview.setXListViewListener(this);
		//messageListview.setDi
		messageAdpater=new MessageAdapter(this, mImageFetcher);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mymessage_back:
			this.finish();
			break;
        case R.id.mymessage_fresh:
        	messageAdpater.clearData();
        	loadCount=1;
        	taskparames.put("loadCount",loadCount+"");
    		taskparames.put("tasktype", Constants.MESSAGE_GET);
    		tasktool.getWebJson(Constants.URL_GET_MESSAGE, taskparames);
			break;
		default:
			break;
		}
		
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		messageAdpater.clearData();
    	loadCount=1;
    	taskparames.put("loadCount",loadCount+"");
		taskparames.put("tasktype", Constants.MESSAGE_GET);
		tasktool.getWebJson(Constants.URL_GET_MESSAGE, taskparames);
	}
	@Override
	public void onRefresh() {
		messageAdpater.clearData();
    	loadCount=1;
    	taskparames.put("loadCount",loadCount+"");
		taskparames.put("tasktype", Constants.MESSAGE_GET);
		tasktool.getWebJson(Constants.URL_GET_MESSAGE, taskparames);
		
	}
	@Override
	public void onLoadMore() {
		loadCount++;
		taskparames.put("loadCount",loadCount+"");
		taskparames.put("tasktype", Constants.MESSAGE_GET);
		tasktool.getWebJson(Constants.URL_GET_MESSAGE, taskparames);
		
	}

}
