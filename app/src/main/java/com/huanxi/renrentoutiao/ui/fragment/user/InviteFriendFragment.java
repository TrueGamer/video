package com.huanxi.renrentoutiao.ui.fragment.user;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.user.ApiShareSuccess;
import com.huanxi.renrentoutiao.net.api.user.ApiUserShare;
import com.huanxi.renrentoutiao.net.bean.ResRequestShare;
import com.huanxi.renrentoutiao.net.bean.news.ResAward;
import com.huanxi.renrentoutiao.ui.activity.user.QuestionsActivity;
import com.huanxi.renrentoutiao.ui.adapter.AdsAdapter;
import com.huanxi.renrentoutiao.ui.dialog.RedPacketDialog;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingFrament;
import com.huanxi.renrentoutiao.utils.SystemUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import butterknife.BindView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Dinosa on 2018/1/29.
 * 这里是邀请好友的界面；
 */

public class InviteFriendFragment extends BaseLoadingFrament {


    @BindView(R.id.rv_invite)
    public RecyclerView mRvInvite;

    @BindView(R.id.rv_ads)
    public RecyclerView mRvAds;

    public AdsAdapter mAdsAdapter;

    @Override
    public int getLoadingContentLayoutId() {
        return R.layout.fragment_invite_friend;
    }


    @Override
    protected void initView() {
        super.initView();

        mRvInvite.setLayoutManager(new LinearLayoutManager(getBaseActivity()));

        //List<AdBean> friend = ((MyApplication) getBaseActivity().getApplication()).getResAds().getFriend();

        mAdsAdapter = new AdsAdapter(null);

        View headerView = LayoutInflater.from(getBaseActivity()).inflate(R.layout.item_invite_friend_header, mRvInvite, false);
        initHeaderView(headerView);
        mAdsAdapter.addHeaderView(headerView);

        mRvInvite.setAdapter(mAdsAdapter);


        if(isHasAds()){
            MyApplication application = (MyApplication) getBaseActivity().getApplication();
            //我们添加一个AD
            //这里我们使用一个广点通的
            AdsAdapter ads2Adapter = new AdsAdapter(application.getResAds().getTasklist());
            mRvAds.setLayoutManager(new LinearLayoutManager(getActivity()){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            mRvAds.setAdapter(ads2Adapter);
        }
    }

    @Override
    protected void initData() {
        super.initData();

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


    public String mTitle;
    public String mContent;
    public String mUrl;
    public String mImgUrl;


    public void share(String platformName) {

        Platform.ShareParams wechatMoments = new Platform.ShareParams();
        wechatMoments.setTitle(mTitle);
        wechatMoments.setText(mContent);
        wechatMoments.setUrl(mUrl);
        wechatMoments.setImageUrl(mImgUrl);

        wechatMoments.setImageData(BitmapFactory.decodeResource(getBaseActivity().getResources(), R.mipmap.ic_launcher));
        wechatMoments.setShareType(Platform.SHARE_WEBPAGE);
        Platform weixin = ShareSDK.getPlatform(platformName);

        weixin.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put(ApiShareSuccess.FROM_UID, getUserBean().getUserid());
                paramsMap.put(ApiShareSuccess.TYPE, ApiShareSuccess.TYPE_INVITE);

                //这里我们要执行的一个操作就是完成增加金币的操作；
                ApiShareSuccess apiShareSuccess = new ApiShareSuccess(new HttpOnNextListener<ResAward>() {
                    @Override
                    public void onNext(ResAward resAward) {
                        //这里表示分享成功；
                        RedPacketDialog redPacketDialog = new RedPacketDialog();
                        redPacketDialog.show(getBaseActivity(), resAward.getIntegral());
                    }
                }, getBaseActivity(), paramsMap);
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

    private void initHeaderView(View headerView) {

        String inviteDesc = "所有您邀请的好友,<br/>在获得邀请奖励的同时,<br/>您还可以持续获取他们所赚金币的<font color='" + getResources().getColor(R.color.invite_font_red_color) + "'><big>5%</big></font>";

        //这里是描述信息
        final TextView mTvInviteDesc = (TextView) headerView.findViewById(R.id.tv_invite_desc);
        final TextView mInviteCode = (TextView) headerView.findViewById(R.id.tv_invite_code);
        mInviteCode.setText(getUserBean().getInvitationcode());
        mTvInviteDesc.setText(Html.fromHtml(inviteDesc));

        headerView.findViewById(R.id.ll_wechat_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里是朋友圈
                share(WechatMoments.NAME);
            }
        });

        headerView.findViewById(R.id.ll_wechat_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里是微信好友
                share(Wechat.NAME);
            }
        });

        headerView.findViewById(R.id.ll_my_friends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里是我的好友的部分
                toast("暂未开通！");
            }
        });

        headerView.findViewById(R.id.tv_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtils.setClipString(getBaseActivity(), mInviteCode.getText().toString());
                toast("复制成功!");
            }
        });

        headerView.findViewById(R.id.tv_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getBaseActivity(), QuestionsActivity.class));

            }
        });
    }
}


/*


    @BindView(R.id.rv_invite)
    RecyclerView mRvInvite;
    private AdsAdapter mAdsAdapter;


    @Override
    protected View getContentView() {
        return inflatLayout(R.layout.fragment_invite_friend);
    }

    @Override
    protected void initData() {
        super.initData();

        mRvInvite.setLayoutManager(new LinearLayoutManager(getActivity()));

        MyApplication application = (MyApplication) getBaseActivity().getApplication();
        ResSplashAds resAds = application.getResAds();

        View headerView = LayoutInflater.from(getBaseActivity()).inflate(R.layout.item_invite_friend_header, mRvInvite, false);
        initHeaderView(headerView);

        mAdsAdapter = new AdsAdapter(resAds.getFriend());

        mAdsAdapter.addHeaderView(headerView);
        mRvInvite.setAdapter(mAdsAdapter);

    }

    */
/**
 * 这里初始化头部的View；
 *
 * @param headerView
 *//*

    private void initHeaderView(View headerView) {

        String inviteDesc = "所有您邀请的好友,<br/>在获得邀请奖励的同时,<br/>您还可以持续获取他们所赚金币的<font color='" + getResources().getColor(R.color.invite_font_red_color) + "'><big>5%</big></font>";

        //这里是描述信息
        TextView mTvInviteDesc = (TextView) headerView.findViewById(R.id.tv_invite_desc);
        mTvInviteDesc.setText(Html.fromHtml(inviteDesc));

        headerView.findViewById(R.id.ll_wechat_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里是朋友圈
            }
        });

        headerView.findViewById(R.id.ll_wechat_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里是微信好友
            }
        });

        headerView.findViewById(R.id.ll_my_friends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里是我的好友的部分
            }
        });

    }


    public void share(String platformName) {

        Platform.ShareParams wechatMoments = new Platform.ShareParams();
        wechatMoments.setTitle(mTitle);
        wechatMoments.setText(mContent);
        wechatMoments.setUrl(mUrl);

        wechatMoments.setImageData(BitmapFactory.decodeResource(getBaseActivity().getResources(),R.mipmap.ic_launcher));
        wechatMoments.setShareType(Platform.SHARE_WEBPAGE);
        Platform weixin = ShareSDK.getPlatform(platformName);

        weixin.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

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
*/

