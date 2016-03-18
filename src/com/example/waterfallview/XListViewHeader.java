package com.example.waterfallview;

import com.example.imgweb.R;
import com.example.view.LoadDialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 
 * @author JWJ1
 *实现刷新头
 */
public class XListViewHeader extends LinearLayout {
	private LinearLayout mContainer;//刷新头的总布局
	private ImageView mArrowImageView;//刷新箭头图标
	private ProgressBar mProgressBar;//刷新进度条
	private TextView mHintTextView;//显示刷新状态的文本框
	private TextView mTimeTextView;//显示刷新时间
	private int mState = STATE_NORMAL;//刷新状态
    
	private LoadDialog loadDialog;//根据刷新头，对话框选择消失还是出现

	
	private Animation mRotateUpAnim;//刷新图标的animation
	private Animation mRotateDownAnim;
	
	private final int ROTATE_ANIM_DURATION = 180;
	
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;
	public void setLoadHeadDialog(LoadDialog lo){
		loadDialog=lo;
	}
	public XListViewHeader(Context context) {
		super(context);
		initView(context);
		
	}
	public XListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}
	@SuppressLint("InflateParams")
	private void initView(Context context) {
		// 初始情况，设置下拉刷新view高度为0
		@SuppressWarnings("deprecation")
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 0);
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.xlistview_header, null);
		addView(mContainer, lp);
		setGravity(Gravity.BOTTOM);

		mArrowImageView = (ImageView)findViewById(R.id.xlistview_header_arrow);
		mHintTextView = (TextView)findViewById(R.id.xlistview_header_hint_textview);
		mTimeTextView=(TextView) findViewById(R.id.xlistview_header_time);
		mProgressBar = (ProgressBar)findViewById(R.id.xlistview_header_progressbar);
		
		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
	}
	//设置刷新头的状态，根据不同的状态做出不同的反应
	public void setState(int state) {
		if (state == mState) {if(loadDialog!=null)
			loadDialog.cancel();
		Log.w("XLIST", "kuang1:"+loadDialog);return ;}
		
		if (state == STATE_REFRESHING) {
			/*if(loadDialog!=null)
				{loadDialog.show();
				//loadDialog.setAnimationLoop(5000);
				}*/
			    
			Log.w("XLIST", "kuang3:"+loadDialog);
			// 显示进度
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.INVISIBLE);
			mProgressBar.setVisibility(View.VISIBLE);
		} else {	// 显示箭头图片
			if(loadDialog!=null)
				loadDialog.cancel();
			Log.w("XLIST", "kuang2:"+loadDialog);
			mArrowImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.INVISIBLE);
		}
		
		switch(state){
		case STATE_NORMAL:
			
			if (mState == STATE_READY) {
				
				mArrowImageView.startAnimation(mRotateDownAnim);
			}
			if (mState == STATE_REFRESHING) {
				mArrowImageView.clearAnimation();
			}
			mHintTextView.setText("下拉刷新");
			break;
		case STATE_READY:
			
			if (mState != STATE_READY) {
				
				mArrowImageView.clearAnimation();
				mArrowImageView.startAnimation(mRotateUpAnim);
				mHintTextView.setText("松开刷新数据");
			}
			break;
		case STATE_REFRESHING:
			
			mHintTextView.setText("正在加载...");
			break;
			default:
		}
		
		mState = state;
	}
	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContainer
				.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	public int getVisiableHeight() {
		return mContainer.getHeight();
	}
}
