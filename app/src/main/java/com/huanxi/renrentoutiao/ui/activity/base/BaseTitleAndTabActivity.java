package com.huanxi.renrentoutiao.ui.activity.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseFragment;
import com.huanxi.renrentoutiao.ui.view.indicator.YellowViewPagerIndicator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Dinosa on 2018/2/3.
 *
 *  这里是title和一个Tab的一个Activity的一个基类；
 */

public abstract class BaseTitleAndTabActivity extends BaseTitleActivity {



    @BindView(R.id.vp_viewpager)
    public ViewPager mVpViewpager;

    @BindView(R.id.vp_indicator)
    public MagicIndicator mMagicIndicator;

    private List<String> mTitleList;
    protected DefaultPagerAdapter mDefaultPagerAdapter;

    @Override
    public int getBodyLayoutId() {
        return R.layout.activity_base_title_and_tab;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        setStatusBarImmersive(null);
        setBackText("");

        mTitleList = getIndicatorTitle();
        mDefaultPagerAdapter = new DefaultPagerAdapter(getSupportFragmentManager(),getFragments());
        mVpViewpager.setAdapter(mDefaultPagerAdapter);
        initMagicIndicator();

    }

    /**
     * 这里我们初始初始化indicator
     */
    private void initMagicIndicator() {

        CommonNavigator commonNavigator7 = new CommonNavigator(this);
        commonNavigator7.setScrollPivotX(0.65f);
        commonNavigator7.setAdapter(new YellowViewPagerIndicator(mTitleList,mVpViewpager,getResources().getColor(R.color.text_gray_c3c3c3),getResources().getColor(R.color.text_light_black)));
        commonNavigator7.setAdjustMode(true);
        mMagicIndicator.setNavigator(commonNavigator7);

        ViewPagerHelper.bind(mMagicIndicator, mVpViewpager);
    }




    public class DefaultPagerAdapter extends FragmentPagerAdapter {

        private final List<BaseFragment> mBaseFragments;

        public DefaultPagerAdapter(FragmentManager fm, List<BaseFragment> baseFragments) {
            super(fm);
            mBaseFragments = baseFragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mBaseFragments.get(position);
        }

        @Override
        public int getCount() {
            return mBaseFragments.size();
        }

    }

    /**
     * 这里是获取indicator的title
     * @return
     */
    public abstract List<String> getIndicatorTitle();

    /**
     * 这里是获取indicator的对应的Fragment;
     * @return
     */
    public abstract List<BaseFragment> getFragments();

}
