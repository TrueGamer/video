package com.huanxi.renrentoutiao.ui.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.utils.UIUtils;

/**
 * Created by Dinosa on 2018/2/2.
 * 评论的对话框；
 */

public class CommentDialog {

    private AlertDialog mAlertDialog;

    public CommentDialog() {

    }

    public void show(final Activity context, final OnDialogClickListener mOnClickListener){

        //红包的对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.inputDialog);
        View view = View.inflate(context, R.layout.dialog_comment_dialog,null);

        ViewGroup.LayoutParams viewGroup = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(viewGroup);

        final EditText commentContent = (EditText) view.findViewById(R.id.et_conmment);
        view.findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String trim = commentContent.getText().toString().trim();
                if(TextUtils.isEmpty(trim)){
                    UIUtils.toast(context,"内容不能为空!");
                    return;
                }
                if (mOnClickListener != null) {
                    mOnClickListener.onClickSure(trim);
                }
                mAlertDialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        builder.setView(view);
        mAlertDialog = builder.create();

        mAlertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0)
                    mAlertDialog.cancel();
                return false;
            }
        });
        mAlertDialog.show();

        WindowManager.LayoutParams layoutParams = mAlertDialog.getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM|Gravity.LEFT;
        layoutParams.width=context.getResources().getDisplayMetrics().widthPixels;

        mAlertDialog.getWindow().setAttributes(layoutParams);
    }
    

    public interface OnDialogClickListener{
        public void onClickSure(String Content);
    }
}
