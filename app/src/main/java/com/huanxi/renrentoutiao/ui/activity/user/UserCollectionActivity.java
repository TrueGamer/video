package com.huanxi.renrentoutiao.ui.activity.user;

import android.os.Bundle;
import android.view.View;

import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleAndTabActivity;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseFragment;
import com.huanxi.renrentoutiao.ui.fragment.user.collection.UserNewsCollectionFragment;
import com.huanxi.renrentoutiao.ui.fragment.user.collection.UserVideoCollectionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户的收藏模块
 */
public class UserCollectionActivity extends BaseTitleAndTabActivity {


    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        super.initView(rootView,savedInstanceState);
        setStatusBarImmersive(null);
        setTitle("我的收藏");
    }

    @Override
    public List<String> getIndicatorTitle() {

        List<String> mTitleList = new ArrayList<>();

        mTitleList.add("文章");
        mTitleList.add("视频");
        return mTitleList;
    }

    @Override
    public List<BaseFragment> getFragments() {

        ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new UserNewsCollectionFragment());
        fragments.add(new UserVideoCollectionFragment());
        return fragments;
    }

}
