package com.huanxi.renrentoutiao.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.user.task.ApiCustomTaskEnd;
import com.huanxi.renrentoutiao.net.api.user.task.ApiCustomTaskStart;
import com.huanxi.renrentoutiao.net.bean.news.ResAward;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.ui.dialog.RedPacketDialog;
import com.huanxi.renrentoutiao.ui.fragment.WebViewFragment;
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

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiCustomTaskEnd.FROM_UID,getUserBean().getUserid());
        paramsMap.put(ApiCustomTaskEnd.TASK_ID,mTaskId);

        ApiCustomTaskStart apiCustomTaskStart=new ApiCustomTaskStart(new HttpOnNextListener<ResAward>() {

            @Override
            public void onNext(ResAward s) {

            }

        },this,paramsMap);

        HttpManager.getInstance().doHttpDeal(apiCustomTaskStart);
    }

    public void endTask(){

        HashMap<String, String> paramsMap = new HashMap<>();
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

        HttpManager.getInstance().doHttpDeal(apiCustomTaskStart);
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
