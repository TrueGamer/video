package com.huanxi.renrentoutiao.ui.activity.other;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantUrl;
import com.huanxi.renrentoutiao.model.bean.JPushBroadcastBean;
import com.huanxi.renrentoutiao.net.api.ApiCheckVersion;
import com.huanxi.renrentoutiao.net.bean.ResCheckVersion;
import com.huanxi.renrentoutiao.presenter.ads.gdt.GdtNativeAds;
import com.huanxi.renrentoutiao.ui.activity.MainTab;
import com.huanxi.renrentoutiao.ui.activity.WebHelperActivity;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.activity.news.NewsDetailActivity2;
import com.huanxi.renrentoutiao.ui.activity.user.IntergralShopActivity;
import com.huanxi.renrentoutiao.ui.activity.user.UserTaskActivity;
import com.huanxi.renrentoutiao.ui.activity.video.VideoItemDetailActivity;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.video.VideoListBean;
import com.huanxi.renrentoutiao.ui.dialog.UpdateDialog;
import com.huanxi.renrentoutiao.ui.fragment.apprentice.ApprenticeFragment;
import com.huanxi.renrentoutiao.ui.fragment.news.HomeFragment;
import com.huanxi.renrentoutiao.ui.fragment.picture.PictureFragment;
import com.huanxi.renrentoutiao.ui.fragment.video.NewVideoFragment;
import com.huanxi.renrentoutiao.ui.media.MediaNewFragment;
import com.huanxi.renrentoutiao.ui.media.channel.MediaChannelView;
import com.huanxi.renrentoutiao.ui.view.FragmentTabHost;
import com.huanxi.renrentoutiao.utils.SharedPreferencesUtils;
import com.huanxi.renrentoutiao.utils.SystemUtils;
import com.huanxi.renrentoutiao.utils.UIUtils;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.zhxu.library.download.DownInfo;
import com.zhxu.library.download.HttpDownManager;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpDownOnNextListener;
import com.zhxu.library.listener.HttpOnNextListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.huanxi.renrentoutiao.utils.SystemUtils.getAppMetaData;


public class MainActivity extends BaseActivity {


    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;
    @BindView(android.R.id.tabhost)
    FragmentTabHost mTabhost;

    private GdtNativeAds mGdtNativeAds;

    public static final int HOME = 0;
    public static final int VIDEO = 1;
    public static final int Picture = 2;
    public static final int APPRENTICE = 3;
    public static final int TASK = 4;
    public static final int MINE = 5;

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        setStatusBarImmersive(null);
    }

    @Override
    protected void initData() {
        initTabHost();

        initPageckageName();

        checkVersion();

        mGdtNativeAds = GdtNativeAds.newInstance();


        //置入一个不设防的VmPolicy,7.0 读写文件是由问题的；
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }


        //这里我们要做的一个操作就是取出来数据；
        JPushBroadcastBean jPushBroadcastBean = ((JPushBroadcastBean) getIntent().getSerializableExtra(JPUSH_BROAD_CAST_BEAN));
        jumpNewActivity(jPushBroadcastBean);
    }

    private void jumpNewActivity(JPushBroadcastBean jPushBroadcastBean) {

        if (jPushBroadcastBean == null) {
            return;
        }
        //这里我们要做的一个新的操作；

        Context context = this;

        if (jPushBroadcastBean != null && jPushBroadcastBean.getType() != null) {
            if (jPushBroadcastBean.getType().equals("1")) {

                //这里我们是需要跳转到新闻的详情页面；
                Intent detailIntent = NewsDetailActivity2.getIntent(context, jPushBroadcastBean.getUrl()
                        , jPushBroadcastBean.getUrlmd5() , "xiaohua" , 1);
                context.startActivity(detailIntent);

            }
            if (jPushBroadcastBean.getType().equals("2")) {
                //这里是视频的详情页面；

                VideoListBean videoBean = new VideoListBean(null, jPushBroadcastBean.getUrlmd5(), jPushBroadcastBean.getTitle(), null, null, null, jPushBroadcastBean.getVideo_id(), null, null);
                Intent videoIntent = VideoItemDetailActivity.getIntent(context, videoBean,null);
                context.startActivity(videoIntent);

            } else if (jPushBroadcastBean.getType().equals("3")) {
                //这里跳转到任务中心；
                Intent taskIntent = new Intent(context, UserTaskActivity.class);
                context.startActivity(taskIntent);

            } else if (jPushBroadcastBean.getType().equals("4")) {
                //邀请好友
                Intent inviteFriendActivity = new Intent(context, InviteFriendActivityNew.class);
                context.startActivity(inviteFriendActivity);

            } else if (jPushBroadcastBean.getType().equals("5")) {
                //兑换提现的页面；
                Intent intergral = new Intent(context, IntergralShopActivity.class);
                context.startActivity(intergral);

            } else if (jPushBroadcastBean.getType().equals("6")) {
                //这里是h5逻辑
                Intent webIntent = WebHelperActivity.getIntent(context, jPushBroadcastBean.getUrl(), jPushBroadcastBean.getTitle(), false);
                context.startActivity(webIntent);
            }
        }

    }

    private void initPageckageName() {
        //这里我们要做的一个操作就是获取所有的包名
        Observable.just("1").map(new Func1<String, List<String>>() {
            @Override
            public List<String> call(String s) {
                //这里我们做对应的操作；
                return getAllPackagename();
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> aBoolean) {

                        if (aBoolean != null && aBoolean.size() > 0) {
                            //这里表示获取到的了所有的包名；
                            getMyApplication().setAllPackageName(aBoolean);
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }


    private ArrayList<String> getAllPackagename() {

        ArrayList<String> strings = new ArrayList<>();

        PackageManager pm = getPackageManager();
        List<PackageInfo> list = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo packageInfo : list) {
            //获取到设备上已经安装的应用的名字,即在AndriodMainfest中的app_name。
            String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            //获取到应用所在包的名字,即在AndriodMainfest中的package的值。
            String packageName = packageInfo.packageName;
            strings.add(packageName);
            System.out.println("allPackName:" + packageName);
        }
        return strings;
    }


    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("MainActivity: onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("MainActivity: onDestory");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("MainActivity: onCreate");

        getWindow().setBackgroundDrawable(null);

    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("MainActivity:onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("MainActivity: onStop");
    }

    public NativeExpressADView getGDTADView() {

        return mGdtNativeAds.getAdView();
    }

    public void removeADView(NativeExpressADView view) {
        mGdtNativeAds.removeADView(view);
    }


    private void initTabHost() {
        //==============================
        mTabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                //如果没登陆显示登陆loginActivity  提示用户登录
                if (!isLogin()) {
                    if (String.valueOf(R.string.main_tab_name_video).equals(tabId)) {

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);

                    } else if (String.valueOf(R.string.main_tab_name_home).equals(tabId)) {

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);

                    } else if (String.valueOf(R.string.main_tab_name_user).equals(tabId)) {

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else if (String.valueOf(R.string.main_tab_name_apprentice).equals(tabId)) {

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        mTabhost.setup(MainActivity.this, supportFragmentManager, R.id.fl_container);

        MainTab[] values = MainTab.values();
        boolean tabSpecs = SharedPreferencesUtils.getInstance(getApplicationContext()).getBoolean(ConstantUrl.IS_SHOW);
        for (MainTab value : values) {

            TabHost.TabSpec tabSpec = mTabhost.newTabSpec(getString(value.getDescResource()));
            View view = LayoutInflater.from(this).inflate(R.layout.tab_indicator_main, null);
            if (value.getIdx() == 3 && !tabSpecs) {
                view.setVisibility(View.GONE);
            }
            if(value.getIdx() == 2 && tabSpecs) {
                view.setVisibility(View.GONE);
            }
            TextView tab_tag = (TextView) view.findViewById(R.id.tv_tab_text);
            tab_tag.setText(value.getDescResource());
            ImageView tab_icon = (ImageView) view.findViewById(R.id.tv_tab_icon);
            tab_icon.setImageResource(value.getDrawableResource());
            tabSpec.setIndicator(view);
            //这里我们将做对Account里面的内容做修改；
            mTabhost.addTab(tabSpec, value.getFragmentClazz(), null);
        }
        //取消分割线，设置背景为白色，选中第一个
        mTabhost.getTabWidget().setDividerDrawable(null);
        mTabhost.setBackgroundColor(Color.WHITE);
        mTabhost.setCurrentTab(HOME);

        //要执行的一个操作就是点击我的就跳转到登陆的逻辑；

        mTabhost.getTabWidget().getChildAt(HOME).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里我们做一下判断操作；
                if (mTabhost.getCurrentTab() == HOME) {
                    //这里表示当前是在新闻页面的时候；
                    if(getNewsFragment() != null) {
                        try{
                            getVideoFragment().refresh();
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                } else {
                    mTabhost.setCurrentTab(HOME);
                }
            }
        });


        mTabhost.getTabWidget().getChildAt(VIDEO).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里我们做一下判断操作；
                if(mTabhost != null) {
                    if (mTabhost.getCurrentTab() == VIDEO) {
                        //这里表示当前是在新闻页面的时候；
                        getVideoFragment().refresh();
                    } else {
                        mTabhost.setCurrentTab(VIDEO);
                    }
                }
            }
        });

        if(tabSpecs) {
            //要执行的一个操作就点击调转到收徒的页面
            mTabhost.getTabWidget().getChildAt(APPRENTICE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //这里我们做一下判断操作；
                    checkLogin(new CheckLoginCallBack() {
                        @Override
                        public void loginSuccess() {
                            Intent webIntent = WebHelperActivity.getIntent(
                                    MainActivity.this,
                                    ConstantUrl.JKD_WEB_URL + getUserBean().getUserid()+"&time="+System.currentTimeMillis(),
                                    getString(R.string.main_tab_name_apprentice),
                                    false);
                            startActivity(webIntent);
                        }

                        @Override
                        public void loginFaild() {
                            //失败我就显示
                            mTabhost.setCurrentTab(mTabhost.getCurrentTab());
                        }
                    });
                }
            });
        } else {
            mTabhost.getTabWidget().getChildAt(Picture).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //这里我们做一下判断操作；
                    if (mTabhost.getCurrentTab() == Picture) {
                        //这里表示当前是在新闻页面的时候；
//                        getPictureFragment().refresh();
//                        getMediaoFragment();
                    } else {
                        mTabhost.setCurrentTab(Picture);
                    }
                }
            });
        }

        //要执行的一个操作就是点击我的就跳转到登陆的逻辑；
        mTabhost.getTabWidget().getChildAt(TASK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里我们做一下判断操作；

                checkLogin(new CheckLoginCallBack() {
                    @Override
                    public void loginSuccess() {
                        startActivity(new Intent(MainActivity.this, UserTaskActivity.class));
                    }

                    @Override
                    public void loginFaild() {
                        //失败我就显示
                        mTabhost.setCurrentTab(mTabhost.getCurrentTab());
                    }
                });
            }
        });




    }

    public boolean isNeedUpdate = false;


    /**
     * 请求服务器，来检查本地的版本和服务器是否一样；
     */
    private void checkVersion() {
        //1、获取本地版本信息；
        //2、请求服务器是否更新；
        //3、弹出对话框显示新版本特性；
        //4、启动Services;

        final String versionCode = SystemUtils.getVersionCode(this);

        HashMap<String, String> paramsMap = new HashMap<>();

        System.out.println("sout:channel: " + getAppMetaData(this));

        paramsMap.put(ApiCheckVersion.VERSION_CODE, versionCode);
        paramsMap.put(ApiCheckVersion.APP_CHANNEL, getAppMetaData(MainActivity.this) + "");

        ApiCheckVersion apiCheckVersion = new ApiCheckVersion(new HttpOnNextListener<ResCheckVersion>() {
            @Override
            public void onNext(final ResCheckVersion version) {

                if (version != null && version.getUrl() != null) {
                    isNeedUpdate = true;
                    //这里表示有更新；
                    final UpdateDialog updateDialog = new UpdateDialog();
                    updateDialog.show(MainActivity.this, version.getContent(), new UpdateDialog.OnClickUpdateListener() {
                        @Override
                        public void onClickUpdate() {
                            //这里我们是需要更新的操作；
                            updateDialog.dismiss();
                            //这里要做的一个操作就是去根据这个URl下载内容；
                            download(version.getUrl());
                        }
                    });
                } else {

                    if (!isLogin()) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);

                if (!isLogin()) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        }, this, paramsMap);
        HttpManager.getInstance().doHttpDeal(apiCheckVersion);
    }

    public FragmentTabHost getTabhost() {
        return mTabhost;
    }

    /**
     * 这里我们是获取视频的Fragment
     *
     * @return
     */
    public NewVideoFragment getVideoFragment() {

        return ((NewVideoFragment) getSupportFragmentManager().findFragmentByTag("视频"));

    }

    /**
     * 这里我们是获取美图的Fragment
     *
     * @return
     */
    public PictureFragment getPictureFragment() {

        return ((PictureFragment) getSupportFragmentManager().findFragmentByTag("美图"));

    }


    /**
     * 这里我们是获取收徒的Fragment
     *
     * @return
     */
    public ApprenticeFragment getApprenticeFragment() {

        return ((ApprenticeFragment) getSupportFragmentManager().findFragmentByTag("收徒狂欢"));

    }

    /**
     * 这里我们是获取新闻的Fragment
     *
     * @return
     */
    public HomeFragment getNewsFragment() {
        return ((HomeFragment) getSupportFragmentManager().findFragmentByTag("看点"));
    }

    /**
     * 获取头条号Fragment
     * @return
     */
    public MediaNewFragment getMediaoFragment() {
        return ((MediaNewFragment) getSupportFragmentManager().findFragmentByTag("美图"));
    }

    public static final String TAB_INDEX = "tabIndex";

    //启动模式的intent；
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            int tabIndex = intent.getIntExtra(TAB_INDEX, HOME);
            mTabhost.setCurrentTab(tabIndex);
        }
    }

    /**
     * 新闻到任务界面；
     */
    public void newsStartTaskActivity() {
        startActivity(new Intent(this, UserTaskActivity.class));
    }

    /**
     * 视频到任务界面；
     */
    public void videoStartTaskActivity() {
        startActivity(new Intent(this, UserTaskActivity.class));
    }


    /**
     * 下载的地址
     *
     * @param url
     */
    public void download(String url) {

        //下载的地址；
        final String storeUrl = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/renrentoutiao" + "/renrentoutiao.apk";

        //这里我们对是否有这个文件进行一个校验的操作；

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("下载进度");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        DownInfo downInfo = new DownInfo(url, new HttpDownOnNextListener() {

            boolean isSuccess = false;

            @Override
            public void onNext(Object o) {
                isSuccess = true;
            }


            @Override
            public void onStart() {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                isSuccess = false;
                //这里显示弹窗下载失败；
            }

            @Override
            public void onComplete() {
                UIUtils.toast(MainActivity.this, "下载完成");


                //跳转到安装的界面；
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Uri data = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //这里的话，我们在7.0是需要使用FileProvider;
                    data = FileProvider.getUriForFile(MainActivity.this, "com.huanxi.renrentoutiao.fileprovider", new File(storeUrl));
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } else {
                    data = Uri.fromFile(new File(storeUrl));
                }

                intent.setDataAndType(data,
                        "application/vnd.android.package-archive");
                startActivity(intent);
            }

            @Override
            public void updateProgress(long readLength, long countLength) {

                //System.out.println("progress: "+readLength+" total:"+countLength);
                int curProgress = ((int) readLength);
                int totalProgress = ((int) countLength);

                progressDialog.setProgress(curProgress);
                progressDialog.setMax(totalProgress);
            }
        });
        downInfo.setSavePath(storeUrl);
        HttpDownManager.getInstance().startDown(downInfo);
    }


    public long mLastClickTime = 0;
    public long mDuractionTime = 500;

    @Override
    public void onBackPressed() {

        //点击返回的时候，如果veticalDrawerlayout是打开的状态，让其关
        if (getNewsFragment() != null && !getNewsFragment().isHidden()) {
            if (getNewsFragment().isMenuOpen()) {
                getNewsFragment().closeMenu();
                return;
            }
        }

        if (getVideoFragment() != null && !getVideoFragment().isHidden()) {

            if (getVideoFragment().isMenuOpen()) {
                getVideoFragment().closeMenu();
                return;
            }
        }
        //这里我们执行双击退出的一个操作；
        if (System.currentTimeMillis() - mLastClickTime < mDuractionTime) {
            //双击返回退出，防止误触；
            super.onBackPressed();
        }
        mLastClickTime = System.currentTimeMillis();
    }


    public static final String JPUSH_BROAD_CAST_BEAN = "jpushBean";

    /**
     * 获取一个
     *
     * @return
     */
    public static Intent getIntent(Context context, Bundle bean) {
        //这表示是否是从通知栏进来的；
        Intent intent = new Intent(context, MainActivity.class);

        intent.putExtra(JPUSH_BROAD_CAST_BEAN, bean);

        return intent;
    }


}
