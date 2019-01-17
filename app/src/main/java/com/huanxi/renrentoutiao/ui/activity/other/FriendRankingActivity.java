package com.huanxi.renrentoutiao.ui.activity.other;

import android.os.Bundle;
import android.view.View;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.ui.fragment.user.FriendRankingFragment;

/**
 * Created by Dinosa on 2018/4/9.
 */

public class FriendRankingActivity extends BaseTitleActivity {



    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        super.initView(rootView, savedInstanceState);
        setTitle("我的好友");
        setBackText("");

        FriendRankingFragment friendRankingActivity = new FriendRankingFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container,friendRankingActivity)
                .commitAllowingStateLoss();
    }
}
