package com.huanxi.renrentoutiao.ui.activity.news.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.adapter.AdBean;
import com.huanxi.renrentoutiao.ui.adapter.AdsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Dinosa on 2018/1/29.
 */

public class SearchDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.ev_search)
    EditText mEvSearch;

    @BindView(R.id.pb_progress)
    ProgressBar mPbProgress;

    @BindView(R.id.web_view)
    WebView mWebView;

    @BindView(R.id.rv_ads)
    RecyclerView mRvAds;

    @Override
    public int getContentLayout() {
        return R.layout.activity_search_detail;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        initWebViewSetting();
        //取消文本框弹出；
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void initWebViewSetting() {
        //webView的设置
        WebSettings webSettings = mWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);//适应分辨率
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUserAgentString(webSettings.getUserAgentString());

        webSettings.setDatabaseEnabled(true);
        webSettings.setGeolocationEnabled(true);
        String dir = getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(dir);
        webSettings.setGeolocationDatabasePath(dir);

        webSettings.setAppCacheEnabled(true);
        String cacheDir = getDir("cache", Context.MODE_PRIVATE).getPath();
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

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mWebView.getSettings().setBlockNetworkImage(false);
                super.onPageFinished(view, url);
            }
        });
        //去掉webView右边的滚动条
        mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        mWebView.setVerticalScrollBarEnabled(false); //垂直不显示
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (mPbProgress == null) {
                    return;
                }
                if (newProgress == 100) {
                    // 网页加载完成
                    mPbProgress.setVisibility(View.GONE);
                } else {
                    // 加载中
                    mPbProgress.setProgress(newProgress);
                }
                System.out.println("progress: "+newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mPbProgress != null) {
                    mPbProgress.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();

        String searchKey = getIntent().getStringExtra(KEY_WORD);

        if (searchKey != null) {
            mEvSearch.setText(searchKey);
            mEvSearch.setSelection(searchKey.length());
        }
        doSearch(searchKey);

        //填充广告；

        if (isHasAds()) {
            //我们添加一个AD
            MyApplication application = (MyApplication) getApplication();
            List<AdBean> tasklist = application.getResAds().getTasklist();
            //这里我们使用一个广点通的
            AdsAdapter adsAdapter = new AdsAdapter(tasklist);
            mRvAds.setLayoutManager(new LinearLayoutManager(this){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            mRvAds.setAdapter(adsAdapter);
        }
    }

    private void doSearch(String searchKey) {

        mPbProgress.setVisibility(View.VISIBLE);
        //这里我们使用webView来加载这个页面；
        String url="https://www.baidu.com/s?wd="+searchKey;
        mWebView.loadUrl(url);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public static String KEY_WORD = "keyWord";


    public static Intent getSearchIntent(Context context, String keyWord) {
        Intent intent = new Intent(context, SearchDetailActivity.class);
        intent.putExtra(KEY_WORD, keyWord);
        return intent;
    }

    /**
     * 这里是搜索的页面；
     */
    @OnClick(R.id.tv_search)
    public void onClickSearch(){

        if (!TextUtils.isEmpty(mEvSearch.getText().toString())) {
            doSearch(mEvSearch.getText().toString());
        }
    }

    @OnClick(R.id.iv_back)
    public void onClickBack(){
        finish();
    }

}
