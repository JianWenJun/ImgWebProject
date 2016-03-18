package com.example.view;

import com.example.imgweb.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * @author  JWJ
 * ¼ÓÔØ¶Ô»°¿ò
 */
public class LoadDialog extends Dialog{
private String TAG="LoadDialog--->>";
private String msg = null;
private TextView msgview = null;
private ImageView imgview = null;
private AnimationDrawable animation = null;
	public LoadDialog(Context context) {
		super(context, R.style.LoadDialogStyle);	
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_load);
		msgview = (TextView) findViewById(R.id.loaddialog_msg);
		imgview = (ImageView) findViewById(R.id.loaddialog_img);
		animation=(AnimationDrawable) imgview.getDrawable();
		
		if (msg != null) {
			msgview.setText(msg);
		}else{
			msgview.setVisibility(View.GONE);
		}
		animation.start();
	}
	public void setmsg(String msg1) {
		msg = msg1;
	}
	public void setAnimationLoop(int tiem){
	
	}

}
