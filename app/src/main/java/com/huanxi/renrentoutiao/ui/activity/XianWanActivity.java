package com.huanxi.renrentoutiao.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;

import butterknife.BindView;

/**
 * Created by Dinosa on 2018/4/23.
 * 这里是针对闲玩的网页的逻辑
 * 这里我们不做任何的操作；
 */
@Deprecated
public class XianWanActivity extends BaseTitleActivity {

    @BindView(R.id.webview)
    WebView mWebView;
    private String mUrl;

    @Override
    public int getBodyLayoutId() {
        return R.layout.activity_xian_wan;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        super.initView(rootView, savedInstanceState);
        setBackText("");
        setTitle("闲玩");
    }

    @Override
    protected void initData() {
        super.initData();
        //这里设置一个操作；

        Intent intent = getIntent();
        mUrl = intent.getStringExtra(URL);


        WebSettings webSettings = mWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);//适应分辨率
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUserAgentString(webSettings.getUserAgentString());

        webSettings.setDatabaseEnabled(true);
        webSettings.setGeolocationEnabled(true);
        String dir = this.getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(dir);
        webSettings.setGeolocationDatabasePath(dir);

        webSettings.setAppCacheEnabled(true);
        String cacheDir = this.getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(cacheDir);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 10);
        webSettings.setAllowFileAccess(true);

        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        webSettings.setBuiltInZoomControls(false);
        //  webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setDefaultTextEncodingName("utf-8");

        mWebView.addJavascriptInterface(new MyInterface(),"android");
        //这里加载网页
        mWebView.loadUrl(mUrl);//这里要进行一个网页的跳转的操作；
    }


    public class MyInterface{

        @JavascriptInterface
        public void installAPP(String url){
            Toast.makeText(XianWanActivity.this,"js调用了InstallAPP的方法"+url,Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void browser(String url){
            Toast.makeText(XianWanActivity.this,"js调用了Browser的方法"+url,Toast.LENGTH_SHORT).show();
        }
    }

    public static final String URL="url";

    public static Intent getIntent(Context context,String url){

        Intent intent = new Intent(context,XianWanActivity.class);
        intent.putExtra(URL,url);
        return intent;
    }
}
