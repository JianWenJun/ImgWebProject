package com.example.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;

@SuppressLint("NewApi")
public class SDcardHelp {
	// �ж�SD���Ƿ񱻹��� ( ���أ������ڴ濨�Ѿ���ϵͳʶ���ȡ�ˣ���������ʹ���ˡ�)
    public static boolean isSDcardMounted(){
	 return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    // ��ȡSD���ĸ�Ŀ¼  
    public static String getSDcardBaseDir(){
    	if(isSDcardMounted()){
    	return Environment.getExternalStorageDirectory().getAbsolutePath();
    	}
    	else return null;
    }
    // ��ȡSD���������ռ��С������MB  
    public static long getSDCardSize() { 
     if(isSDcardMounted()){
    	 StatFs fs=new StatFs(getSDcardBaseDir());
    	 long count=fs.getFreeBlocksLong();
    	 long size=fs.getBlockSizeLong();
    	 return count*size/1024/1024;    	 
     }
     else return 0;
    }
   // ��ȡSD����ʣ��ռ��С  
    public static long getSDCardFreeSize() {  
        if (isSDcardMounted()) {  
            StatFs fs = new StatFs(getSDcardBaseDir());  
            long count = fs.getFreeBlocksLong();  
            long size = fs.getBlockSizeLong();  
            return count * size / 1024 / 1024;  
        }  
        return 0;  
    }  
    
    // ��ȡSD���Ŀ��ÿռ��С  
    public static long getSDCardAvailableSize() {  
        if (isSDcardMounted()) {  
            StatFs fs = new StatFs(getSDcardBaseDir());  
            long count = fs.getAvailableBlocksLong();  
            long size = fs.getBlockSizeLong();  
            return count * size / 1024 / 1024;  
        }  
        return 0;  
    }  
    // ��SD���Ĺ���Ŀ¼�±����ļ�  
    public static boolean saveFileToSDcardPublicDir(byte[]data,String type,String filename){
    	BufferedOutputStream bos=null;
    	if(isSDcardMounted()){
    		File file=Environment.getExternalStoragePublicDirectory(type);
    		try {
				bos=new BufferedOutputStream(new FileOutputStream(new File(file,filename)));
			    bos.write(data);
			    bos.flush();
			    return true;
    		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    	}
    	return false;
    }
    
 // ��SD�����Զ���Ŀ¼�±����ļ�  
     public static boolean saveFileToSDcardCustomDir(byte []data,String dir,String filename){
    	 BufferedOutputStream bos=null;
    	 if(isSDcardMounted()){
    		 File file=new File(getSDcardBaseDir()+File.separator+dir);
    		 if (!file.exists()) {
				file.mkdir();// �ݹ鴴���Զ���Ŀ¼  
			}
    		 try {
				bos=new BufferedOutputStream(new FileOutputStream(new File(file,filename)));
				 bos.write(data);  
	                bos.flush();  
	                return true;  
    		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    	 }return false;
     }
     // ��SD����˽��FilesĿ¼�±����ļ�  
     public static boolean saveFileToSDcardPrivateFileDir(byte[]data,String type,String filename,Context context){
    	 BufferedOutputStream bos=null;
    	 if(isSDcardMounted()){
    		 File file=context.getExternalFilesDir(type);
    		 try {
				bos=new BufferedOutputStream(new FileOutputStream(new File(file,filename)));
			   bos.write(data);
			   bos.flush();
    		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    		 
    	 }return false;
     }
     
  // ��SD����˽��CacheĿ¼�±����ļ�  
     public static boolean saveFileToSDcardPrivateCacheDir(byte[]data,String filename,Context context){
    	 BufferedOutputStream bos=null;
    	 if(isSDcardMounted()){
    		 File file=context.getExternalCacheDir();
    		 try {
				bos=new BufferedOutputStream(new FileOutputStream(new File(file,filename)));
			   bos.write(data);
			   bos.flush();
    		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    		 
    	 }return false;
     }
     
     // ����bitmapͼƬ��SDCard��˽��CacheĿ¼  
     public static boolean saveBitmapToSDcardPrivateCacheDir(Bitmap bitmap,String filename,Context context){
    	if(isSDcardMounted()) {
    		BufferedOutputStream bos=null;
    		File file =context.getExternalCacheDir();
    		try {
				bos=new BufferedOutputStream(new FileOutputStream(new File(file,filename)));
				if(filename!=null&&(filename.contains(".png")||filename.contains(".PNG"))){
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
					
				}else bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
				bos.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {  
                if (bos != null) {  
                    try {  
                        bos.close();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
    		return true;
    	
    	}	else return false;
     }
     
  // ��SD����ȡ�ļ� 
     public static byte[] loadFileFromSDcard(String fileDir){
    	 BufferedInputStream bis=null;
    	 ByteArrayOutputStream baos =new ByteArrayOutputStream();
    	 try {
			bis=new BufferedInputStream(new FileInputStream(new File(fileDir)));
			byte[]buffer=new byte[1024];
			int c=0;
			while((c=bis.read(buffer))!=-1){
				baos.write(buffer,0,c);
				baos.flush();
			}
			return baos.toByteArray();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				baos.close();
				bis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}return null;
     }
     
     // ��SDCard��Ѱ��ָ��Ŀ¼�µ��ļ�������Bitmap  
     public Bitmap loadBitmapFromSDcard(String filepath){
    	 byte[]data=loadFileFromSDcard(filepath);
    	 if(data!=null){
    		Bitmap bm=BitmapFactory.decodeByteArray(data, 0, data.length) ;
    		return bm;
    	 }
    	 return null;
     }
     
     // ��ȡSD������Ŀ¼��·��  
     public static String getSDcardPublicDir(String type){
    	 return Environment.getExternalStoragePublicDirectory(type).toString();
     }
     // ��ȡSD��˽��CacheĿ¼��·��  
     public static String getSDCardPrivateCacheDir(Context context) {  
         return context.getExternalCacheDir().getAbsolutePath();  
     }  
     // ��ȡSD��˽��FilesĿ¼��·��  
     public static String getSDCardPrivateFilesDir(Context context, String type) {  
         return context.getExternalFilesDir(type).getAbsolutePath();  
     }  
     public static boolean removeFileFromSDCard(String filePath) {  
         File file = new File(filePath);  
         if (file.exists()) {  
             try {  
                 file.delete();  
                 return true;  
             } catch (Exception e) {  
                 return false;  
             }  
         } else {  
             return false;  
         }  
     }  
}
