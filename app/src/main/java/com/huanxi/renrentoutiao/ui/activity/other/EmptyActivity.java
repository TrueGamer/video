package com.huanxi.renrentoutiao.ui.activity.other;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;

public class EmptyActivity extends BaseTitleActivity {

    @Override
    public int getBodyLayoutId() {
        return R.layout.activity_empty;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        super.initView(rootView, savedInstanceState);
        setBackText("");
        setTitle(getIntent().getStringExtra("title"));
    }

    public static Intent getIntent(Context context, String title){
        Intent intent = new Intent(context,EmptyActivity.class);
        intent.putExtra("title",title);
        return intent;
    }
}
