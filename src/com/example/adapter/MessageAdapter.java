package com.example.adapter;

import java.util.LinkedList;
import java.util.List;
import com.example.bitmapfun.util.ImageFetcher;
import com.example.imgweb.R;
import com.example.model.Message;

import com.example.view.CircularImage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter{
private String TAG="MessageAdapter--->>";
private List<Message>messages;
private Context context;
private ImageFetcher mImageFetcher;
private boolean isread=true;

 public MessageAdapter( Context c,ImageFetcher mImageFetcher) {
	    context=c;
		this.mImageFetcher=mImageFetcher;
		messages=new LinkedList<Message>();
}
public int getMessagePid(int pos){
	return messages.get(pos).getPid();
}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return messages.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return messages.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public void setData(List<Message> datas){
		messages=datas;
	}
   public void clearData(){
	   messages.clear();
   }
   public void addItemLast(List<Message> datas) {
	   messages.addAll(datas);
   }
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder;
		Message message=messages.get(position);
	   //  Log.e(TAG, "position:"+position+" pid"+message.getPid());
		if(convertView==null){
			LayoutInflater lay=LayoutInflater.from(context);
			convertView=lay.inflate(R.layout.my_message_list_item, null);
			viewholder=new ViewHolder();
			viewholder.message_user_face=(CircularImage) convertView.findViewById(R.id.my_message_imgc);
		    viewholder.message_content=(TextView) convertView.findViewById(R.id.my_message_comment_content);
		    viewholder.message_username=(TextView) convertView.findViewById(R.id.my_message_username);
		    viewholder.message_time=(TextView) convertView.findViewById(R.id.my_message_uptime);
		    viewholder.message_comment_state=(TextView) convertView.findViewById(R.id.my_message_comment_state);
		    viewholder.message_read_state=(ImageView) convertView.findViewById(R.id.my_message_read_state);
		    convertView.setTag(viewholder);
		}
		viewholder=(ViewHolder) convertView.getTag();
		//Log.e(TAG, "头像"+viewholder.message_username);
		mImageFetcher.loadImage(message.getUserface(), viewholder.message_user_face);
		viewholder.message_content.setText(message.getMessageContent());
		viewholder.message_username.setText(message.getUsername());
	    viewholder.message_time.setText(message.getHowLongBefore()); 
	    isread=message.isIsread();
	    if(!isread){
	    	viewholder.message_read_state.setVisibility(View.VISIBLE);
	    }else 
	    	viewholder.message_read_state.setVisibility(View.GONE);
	    if(message.getMsgObj()==-1){
	    	  viewholder.message_comment_state.setText("评论了你的图片"+message.getPicDescribe());
	    }
	    else 
	    	  viewholder.message_comment_state.setText("回复了你");
		return convertView;
	}
	class ViewHolder{
		ImageView message_read_state;
		CircularImage message_user_face;
		TextView message_username,message_comment_state,message_time,message_content;
	}

}
