package com.huanxi.renrentoutiao.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.news.ApiNewAdLog;
import com.huanxi.renrentoutiao.net.api.user.task.ApiCustomTaskEnd;
import com.huanxi.renrentoutiao.net.bean.news.ResAward;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.ui.dialog.RedPacketDialog;
import com.huanxi.renrentoutiao.ui.fragment.WebViewFragment;
import com.huanxi.renrentoutiao.utils.InfoUtil;
import com.huanxi.renrentoutiao.utils.SharedPreferencesUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;


public class WebHelperActivity extends BaseTitleActivity  {


    public static final String WEB_TITLE="title";
    public static final String WEB_URL="url";
    public static final String WEB_IN="isJumpWeb";
    private boolean mWebInJump;
    private String mUrl;
    private String mWebTitle;
    private WebViewFragment mFragment;

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        super.initView(rootView, savedInstanceState);

        setBackText("");
        setTitle("");
        ImageView imageColse = (ImageView) getColseImg();

        mWebTitle = getIntent().getStringExtra(WEB_TITLE);
        mUrl = getIntent().getStringExtra(WEB_URL);
        mWebInJump = getIntent().getBooleanExtra(WEB_IN,true);

        setTitle(mWebTitle);

        imageColse.setVisibility(View.VISIBLE);
        imageColse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mFragment = WebViewFragment.getFragment(mUrl,mWebInJump);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container,mFragment)
                .commitNowAllowingStateLoss();
    }

    public static Intent getIntent(Context context,String url,String title,boolean isWebIn){

        Intent intent = new Intent(context, WebHelperActivity.class);
        intent.putExtra(WEB_URL,url);
        intent.putExtra(WEB_IN,isWebIn);
        intent.putExtra(WEB_TITLE,title);
        return intent;
    }

    public static Intent getIntent(Context context,String url,String title){

       return getIntent(context,url,title,true);
    }

    @Override
    public void onBackPressed() {
        if (!mFragment.canBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onClickBack() {
        onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFragment.onActivityResult(requestCode,resultCode,data);
    }

    public void endTask(){

        /*HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiCustomTaskEnd.FROM_UID,getUserBean().getUserid());
        paramsMap.put(ApiCustomTaskEnd.TASK_ID , "");

        ApiCustomTaskEnd apiCustomTaskStart=new ApiCustomTaskEnd(new HttpOnNextListener<ResAward>() {

            @Override
            public void onNext(ResAward resAward) {

                RedPacketDialog.show(WebHelperActivity.this, resAward.getIntegral(), new DialogInterface.OnDismissListener() {
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
                paramsMap.put(ApiNewAdLog.SERVER_NUMBER,SharedPreferencesUtils.getInstance(WebHelperActivity.this).getString(SharedPreferencesUtils.CHANNEL));
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
                },WebHelperActivity.this,paramsMap);
                HttpManager.getInstance().doHttpDeal(apiStartReadIssure);
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
            }
        });
    }

}
