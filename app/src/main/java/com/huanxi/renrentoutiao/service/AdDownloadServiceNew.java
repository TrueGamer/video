package com.huanxi.renrentoutiao.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.huanxi.renrentoutiao.MyApplication;
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

public class AdDownloadServiceNew extends IntentService {

    public static final String DOWNLOAD_INTERFACE_BEAN="download_interface";

    public List<String> mPageNames=new ArrayList<>();
    private Handler mHandler;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public AdDownloadServiceNew(String name) {
        super(name);
    }

    public AdDownloadServiceNew() {
        super("default");
        //这里创建一个handler是主线程的；
        mHandler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
    }


    private void getAllPackagename() {

        MyApplication application = (MyApplication) getApplication();

        List<String> allPackageName = application.getAllPackageName();

        if(allPackageName!=null && allPackageName.size()>0){
            mPageNames=allPackageName;
        }
        //==============上面是从application中获取包名=======================

        if(mPageNames!=null && mPageNames.size()>0){

            return;
        }

        PackageManager pm=getPackageManager();
        List<PackageInfo> list=pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo packageInfo : list) {
            //获取到设备上已经安装的应用的名字,即在AndriodMainfest中的app_name。
            String appName=packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            //获取到应用所在包的名字,即在AndriodMainfest中的package的值。
            String packageName=packageInfo.packageName;
            mPageNames.add(packageName);
            //设置包名
            application.setAllPackageName(mPageNames);
        }
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        getAllPackagename();

        //这里放子线程中进行，对其进行一个优化
        DownloadServiceInterface newsItemBean = (DownloadServiceInterface) intent.getSerializableExtra(DOWNLOAD_INTERFACE_BEAN);

        if(newsItemBean != null && mPageNames.contains(newsItemBean.getPackageName())){

            //判断用户是否安装这个应用
            try{
                Intent startIntent = this.getPackageManager().getLaunchIntentForPackage(newsItemBean.getPackageName());
                startActivity(startIntent);
            }catch(Exception e){
                //去下载；
                downloadApks(newsItemBean);
            }

        }else{
            downloadApks(newsItemBean);
        }
    }


    /**
     * 这里是去下载的逻辑
     * @param newsItemBean
     */
    private void downloadApks(final DownloadServiceInterface newsItemBean) {

        String packename = newsItemBean.getPackageName();
        String downloadUrl=newsItemBean.getDownloadUrl();

        //下载的地址；
        final String storeUrl= Environment.getExternalStorageDirectory().getAbsoluteFile()+"/quanmingnews"+"/"+packename+".apk";

        File file = new File(storeUrl);

        if(file.exists()){
            //如果大小相等就，不去下载；
            if(file.length() == newsItemBean.getAppSize()){

                //跳转到安装的界面；
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Uri data=null;
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                    //这里的话，我们在7.0是需要使用FileProvider;
                    data = FileProvider.getUriForFile(AdDownloadServiceNew.this, "com.huanxi.renrentoutiao.fileprovider", new File(storeUrl));
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }else{
                    data = Uri.fromFile(new File(storeUrl));
                }

                intent.setDataAndType(data,
                        "application/vnd.android.package-archive");
                startActivity(intent);
                return;
            }
        }


        mHandler.post(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(AdDownloadServiceNew.this,"开始下载"+newsItemBean.getAppName(),Toast.LENGTH_SHORT).show();

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
             /*   Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                Uri data = Uri.fromFile(new File(storeUrl));
                intent.setDataAndType(data,
                        "application/vnd.android.package-archive");
                startActivity(intent);
            */

                //跳转到安装的界面；
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Uri data=null;
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                    //这里的话，我们在7.0是需要使用FileProvider;
                    data = FileProvider.getUriForFile(AdDownloadServiceNew.this, "com.huanxi.renrentoutiao.fileprovider", new File(storeUrl));
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
