package com.huanxi.renrentoutiao.ui.activity.user;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.content.Constants;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.user.userInfo.ApiUserInfo;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.net.bean.money.ApiUserWithdrawals;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.ui.adapter.TxNumAdapter;
import com.huanxi.renrentoutiao.ui.adapter.bean.TxNum;
import com.huanxi.renrentoutiao.utils.ImageUtils;
import com.huanxi.renrentoutiao.utils.TToast;
import com.huanxi.renrentoutiao.utils.UIUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
/**
 * 这里是提现的activity;
 */
public class WithdrawalsActivity2 extends BaseTitleActivity{
    private static final String TAG = "WithdrawalsActivity";

    @BindView(R.id.et_pay_username)
    EditText mEtPayNumber;

    @BindView(R.id.et_real_name)
    EditText mEtRealName;

    @BindView(R.id.et_withdrawals_money)
    EditText mEtWithdrawalsMoney;

    @BindView(R.id.tv_can_withdrawals_money)
    TextView mTvCanWithdrawalsMoney;

    @BindView(R.id.tv_request)
    TextView mTvRequest;

    @BindView(R.id.rv)
    RecyclerView rv;


    @BindView(R.id.ll_alipay)
    LinearLayout ll_alipay;

    @BindView(R.id.ll_wechat)
    LinearLayout ll_wechat;


    @BindView(R.id.v_alipay)
    View v_alipay;


    @BindView(R.id.v_wechat)
    View v_wechat;

    @BindView(R.id.rl_wechat_show)
    LinearLayout rl_wechat_show;

    @BindView(R.id.ll_alipay_show)
    LinearLayout ll_alipay_show;

    @BindView(R.id.iv_user)
    CircleImageView mIvUserIcon;

    @BindView(R.id.tv_nickname)
    TextView tv_nickname;

    @BindView(R.id.tv_withdrawdes)
    TextView tv_withdrawdes;

    private TxNumAdapter adapter = null;
    private UserBean user;

    /**
     * 支付类型
     */
    private String payType = Constants.KEY_ALIPAY;

    @Override
    public int getBodyLayoutId() {
        return R.layout.activity_withdrawals2;
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
//        mEtWithdrawalsMoney.setHint(getUserBean().getLimitmoney()+"元起提");

//        User user = (MyApplication).getU;
        user = ((MyApplication)getApplication()).getUserBean();
//        mIvUserIcon.setImageURI(user.getAvatar());
        ImageUtils.loadImage(this, user.getAvatar(), mIvUserIcon);
        tv_nickname.setText(user.getNickname());

//        String jsonstr = ConfigUtils.INSTANCE.getConfig(Constants.KEY_WITHDRAWDES);
//        tv_withdrawdes.setText(jsonstr);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        adapter = new TxNumAdapter(this);

        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = (int) getResources().getDimension(R.dimen.dp_5);
                outRect.bottom = (int) getResources().getDimension(R.dimen.dp_5);
                outRect.left = (int) getResources().getDimension(R.dimen.dp_5);
                outRect.right = (int) getResources().getDimension(R.dimen.dp_5);
            }
        });
        rv.setLayoutManager(gridLayoutManager);
        rv.setAdapter(adapter);

        bindRV();
    }


    @Override
    protected void initData() {
        super.initData();
        //这里我们要做的一个操作就是：
        UserBean user = ((MyApplication)getApplication()).getUserBean();
        mTvCanWithdrawalsMoney.setText(user.getMoney() + "元");

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

        int withdrawalsMoney = adapter.getWithDrawMoney();
        if (payType.equals(Constants.KEY_ALIPAY)) {
            if (validInputData(payUsername, realName, withdrawalsMoney)) {
                //这里表示输入是有效果的；
                //这里做提现的操作；
//                sendReq(payUsername, withdrawalsMoney, realName, "zhifubao");
                TToast.show(this , "支付宝提现正在建设中，请使用微信提现!!!");
            }
        } else {
            if (validInputData(payUsername, realName, withdrawalsMoney)) {
                final Dialog dialog = new Dialog(this, R.style.dialog1);
                dialog.setContentView(R.layout.dialog_simple);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                TextView msgTextView = (TextView) dialog.findViewById(R.id.msg);
                msgTextView.setText("需要关注公众号才可以提现，现在马上关注吗？");
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (v.getId() == R.id.btn_confirm) {
                            jumpWCApp();
                            sendReq(payUsername, withdrawalsMoney, realName, "weixin");

                        } else {
                            sendReq(payUsername, withdrawalsMoney, realName, "weixin");
                        }
                    }
                };
                dialog.findViewById(R.id.btn_cancel).setOnClickListener(listener);
                dialog.findViewById(R.id.btn_confirm).setOnClickListener(listener);
                dialog.show();
            }
        }
    }

    private void jumpWCApp(){
        String appId = "wx39476c2ddeeee7da"; // 填应用AppId
        IWXAPI api = WXAPIFactory.createWXAPI(this, appId);

        if(api.isWXAppInstalled()) {
            WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
            req.userName = "gh_5b7e29b40445"; // 小程序原始id  gh_b6664957bab7
            req.path = "";    //拉起小程序页面的可带参路径，不填默认拉起小程序首页
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;
            api.sendReq(req);


        } else {
            Toast.makeText(this , "请安装微信" , Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.btn_wechat)
    public void onClickWechat() {
        String mSwitch = "0";
        if ("0".equals(mSwitch)) {
            mTvRequest.setClickable(true);
            payType = Constants.KEY_WECHAT;
            v_alipay.setVisibility(View.INVISIBLE);
            v_wechat.setVisibility(View.VISIBLE);
            ll_alipay_show.setVisibility(View.GONE);
            rl_wechat_show.setVisibility(View.VISIBLE);
            mEtRealName.setText("");
            mEtRealName.setHint(getResources().getString(R.string.tv_tx_wechat_ts));

        } else {
//            ToastUtils.INSTANCE.show(
//                    getResources().getString(
//                            R.string.tv_tx_zwkt));
            TToast.show(this , getResources().getString(
                    R.string.tv_tx_zwkt));
        }
    }


    @OnClick(R.id.btn_alipay)
    public void onClickAlipay() {
        mTvRequest.setClickable(true);
        payType = Constants.KEY_ALIPAY;
        v_wechat.setVisibility(View.INVISIBLE);
        v_alipay.setVisibility(View.VISIBLE);
        ll_alipay_show.setVisibility(View.VISIBLE);
        rl_wechat_show.setVisibility(View.GONE);
        mEtRealName.setText("");
        mEtRealName.setHint(getResources().getString(R.string.tv_tx_alipay_ts));
    }

    /**
     * 绑定提现金额
     */
    private void bindRV() {
        user = ((MyApplication)getApplication()).getUserBean();
//        if (withdrawQuotaReplay == null || user == null) {
//            return;
//        }

//        balance = user.getLimitmoney() / 100f;
        mTvCanWithdrawalsMoney.setText(user.getMoney() + "元");
        List<TxNum> listTN = new ArrayList<>();
//        List<Integer> withdrawLevelsList = SharedPreferencesUtils
//                .getInstance(this)
//                .getWithdraw();
//        if (null == withdrawLevelsList || withdrawLevelsList.size() <= 0)
//            return;
//        int count = withdrawLevelsList.size();
//        for (int i = 0; i < count; i++) {
//            TxNum t = new TxNum(withdrawLevelsList.get(i), false);
//            switch (t.getNum()) {
//                case 500:
//                    t.setLeft(withdrawQuotaReplay.getWithdraw5Quota() - withdrawQuotaReplay.getWithdraw5Used());
//                    break;
//                case 1500:
//                    t.setLeft(withdrawQuotaReplay.getWithdraw15Quota() - withdrawQuotaReplay.getWithdraw15Used());
//                    break;
//                case 3000:
//                    t.setLeft(withdrawQuotaReplay.getWithdraw30Quota() - withdrawQuotaReplay.getWithdraw30Used());
//                    break;
//                case 5000:
//                    t.setLeft(withdrawQuotaReplay.getWithdraw50Quota() - withdrawQuotaReplay.getWithdraw50Used());
//                    break;
//            }
//            listTN.add(t);
//        }

        TxNum txNum = new TxNum(10 , true);
        txNum.setLeft(100);
        listTN.add(txNum);
        adapter.setData(listTN);
        adapter.selectedDefaultItem();
    }


    /**
     * 请求相关
     */
    private void sendReq(String alipay, final int amount, String realName, String payType2) {

        if (UIUtils.isFastTXClick()) {
//            new TaskWithdraw().withDraw(amount, alipay, realName, payType, new CallBack<EmptyReply>() {
//                @Override
//                public void response(EmptyReply reply) {
//                    if (null == reply)
//                        return;
//                    float money = amount / 100f;
//                    mTvCanWithdrawalsMoney.setText("￥" + FormatUtils.decimalFormatTwo(balance - money));
//                    startActivity(new Intent(WithdrawalsActivity2.this, WithdrawalRecordsActivity.class));
//                }
//            });
            String realName2 = mEtRealName.getText().toString();

            //这里表示输入是有效果的；
            //这里做提现的操作；
            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put(ApiUserWithdrawals.FROM_UID , getUserBean().getUserid());
            paramsMap.put(ApiUserWithdrawals.ALIPAY_ACCOUNT , alipay);
            paramsMap.put(ApiUserWithdrawals.REAL_NAME , realName2);
            paramsMap.put(ApiUserWithdrawals.MONEY , amount+"");
            paramsMap.put(ApiUserWithdrawals.PayType , payType2);

            ApiUserWithdrawals apiUserWithdrawals=new ApiUserWithdrawals(new HttpOnNextListener<ResEmpty>() {

                @Override
                public void onNext(ResEmpty resEmpty) {
                    //这里表示提现成功；
                    startActivity(new Intent(WithdrawalsActivity2.this,WithdrawalRecordsActivity.class));
                }
            },this,paramsMap);
            HttpManager.getInstance().doHttpDeal(apiUserWithdrawals);

        }

    }

    /**
     * 校验输入信息；
     */
    public boolean validInputData(String payUsername, String realName, int withdrawalsMoney) {

        UserBean user = ((MyApplication)getApplication()).getUserBean();
        if (withdrawalsMoney <= 0) {
            toast("请选择提现金额");
            return false;
        }

        if (withdrawalsMoney > Float.parseFloat(user.getMoney())) {
            toast("余额不足");
            return false;
        }

        if (withdrawalsMoney < getUserBean().getLimitmoney()) {
            toast("提现金额最低"+getUserBean().getLimitmoney()+"元!!!");
            return false;
        }

//        if (TextUtils.isEmpty(payUsername)) {
//            toast("支付宝账号不能为空!!!");
//            return false;
//        } else {
//            if ((payUsername.contains("@") || payUsername.length() == 11)) {
//            } else {
//                toast("手机或邮箱格式不正确!!!");
//            }
//        }

        //验证电话号号码的合法性；
        if (TextUtils.isEmpty(realName)) {
            toast("真实姓名不能为空!!!");
            return false;
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTvRequest.setClickable(true);
        if(isLogin()){
            getUserInfo();
        }
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
                ((MyApplication) getApplication()).updateUser(userBean);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, this, paramsMap);

        HttpManager.getInstance().doHttpDeal(apiUserInfo);
    }

}
