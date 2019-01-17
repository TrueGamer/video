package com.huanxi.renrentoutiao.ui.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;

/**
 * Created by Dinosa on 2018/2/8.
 * 这里是一个输入内容的一个对话框
 */
public class InputDialog {

    public  AlertDialog mAlertDialog=null;



    public  void show (Context context, String defaultInput, OnDialogClickListener onDialogClickListener,String title,String inputTitle){

        mOnDialogClickListener=onDialogClickListener;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //初始化自定义布局；
        View view = View.inflate(context, R.layout.dialog_bind_email, null);
        final EditText editText = (EditText) view.findViewById(R.id.et_input);
        final TextView tvInputTitle = (TextView) view.findViewById(R.id.tv_inputTitle);

        final View ivClose =  view.findViewById(R.id.iv_close);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                    ivClose.setVisibility(View.INVISIBLE);
                }else{
                    ivClose.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        if (defaultInput != null) {
            editText.setText(defaultInput);
            editText.setSelection(defaultInput.length());
        }
        tvInputTitle.setText(title);
        //设置一些参数；
        builder.setView(view);
        builder.setCancelable(false);

        view.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里是点击确定的操作；
                //这里我们做一些验证的操作；
                if (mOnDialogClickListener != null) {
                    //这里是获取dialogClickSure点击sure
                    mOnDialogClickListener.onDialogClickSure(editText.getText().toString());
                }
            }
        });

        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里是点击取消的操作；
                mAlertDialog.dismiss();
            }
        });

        mAlertDialog = builder.create();

        mAlertDialog.show();
    }

    public OnDialogClickListener mOnDialogClickListener;

    public interface OnDialogClickListener{
        public void onDialogClickSure(String inputContent);
    }

    public void dismiss(){
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();;
        }
    }

    public OnDialogClickListener getOnDialogClickListener() {
        return mOnDialogClickListener;
    }

    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        mOnDialogClickListener = onDialogClickListener;
    }
}
