package com.huanxi.renrentoutiao.ui.dialog.invite;

import android.view.View;

import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.utils.ShareUtils;

/**
 * Created by Dinosa on 2018/4/23.
 */

public class ShareContentAndVideoDialog extends BaseShareDialog {

    private String mUrlMd5;
    private String mContentType;

    public ShareContentAndVideoDialog(BaseActivity baseActivity) {
        super(baseActivity);
        mLlQQ.setVisibility(View.VISIBLE);
        mLlQrCode.setVisibility(View.GONE);
        mLlSina.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onClickWechatComment() {
//        super.onClickWechatComment();
//
//        ShareUtils.requestContentWechatCommentShare(mBaseActivity,mUrlMd5,mContentType );
    }

    @Override
    protected void onClickWechatFriend() {
//        super.onClickWechatFriend();
//        ShareUtils.requestContentWechatShare(mBaseActivity,mUrlMd5,mContentType );
    }

    @Override
    protected void onClickQQ() {
//        super.onClickQQ();
//        ShareUtils.requestContentQQShare(mBaseActivity ,mUrlMd5 , mContentType);
    }

    protected void onClickSina() {
//        super.onClickSina();
//        ShareUtils.requestContentSinaShare(mBaseActivity , mUrlMd5 , mContentType);
    }

    /**
     * 分享的操作；
     * @param urlMd5
     * @param contentType 文章还是视频的
     */
    public void show(String urlMd5,String contentType){
        mUrlMd5 = urlMd5;
        mContentType = contentType;
        show();
    }
}
