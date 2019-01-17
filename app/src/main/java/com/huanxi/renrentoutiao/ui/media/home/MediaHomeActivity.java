package com.huanxi.renrentoutiao.ui.media.home;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.TextUtils;
import android.util.Log;

import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantUrl;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.model.bean.media.MediaProfileBean;
import com.huanxi.renrentoutiao.net.api.mediao.IMobileMediaApi;
import com.huanxi.renrentoutiao.net.api.mediao.INewsApi;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.adapter.BasePagerAdapter;
import com.huanxi.renrentoutiao.ui.media.MediaUserBean;
import com.huanxi.renrentoutiao.ui.media.home.tab.MediaArticleFragment;
import com.huanxi.renrentoutiao.ui.media.home.tab.MediaVideoFragment;
import com.huanxi.renrentoutiao.ui.media.home.tab.MediaWendaFragment;
import com.huanxi.renrentoutiao.utils.ErrorAction;
import com.huanxi.renrentoutiao.utils.RetrofitFactory;
import com.huanxi.renrentoutiao.utils.SettingUtil;
import com.zhxu.library.RxRetrofitApp;
import com.zhxu.library.http.ParamsInterceptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Meiji on 2017/6/29.
 */

public class MediaHomeActivity extends FragmentActivity {

    private static final String ARG_MEDIAID = "mediaId";
    private String mediaName = null;
//    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ContentLoadingProgressBar progressBar;

    public static void launch(String MediaId , String userId) {
        MyApplication.mContext.startActivity(new Intent(MyApplication.mContext, MediaHomeActivity.class)
                .putExtra(ARG_MEDIAID, MediaId)
                .putExtra("userId" , userId)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_home);

        initView();
        initData();
    }

    private void initView() {
//        toolbar = findViewById(R.id.toolbar);
//        toolbar.setBackgroundColor(SettingUtil.getInstance().getColor());

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout.setBackgroundColor(SettingUtil.getInstance().getColor());
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        progressBar = (ContentLoadingProgressBar) findViewById(R.id.pb_progress);
        int color = SettingUtil.getInstance().getColor();
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        progressBar.show();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (position == 0) {
//                    if (slidrInterface != null) {
//                        slidrInterface.unlock();
//                    }
//                } else {
//                    if (slidrInterface != null) {
//                        slidrInterface.lock();
//                    }
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        this.mediaName = intent.getStringExtra(ARG_MEDIAID);
        String userId = intent.getStringExtra("userId");
        if (TextUtils.isEmpty(mediaName)) {
            onError();
            return;
        }

//        RetrofitFactory.getRetrofit().create(IMobileMediaApi.class)
//                .getMediaProfile(mediaId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
////                .as(this.bindAutoDispose())
//                .subscribe(bean -> {
//                    String name = bean.getData().getName();
////                    initToolBar(toolbar, true, name);
//                    List<MediaProfileBean.DataBean.TopTabBean> topTab = bean.getData().getTop_tab();
//                    if (null != topTab && topTab.size() < 0) {
//                        onError();
//                        return;
//                    }
//                    initTabLayout(bean.getData());
//                }, throwable -> {
//                    onError();
//                    ErrorAction.print(throwable);
//                });

//        initTabLayout(bean.getData());

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(String.format(ConstantUrl.MediaDetailURL , mediaName , userId ))
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("info" , "MediaDetailURL="+result);
                try {
                    JSONObject obj = new JSONObject(result);
                    int code = obj.optInt("code");
                    if(code == 200) {
                        JSONObject jsonObject = obj.optJSONObject("data");
                        String jianjie = jsonObject.optString("jianjie");
                        int newUserId = jsonObject.optInt("newUserId");
                        String newUserName = jsonObject.optString("newUserName");
                        String newUserUrl = jsonObject.optString("newUserUrl");
                        String subscription = jsonObject.optString("subscription");

                        MediaUserBean userBean = new MediaUserBean();
                        userBean.setJiangjie(jianjie);
                        userBean.setNewUserId(newUserId);
                        userBean.setNewUserName(newUserName);
                        userBean.setNewUserUrl(newUserUrl);
                        userBean.setSubscription(subscription);
                        userBean.setUserId(userId);

                        List<MediaProfileBean.DataBean.TopTabBean> topTab = new ArrayList<>();
                        MediaProfileBean.DataBean.TopTabBean topTabBean = new MediaProfileBean.DataBean.TopTabBean();
                        topTabBean.setShow_name("文章");
                        topTabBean.setType("all");

                        topTab.add(topTabBean);

                        MediaHomeActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initTabLayout(userBean , topTab);
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initTabLayout(MediaUserBean dataBean , List<MediaProfileBean.DataBean.TopTabBean> topTab) {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        for (MediaProfileBean.DataBean.TopTabBean bean : topTab) {
            if (bean.getType().equals("all")) {
                fragmentList.add(MediaArticleFragment.newInstance(dataBean));
                titleList.add(bean.getShow_name());
            }
            if (bean.getType().equals("video")) {
                fragmentList.add(MediaVideoFragment.newInstance(mediaName));
                titleList.add(bean.getShow_name());
            }
            if (bean.getType().equals("wenda")) {
                fragmentList.add(MediaWendaFragment.newInstance(dataBean.getNewUserId() + ""));
                titleList.add(bean.getShow_name());
            }
        }
        BasePagerAdapter pagerAdapter = new BasePagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(topTab.size());
        progressBar.hide();
    }

    private void onError() {
        progressBar.hide();
        Snackbar.make(progressBar, getString(R.string.error), Snackbar.LENGTH_INDEFINITE).show();
    }
}
