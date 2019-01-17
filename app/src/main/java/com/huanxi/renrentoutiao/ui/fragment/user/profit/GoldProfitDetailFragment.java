package com.huanxi.renrentoutiao.ui.fragment.user.profit;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.user.task.ApiUserGoldCoinDetailList;
import com.huanxi.renrentoutiao.net.bean.money.ResGoldDetailList;
import com.huanxi.renrentoutiao.ui.adapter.bean.GoldDetailBean;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingRecyclerViewFragment;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dinosa on 2018/4/16.
 */

public class GoldProfitDetailFragment extends BaseLoadingRecyclerViewFragment {

    private ProfitGoldCoinDetailAdapter mProfitGoldCoinDetailAdapter;

    @Override
    public RecyclerView.Adapter getAdapter() {

        if (mProfitGoldCoinDetailAdapter == null) {
            mProfitGoldCoinDetailAdapter = new ProfitGoldCoinDetailAdapter(null);
        }
        return mProfitGoldCoinDetailAdapter;
    }

    @Override
    public void requestAdapterData(final boolean isFirst) {
        //第一次加载数据；
        //这里是金币的明细；
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiUserGoldCoinDetailList.FROM_UID, getUserBean().getUserid());
        paramsMap.put(ApiUserGoldCoinDetailList.PAGE_NUM, "1");

        ApiUserGoldCoinDetailList apiUserGoldCoinDetailList = new ApiUserGoldCoinDetailList(new HttpOnNextListener<ResGoldDetailList>() {


            @Override
            public void onNext(ResGoldDetailList resGoldDetailList) {

                if (resGoldDetailList.getList() != null) {
                    //这里表示有数据；
                    if (resGoldDetailList.getList().size() > 0) {
                        mProfitGoldCoinDetailAdapter.replaceData(resGoldDetailList.getList());
                        mPage=2;
                    }
                }

                if(isFirst){
                    showSuccess();
                }else{
                    refreshComplete();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(isFirst){
                    showFaild();
                }else{
                    refreshComplete();
                }
            }
        }, getBaseActivity(), paramsMap);
        HttpManager.getInstance().doHttpDeal(apiUserGoldCoinDetailList);

    }

    protected int mPage;

    @Override
    public void requestNextAdapterData() {
        //这里是金币的明细；
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiUserGoldCoinDetailList.FROM_UID, getUserBean().getUserid());
        paramsMap.put(ApiUserGoldCoinDetailList.PAGE_NUM, mPage + "");

        ApiUserGoldCoinDetailList apiUserGoldCoinDetailList = new ApiUserGoldCoinDetailList(new HttpOnNextListener<ResGoldDetailList>() {


            @Override
            public void onNext(ResGoldDetailList resGoldDetailList) {

                if (resGoldDetailList.getList() != null && resGoldDetailList.getList().size() > 0) {
                    //这里表示有数据；
                    mProfitGoldCoinDetailAdapter.addData(resGoldDetailList.getList());
                    mPage++;
                } else {
                    toast("没有更多数据");
                }
                loadMoreComplete();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                loadMoreComplete();
            }

        }, getBaseActivity(), paramsMap);
        apiUserGoldCoinDetailList.setShowProgress(false);
        HttpManager.getInstance().doHttpDeal(apiUserGoldCoinDetailList);
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
            if ("2".equals(item.getType()) || "31".equals(item.getType()) ) {
                desc = "-" + item.getPrice() + "金币";
                color = ContextCompat.getColor(getBaseActivity(), R.color.color_19bf5c);
            } else {
                desc = "+" + item.getPrice() + "金币";
                color = ContextCompat.getColor(getBaseActivity(), R.color.color_fb7c53);
            }
            helper.setText(R.id.tv_gold_flow, desc);

            helper.setTextColor(R.id.tv_gold_flow, color);
        }
    }


}
