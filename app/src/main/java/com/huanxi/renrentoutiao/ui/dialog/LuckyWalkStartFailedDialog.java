package com.huanxi.renrentoutiao.ui.dialog;

import android.content.Context;

import com.huanxi.renrentoutiao.R;

import butterknife.OnClick;


/**
 * Created by ZD on 2017/7/4.
 */

public class LuckyWalkStartFailedDialog extends BaseDialog {

    private final OnClickListener mListener;

    public LuckyWalkStartFailedDialog(Context context, OnClickListener listener) {
        super(context);
        mListener = listener;
        initView();
    }

    void initView() {
        setContentView(R.layout.dialog_luck_walk_start_failed);
    }


    @OnClick(R.id.tvConfirm)
    void up() {
        dismiss();

        if (mListener != null) {
            mListener.onClickConfirm();
        }
    }

    @OnClick(R.id.tvCancel)
    void next() {
        dismiss();

        if (mListener != null) {
            mListener.onClickCancel();
        }
    }



    public interface OnClickListener{
        public void onClickConfirm();
        public void onClickCancel();
    }
}
