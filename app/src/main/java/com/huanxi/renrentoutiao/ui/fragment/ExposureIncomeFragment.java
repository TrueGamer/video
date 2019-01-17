package com.huanxi.renrentoutiao.ui.fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.share.ApiInviteFriendContent;
import com.huanxi.renrentoutiao.net.api.user.ApiShareSuccess;
import com.huanxi.renrentoutiao.net.api.user.ApiUserWallettotal;
import com.huanxi.renrentoutiao.net.bean.ResInviteFriendContent;
import com.huanxi.renrentoutiao.net.bean.ResWallettotalBean;
import com.huanxi.renrentoutiao.net.bean.news.ResAward;
import com.huanxi.renrentoutiao.ui.dialog.RedPacketDialog;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingFrament;
import com.huanxi.renrentoutiao.utils.FormatUtils;
import com.huanxi.renrentoutiao.utils.QrCodeUtils;
import com.huanxi.renrentoutiao.utils.UIUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Dinosa on 2018/2/28.
 */

public class ExposureIncomeFragment extends BaseLoadingFrament {


    public String mTitle;
    public String mContent;
    public String mUrl;


    @BindView(R.id.tv_has_get_money)
    public TextView mTvHasGetMoney;

    @Override
    public int getLoadingContentLayoutId() {

        return R.layout.activity_exposure_income_new;

    }

    @Override
    protected void initData() {
        super.initData();
        //这里我们要请求参数；
        getWalletTotalMoney();
    }

    /**
     * 这里是获取收益明细的一个方法;
     */
    public void getWalletTotalMoney() {

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiUserWallettotal.FROM_UID,getUserBean().getUserid());
        ApiUserWallettotal apiUserWallettotal = new ApiUserWallettotal(new HttpOnNextListener<ResWallettotalBean>() {

            @Override
            public void onNext(ResWallettotalBean resWallettotalBean) {
                //这里是汇率之类的一个接口；
                //updateUI(resWallettotalBean);
                mTvHasGetMoney.setText("赚了"+FormatUtils.decimalFormatTwo(resWallettotalBean.getTotalmoney())+"元!");
                showSuccess();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showFaild();
            }
        }, getBaseActivity(), paramsMap);

        HttpManager.getInstance().doHttpDeal(apiUserWallettotal);
    }


    @OnClick(R.id.ll_wechat_friend)
    public void onClickShareWeChatFriend(){

        // share(Wechat.NAME);
        requestShareContent(Wechat.NAME);

    }

    @OnClick(R.id.ll_wechat_comment)
    public void onClickShareWeChatComment(){

        //share(WechatMoments.NAME);

        requestShareContent(WechatMoments.NAME);

    }

    private void requestShareContent(final String sharePlant) {


        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put(ApiInviteFriendContent.FROM_UID,getUserBean().getUserid());
        paramsMap.put(ApiInviteFriendContent.TYPE,ApiInviteFriendContent.INVITE_FRIEND_CONTENT);

        ApiInviteFriendContent apiUserShare=new ApiInviteFriendContent(new HttpOnNextListener<ResInviteFriendContent>() {
            @Override
            public void onNext(final ResInviteFriendContent resInviteFriendContent) {
                //mTitle=resRequestShare.getTitle();
                //mContent=resRequestShare.getDesc();
                //mUrl=resRequestShare.getUrl();

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
                                if(shareImgBitmap!= null)
                                {
                                    share(sharePlant,shareImgBitmap);
                                }
                                else{
                                    toast("获取分享内容失败!");
                                }
                            }
                        });
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                toast("获取分享内容失败!");
            }
        },getBaseActivity(),paramsMap);
        HttpManager.getInstance().doHttpDeal(apiUserShare);
    }

    /**
     * 创建一个二维码的对象；
     * @return
     */
    public Bitmap createBitmap(Drawable bgDrawable, Bitmap qrcodeBitmap){

        Resources resources = getResources();

        int w = bgDrawable.getIntrinsicWidth();
        int h = bgDrawable.getIntrinsicHeight();
        bgDrawable.setBounds(0,0,w,h);

        // 取 drawable 的颜色格式
        Bitmap.Config config = bgDrawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;

        //这是一个空的Bitmap对象的；
        Bitmap bitmap ;
        try{
            bitmap = Bitmap.createBitmap(w, h, config);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

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





    public void share(final String platformName, Bitmap bitmap) {

        Platform.ShareParams wechatMoments = new Platform.ShareParams();
        wechatMoments.setTitle(mTitle);
        wechatMoments.setText(mContent);
        wechatMoments.setUrl(mUrl);

        wechatMoments.setImageData(bitmap);
        wechatMoments.setShareType(Platform.SHARE_IMAGE);
        Platform weixin = ShareSDK.getPlatform(platformName);

        weixin.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                if(platformName == Wechat.NAME){
                    //这里了要做
                    toast("分享成功!!!");
                    return;
                }
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put(ApiShareSuccess.FROM_UID,getUserBean().getUserid());
                paramsMap.put(ApiShareSuccess.TYPE,ApiShareSuccess.TYPE_EXPORE_INCOME);

                //这里我们要执行的一个操作就是完成增加金币的操作；
                ApiShareSuccess apiShareSuccess=new ApiShareSuccess(new HttpOnNextListener<ResAward>() {
                    @Override
                    public void onNext(ResAward resAward) {
                        //这里表示分享成功；
                        RedPacketDialog redPacketDialog=new RedPacketDialog();
                        redPacketDialog.show(getBaseActivity(),resAward.getIntegral());
                    }
                },getBaseActivity(),paramsMap);
                HttpManager.getInstance().doHttpDeal(apiShareSuccess);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });

        weixin.share(wechatMoments);
    }
}
