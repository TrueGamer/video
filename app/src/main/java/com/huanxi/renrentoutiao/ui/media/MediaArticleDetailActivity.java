package com.huanxi.renrentoutiao.ui.media;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantUrl;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;

import butterknife.BindView;

/**
 * 头条号新闻详情
 */
public class MediaArticleDetailActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏模式
        setContentView(R.layout.activity_media_article_detail_layout);
        initView();
    }

    protected void initView() {
        TextView tv_back = findViewById(R.id.tv_back);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_back.setText("");
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String mediaId = getIntent().getStringExtra("mediaId");
        String title = getIntent().getStringExtra("title");

        if(title != null) {
            tv_title.setText(title);
        }

        UserBean userBean = ((MyApplication)getApplication()).getUserBean();
        String userId = userBean.getUserid();

        WebView mWebView = findViewById(R.id.webView);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        Log.i("info" , "detailUrl===="+String.format(ConstantUrl.MediaArticalDetailURL , mediaId , userId));
        mWebView.loadUrl(String.format(ConstantUrl.MediaArticalDetailURL , mediaId , userId));
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 这里进行url拦截
                view.loadUrl(url);
                return true;
            }
        });
    }
}
