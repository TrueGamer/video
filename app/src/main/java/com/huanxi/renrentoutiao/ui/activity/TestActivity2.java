package com.huanxi.renrentoutiao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.db.ta.sdk.NonStandardTm;
import com.db.ta.sdk.NsTmListener;
import com.google.gson.Gson;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.net.bean.ResTaCustomAdBean;
import com.huanxi.renrentoutiao.utils.ImageUtils;
import com.huanxi.renrentoutiao.utils.UIUtils;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class TestActivity2 extends AppCompatActivity{


    public static final String TAG=TestActivity2.class.getSimpleName();

    private View mButton;
    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);


        Log.e(TAG,"onCreate");

        mButton = findViewById(R.id.btn_rotate);

        mImageView = ((ImageView) findViewById(R.id.iv_ad_img));
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里分享到朋友圈
                //shareWechatComment();

                mNonStandardTm.loadAd(ConstantAd.TuiAAD.CUSTOM_AD);
            }
        });

        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里分享到微信；
                //shareWecharFriend();
            showShare("","","");
            }
        });
        /*500*250*/
        mNonStandardTm = new NonStandardTm(this);
        mNonStandardTm.loadAd(ConstantAd.TuiAAD.CUSTOM_AD);
        mNonStandardTm.setAdListener(new NsTmListener() {
            @Override
            public void onReceiveAd(String s) {
                System.out.println("233233: "+s);

                Gson gson = new Gson();
                ResTaCustomAdBean resTaCustomAdBean = gson.fromJson(s, ResTaCustomAdBean.class);
                ImageUtils.loadImage(TestActivity2.this,resTaCustomAdBean.getImg_url(),mImageView);
            }

            @Override
            public void onFailedToReceiveAd() {
                System.out.println("获取失败2333");
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNonStandardTm.adClicked();
                mNonStandardTm.adExposed();
            }
        });

        //test731，123456

        findViewById(R.id.btn_test4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里执行相应的luo

                Intent intent = new Intent(TestActivity2.this, TestActivity2.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"onCreate");
    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"onResume");
    }

    NonStandardTm mNonStandardTm;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mNonStandardTm != null) {
            mNonStandardTm.destroy();
        }
    }

    /**
     * 分享到朋友圈；
     */
    private void shareWechatComment() {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText("分享到朋友圈");
        sp.setImagePath("");
        sp.setShareType(Platform.SHARE_TEXT);
        Platform weibo = ShareSDK.getPlatform(WechatMoments.NAME);
        weibo.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                UIUtils.toast(TestActivity2.this,"omComplete");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                UIUtils.toast(TestActivity2.this,"onError");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                UIUtils.toast(TestActivity2.this,"onCancel");
            }
        }); // 设置分享事件回调
        // 执行图文分享
        weibo.share(sp);

    }


    /**
     * 分享到微信
     */
    private void shareWecharFriend() {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText("分享");
        sp.setImagePath("");
        sp.setShareType(Platform.SHARE_WEBPAGE);
        Platform weibo = ShareSDK.getPlatform(Wechat.NAME);
        weibo.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                UIUtils.toast(TestActivity2.this,"omComplete");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                UIUtils.toast(TestActivity2.this,"onError");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                UIUtils.toast(TestActivity2.this,"onCancel");
            }
        }); // 设置分享事件回调
        // 执行图文分享
        weibo.share(sp);
    }


    private void showShare(String title,String shareContent,String url) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("分享");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://www.baidu.com");
        // comment是我对这条分享的评论，仅在人人网使用
        //oks.setComment("我是测试评论文本");


        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        // 启动分享GUI
        oks.show(this);
    }




}
