package com.example.adapter;

import java.util.HashMap;
/**
 * 评论的适配器
 */
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.example.activity.ImgDetailActivity;
import com.example.activity.TaskApplication;
import com.example.bitmapfun.util.ImageFetcher;
import com.example.imgweb.R;
import com.example.model.Comments;
import com.example.tool.Constants;
import com.example.tool.TaskTool;
import com.example.view.CircularImage;
import com.example.view.CommentDialog;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;

public class CommentAdapter extends BaseAdapter{
	private String TAG="CommentAdapter--->>";
private List<Comments>comments;//一级评论
//private CommentReplyAdapter comments_reply_adpater;
private Context context;
private ImageFetcher mImageFetcher;
private CommentDialog comment_dialog;
private TaskTool tool;
private Map<String, Object>parame;//传递网络请求参数
private String cid;//用于删除评论
//用于回复评论
private String name;
private int pid;
private String objUId;
private String  pCId;
private boolean isLogin;
public CommentAdapter(Context c,ImageFetcher mImageFetcher,TaskTool tool,int pid,boolean isLogin){
	context=c;
	this.mImageFetcher=mImageFetcher;
	comments=new LinkedList<Comments>();
	this.tool=tool;
	this.pid=pid;
	this.isLogin=isLogin;
	parame=new HashMap<String, Object>();
}
class ViewSet{
	CircularImage Comment_cir_img;//评论者的头像
	TextView comment_username;//评论人的名字
	TextView comment_content;//评论内容
	TextView comment_img_time;//发表评论的时间
	ImageView comment_submit,comment_delete;//删除评论和回复评论
}
public void setData(List<Comments>comments){
	this.comments= comments;
}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comments.size();
	}

	@Override
	public Object getItem(int position) {

		return comments.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Comments comment=comments.get(comments.size()-1-position);
		//Log.e("评论----》", position+"");
		ViewSet viewset=null;
		if(convertView==null){
			LayoutInflater lay=LayoutInflater.from(context);
			convertView=lay.inflate(R.layout.img_detail_action_content_listview_item, null);
			viewset=new ViewSet();
            viewset.Comment_cir_img=(CircularImage) convertView.findViewById(R.id.img_detail_imgc);
			viewset.comment_img_time=(TextView) convertView.findViewById(R.id.img_detail_img_uptime);
			viewset.comment_content=(TextView) convertView.findViewById(R.id.img_detail_content);
		    viewset.comment_submit=(ImageView) convertView.findViewById(R.id.img_detail_img_comment_submit);
			viewset.comment_delete=(ImageView) convertView.findViewById(R.id.img_detail_img_comment_delete);
			viewset.comment_username=(TextView) convertView.findViewById(R.id.img_detail_img_user);
			convertView.setTag(viewset);
		}	
			viewset=(ViewSet) convertView.getTag();
			mImageFetcher.loadImage(comment.getCommenter_face(), viewset.Comment_cir_img);
			
			viewset.comment_img_time.setText(Constants.formattime(comment.getPostDate()));
		   if(comment.getIsMe()){
			   //可删除
			   cid=comment.getCid();
			   viewset.comment_delete.setVisibility(View.VISIBLE); 
			   viewset.comment_submit.setVisibility(View.GONE);
		       viewset.comment_delete.setOnClickListener(new CommentListener(0));
		     
		   }else{
			   //可评论
			   name=comment.getCommenter_name();
		       objUId=comment.getuCid();
		       pCId=comment.getCid();
		    //   Log.e(TAG, "objUId"+objUId+" pcid"+pCId);
			   viewset.comment_submit.setVisibility(View.VISIBLE); 
			   viewset.comment_delete.setVisibility(View.GONE);
		       viewset.comment_submit.setOnClickListener(new CommentListener(1));
		    
		       
		   }
		   viewset.comment_username.setText(comment.getCommenter_name());
			//判断当前评论是不是有二级评论（带回复的）
			if((comment.getPcid().equals("-1"))){
				viewset.comment_content.setText(comment.getContent());
				
			}else{
				viewset.comment_content.setText("回复 "+getRePlyName(comment.getObjuid())+": "+comment.getContent());	
			}
			 
			return convertView;
	}
	public String getRePlyName(String uObjuid){
		String result="";
		for(Comments comment:comments){
			if(comment.getuCid().equals(uObjuid))
				result=comment.getCommenter_name();			
		}
		return result;
	}
class CommentListener implements OnClickListener{
private int type;
public  CommentListener(int type){
this.type=type;
}
	@Override
	public void onClick(View v) {
		if(type==0){
			//删除评论
			Log.e(TAG, "删除评论");
		    parame.put("tasktype", Constants.COMMENT_DELETE);
			parame.put("cId", cid);
			Log.e(TAG, "cId"+cid+"   "+tool);
			tool.getWebJson(Constants.URL_UNWEBCOMMNET, parame);
		}
		if(type==1){
			//回复评论
			Log.e(TAG, "回复评论");

         if(isLogin){
				
 			comment_dialog=new CommentDialog(context, pid, Integer.parseInt(objUId),Integer.parseInt(pCId), tool, name);
 			comment_dialog.show();
			}else{
				TaskApplication.showTip(R.drawable.tips_error, "请登入", context);
			}
		}
	}
	
}
}
