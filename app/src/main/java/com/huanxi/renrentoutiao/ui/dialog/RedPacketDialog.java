package com.huanxi.renrentoutiao.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.db.ta.sdk.NonStandardTm;
import com.db.ta.sdk.NsTmListener;
import com.db.ta.sdk.TmActivity;
import com.google.gson.Gson;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.net.bean.ResTaCustomAdBean;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.utils.ImageUtils;

/**
 * Created by Dinosa on 2018/1/30.
 */

public class RedPacketDialog  {

    public static AlertDialog alertDialog=null;
    public static NonStandardTm mNonStandardTm=null;

    public static DialogInterface.OnDismissListener sOnDismissListener;


    public static void show (final Context context, String goldNumber, final DialogInterface.OnDismissListener mListener){

        //红包的对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.NoBackGroundDialog);
        View view = View.inflate(context, R.layout.dialog_red_packet, null);
        TextView titleView = (TextView) view.findViewById(R.id.tv_dialog_title);

        String title="<big>恭喜您</big><br/>获得 <big><font color='"+context.getResources().getColor(R.color.dialog_red_pack_number_text_color)+"'>"+goldNumber+"</font></big> 金币";

        titleView.setText(Html.fromHtml(title));
        /*420*240 自定义广告大小*/

        final ImageView bannerView = (ImageView)view.findViewById(R.id.ll_ad);


        if(((BaseActivity) context).isHasAds()){
            mNonStandardTm = new NonStandardTm(context);
            mNonStandardTm.loadAd(ConstantAd.TuiAAD.CUSTOM_AD);
            mNonStandardTm.setAdListener(new NsTmListener() {
                @Override
                public void onReceiveAd(String s) {

                    BaseActivity activity = (BaseActivity) context;
                    if (activity == null || activity.isFinishing()) {
                        return;
                    }

                    Gson gson = new Gson();
                    final ResTaCustomAdBean resTaCustomAdBean = gson.fromJson(s, ResTaCustomAdBean.class);

                    //这里是加载图片的问题；
                    try {
                        ImageUtils.loadImage(context,resTaCustomAdBean.getImg_url(),bannerView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (mNonStandardTm != null) {
                        mNonStandardTm.adExposed();
                    }

                    bannerView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mNonStandardTm != null) {
                                mNonStandardTm.adClicked();
                            }
                            //这里还是需要做对应的操作；
                            TmActivity.a(context,resTaCustomAdBean.getClick_url());
                        }
                    });
                }

                @Override
                public void onFailedToReceiveAd() {
                    System.out.println("获取失败2333");
                }
            });
        }
        bannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

      /*  view.findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                    //这里是点击了取消的操作；
                }
            }
        });
*/
        view.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                    //这里是点击了确定的操作；
                }
            }
        });

        builder.setView(view);
        alertDialog= builder.create();
        if (mListener != null) {
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    mListener.onDismiss(dialog);
                    if (mNonStandardTm != null) {
                        mNonStandardTm.destroy();
                        mNonStandardTm=null;
                    }
                }
            });
        }

        alertDialog.show();
    }

    public static void show (Context context, String goldNumber){
        show(context,goldNumber,null);
    }

}
