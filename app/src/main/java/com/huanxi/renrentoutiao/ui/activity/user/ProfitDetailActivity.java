package com.huanxi.renrentoutiao.ui.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.user.ApiUserWallettotal;
import com.huanxi.renrentoutiao.net.api.user.task.ApiUserGoldCoinDetailList;
import com.huanxi.renrentoutiao.net.api.user.task.ApiUserMoneyDetailList;
import com.huanxi.renrentoutiao.net.bean.ResWallettotalBean;
import com.huanxi.renrentoutiao.net.bean.money.ResGoldDetailList;
import com.huanxi.renrentoutiao.net.bean.money.ResMoneyDetailList;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.adapter.bean.GoldDetailBean;
import com.huanxi.renrentoutiao.ui.adapter.bean.MoneyDetailBean;
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
 */

public class ProfitDetailActivity extends BaseActivity {


    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.ll_gold_number)
    LinearLayout mLlGoldNumber;
    @BindView(R.id.ll_small_change_number)
    LinearLayout mLlSmallChangeNumber;
    @BindView(R.id.rv_home)
    RecyclerView mRvHome;
    @BindView(R.id.rl_refreshLayout)
    EasyRefreshLayout mRlRefreshLayout;

    @BindView(R.id.tv_withdrawals)
    TextView mTvWithdrawals;

    @BindView(R.id.tv_progress)
    TextView mTvProgress;

    @BindView(R.id.cb_gold_coin)
    CheckBox mCbGoldCoin;
    @BindView(R.id.cb_money)
    CheckBox mCbMoney;
    @BindView(R.id.tv_wallet_money)
    TextView mTvWalletMoney;
    @BindView(R.id.tv_has_get_total_money)
    TextView mTvHasGetTotalMoney;
    @BindView(R.id.tv_yesterday_profit)
    TextView mTvYesterdayProfit;
    @BindView(R.id.tv_yesterday_rate)
    TextView mTvYesterdayRate;

    private ProfitMoneyDetailAdapter mProfitMoneyDetailAdapter;

    public static final String SELECTED_MONEY = "money";
    public static final String SELECTED_GOLD_COIN = "gold";

    public String mCurSelected = "money";

    @Override
    public int getContentLayout() {
        return R.layout.activity_profit_detail;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {

        setStatusBarImmersive(null);
        mTvTitle.setText("收益明细");
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

    @Override
    protected void initData() {
        super.initData();
        //这里我们是需要请求服务器数据的；

        boolean booleanExtra = getIntent().getBooleanExtra(IS_GOLD_COIN, true);
        showGoldCoin(booleanExtra);

        getDataProfitDetail();
        getWalletTotalMoney();
    }


    @OnClick(R.id.ll_gold_number)
    public void onClickGoldNumber() {
//        这里要重新获取数据；
        showGoldCoin(true);
        getDataProfitDetail();
    }

    @OnClick(R.id.ll_small_change_number)
    public void onClickMoneyNumber() {
//        这里要重新获取数据；
        showGoldCoin(false);
        try {
            getDataProfitDetail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 这里表示是否显示金币的
     *
     * @param isShow
     */
    public void showGoldCoin(boolean isShow) {

        mCbGoldCoin.setChecked(isShow);
        mCbMoney.setChecked(!isShow);

        if (isShow) {
            mCurSelected = SELECTED_GOLD_COIN;
        } else {
            mCurSelected = SELECTED_MONEY;
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
        if (mCurSelected.equals(SELECTED_GOLD_COIN)) {
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

    //点击提现；
    @OnClick(R.id.tv_withdrawals)
    public void onClickWithDrawals() {
        //提现的界面；
        startActivity(new Intent(this, IntergralShopActivity.class));
    }

    //点击进度
    @OnClick(R.id.tv_progress)
    public void onClickProgress() {
        //进度的界面
        startActivity(new Intent(this, WithdrawalRecordsActivity.class));

    }

    int page = 1;

    public void getMoreProfitDetail() {
        if (mCurSelected.equals(SELECTED_GOLD_COIN)) {
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
        paramsMap.put(ApiUserWallettotal.FROM_UID,getUserBean().getUserid());
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
     * 这里我们要修改页面；
     *
     * @param resWallettotalBean
     */
    private void updateUI(ResWallettotalBean resWallettotalBean) {
        //这里保存小数；
        mTvWalletMoney.setText(FormatUtils.decimalFormatTwo(resWallettotalBean.getLastmoney()));
        mTvHasGetTotalMoney.setText("累计收益:"+FormatUtils.decimalFormatTwo(resWallettotalBean.getTotalmoney())+"元");
        mTvYesterdayProfit.setText(FormatUtils.decimalFormatTwo(resWallettotalBean.getYesterday()));
        mTvYesterdayRate.setText(FormatUtils.decimalFormatTwo(resWallettotalBean.getRate()));
    }



    /**
     * 这里是金币明细的adapter
     */
    public class ProfitGoldCoinDetailAdapter extends BaseQuickAdapter<GoldDetailBean, BaseViewHolder> {

        public ProfitGoldCoinDetailAdapter(@Nullable List<GoldDetailBean> data) {
            super(R.layout.item_profit_detail, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoldDetailBean item) {
            //这里表示10元钱
            helper.setText(R.id.tv_title, item.getDes());
            helper.setText(R.id.tv_time, item.getAddtime());

            String desc="";

            if(item.getType().equals("2")){
                desc="-"+item.getPrice() + "金币";
            }else {
                desc="+"+item.getPrice() + "金币";
            }
            helper.setText(R.id.tv_gold_flow, desc);
        }
    }


    /**
     * 这里是余额明细的Adapter
     */
    public class ProfitMoneyDetailAdapter extends BaseQuickAdapter<MoneyDetailBean, BaseViewHolder> {


        public ProfitMoneyDetailAdapter(@Nullable List<MoneyDetailBean> data) {
            super(R.layout.item_profit_detail, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MoneyDetailBean item) {
            //这里表示10金币
            helper.setText(R.id.tv_title, item.getDes());
            helper.setText(R.id.tv_time, item.getAddtime());

            String desc="";

            if(item.getType().equals("24")){
                desc="+"+item.getPrice() + "元";
            }else {
                desc="-"+item.getPrice() + "元";
            }

            helper.setText(R.id.tv_gold_flow, desc);
        }
    }


    public static final String IS_GOLD_COIN = "isGoldCoin";

    /**
     * 这里我们要做的一个操作就是显示具体的金额
     *
     * @return
     */
    public static Intent getIntent(Context context, boolean isGoldCoin) {

        Intent intent = new Intent(context, ProfitDetailActivity.class);
        intent.putExtra(IS_GOLD_COIN, isGoldCoin);
        return intent;
    }

    @OnClick(R.id.iv_question)
    public void onClickQuestion(){
        startActivity(new Intent(this,QuestionsActivity.class));
    }

}
