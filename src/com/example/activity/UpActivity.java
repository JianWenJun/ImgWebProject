package com.example.activity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.imgweb.R;
import com.example.tool.BitmapUtil;
import com.example.tool.Constants;
import com.example.tool.JsonTool;
import com.example.tool.SDcardHelp;
import com.example.tool.TaskTool;
import com.example.tool.UriTool;
import com.example.view.LoadDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class UpActivity extends Activity implements OnClickListener{
//showimg��Ŀؼ�	
private Button showimg_show;
private ImageView showimg_cancell,showimg_img;
private EditText showimg_des,showimg_tag;
	
private static String TAG="UpActivity---->>";
//����ͼƬ�ĵ�ַ
//private List<String> bitmapList = new ArrayList<String>();
private ImageView up_close;
private RelativeLayout up_camera,up_mapstore,show1,show2;
private final int PHOTO_REQUEST_GALLERY=1;//ͼ���ȡ
private final int PHOTO_REQUEST_CAREMA=2;//����

private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
private File tempFile;
private Bitmap bitmap;
private LoadDialog loaddialog;
//private boolean isCarame=true;//�Ƿ�������
private TaskTool taskhelp;
private Handler handler=new Handler(){
	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case Constants.UPTOWEB:			
			if(JsonTool.getUpImg((String) msg.obj)){
				TaskApplication.showTip(R.drawable.tips_smile, "�ϴ��ɹ�", UpActivity.this);
				UpActivity.this.finish();
				UpActivity.this.overridePendingTransition(0,R.anim.anim_down_to_up);				
			}else TaskApplication.showTip(R.drawable.tips_error, "�ϴ�ʧ�ܣ������쳣", UpActivity.this);
			loaddialog.cancel();
			break;

		default:
			break;
		}
	}
	
};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.up);
		initview();		
		taskhelp=new TaskTool(handler);
		loaddialog=new LoadDialog(this);
	}
   public void initview(){
	   show1=(RelativeLayout) findViewById(R.id.show1);
	   show2=(RelativeLayout) findViewById(R.id.show2);
	   up_close=(ImageView) findViewById(R.id.up_close);
	   up_camera=(RelativeLayout) findViewById(R.id.up_camera);
	   up_mapstore=(RelativeLayout) findViewById(R.id.up_mapstore);
	   
	    showimg_cancell=(ImageView)findViewById(R.id.showimg_cancell);
		showimg_show=(Button)findViewById(R.id.showimg_show);
		showimg_img=(ImageView)findViewById(R.id.showimg_img);
		showimg_des=(EditText)findViewById(R.id.showimg_des);
		showimg_tag=(EditText) findViewById(R.id.showimg_tag);	
		showimg_cancell.setOnClickListener(this);
		showimg_show.setOnClickListener(this);	
		
	   up_camera.setOnClickListener(this);
	   up_mapstore.setOnClickListener(this);
	   up_close.setOnClickListener(this);
   }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction()==KeyEvent.ACTION_DOWN&&keyCode==KeyEvent.KEYCODE_BACK){
		finish();
    	overridePendingTransition(0,R.anim.anim_down_to_up);	
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != RESULT_CANCELED) {				
		switch(requestCode){
		 case PHOTO_REQUEST_GALLERY: 
			 //ͼ��
		 { 
			 //isCarame=false;
		 if(data!=null) {
				   String path= UriTool.getPath(UpActivity.this, data.getData());
				   tempFile=new File(path);
				   getImageToView2(data.getData());
				}
				
				
				
			
		 } 
		 break;
		 case PHOTO_REQUEST_CAREMA: {
			 //����
			// isCarame=true;
			 if(Constants.IsHaveSd()){
				 File path = Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
					 tempFile = new File(path, PHOTO_FILE_NAME);
					getImageToView2(Uri.fromFile(tempFile));
			
			}else {
				TaskApplication.showTip(R.drawable.tips_error, "û���ڴ濨", this);
				File path = Environment.getDataDirectory();
				 tempFile = new File(path, PHOTO_FILE_NAME);
				// startPhotoZoom(Uri.fromFile(tempFile));
				getImageToView2(Uri.fromFile(tempFile));
			}
			 }  break;
		  default:
			  break;
		 }
		
		
		}
		super.onActivityResult(requestCode, resultCode, data);
		
	}

	/**
	 * ����ͼƬ����
	 * 
	 * @param picdata
	 */
	private void getImageToView2(Uri uri) {

		if (uri != null) {
			 bitmap = BitmapUtil.getBitmapFromUri(this, uri);
			// Log.e(TAG, "����ͼƬ");
			    show2.setVisibility(View.VISIBLE);
			    show1.setVisibility(View.GONE);
			    show2.setBackgroundColor(Color.WHITE);
			   
			   
			// myface.setImageBitmap(photo);
			    
			if (bitmap != null)
				savePic(bitmap);
			// photo=Tools.toRoundCorner(photo,7);
			// Drawable drawable = new
			// BitmapDrawable(this.getResources(),photo);
		}
	}

	private void savePic(Bitmap photo) {

		long millis = System.currentTimeMillis();
		String fileName = millis + ".jpg";
		String tempImgPath = getFilesDir().getAbsolutePath() + "/smsc/temp/"
				+ fileName;
		
		String dir = getDir(tempImgPath);

		File dirFile = new File(dir);
		
		dirFile.mkdirs();
		if (!dirFile.exists()) {
			Toast.makeText(this, "�޷�����SD��Ŀ¼,ͼƬ�޷�����", Toast.LENGTH_LONG).show();
		}
		
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					                          new FileOutputStream(tempImgPath));
			photo.compress(Bitmap.CompressFormat.JPEG, 75, bos);// (0 - 100)ѹ���ļ�
			
			bitmap=BitmapUtil.getBitmap(tempImgPath, 200, 200);
			showimg_img.setImageBitmap(bitmap);
			
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	public String getDir(String filePath) {
		int lastSlastPos = filePath.lastIndexOf('/');
		return filePath.substring(0, lastSlastPos);
	}
	@SuppressLint("ResourceAsColor")
	@Override
	public void onClick(View v) {
	switch (v.getId()) {
	case R.id.up_camera:
		Intent i=new Intent("android.media.action.IMAGE_CAPTURE");//�������
		if(Constants.IsHaveSd())
		{    
			File path=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);			
			tempFile=new File(path,PHOTO_FILE_NAME);
			Uri ui=Uri.fromFile(tempFile);
			i.putExtra(MediaStore.EXTRA_OUTPUT, ui);//����Ƭ��ŵ�ָ�����ļ���ȥ
		}else{
		File path = Environment.getDataDirectory();
		tempFile = new File(path, PHOTO_FILE_NAME);
		i.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(tempFile));	
		}
		UpActivity.this.startActivityForResult(i, PHOTO_REQUEST_CAREMA);
		//֪ͨonActivityResult������Ӧ�ı仯
		break;
   case R.id.up_mapstore:
	    Intent ib=new Intent();
	    ib.setType("image/*");
	    ib.setAction(Intent.ACTION_GET_CONTENT);
		UpActivity.this.startActivityForResult(ib, PHOTO_REQUEST_GALLERY);
		break;
   case R.id.up_close:	   
	   finish();
   	   overridePendingTransition(0,R.anim.anim_down_to_up);	   	   
		break;
   case R.id.showimg_cancell:
	     show2.setVisibility(View.GONE);
	     show1.setVisibility(View.VISIBLE);
	     show2.setBackgroundColor(R.color.hui);
		break;
     case R.id.showimg_show:
    	   Map <String,Object>parame=new HashMap<String,Object>();
    	   parame.put("tasktype", Constants.UPTOWEB);
    	   parame.put("tags", showimg_tag.getText().toString().trim());
    	   parame.put("summary", showimg_des.getText().toString().trim());
    	   
     	   parame.put("file", tempFile);
    	   
    	   System.out.println("----�ϴ�");
    	   taskhelp.getWebJson(Constants.URL_UPTOWEB,parame);
    	   loaddialog.show();
    	  // finish();
     	  // overridePendingTransition(0,R.anim.anim_down_to_up);	
		break;
	default:
		break;
	}	
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	
	}
	

}
