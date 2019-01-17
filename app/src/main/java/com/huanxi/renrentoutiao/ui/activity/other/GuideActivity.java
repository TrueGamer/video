package com.huanxi.renrentoutiao.ui.activity.other;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.ApiSplashAds;
import com.huanxi.renrentoutiao.net.bean.ResSplashAds;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.view.indicator.ScaleCircleNavigator;
import com.huanxi.renrentoutiao.utils.SharedPreferencesUtils;
import com.huanxi.renrentoutiao.utils.SystemUtils;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class GuideActivity extends BaseActivity {


    @BindView(R.id.vp_view_pager)
    ViewPager mVpViewPager;
    @BindView(R.id.circle_indicator)
    MagicIndicator mCircleIndicator;
    @BindView(R.id.tv_go)
    TextView mTvGo;

  /*  int[] mImgs = {
        R.drawable.bg_splash_qq_1,
        R.drawable.bg_splash_qq_2,
        R.drawable.bg_splash_qq_3
    };*/
    int[] mImgs = {
        R.drawable.splash_new_1,
        R.drawable.splash_new_2,
        R.drawable.splash_new_3,
        R.drawable.splash_new_4
    };

    @Override
    public int getContentLayout() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        setStatusBarImmersive(null);

        doGetAds();
        //这里我们动态获取所有的权限问题；

        String [] permissions={
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION
        };

        RxPermissions rxPermissions = new RxPermissions(this); // where this is an Activity instance
        rxPermissions
                .request(permissions)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            //这里表示获取权限成功
                            initViewPager();
                           // initIndicator();
                        }else{
                            // 如果用户没有授权，那么应该说明意图，引导用户去设置里面授权。
                            toast("应用缺少必要的权限！请点击'权限'，打开所需要的权限。");
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:" + getPackageName()));
                            startActivity(intent);
                            finish();
                        }
                    }
                });


    }

    /**
     * 这里初始化ViewPager
     */
    private void initViewPager() {
        mVpViewPager.setAdapter(new ImageViewPagerAdapter());
        mVpViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==mImgs.length-1){
                    //这里表示；
                    mTvGo.setVisibility(View.VISIBLE);
                }else{
                    mTvGo.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.tv_go)
    public void onClickTvGo(){
        //成功
        startActivity(new Intent(this,MainActivity.class));
        SharedPreferencesUtils.getInstance(this).setShowGuide(this, SystemUtils.getVersionCode(this)+"");
        //这里
        finish();
    }

    private void initIndicator() {
        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(this);
        scaleCircleNavigator.setCircleCount(mImgs.length);
        scaleCircleNavigator.setNormalCircleColor(Color.LTGRAY);
        scaleCircleNavigator.setSelectedCircleColor(Color.DKGRAY);
        scaleCircleNavigator.setCircleClickListener(new ScaleCircleNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                mVpViewPager.setCurrentItem(index);
            }
        });
        mCircleIndicator.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(mCircleIndicator, mVpViewPager);
    }

    public class ImageViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImgs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View object1 = (View) object;
            container.removeView(object1);
            object1 = null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = new ImageView(GuideActivity.this);
            ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
            layoutParams.height = ViewPager.LayoutParams.MATCH_PARENT;
            layoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
            imageView.setImageResource(mImgs[position]);
            imageView.setLayoutParams(layoutParams);
            container.addView(imageView);
            return imageView;
        }
    }



    /**
     * 获取所有的广告信息；
     */
    private void doGetAds() {

        ApiSplashAds apiSplashAds=new ApiSplashAds(new HttpOnNextListener<ResSplashAds>() {

            @Override
            public void onNext(ResSplashAds resSplashAds) {

                ((MyApplication) getApplication()) .setResAds(resSplashAds);
            }

        },this);

        HttpManager.getInstance().doHttpDeal(apiSplashAds);
    }
}
