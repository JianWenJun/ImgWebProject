package com.example.view;


import java.util.HashMap;
import java.util.Map;

import com.example.activity.MyHavingActivity;
import com.example.adapter.MyhavingImgAdapter;
import com.example.bitmapfun.util.ImageFetcher;
import com.example.imgweb.R;
import com.example.tool.Constants;
import com.example.tool.JsonTool;
import com.example.tool.TaskTool;
import com.example.waterfallview.XListView;
import com.example.waterfallview.XListView.IXListViewListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/*
 * 我的上传
 */
public class MyUpFragment extends Fragment implements IXListViewListener{
private String TAG="MyUpFragment--->>";
private int up_load_number=1;
private TaskTool tasktool;
private XListView myhaving_up_view;
private MyhavingImgAdapter imageAdapter=null;
private ImageFetcher mImageFetcher;//加载瀑布流里面的图片
private Map<String,Object>taskparames=new HashMap<String, Object>();
public MyUpFragment(ImageFetcher mImageFetcher,Context c){
	this.mImageFetcher=mImageFetcher;
	imageAdapter=new MyhavingImgAdapter(1,c,mImageFetcher);

}

@SuppressLint("HandlerLeak")
private Handler handler=new Handler(){

	@Override
	public void handleMessage(Message msg) {
    switch (msg.what) {
    case Constants.MY_UP_FRESH:
		imageAdapter.addItemTop(JsonTool.getMyUPorStore((String)msg.obj));			
		imageAdapter.notifyDataSetChanged();
		myhaving_up_view.stopRefresh();
		break;
    case Constants.MY_UP_LOAD:
    	myhaving_up_view.stopLoadMore();		 
    	imageAdapter.addItemLast(JsonTool.getMyUPorStore((String)msg.obj));
    	imageAdapter.notifyDataSetChanged();
		break;
   case Constants.DELETE_UPTOWEB:
    	//删除成功后重新获取刷新页面
	    taskparames.put("cishu",1+"");
		taskparames.put("tasktype", Constants.DELETE_UPTOWEB_FRESH);
		tasktool.getWebJson(Constants.URL_MY_UP, taskparames);	
	   break;
   case Constants.DELETE_UPTOWEB_FRESH:
	   imageAdapter.cleardata();
	   imageAdapter.addItemLast(JsonTool.getMyUPorStore((String)msg.obj));
   	   imageAdapter.notifyDataSetChanged();
	   break;
	default:
		break;
	}
		
	}
	
};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.myupfragment, null);
		myhaving_up_view=(XListView) view.findViewById(R.id.myhaving_up_list);
		myhaving_up_view.setPullLoadEnable(true);
		myhaving_up_view.setPullRefreshEnable(true);
		myhaving_up_view.setXListViewListener(this);
		tasktool=new TaskTool(handler);	
		imageAdapter.setTask(tasktool);
		myhaving_up_view.setAdapter(imageAdapter);
		if(MyHavingActivity.IsUp){
		taskparames.put("cishu",up_load_number+"");
		taskparames.put("tasktype", Constants.MY_UP_FRESH);
		tasktool.getWebJson(Constants.URL_MY_UP, taskparames);	
		}
		return view;
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);		
		//Log.e(TAG, "UP_onAttach");
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//Log.e(TAG, "UP_onDestroy");
	}
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		//Log.e(TAG, "UP_onDetach");
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//Log.e(TAG, "UP_onPause");
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//Log.e(TAG, "UP_onResume");
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//Log.e(TAG, "UP_onStart");
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//Log.e(TAG, "UP_onStop");
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//Log.e(TAG, "UP_onCreate");
	}
	public void FreshData() {
		//if(myhaving_up_view!=null){
			taskparames.put("cishu",up_load_number+"");
			taskparames.put("tasktype", Constants.MY_UP_FRESH);
			tasktool.getWebJson(Constants.URL_MY_UP, taskparames);
		//}
		
          	
		
	}
	@Override
	public void onRefresh() {

		up_load_number=1;	
		imageAdapter.cleardata();
		taskparames.put("cishu",up_load_number+"");
		taskparames.put("tasktype", Constants.MY_UP_FRESH);
		tasktool.getWebJson(Constants.URL_MY_UP, taskparames);
		
	}
	@Override
	public void onLoadMore() {
		up_load_number++;		
		taskparames.put("cishu",up_load_number+"");
		taskparames.put("tasktype", Constants.MY_UP_LOAD);
		tasktool.getWebJson(Constants.URL_MY_UP, taskparames);
		
	}


}
