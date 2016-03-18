package com.example.activity;


import java.util.ArrayList;

import com.example.bitmapfun.util.ImageFetcher;
import com.example.imgweb.R;
import com.example.view.MyStoreFragment;
import com.example.view.MyUpFragment;
import com.example.view.MyViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class MyHavingActivity extends FragmentActivity {
	public static boolean IsUp=true;//true表示打开是我的上传，false表示我的收藏
	private ArrayList<Fragment> fragments; 
	private MyViewPager viewPager;
	private ImageFetcher mImageFetcher;		
	private MyStoreFragment storeFragment;
	private MyUpFragment upFragement;
    private String TAG="MyHavingActivity--->>"; 
    private TextView tab_up,tab_store;
    private Button back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.myhaving);	
		mImageFetcher=new ImageFetcher(this, 200);	
		tab_up=(TextView) findViewById(R.id.tab_up);
		tab_store=(TextView) findViewById(R.id.tab_store);
		back=(Button) findViewById(R.id.myhaving_back);
		fragments = new ArrayList<Fragment>();
		storeFragment=new MyStoreFragment(mImageFetcher,this);
		upFragement=new MyUpFragment(mImageFetcher,this);
		fragments.add(upFragement);
		fragments.add(storeFragment);
		viewPager = (MyViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(new FragmentStatePagerAdapter(
				getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return fragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return fragments.get(arg0);
			}
		});
 
		Intent intent=getIntent();
		if(intent!=null){
			int pag=intent.getIntExtra("pagm", 1);
			//根据用户点击的是我的上传还是我的收藏来显示pag
			if(pag==0) {
				tab_up.setVisibility(View.VISIBLE);
				tab_store.setVisibility(View.GONE);
				MyHavingActivity.IsUp=true;
			}
			else if(pag==1) 
			{
				MyHavingActivity.IsUp=false;
			tab_up.setVisibility(View.GONE);
			tab_store.setVisibility(View.VISIBLE);
			}
			viewPager.setCurrentItem(pag);
					}
	back.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			MyHavingActivity.this.finish();
		}
	});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}


}
