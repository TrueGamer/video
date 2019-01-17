package com.huanxi.renrentoutiao.ui.activity.other;

import android.os.Bundle;
import android.view.View;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.ui.fragment.ChannelFragment;

/**
 * Created by gdhuo on 2019/1/9.
 */
public class ChannelActivity extends BaseTitleActivity {

    private ChannelFragment fragment;

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        setStatusBarImmersive(null);
        setBackText("");
        setTitle("渠道号");
        setRightText("确认");
        fragment = new ChannelFragment();
        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container,fragment)
                .commitAllowingStateLoss();
    }

    @Override
    public void onClickRight() {
        fragment.onClickRight();
    }
}
