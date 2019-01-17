package com.huanxi.renrentoutiao.ui.activity;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class TestThreeActivity extends BaseActivity {


    @BindView(R.id.appbar)
    AppBarLayout mAppbar;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tb_tab)
    TabLayout mTbTab;

    @BindView(R.id.vp_viewpager)
    ViewPager mVpViewpager;

    private HomeViewPagerAdapter mHomeViewPagerAdapter;


    @Override
    public int getContentLayout() {
        return R.layout.activity_test_three;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {

        setStatusBarImmersive(null);
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
    }

    @Override
    protected void initData() {
        super.initData();
        List<HomeTabBean> mHomeTabBeen = new ArrayList<>();


    }

    public class PageAdapter extends FragmentPagerAdapter{

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return new IncomeRankingFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position%2==0){
                return "周排行榜";
            }
            return "月排行榜";
        }
    }
}
