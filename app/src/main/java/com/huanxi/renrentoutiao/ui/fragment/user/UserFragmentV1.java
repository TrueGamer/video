package com.huanxi.renrentoutiao.ui.fragment.user;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseViewHolder;
import com.db.ta.sdk.NonStandardTm;
import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.ApiSMSSend;
import com.huanxi.renrentoutiao.net.api.share.ApiInviteFriendContent;
import com.huanxi.renrentoutiao.net.api.user.ApiRequestAddInviteCode;
import com.huanxi.renrentoutiao.net.api.user.ApiShareSuccess;
import com.huanxi.renrentoutiao.net.api.user.ApiUserShare;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiBindPhoneNumber;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiOnlyBindPhoneNumber;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiUserInfo;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.net.bean.ResInviteFriendContent;
import com.huanxi.renrentoutiao.net.bean.ResRequestShare;
import com.huanxi.renrentoutiao.net.bean.news.ResAward;
import com.huanxi.renrentoutiao.ui.activity.WebHelperActivity;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.activity.other.ChannelActivity;
import com.huanxi.renrentoutiao.ui.activity.other.ContactKeFuActivity;
import com.huanxi.renrentoutiao.ui.activity.other.FriendRankingActivity;
import com.huanxi.renrentoutiao.ui.activity.other.InviteFriendActivityNew;
import com.huanxi.renrentoutiao.ui.activity.other.LuckyWalkActivity;
import com.huanxi.renrentoutiao.ui.activity.other.MainActivity;
import com.huanxi.renrentoutiao.ui.activity.other.SettingActivity;
import com.huanxi.renrentoutiao.ui.activity.user.IntergralShopActivity;
import com.huanxi.renrentoutiao.ui.activity.user.MyMessageActivity;
import com.huanxi.renrentoutiao.ui.activity.user.ProfitDetailV1Activity;
import com.huanxi.renrentoutiao.ui.activity.user.QuestionsActivity;
import com.huanxi.renrentoutiao.ui.activity.user.UserBrowerRecordsActivity;
import com.huanxi.renrentoutiao.ui.activity.user.UserCollectionActivity;
import com.huanxi.renrentoutiao.ui.activity.user.UserCommentActivity;
import com.huanxi.renrentoutiao.ui.activity.user.UserSignActivity;
import com.huanxi.renrentoutiao.ui.activity.user.UserTaskActivity;
import com.huanxi.renrentoutiao.ui.adapter.AdBean;
import com.huanxi.renrentoutiao.ui.adapter.AdsAdapter;
import com.huanxi.renrentoutiao.ui.dialog.InputDialog;
import com.huanxi.renrentoutiao.ui.dialog.QrcodeShareDialog;
import com.huanxi.renrentoutiao.ui.dialog.RedPacketDialog;
import com.huanxi.renrentoutiao.ui.dialog.invite.InviteFriendShareDialog;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseFragment;
import com.huanxi.renrentoutiao.utils.ImageUtils;
import com.huanxi.renrentoutiao.utils.QrCodeUtils;
import com.huanxi.renrentoutiao.utils.ShareUtils;
import com.huanxi.renrentoutiao.utils.UIUtils;
import com.huanxi.renrentoutiao.utils.ValidUtils;
import com.mob.commons.SHARESDK;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

import static com.huanxi.renrentoutiao.R.id.tv_qrcode;

/**
 * Created by Dinosa on 2018/1/12.
 */

public class UserFragmentV1 extends BaseFragment {


    @BindView(R.id.iv_icon_user)
    public ImageView mIvUserIcon;

    @BindView(R.id.tv_user_name)
    public TextView mTvUsername;

    @BindView(R.id.tv_wallet_number)
    public TextView mTvWalletNumber;

    @BindView(R.id.tv_money)
    public TextView mTvMoney;

    @BindView(R.id.tv_friend_number)
    public TextView mTvFriednNumber;

    @BindView(R.id.view_divider)
    public View mDivider;

    @BindView(R.id.iv_ad_banner)
    public RecyclerView mRvAds;

    @BindView(R.id.tv_desc)
    TextView mTvDesc;

    @BindView(R.id.ll_yaoqingma)
    LinearLayout linearLayoutYaoqingma;

    @BindView(R.id.ll_phoneNum)
    LinearLayout linearLayoutPhoneNum;

    @BindView(R.id.tv_user_inviteCode)
    TextView tv_user_inviteCode;

    private AdsAdapter mAdsAdapter;
    private NonStandardTm mNonStandardTm;

    InviteFriendShareDialog mShareDialog;

    @Override
    protected View getContentView() {
        return inflatLayout(R.layout.fragment_user_v1);
    }

    @OnClick(R.id.ll_yaoqingma)
    public void onYaoqingmaClickHandler(){
        checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                doAddInviteCode();
            }

            @Override
            public void loginFaild() {

            }
        });
    }


    /**
     * 这里是填写邀请码的操作；
     */
    private void doAddInviteCode() {

        final InputDialog inputDialog = new InputDialog();
        inputDialog.show(getBaseActivity(), "", new InputDialog.OnDialogClickListener() {
            @Override
            public void onDialogClickSure(String inputContent) {

                if(!TextUtils.isEmpty(inputContent) && getBaseActivity().getUserBean() != null){
                    //这里表示表示邀请码，不为空;

                    HashMap<String, String> paramMap = new HashMap<>();
                    paramMap.put(ApiRequestAddInviteCode.FROM_UID,getBaseActivity().getUserBean().getUserid());
                    paramMap.put(ApiRequestAddInviteCode.INVENTCODE,inputContent);

                    ApiRequestAddInviteCode apiRequestAddInviteCode=new ApiRequestAddInviteCode(new HttpOnNextListener<ResAward>() {

                        @Override
                        public void onNext(ResAward resSign) {

                            //这里表示领取成功
                            getBaseActivity().toast("恭喜领取"+resSign.getIntegral()+"金币");

                            inputDialog.dismiss();
                        }
                    },getBaseActivity(),paramMap);
                    HttpManager.getInstance().doHttpDeal(apiRequestAddInviteCode);

                }else{
                    getBaseActivity().toast("邀请码不能为空");
                }
            }
        },"输入邀请码","邀请码");
    }
    private void doShowAddPhoneNumber() {

        final HashMap<String ,String > paramsMap=new HashMap<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("绑定手机号");
        View view = View.inflate(getContext(), R.layout.dialog_bind_phone_number, null);

        final EditText mEtPhone = (EditText) view.findViewById(R.id.et_phone);
        final EditText mEtPhoneCode = (EditText) view.findViewById(R.id.et_phone_number_code);
        final TextView tvSendCode = (TextView) view.findViewById(R.id.tv_send_proof);
        tvSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = mEtPhone.getText().toString().trim();
                if(!ValidUtils.isMobileNO(trim)){
                    toast("请输入正确的手机号!");
                }

                //发送短信的一个方法；
                ApiSMSSend.defaultSend(getBaseActivity(),tvSendCode,trim,ApiSMSSend.BIND_PHONE_CODE);
            }
        });
        builder.setView(view);

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("确定", null);
        final AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                //这里我们对进行一个验证的操作；

                Button button =  alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        //这里我们对输入的内容进行一个校验操作；
                        String trim = mEtPhone.getText().toString().trim();
                        if(!ValidUtils.isMobileNO(trim)){
                            toast("请输入正确的手机号!");
                            return;
                        }
                        String phoneCode = mEtPhoneCode.getText().toString().trim();
                        if(TextUtils.isEmpty(phoneCode)){
                            toast("验证码不能为空!");
                            return;
                        }

                        paramsMap.put(ApiBindPhoneNumber.PHONE_NUMBER,trim);
                        paramsMap.put(ApiBindPhoneNumber.PHONE_CODE,phoneCode);
                        //这里实现绑定手机号的逻辑
                        bindPhoneNumber(paramsMap,alertDialog);
                    }
                });
            }
        });
        alertDialog.show();
    }


    /**
     * 这里要执行绑定手机号的逻辑；
     */
    private void bindPhoneNumber(HashMap<String,String> paramsMap, final AlertDialog alertDialog) {

        ApiOnlyBindPhoneNumber apiBindPhoneNumber=new ApiOnlyBindPhoneNumber(new HttpOnNextListener<ResEmpty>() {
            @Override
            public void onNext(ResEmpty userStr) {

                //这表示绑定成功；
                toast("绑定成功！");
                //这里是需要将绑定给移除的；
                alertDialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //这里表示绑定出错
            }
        },getBaseActivity(),paramsMap);
        HttpManager.getInstance().doHttpDeal(apiBindPhoneNumber);
    }


    @OnClick(R.id.ll_phoneNum)
    public void onPhoneNumClickHandler(){
        doShowAddPhoneNumber();
    }

    @OnClick(R.id.ll_cash)
    public void onCashClick() {
        // 提现
        ((BaseActivity) getActivity()).checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                //收益明细的里面的钱包；
                startActivity(ProfitDetailV1Activity.getIntent(getActivity(), ProfitDetailV1Activity.Type.CASH.name()));
            }

            @Override
            public void loginFaild() {

            }
        });
    }

    @OnClick(R.id.ll_myMoney)
    public void onMyMoneyClick() {
        // 我的钱包
        ((BaseActivity) getActivity()).checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                //这里是跳转到的收益明细里面的金币；
                startActivity(ProfitDetailV1Activity.getIntent(getActivity(), ProfitDetailV1Activity.Type.GOLD.name()));
            }

            @Override
            public void loginFaild() {

            }
        });
    }

    @OnClick(R.id.ll_taskCenter)
    public void onTaskClick() {
        // 任务大厅
        checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                Intent taskIntent = new Intent(getBaseActivity(), UserTaskActivity.class);
                getBaseActivity().startActivity(taskIntent);
            }

            @Override
            public void loginFaild() {

            }
        });
    }

    @OnClick(R.id.ll_guide)
    public void onGuideClick() {
        // 指引
//        startActivity(new Intent(getActivity(), QuestionsActivity.class));
        ((BaseActivity) getActivity()).checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                //startActivity(MyFriendsActivity.getIntent(getActivity(), false));
                Intent intent = new Intent(getBaseActivity(), InviteFriendActivityNew.class);
                getBaseActivity().startActivity(intent);
            }

            @Override
            public void loginFaild() {

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        UserBean userBean = ((MainActivity) getActivity()).getUserBean();
        if (userBean != null) {
            updateText(userBean);
        }

        if(isHasAds()){
            initAds();
            mDivider.setVisibility(View.VISIBLE);
        }else{
            mDivider.setVisibility(View.GONE);
        }

        //这里要做的一个操作就是显示

        String html="<html>邀请好友立赚<font color="+getResources().getColor(R.color.base_color_orange)+">2500</font>金币</html>";
        mTvDesc.setText(Html.fromHtml(html));

        String uid=  "";
        if (getUserBean() != null) {
            uid = getUserBean().getUserid();
        }

        //这里我们要做的一个操作就是
        mShareDialog = new InviteFriendShareDialog(getBaseActivity(), new InviteFriendShareDialog.OnClickShareType() {
            @Override
            public void onClickQQ() {
                //这里我们要做的一个操作就是
            }

            @Override
            public void onClickWechat() {
            }

            @Override
            public void onClickWechatComment() {

            }
        },uid);
    }

    private void initAds() {
        //我们添加一个AD
        MyApplication application = (MyApplication) getBaseActivity().getApplication();
        List<AdBean> tasklist = application.getResAds().getMy();
        //这里我们使用一个广点通的
        AdsAdapter adsAdapter = new AdsAdapter(tasklist);
        mRvAds.setLayoutManager(new LinearLayoutManager(getBaseActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRvAds.setAdapter(adsAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mNonStandardTm != null) {
            mNonStandardTm.destroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //刷新ui；
        if(isLogin()){
            getUserInfo();
        }else{
            updateText(null);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {

            if(isLogin()){
                getUserInfo();
            }else{
                updateText(null);
            }

        }
    }

    private void getUserInfo() {

        UserBean userBean = ((BaseActivity) getActivity()).getUserBean();
        if (userBean == null) {
            return;
        }

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiUserInfo.uid, userBean.getUserid());
        ApiUserInfo apiUserInfo = new ApiUserInfo(new HttpOnNextListener<UserBean>() {

            @Override
            public void onNext(UserBean userBean) {
                ((MyApplication) getActivity().getApplication()).updateUser(userBean);
                updateText(userBean);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, ((RxAppCompatActivity) getActivity()), paramsMap);

        HttpManager.getInstance().doHttpDeal(apiUserInfo);
    }

    private void updateText(UserBean userBean) {


        mTvUsername.setText("未登陆");    //昵称
        mTvWalletNumber.setText("0.00");   //余额
        mTvMoney.setText("0.00");       //金币
        mTvFriednNumber.setText("0");  //好友
        tv_user_inviteCode.setText("邀请码："); // 验证码
        mIvUserIcon.setImageResource(R.drawable.icon_user_default);

        if (userBean == null) {
            return;
        }

        String nickname = TextUtils.isEmpty(userBean.getNickname()) ? "0" : userBean.getNickname();
        String money = TextUtils.isEmpty(userBean.getMoney()) ? "0" : userBean.getMoney();
        String integral = TextUtils.isEmpty(userBean.getIntegral()) ? "0" : userBean.getIntegral();
        String friend = TextUtils.isEmpty(userBean.getFriend()) ? "0" : userBean.getFriend();
        String inviteCode = TextUtils.isEmpty(userBean.getInvitationcode()) ? "0" : userBean.getInvitationcode();

        if (mShareDialog != null) {
            mShareDialog.setUid(getUserBean().getUserid()); //更新一下uid
        }

        mTvUsername.setText(nickname);    //昵称
        mTvWalletNumber.setText(money);   //余额
        mTvMoney.setText(integral);       //金币
        mTvFriednNumber.setText(friend);  //好友
        tv_user_inviteCode.setText("邀请码："+inviteCode); // 邀请码

        ImageUtils.loadImage(getActivity(), userBean.getAvatar(), mIvUserIcon);
    }

    @OnClick(R.id.iv_icon_user_message)
    public void onClickMyMessage() {

        ((BaseActivity) getActivity()).checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                startActivity(new Intent(getActivity(), MyMessageActivity.class));
            }

            @Override
            public void loginFaild() {

            }
        });

    }


    @OnClick({R.id.ll_contacts,R.id.ll_channel})
    public  void  onClickContactKeFu(View view){
        switch (view.getId()) {
            case R.id.ll_contacts:
                startActivity(new Intent(getActivity(), ContactKeFuActivity.class));
                break;
            case R.id.ll_channel:
                Intent intent = new Intent(getActivity(), ChannelActivity.class);
                intent.putExtra("from","from_mine");
                startActivity(intent);
                break;
        }
    }

    public final int requestExit = 2;

    @OnClick({R.id.iv_icon_user, R.id.tv_setting})
    public void onClickUserIcon() {


        ((BaseActivity) getActivity()).checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                //这里是点击用户头像；
                startActivityForResult(new Intent(getActivity(), SettingActivity.class), requestExit);

                //startActivity(new Intent(getBaseActivity(), SettingActivity.class));
            }

            @Override
            public void loginFaild() {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestExit && resultCode == Activity.RESULT_OK) {
            ((MainActivity) getActivity()).getTabhost().setCurrentTab(MainActivity.HOME);
        }
    }

    //    @OnClick(R.id.ll_user_sign)
    public void onClickUserSign() {

        ((BaseActivity) getActivity()).checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                //签到的模块；
                startActivity(new Intent(getActivity(), UserSignActivity.class));
            }

            @Override
            public void loginFaild() {

            }
        });
    }


    //这里表示钱包余额、我的金币、我的好友 现金金额；
    @OnClick(R.id.ll_gold_wallet)
    public void onClickWallet() {
        ((BaseActivity) getActivity()).checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                //收益明细的里面的钱包；
                startActivity(ProfitDetailV1Activity.getIntent(getActivity(), ProfitDetailV1Activity.Type.CASH.name()));
            }

            @Override
            public void loginFaild() {

            }
        });
    }

    //已获金币；
    @OnClick(R.id.ll_get_money)
    public void onClickMoney() {
        ((BaseActivity) getActivity()).checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                //这里是跳转到的收益明细里面的金币；
                startActivity(ProfitDetailV1Activity.getIntent(getActivity(), ProfitDetailV1Activity.Type.GOLD.name()));
            }

            @Override
            public void loginFaild() {

            }
        });
    }

    @OnClick(R.id.ll_my_friends)
    public void onClickMyFriends() {

        ((BaseActivity) getActivity()).checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                //startActivity(MyFriendsActivity.getIntent(getActivity(), false));
//                Intent intent = new Intent(getBaseActivity(), FriendRankingActivity.class);
//                startActivity(intent);
                Intent intent = new Intent(getBaseActivity(), InviteFriendActivityNew.class);
                getBaseActivity().startActivity(intent);
            }

            @Override
            public void loginFaild() {

            }
        });
    }

    //==============邀请好友，我的团队========；
    @OnClick(R.id.ll_invite)
    public void onClickInvite() {
        //startActivity(MyFriendsActivity.getIntent(getActivity(), true));
        checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                Intent intent = new Intent(getBaseActivity(), InviteFriendActivityNew.class);
                startActivity(intent);
            }
            @Override
            public void loginFaild() {
            }
        });
    }


    //=============这里是中间的任务中心，拆红包、兑现提现；===================

    //    @OnClick(R.id.ll_task)
    public void onClickTask() {
        //任务模块T
        startActivity(new Intent(getActivity(), UserTaskActivity.class));
    }


    //    @OnClick(R.id.ll_red_pack)
    public void onClickRedPacket() {
        //这里表示点击拆红包；
        startActivity(new Intent(getActivity(), UserTaskActivity.class));
    }


    //底部的四个模块：

    @OnClick(R.id.ll_collection)
    public void onClickUserCollection() {

        checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                startActivity(new Intent(getActivity(), UserCollectionActivity.class));
            }

            @Override
            public void loginFaild() {

            }
        });


    }

    @OnClick(R.id.ll_redpkg)
    public void onClickRedPkg() {
        checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                //这里我们要做的一个操作就是红包；
                //startActivity(new Intent(getActivity(),UserTaskActivity.class));
               // ((MainActivity) getBaseActivity()).newsStartTaskActivity();
                //这里是红包的逻辑；
                String url="http://engine.tuicoco.com/index/activity?appKey=3ThfjmMjDSM7z5gk5QBx3nbho1Dy&adslotId=191804";
                startActivity(WebHelperActivity.getIntent(getBaseActivity(),url,"",false));
            }

            @Override
            public void loginFaild() {

            }
        });
    }

    //    @OnClick(R.id.ll_comment)
    public void onClickUserComment() {
        startActivity(new Intent(getActivity(), UserCommentActivity.class));
    }

    //点击提现；
    @OnClick(R.id.ll_withdrawals)
    public void onClickWithDrawals() {
        //提现的界面；
        checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                startActivity(LuckyWalkActivity.getIntent(getBaseActivity(), true));
            }

            @Override
            public void loginFaild() {

            }
        });
    }


    @OnClick(R.id.ll_question)
    public void onClickQuestion() {
        startActivity(new Intent(getActivity(), QuestionsActivity.class));
    }

    @OnClick(R.id.ll_history)
    public void onClickBrowerRecord() {

        ((BaseActivity) getActivity()).checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                startActivity(new Intent(getActivity(), UserBrowerRecordsActivity.class));
            }

            @Override
            public void loginFaild() {

            }
        });
    }

    //这里以后通过自定义注解的方式判断用户是否登陆了；
    @OnClick(R.id.tv_wechat)
    void shareWechat() {
        //这里是微信好友
        checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                //getShareParam(Wechat.NAME);
                requestWechatShare();
            }

            @Override
            public void loginFaild() {

            }
        });

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
                                //view.setImageDrawable(resource);
                                //这里在合成图片；
                                Bitmap shareImgBitmap=QrCodeUtils.createBitmap(
                                        getContext(),
                                        resource,
                                        QrCodeUtils.generateBitmap(
                                                resInviteFriendContent.getErwema(),
                                                UIUtils.dip2px(getBaseActivity(),170),
                                                UIUtils.dip2px(getBaseActivity(),170)
                                        )
                                );
//                                mShareDialog.shareWechatFriend(shareImgBitmap,null);
                                ShareUtils.shareWechatFriend(getContext() , shareImgBitmap , null);
                                dismissDialog();
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                super.onLoadFailed(e, errorDrawable);
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


    @OnClick(R.id.tv_wechat_comment)
    void sharePyq() {
        //这里是朋友圈
        checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                //getShareParam(WechatMoments.NAME);
                //这里分享朋友圈；
                reqeustWechatComment();
            }

            @Override
            public void loginFaild() {

            }
        });

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
                                Bitmap shareImgBitmap=QrCodeUtils.createBitmap(
                                        getContext() ,
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

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                dismissDialog();
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




    @OnClick(R.id.tv_qq)
    void shareQQ() {
        //这里是QQ

        checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {

                //qq登陆；
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put(ApiInviteFriendContent.FROM_UID,getUserBean().getUserid());
                paramsMap.put(ApiInviteFriendContent.TYPE,ApiInviteFriendContent.QQ);

                ApiInviteFriendContent apiInviteFriendContent=new ApiInviteFriendContent(new HttpOnNextListener<ResInviteFriendContent>() {

                    @Override
                    public void onNext(ResInviteFriendContent resInviteFriendContent) {

                        //这里我们要做的一个操作就是;
                        mShareDialog.shareQQ(resInviteFriendContent.getContent(), null);

//                        shareQQ(resInviteFriendContent.getContent());

                    }

                },getBaseActivity(),paramsMap);

                HttpManager.getInstance().doHttpDeal(apiInviteFriendContent);

            }

            @Override
            public void loginFaild() {

            }
        });

    }

    /**
     * 分享qq
     */
    public void shareQQ(String title){

//        Platform.ShareParams wechatMoments = new Platform.ShareParams();
//        wechatMoments.setText(title);
//        wechatMoments.setShareType(Platform.SHARE_TEXT);
//        Platform weixin = ShareSDK.getPlatform(QQ.NAME);
//        weixin.share(wechatMoments);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, title);
        sendIntent.setType("text/plain");
        try {
            sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
            Intent chooserIntent = Intent.createChooser(sendIntent, "选择分享途径");
            if (chooserIntent == null) {
                return;
            }
            startActivity(chooserIntent);
        } catch (Exception e) {
            startActivity(sendIntent);
        }

    }

    @OnClick(tv_qrcode)
    void shareQrcode() {
       // toast("暂未开通！");

        //这里请求微信好友，然后然后生成二维码

        checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                shareNetQrCode();
            }

            @Override
            public void loginFaild() {

            }
        });

    }
    //这里是分享二维码的逻辑
    private void shareNetQrCode() {

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
                                //view.setImageDrawable(resource);
                                //这里在合成图片；
                                Bitmap bitmap = QrCodeUtils.generateBitmap(
                                        resInviteFriendContent.getErwema(),
                                        UIUtils.dip2px(getBaseActivity(), 170),
                                        UIUtils.dip2px(getBaseActivity(), 170)
                                );

                                QrcodeShareDialog qrcodeShareDialog = new QrcodeShareDialog(getBaseActivity(), new QrcodeShareDialog.GoReadDialogListener() {
                                    @Override
                                    public void onClickShare() {

                                        //这里要做的一个操作就是分享；

                                        mShareDialog.show();

                                    }
                                },bitmap);
                                qrcodeShareDialog.show();

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

    public String mTitle;
    public String mContent;
    public String mUrl;
    public String mImgUrl;

    /**
     * 分享参数
     *
     * @param platformName
     */
    void getShareParam(final String platformName) {
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
                share(platformName);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                toast(getString(R.string.text_nonet));
            }
        }, getBaseActivity(), paramsMap);
        apiUserShare.setShowProgress(false);
        HttpManager.getInstance().doHttpDeal(apiUserShare);
    }

    /**
     * 分享 邀请好友
     *
     * @param platformName
     */
    void share(String platformName) {
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
}
