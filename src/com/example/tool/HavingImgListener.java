package com.example.tool;





import com.example.activity.TaskApplication;
import com.example.imgweb.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class HavingImgListener implements OnClickListener {
private int type;
private Context cont;
public HavingImgListener(int type,Context con){
	this.type=type;
	cont=con;
}
	@Override
	public void onClick(View v) {
		if(type==1){
			//�ҵ��ϴ�
			TaskApplication.showTip(R.drawable.tips_error, "����ͼɾ��", cont);
		}
		if(type==2){
			//�ҵ��ղ�
			TaskApplication.showTip(R.drawable.tips_error, "����ͼȡ��", cont);
			
		}
		
	}


}
