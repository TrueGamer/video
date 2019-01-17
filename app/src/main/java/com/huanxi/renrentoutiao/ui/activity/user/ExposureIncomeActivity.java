package com.huanxi.renrentoutiao.ui.activity.user;

import android.os.Bundle;
import android.view.View;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.ui.fragment.ExposureIncomeFragment;

/**
 * Created by Dinosa on 2018/1/27.
 * 这里是晒收入的activity
 *
 */

public class ExposureIncomeActivity extends BaseTitleActivity {



    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        setStatusBarImmersive(null);

        setTitle("晒收入");
        setBackText("");

        ExposureIncomeFragment exposureIncomeFragment = new ExposureIncomeFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container,exposureIncomeFragment)
                .commitAllowingStateLoss();

    }
}
