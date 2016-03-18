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
 *ʵ��ˢ��ͷ
 */
public class XListViewHeader extends LinearLayout {
	private LinearLayout mContainer;//ˢ��ͷ���ܲ���
	private ImageView mArrowImageView;//ˢ�¼�ͷͼ��
	private ProgressBar mProgressBar;//ˢ�½�����
	private TextView mHintTextView;//��ʾˢ��״̬���ı���
	private TextView mTimeTextView;//��ʾˢ��ʱ��
	private int mState = STATE_NORMAL;//ˢ��״̬
    
	private LoadDialog loadDialog;//����ˢ��ͷ���Ի���ѡ����ʧ���ǳ���

	
	private Animation mRotateUpAnim;//ˢ��ͼ���animation
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
		// ��ʼ�������������ˢ��view�߶�Ϊ0
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
	//����ˢ��ͷ��״̬�����ݲ�ͬ��״̬������ͬ�ķ�Ӧ
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
			// ��ʾ����
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.INVISIBLE);
			mProgressBar.setVisibility(View.VISIBLE);
		} else {	// ��ʾ��ͷͼƬ
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
			mHintTextView.setText("����ˢ��");
			break;
		case STATE_READY:
			
			if (mState != STATE_READY) {
				
				mArrowImageView.clearAnimation();
				mArrowImageView.startAnimation(mRotateUpAnim);
				mHintTextView.setText("�ɿ�ˢ������");
			}
			break;
		case STATE_REFRESHING:
			
			mHintTextView.setText("���ڼ���...");
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
