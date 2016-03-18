package com.example.view;




import com.example.imgweb.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

public class ShareItemPopupWindow extends PopupWindow {
	int w;//
	    private ImageView select_menu_qq,
	    select_menu_sina, select_menu_wei,
	    select_menu_qq_Zqone,select_menu_wechatmoments;  
	    private Button select_menu_cancell;
	    private View mMenuView;
		public ShareItemPopupWindow(Activity context,OnClickListener itemsOnClick,int res)
		{
			super(context);
			 LayoutInflater inflater = (LayoutInflater) context  
		                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
		        mMenuView = inflater.inflate(res, null);  
		        
		        this.setContentView(mMenuView);
		         w = context.getWindowManager().getDefaultDisplay().getWidth();  
		        this.setWidth(w);  
		        //设置SelectPicPopupWindow弹出窗体的高  
		        this.setHeight(LayoutParams.WRAP_CONTENT);  
		        //设置SelectPicPopupWindow弹出窗体可点ji 
		        this.setFocusable(true);  
		        this.setBackgroundDrawable(null); 
		        this.setAnimationStyle(R.style.ShareItemPopWindow);
		        select_menu_qq=(ImageView) mMenuView.findViewById(R.id.select_menu_qq);
		        select_menu_sina=(ImageView) mMenuView.findViewById(R.id.select_menu_sina);
		        select_menu_wei=(ImageView) mMenuView.findViewById(R.id.select_menu_wei);
		        select_menu_qq_Zqone=(ImageView) mMenuView.findViewById(R.id.select_qq_qzone);
		        select_menu_wechatmoments=(ImageView) mMenuView.findViewById(R.id.select_wechatmoments);
		        select_menu_cancell=(Button) mMenuView.findViewById(R.id.select_menu_cancell);
		        select_menu_cancell.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dismiss();
					}
				});
		        mMenuView.setOnTouchListener(new OnTouchListener() {               
		            public boolean onTouch(View v, MotionEvent event) {  
		                  
		                int height = mMenuView.findViewById(R.id.pop_layout).getTop();  
		                int y=(int) event.getY();  
		                if(event.getAction()==MotionEvent.ACTION_UP){  
		                    if(y<height){  
		                        dismiss();  
		                    }  
		                }                 
		                return true;  
		            }  
		        });  
		       
		        select_menu_qq.setOnClickListener(itemsOnClick);
		        select_menu_qq_Zqone.setOnClickListener(itemsOnClick);
		        select_menu_sina.setOnClickListener(itemsOnClick);
		        select_menu_wechatmoments.setOnClickListener(itemsOnClick);
		        select_menu_wei.setOnClickListener(itemsOnClick);
		        
		}
		 @SuppressLint("NewApi")
		public void showPopupWindow(View parent) {  
		        if (!this.isShowing()) {  
		        	this.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		          
		        } else {  
		            this.dismiss();  
		        }  
		    }  
}
