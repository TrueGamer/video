package com.huanxi.renrentoutiao.ui.view.indicator;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.huanxi.renrentoutiao.ui.view.ColorFlipPagerTitleView;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.List;

/**
 * Created by Dinosa on 2018/1/30.
 * 这里是黄色的indicatory
 */

public class YellowViewPagerIndicator extends CommonNavigatorAdapter {

    private final ViewPager mViewPager;
    public List<String> mTitleList;
    private int mNormalColor;
    private int mSelectColor;

    public YellowViewPagerIndicator(List<String> mTitleList, ViewPager viewPager) {
        this.mTitleList = mTitleList;
        mViewPager = viewPager;
        mNormalColor = Color.parseColor("#99ffffff");
        mSelectColor = Color.parseColor("#ffffff");
    }

    public YellowViewPagerIndicator(List<String> mTitleList, ViewPager viewPager, int normalColor, int selectColor) {
        this.mTitleList = mTitleList;
        mViewPager = viewPager;
        mNormalColor = normalColor;
        mSelectColor = selectColor;
    }

    @Override
    public int getCount() {
        return mTitleList == null ? 0 : mTitleList.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {

        SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
        simplePagerTitleView.setText(mTitleList.get(index));
        simplePagerTitleView.setNormalColor(mNormalColor);
        simplePagerTitleView.setSelectedColor(mSelectColor);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.rightMargin=UIUtil.dip2px(context,50);
        simplePagerTitleView.setLayoutParams(layoutParams);

        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(index);
            }
        });

        return simplePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        indicator.setLineHeight(UIUtil.dip2px(context, 3));
        indicator.setLineWidth(UIUtil.dip2px(context, 20));
        indicator.setRoundRadius(UIUtil.dip2px(context, 3));
        indicator.setStartInterpolator(new AccelerateInterpolator());
        indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
        indicator.setColors(Color.parseColor("#ffd200"));
        indicator.setYOffset(UIUtil.dip2px(context, 5));
        return indicator;
    }
}

