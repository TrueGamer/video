package com.huanxi.renrentoutiao.ui.fragment.user;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.share.ApiInviteFriendContent;
import com.huanxi.renrentoutiao.net.api.user.ApiInviteFriendDesc;
import com.huanxi.renrentoutiao.net.api.user.ApiUserShare;
import com.huanxi.renrentoutiao.net.bean.ResInviteFriendContent;
import com.huanxi.renrentoutiao.net.bean.ResInviteFriendDesc;
import com.huanxi.renrentoutiao.net.bean.ResRequestShare;
import com.huanxi.renrentoutiao.ui.dialog.invite.InviteFriendShareDialogFour;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingFrament;
import com.huanxi.renrentoutiao.utils.QrCodeUtils;
import com.huanxi.renrentoutiao.utils.ShareUtils;
import com.huanxi.renrentoutiao.utils.SystemUtils;
import com.huanxi.renrentoutiao.utils.UIUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Dinosa on 2018/1/29.
 * 这里是邀请好友的界面；
 */

public class InviteFriendFragmentNew extends BaseLoadingFrament {

    public String mTitle;
    public String mContent;
    public String mUrl;
    public String mImgUrl;

    @BindView(R.id.tv_invite_code)
    TextView mTvInviteCode;

    @BindView(R.id.tv_activity_desc)
    TextView mTvActivityDesc;
    private InviteFriendShareDialogFour mShareDialog;

    @Override
    public int getLoadingContentLayoutId() {
        return R.layout.fragment_invite_friend_new;
    }


    @Override
    protected void initView() {
        super.initView();

        String uid="";
        if (getUserBean() != null) {
            uid=getUserBean().getUserid();
        }

        //这里我们要做的一个操作就是
        mShareDialog = new InviteFriendShareDialogFour(getBaseActivity(), null,uid);

        getActivityDesc();
    }

    /**
     * 朋友圈的分享；
     */
    private void reqeustWechatComment() {

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiInviteFriendContent.FROM_UID,getUserBean().getUserid());
        paramsMap.put(ApiInviteFriendContent.TYPE,ApiInviteFriendContent.WECHAT_FRIEND_COMMENT);

        ApiInviteFriendContent apiInviteFriendContent=new ApiInviteFriendContent(new HttpOnNextListener<ResInviteFriendContent>() {

            @Override
            public void onNext(final ResInviteFriendContent resInviteFriendContent) {




                Glide.with(getBaseActivity())
                        .load(resInviteFriendContent.getImg())
                        .into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                //view.setImageDrawable(resource);
                                //这里在合成图片；
                                Bitmap shareImgBitmap=createBitmap(
                                        resource    ,
                                        QrCodeUtils.generateBitmap(
                                                resInviteFriendContent.getErwema()  ,
                                                UIUtils.dip2px(getBaseActivity(),170)   ,
                                                UIUtils.dip2px(getBaseActivity(),170)
                                        )
                                );


                                shareToWeChat(resInviteFriendContent.getContent(),shareImgBitmap);

                                dismissDialog();
                            }
                        });


            }

        },getBaseActivity(),paramsMap);

        HttpManager.getInstance().doHttpDeal(apiInviteFriendContent);
    }



    public  void shareToWeChat(String msg,Bitmap bitmap) {
        Context context=getBaseActivity();
        // TODO: 2015/12/13 将需要分享到微信的图片准备好
        try {

            Intent intent = new Intent();
            //分享精确到微信的页面，朋友圈页面，或者选择好友分享页面
            ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
            intent.setComponent(comp);
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.setType("image/*");
//        intent.setType("text/plain");
            //添加Uri图片地址
//        String msg=String.format(getString(R.string.share_content), getString(R.string.app_name), getLatestWeekStatistics() + "");

            intent.putExtra("Kdescription", msg);
            ArrayList<Uri> imageUris = new ArrayList<Uri>();
            // TODO: 2016/3/8 根据不同图片来设置分享
            File dir = context.getExternalFilesDir(null);
            if (dir == null || dir.getAbsolutePath().equals("")) {
                dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            }
            File pic = new File(dir, "bigbang.jpg");
            pic.deleteOnExit();

            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, new FileOutputStream(pic));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                imageUris.add(Uri.fromFile(pic));
            } else {
                //修复微信在7.0崩溃的问题
                Uri uri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(context.getContentResolver(), pic.getAbsolutePath(), "bigbang.jpg", null));
                imageUris.add(uri);
            }

            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
            ((Activity) context).startActivityForResult(intent, 1000);
        } catch (Throwable e) {
            toast("未检测到微信");
        }
    }


    /**
     * 微信的分享；
     */
    private void requestWechatShare() {


        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiInviteFriendContent.FROM_UID,getUserBean().getUserid());
        paramsMap.put(ApiInviteFriendContent.TYPE,ApiInviteFriendContent.WECHAT_FRIEND);

        showDialog();
        ApiInviteFriendContent apiInviteFriendContent=new ApiInviteFriendContent(new HttpOnNextListener<ResInviteFriendContent>() {

            @Override
            public void onNext(final ResInviteFriendContent resInviteFriendContent) {

                //这里分享图片：img表示背景，erwma表示二维码；


                Glide.with(getBaseActivity())
                        .load(resInviteFriendContent.getImg())
                        .into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

                                //这里在合成图片；
                                Bitmap shareImgBitmap=createBitmap(
                                        resource    ,
                                        QrCodeUtils.generateBitmap(
                                                resInviteFriendContent.getErwema()  ,
                                                UIUtils.dip2px(getBaseActivity(),170)   ,
                                                UIUtils.dip2px(getBaseActivity(),170)
                                        )
                                );

//                                mShareDialog.shareWechatFriend(shareImgBitmap,null);
                                ShareUtils.shareWechatFriend(getContext() , shareImgBitmap , null);
                                dismissDialog();
                            }
                        });
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                dismissDialog();
            }
        },getBaseActivity(),paramsMap);

        HttpManager.getInstance().doHttpDeal(apiInviteFriendContent);

    }


    /**
     * 启动qq分享的功能
     */
    private void requestQQShare() {

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiInviteFriendContent.FROM_UID,getUserBean().getUserid());
        paramsMap.put(ApiInviteFriendContent.TYPE,ApiInviteFriendContent.QQ);

        ApiInviteFriendContent apiInviteFriendContent=new ApiInviteFriendContent(new HttpOnNextListener<ResInviteFriendContent>() {

            @Override
            public void onNext(ResInviteFriendContent resInviteFriendContent) {

                //这里我们要做的一个操作就是;
                mShareDialog.shareQQ(resInviteFriendContent.getContent(),null);

            }

        },getBaseActivity(),paramsMap);

        HttpManager.getInstance().doHttpDeal(apiInviteFriendContent);

    }


    /**
     * 创建一个二维码的对象；
     * @return
     */
    public Bitmap createBitmap(Drawable bgDrawable,Bitmap qrcodeBitmap){

        Resources resources = getResources();

        int w = bgDrawable.getIntrinsicWidth();
        int h = bgDrawable.getIntrinsicHeight();
        bgDrawable.setBounds(0,0,w,h);

        // 取 drawable 的颜色格式
        Bitmap.Config config = bgDrawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;

        //这是一个空的Bitmap对象的；
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //创建要给画布对象；
        Canvas canvas = new Canvas(bitmap);
        //画一个背景
        bgDrawable.draw(canvas);


        int size = UIUtils.dip2px(getBaseActivity(), 170);

        int left = (bgDrawable.getIntrinsicWidth() - size)/2;
        int top = (bgDrawable.getIntrinsicHeight() - size)/2-UIUtils.dip2px(getBaseActivity(),10);

        //画二维码；
        canvas.drawBitmap(qrcodeBitmap,left,top,new Paint());

        return bitmap;
    }


    /**
     * 获取活动的说明内容；
     */
    private void getActivityDesc() {
        ApiInviteFriendDesc apiInviteFriendDesc=new ApiInviteFriendDesc(new HttpOnNextListener<ResInviteFriendDesc>() {

            @Override
            public void onNext(ResInviteFriendDesc resInviteFriendDesc) {
                //这里要做的一个操作就是显示对应的
                mTvActivityDesc.setText(resInviteFriendDesc.getInventtext());

            }
        },getBaseActivity(),new HashMap<String,String>());

        HttpManager.getInstance().doHttpDeal(apiInviteFriendDesc);
    }

    @Override
    protected void initData() {
        super.initData();

        getShareContent();
        mTvInviteCode.setText(getUserBean().getInvitationcode()+"");
    }

    /**
     * 获取分享的内容；
     */
    private void getShareContent() {

        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put(ApiUserShare.TYPE, ApiUserShare.TYPE_INVITE);
        paramsMap.put(ApiUserShare.FROM_UID, getUserBean().getUserid());

        ApiUserShare apiUserShare = new ApiUserShare(new HttpOnNextListener<ResRequestShare>() {
            @Override
            public void onNext(ResRequestShare resRequestShare) {
                mTitle = resRequestShare.getTitle();
                mContent = resRequestShare.getDesc();
                mUrl = resRequestShare.getUrl();
                mImgUrl = resRequestShare.getAvatar();
                showSuccess();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showFaild();
            }
        }, getBaseActivity(), paramsMap);
        apiUserShare.setShowProgress(false);
        HttpManager.getInstance().doHttpDeal(apiUserShare);
    }


    @OnClick(R.id.iv_invite)
    public void onClickInviteNow() {

        //显示分享对话框
        mShareDialog.show();
    }

    @OnClick(R.id.tv_copy)
    public void onClickCopy() {
        //这里表示复制邀请码的一个操作；
        //SystemUtils.setClipString(getBaseActivity(), mTvInviteCode.getText().toString());
        //这里是qq分享的内容的操作；


        if(!isLogin()){
            return;
        }


            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put(ApiInviteFriendContent.FROM_UID,getUserBean().getUserid());
            paramsMap.put(ApiInviteFriendContent.TYPE,ApiInviteFriendContent.QQ);

            ApiInviteFriendContent apiInviteFriendContent=new ApiInviteFriendContent(new HttpOnNextListener<ResInviteFriendContent>() {

                @Override
                public void onNext(ResInviteFriendContent resInviteFriendContent) {

                    //这里我们要做的一个操作就是;
                   // shareQQ(resInviteFriendContent.getContent(),null);

                    SystemUtils.setClipString(getBaseActivity(),resInviteFriendContent.getContent());
                    toast("复制成功!");

                }

            }, (getBaseActivity()),paramsMap);

            HttpManager.getInstance().doHttpDeal(apiInviteFriendContent);

    }




}




