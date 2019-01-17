package com.huanxi.renrentoutiao.ui.fragment.user;

import android.view.View;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseFragment;

/**
 * Created by Dinosa on 2018/1/20.
 * 这里是通过获取我的好友贡献金币的列表的；
 */

public class MyFriendFragment extends BaseFragment {


    @Override
    protected View getContentView() {
        return inflatLayout(R.layout.fragment_my_friend);
    }

    @Override
    protected void initData() {
        super.initData();


        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_friend_list_container,new MyFriendListFragment())
                .commitAllowingStateLoss();

    }
}
