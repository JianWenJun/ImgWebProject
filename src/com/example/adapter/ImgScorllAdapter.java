package com.example.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class ImgScorllAdapter extends PagerAdapter{
private	List<View> mListViews;
 public ImgScorllAdapter(List<View> mListViews) {
	 this.mListViews=mListViews;
 }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListViews.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((View) object);
	}
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

	@Override
	public void finishUpdate(ViewGroup container) {
		// TODO Auto-generated method stub
		super.finishUpdate(container);
	}

	@Override
	public Object instantiateItem(ViewGroup v, int i) {
	
		((ViewPager) v).removeView(mListViews.get(i % mListViews.size()));
		((ViewPager) v).addView(mListViews.get(i % mListViews.size()), 0);
		
		return mListViews.get(i % mListViews.size());
	}

	@Override
	public void startUpdate(ViewGroup container) {
		// TODO Auto-generated method stub
		super.startUpdate(container);
	}
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}
     
}
