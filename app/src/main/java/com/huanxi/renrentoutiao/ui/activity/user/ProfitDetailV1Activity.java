package com.huanxi.renrentoutiao.ui.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.user.ApiUserWallettotal;
import com.huanxi.renrentoutiao.net.api.user.task.ApiUserGoldCoinDetailList;
import com.huanxi.renrentoutiao.net.api.user.task.ApiUserMoneyDetailList;
import com.huanxi.renrentoutiao.net.bean.ResWallettotalBean;
import com.huanxi.renrentoutiao.net.bean.money.ResGoldDetailList;
import com.huanxi.renrentoutiao.net.bean.money.ResMoneyDetailList;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.activity.other.LuckyWalkActivity;
import com.huanxi.renrentoutiao.ui.adapter.bean.GoldDetailBean;
import com.huanxi.renrentoutiao.ui.adapter.bean.MoneyDetailBean;
import com.huanxi.renrentoutiao.ui.fragment.user.profit.GoldProfitDetailFragment;
import com.huanxi.renrentoutiao.ui.fragment.user.profit.MoneyProfitDetailFragment;
import com.huanxi.renrentoutiao.utils.FormatUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Dinosa on 2018/1/20.
 * 现金金额 和 已获金币 公用
 */

public class ProfitDetailV1Activity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_wallet_title)
    TextView mTvWalletTitle;
    //    @BindView(R.id.ll_small_change_number)
//    LinearLayout mLlSmallChangeNumber;
    @BindView(R.id.rv_home)
    RecyclerView mRvHome;
    @BindView(R.id.rl_refreshLayout)
    EasyRefreshLayout mRlRefreshLayout;
    //    @BindView(R.id.tv_withdrawals)
//    TextView mTvWithdrawals;
//    @BindView(R.id.tv_progress)
//    TextView mTvProgress;
//    @BindView(R.id.cb_gold_coin)
//    CheckBox mCbGoldCoin;
//    @BindView(R.id.cb_money)
//    CheckBox mCbMoney;
    @BindView(R.id.tv_wallet_money)
    TextView mTvWalletMoney;
    @BindView(R.id.tv_has_get_total_money)
    TextView mTvHasGetTotalMoney;
//    @BindView(R.id.tv_yesterday_profit)
//    TextView mTvYesterdayProfit;
//    @BindView(R.id.tv_yesterday_rate)
//    TextView mTvYesterdayRate;
    @BindView(R.id.tv_record_title)
    TextView tvRecordTitle;
    @BindView(R.id.iv_record_icon)
    ImageView ivRecordIcon;
    @BindView(R.id.iv_bill_bg)
    ImageView ivBillBg;

    @BindView(R.id.tv_right)
    public TextView mTvRight;

    private ProfitMoneyDetailAdapter mProfitMoneyDetailAdapter;
    UserBean userBean;

    /**
     * 现金余额 CASH 和 已获金币 GOLD 公用 作用于区分
     */
    public enum Type {
        CASH, GOLD
    }

    Type currentType = Type.CASH;

    @Override
    public int getContentLayout() {
        return R.layout.activity_profit_detail_v1;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {

        setStatusBarImmersive(null);
        mRvHome.setLayoutManager(new LinearLayoutManager(this));
        mProfitMoneyDetailAdapter = new ProfitMoneyDetailAdapter(null);
        mRvHome.setAdapter(mProfitMoneyDetailAdapter);

        mRlRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                //这里要做第一是一个加载更多的逻辑
                getMoreProfitDetail();
            }

            @Override
            public void onRefreshing() {
                getDataProfitDetail();
            }
        });

    }

    @OnClick(R.id.tv_right)
    public void onClickRight(){

        //这里我们要判断一下是金币，还是余额的操作

        if (currentType.equals(Type.GOLD)) {

            Intent intent = LuckyWalkActivity.getIntent(this,false);
            startActivity(intent);

        }else{
            Intent intent = LuckyWalkActivity.getIntent(this,true);
            startActivity(intent);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        userBean = getUserBean();
        String menuType = getIntent().getStringExtra(MENU_TYPE);
        if(!TextUtils.isEmpty(menuType)) {
            currentType = Type.valueOf(menuType);
            updateUI(null);
            //getDataProfitDetail();
            getWalletTotalMoney();
            if(currentType.equals(Type.GOLD)){
                //这里表示金币的Fragment;
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container,new GoldProfitDetailFragment())
                        .commitAllowingStateLoss();
            }else {
                //这里表示余额的Fragment;
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container,new MoneyProfitDetailFragment())
                        .commitAllowingStateLoss();
            }



            if (currentType.equals(Type.GOLD)) {
                mTvRight.setText("金币娱乐");
            }else{
                mTvRight.setText("提现");
            }
        }
    }

    private ProfitGoldCoinDetailAdapter mGoldCoinDetailAdapter;
    private ProfitMoneyDetailAdapter mMoneyDetailAdapter;


    /**
     * 这里是具体的具体的数据；
     */
    private void getDataProfitDetail() {
        page = 1;
        ArrayList<GoldDetailBean> moneyFlowBeen = new ArrayList<>();
        if (currentType.equals(Type.GOLD)) {
            //这里是金币的明细；
            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put(ApiUserGoldCoinDetailList.FROM_UID, getUserBean().getUserid());

            ApiUserGoldCoinDetailList apiUserGoldCoinDetailList = new ApiUserGoldCoinDetailList(new HttpOnNextListener<ResGoldDetailList>() {


                @Override
                public void onNext(ResGoldDetailList resGoldDetailList) {

                    if (resGoldDetailList.getList() != null) {
                        //这里表示有数据；
                        mGoldCoinDetailAdapter = new ProfitGoldCoinDetailAdapter(resGoldDetailList.getList());
                        mRvHome.setAdapter(mGoldCoinDetailAdapter);
                        if (resGoldDetailList.getList().size() > 0) {
                            page++;
                        }
                    }
                    mRlRefreshLayout.refreshComplete();
                }

            }, this, paramsMap);
            HttpManager.getInstance().doHttpDeal(apiUserGoldCoinDetailList);
        } else {
            //这里是余额的明细；
            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put(ApiUserMoneyDetailList.FROM_UID, getUserBean().getUserid());
            ApiUserMoneyDetailList apiUserMoneyDetailList = new ApiUserMoneyDetailList(new HttpOnNextListener<ResMoneyDetailList>() {

                @Override
                public void onNext(ResMoneyDetailList resMoneyDetailList) {
                    //这里我们要做的一个操作就是更新adapter；

                    if (resMoneyDetailList.getList() != null) {

                        mMoneyDetailAdapter = new ProfitMoneyDetailAdapter(resMoneyDetailList.getList());
                        mRvHome.setAdapter(mMoneyDetailAdapter);
                        if (resMoneyDetailList.getList().size() > 0) {
                            page++;
                        }
                        mRlRefreshLayout.refreshComplete();
                    }
                }
            }, this, paramsMap);

            HttpManager.getInstance().doHttpDeal(apiUserMoneyDetailList);
        }
    }

    @OnClick(R.id.tv_back)
    public void onClickBack() {
        finish();
    }

    int page = 1;

    public void getMoreProfitDetail() {
        if (currentType.equals(Type.GOLD)) {
            //这里表示金币的更多
            //这里是金币的明细；
            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put(ApiUserGoldCoinDetailList.FROM_UID, getUserBean().getUserid());
            paramsMap.put(ApiUserGoldCoinDetailList.PAGE_NUM, page + "");

            ApiUserGoldCoinDetailList apiUserGoldCoinDetailList = new ApiUserGoldCoinDetailList(new HttpOnNextListener<ResGoldDetailList>() {


                @Override
                public void onNext(ResGoldDetailList resGoldDetailList) {

                    if (resGoldDetailList.getList() != null && resGoldDetailList.getList().size() > 0) {
                        //这里表示有数据；
                        mGoldCoinDetailAdapter.addData(resGoldDetailList.getList());
                        page++;
                    } else {
                        toast("没有更多数据");
                    }
                    mRlRefreshLayout.loadMoreComplete();
                }

            }, this, paramsMap);
            apiUserGoldCoinDetailList.setShowProgress(false);
            HttpManager.getInstance().doHttpDeal(apiUserGoldCoinDetailList);
        } else {
            //这里表示余额的更多；
            //这里是余额的明细；
            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put(ApiUserMoneyDetailList.FROM_UID, getUserBean().getUserid());
            paramsMap.put(ApiUserMoneyDetailList.PAGE_NUM, page + "");
            ApiUserMoneyDetailList apiUserMoneyDetailList = new ApiUserMoneyDetailList(new HttpOnNextListener<ResMoneyDetailList>() {

                @Override
                public void onNext(ResMoneyDetailList resMoneyDetailList) {
                    //这里我们要做的一个操作就是更新adapter；

                    if (resMoneyDetailList.getList() != null && resMoneyDetailList.getList().size() > 0) {
                        //这里表示有数据；
                        mMoneyDetailAdapter.addData(resMoneyDetailList.getList());
                        page++;
                    } else {
                        toast("没有更多数据");
                    }
                    mRlRefreshLayout.loadMoreComplete();
                }
            }, this, paramsMap);
            apiUserMoneyDetailList.setShowProgress(false);
            HttpManager.getInstance().doHttpDeal(apiUserMoneyDetailList);
        }
    }

    /**
     * 这里是获取收益明细的一个方法;
     */
    public void getWalletTotalMoney() {


        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiUserWallettotal.FROM_UID, getUserBean().getUserid());
        ApiUserWallettotal apiUserWallettotal = new ApiUserWallettotal(new HttpOnNextListener<ResWallettotalBean>() {

            @Override
            public void onNext(ResWallettotalBean resWallettotalBean) {
                //这里是汇率之类的一个接口；
                updateUI(resWallettotalBean);
            }
        }, this, paramsMap);

        HttpManager.getInstance().doHttpDeal(apiUserWallettotal);
    }

    /**
     * 这里是金币明细的adapter
     */
    public class ProfitGoldCoinDetailAdapter extends BaseQuickAdapter<GoldDetailBean, BaseViewHolder> {

        public ProfitGoldCoinDetailAdapter(@Nullable List<GoldDetailBean> data) {
            super(R.layout.item_profit_detail_v1, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoldDetailBean item) {
            //这里表示10元钱
            helper.setText(R.id.tv_title, item.getDes());
            helper.setText(R.id.tv_time, item.getAddtime());

            String desc = "";
            int color;
            if (item.getType().equals("2")) {
                desc = "-" + item.getPrice() + "金币";
                color = ContextCompat.getColor(ProfitDetailV1Activity.this, R.color.color_19bf5c);
            } else {
                desc = "+" + item.getPrice() + "金币";
                color = ContextCompat.getColor(ProfitDetailV1Activity.this, R.color.color_fb7c53);
            }
            helper.setText(R.id.tv_gold_flow, desc);

            helper.setTextColor(R.id.tv_gold_flow, color);
        }
    }


    /**
     * 这里是余额明细的Adapter
     */
    public class ProfitMoneyDetailAdapter extends BaseQuickAdapter<MoneyDetailBean, BaseViewHolder> {


        public ProfitMoneyDetailAdapter(@Nullable List<MoneyDetailBean> data) {
            super(R.layout.item_profit_detail_v1, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MoneyDetailBean item) {
            //这里表示10金币
            helper.setText(R.id.tv_title, item.getDes());
            helper.setText(R.id.tv_time, item.getAddtime());
            String desc = "";
            int color;
            if (item.getType().equals("24")) {
                desc = "+" + item.getPrice() + "元";
                color = ContextCompat.getColor(ProfitDetailV1Activity.this, R.color.color_fb7c53);
            } else {
                desc = "-" + item.getPrice() + "元";
                color = ContextCompat.getColor(ProfitDetailV1Activity.this, R.color.color_19bf5c);
            }
            helper.setText(R.id.tv_gold_flow, desc);

            helper.setTextColor(R.id.tv_gold_flow, color);
        }
    }


    /**
     * 这里我们要修改页面；
     *
     * @param resWallettotalBean
     */
    private void updateUI(ResWallettotalBean resWallettotalBean) {
        if (resWallettotalBean == null) resWallettotalBean = new ResWallettotalBean();
        switch (currentType) {
            case CASH:
                showCashUI(resWallettotalBean);
                break;
            case GOLD:
                showGoldUI(resWallettotalBean);
                break;
        }
    }


    void showCashUI(ResWallettotalBean resWallettotalBean) {
        mTvTitle.setText("钱包余额");
        mTvWalletTitle.setText("当前余额");
        tvRecordTitle.setText("账单记录");
        mTvHasGetTotalMoney.setText(Html.fromHtml("累计总收益:<font color=#ff7c34>" + FormatUtils.decimalFormatTwo(resWallettotalBean.getTotalmoney()) + "元</font>"));
        mTvWalletMoney.setText(FormatUtils.decimalFormatTwo(resWallettotalBean.getLastmoney()));
        ivBillBg.setImageResource(R.drawable.bg_profit_cash);
        ivRecordIcon.setImageResource(R.drawable.icon_bill);
    }

    void showGoldUI(ResWallettotalBean resWallettotalBean) {
        mTvTitle.setText("已获金币");
        mTvWalletTitle.setText("已获得金币");
        tvRecordTitle.setText("金币记录");
        mTvHasGetTotalMoney.setText(Html.fromHtml("昨日汇率（元）：<br/><font color=#ff7c34>1000金币=" + FormatUtils.decimalFormatTwo(resWallettotalBean.getRate()) + "元</font>"));
        mTvWalletMoney.setText(userBean.getIntegral());
        ivBillBg.setImageResource(R.drawable.bg_profit_gold);
        ivRecordIcon.setImageResource(R.drawable.icon_gold);
    }

    public static final String MENU_TYPE = "type";

    /**
     * 这里我们要做的一个操作就是显示具体的金额
     *
     * @return
     */
    public static Intent getIntent(Context context , String type) {

        Intent intent = new Intent(context, ProfitDetailV1Activity.class);
        intent.putExtra(MENU_TYPE, type);
        return intent;
    }

    //    @OnClick(R.id.iv_question)
    public void onClickQuestion() {
        startActivity(new Intent(this, QuestionsActivity.class));
    }

}
