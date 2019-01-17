package com.huanxi.renrentoutiao.ui.activity.other;

import android.os.Bundle;
import android.view.View;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.ui.fragment.ContackKeFuFragment;

/**
 * Created by Dinosa on 2018/3/28.
 */

public class ContactKeFuActivity extends BaseTitleActivity {


    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        setStatusBarImmersive(null);
        setBackText("");
        setTitle("联系客服");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container,new ContackKeFuFragment())
                .commitAllowingStateLoss();
    }
}
