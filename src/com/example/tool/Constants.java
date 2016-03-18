package com.example.tool;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
/**
 * 
 * @author JWJ1
 *  @category 接口类
 */
public class Constants {
	//注册时提交用户的username,password,verify,email的链接
	public  static String URL_REGISTER="http://online.cumt.edu.cn/irides/Account/Register";
	
	//登入时提交验证码，用户名和密码的链接
	public  static String URL_LOGIN="http://online.cumt.edu.cn/irides/Account/Login";
	
	//获取验证码图片的链接
	public final static String URL_CONFIGCODE="http://online.cumt.edu.cn/irides/Account/ValidateCode";
	
	//主应用的path
	public  static String URL_PLTH="http://online.cumt.edu.cn/irides/";
	
	//获取当前用户信息
	public  static String URL_GETUSERINFO="http://online.cumt.edu.cn/irides/CommonJSON/GetCurrentUser";
	
	//首页批量获取图片的链接//cishu   int   //请求次数,用于分批获取图片数据,为1时获得1~20条数据,2时21~22
	public  static String GET_PICTURE="http://online.cumt.edu.cn/irides/Home/GetPictureList";
	
	//获取某个照片的详细信息接口//pId  int //图片Id
	
	public static String PICTURE_DETAIL="http://online.cumt.edu.cn/irides/CommonJSON/LoadPictureDetail";
	
	//我的收藏图片  //参数：loadCount  int 	//加载次数,从0开始算loadSize    ing  //加载数量
	public static String URL_MY_STORE="http://online.cumt.edu.cn/irides/UserIndex/LoadCollectionPicture";
	
	//我的上传//参数：loadCount  int 	//加载次数,从0开始算 loadSize    ing  //加载数量
	public static String URL_MY_UP="http://online.cumt.edu.cn/irides/UserIndex/LoadUserPicture";
	
	//上传图片的接口
	/*tags  string  用逗号隔开的标签,不需要检查多个标签是否重复后端已经处理
	summary string 图片描述*/
	public static String URL_UPTOWEB="http://online.cumt.edu.cn/irides/CommonJSON/UploadSinglePicture";
	//删除上传的图片
	public static String URL_DELETE_UPTOWEB="http://online.cumt.edu.cn/irides/UserIndex/DeletePicture";
	//收藏图片 
/*	pId int 图片Id 
	isCollect	bool  描述当前图片是否被收藏,例:在图片已经收藏的情况下点击红心按钮,该值为true*/
	public static String URL_WEBSTORE="http://online.cumt.edu.cn/irides/CommonJSON/CollectPicture";
	
	//评论图片
	public static String URL_WEBCOMMNET="http://online.cumt.edu.cn/irides/CommonJSON/SubmitComment";
	
	//删除评论
	public static String URL_UNWEBCOMMNET="http://online.cumt.edu.cn/irides/CommonJSON/DeleteComment";	
	
	//文件保持的本地路径
	public static String imgs=Environment.getExternalStorageDirectory().getPath()+"/imgweb/myup";
	
	
	//手机自带登入
	public static String URL_AUTO_LOGIN="http://online.cumt.edu.cn/irides/Account/MobileCookieLogin";
	
	//获取消息
	public  static String URL_GET_MESSAGE="http://online.cumt.edu.cn/irides/UserIndex/LoadUserMsg";
	
	//退出
	public static String URL_LOGIN_OUT="http://online.cumt.edu.cn/irides/Account/MobileLogOut";
	public final static int LOGIN=1;//登录
	public final static int AUTO_LOGIN=30;//自动登录
    public final static int LOGIN_CODE=6;//获取验证码
    public final static int REGISTER=2;//注册
    public final static int UP_IMG_FRESH=3;
    public final static int UP_IMG_LOAD=9;
    public final static int GET_IMG_DETAIL=4;//获取图片详情
    public final static int GET_ALL_IMG=5;  //h获取所有图片
    public final static int GET_USERINFO=10;
    public final static int STORE_IMG=12;
    public final static int UP_IMG=13;
    public final static int UPTOWEB=14;
    public final static int WEBSTORE=15;
    public final static int MY_UP_FRESH=16;
    public final static int MY_UP_LOAD=21;
    public final static int MY_STORE_FRESH=17;
    public final static int MY_STORE_LOAD=20;
    public final static int STOREING=18;
    public final static int UNSTOREING=19;
    public final static int UNSTOREING_MY=22;//实时更新取消收藏的图片的界面
    public final static int DELETE_UPTOWEB=23;
    public final static int COMMENT_SUBMIT=24;
    public final static int COMMENT_SUBMIT_FRESH=26; 
    public final static int COMMENT_DELETE=25;

	public static final int UNSTOREING_MY_FRESH = 27;//刷新重新获取我的收藏

	public static final int DELETE_UPTOWEB_FRESH = 28;//刷新重新获取我的上传
	
	public static final int MESSAGE_GET=29;
	public static final int FIRST_SHOW_IMG=31;//应用第一次加载图片
	public static final int LOGIN_OUT=32;
    /*
     * 判断手机有没有sd卡
     */
    public static boolean IsHaveSd(){
    	if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
    		return true;
    	}else return false;
    }
    /*
     * 判断手机有没有联网
     */
    public static boolean IsNetWorkAvaliable(Context c){
    	ConnectivityManager connectivitymanager=(ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
    	if(connectivitymanager==null){
    		return false;
    	}else{
    		NetworkInfo[]networkinfos=connectivitymanager.getAllNetworkInfo();
    		
    		for(int i=0;i<networkinfos.length;i++){
    			if(networkinfos[i].isConnectedOrConnecting())
    				return true;
    		}
    	}
		return false;
    	
    }
    //时间格式
    @SuppressLint("SimpleDateFormat")
	public static String formattime(String str){  
    	 int start=0;
         int end=0;
    	 start=str.indexOf("(");
    	 end=str.indexOf(")");
    	 if(start!=0&&end!=0&&start<end&&end<str.length())
    		 str=str.substring(start+1, end);
         if(!str.equals("")){
        long dateLong=Long.parseLong(str);
        Date date = new Date(dateLong);
    	SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		return sdformat.format(date);
         }
         return "";
    }
   
}
