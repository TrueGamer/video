package com.huanxi.renrentoutiao.ui.activity.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.ApiSMSSend;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiBindPhoneNumber;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiOnlyBindPhoneNumber;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.net.bean.ResSplashAds;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.ui.activity.other.LuckyWalkActivity;
import com.huanxi.renrentoutiao.ui.adapter.AdsAdapter;
import com.huanxi.renrentoutiao.utils.ValidUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 这里是积分商城的Activity
 */
public class IntergralShopActivity extends BaseTitleActivity {

    @BindView(R.id.rv_income)
    public RecyclerView mRvIncome;

    @BindView(R.id.rv_ads)
    public RecyclerView mRvAds;


    private AdsAdapter mAdsAdapter;
    private View mIntergralShopHeaderView;

    @Override
    public int getBodyLayoutId() {
        return R.layout.activity_intergral_shop;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        super.initView(rootView,savedInstanceState);
        setTitle("积分商城");
        setBackText("");

        setRightText("金币娱乐");
        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntergralShopActivity.this, LuckyWalkActivity.class);
                startActivity(intent);
            }
        });

        mRvIncome.setLayoutManager(new LinearLayoutManager(this));

        initHeaderView();

        mAdsAdapter = new AdsAdapter(null);
        mAdsAdapter.addHeaderView(mIntergralShopHeaderView);

        mRvIncome.setAdapter(mAdsAdapter);


        if(isHasAds()){
            MyApplication application = (MyApplication) getApplication();
            ResSplashAds resAds = application.getResAds();

            mRvAds.setLayoutManager(new LinearLayoutManager(this));
            AdsAdapter adsAdapter = new AdsAdapter(resAds.getExchange());
            mRvAds.setAdapter(adsAdapter);
        }
    }

    private void initHeaderView() {

        mIntergralShopHeaderView = LayoutInflater.from(this).inflate(R.layout.item_intershop_header, mRvIncome,false);

        mIntergralShopHeaderView.findViewById(R.id.tv_withdrawals)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserBean userBean = getUserBean();
                if(!TextUtils.isEmpty(userBean.getPhone())){
                    onClickWithdrawals();
                }else{
                    //这里要绑定手机号；
                    doShowAddPhoneNumber();
                }
            }
        });

    }

    public void onClickWithdrawals(){
        //这里我们要做的一个操作就是提现；
        startActivity(new Intent(this,WithdrawalsActivity.class));
    }

    @OnClick(R.id.iv_exposure_income)
    public void onClickExposureIncome(){
        startActivity(new Intent(this,ExposureIncomeActivity.class));
    }



    /**
     * 这里我们是需要绑定手机号的一个操作；
     */
    private void doShowAddPhoneNumber() {

        final HashMap<String ,String > paramsMap=new HashMap<>();
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
                if(!ValidUtils.isMobileNO(trim)){
                    toast("请输入正确的手机号!");
                }

                //发送短信的一个方法；
                ApiSMSSend.defaultSend(IntergralShopActivity.this,tvSendCode,trim,ApiSMSSend.BIND_PHONE_CODE);
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
                        bindPhoneNumber(paramsMap,alertDialog,trim);
                    }
                });
            }
        });
        alertDialog.show();
    }


    /**
     * 这里要执行绑定手机号的逻辑；
     */
    private void bindPhoneNumber(final HashMap<String,String> paramsMap, final AlertDialog alertDialog, final String phoneNumber) {


        ApiOnlyBindPhoneNumber apiBindPhoneNumber=new ApiOnlyBindPhoneNumber(new HttpOnNextListener<ResEmpty>() {
            @Override
            public void onNext(ResEmpty userStr) {

                //这表示绑定成功；
                toast("绑定成功！");
                //这里是需要将绑定给移除的；this
                alertDialog.dismiss();

                //这里要更新
                UserBean userBean = getUserBean();
                userBean.setPhone(phoneNumber);
                updateUser(userBean);
            }

        },this,paramsMap);
        HttpManager.getInstance().doHttpDeal(apiBindPhoneNumber);
    }

}
