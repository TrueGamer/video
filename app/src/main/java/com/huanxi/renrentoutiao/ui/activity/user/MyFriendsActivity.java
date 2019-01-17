package com.huanxi.renrentoutiao.ui.activity.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.ui.fragment.user.InviteFriendFragment;
import com.huanxi.renrentoutiao.ui.fragment.user.MyFriendFragment;
import com.huanxi.renrentoutiao.ui.view.indicator.YellowViewPagerIndicator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Dinosa on 2018/1/20.
 */

public class MyFriendsActivity extends BaseTitleActivity {

    @BindView(R.id.tl_layout)
    TabLayout mTlLayout;

    @BindView(R.id.vp_viewpager)
    ViewPager mVpViewpager;
    private ArrayList<String> mTitleList;

    @Override
    public View getContentView(LayoutInflater inflater, ViewGroup container) {
        View contentView = super.getContentView(inflater, container);
        contentView.setBackground(getResources().getDrawable(R.drawable.shape_profit_detail));
        return contentView;
    }

    @Override
    public int getBodyLayoutId() {
        return R.layout.activity_my_friends;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {

        setStatusBarImmersive(null);
        setTitle("我的好友");
        setBackText("");

        mTvTitle.setTextColor(Color.WHITE);

        mTvBack.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tv_back_white_new,0,0,0);

        getTitleBar().setBackgroundColor(Color.TRANSPARENT);

        mStatusBar.setBackground(new ColorDrawable(Color.parseColor("#0099ff")));

        mTitleList = new ArrayList<>();
        mTitleList.add("邀请好友");
        mTitleList.add("贡献金币");

        initMagicIndicator();

        mVpViewpager.setAdapter(new FriendsPagerAdapter(getSupportFragmentManager()));
        mTlLayout.setupWithViewPager(mVpViewpager);
    }

    /**
     * 这里我们初始初始化indicator
     */
    private void initMagicIndicator() {

        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.vp_indicator);
        magicIndicator.setBackgroundColor(Color.TRANSPARENT);
        CommonNavigator commonNavigator7 = new CommonNavigator(this);
        commonNavigator7.setScrollPivotX(0.65f);
        commonNavigator7.setAdapter(new YellowViewPagerIndicator(mTitleList,mVpViewpager));
        magicIndicator.setNavigator(commonNavigator7);
        ViewPagerHelper.bind(magicIndicator, mVpViewpager);
    }


    @Override
    protected void initData() {
        super.initData();
        //这里要做的一个操作
        boolean isInvite = getIntent().getBooleanExtra(IS_INVITE, false);
        if(isInvite){
            //这里表示邀请好友的页面；
            mVpViewpager.setCurrentItem(0);
        }else{
            //这里表示贡献金币；
            mVpViewpager.setCurrentItem(1);
        }
    }

    public class FriendsPagerAdapter extends FragmentPagerAdapter{

        public FriendsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position==0) {
                return new InviteFriendFragment();
            }else{
                return new MyFriendFragment();
            }
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

    public static String IS_INVITE="isInvite";

    /**
     * 这里是获取贡献金币还是邀请好友；
     * @return
     */
    public static Intent getIntent(Context context,boolean isInvite){
        Intent intent = new Intent(context, MyFriendsActivity.class);
        intent.putExtra(IS_INVITE,isInvite);
        return intent;
    }
}
