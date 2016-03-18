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
 *   �Զ���ͼƬ������װÿһ��ÿһ�ŵ�ͼƬ
 */
public class FlowView extends ImageView implements OnClickListener,OnLongClickListener{
private Context context;
private Bitmap bitmap;
private int columnIndex;// ͼƬ���ڵڼ���
private String fileName;
private int Pid;//ͼƬ��id
private int type;//ͼƬ������ȡ���ղػ���ɾ��
private boolean IsGif=false;//ͼƬ�Ƿ��Ƕ�̬ͼ
private TaskTool tasktool;
private Map<String, Object>parame;//���������������
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
          ����ͼƬ�̶���С������ͼƬ��߱ȣ���Ҫ�������ã�
      1�� ����setAdjustViewBoundsΪtrue��
      2�� ����maxWidth��MaxHeight��
      3�� ��������layout_width��layout_heightΪwrap_content��
            */
          setAdjustViewBounds(true);
 
      }
      public void setTaskToll(TaskTool tasktool){
    	  this.tasktool=tasktool;
      }
		@Override
		public boolean onLongClick(View v) {
			if(type==1){
				TaskApplication.showTip(R.drawable.tips_error, "ɾ���ϴ�", context);
				 parame=new HashMap<String, Object>();
				 parame.put("tasktype", Constants.DELETE_UPTOWEB);
				 parame.put("pId", Pid);
				 tasktool.getWebJson(Constants.URL_DELETE_UPTOWEB, parame);
			}
			if(type==2){
			 TaskApplication.showTip(R.drawable.tips_error, "ȡ���ղ�", context);
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
