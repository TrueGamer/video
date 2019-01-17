package com.huanxi.renrentoutiao.service;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.Toast;

import com.huanxi.renrentoutiao.utils.FormatUtils;
import com.zhxu.library.download.DownInfo;
import com.zhxu.library.download.HttpDownManager;
import com.zhxu.library.listener.HttpDownOnNextListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinosa on 2018/3/28.
 * 下载应用的逻辑
 */

public class DownloadService extends IntentService {

    public static final String DOWNLOAD_URL="downloadUrl";

    public List<String> mPageNames=new ArrayList<>();
    private Handler mHandler;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadService(String name) {
        super(name);
    }

    public DownloadService() {
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
        String newsItemBean = intent.getStringExtra(DOWNLOAD_URL);

        if(!TextUtils.isEmpty(newsItemBean)) {
            downloadApks(newsItemBean);
        }
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

                Toast.makeText(DownloadService.this,"开始下载",Toast.LENGTH_SHORT).show();

            }
        });


        DownInfo downInfo = new DownInfo(downloadUrl, new HttpDownOnNextListener() {
            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onStart() {
         }

            @Override
            public void onComplete() {

                //跳转到安装的界面；
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Uri data=null;
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                    //这里的话，我们在7.0是需要使用FileProvider;
                    data = FileProvider.getUriForFile(DownloadService.this, "com.huanxi.renrentoutiao.fileprovider", new File(storeUrl));
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }else{
                    data = Uri.fromFile(new File(storeUrl));
                }

                intent.setDataAndType(data,
                        "application/vnd.android.package-archive");
                startActivity(intent);

            }

            @Override
            public void updateProgress(long readLength, long countLength) {

            }
        });
        downInfo.setSavePath(storeUrl);
        HttpDownManager.getInstance().startDownSync(downInfo);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler=null;
        }
    }
}
