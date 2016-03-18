package com.example.adapter;

import java.util.LinkedList;
import java.util.List;

import com.example.bitmapfun.util.ImageFetcher;
import com.example.imgweb.R;
import com.example.model.Picture;
import com.example.tool.Constants;
import com.example.view.CircularImage;
import com.example.waterfallview.FlowView;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HomePictureAdapter extends BaseAdapter{
private String TAG="HomePictureAdapter";
private Context context;
private ImageFetcher mImageFetcher;
private LinkedList<Picture>pictures;
public  HomePictureAdapter(Context c,ImageFetcher imageFetcher) {
 		context=c;
 		pictures=new LinkedList<Picture>();
 		this.mImageFetcher=imageFetcher;
 		 		
}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pictures.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pictures.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder holder;
      int taglength = 0;
      Picture picture=pictures.get(position);
      if(convertView==null){
    	  LayoutInflater layin=LayoutInflater.from(context);
    	  convertView=layin.inflate(R.layout.home_list_infos_item, null);
    	  holder=new ViewHolder();  
    	  holder.news_pic=(FlowView) convertView.findViewById(R.id.news_pic);  	 
    	  holder.news_title=(TextView) convertView.findViewById(R.id.news_title);
    	  holder.news_tags[0]=new TextView(context);
    	  holder.news_tags[0]=(TextView) convertView.findViewById(R.id.news_tag1);
    	  holder.news_tags[1]=new TextView(context);
    	  holder.news_tags[1]=(TextView)convertView.findViewById(R.id.news_tag2);
    	  holder.news_tags[2]=new TextView(context);
    	  holder.news_tags[2]=(TextView) convertView.findViewById(R.id.news_tag3);
    	  holder.news_img=(CircularImage) convertView.findViewById(R.id.news_img);
    	  holder.news_username=(TextView) convertView.findViewById(R.id.news_username);
    	  holder.news_uptime=(TextView) convertView.findViewById(R.id.news_uptime);
    	  convertView.setTag(holder);
      }
        holder=(ViewHolder) convertView.getTag();
        //if(!picture.isIsGIf()) 
        //将图片的ID与图片绑定
        holder.news_pic.setGif(picture.isIsGIf());
        holder.news_pic.setPictureId(Integer.parseInt(picture.getId()));
        mImageFetcher.loadImage(Constants.URL_PLTH+picture.getImg_url(), holder.news_pic);
        mImageFetcher.loadImage(picture.getUserface_url(), holder.news_img);
        if(picture.getTags().length>3) taglength=3;
        else taglength=picture.getTags().length;
        for(int i=0;i<taglength;i++){
    	      	holder.news_tags[i].setText(picture.getTags()[i]);
        }
        holder.news_title.setText(picture.getTitle());
        holder.news_uptime.setText(Constants.formattime(picture.getUploaddata()));
        holder.news_username.setText(picture.getUsername());
		return convertView;
	}
	  public void addItemLast(List<Picture> datas) {
          pictures.addAll(datas);
      }
	  public void addItemTop(List<Picture> datas) {
          for (Picture info : datas) {
        	  pictures.addFirst(info);
          }
	  }
	  public void clearData(){
		  pictures.clear();
	  }
	class ViewHolder{
		FlowView news_pic;//展示的图片
		TextView news_title;//图片的标题
		TextView[] news_tags=new TextView[3];//3个标签
		//TextView news_tag1,news_tag2,news_tag3;
		CircularImage news_img;//上传用户照片
		TextView news_username;//上传者
		TextView news_uptime;	//上传时间
	}
}
