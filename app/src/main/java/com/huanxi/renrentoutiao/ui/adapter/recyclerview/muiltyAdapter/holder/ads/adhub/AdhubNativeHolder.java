package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.adhub;

import android.content.Context;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.ui.activity.WebHelperActivity;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.base.BaseMuiltyViewHolder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.BaseMuiltyAdapterBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.adhub.AdhubNativeBean;
import com.huanxi.renrentoutiao.utils.ImageUtils;
import com.hubcloud.adhubsdk.NativeAd;
import com.hubcloud.adhubsdk.NativeAdListener;
import com.hubcloud.adhubsdk.NativeAdResponse;
import com.hubcloud.adhubsdk.internal.nativead.NativeAdEventListener;
import com.hubcloud.adhubsdk.internal.nativead.NativeAdUtil;

import java.util.List;

import cn.tongdun.android.shell.db.utils.LogUtil;

/**
 * Created by gdhuo on 2018/12/31.
 */
public class AdhubNativeHolder extends BaseMuiltyViewHolder<AdhubNativeBean> {
    private static final String TAG = "AdhubNativeHolder";

    @Override
    public void init(AdhubNativeBean bean, BaseViewHolder helper, Context context) {
        NativeAdResponse response = bean.getResponse();
        init(response,helper,context);
    }

    public void init(NativeAdResponse response, BaseViewHolder helper, Context context) {
        try {
            ImageUtils.loadImage(context,response.getImageUrls().get(0),helper.getView(R.id.iv_native1));
            ImageUtils.loadImage(context,response.getImageUrls().get(1),helper.getView(R.id.iv_native2));
            ImageUtils.loadImage(context,response.getImageUrls().get(2),helper.getView(R.id.iv_native3));
        } catch (IndexOutOfBoundsException ignored) {

        }
        LinearLayout parent = (LinearLayout) helper.getView(R.id.parent);
        LinearLayout ad_container = (LinearLayout) helper.getView(R.id.ad_container);
        //sdk内部提供了以下方法，可以将一个view加上logo并返回一个加入了logo的framelayout替代原本无logo的view;
        //注意调用了此方法之后原来的view将不存在于之前的布局之中，须将返回的framelayout加入之前的布局。
        //若此方法不满足要求，请开发者自己实现加入logo及广告字样
        FrameLayout frameLayout = NativeAdUtil.addADLogo(ad_container, response);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        layoutParams.gravity = Gravity.CENTER;
        parent.addView(frameLayout, 0, layoutParams);
        // This must be called.
        //注册原生广告展示及点击曝光，必须调用。
        NativeAdUtil.registerTracking(response, helper.getView(R.id.ad_container), new NativeAdEventListener() {
            @Override
            public void onAdWasClicked() {
                LogUtil.d(TAG,"onAdWasClicked");
//                Toast.makeText(helper.getConvertView().getContext(), "onAdWasClicked", Toast.LENGTH_SHORT).show();
//                context.startActivity(WebHelperActivity.getIntent(context,response.getLandingPageUrl(),"title",false));
            }

            @Override
            public void onAdWillLeaveApplication() {
                LogUtil.d(TAG,"onAdWillLeaveApplication");
//                Toast.makeText(helper.getConvertView().getContext(), "onAdWillLeaveApplication", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
