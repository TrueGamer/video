package com.huanxi.renrentoutiao.ui.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.share.ApiInviteFriendContent;
import com.huanxi.renrentoutiao.net.bean.ResInviteFriendContent;
import com.huanxi.renrentoutiao.service.DownloadServiceWithNotifytation;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.dialog.QrcodeShareDialog;
import com.huanxi.renrentoutiao.ui.dialog.invite.InviteFriendShareDialog;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseFragment;
import com.huanxi.renrentoutiao.utils.QrCodeUtils;
import com.huanxi.renrentoutiao.utils.ShareUtils;
import com.huanxi.renrentoutiao.utils.UIUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import butterknife.BindView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by Dinosa on 2018/2/28.
 *
 * 这里是webView的公共类；
 */

public class WebViewFragment extends BaseFragment {

    @BindView(R.id.web_view)
    WebView mWebView;

    @BindView(R.id.pb_progress)
    ProgressBar mProgess;


    private final static String TAG = "MainActivity";

    //android webView调用原生的相册的操作；
    private final static int CAPTURE_RESULTCODE = 1;
    private ValueCallback<Uri> mUploadMessage;
    private String filePath;


    public static final String WEB_TITLE="title";
    public static final String WEB_URL="url";
    public static final String WEB_IN="isJumpWeb";
    private boolean mWebInJump;
    private String mUrl;

    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;

    //用一个LinkedList来维护；
    LinkedList<String> mUrls = new LinkedList<>();
    InviteFriendShareDialog mShareDialog;

    @Override
    protected View getContentView() {
        return inflatLayout(R.layout.fragment_web_view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();

        mUrl = arguments.getString(WEB_URL);
        mWebInJump=arguments.getBoolean(WEB_IN);
    }

    @Override
    protected void initView() {
        super.initView();
        Log.i("info" , "initView");
        mUrl = getActivity().getIntent().getStringExtra(WEB_URL);
        mWebInJump = getActivity().getIntent().getBooleanExtra(WEB_IN,true);

        String uid=  "";
        if (getUserBean() != null) {
            uid = getUserBean().getUserid();
        }

        //这里我们要做的一个操作就是
        mShareDialog = new InviteFriendShareDialog(getBaseActivity(), new InviteFriendShareDialog.OnClickShareType() {
            @Override
            public void onClickQQ() {
                //这里我们要做的一个操作就是
            }

            @Override
            public void onClickWechat() {
            }

            @Override
            public void onClickWechatComment() {

            }
        },uid);
    }


    @Override
    public void initData() {
        Log.i("info" , "initData");
        //webView的设置
        WebSettings webSettings = mWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);//适应分辨率
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUserAgentString(webSettings.getUserAgentString());

        webSettings.setDatabaseEnabled(true);
        webSettings.setGeolocationEnabled(true);
        String dir = getActivity().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(dir);
        webSettings.setGeolocationDatabasePath(dir);

        webSettings.setAppCacheEnabled(true);
        String cacheDir = getActivity().getDir("cache", Context.MODE_PRIVATE).getPath();
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




        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        mWebView.setVerticalScrollBarEnabled(true);
        mWebView.setHorizontalScrollBarEnabled(true);


        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Log.i("tag", "url="+url);

                Log.i("tag", "userAgent="+userAgent);

                Log.i("tag", "contentDisposition="+contentDisposition);

                Log.i("tag", "mimetype="+mimetype);

                Log.i("tag", "contentLength="+contentLength);

                startDownoad(url);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebView.setWebViewClient(new WebViewClient() {


            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mWebView.getSettings().setBlockNetworkImage(false);
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if(url.startsWith("http")||url.startsWith("https")){

                    //这里进行一个出栈的操作；

                    mUrls.add(url);

                    mWebView.loadUrl(url);
                    return true;

                    //这里要做的一个逻辑就是
                   /* if(mWebInJump){

                        mWebView.loadUrl(url);
                        return true;

                    }else{
                        return super.shouldOverrideUrlLoading(view, url);
                    }*/

                }else {

                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        Uri data = Uri.parse(url);
                        intent.setData(data);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //这里表示没有找到对应的包名
                        toast("未找到相关应用");
                    }
                    return true;
                }
            }

            // The webPage has 2 filechoosers and will send a
            // console message informing what action to perform,
            // taking a photo or updating the file

            public boolean onConsoleMessage(ConsoleMessage cm) {

                onConsoleMessage(cm.message(), cm.lineNumber(), cm.sourceId());
                return true;
            }

            public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                Log.d("androidruntime", "Show console messages, Used for debugging: " + message);
            }
        });
        //去掉webView右边的滚动条
       // mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
       // mWebView.setVerticalScrollBarEnabled(false); //垂直不显示
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    mProgess.setVisibility(View.GONE);
                } else {
                    // 加载中
                    mProgess.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                Log.d(TAG, "openFileChoose(ValueCallback<Uri> uploadMsg)");
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                getBaseActivity().startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }

            // For Android 3.0+
            public void openFileChooser( ValueCallback uploadMsg, String acceptType ) {
                Log.d(TAG, "openFileChoose( ValueCallback uploadMsg, String acceptType )");
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                getBaseActivity().startActivityForResult(
                        Intent.createChooser(i, "File Browser"),
                        FILECHOOSER_RESULTCODE);
            }
            //For Android 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
                Log.d(TAG, "openFileChoose(ValueCallback<Uri> uploadMsg, String acceptType, String capture)");
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                getBaseActivity().startActivityForResult( Intent.createChooser( i, "File Browser" ), WebViewFragment.FILECHOOSER_RESULTCODE );
            }
            // For Android 5.0+
            public boolean onShowFileChooser (WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                Log.d(TAG, "onShowFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)");
                mUploadCallbackAboveL = filePathCallback;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                getBaseActivity().startActivityForResult(
                        Intent.createChooser(i, "File Browser"),
                        FILECHOOSER_RESULTCODE);
                return true;
            }
        });

        mWebView.addJavascriptInterface(new MyInterface(),"android");

        //加载url
        mWebView.loadUrl(mUrl);
    }



    public static WebViewFragment getFragment(String url, boolean isWebInJump){

        WebViewFragment webViewFragment = new WebViewFragment();
        Bundle bundle = new Bundle();

        bundle.putString(WEB_URL,url);
        bundle.putBoolean(WEB_IN,isWebInJump);

        webViewFragment.setArguments(bundle);

        return webViewFragment;
    }

    public static Fragment getFragment(String url){

        WebViewFragment webViewFragment = new WebViewFragment();
        Bundle bundle = new Bundle();

        bundle.putString(WEB_URL,url);
        bundle.putBoolean(WEB_IN,true);
        webViewFragment.setArguments(bundle);
        return webViewFragment;
    }



    public class MyInterface{

        @JavascriptInterface
        public void installAPP(String url){

            //Toast.makeText(getBaseActivity(),"js调用了InstallAPP的方法"+url,Toast.LENGTH_SHORT).show();
            //下载安装的逻辑
            startDownoad(url);

        }

        @JavascriptInterface
        public void browser(String url){
            //浏览器的操作
            //Toast.makeText(getBaseActivity(),"js调用了Browser的方法"+url,Toast.LENGTH_SHORT).show();

            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        @JavascriptInterface
        public void showShare(String setTitle, String desc, String setImageUrl, String clickUrl) {
            OnekeyShare oks = new OnekeyShare();
            //关闭sso授权
            oks.disableSSOWhenAuthorize();
            // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
            //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
            oks.setTitle(setTitle);
            // text是分享文本，所有平台都需要这个字段
            oks.setText(desc);
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            oks.setImageUrl(setImageUrl);
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl(clickUrl);
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            //oks.setComment("我是测试评论文本");
            // site是分享此内容的网站名称，仅在QQ空间使用
            // oks.setSite(getString(R.string.app_name));
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl(clickUrl);
            // 启动分享GUI
            oks.show(getContext());
        }

        @JavascriptInterface
        public void shareWeixin(String setTitle, String desc, String setImageUrl, String clickUrl) {
            checkLogin(new BaseActivity.CheckLoginCallBack() {
                @Override
                public void loginSuccess() {
                    //getShareParam(Wechat.NAME);
                    requestWechatShare();
                }

                @Override
                public void loginFaild() {

                }
            });
        }
        @JavascriptInterface
        public void shareWeixinQuan(String setTitle, String desc, String setImageUrl, String clickUrl) {
            //这里是朋友圈
            checkLogin(new BaseActivity.CheckLoginCallBack() {
                @Override
                public void loginSuccess() {
                    //getShareParam(WechatMoments.NAME);
                    //这里分享朋友圈；
                    reqeustWechatComment();
                }

                @Override
                public void loginFaild() {

                }
            });
        }
        @JavascriptInterface
        public void shareQQ(String setTitle, String desc, String setImageUrl, String clickUrl) {
            //这里是QQ

            checkLogin(new BaseActivity.CheckLoginCallBack() {
                @Override
                public void loginSuccess() {

                    //qq登陆；
                    HashMap<String, String> paramsMap = new HashMap<>();
                    paramsMap.put(ApiInviteFriendContent.FROM_UID,getUserBean().getUserid());
                    paramsMap.put(ApiInviteFriendContent.TYPE,ApiInviteFriendContent.QQ);

                    ApiInviteFriendContent apiInviteFriendContent=new ApiInviteFriendContent(new HttpOnNextListener<ResInviteFriendContent>() {

                        @Override
                        public void onNext(ResInviteFriendContent resInviteFriendContent) {

                            //这里我们要做的一个操作就是;
                             mShareDialog.shareQQ(resInviteFriendContent.getContent(),null);

//                            shareQQ2(resInviteFriendContent.getContent());

                        }

                    },getBaseActivity(),paramsMap);

                    HttpManager.getInstance().doHttpDeal(apiInviteFriendContent);

                }

                @Override
                public void loginFaild() {

                }
            });

        }

        @JavascriptInterface
        public void shareQrcode(String setTitle, String desc, String setImageUrl, String clickUrl) {
            //这里请求微信好友，然后然后生成二维码

            checkLogin(new BaseActivity.CheckLoginCallBack() {
                @Override
                public void loginSuccess() {
                    shareNetQrCode();
                }

                @Override
                public void loginFaild() {

                }
            });
        }
    }



    //这里是分享二维码的逻辑
    private void shareNetQrCode() {

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiInviteFriendContent.FROM_UID,getUserBean().getUserid());
        paramsMap.put(ApiInviteFriendContent.TYPE,ApiInviteFriendContent.WECHAT_FRIEND);

        showDialog();
        ApiInviteFriendContent apiInviteFriendContent=new ApiInviteFriendContent(new HttpOnNextListener<ResInviteFriendContent>() {

            @Override
            public void onNext(final ResInviteFriendContent resInviteFriendContent) {

                //这里分享图片：img表示背景，erwma表示二维码；
                Glide.with(getBaseActivity())
                        .load(resInviteFriendContent.getImg())
                        .into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                //view.setImageDrawable(resource);
                                //这里在合成图片；
                                Bitmap bitmap = QrCodeUtils.generateBitmap(
                                        resInviteFriendContent.getErwema(),
                                        UIUtils.dip2px(getBaseActivity(), 170),
                                        UIUtils.dip2px(getBaseActivity(), 170)
                                );

                                QrcodeShareDialog qrcodeShareDialog = new QrcodeShareDialog(getBaseActivity(), new QrcodeShareDialog.GoReadDialogListener() {
                                    @Override
                                    public void onClickShare() {

                                        //这里要做的一个操作就是分享；

                                        mShareDialog.show();

                                    }
                                },bitmap);
                                qrcodeShareDialog.show();

                                dismissDialog();
                            }
                        });
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                dismissDialog();
            }
        },getBaseActivity(),paramsMap);

        HttpManager.getInstance().doHttpDeal(apiInviteFriendContent);

    }

    /**
     * 分享qq
     */
    public void shareQQ2(String title){

        Platform.ShareParams wechatMoments = new Platform.ShareParams();
        wechatMoments.setText(title);
        wechatMoments.setShareType(Platform.SHARE_TEXT);
        Platform weixin = ShareSDK.getPlatform(QQ.NAME);
        weixin.share(wechatMoments);

    }
    /**
     * 朋友圈的分享；
     */
    private void reqeustWechatComment() {

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiInviteFriendContent.FROM_UID,getUserBean().getUserid());
        paramsMap.put(ApiInviteFriendContent.TYPE,ApiInviteFriendContent.WECHAT_FRIEND_COMMENT);

        ApiInviteFriendContent apiInviteFriendContent=new ApiInviteFriendContent(new HttpOnNextListener<ResInviteFriendContent>() {

            @Override
            public void onNext(final ResInviteFriendContent resInviteFriendContent) {

                Glide.with(getBaseActivity())
                        .load(resInviteFriendContent.getImg())
                        .into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                //view.setImageDrawable(resource);
                                //这里在合成图片；
                                Bitmap shareImgBitmap=QrCodeUtils.createBitmap(
                                        getContext() ,
                                        resource    ,
                                        QrCodeUtils.generateBitmap(
                                                resInviteFriendContent.getErwema()  ,
                                                UIUtils.dip2px(getBaseActivity(),170)   ,
                                                UIUtils.dip2px(getBaseActivity(),170)
                                        )
                                );


                                shareToWeChat(resInviteFriendContent.getContent(),shareImgBitmap);

                                dismissDialog();
                            }
                        });

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                dismissDialog();
            }
        },getBaseActivity(),paramsMap);

        HttpManager.getInstance().doHttpDeal(apiInviteFriendContent);
    }

    public  void shareToWeChat(String msg,Bitmap bitmap) {
        Context context=getBaseActivity();
        try {

            Intent intent = new Intent();
            //分享精确到微信的页面，朋友圈页面，或者选择好友分享页面
            ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
            intent.setComponent(comp);
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.setType("image/*");
//        intent.setType("text/plain");
            //添加Uri图片地址
//        String msg=String.format(getString(R.string.share_content), getString(R.string.app_name), getLatestWeekStatistics() + "");

            intent.putExtra("Kdescription", msg);
            ArrayList<Uri> imageUris = new ArrayList<Uri>();
            File dir = context.getExternalFilesDir(null);
            if (dir == null || dir.getAbsolutePath().equals("")) {
                dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            }
            File pic = new File(dir, "bigbang.jpg");
            pic.deleteOnExit();

            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, new FileOutputStream(pic));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                imageUris.add(Uri.fromFile(pic));
            } else {
                //修复微信在7.0崩溃的问题
                Uri uri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(context.getContentResolver(), pic.getAbsolutePath(), "bigbang.jpg", null));
                imageUris.add(uri);
            }

            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
            ((Activity) context).startActivityForResult(intent, 1000);
        } catch (Throwable e) {
            toast("未检测到微信");
        }
    }
    /**
     * 微信的分享；
     */
    private void requestWechatShare() {


        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiInviteFriendContent.FROM_UID,getUserBean().getUserid());
        paramsMap.put(ApiInviteFriendContent.TYPE,ApiInviteFriendContent.WECHAT_FRIEND);

        showDialog();
        ApiInviteFriendContent apiInviteFriendContent=new ApiInviteFriendContent(new HttpOnNextListener<ResInviteFriendContent>() {

            @Override
            public void onNext(final ResInviteFriendContent resInviteFriendContent) {
                //这里分享图片：img表示背景，erwma表示二维码；
                Glide.with(getBaseActivity())
                        .load(resInviteFriendContent.getImg())
                        .into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                //view.setImageDrawable(resource);
                                //这里在合成图片；
                                Bitmap shareImgBitmap=QrCodeUtils.createBitmap(
                                        getContext() ,
                                        resource,
                                        QrCodeUtils.generateBitmap(
                                                resInviteFriendContent.getErwema(),
                                                UIUtils.dip2px(getBaseActivity(),170),
                                                UIUtils.dip2px(getBaseActivity(),170)
                                        )
                                );
//                                mShareDialog.shareWechatFriend(shareImgBitmap,null);
                                ShareUtils.shareWechatFriend(getContext() , shareImgBitmap , null);
                                dismissDialog();
                            }
                        });
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                dismissDialog();
            }
        },getBaseActivity(),paramsMap);

        HttpManager.getInstance().doHttpDeal(apiInviteFriendContent);

    }

    /**
     * 是否可以返回的操作；
     *
     */
    public boolean canBack(){
       /* if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }*/
        String url = "";
        if(mUrls!=null && !mUrls.isEmpty()){
            url = mUrls.removeLast();
        }

        if(!TextUtils.isEmpty(url)){
            mWebView.loadUrl(url);
            return true;
       }
        return false;
    }

    /**
     * 开始下载
     */
    public void startDownoad(String url){
        if(getBaseActivity() != null && url != null) {
            Intent intent = new Intent(getBaseActivity(), DownloadServiceWithNotifytation.class);
            intent.putExtra(DownloadServiceWithNotifytation.DOWNLOAD_URL,url);
            getBaseActivity().startService(intent);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==FILECHOOSER_RESULTCODE)
        {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            }
            else  if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }

        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {

            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
        return;
    }
}
