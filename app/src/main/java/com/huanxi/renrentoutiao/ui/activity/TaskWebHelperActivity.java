package com.huanxi.renrentoutiao.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.news.ApiNewAdLog;
import com.huanxi.renrentoutiao.net.api.user.task.ApiCustomTaskEnd;
import com.huanxi.renrentoutiao.net.api.user.task.ApiCustomTaskStart;
import com.huanxi.renrentoutiao.net.bean.news.ResAward;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.ui.dialog.RedPacketDialog;
import com.huanxi.renrentoutiao.ui.fragment.WebViewFragment;
import com.huanxi.renrentoutiao.utils.InfoUtil;
import com.huanxi.renrentoutiao.utils.SharedPreferencesUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

/**
 *  这里是自定义任务的WebHelpter:
 *  网页的操作；
 */

public class TaskWebHelperActivity extends BaseTitleActivity  {


    public static final String WEB_TITLE="title";
    public static final String WEB_URL="url";
    public static final String WEB_IN="isJumpWeb";
    public static final String TASK_ID="taskId";

    private boolean mWebInJump;
    private String mUrl;
    private String mWebTitle;
    private String mTaskId;

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        super.initView(rootView, savedInstanceState);

        setBackText("");
        setTitle("");

        mWebTitle = getIntent().getStringExtra(WEB_TITLE);
        mUrl = getIntent().getStringExtra(WEB_URL);
        mWebInJump = getIntent().getBooleanExtra(WEB_IN,true);
        mTaskId = getIntent().getStringExtra(TASK_ID);

        setTitle(mWebTitle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container,WebViewFragment.getFragment(mUrl,mWebInJump))
                .commitNowAllowingStateLoss();

    }



    public static Intent getIntent(Context context,String url,String title,boolean isWebIn,String taskId){

        Intent intent = new Intent(context, TaskWebHelperActivity.class);
        intent.putExtra(WEB_URL,url);
        intent.putExtra(WEB_IN,isWebIn);
        intent.putExtra(WEB_TITLE,title);
        intent.putExtra(TASK_ID,taskId);
        return intent;
    }


    public void startTask(){

        /*HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiCustomTaskEnd.FROM_UID,getUserBean().getUserid());
        paramsMap.put(ApiCustomTaskEnd.TASK_ID,mTaskId);

        ApiCustomTaskStart apiCustomTaskStart=new ApiCustomTaskStart(new HttpOnNextListener<ResAward>() {

            @Override
            public void onNext(ResAward s) {

            }

        },this,paramsMap);

        HttpManager.getInstance().doHttpDeal(apiCustomTaskStart);*/
        InfoUtil.getNetIp(new InfoUtil.NetCallback() {
            @Override
            public void onSuccess(String value) {
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put(ApiNewAdLog.TYPE,"0");
                paramsMap.put(ApiNewAdLog.SERVER_NUMBER,SharedPreferencesUtils.getInstance(TaskWebHelperActivity.this).getString(SharedPreferencesUtils.CHANNEL));
                paramsMap.put(ApiNewAdLog.MAC_ADDRESS,InfoUtil.getMacAddress());
                paramsMap.put(ApiNewAdLog.PHONE_BRAND,Build.BRAND);
                paramsMap.put(ApiNewAdLog.PHONE_MODULE,Build.MODEL);
                paramsMap.put(ApiNewAdLog.SYSTEM_VERSION,Build.VERSION.RELEASE);
                paramsMap.put(ApiNewAdLog.IP,value);
                paramsMap.put(ApiNewAdLog.AD_CHANNEL_NUM,ApiNewAdLog.AD_CHANNEL_ADHUB);
//                paramsMap.put(ApiNewAdLog.AD_ID,);
//                paramsMap.put(ApiNewAdLog.NEWS_ID,mMd5Url);

                ApiNewAdLog apiStartReadIssure = new ApiNewAdLog(new HttpOnNextListener<String>() {

                    @Override
                    public void onNext(String str) {
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                },TaskWebHelperActivity.this,paramsMap);
                HttpManager.getInstance().doHttpDeal(apiStartReadIssure);
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void endTask(){

        /*HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiCustomTaskEnd.FROM_UID,getUserBean().getUserid());
        paramsMap.put(ApiCustomTaskEnd.TASK_ID,mTaskId);

        ApiCustomTaskEnd apiCustomTaskStart=new ApiCustomTaskEnd(new HttpOnNextListener<ResAward>() {

            @Override
            public void onNext(ResAward resAward) {

                RedPacketDialog.show(TaskWebHelperActivity.this, resAward.getIntegral(), new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                finish();
            }
        },this,paramsMap);

        HttpManager.getInstance().doHttpDeal(apiCustomTaskStart);*/
        InfoUtil.getNetIp(new InfoUtil.NetCallback() {
            @Override
            public void onSuccess(String value) {
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put(ApiNewAdLog.TYPE,"1");
                paramsMap.put(ApiNewAdLog.SERVER_NUMBER,SharedPreferencesUtils.getInstance(TaskWebHelperActivity.this).getString(SharedPreferencesUtils.CHANNEL));
                paramsMap.put(ApiNewAdLog.MAC_ADDRESS,InfoUtil.getMacAddress());
                paramsMap.put(ApiNewAdLog.PHONE_BRAND,Build.BRAND);
                paramsMap.put(ApiNewAdLog.PHONE_MODULE,Build.MODEL);
                paramsMap.put(ApiNewAdLog.SYSTEM_VERSION,Build.VERSION.RELEASE);
                paramsMap.put(ApiNewAdLog.IP,value);
                paramsMap.put(ApiNewAdLog.AD_CHANNEL_NUM,ApiNewAdLog.AD_CHANNEL_ADHUB);
//                paramsMap.put(ApiNewAdLog.AD_ID,);
//                paramsMap.put(ApiNewAdLog.NEWS_ID,mMd5Url);

                ApiNewAdLog apiStartReadIssure = new ApiNewAdLog(new HttpOnNextListener<String>() {

                    @Override
                    public void onNext(String str) {
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                },TaskWebHelperActivity.this,paramsMap);
                HttpManager.getInstance().doHttpDeal(apiStartReadIssure);
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void onBackPressed() {
        //这里是需要做判断的操作；
        //super.onBackPressed();
        endTask();
    }

    @Override
    public void onClickBack() {
        onBackPressed();
    }
}
