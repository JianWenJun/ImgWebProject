package com.example.activity;


import java.util.HashMap;

import java.util.List;
import java.util.Map;
import com.example.bitmapfun.util.ImageFetcher;
import com.example.imgweb.R;

import com.example.model.PictureDetail;
import com.example.tool.AndroidShare;
import com.example.tool.Constants;
import com.example.tool.JsonTool;
import com.example.tool.TaskTool;
import com.example.view.CommentDialog;
import com.example.view.FadingActionBarHelper;
import com.example.view.ListViewFragment;
import com.example.view.ShareItemPopupWindow;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImgDetailActivity extends Activity implements OnClickListener ,OnCheckedChangeListener{
private String TAG="ImgDetailActivity---->";
private ActionBar actionbar;
private FadingActionBarHelper mFadingActionBarHelper;
private Intent intent;
private boolean isGif=false;
private boolean IsLogin=false;

private CheckBox img_store_checkbox;
private ShareItemPopupWindow sharePopupWindow;
private LinearLayout img_comment;
private ListViewFragment list_comments_fragment;
private CommentDialog commentDialog;//评论的对话框
private String up_name;//图片上传者的名称
private int pid; //图片的id
//actionbar 上的按钮
private Button img_back;
private ImageView img_share;
private View actionbar_view;

//消息任务
private TaskTool taskhelp;
private Map<String, Object>parame;//传递网络请求参数
@SuppressLint("HandlerLeak")
private Handler handler=new Handler(){
  @Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case Constants.GET_IMG_DETAIL:
			{
				//处理服务器返回的数据
				PictureDetail picture_detail=JsonTool.getPictureDetail((String) msg.obj);
				list_comments_fragment.setAdapterData(picture_detail.getComments());
				up_name=picture_detail.getUserinfo().getUsername();
				list_comments_fragment.setUpUserData(picture_detail.getUserinfo(),picture_detail.getSummary()
						,picture_detail.getUploadData(),picture_detail.getTags());		        
				list_comments_fragment.setGifImg(isGif,picture_detail.getUrl_picture());
				if(picture_detail.isIscollect()){
					img_store_checkbox.setChecked(true);
				}else img_store_checkbox.setChecked(false);
			}			
			break;
		case  Constants.COMMENT_SUBMIT:
			if(JsonTool.getCommentSubmit((String) msg.obj)){
				TaskApplication.showTip(R.drawable.tips_smile, "评论成功", ImgDetailActivity.this);
				
				parame=new HashMap<String, Object>();
			    parame.put("tasktype", Constants.COMMENT_SUBMIT_FRESH);
			    parame.put("pId", pid);
			    taskhelp.getWebJson(Constants.PICTURE_DETAIL, parame);
				
			}else{
				TaskApplication.showTip(R.drawable.tips_error, "评论异常", ImgDetailActivity.this);
					
			}
	    
			break;
		case Constants.COMMENT_SUBMIT_FRESH:
			PictureDetail picture_detail=JsonTool.getPictureDetail((String) msg.obj);
			Log.e(TAG, ""+(String) msg.obj);
			list_comments_fragment.Fresh(picture_detail.getComments());
			break;
	   case Constants.COMMENT_DELETE:
		   if(JsonTool.getCommentDelete((String) msg.obj)){
				TaskApplication.showTip(R.drawable.tips_smile, "删除成功", ImgDetailActivity.this);
				//刷新评论界面
				parame=new HashMap<String, Object>();
			    parame.put("tasktype", Constants.COMMENT_SUBMIT_FRESH);
			    parame.put("pId", pid);
			    taskhelp.getWebJson(Constants.PICTURE_DETAIL, parame);
		   }
	   else TaskApplication.showTip(R.drawable.tips_error, "删除异常", ImgDetailActivity.this);			
		   
			break;
		default:
	     break;
		}
	}
	
};
public  void showImgDeatil(String text){
	
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.img_detail);
		initactionBarview();
		actionbar=getActionBar();
		actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionbar.setCustomView(actionbar_view);		
		   intent=getIntent();
	       isGif=intent.getBooleanExtra("Gif", false);
	       IsLogin=intent.getBooleanExtra("Login", false);
	       pid=intent.getIntExtra("Pid",0);
	       taskhelp=new TaskTool(handler);
		initview();
	        //定义白色为Actionbar的背景色
	        mFadingActionBarHelper = new FadingActionBarHelper(getActionBar(),
	                getResources().getDrawable(R.drawable.actionbar_bg1));
	        list_comments_fragment=new ListViewFragment(this,new ImageFetcher(this, 200),taskhelp,pid,IsLogin); 
	        if (savedInstanceState == null) {
	            getFragmentManager().beginTransaction()
	                    .add(R.id.container,list_comments_fragment )
	                    .commit();
	        }
	     //   System.out.println(TAG+getIntent().getIntExtra("Pid",0));
      
       parame=new HashMap<String, Object>();
       parame.put("tasktype", Constants.GET_IMG_DETAIL);
       parame.put("pId",pid );
       taskhelp.getWebJson(Constants.PICTURE_DETAIL, parame);
	}
	
	public  void initview()
	{
		img_store_checkbox=(CheckBox) findViewById(R.id.detail_like);
		img_comment=(LinearLayout) findViewById(R.id.comment);
		img_comment.setOnClickListener(this);
		img_store_checkbox.setOnCheckedChangeListener(this);		
		
	}
	@SuppressLint("InflateParams")
	public void initactionBarview(){
		LayoutInflater in=LayoutInflater.from(getApplicationContext());
		 actionbar_view=in.inflate(R.layout.img_detail_actionbar, null);
		img_back=(Button) actionbar_view.findViewById(R.id.myinfo_actionbar_back);
		img_share=(ImageView)actionbar_view.findViewById(R.id.myinfo_actionbar_share);		
		img_back.setOnClickListener(this);
		img_share.setOnClickListener(this);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return true;
	}
	public FadingActionBarHelper getFadingActionBarHelper() {
	        return mFadingActionBarHelper;
	    }
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
	
		case R.id.comment:
		{
			if(IsLogin){
				
				commentDialog=new CommentDialog(this ,pid, -1, -1,taskhelp,up_name);
				commentDialog.show();
			}else{
				TaskApplication.showTip(R.drawable.tips_error, "请登入", this);
			}
		}
			
			break;
		case R.id.myinfo_actionbar_back:
			finish();
			break;
		case R.id.myinfo_actionbar_share:
			Log.e("分享", "fengxiang");
			/*sharePopupWindow=new ShareItemPopupWindow(ImgDetailActivity.this, new ItemOnclike(), R.layout.img_detail_share_item);
			sharePopupWindow.showPopupWindow(findViewById(R.id.contain));*/
			AndroidShare sh=new AndroidShare(ImgDetailActivity.this, "分享", list_comments_fragment.getURL());
			sh.show();
			break;
		default:
			
			break;
		}
		
	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		
		if(IsLogin){
			
	if(isChecked){
		/*收藏图片
		1.先判断是否登录
		*/
		
		TaskApplication.showTip(R.drawable.tips_smile, "已经收藏", this);
		 parame=new HashMap<String, Object>();
		 parame.put("tasktype", Constants.STOREING);
		 parame.put("pId", pid);
		 parame.put("Collect", false);
		 taskhelp.getWebJson(Constants.URL_WEBSTORE, parame);
		 
	}else{
		//取消收藏
		TaskApplication.showTip(R.drawable.tips_error, "取消收藏", this);
		 parame=new HashMap<String, Object>();
		 parame.put("tasktype", Constants.UNSTOREING);
		 parame.put("pId", pid);
		 parame.put("Collect", true);
		 taskhelp.getWebJson(Constants.URL_WEBSTORE, parame);
	}
	}else{
		TaskApplication.showTip(R.drawable.tips_error, "请登入", this);
		img_store_checkbox.setChecked(false);
	}
	}
/*class ItemOnclike implements OnClickListener{

	@Override
	public void onClick(View v) {
		AndroidShare share=null;
		switch (v.getId()) {
		case R.id.select_menu_qq:	
			share=new AndroidShare(ImgDetailActivity.this,  list_comments_fragment.getURL());
			Log.e(TAG, Constants.URL_PLTH+list_comments_fragment.getURL());
			share.setPackageName("com.tencent.mobileqq");
			share.setActivityName("com.tencent.mobileqq.activity.JumpActivity");
			share.setTitle("QQ");
	
			share.shareMsg(ImgDetailActivity.this, "图片");
			 break;
		case R.id.select_qq_qzone:
			 share=new AndroidShare(ImgDetailActivity.this,  list_comments_fragment.getURL());
			 share.setPackageName("com.qzone");
			 share.setActivityName("com.qzonex.module.operation.ui.QZonePublishMoodActivity");
			 share.setTitle("QQ空间");
			
				
		   share.shareMsg(ImgDetailActivity.this, "图片");
			break;
        case R.id.select_menu_sina:
        	 share=new AndroidShare(ImgDetailActivity.this,  list_comments_fragment.getURL());
        	 share.setPackageName("com.tencent.mobileqq");
			 share.setActivityName("com.sina.weibo.EditActivity");
			 share.setTitle("新浪微博");
        	
				
				share.shareMsg(ImgDetailActivity.this, "图片");
				break;
		case R.id.select_menu_wei:
			 share=new AndroidShare(ImgDetailActivity.this, list_comments_fragment.getURL());			
			    share.setPackageName("com.sina.weibo");
				share.setActivityName("com.tencent.mm.ui.tools.ShareImgUI");
				share.setTitle("微信");
				
				share.shareMsg(ImgDetailActivity.this, "图片");
			break;
			
        case R.id.select_wechatmoments:		
        	 share=new AndroidShare(ImgDetailActivity.this, list_comments_fragment.getURL());
        	 share.setPackageName("com.tencent.mm");
			 share.setActivityName("com.tencent.mm.ui.tools.ShareImgUI");
			 share.setTitle("微信");
				
				share.shareMsg(ImgDetailActivity.this, "图片");
            break;


		default:
			break;
		}
		
	}
	
}*/
  
}
