package com.huanxi.renrentoutiao.ui.activity.other;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.ui.fragment.user.InviteFriendFragmentNew;

/**
 * 这里是邀请好友的一个新的页面；
 */
public class InviteFriendActivityNew extends BaseTitleActivity {

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        super.initView(rootView, savedInstanceState);

        setTitle("邀请好友");
        setBackText("");
        setRightText("我的好友");
        mTvRight.setTextColor(Color.BLACK);
        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InviteFriendActivityNew.this, FriendRankingActivity.class);
                startActivity(intent);
            }
        });

        InviteFriendFragmentNew inviteFriendFragmentNew = new InviteFriendFragmentNew();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container,inviteFriendFragmentNew)
                .commitAllowingStateLoss();
    }
}
