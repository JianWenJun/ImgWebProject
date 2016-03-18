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
 *  @category �ӿ���
 */
public class Constants {
	//ע��ʱ�ύ�û���username,password,verify,email������
	public  static String URL_REGISTER="http://online.cumt.edu.cn/irides/Account/Register";
	
	//����ʱ�ύ��֤�룬�û��������������
	public  static String URL_LOGIN="http://online.cumt.edu.cn/irides/Account/Login";
	
	//��ȡ��֤��ͼƬ������
	public final static String URL_CONFIGCODE="http://online.cumt.edu.cn/irides/Account/ValidateCode";
	
	//��Ӧ�õ�path
	public  static String URL_PLTH="http://online.cumt.edu.cn/irides/";
	
	//��ȡ��ǰ�û���Ϣ
	public  static String URL_GETUSERINFO="http://online.cumt.edu.cn/irides/CommonJSON/GetCurrentUser";
	
	//��ҳ������ȡͼƬ������//cishu   int   //�������,���ڷ�����ȡͼƬ����,Ϊ1ʱ���1~20������,2ʱ21~22
	public  static String GET_PICTURE="http://online.cumt.edu.cn/irides/Home/GetPictureList";
	
	//��ȡĳ����Ƭ����ϸ��Ϣ�ӿ�//pId  int //ͼƬId
	
	public static String PICTURE_DETAIL="http://online.cumt.edu.cn/irides/CommonJSON/LoadPictureDetail";
	
	//�ҵ��ղ�ͼƬ  //������loadCount  int 	//���ش���,��0��ʼ��loadSize    ing  //��������
	public static String URL_MY_STORE="http://online.cumt.edu.cn/irides/UserIndex/LoadCollectionPicture";
	
	//�ҵ��ϴ�//������loadCount  int 	//���ش���,��0��ʼ�� loadSize    ing  //��������
	public static String URL_MY_UP="http://online.cumt.edu.cn/irides/UserIndex/LoadUserPicture";
	
	//�ϴ�ͼƬ�Ľӿ�
	/*tags  string  �ö��Ÿ����ı�ǩ,����Ҫ�������ǩ�Ƿ��ظ�����Ѿ�����
	summary string ͼƬ����*/
	public static String URL_UPTOWEB="http://online.cumt.edu.cn/irides/CommonJSON/UploadSinglePicture";
	//ɾ���ϴ���ͼƬ
	public static String URL_DELETE_UPTOWEB="http://online.cumt.edu.cn/irides/UserIndex/DeletePicture";
	//�ղ�ͼƬ 
/*	pId int ͼƬId 
	isCollect	bool  ������ǰͼƬ�Ƿ��ղ�,��:��ͼƬ�Ѿ��ղص�����µ�����İ�ť,��ֵΪtrue*/
	public static String URL_WEBSTORE="http://online.cumt.edu.cn/irides/CommonJSON/CollectPicture";
	
	//����ͼƬ
	public static String URL_WEBCOMMNET="http://online.cumt.edu.cn/irides/CommonJSON/SubmitComment";
	
	//ɾ������
	public static String URL_UNWEBCOMMNET="http://online.cumt.edu.cn/irides/CommonJSON/DeleteComment";	
	
	//�ļ����ֵı���·��
	public static String imgs=Environment.getExternalStorageDirectory().getPath()+"/imgweb/myup";
	
	
	//�ֻ��Դ�����
	public static String URL_AUTO_LOGIN="http://online.cumt.edu.cn/irides/Account/MobileCookieLogin";
	
	//��ȡ��Ϣ
	public  static String URL_GET_MESSAGE="http://online.cumt.edu.cn/irides/UserIndex/LoadUserMsg";
	
	//�˳�
	public static String URL_LOGIN_OUT="http://online.cumt.edu.cn/irides/Account/MobileLogOut";
	public final static int LOGIN=1;//��¼
	public final static int AUTO_LOGIN=30;//�Զ���¼
    public final static int LOGIN_CODE=6;//��ȡ��֤��
    public final static int REGISTER=2;//ע��
    public final static int UP_IMG_FRESH=3;
    public final static int UP_IMG_LOAD=9;
    public final static int GET_IMG_DETAIL=4;//��ȡͼƬ����
    public final static int GET_ALL_IMG=5;  //h��ȡ����ͼƬ
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
    public final static int UNSTOREING_MY=22;//ʵʱ����ȡ���ղص�ͼƬ�Ľ���
    public final static int DELETE_UPTOWEB=23;
    public final static int COMMENT_SUBMIT=24;
    public final static int COMMENT_SUBMIT_FRESH=26; 
    public final static int COMMENT_DELETE=25;

	public static final int UNSTOREING_MY_FRESH = 27;//ˢ�����»�ȡ�ҵ��ղ�

	public static final int DELETE_UPTOWEB_FRESH = 28;//ˢ�����»�ȡ�ҵ��ϴ�
	
	public static final int MESSAGE_GET=29;
	public static final int FIRST_SHOW_IMG=31;//Ӧ�õ�һ�μ���ͼƬ
	public static final int LOGIN_OUT=32;
    /*
     * �ж��ֻ���û��sd��
     */
    public static boolean IsHaveSd(){
    	if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
    		return true;
    	}else return false;
    }
    /*
     * �ж��ֻ���û������
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
    //ʱ���ʽ
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
