package com.huanxi.renrentoutiao.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.utils.FormatUtils;
import com.zhxu.library.download.DownInfo;
import com.zhxu.library.download.HttpDownManager;
import com.zhxu.library.listener.HttpDownOnNextListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinosa on 2018/3/28.
 * 下载带通知栏显示进度条的那种；
 */

public class DownloadServiceWithNotifytation extends IntentService {

    public static final String DOWNLOAD_URL="downloadUrl";

    public List<String> mPageNames=new ArrayList<>();
    private Handler mHandler;

    long lastTime;  //一秒钟采集一次；

    private final int id =1;
    private String mDownLoadUrl;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadServiceWithNotifytation(String name) {
        super(name);
    }

    public DownloadServiceWithNotifytation() {
        super("default");
        //这里创建一个handler是主线程的；
        mHandler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        //这里放子线程中进行，对其进行一个优化
        mDownLoadUrl = intent.getStringExtra(DOWNLOAD_URL);

        downloadApks(mDownLoadUrl);
    }


    /**
     * 这里是去下载的逻辑
     * @param url
     */
    private void downloadApks(String url) {

        String downloadUrl=url;
        String name= FormatUtils.md5(url);


        //下载的地址；
        final String storeUrl= Environment.getExternalStorageDirectory().getAbsoluteFile()+"/quanmingnews"+"/"+name+".apk";

        File file = new File(storeUrl);


        mHandler.post(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(DownloadServiceWithNotifytation.this,"开始下载",Toast.LENGTH_SHORT).show();

            }
        });


        DownInfo downInfo = new DownInfo(downloadUrl, new HttpDownOnNextListener() {

            boolean isSuccess;
            @Override
            public void onNext(Object o) {
                isSuccess=true;
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                isSuccess=false;

                mBuilder.setProgress(0, 0,false).setContentText("下载失败，点击重试！！！");
                mBuilder.setContentIntent(getReTry());
                mNotificationManager.notify(id, mBuilder.build());
            }

            @Override
            public void onComplete() {

                if(isSuccess){


                    mBuilder.setProgress(0, 0,false).setContentText("下载进度:" + 100+"%");

                    // mBuilder.setFullScreenIntent(getPedingIntent(), true);
                    mBuilder.setContentTitle("下载完成点击安装");
                    mBuilder.setDefaults(Notification.DEFAULT_ALL); //这里可以悬挂几秒自动收起来
                    mBuilder.setContentIntent(getPedingIntent(storeUrl));
                    mBuilder.setPriority(Notification.PRIORITY_HIGH);
                    Notification notification = mBuilder.build();
                    notification.flags=Notification.FLAG_AUTO_CANCEL;
                    mNotificationManager.notify(id, notification);
                    //显示下载完成；


                    //这里表示下载成功；
                    //跳转到安装的界面；
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    Uri data=null;
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                        //这里的话，我们在7.0是需要使用FileProvider;
                        data = FileProvider.getUriForFile(DownloadServiceWithNotifytation.this, "com.huanxi.renrentoutiao.fileprovider", new File(storeUrl));
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }else{
                        data = Uri.fromFile(new File(storeUrl));
                    }

                    intent.setDataAndType(data,
                            "application/vnd.android.package-archive");
                    startActivity(intent);

                }
            }

            @Override
            public void updateProgress(long readLength, long countLength) {

                //这里我们在通知栏显示下载的进度；


                long currentTime =System.currentTimeMillis();
                if (currentTime-lastTime>1000) {
                    //1秒钟采集一次；
                    Float currentNum =  (100.0f * readLength / countLength);//下载的百分比；
                    //更新进度条
                    mBuilder.setProgress(100, currentNum.intValue(),false);
                    mBuilder.setContentText("下载进度:" + currentNum.intValue()+"%");
                    mNotificationManager.notify(id, mBuilder.build());
                    lastTime=currentTime;
                }
            }
        });
        downInfo.setSavePath(storeUrl);
        HttpDownManager.getInstance().startDownSync(downInfo);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        startNotifycation();
    }



    NotificationManager mNotificationManager;
    Notification mNotification;
    private Notification.Builder mBuilder;

    /**
     * 打开通知的测试；
     */
    private void startNotifycation() {


        mNotificationManager=((NotificationManager) getSystemService(NOTIFICATION_SERVICE));

        mBuilder = new Notification.Builder(this);
        mBuilder.setContentTitle("正在下载...") //设置通知标题
                .setSmallIcon(R.mipmap.ic_launcher) //设置通知的小图标
                .setLargeIcon(BitmapFactory
                        .decodeResource(this.getResources(),
                                R.mipmap.ic_launcher)) //设置通知的大图标
                .setDefaults(Notification.DEFAULT_LIGHTS) //设置通知的提醒方式： 呼吸灯
                .setPriority(Notification.PRIORITY_MAX) //设置通知的优先级：最大
                .setAutoCancel(false)//设置通知被点击一次是否自动取消
                .setContentText("下载进度:" + "0%")
                .setProgress(100, 0, false);
        mNotification = mBuilder.build();//构建通知对象

        mNotificationManager.notify(id, mNotification);
    }


    /**
     * 重新下载的逻辑
     * @return
     */
    private PendingIntent getReTry() {
        Intent intent = new Intent(this, DownloadServiceWithNotifytation.class);
        intent.putExtra(DOWNLOAD_URL,mDownLoadUrl);
        PendingIntent service = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return service;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler=null;
        }
    }


    public PendingIntent getPedingIntent(String storeUrl) {

        //这里表示下载成功；
        //跳转到安装的界面；
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Uri data=null;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            //这里的话，我们在7.0是需要使用FileProvider;
            data = FileProvider.getUriForFile(DownloadServiceWithNotifytation.this, "com.huanxi.renrentoutiao.fileprovider", new File(storeUrl));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }else{
            data = Uri.fromFile(new File(storeUrl));
        }

        intent.addCategory(Intent.CATEGORY_DEFAULT);

        intent.setDataAndType(data,
                "application/vnd.android.package-archive");

        PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return pendingIntent;
    }
}
