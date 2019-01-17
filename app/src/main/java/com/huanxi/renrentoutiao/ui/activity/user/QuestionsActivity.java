package com.huanxi.renrentoutiao.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.ui.fragment.user.QuestionsFragmentNew;

/**
 * Created by Dinosa on 2018/1/18.
 * 这里是常见问题的Activity；
 */

public class QuestionsActivity extends BaseTitleActivity {

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        setStatusBarImmersive(null);
        setBackText("");
        setTitle("常见问题");

        setRightText("赚金币");
        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(QuestionsActivity.this, UserTaskActivity.class);
                startActivity(intent);

            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container,new QuestionsFragmentNew())
                .commitAllowingStateLoss();
    }
}
