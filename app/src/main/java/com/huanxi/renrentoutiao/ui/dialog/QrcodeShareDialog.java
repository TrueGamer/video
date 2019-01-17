package com.huanxi.renrentoutiao.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;

/**
 * Created by Dinosa on 2018/4/9.
 *
 * 这里是二维码分享的对话框；
 */

public class QrcodeShareDialog {

    private final GoReadDialogListener mListener;
    private final Bitmap mQrCodeBitmap;
    public Dialog alertDialog=null;

    Context context;

    public QrcodeShareDialog(Context context, GoReadDialogListener listener, Bitmap qrCodeBitmap) {
        this.context = context;
        mListener = listener;
        mQrCodeBitmap = qrCodeBitmap;
        init();
    }

    /**
     */
    public void init(){

        alertDialog = new Dialog(context, R.style.NoBackGroundDialog);
        //设置宽度
        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
        params.width = (int) (context.getResources().getDisplayMetrics().widthPixels );
        params.height = (int) (context.getResources().getDisplayMetrics().widthPixels );
        params.gravity= Gravity.TOP;
        alertDialog.getWindow().setAttributes(params);


        View contentView = View.inflate(context, getDialogLayout(), null);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        contentView.setLayoutParams(layoutParams);

        TextView tvContentDesc = (TextView) contentView.findViewById(R.id.tv_desc);
        //邀请好友打开微信\n点击右上角的"+"\n使用“扫一扫”扫描下方二维码
        String contentDesc="<html>邀请好友打开<font color='"+context.getResources().getColor(R.color.base_color_orange)+"'><b>微信</b></font><br/>点击右上角的“<font color='"+context.getResources().getColor(R.color.base_color_orange)+"'><b>+</b></font>”<br/>使用“<font color='"+context.getResources().getColor(R.color.base_color_orange)+"'><b>扫一扫</b></font>”扫描下方二维码  </html>";
        tvContentDesc.setText(Html.fromHtml(contentDesc));

        contentView.findViewById(R.id.tv_detail_desc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里是阅读的逻辑
                if (mListener != null) {
                    mListener.onClickShare();
                }
            }
        });

        ((ImageView) contentView.findViewById(R.id.tv_read_button)).setImageBitmap(mQrCodeBitmap);


        //关闭的逻辑
        contentView.findViewById(R.id.iv_go_read_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setContentView(contentView);
    }


    public void show(){
        if (alertDialog != null) {
            alertDialog.show();

            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            alertDialog.getWindow().setGravity(Gravity.TOP);
        }
    }

    public void dismiss(){
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public  int getDialogLayout(){

        return R.layout.dialog_qrcode_share;
    }


    /**
     * 阅读的对话框
     */
    public interface GoReadDialogListener{
        public void onClickShare();
    }

    public boolean isShowing(){
        return alertDialog.isShowing();
    }

}
