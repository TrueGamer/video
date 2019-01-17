package com.huanxi.renrentoutiao.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;

/**
 * 计时说明页面
 */
public class TimeCountTipsActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_count_layout);

        TextView tv_back = (TextView) findViewById(R.id.tv_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);

        tv_back.setText("");
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_title.setText("计时说明");
    }
}
