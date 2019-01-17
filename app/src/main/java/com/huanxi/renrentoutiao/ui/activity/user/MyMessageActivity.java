package com.huanxi.renrentoutiao.ui.activity.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.ui.fragment.user.MyMessageFragment;

import butterknife.OnClick;

public class MyMessageActivity extends BaseTitleActivity {


    private MyMessageFragment mMyMessageFragment;

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        setStatusBarImmersive(null);
        setBackText("");
        setTitle("我的消息");

        mMyMessageFragment = new MyMessageFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container,mMyMessageFragment)
                .commitAllowingStateLoss();
    }


    @OnClick(R.id.tv_right_option)
    public void onClickClear(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("是否要清除所有的消息？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里调用Fragment里面的方法
                mMyMessageFragment.clear();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
}
