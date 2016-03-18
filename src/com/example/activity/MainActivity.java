package com.example.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.example.activity.LoginActivity.LoginListener;
import com.example.adapter.HomePictureAdapter;
import com.example.bitmapfun.util.ImageFetcher;
import com.example.db.LoginData;
import com.example.imgweb.R;
import com.example.model.UserInfo;
import com.example.tool.Constants;
import com.example.tool.JsonTool;
import com.example.tool.MenuRigthAnimations;
import com.example.tool.TaskTool;
import com.example.view.CircularImage;
import com.example.view.DragLayout;
import com.example.view.DragLayout.DragListener;
import com.example.view.HomeImgScroll;
import com.example.view.LoadDialog;
import com.example.waterfallview.XListView;
import com.example.waterfallview.XListView.IXListViewListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
/*IXListViewListenerʵ��ˢ�ºͼ��ظ���ӿ�
 * OnTouchListener��OnClickListener
 * DragListenerʵ����߲˵��򿪽ӿ�
 */
public class MainActivity extends FragmentActivity implements LoginListener,IXListViewListener,OnTouchListener, OnClickListener,DragListener{
private String TAG="MainActivity---->>";

private static int i=0; 


//��߲˵�ģ��
private DragLayout mDragLayout;
private LinearLayout menu_no_login;//��߲˵�û�е���ʱ��ʾ�Ĳ���
private Button login,register;//��߲˵��ĵ����ע�ᰴť
private ListView menuListView;// ��߲˵��б�û����Visibility��Gone
private ImageButton menuSettingBtn;// ��߲˵�������ť
private ImageButton up,my,message,search;//��ť�˵�����Ĳ˵���
private RelativeLayout composerButtonsWrapper;//������ť���
private ImageView composerButtonsShowHideButtonIcon;//������ťͼ��
private RelativeLayout composerButtonsShowHideButton;//������ť
private boolean areButtonsShowing=false;
private boolean Isopen=false;//�ж�������˵��Ƿ�չ��
private boolean Isopenleft=false;//�ж����������߲˵��Ƿ�չ��
private boolean IsLogin=false;//�Ƿ��¼
private boolean AutoLogin=false;//�Զ���¼
private CircularImage menu_user_ico;//��߲˵����û�ͼ��
private TextView menu_user_name;//��߲˵��û�������

//�ٲ���

private XListView mAdapterView = null;//�ٲ����ؼ�
private ImageFetcher mImageFetcher;//�����ٲ��������ͼƬ
private HomePictureAdapter homePictureAdapter=null;
private TaskTool taskhelp;
private Map<String,Object>taskparames=new HashMap<String, Object>();
private int cishu = 1;

//ImgScroll�ؼ�
private	HomeImgScroll myPager; // ͼƬ�����ؼ�
private	LinearLayout ovalLayout; // �·���СԲ��
private List<View> listImgs; // Ҫ������ ͼƬ��

//�Զ�����ȼ��ضԻ���
private LoadDialog loadDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		i=0;//��ʼ��ˢ��Ч��
		MenuRigthAnimations.initOffset(MainActivity.this);//�������Ĵ��ݵ�Animation��������ť
		initview();
		if(!Constants.IsNetWorkAvaliable(getApplicationContext()))
			TaskApplication.showTip(R.drawable.tips_error, "û������!!!",this);
		else{ 
			TaskApplication.showTip(R.drawable.tips_smile, "�Ѿ�����",this);
			
			}
		//Log.e(TAG," ���ݣ�"+LoginData.getUserCookies());
				
	}
     public  void initview(){
    	 menu_no_login=(LinearLayout) findViewById(R.id.menu_login);
    	 login=(Button) findViewById(R.id.button_login);
    	 register=(Button) findViewById(R.id.button_register);
    	 menuListView=(ListView) findViewById(R.id.menu_listview);
    	 menu_user_name=(TextView) findViewById(R.id.menu_user_name);
    	 menu_user_ico= (CircularImage) findViewById(R.id.menu_user_ico); 
         menuSettingBtn = (ImageButton) findViewById(R.id.menu_imgbtn);
   	     mDragLayout = (DragLayout) findViewById(R.id.dl);
   	     composerButtonsWrapper=(RelativeLayout) findViewById(R.id.composer_buttons_wrapper);
    	 composerButtonsShowHideButton=(RelativeLayout) findViewById(R.id.composer_buttons_show_hide_button);
    	 composerButtonsShowHideButtonIcon=(ImageView) findViewById(R.id.composer_buttons_show_hide_button_icon);
   	      
   	     up=(ImageButton)findViewById(R.id.composer_button_camera);//�ϴ���Ƭ��ť
   	     message=(ImageButton)findViewById(R.id.composer_button_message);//��Ϣ��ť
   	     my=(ImageButton)findViewById(R.id.composer_button_my);//�鿴�ҵİ�ť
     	search=(ImageButton)findViewById(R.id.composer_button_search);//������ť
     	
     	mAdapterView=(XListView) findViewById(R.id.list);
     	mAdapterView.setPullLoadEnable(true);
     	mAdapterView.setPullRefreshEnable(true);
     	mAdapterView.setXListViewListener(this);
     	mImageFetcher=new ImageFetcher(this, 200);
     	loadDialog=new LoadDialog(this);//��ʼ���Ի���
     	mAdapterView.setLoadDialog(loadDialog);//�󶨶Ի���
     	homePictureAdapter=new HomePictureAdapter(this, mImageFetcher);
     	taskhelp=new TaskTool(handler);
     	
     	View v=LayoutInflater.from(this).inflate(R.layout.home_sorollimg, null);
     	myPager=(HomeImgScroll) v.findViewById(R.id.imgweb_home_imgscroll);
        ovalLayout=(LinearLayout) v.findViewById(R.id.vb);
     	initSorollview();
     	myPager.start(this, listImgs, 3000, ovalLayout,
				R.layout.ad_bottom_item, R.id.ad_item_v,
				R.drawable.dot_focused, R.drawable.dot_normal);
     	mAdapterView.addHeaderView(v);     	
     	
     	
     	up.setOnClickListener(this);
     	message.setOnClickListener(this);
        my.setOnClickListener(this);
     	search.setOnClickListener(this);
    	
    	 mDragLayout.setDragListener(this);
    	 login.setOnClickListener(this);
    	 register.setOnClickListener(this);
    	 menuSettingBtn.setOnClickListener(this);
    	 composerButtonsShowHideButton.setOnClickListener(this);
    	 composerButtonsWrapper.setOnTouchListener(this);
    	 
    	 LoginActivity.setLoginListener(this);
     }
     public void initSorollview(){   	 
    	 /*
 		 * imgScroll��ʼ��
 		 */
 		listImgs = new ArrayList<View>();
 		int[] imageResId = new int[] { R.drawable.banner1, R.drawable.banner2,
 				R.drawable.banner3 };
 		for (int i = 0; i < imageResId.length; i++) {
 			ImageView imageView = new ImageView(this);
 			imageView.setOnClickListener(new OnClickListener() {
 				public void onClick(View v) {
 					// ��������¼�
 					Log.w(TAG, "�����" + myPager.getCurIndex());
 				}
 			});
 			imageView.setImageResource(imageResId[i]);
 			imageView.setScaleType(ScaleType.CENTER_CROP);
 			listImgs.add(imageView);
 		}

     }    
     public void initMenuItem(){
    	 List<HashMap<String,Object>>data=new ArrayList<HashMap<String,Object>>();
    	 HashMap<String, Object>map1=new HashMap<String, Object>();
    	 map1.put("text", "�ҵ��ϴ�");
    	 map1.put("img", R.drawable.menu_my_up);
    	 data.add(map1);
    	 HashMap<String, Object>map2=new HashMap<String, Object>();
    	 map2.put("text", "�ҵ��ղ�");
    	 map2.put("img", R.drawable.menu_my_having);
    	 data.add(map2);
    	 HashMap<String, Object>map3=new HashMap<String, Object>();
    	 map3.put("text", "��������");
    	 map3.put("img", R.drawable.menu_about_us);
    	 data.add(map3);
    	 HashMap<String, Object>map4=new HashMap<String, Object>();
    	 map4.put("text", "ע����¼");
    	 map4.put("img", R.drawable.menu_out);
    	 data.add(map4);
    	 SimpleAdapter simadapter=new SimpleAdapter(MainActivity.this, data,R.layout.left_menu_item, new String[]{"text","img"},
    			 new int[]{R.id.menu_item_text,R.id.menu_item_ico});
    	 menuListView.setAdapter(simadapter);
    	 menuListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					{Intent in=new Intent(MainActivity.this,MyHavingActivity.class);
					 in.putExtra("pagm", 0); //�ҵ��ϴ�
					startActivity(in);}
					break;
                case 1:
					{Intent in=new Intent(MainActivity.this,MyHavingActivity.class);
					 in.putExtra("pagm", 1);//�ҵ��ղ�
					startActivity(in);}
					break;
                case 2:
                {
                	Intent in =new Intent(MainActivity.this, AboutUsActivity.class);
                	startActivity(in);
                }
                	break;
                case 3:
                	
                	LoginData.removeUserCookies(); 
                	LoginData.removeClient();
                	taskparames.put("tasktype", Constants.LOGIN_OUT);
        			taskhelp.getWebJson(Constants.URL_LOGIN_OUT, taskparames);
                	IsLogin(false);
                	AutoLogin=false;
                	break;
				default:
					break;
				}
				
			}
		});
     }
	@Override
	 public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
        case R.id.menu_imgbtn:
        	mDragLayout.open();//չʾ��߲˵�
        	Isopen=true;
        	break;
        case R.id.composer_buttons_show_hide_button:
        	
        	onClickView(v,false);//չ���˵�
        	Isopen=true;
        	break;
        case R.id.button_login:
        {//׼������������
        	Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        	startActivity(intent);
        	overridePendingTransition(R.anim.anim_login, 0);
        } break;
        case R.id.button_register:
        {//׼������ע�����
        	Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
        	startActivity(intent);
        	overridePendingTransition(R.anim.anim_login, 0);
        }break;
        case R.id.composer_button_camera:
        {
        //�����ϴ���Ƭ�Ľ���
        	//�ж��Ƿ��Ѿ����룬�����˲��ܽ���
             if(IsLogin){
        	Intent intent=new Intent(MainActivity.this,UpActivity.class);
        	startActivity(intent);
        	overridePendingTransition(R.anim.anim_up_to_down, 0);
        	onClickView(v, true);
        	Isopen=false;}
             else{
            	 TaskApplication.showTip(R.drawable.tips_error, "���ȵ���", this);
             }
        	break;
        }
        	
        case R.id.composer_button_message:
        	  if(IsLogin){
              	Intent intent=new Intent(MainActivity.this,MessageActivity.class);
              	startActivity(intent);
              	onClickView(v, true);
              	Isopen=false;}
                   else{
                  	 TaskApplication.showTip(R.drawable.tips_error, "���ȵ���", this);
                   }
        	break;
        case R.id.composer_button_my:
        	mDragLayout.open();//չʾ��߲˵�
        	onClickView(v, true);//�ջ�ɢ���Ĳ˵�
        	Isopen=false;
        	break;
        case R.id.composer_button_search:
        	
        	break;
		default:
			break;
		}
	}
	/*
	 * ����IsOnlyClose��������չ�������ջز˵���ť
	 * fasleչ��
	 * true�ջ�
	 */
	 public void onClickView(View v, boolean isOnlyClose) {
	        if (isOnlyClose) {
	            if (areButtonsShowing) {
	                MenuRigthAnimations.startAnimationsOut(composerButtonsWrapper, 300);
	                composerButtonsShowHideButtonIcon.startAnimation(MenuRigthAnimations
	                        .getRotateAnimation(-315, 0, 300));
	                areButtonsShowing = !areButtonsShowing;
	            }

	        } else {

	            if (!areButtonsShowing) {
	                MenuRigthAnimations.startAnimationsIn(composerButtonsWrapper, 300);
	                composerButtonsShowHideButtonIcon.startAnimation(MenuRigthAnimations
	                        .getRotateAnimation(0, -315, 300));
	            } else {
	                MenuRigthAnimations.startAnimationsOut(composerButtonsWrapper, 300);
	                composerButtonsShowHideButtonIcon.startAnimation(MenuRigthAnimations
	                        .getRotateAnimation(-315, 0, 300));
	            }
	            areButtonsShowing = !areButtonsShowing;
	        }

	    }

	@Override
	public void onOpen() {

		myPager.stopTimer();
		Isopenleft=true;
	}
	@Override
	public void onClose() {
		myPager.startTimer();
		// TODO Auto-generated method stub
		Isopenleft=false;
	}
	@Override
	public void onDrag(float percent) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		onClickView(v,true);
		Isopen=false;
		return false;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(event.getAction()==KeyEvent.ACTION_DOWN&&keyCode==KeyEvent.KEYCODE_BACK)
		{
			//�ж�������Ĳ˵���ť�ǲ���չ����������Ӧ�Ķ���
			if(Isopen) {onClickView(null, true);Isopen=false;	return false;}
			//�ж���ߵĲ˵��ǲ���չ����������Ӧ�Ķ���
			if(Isopenleft) {mDragLayout.close(); Isopenleft=false;return false;	}
			
			else return super.onKeyDown(keyCode, event);
		}
				
		return super.onKeyDown(keyCode, event);//�����onstop��������Activity
	}
   
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.w(TAG, "pause");
		myPager.stopTimer();//����ͼƬֹͣ
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.w(TAG, "stop");
		myPager.stopTimer();//����ͼƬֹͣ
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.w(TAG, "start");
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(i==0){
		mImageFetcher.setExitTasksEarly(false);
		//Log.e(TAG,homePictureAdapter+"---" );
        mAdapterView.setAdapter(homePictureAdapter);
        taskparames.put("cishu",cishu+"");
		taskparames.put("tasktype", Constants.FIRST_SHOW_IMG);
		taskhelp.getWebJson(Constants.GET_PICTURE, taskparames);		
		i++;}
		//Log.w(TAG, "onResume");
	}
	@Override
	public void onRefresh() {
		cishu=1;
		homePictureAdapter.clearData();
		taskparames.put("cishu",cishu+"");
		taskparames.put("tasktype", Constants.UP_IMG_FRESH);
		taskhelp.getWebJson(Constants.GET_PICTURE, taskparames);
		
	}
	@Override
	public void onLoadMore() {
		cishu++;
		taskparames.put("cishu",cishu+"");
		taskparames.put("tasktype", Constants.UP_IMG_LOAD);
		taskhelp.getWebJson(Constants.GET_PICTURE, taskparames);	
		
	}
private Handler handler=new Handler(){

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case Constants.UP_IMG_FRESH:
			//Log.v(TAG,"ˢ��:"+msg.obj);
			homePictureAdapter.addItemTop(JsonTool.getList((String)msg.obj));			
			homePictureAdapter.notifyDataSetChanged();
            mAdapterView.stopRefresh();
			break;
		case Constants.UP_IMG_LOAD:
			//Log.v(TAG,"���ظ���:"+msg.obj);
			 mAdapterView.stopLoadMore();
			 
			 homePictureAdapter.addItemLast(JsonTool.getList((String)msg.obj));
			 homePictureAdapter.notifyDataSetChanged();
				
			break;
		case Constants.GET_USERINFO:
			//�����ȡ�û���Ϣ������
			doGetUserInfo(JsonTool.getCurrent((String) msg.obj));
			break;
		case Constants.FIRST_SHOW_IMG:
			mAdapterView.stopLoadMore();
			 
			 homePictureAdapter.addItemLast(JsonTool.getList((String)msg.obj));
			 homePictureAdapter.notifyDataSetChanged();
			// Log.e(TAG, "��ʼ��¼������");
			 taskparames.put("tasktype", Constants.AUTO_LOGIN);
			 taskhelp.getWebJson(Constants.URL_AUTO_LOGIN, taskparames);
			break;
		case Constants.AUTO_LOGIN:
			if(JsonTool.getAutoLogin((String) msg.obj)){
				//IsLogin=true;
			//	 Log.e(TAG, "�Զ���¼�ɹ�");
				IsLogin(true);
			}
			//else{
			//	Log.e(TAG, "�Զ���¼ʧ��");
			//}
			break;
		case Constants.LOGIN_OUT:
			if(JsonTool.getAutoLogin((String) msg.obj)){
				TaskApplication.showTip(R.drawable.tips_smile, "���˳�", MainActivity.this);
			}
		default:
			break;
		}
		
		
	}
};
/*
 * ���ص�¼״̬
 */
public boolean getLoginInfo(){
	return IsLogin;
}
@Override
public void IsLogin(boolean is) {
     IsLogin=is;
	if(is){
		//1���������
		//2����ȡͷ�񣬺��û���Ϣ
		//�Զ�����Ϊ�Զ���¼
		AutoLogin=true;
		menu_no_login.setVisibility(View.GONE);
		
		initMenuItem();//��ʼ���ҵ�ҳ��Ĺ��ܲ˵�
		menuListView.setVisibility(View.VISIBLE);
		Map <String,Object>parame=new HashMap<String,Object>();
		parame.put("tasktype", Constants.GET_USERINFO);
		taskhelp.getWebJson(Constants.URL_GETUSERINFO, parame);
	}
	else {
		menu_no_login.setVisibility(View.VISIBLE);
		menuListView.setVisibility(View.GONE);
		menu_user_name.setText("���¼");
		menu_user_name.setTextColor(Color.BLACK);
		menu_user_ico.setImageResource(R.drawable.img10);
		//Log.w(TAG, "����ʧ��");
	}

}
private void doGetUserInfo(UserInfo userinfo){
	
	 if(userinfo!=null){
		if(userinfo.isUserstatus()){
			//�����û���Ϣ ���Ľ�
			menu_user_name.setText(userinfo.getUsername());
			menu_user_name.setTextColor(Color.RED);
			mImageFetcher.loadImage(userinfo.getUser_img_url(), menu_user_ico);
		}else{
			Log.w(TAG, "ͷ��");
		}
	 }else TaskApplication.showTip(R.drawable.tips_error, "�쳣����", this);

	 
}



	
}
