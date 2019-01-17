package com.huanxi.renrentoutiao.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.huanxi.renrentoutiao.R;

/**
 * Created by zd on 2016/11/8.
 */

public class LoadingDialog extends Dialog{
    public static LoadingDialog showDialog(Context context, String message) {
        LoadingDialog dialog = new LoadingDialog(context,R.style.loadingDialog);
        dialog.setContent(message);
        dialog.show();
        return dialog;
    }

    public LoadingDialog(Context context) {
        super(context, R.style.loadingDialog);
        initViews();
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        initViews();
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initViews();
    }

    TextView tvContent;
    SpinKitView spin_kit;

    void initViews() {
        setContentView(R.layout.dialog_view_loading);
        tvContent = (TextView) findViewById(R.id.tvContent);
        spin_kit = (SpinKitView) findViewById(R.id.spin_kit);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    public void hideSpinKit(boolean val) {
        if (val) {
            spin_kit.setVisibility(View.GONE);
        } else {
            spin_kit.setVisibility(View.VISIBLE);
        }
    }

    public void setContent(String content) {
        if (TextUtils.isEmpty(content)) {
            tvContent.setVisibility(View.GONE);
        } else {
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(content);
        }
    }

    public void dismiss(long delayMills) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, delayMills);
    }
}
