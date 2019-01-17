package com.huanxi.renrentoutiao.ui.dialog.invite;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;

/**
 * Created by Dinosa on 2018/4/16.
 * 这里是单独邀请的基类；
 */

public class BaseShareDialog {

    BaseActivity mBaseActivity;
    protected Dialog mDialog;
    View mShareDialogView;
    protected View mLlWechatComment;
    protected View mLlWechatFriend;
    protected View mLlQQ;
    protected View mLlQrCode;
    protected View mLlSina;

    public BaseShareDialog(BaseActivity baseActivity) {
        mBaseActivity = baseActivity;
        initView();
    }

    private void initView() {

        mDialog = new Dialog(mBaseActivity, R.style.dialog_share);

        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        mShareDialogView = View.inflate(mBaseActivity, R.layout.dialog_invite_friend_share, null);


        //======================================================

        mLlWechatComment = mShareDialogView.findViewById(R.id.ll_wechat_comment_share_icon);
        mLlWechatComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //微信朋友圈的操作；
                onClickWechatComment();
                dismiss();

            }
        });

        mLlWechatFriend = mShareDialogView.findViewById(R.id.ll_wechat_friend_share_icon);
        mLlWechatFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //微信好友的分享的操作；
              onClickWechatFriend();
                dismiss();
            }
        });

        mLlQQ = mShareDialogView.findViewById(R.id.ll_qq_share_icon);
        mLlQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里要做的一个qq分享的操作；
                onClickQQ();
                dismiss();
            }
        });

        mLlQrCode = mShareDialogView.findViewById(R.id.ll_qrcode_share_icon);
        mLlQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickQrCode();
                dismiss();
            }
        });

        ///===============================================================

        mShareDialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mLlSina = mShareDialogView.findViewById(R.id.ll_sina_share_icon);
        mLlSina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSina();
                dismiss();
            }
        });


        mDialog.setContentView(mShareDialogView);

        WindowManager systemService = (WindowManager) mBaseActivity.getSystemService(Context.WINDOW_SERVICE);
        int width = systemService.getDefaultDisplay().getWidth();
        //将对话框的宽度设置为屏幕的宽度；
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width=width;

        window.setAttributes(attributes);

    }


    protected void onClickQrCode() {

    }

    protected void onClickQQ() {

    }

    protected void onClickWechatFriend() {

    }

    protected void onClickWechatComment() {

    }

    protected void onClickSina() {

    }

    protected void show(){
        mDialog.show();
    }


    protected void dismiss(){
        mDialog.dismiss();
    }

}
