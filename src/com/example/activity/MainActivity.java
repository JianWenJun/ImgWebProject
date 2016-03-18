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
/*IXListViewListener实现刷新和加载更多接口
 * OnTouchListener，OnClickListener
 * DragListener实现左边菜单打开接口
 */
public class MainActivity extends FragmentActivity implements LoginListener,IXListViewListener,OnTouchListener, OnClickListener,DragListener{
private String TAG="MainActivity---->>";

private static int i=0; 


//左边菜单模块
private DragLayout mDragLayout;
private LinearLayout menu_no_login;//左边菜单没有登入时显示的布局
private Button login,register;//左边菜单的登入和注册按钮
private ListView menuListView;// 左边菜单列表没登入Visibility是Gone
private ImageButton menuSettingBtn;// 左边菜单呼出按钮
private ImageButton up,my,message,search;//旋钮菜单里面的菜单项
private RelativeLayout composerButtonsWrapper;//悬浮按钮组合
private ImageView composerButtonsShowHideButtonIcon;//悬浮按钮图标
private RelativeLayout composerButtonsShowHideButton;//悬浮按钮
private boolean areButtonsShowing=false;
private boolean Isopen=false;//判断主界面菜单是否展开
private boolean Isopenleft=false;//判断主界面的左边菜单是否展开
private boolean IsLogin=false;//是否登录
private boolean AutoLogin=false;//自动登录
private CircularImage menu_user_ico;//左边菜单的用户图标
private TextView menu_user_name;//左边菜单用户的名字

//瀑布流

private XListView mAdapterView = null;//瀑布流控件
private ImageFetcher mImageFetcher;//加载瀑布流里面的图片
private HomePictureAdapter homePictureAdapter=null;
private TaskTool taskhelp;
private Map<String,Object>taskparames=new HashMap<String, Object>();
private int cishu = 1;

//ImgScroll控件
private	HomeImgScroll myPager; // 图片滚动控件
private	LinearLayout ovalLayout; // 下方的小圆点
private List<View> listImgs; // 要滚动的 图片组

//自定义进度加载对话框
private LoadDialog loadDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		i=0;//初始化刷新效果
		MenuRigthAnimations.initOffset(MainActivity.this);//将上下文传递到Animation，悬浮按钮
		initview();
		if(!Constants.IsNetWorkAvaliable(getApplicationContext()))
			TaskApplication.showTip(R.drawable.tips_error, "没有联网!!!",this);
		else{ 
			TaskApplication.showTip(R.drawable.tips_smile, "已经联网",this);
			
			}
		//Log.e(TAG," 数据："+LoginData.getUserCookies());
				
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
   	      
   	     up=(ImageButton)findViewById(R.id.composer_button_camera);//上传照片按钮
   	     message=(ImageButton)findViewById(R.id.composer_button_message);//消息按钮
   	     my=(ImageButton)findViewById(R.id.composer_button_my);//查看我的按钮
     	search=(ImageButton)findViewById(R.id.composer_button_search);//搜索按钮
     	
     	mAdapterView=(XListView) findViewById(R.id.list);
     	mAdapterView.setPullLoadEnable(true);
     	mAdapterView.setPullRefreshEnable(true);
     	mAdapterView.setXListViewListener(this);
     	mImageFetcher=new ImageFetcher(this, 200);
     	loadDialog=new LoadDialog(this);//初始化对话框
     	mAdapterView.setLoadDialog(loadDialog);//绑定对话框
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
 		 * imgScroll初始化
 		 */
 		listImgs = new ArrayList<View>();
 		int[] imageResId = new int[] { R.drawable.banner1, R.drawable.banner2,
 				R.drawable.banner3 };
 		for (int i = 0; i < imageResId.length; i++) {
 			ImageView imageView = new ImageView(this);
 			imageView.setOnClickListener(new OnClickListener() {
 				public void onClick(View v) {
 					// 点击监听事件
 					Log.w(TAG, "点击了" + myPager.getCurIndex());
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
    	 map1.put("text", "我的上传");
    	 map1.put("img", R.drawable.menu_my_up);
    	 data.add(map1);
    	 HashMap<String, Object>map2=new HashMap<String, Object>();
    	 map2.put("text", "我的收藏");
    	 map2.put("img", R.drawable.menu_my_having);
    	 data.add(map2);
    	 HashMap<String, Object>map3=new HashMap<String, Object>();
    	 map3.put("text", "关于我们");
    	 map3.put("img", R.drawable.menu_about_us);
    	 data.add(map3);
    	 HashMap<String, Object>map4=new HashMap<String, Object>();
    	 map4.put("text", "注销登录");
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
					 in.putExtra("pagm", 0); //我的上传
					startActivity(in);}
					break;
                case 1:
					{Intent in=new Intent(MainActivity.this,MyHavingActivity.class);
					 in.putExtra("pagm", 1);//我的收藏
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
        	mDragLayout.open();//展示侧边菜单
        	Isopen=true;
        	break;
        case R.id.composer_buttons_show_hide_button:
        	
        	onClickView(v,false);//展开菜单
        	Isopen=true;
        	break;
        case R.id.button_login:
        {//准备进入登入界面
        	Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        	startActivity(intent);
        	overridePendingTransition(R.anim.anim_login, 0);
        } break;
        case R.id.button_register:
        {//准备进入注册界面
        	Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
        	startActivity(intent);
        	overridePendingTransition(R.anim.anim_login, 0);
        }break;
        case R.id.composer_button_camera:
        {
        //进入上传照片的界面
        	//判断是否已经登入，登入了才能进入
             if(IsLogin){
        	Intent intent=new Intent(MainActivity.this,UpActivity.class);
        	startActivity(intent);
        	overridePendingTransition(R.anim.anim_up_to_down, 0);
        	onClickView(v, true);
        	Isopen=false;}
             else{
            	 TaskApplication.showTip(R.drawable.tips_error, "请先登入", this);
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
                  	 TaskApplication.showTip(R.drawable.tips_error, "请先登入", this);
                   }
        	break;
        case R.id.composer_button_my:
        	mDragLayout.open();//展示侧边菜单
        	onClickView(v, true);//收回散开的菜单
        	Isopen=false;
        	break;
        case R.id.composer_button_search:
        	
        	break;
		default:
			break;
		}
	}
	/*
	 * 根据IsOnlyClose来处理是展开还是收回菜单按钮
	 * fasle展开
	 * true收回
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
			//判断主界面的菜单按钮是不是展开，做出相应的动作
			if(Isopen) {onClickView(null, true);Isopen=false;	return false;}
			//判断左边的菜单是不是展开，做出相应的动作
			if(Isopenleft) {mDragLayout.close(); Isopenleft=false;return false;	}
			
			else return super.onKeyDown(keyCode, event);
		}
				
		return super.onKeyDown(keyCode, event);//会调用onstop方法销毁Activity
	}
   
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.w(TAG, "pause");
		myPager.stopTimer();//滚动图片停止
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.w(TAG, "stop");
		myPager.stopTimer();//滚动图片停止
		
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
			//Log.v(TAG,"刷新:"+msg.obj);
			homePictureAdapter.addItemTop(JsonTool.getList((String)msg.obj));			
			homePictureAdapter.notifyDataSetChanged();
            mAdapterView.stopRefresh();
			break;
		case Constants.UP_IMG_LOAD:
			//Log.v(TAG,"加载更多:"+msg.obj);
			 mAdapterView.stopLoadMore();
			 
			 homePictureAdapter.addItemLast(JsonTool.getList((String)msg.obj));
			 homePictureAdapter.notifyDataSetChanged();
				
			break;
		case Constants.GET_USERINFO:
			//处理获取用户信息的资料
			doGetUserInfo(JsonTool.getCurrent((String) msg.obj));
			break;
		case Constants.FIRST_SHOW_IMG:
			mAdapterView.stopLoadMore();
			 
			 homePictureAdapter.addItemLast(JsonTool.getList((String)msg.obj));
			 homePictureAdapter.notifyDataSetChanged();
			// Log.e(TAG, "开始登录。。。");
			 taskparames.put("tasktype", Constants.AUTO_LOGIN);
			 taskhelp.getWebJson(Constants.URL_AUTO_LOGIN, taskparames);
			break;
		case Constants.AUTO_LOGIN:
			if(JsonTool.getAutoLogin((String) msg.obj)){
				//IsLogin=true;
			//	 Log.e(TAG, "自动登录成功");
				IsLogin(true);
			}
			//else{
			//	Log.e(TAG, "自动登录失败");
			//}
			break;
		case Constants.LOGIN_OUT:
			if(JsonTool.getAutoLogin((String) msg.obj)){
				TaskApplication.showTip(R.drawable.tips_smile, "已退出", MainActivity.this);
			}
		default:
			break;
		}
		
		
	}
};
/*
 * 返回登录状态
 */
public boolean getLoginInfo(){
	return IsLogin;
}
@Override
public void IsLogin(boolean is) {
     IsLogin=is;
	if(is){
		//1，界面更换
		//2，获取头像，和用户信息
		//自动设置为自动登录
		AutoLogin=true;
		menu_no_login.setVisibility(View.GONE);
		
		initMenuItem();//初始化我的页面的功能菜单
		menuListView.setVisibility(View.VISIBLE);
		Map <String,Object>parame=new HashMap<String,Object>();
		parame.put("tasktype", Constants.GET_USERINFO);
		taskhelp.getWebJson(Constants.URL_GETUSERINFO, parame);
	}
	else {
		menu_no_login.setVisibility(View.VISIBLE);
		menuListView.setVisibility(View.GONE);
		menu_user_name.setText("请登录");
		menu_user_name.setTextColor(Color.BLACK);
		menu_user_ico.setImageResource(R.drawable.img10);
		//Log.w(TAG, "登入失败");
	}

}
private void doGetUserInfo(UserInfo userinfo){
	
	 if(userinfo!=null){
		if(userinfo.isUserstatus()){
			//设置用户信息 待改进
			menu_user_name.setText(userinfo.getUsername());
			menu_user_name.setTextColor(Color.RED);
			mImageFetcher.loadImage(userinfo.getUser_img_url(), menu_user_ico);
		}else{
			Log.w(TAG, "头像");
		}
	 }else TaskApplication.showTip(R.drawable.tips_error, "异常错误", this);

	 
}



	
}
