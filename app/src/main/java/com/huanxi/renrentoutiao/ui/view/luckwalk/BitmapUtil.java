package com.huanxi.renrentoutiao.ui.view.luckwalk;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by zd on 2016/7/13.
 */
public class BitmapUtil {


    public static StateListDrawable getGradientByStatus(Context context,int resId[],int state[]) {
        StateListDrawable stateList = new StateListDrawable();
        for (int i=0;i<resId.length;i++){
            Drawable checked = ContextCompat.getDrawable(context,resId[i]) ;
            stateList.addState(new int[] {state[i]}, checked);
        }
        return stateList;
    }

    public static StateListDrawable getGradientByStatus(Context context,String resId[],int state[]) {
        StateListDrawable stateList = new StateListDrawable();
        for (int i=0;i<resId.length;i++){
            String s = resId[i];
            s=s.replaceAll(".png","");
            if(s.contains("9.9"))
                s="tab_"+s.replaceAll("9.9","nine");
            Drawable checked = ContextCompat.getDrawable(context,getResIdByFolder(context,s,"mipmap")) ;
            stateList.addState(new int[] {state[i]}, checked);
        }
        return stateList;
    }

    public static int getResIdByFolder(Context context,String resName,String folderName){
        resName=resName.replaceAll(".png","");
        Log.e("resId",resName+"_"+folderName);
        return context.getResources().getIdentifier(resName, folderName, context.getPackageName());
    }

    public static int getResIdByMipMapFolder(Context context,String resName){
        resName=resName.replaceAll(".png","");
        Log.e("resId",resName+"_");
        return context.getResources().getIdentifier(resName, "mipmap", context.getPackageName());
    }

    public static Drawable getDrawableByName(Context context,String name){
        return ContextCompat.getDrawable(context,getResIdByFolder(context,name,"mipmap")) ;
    }

    public static Uri getRealImageUri(String path,Context context){
        Log.d("getRealImageUri", "path1 is " + path);
        Uri uri  = null;
        if (path != null) {
            path = Uri.decode(path);
            Log.d("getRealImageUri", "path2 is " + path);
            ContentResolver cr = context.getContentResolver();
            StringBuffer buff = new StringBuffer();
            buff.append("(")
                    .append(MediaStore.Images.ImageColumns.DATA)
                    .append("=")
                    .append("'" + path + "'")
                    .append(")");
            Cursor cur = cr.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[] { MediaStore.Images.ImageColumns._ID },
                    buff.toString(), null, null);
            int index = 0;
            for (cur.moveToFirst(); !cur.isAfterLast(); cur
                    .moveToNext()) {
                index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                // set _id value
                index = cur.getInt(index);
            }
            if (index == 0) {
                //do nothing
                uri = new Uri.Builder().build();
            } else {
                Uri uri_temp = Uri
                        .parse("content://media/external/images/media/"
                                + index);
                Log.d("getRealImageUri", "uri_temp is " + uri_temp);
                if (uri_temp != null) {
                    uri = uri_temp;
                }
            }
        }
        return uri;
    }



    public static Bitmap getThumbnail(Context context, String path) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] {MediaStore.Images.Media._ID},
                "LOWER(" + MediaStore.Images.Media.DATA + ")" + "=?",
                new String[] {path.toLowerCase()},
                MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        long origId = 0;
        if (cursor.moveToNext()) {
            origId = cursor.getLong(0);
        }
        return MediaStore.Images.Thumbnails.getThumbnail(resolver, origId, MediaStore.Images.Thumbnails.MICRO_KIND, null);
    }


    public static int getTextColor(String color){
        try {
            return Color.parseColor(color);
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }
}
