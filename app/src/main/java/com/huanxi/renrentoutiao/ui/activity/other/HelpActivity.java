package com.huanxi.renrentoutiao.ui.activity.other;

import android.os.Bundle;
import android.view.View;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.ui.fragment.HelpFragment;

/**
 * Created by Dinosa on 2018/2/8.
 *
 *  这样的格式，我们到时候进一步进行一个抽取；
 */

public class HelpActivity extends BaseTitleActivity {


    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        super.initView(rootView, savedInstanceState);
        setBackText("");
        setTitle("帮助反馈");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container,new HelpFragment())
                .commitAllowingStateLoss();
    }
}
