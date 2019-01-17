package com.huanxi.renrentoutiao.presenter;

import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.ApiWechatLoginNew;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiUserInfo;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiWeChatLogin;
import com.huanxi.renrentoutiao.net.bean.user.ResUserInfo;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.utils.GsonUtils;
import com.huanxi.renrentoutiao.utils.SecurityUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by Dinosa on 2018/4/21.
 * 登陆的业务逻辑的类；
 */

public class LoginPresenter extends BasePresenter{


    private final OnLoginListener mOnLoginListener;

    public LoginPresenter(BaseActivity baseActivity, OnLoginListener mOnLoginListener) {
        super(baseActivity);
        this.mOnLoginListener = mOnLoginListener;
    }

    public void login(){
        auth(Wechat.NAME);
    }

    void auth(String name) {

        if (mOnLoginListener != null) {
            mOnLoginListener.onLoginStart();
        }

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
            throwable.printStackTrace();

            if (mOnLoginListener != null) {
                mOnLoginListener.onLoginFaild();
            }
        }

        @Override
        public void onCancel(Platform platform, int i) {

            if (mOnLoginListener != null) {
                mOnLoginListener.onLoginFaild();
            }

        }
    }

    /**
     * 这里是登陆逻辑
     *
     * @param paramsMap 参数；
     */
    public void weChatLogin(final HashMap<String, String> paramsMap) {


        ApiWechatLoginNew apiWechatLoginNew=new ApiWechatLoginNew(new HttpOnNextListener<String>() {

            @Override
            public void onNext(String userBeanStr) {

                String userBeanData = decodeStr(userBeanStr);
                //这里将userBean的String转换为Userbean对象；
                ResUserInfo userBean = GsonUtils.getInstance().toBean(userBeanData, ResUserInfo.class);
                //这里设置别名；
                JPushInterface.setAlias(mBaseActivity, ((int) System.currentTimeMillis()),userBean.getUserinfo().getUserid());

                updateUser(userBean.getUserinfo());
                getMyApplication().updateTokenAndUid(userBean.getUserinfo().getToken(), userBean.getUserinfo().getUserid());
                getUserInfo();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);

                if (mOnLoginListener != null) {
                    mOnLoginListener.onLoginFaild();
                }
            }
        },mBaseActivity,paramsMap);
        HttpManager.getInstance().doHttpDeal(apiWechatLoginNew);
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

        UserBean userBean = getUserBean();
        if (userBean == null) {
            return;
        }

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiUserInfo.uid, userBean.getUserid());
        ApiUserInfo apiUserInfo = new ApiUserInfo(new HttpOnNextListener<UserBean>() {

            @Override
            public void onNext(UserBean userBean) {
                getMyApplication().updateUser(userBean);
                updateUser(userBean);

                if (mOnLoginListener != null) {
                    mOnLoginListener.onLoginSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);

                if (mOnLoginListener != null) {
                    mOnLoginListener.onLoginFaild();
                }
            }
        }, mBaseActivity, paramsMap);

        HttpManager.getInstance().doHttpDeal(apiUserInfo);
    }


    public interface OnLoginListener{
        public void onLoginSuccess();
        public void onLoginStart();
        public void onLoginFaild();
    }
}
