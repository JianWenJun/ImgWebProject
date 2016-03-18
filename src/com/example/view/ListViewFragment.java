
package com.example.view;

import java.io.IOException;
import java.util.List;
import org.apache.http.Header;
import com.example.activity.ImgDetailActivity;
import com.example.activity.TaskApplication;
import com.example.adapter.CommentAdapter;
import com.example.bitmapfun.util.ImageFetcher;
import com.example.imgweb.R;
import com.example.model.Comments;
import com.example.model.Tag;
import com.example.model.UserInfo;
import com.example.tool.Constants;
import com.example.tool.TaskTool;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.annotation.TargetApi;
import android.app.Activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * /*
* 图片详情的界面
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ListViewFragment extends HeaderFragment {
	//actionBarHeader
	private CircularImage up_user_img;
	private TextView up_time;
	private TextView up_name;
	private TextView up_summary;
	private TextView up_tag1,up_tag2,up_tag3;
	private GifImageView gifView;
	private ImageView show_img;
	private LoadDialog dialog;
	private AsyncHttpClient asyncHttpClient;
	
	private String url_pic;
	
    private ListView mListView;
    private CommentAdapter commentAdapter;
    private ImageFetcher imgfetcher;
    public  ListViewFragment(Context c,ImageFetcher imgfetcher,TaskTool tool,int pid,boolean isLogin){
    	commentAdapter=new CommentAdapter(c, imgfetcher,tool,pid,isLogin);
    	
    	this.imgfetcher=imgfetcher;
   }
    public String getURL(){
    	return Constants.URL_PLTH+url_pic; 
    }
    public void setGifImg(boolean is,String url){
    	url_pic=url;
    	if(url.endsWith(".gif")||url.endsWith(".GIF"))
    		is=true;
    	if(!is){
    		
    		show_img.setVisibility(View.VISIBLE);
    		gifView.setVisibility(View.GONE);
    		imgfetcher.loadImage(Constants.URL_PLTH+url, show_img);
    	}else{
    		show_img.setVisibility(View.GONE);
    		gifView.setVisibility(View.VISIBLE);
    		dialog = new LoadDialog(getActivity());
    		dialog.show();
    		asyncHttpClient = new AsyncHttpClient();
    		asyncHttpClient.get(Constants.URL_PLTH+url, new AsyncHttpResponseHandler() {
							
				
				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					// TODO Auto-generated method stub
					GifDrawable drawable = null;
					try {
						drawable = new GifDrawable(arg2);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					gifView.setBackgroundDrawable(drawable);

					dialog.dismiss();

				}
				
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					// TODO Auto-generated method stub
				TaskApplication.showTip(R.drawable.tips_error, "加载网络图片出错", getActivity());
					dialog.dismiss();
				}
			});
    	}
    	
    }
    //获得评论
    public void setAdapterData(List<Comments>comments){
    	commentAdapter.setData(comments);
    	setListViewAdapter(mListView, commentAdapter);
    }
    //更新用户除图片外使用的VIew
    public void setUpUserData(UserInfo userinfo,String summary,String date,List<Tag>tags){
    	imgfetcher.loadImage(userinfo.getUser_img_url(), up_user_img);
    	up_time.setText(Constants.formattime(date));
    	up_name.setText(userinfo.getUsername());
    	up_summary.setText(summary);
    
    	if(tags.size()==1){
    		up_tag1.setText(tags.get(0).getTagname()); 
    		up_tag2.setText(""); 
    		up_tag3.setText("");
    		up_tag1.setBackgroundColor(Color.rgb(00, 197,205));
    		up_tag2.setBackgroundColor(Color.WHITE);
    		up_tag3.setBackgroundColor(Color.WHITE);
    		
    	}
    	if(tags.size()==2){
    		up_tag1.setText(tags.get(0).getTagname());  
    		up_tag2.setText(tags.get(1).getTagname());
    		up_tag3.setText("");
    		up_tag1.setBackgroundColor(Color.rgb(00, 197,205));
    		up_tag2.setBackgroundColor(Color.rgb(00, 197,205));
    		up_tag3.setBackgroundColor(Color.WHITE);
    	}
    	if(tags.size()==3||tags.size()>3){
    		up_tag1.setText(tags.get(0).getTagname());  
    		up_tag2.setText(tags.get(1).getTagname());  
    		up_tag3.setText(tags.get(2).getTagname());
    		up_tag1.setBackgroundColor(Color.rgb(00, 197,205));
    		up_tag2.setBackgroundColor(Color.rgb(00, 197,205));
    		up_tag3.setBackgroundColor(Color.rgb(00, 197,205));
    	}        	
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setHeaderBackgroundScrollMode(HEADER_BACKGROUND_SCROLL_PARALLAX);
        setOnHeaderScrollChangedListener(new OnHeaderScrollChangedListener() {
            @Override
            public void onHeaderScrollChanged(float progress, int height, int scroll) {
                height -= getActivity().getActionBar().getHeight();

                progress = (float) scroll / height;
                if (progress > 1f) progress = 1f;

                // *
                // `*
                // ```*
                // ``````*
                // ````````*
                // `````````*
                progress = (1 - (float) Math.cos(progress * Math.PI)) * 0.5f;
                ((ImgDetailActivity) getActivity())
                        .getFadingActionBarHelper()
                        .setActionBarAlpha((int) (255 * progress));
            }
        });

    }
    @Override
    public View onCreateHeaderView(LayoutInflater inflater, ViewGroup container) {
    	View header_view=inflater.inflate(R.layout.img_detail_actionbar_header, container, false);
    	up_user_img=(CircularImage) header_view.findViewById(R.id.news_img);
    	up_name=(TextView) header_view.findViewById(R.id.news_username);
    	up_time=(TextView) header_view.findViewById(R.id.news_uptime);
    	up_summary=(TextView) header_view.findViewById(R.id.news_summary);
    	up_tag1=(TextView) header_view.findViewById(R.id.news_tag1);
    	up_tag2=(TextView) header_view.findViewById(R.id.news_tag2);
    	up_tag3=(TextView) header_view.findViewById(R.id.news_tag3);
    	gifView=(GifImageView) header_view.findViewById(R.id.gif);
    	show_img=(ImageView) header_view.findViewById(R.id.img_detail);
    	//gifView.setGifImageType(GifImageType.WAIT_FINISH);
    	return header_view;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
    	mListView =  (ListView) inflater.inflate(R.layout.img_detail_actionbar_content, container,false);            
        
    	return mListView;
    }
	public void Fresh(List<Comments>comments) {
		commentAdapter.setData(comments);		
		commentAdapter.notifyDataSetChanged();
		
	}
   
}
