package com.huanxi.renrentoutiao.ui.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.net.bean.money.ApiUserWithdrawals;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

import static com.huanxi.renrentoutiao.R.id.et_withdrawals_money;

/**
 * 这里是提现的activity;
 */
public class WithdrawalsActivity extends BaseTitleActivity {

    @BindView(R.id.et_pay_username)
    EditText mEtPayNumber;

    @BindView(R.id.et_real_name)
    EditText mEtRealName;

    @BindView(et_withdrawals_money)
    EditText mEtWithdrawalsMoney;

    @BindView(R.id.tv_can_withdrawals_money)
    TextView mTvCanWithdrawalsMoney;

    @BindView(R.id.tv_request)
    TextView mTvRequest;
    private UserBean mUserBean;

    @Override
    public int getBodyLayoutId() {
        return R.layout.activity_withdrawals;
    }


    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        super.initView(rootView, savedInstanceState);
        setTitle("提现");
        setBackText("");
        //setRightTextAndDrawable("", R.drawable.icon_withdrawals_progress);

        setRightText("提现进度");
        mTvRight.setTextColor(Color.BLACK);


        //设置多少元起可以提现；
        mEtWithdrawalsMoney.setHint(getUserBean().getLimitmoney()+"元起提");
    }


    @Override
    protected void initData() {
        super.initData();
        //这里我们要做的一个操作就是：
        mUserBean = getUserBean();
        mTvCanWithdrawalsMoney.setText("￥"+ mUserBean.getMoney());

    }

    /**
     * 这里是title右边的按钮;
     */
    @OnClick(R.id.tv_right_option)
    public void onClickTitleRightButton() {
        //这里我们是需要条跳转到提现进度；
        startActivity(new Intent(this, WithdrawalRecordsActivity.class));
    }

    @OnClick(R.id.tv_request)
    public void onClickRequestWithdrawals() {
        //这里我们要做的一个操作就是申请提现；
        String payUsername = mEtPayNumber.getText().toString();
        String realName = mEtRealName.getText().toString();
        String withdrawalsMoney = mEtWithdrawalsMoney.getText().toString();

        if (validInputData(payUsername,realName,withdrawalsMoney)) {
            //这里表示输入是有效果的；
            //这里做提现的操作；
            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put(ApiUserWithdrawals.FROM_UID,getUserBean().getUserid());
            paramsMap.put(ApiUserWithdrawals.ALIPAY_ACCOUNT,payUsername);
            paramsMap.put(ApiUserWithdrawals.REAL_NAME,realName);
            paramsMap.put(ApiUserWithdrawals.MONEY,withdrawalsMoney);

            ApiUserWithdrawals apiUserWithdrawals=new ApiUserWithdrawals(new HttpOnNextListener<ResEmpty>() {

                @Override
                public void onNext(ResEmpty resEmpty) {
                    //这里表示提现成功；
                    startActivity(new Intent(WithdrawalsActivity.this,WithdrawalRecordsActivity.class));
                }
            },this,paramsMap);
            HttpManager.getInstance().doHttpDeal(apiUserWithdrawals);
        }
    }

    /**
     * 校验输入信息；
     */
    public boolean validInputData(String payUsername,String realName,String withdrawalsMoney){

        if (TextUtils.isEmpty(payUsername)) {
            toast("支付宝账号不能为空!!!");
            return false;
        }
        //验证电话号号码的合法性；
        if (TextUtils.isEmpty(realName)) {
            toast("真实姓名不能为空!!!");
            return false;
        }
        float withdraw = 0;
        try {
            withdraw = Float.parseFloat(withdrawalsMoney);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(withdrawalsMoney) || withdraw < getUserBean().getLimitmoney()) {
            toast("提现金额最低"+getUserBean().getLimitmoney()+"元!!!");
            return false;
        }

        float v = 0;

        try {
            v = Float.parseFloat(mUserBean.getMoney());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (withdraw > v) {
            toast("提现金额最多"+v+"元");
            return false;
        }
        return true;
    }

}
