package com.huanxi.renrentoutiao.ui.activity.other;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.ApiSMSSend;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiBindPhoneNumber;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiUserInfo;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.utils.GsonUtils;
import com.huanxi.renrentoutiao.utils.SecurityUtils;
import com.huanxi.renrentoutiao.utils.ValidUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

import static com.huanxi.renrentoutiao.ui.dialog.RedPacketDialog.alertDialog;

/**
 * Created by Dinosa on 2018/3/29.
 */

public class BindPhoneNumberActivity extends BaseActivity {

    @BindView(R.id.et_phone_number_code)
    EditText mEtPhoneNumberCode;

    @BindView(R.id.tv_get_phone_number)
    TextView mTvGetPhoneNumber;

    @BindView(R.id.et_phone_number)
    EditText mEtPhoneNumber;

    @BindView(R.id.tv_bindphone_number)
    TextView mTvBindphoneNumber;
    private HashMap<String, String> mWechatParamsMap;


    @Override
    public int getContentLayout() {
        return R.layout.activity_bind_phone_number;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        setStatusBarImmersive(null);
    }


    @OnClick(R.id.ll_back)
    public void onClickBack(){
        finish();
    }

    @OnClick(R.id.tv_bindphone_number)
    public void onClickBindPhoneNumber(){
        //绑定手机号；

        String trim = mEtPhoneNumber.getText().toString().trim();
        if(!ValidUtils.isMobileNO(trim)){
            toast("请输入正确的手机号!");
            return;
        }
        String phoneCode = mEtPhoneNumberCode.getText().toString().trim();
        if(TextUtils.isEmpty(phoneCode)){
            toast("验证码不能为空!");
            return;
        }

        mWechatParamsMap.put(ApiBindPhoneNumber.PHONE_NUMBER,trim);
        mWechatParamsMap.put(ApiBindPhoneNumber.PHONE_CODE,phoneCode);
        //这里实现绑定手机号的逻辑
        bindPhoneNumber(mWechatParamsMap,alertDialog);

    }

    @OnClick(R.id.tv_get_phone_number)
     public void onClickNumberCode(){
        //点击发送验证码

        String trim = mEtPhoneNumber.getText().toString().trim();
        if(!ValidUtils.isMobileNO(trim)){
            toast("请输入正确的手机号!");
            return;
        }
        //发送短信的一个方法；
        ApiSMSSend.defaultSend(BindPhoneNumberActivity.this,mTvGetPhoneNumber,trim,ApiSMSSend.BIND_PHONE_CODE);
    }

    /**
     * 这里要执行绑定手机号的逻辑；
     */
    private void bindPhoneNumber(HashMap<String,String> paramsMap, final AlertDialog alertDialog) {

        ApiBindPhoneNumber apiBindPhoneNumber=new ApiBindPhoneNumber(new HttpOnNextListener<String>() {
            @Override
            public void onNext(String userStr) {
                //
                //这里表示登陆成功；
                String data = decodeStr(userStr);
                UserBean userBean = GsonUtils.getInstance().toBean(data, UserBean.class);

                getMyApplication().updateTokenAndUid(userBean.getToken(),userBean.getUserid());

                updateUser(userBean);
                getUserInfo(alertDialog);
            }

        },this,paramsMap);
        HttpManager.getInstance().doHttpDeal(apiBindPhoneNumber);
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

    /**
     * 这里我们对服务器返回的数据进行一个解密的操作；
     * @param userBeanStr rsa的一个数据操作；
     * @return
     */
    private String decodeStr(String userBeanStr) {

        return SecurityUtils.decodeRsaData(userBeanStr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWechatParamsMap = (HashMap<String, String>) getIntent().getSerializableExtra(WECHAT_PARAMS);
        //这里是参数的信息；
    }

    public static final String WECHAT_PARAMS="params";

    public static Intent getIntent(Context context,HashMap<String,String> params){

        Intent intent = new Intent(context, BindPhoneNumberActivity.class);
        intent.putExtra(WECHAT_PARAMS,params);
        return intent;
    }

}
