package com.huanxi.renrentoutiao.ui.activity.other;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.ApiSMSSend;
import com.huanxi.renrentoutiao.net.api.ApiWechatLoginNew;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiBindPhoneNumber;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiUserInfo;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiWeChatLogin;
import com.huanxi.renrentoutiao.net.bean.user.ResUserInfo;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.utils.GsonUtils;
import com.huanxi.renrentoutiao.utils.SecurityUtils;
import com.huanxi.renrentoutiao.utils.ValidUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by Dinosa on 2018/1/26.
 */

public class LoginActivity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.dialog_login_guide_new;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        setStatusBarImmersive(null);
    }

    @OnClick(R.id.iv_close)
    public void onClickBack() {
        finish();
    }


    @OnClick(R.id.ll_login)
    public void onClickWeixin() {
        //实现微信的登陆的逻辑；
        auth(Wechat.NAME);
    }

    void auth(String name) {
        showDialog();

        Platform platform = ShareSDK.getPlatform(name);

        if (platform.isAuthValid()) {
            platform.removeAccount(true);
        }

        platform.setPlatformActionListener(new MyPlatformActionListener(name));
        platform.SSOSetting(false);
        platform.authorize();
        platform.showUser(null);

    }


    class MyPlatformActionListener implements PlatformActionListener {

        String mName;

        public MyPlatformActionListener(String mName) {
            this.mName = mName;
        }

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            dismissDialog();
            //toast("onComplete");
            // Log.e("info", JSON.toJSONString(hashMap));
            final PlatformDb db = platform.getDb();
            System.out.println("从微信哪里获取的数据为：" + platform.getDb().exportData());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //  threePartLogin(db.get("nickname"),db.get("unionid"),db.getUserIcon());

                    HashMap<String, String> paramsMap = new HashMap<>();
                    //这里地区，我们不删除；
                    paramsMap.put(ApiWeChatLogin.WECHAT_CITY, "");
                    paramsMap.put(ApiWeChatLogin.WECHAT_COUNTRY, "");
                    paramsMap.put(ApiWeChatLogin.WECHAT_PROVINCE, "");
                    paramsMap.put(ApiWeChatLogin.WECHAT_HEADIMGURL, db.get("icon"));
                    paramsMap.put(ApiWeChatLogin.WECHAT_NICKNAME, db.get("nickname"));
                    paramsMap.put(ApiWeChatLogin.WECHAT_OPENID, db.get("openid"));
                    //1表示男，2表示女
                    String gender = "1";
                    if (db.get("gender").equals("0")) {
                        //表示女；
                        gender = "2";
                    }
                    paramsMap.put(ApiWeChatLogin.WECHAT_SEX, gender);
                    paramsMap.put(ApiWeChatLogin.WECHAT_UNIONID, db.get("unionid"));

                    weChatLogin(paramsMap);
                }
            });
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            toast(throwable.getMessage());
            dismissDialog();
        }

        @Override
        public void onCancel(Platform platform, int i) {
            toast("cancel");
            dismissDialog();
        }
    }

    public static final int REQUEST_BIND_NUMBER = 123;

    /**
     * 这里是登陆逻辑
     *
     * @param paramsMap 参数；
     */
    public void weChatLogin(final HashMap<String, String> paramsMap) {

        /*ApiWeChatLogin apiWeChatLogin = new ApiWeChatLogin(new HttpOnNextListener<String>() {

            @Override
            public void onNext(String userBeanStr) {


                //这里我们使用对其进行一个转换操作；
                String userBeanData = decodeStr(userBeanStr);
                //这里将userBean的String转换为Userbean对象；
                ResUserInfo userBean = GsonUtils.getInstance().toBean(userBeanData, ResUserInfo.class);
                //这里设置别名；
                JPushInterface.setAlias(LoginActivity.this, ((int) System.currentTimeMillis()),userBean.getUserinfo().getUserid());

                if (userBean.getHasbind() == 0) {
                    //这里表示没有绑定手机号
                    //绑定手机号的逻辑
                    //doShowAddPhoneNumber(paramsMap);
                    //这里要做的一个操作就是
                    //Intent intent = new Intent(LoginActivity.this, BindPhoneNumberActivity.class);

                    startActivityForResult(BindPhoneNumberActivity.getIntent(LoginActivity.this, paramsMap), REQUEST_BIND_NUMBER);
                } else {
                    //这里表示登陆成功；
                    updateUser(userBean.getUserinfo());
                    getMyApplication().updateTokenAndUid(userBean.getUserinfo().getToken(), userBean.getUserinfo().getUserid());
                    getUserInfo();
                }
            }
        }, LoginActivity.this, paramsMap);
        HttpManager.getInstance().doHttpDeal(apiWeChatLogin);*/

        ApiWechatLoginNew apiWechatLoginNew=new ApiWechatLoginNew(new HttpOnNextListener<String>() {

            @Override
            public void onNext(String userBeanStr) {

                String userBeanData = decodeStr(userBeanStr);
                //这里将userBean的String转换为Userbean对象；
                ResUserInfo userBean = GsonUtils.getInstance().toBean(userBeanData, ResUserInfo.class);
                //这里设置别名；
                JPushInterface.setAlias(LoginActivity.this, ((int) System.currentTimeMillis()),userBean.getUserinfo().getUserid());

                updateUser(userBean.getUserinfo());
                getMyApplication().updateTokenAndUid(userBean.getUserinfo().getToken(), userBean.getUserinfo().getUserid());
                getUserInfo();
                dismissDialog();
            }
        },this,paramsMap);
        HttpManager.getInstance().doHttpDeal(apiWechatLoginNew);
        dismissDialog();
    }


    /**
     * 这里我们是需要绑定手机号的一个操作；
     */
    private void doShowAddPhoneNumber(final HashMap<String, String> paramsMap) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("绑定手机号");
        View view = View.inflate(this, R.layout.dialog_bind_phone_number, null);

        final EditText mEtPhone = (EditText) view.findViewById(R.id.et_phone);
        final EditText mEtPhoneCode = (EditText) view.findViewById(R.id.et_phone_number_code);
        final TextView tvSendCode = (TextView) view.findViewById(R.id.tv_send_proof);
        tvSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = mEtPhone.getText().toString().trim();
                if (!ValidUtils.isMobileNO(trim)) {
                    toast("请输入正确的手机号!");
                }

                //发送短信的一个方法；
                ApiSMSSend.defaultSend(LoginActivity.this, tvSendCode, trim, ApiSMSSend.BIND_PHONE_CODE);
            }
        });
        builder.setView(view);


        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toast("登陆失败!!!");
            }
        });
        builder.setPositiveButton("确定", null);
        final AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                //这里我们对进行一个验证的操作；

                Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        //这里我们对输入的内容进行一个校验操作；
                        String trim = mEtPhone.getText().toString().trim();
                        if (!ValidUtils.isMobileNO(trim)) {
                            toast("请输入正确的手机号!");
                            return;
                        }
                        String phoneCode = mEtPhoneCode.getText().toString().trim();
                        if (TextUtils.isEmpty(phoneCode)) {
                            toast("验证码不能为空!");
                            return;
                        }

                        paramsMap.put(ApiBindPhoneNumber.PHONE_NUMBER, trim);
                        paramsMap.put(ApiBindPhoneNumber.PHONE_CODE, phoneCode);
                        //这里实现绑定手机号的逻辑
                        bindPhoneNumber(paramsMap, alertDialog);
                    }
                });
            }
        });
        alertDialog.show();
    }

    /**
     * 这里要执行绑定手机号的逻辑；
     */
    private void bindPhoneNumber(HashMap<String, String> paramsMap, final AlertDialog alertDialog) {

        ApiBindPhoneNumber apiBindPhoneNumber = new ApiBindPhoneNumber(new HttpOnNextListener<String>() {
            @Override
            public void onNext(String userStr) {
                //
                //这里表示登陆成功；
                String data = decodeStr(userStr);
                UserBean userBean = GsonUtils.getInstance().toBean(data, UserBean.class);

                getMyApplication().updateTokenAndUid(userBean.getToken(), userBean.getUserid());

                updateUser(userBean);
                getUserInfo(alertDialog);
            }

        }, this, paramsMap);
        HttpManager.getInstance().doHttpDeal(apiBindPhoneNumber);
    }


    /**
     * 这里我们对服务器返回的数据进行一个解密的操作；
     *
     * @param userBeanStr rsa的一个数据操作；
     * @return
     */
    private String decodeStr(String userBeanStr) {

        return SecurityUtils.decodeRsaData(userBeanStr);
    }

    private void getUserInfo() {
        getUserInfo(null);
    }

    private void getUserInfo(final Dialog dialog) {

        UserBean userBean = getUserBean();
        if (userBean == null) {
            return;
        }

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiUserInfo.uid, userBean.getUserid());
        ApiUserInfo apiUserInfo = new ApiUserInfo(new HttpOnNextListener<UserBean>() {

            @Override
            public void onNext(UserBean userBean) {
                ((MyApplication) getApplication()).updateUser(userBean);
                updateUser(userBean);

                if (dialog != null) {
                    dialog.dismiss();
                }
                //更新数据；
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, this, paramsMap);

        HttpManager.getInstance().doHttpDeal(apiUserInfo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_BIND_NUMBER) {
            if (resultCode == RESULT_OK) {
                //这里是结束
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
