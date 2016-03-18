package com.example.view;

import android.content.Context;

import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class MyViewPager extends ViewPager {

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}



	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
