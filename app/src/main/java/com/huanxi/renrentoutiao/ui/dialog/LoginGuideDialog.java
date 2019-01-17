package com.huanxi.renrentoutiao.ui.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.huanxi.renrentoutiao.R;

/**
 * Created by Dinosa on 2018/3/1.
 * 引导去的登陆的对话框
 */

public class LoginGuideDialog {


    static AlertDialog alertDialog=null;

    public static void show (Context context, final OnClickLoginListener listener){

        //红包的对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.NoBackGroundDialog);
        View view = View.inflate(context, R.layout.dialog_login_guide, null);


        View ivClose =  view.findViewById(R.id.iv_close);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
            }
        });


        view.findViewById(R.id.iv_login_guide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickLogin();
                }
            }
        });

        builder.setView(view);
        alertDialog= builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }


    public OnClickLoginListener mListener;


    /**
     *
     */
    public interface OnClickLoginListener{

        public void onClickLogin();

    }

    public void dismiss(){
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

}
