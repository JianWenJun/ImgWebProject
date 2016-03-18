package com.example.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.activity.MyHavingActivity;
import com.example.adapter.MyhavingImgAdapter;
import com.example.bitmapfun.util.ImageFetcher;
import com.example.imgweb.R;
import com.example.model.MyUpPictrue;
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
/**
 * 
 * @author JWJ1
 * 收藏的类
 */
public class MyStoreFragment extends Fragment implements IXListViewListener{
private String TAG="MyStoreFragment--->>";
private int store_load_number=1;
private TaskTool tasktool;
private XListView myhaving_having_view;
private MyhavingImgAdapter imageAdapter=null;
private ImageFetcher mImageFetcher;//加载瀑布流里面的图片
private Map<String,Object>taskparames=new HashMap<String, Object>();
public MyStoreFragment(ImageFetcher mImageFetcher,Context c ){
	this.mImageFetcher=mImageFetcher;
	imageAdapter=new MyhavingImgAdapter(2,c,mImageFetcher);
	
}
@SuppressLint("HandlerLeak")
private Handler handler=new Handler(){

	@Override
	public void handleMessage(Message msg) {
    switch (msg.what) {
	case Constants.MY_STORE_FRESH:
		imageAdapter.addItemTop(JsonTool.getMyUPorStore((String)msg.obj));
		//Log.e(TAG,"结果："+(String)msg.obj);
		imageAdapter.notifyDataSetChanged();
		myhaving_having_view.stopRefresh();

		break;
    case Constants.MY_STORE_LOAD:
    	myhaving_having_view.stopLoadMore();		 
    	imageAdapter.addItemLast(JsonTool.getMyUPorStore((String)msg.obj));
    	imageAdapter.notifyDataSetChanged();
		break;
    case Constants.UNSTOREING_MY:
    	//取消收藏成功实时更新//重新获取资料
    	taskparames.put("cishu",1+"");
		taskparames.put("tasktype", Constants.UNSTOREING_MY_FRESH);
		tasktool.getWebJson(Constants.URL_MY_STORE, taskparames);
    	
    	break;
    case Constants.UNSTOREING_MY_FRESH:
    	imageAdapter.cleardata();
    	imageAdapter.addItemLast(JsonTool.getMyUPorStore((String)msg.obj));
    	imageAdapter.notifyDataSetChanged();
    	break;
	default:
		break;
	}
		
	}
	
};
@SuppressLint("InflateParams")
@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.e(TAG, "Store_onCreateView");
		View rootView=inflater.inflate(R.layout.mystorefragment, null);
		myhaving_having_view=(XListView) rootView.findViewById(R.id.myhaving_having_list);
		myhaving_having_view.setPullLoadEnable(true);
		myhaving_having_view.setPullRefreshEnable(true);
		myhaving_having_view.setXListViewListener(this);
		tasktool=new TaskTool(handler);
		imageAdapter.setTask(tasktool);
		myhaving_having_view.setAdapter(imageAdapter);
		if(!MyHavingActivity.IsUp)
		{
		taskparames.put("cishu",store_load_number+"");
		taskparames.put("tasktype", Constants.MY_STORE_FRESH);
		tasktool.getWebJson(Constants.URL_MY_STORE, taskparames);
		}
		return rootView;
	}
	

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);		
		Log.e(TAG, "UP_onAttach");
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//Log.e(TAG, "Store_onDestroy");
	}
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		//Log.e(TAG, "Store_onDetach");
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//Log.e(TAG, "Store_onPause");
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//FreshData();
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//Log.e(TAG, "Store_onStart");
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//Log.e(TAG, "Store_onStop");
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//Log.e(TAG, "Store_onCreate");
	}
	public void FreshData() {
		//mImageFetcher.setExitTasksEarly(false);
		Log.e(TAG,myhaving_having_view+"" );
		
		    store_load_number=1;
			taskparames.put("cishu",store_load_number+"");
			taskparames.put("tasktype", Constants.MY_STORE_FRESH);
			tasktool.getWebJson(Constants.URL_MY_STORE, taskparames);

		
	}
	@Override
	public void onRefresh() {
		store_load_number=1;
		imageAdapter.cleardata();
		taskparames.put("cishu",store_load_number+"");
		taskparames.put("tasktype", Constants.MY_STORE_FRESH);
		tasktool.getWebJson(Constants.URL_MY_STORE, taskparames);
		
	}
	@Override
	public void onLoadMore() {
		store_load_number++;
		taskparames.put("cishu",store_load_number+"");
		taskparames.put("tasktype", Constants.MY_STORE_LOAD);
		tasktool.getWebJson(Constants.URL_MY_STORE, taskparames);
		
	}

}
