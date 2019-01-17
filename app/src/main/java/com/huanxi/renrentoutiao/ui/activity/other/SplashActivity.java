package com.huanxi.renrentoutiao.ui.activity.other;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.MainThread;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.globle.ConstantUrl;
import com.huanxi.renrentoutiao.model.bean.AdsBean;
import com.huanxi.renrentoutiao.model.bean.JPushBroadcastBean;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.ApiAds;
import com.huanxi.renrentoutiao.net.api.ApiCheckService;
import com.huanxi.renrentoutiao.net.api.ApiSplashAds;
import com.huanxi.renrentoutiao.net.api.news.ApiNewAdLog;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.net.bean.ResSplashAds;
import com.huanxi.renrentoutiao.ui.activity.TaskWebHelperActivity;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseFragment;
import com.huanxi.renrentoutiao.ui.fragment.main.SplashGDTFragment;
import com.huanxi.renrentoutiao.ui.fragment.main.SplashH5AdFragment;
import com.huanxi.renrentoutiao.ui.fragment.main.SplashTuiAFragment;
import com.huanxi.renrentoutiao.utils.InfoUtil;
import com.huanxi.renrentoutiao.utils.SharedPreferencesUtils;
import com.huanxi.renrentoutiao.utils.SystemUtils;
import com.huanxi.renrentoutiao.utils.TTAdManagerHolder;
import com.hubcloud.adhubsdk.AdListener;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zhxu.library.exception.HttpTimeException;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Dinosa on 2018/1/4.
 */

public class SplashActivity extends BaseActivity {


    @BindView(R.id.fl_ad_container)
    FrameLayout mFrameLayout;

    @BindView(R.id.ll_bottomS)
    LinearLayout ll_bottomS;

    private Bundle mExtras;
    private String sign;
    private String goldGame;
    private String redPacket;

    private Context mContext;
    private TTAdNative mTTAdNative;
    private static final int AD_TIME_OUT = 3000;
    private boolean canJumpImmediately;

    @Override
    public int getContentLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String[] permissions = {
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA
        };

        mContext = SplashActivity.this;
        mExtras = getIntent().getExtras();
        mTTAdNative = TTAdManagerHolder.getInstance(getApplicationContext()).createAdNative(this);

        RxPermissions rxPermissions = new RxPermissions(this); // where this is an Activity instance
        rxPermissions
                .request(permissions)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            //这里表示获取权限成功
                            if (isFirst()) {
                                getMyApplication().clearUser();
                                getAdsIS();
                                //第一次进入新手引导页；
                                startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                                finish();
                            } else {
                                getAdsIS();
                            }
                        } else {
                            // 如果用户没有授权，那么应该说明意图，引导用户去设置里面授权。
                            toast("应用缺少必要的权限！请点击'权限'，打开所需要的权限。");
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:" + getPackageName()));
                            startActivity(intent);
                            finish();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        System.out.println("出异常了");
                    }
                });

        sign = getIntent().getStringExtra("sign");
        goldGame = getIntent().getStringExtra("goldGame");
        redPacket = getIntent().getStringExtra("redPacket");

    }

    public Bundle getExtras() {
        return mExtras;
    }


    /**
     * 获取所有的广告信息；
     */
    private void doGetAds() {

        ApiSplashAds apiSplashAds = new ApiSplashAds(new HttpOnNextListener<ResSplashAds>() {

            @Override
            public void onNext(ResSplashAds resSplashAds) {

                //第二次直接进入广告；
                Log.i("info" , "ResSplashAds="+resSplashAds.toString());
                BaseFragment baseFragment = null;
//                String splashType = SharedPreferencesUtils.getInstance(mContext).getSplashType(mContext);
//                Log.i("info" , "splashType="+splashType);
//                if(ResSplashAds.SplashBean.TYPE_BD.equals(splashType)) {
//                    SharedPreferencesUtils.getInstance(mContext).setSplashType(mContext , ResSplashAds.SplashBean.TYPE_GDT);
//                    loadBDAd();
//                } else {
//                    if(ResSplashAds.SplashBean.TYPE_GDT.equals(splashType)) {
                        // 广点通
                        baseFragment = new SplashGDTFragment();
                        SharedPreferencesUtils.getInstance(mContext).setSplashType(mContext , ResSplashAds.SplashBean.TYPE_TA);
//                    } else if(ResSplashAds.SplashBean.TYPE_CUSTOM.equals(splashType)) {
//                        // 普通广告
//                        baseFragment = SplashH5AdFragment.getSplashH5Fragment(SplashActivity.this, resSplashAds.getSplash().getImgurl(), resSplashAds.getSplash().getUrl());
//                    } else {
//                        //这里我们要执行的是推啊的广告；
//                        baseFragment = new SplashTuiAFragment();
//                        SharedPreferencesUtils.getInstance(mContext).setSplashType(mContext , ResSplashAds.SplashBean.TYPE_GDT);
//                    }

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_ad_container, baseFragment)
                            .commitAllowingStateLoss();
//                }

                ((MyApplication) getApplication()).setResAds(resSplashAds);
//                addAdHubAd();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //这里我们就等待几秒中跳转到
                Observable.timer(3, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                //startActivity(MainActivity.getIntent(SplashActivity.this,getExtras()));
                                startActivity();
                                finish();
                            }
                        });
            }
        }, this);
        HttpManager.getInstance().doHttpDeal(apiSplashAds);
    }

    private void addAdHubAd() {
        //以下AdHub广告
        AdListener listener = new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.i("SplashActivity", "onAdLoaded");
            }

            @Override
            public void onAdShown() {
                Log.i("SplashActivity", "onAdShown");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.i("SplashActivity", "onAdFailedToLoad "+errorCode);
                jumpMain();
            }

            @Override
            public void onAdClosed() {
                Log.i("SplashActivity", "onAdClosed");
                adhubAdEnd();
                jumpWhenCanClick(); // 跳转至您的应用主界面
            }

            @Override
            public void onAdClicked() {
                Log.i("SplashActivity", "onAdClick");
                // 设置开屏可接受点击时，该回调可用
                adhubAdStart();
            }
        };
        com.hubcloud.adhubsdk.SplashAd splashAd = new com.hubcloud.adhubsdk.SplashAd(mContext, mFrameLayout, listener, ConstantAd.ADHUBAD.FPLASH_AD);
        splashAd.setCloseButtonPadding(10, 20, 10, 10);
    }

    private void adhubAdStart() {
        InfoUtil.getNetIp(new InfoUtil.NetCallback() {
            @Override
            public void onSuccess(String value) {
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put(ApiNewAdLog.TYPE,"2");
                paramsMap.put(ApiNewAdLog.SERVER_NUMBER,SharedPreferencesUtils.getInstance(getApplicationContext()).getString(SharedPreferencesUtils.CHANNEL));
                paramsMap.put(ApiNewAdLog.MAC_ADDRESS,InfoUtil.getMacAddress());
                paramsMap.put(ApiNewAdLog.PHONE_BRAND,Build.BRAND);
                paramsMap.put(ApiNewAdLog.PHONE_MODULE,Build.MODEL);
                paramsMap.put(ApiNewAdLog.SYSTEM_VERSION,Build.VERSION.RELEASE);
                paramsMap.put(ApiNewAdLog.IP,value);
                paramsMap.put(ApiNewAdLog.AD_CHANNEL_NUM,ApiNewAdLog.AD_CHANNEL_ADHUB);
                paramsMap.put(ApiNewAdLog.AD_ID,ConstantAd.ADHUBAD.FPLASH_AD);
//                paramsMap.put(ApiNewAdLog.NEWS_ID,mMd5Url);

                ApiNewAdLog apiStartReadIssure = new ApiNewAdLog(new HttpOnNextListener<String>() {

                    @Override
                    public void onNext(String str) {
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                },SplashActivity.this,paramsMap);
                HttpManager.getInstance().doHttpDeal(apiStartReadIssure);
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void adhubAdEnd() {
        InfoUtil.getNetIp(new InfoUtil.NetCallback() {
            @Override
            public void onSuccess(String value) {
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put(ApiNewAdLog.TYPE,"3");
                paramsMap.put(ApiNewAdLog.SERVER_NUMBER,SharedPreferencesUtils.getInstance(getApplicationContext()).getString(SharedPreferencesUtils.CHANNEL));
                paramsMap.put(ApiNewAdLog.MAC_ADDRESS,InfoUtil.getMacAddress());
                paramsMap.put(ApiNewAdLog.PHONE_BRAND,Build.BRAND);
                paramsMap.put(ApiNewAdLog.PHONE_MODULE,Build.MODEL);
                paramsMap.put(ApiNewAdLog.SYSTEM_VERSION,Build.VERSION.RELEASE);
                paramsMap.put(ApiNewAdLog.IP,value);
                paramsMap.put(ApiNewAdLog.AD_CHANNEL_NUM,ApiNewAdLog.AD_CHANNEL_ADHUB);
                paramsMap.put(ApiNewAdLog.AD_ID,ConstantAd.ADHUBAD.FPLASH_AD);
//                paramsMap.put(ApiNewAdLog.NEWS_ID,mMd5Url);

                ApiNewAdLog apiStartReadIssure = new ApiNewAdLog(new HttpOnNextListener<String>() {

                    @Override
                    public void onNext(String str) {
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                },SplashActivity.this,paramsMap);
                HttpManager.getInstance().doHttpDeal(apiStartReadIssure);
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void jumpWhenCanClick() {
        Log.d("SplashActivity", "canJumpImmediately:" + canJumpImmediately);
        if (canJumpImmediately) {
            jumpMain();
        } else {
            canJumpImmediately = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("SplashActivity", "onPause:" + canJumpImmediately);
        canJumpImmediately = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SplashActivity", "onPause:" + canJumpImmediately);
        if (canJumpImmediately) {
            jumpWhenCanClick();
        }
        canJumpImmediately = true;
    }

    private void getAdsIS() {

        ApiAds apiAds = new ApiAds(new HttpOnNextListener<AdsBean>() {
            @Override
            public void onNext(AdsBean aBoolean)
            {
                Log.i("info" , "aBoolean="+aBoolean.isAds());
                SharedPreferencesUtils.getInstance(getApplication()).writeBoolean(ConstantUrl.IS_SHOW, aBoolean.isAds());
                doGetAds();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                doGetAds();
            }
        }, this);
        HttpManager.getInstance().doHttpDeal(apiAds);
    }

    /**
     * 开屏页一定要禁止用户对返回按钮的控制，否则将可能导致用户手动退出了App而广告无法正常曝光和计费
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 这里我们判断是否是最新版本的第一次进入；
     *
     * @return
     */
    public boolean isFirst() {
        String showGuide = SharedPreferencesUtils.getInstance(SplashActivity.this).isShowGuide(SplashActivity.this);
        return !SystemUtils.getVersionCode(this).equals(showGuide);
    }

    /**
     * 开启一个activity,进行一个跳转的操作；
     */
    public void startActivity() {

        if("sign".equals(sign)) {
            setResult(121);
            finish();
        } else  if ("goldGame".equals(goldGame)) {
            Intent intent = new Intent(SplashActivity.this, LuckyWalkActivity.class);
            startActivity(intent);
            finish();
        } else  if ("redPacket".equals(redPacket)) {
            setResult(122);
            finish();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.JPUSH_BROAD_CAST_BEAN, getJPushBroadcastBeanFromIntent());

            startActivity(intent);
        }
    }

    /**
     * 这里从intent访问JPushBroadcast内容；
     *
     * @return
     */
    public JPushBroadcastBean getJPushBroadcastBeanFromIntent() {
        return ((JPushBroadcastBean) getIntent().getSerializableExtra(MainActivity.JPUSH_BROAD_CAST_BEAN));
    }

    /**
     * 加载穿山甲开屏广告
     */
    private void loadSplashAd() {
        //开屏广告参数
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(ConstantAd.CSJAD.APP_ID)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1840)
                .build();
        //调用开屏广告异步请求接口
        mTTAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            @MainThread
            public void onError(int code, String message) {
                Log.i("info", "code="+code+",message="+message);
                jumpMain();
            }

            @Override
            @MainThread
            public void onTimeout() {
                jumpMain();
            }

            @Override
            @MainThread
            public void onSplashAdLoad(TTSplashAd ad) {
                Log.i("info", "开屏广告请求成功");
                if (ad == null) {
                    return;
                }
                //获取SplashView
                View view = ad.getSplashView();
                mFrameLayout.removeAllViews();
                //把SplashView 添加到ViewGroup中
                mFrameLayout.addView(view);
                //设置不开启开屏广告倒计时功能以及不显示跳过按钮
                //ad.setNotAllowSdkCountdown();

                //设置SplashView的交互监听器
                ad.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        Log.d("info", "onAdClicked");
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        Log.d("info", "onAdShow");
                    }

                    @Override
                    public void onAdSkip() {
                        Log.d("info", "onAdSkip");
                        jumpMain();
                    }

                    @Override
                    public void onAdTimeOver() {
                        Log.d("info", "onAdTimeOver");
                        jumpMain();
                    }
                });
            }
        }, AD_TIME_OUT);
    }

    /**
     * 加载百度开屏广告
     */
    private void loadBDAd() {
        // 设置视频广告最大缓存占用空间(15MB~100MB)，单位 MB
        SplashAd.setMaxVideoCacheCapacityMb(30);
        SplashAdListener listener = new SplashAdListener() {
            @Override
            public void onAdDismissed() {
                Log.i("RSplashActivity", "onAdDismissed");
                jumpMain();
            }

            @Override
            public void onAdFailed(String arg0) {
                Log.i("RSplashActivity", "onAdFailed="+arg0);
                jumpMain();
            }

            @Override
            public void onAdPresent() {
                Log.i("RSplashActivity", "onAdPresent");
            }

            @Override
            public void onAdClick() {
                Log.i("RSplashActivity", "onAdClick");
                // 设置开屏可接受点击时，该回调可用
            }
        };
        String adPlaceId = ConstantAd.BAIDUAD.SPLASH_AD; // 重要：请填上您的广告位ID，代码位错误会导致无法请求到广告
        // 如果开屏需要支持vr,needRequestVRAd(true)
//        SplashAd.needRequestVRAd(true);
        new SplashAd(this, mFrameLayout, listener, adPlaceId, true);
    }

    private void jumpMain() {
        startActivity();
        SplashActivity.this.finish();
    }

}
