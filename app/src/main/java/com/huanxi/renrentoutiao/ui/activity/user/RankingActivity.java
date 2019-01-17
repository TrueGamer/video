package com.huanxi.renrentoutiao.ui.activity.user;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.bean.news.HomeTabBean;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.adapter.HomeViewPagerAdapter;
import com.huanxi.renrentoutiao.ui.fragment.user.IncomeRankingFragment;
import com.huanxi.renrentoutiao.ui.view.indicator.YellowViewPagerIndicator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Dinosa on 2018/1/30.
 */

public class RankingActivity extends BaseActivity {


    @BindView(R.id.appbar)
    AppBarLayout mAppbar;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tb_tab)
    TabLayout mTbTab;

    @BindView(R.id.vp_viewpager)
    ViewPager mVpViewpager;

    @BindView(R.id.vp_indicator)
    MagicIndicator mMagicIndicator;

    private HomeViewPagerAdapter mHomeViewPagerAdapter;
    private List<String> mTitleList;


    @Override
    public int getContentLayout() {
        return R.layout.activity_ranking;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {

        setStatusBarImmersive(null);

        mTitleList = new ArrayList<>();
        mTitleList.add("周排行榜");
        mTitleList.add("月排行榜");

        mVpViewpager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        mTbTab.setupWithViewPager(mVpViewpager);

        mToolbar.setTitle("排行榜");
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.left_white_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initMagicIndicator();
    }

    /**
     * 这里我们初始初始化indicator
     */
    private void initMagicIndicator() {

        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.vp_indicator);
        CommonNavigator commonNavigator7 = new CommonNavigator(this);
        commonNavigator7.setScrollPivotX(0.65f);
        commonNavigator7.setAdapter(new YellowViewPagerIndicator(mTitleList,mVpViewpager));
        magicIndicator.setNavigator(commonNavigator7);
        ViewPagerHelper.bind(magicIndicator, mVpViewpager);
    }

    @Override
    protected void initData() {
        super.initData();
        List<HomeTabBean> mHomeTabBeen = new ArrayList<>();

    }

    public class PageAdapter extends FragmentPagerAdapter {

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return new IncomeRankingFragment();
        }

        @Override
        public int getCount() {
            return mTitleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
           return mTitleList.get(position);
        }
    }
}
