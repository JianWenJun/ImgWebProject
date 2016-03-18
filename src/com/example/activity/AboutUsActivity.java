package com.example.activity;


import com.example.imgweb.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import android.view.MenuItem;


public class AboutUsActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);
		ActionBar actionbar=getActionBar();
		actionbar.setDisplayShowHomeEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);
		
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
