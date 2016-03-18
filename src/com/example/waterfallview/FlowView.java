package com.example.waterfallview;

import java.util.HashMap;
import java.util.Map;

import com.example.activity.ImgDetailActivity;
import com.example.activity.MainActivity;
import com.example.activity.TaskApplication;
import com.example.imgweb.R;
import com.example.tool.Constants;
import com.example.tool.TaskTool;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;

/**
 * 
 * @author JWJ1
 *   自定义图片类来封装每一列每一张的图片
 */
public class FlowView extends ImageView implements OnClickListener,OnLongClickListener{
private Context context;
private Bitmap bitmap;
private int columnIndex;// 图片属于第几列
private String fileName;
private int Pid;//图片的id
private int type;//图片长按是取消收藏还是删除
private boolean IsGif=false;//图片是否是动态图
private TaskTool tasktool;
private Map<String, Object>parame;//传递网络请求参数
private static String TAG="FlowView---->>";
	  public FlowView(Context c, AttributeSet attrs, int defStyle) {
	        super(c, attrs, defStyle);
	        this.context = c;
	        Init();
	    }

	    public FlowView(Context c, AttributeSet attrs) {
	        super(c, attrs);
	        this.context = c;
	        Init();
	    }

	    public FlowView(Context c) {
	        super(c);
	        this.context = c;
	        Init();
	    }
      public void Init(){
    	  setOnClickListener(this);
    	  setOnLongClickListener(this);
    	   /*
          设置图片固定大小，保持图片宽高比，需要如下设置：
      1） 设置setAdjustViewBounds为true；
      2） 设置maxWidth、MaxHeight；
      3） 设置设置layout_width和layout_height为wrap_content。
            */
          setAdjustViewBounds(true);
 
      }
      public void setTaskToll(TaskTool tasktool){
    	  this.tasktool=tasktool;
      }
		@Override
		public boolean onLongClick(View v) {
			if(type==1){
				TaskApplication.showTip(R.drawable.tips_error, "删除上传", context);
				 parame=new HashMap<String, Object>();
				 parame.put("tasktype", Constants.DELETE_UPTOWEB);
				 parame.put("pId", Pid);
				 tasktool.getWebJson(Constants.URL_DELETE_UPTOWEB, parame);
			}
			if(type==2){
			 TaskApplication.showTip(R.drawable.tips_error, "取消收藏", context);
			 parame=new HashMap<String, Object>();
			 parame.put("tasktype", Constants.UNSTOREING_MY);
			 parame.put("pId", Pid);
			 parame.put("Collect", true);
			 tasktool.getWebJson(Constants.URL_WEBSTORE, parame);
			}
		
			return true;
		}

		@Override
		public void onClick(View v) {

			Intent in=new Intent(context,ImgDetailActivity.class);
			in.putExtra("Pid", Pid);
			in.putExtra("Gif", IsGif);	
			if(context instanceof MainActivity )
			in.putExtra("Login", ((MainActivity)context).getLoginInfo());
			else in.putExtra("Login", true);
			context.startActivity(in);
		     
		}
		public void setPictureId(int  id){
			
			this.Pid=id;
		}
         public void setGif(boolean is){
			
			IsGif=is;
		}

		public void setID(int i) {
			type=i;
			
		}
}
