package com.example.view;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.adapter.ImgScorllAdapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
/**
 * 
 * @author JWJ1
 *   滚动图片类
 */
public class HomeImgScroll extends ViewPager{
	private String TAG="HomeImgScroll---->>";
	Context context; //
	List<View> mListViews; //要滚动的图片控件list
	int mScrollTime = 0;//每张图片停留时间
	Timer timer;
	int oldIndex = 0;
	int curIndex = 0;
	public  HomeImgScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public void start(Context context, List<View> imgList,
			int scrollTime, LinearLayout ovalLayout, int ovalLayoutId,
			int ovalLayoutItemId, int focusedId, int normalId) {
		this.context = context;
		mListViews = imgList;
		mScrollTime = scrollTime;
		setOvalLayout(ovalLayout, ovalLayoutId, ovalLayoutItemId, focusedId,
				normalId);
		this.setAdapter(new ImgScorllAdapter(imgList));//设置适配器
		if (scrollTime != 0 && imgList.size() > 1) {

			startTimer();
			// 点击停止滚动
			this.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_UP) {
						startTimer();
					} else {
						stopTimer();
					}
					return false;
				}
			});
		}
		if (mListViews.size() > 1) {
			this.setCurrentItem((Integer.MAX_VALUE / 2)
					- (Integer.MAX_VALUE / 2) % mListViews.size());
		}
	}
	// 设置滚动小圆圈图标
		private void setOvalLayout(final LinearLayout ovalLayout, int ovalLayoutId,
				final int ovalLayoutItemId, final int focusedId, final int normalId) {
			if (ovalLayout != null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				for (int i = 0; i < mListViews.size(); i++) {
					ovalLayout.addView(inflater.inflate(ovalLayoutId, null));

				}
				if (ovalLayout.getChildAt(0) == null)
					return;
				else
					ovalLayout.getChildAt(0).findViewById(ovalLayoutItemId)
							.setBackgroundResource(focusedId);
				this.setOnPageChangeListener(new OnPageChangeListener() {
					public void onPageSelected(int i) {
						curIndex = i % mListViews.size();
						ovalLayout.getChildAt(oldIndex)
								.findViewById(ovalLayoutItemId)
								.setBackgroundResource(normalId);
						//更换当前滚动位置的图片
						ovalLayout.getChildAt(curIndex)
								.findViewById(ovalLayoutItemId)
								.setBackgroundResource(focusedId);
						oldIndex = curIndex;
					}

					public void onPageScrolled(int arg0, float arg1, int arg2) {
					}

					public void onPageScrollStateChanged(int arg0) {
					}
				});
			}
		}


		/**
		 * @return
		 */
		public int getCurIndex() {
			return curIndex;
		}

		/**
		 * ֹͣ停止计时器
		 */
		public void stopTimer() {
			if (timer != null) {
				timer.cancel();
				timer = null;
			}
		}

		/**
		 * 开始计时器
		 */
		public void startTimer() {
			timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
					((Activity) context).runOnUiThread(new Runnable() {
						public void run() {
							int i=HomeImgScroll.this
									.getCurrentItem();
							i++;
							i=i%mListViews.size();
							//Log.w(TAG,"you "+(i));
							HomeImgScroll.this.setCurrentItem(i);
							
						}
					});
				}
			}, mScrollTime, mScrollTime);
		}

}
