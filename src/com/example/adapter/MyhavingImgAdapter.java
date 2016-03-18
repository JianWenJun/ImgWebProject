package com.example.adapter;

import java.util.LinkedList;
import java.util.List;

import com.example.bitmapfun.util.ImageFetcher;
import com.example.imgweb.R;
import com.example.model.MyUpPictrue;
import com.example.tool.Constants;
import com.example.tool.HavingImgListener;
import com.example.tool.TaskTool;
import com.example.waterfallview.FlowView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MyhavingImgAdapter extends BaseAdapter{
	private String TAG="MyhavingImgAdapter---->";
	private Context context;
	private ImageFetcher mImageFetcher;
	private LinkedList<MyUpPictrue>myUpPictrues;
	private  HavingImgListener upListener=null,storeListener=null;
	private TaskTool tasktool;
	private int type=1;
	public MyhavingImgAdapter(int type,Context context,ImageFetcher mImageFetcher){
		this.context=context;
		this.mImageFetcher=mImageFetcher;
		myUpPictrues=new LinkedList<MyUpPictrue>();
		this.type=type;
	}
	class ViewHolder{
		FlowView flowview;
		ImageView having_up,having_store;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myUpPictrues.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return myUpPictrues.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder=null;
		MyUpPictrue pictrue=myUpPictrues.get(position);
       if(convertView==null){   	
    	 LayoutInflater layin=LayoutInflater.from(context);
   	     convertView=layin.inflate(R.layout.myhaving_list_item, null);
    	 viewholder=new ViewHolder();
    	 viewholder.flowview=(FlowView) convertView.findViewById(R.id.myhaving_list_item_flow);
    	
    	 viewholder.having_store=(ImageView) convertView.findViewById(R.id.myhaving_list_item_store);
    	 viewholder.having_up=(ImageView) convertView.findViewById(R.id.myhaving_list_item_up);    	 
    	 storeListener=new HavingImgListener(2,context);
    	 upListener=new HavingImgListener(1,context);
    	 viewholder.having_store.setOnClickListener(storeListener);
    	 viewholder.having_up.setOnClickListener(upListener);
    	 convertView.setTag(viewholder);
    }
    viewholder=(ViewHolder) convertView.getTag();
    //设置图片对应的id，点击时可以获取
    viewholder.flowview.setTaskToll(tasktool);
    
    viewholder.flowview.setPictureId(pictrue.getPid());
    mImageFetcher.loadImage(Constants.URL_PLTH+pictrue.getUrl_img(), viewholder.flowview);
	if(type==1){
		//我的上传
		viewholder.having_store.setVisibility(View.GONE);
		viewholder.having_up.setVisibility(View.VISIBLE);
		viewholder.flowview.setID(1);
		//Log.w(TAG,viewholder.having_store+"|"+viewholder.having_up);
	}
	if(type==2){
		//我的收藏
		viewholder.having_store.setVisibility(View.VISIBLE);
		viewholder.having_up.setVisibility(View.GONE);
		viewholder.flowview.setID(2);
	}
	//Log.w(TAG,viewholder.having_store+"||"+viewholder.having_up);
    return convertView;
	}
	//加载更多
	  public void addItemLast(List<MyUpPictrue> datas) {
          myUpPictrues.addAll(datas);
      }
	  //下拉刷新
	  public void addItemTop(List<MyUpPictrue> datas) {
          for (MyUpPictrue info : datas) {
        	  myUpPictrues.addFirst(info);
          }
	  }
    public void setTask(TaskTool tasktool){
    	this.tasktool=tasktool;
    }
    public void cleardata(){
    	myUpPictrues.clear();
    }
}
