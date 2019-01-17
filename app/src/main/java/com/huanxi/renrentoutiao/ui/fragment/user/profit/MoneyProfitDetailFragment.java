package com.huanxi.renrentoutiao.ui.fragment.user.profit;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.user.task.ApiUserMoneyDetailList;
import com.huanxi.renrentoutiao.net.bean.money.ResMoneyDetailList;
import com.huanxi.renrentoutiao.ui.adapter.bean.MoneyDetailBean;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingRecyclerViewFragment;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dinosa on 2018/4/16.
 */

public class MoneyProfitDetailFragment extends BaseLoadingRecyclerViewFragment {

    private ProfitMoneyDetailAdapter mMoneyDetailAdapter;

    @Override
    public RecyclerView.Adapter getAdapter() {

        if (mMoneyDetailAdapter == null) {
            mMoneyDetailAdapter=new ProfitMoneyDetailAdapter(null);
        }
        return mMoneyDetailAdapter;
    }

    int mPage=1;

    @Override
    public void requestAdapterData(final boolean isFirst) {

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiUserMoneyDetailList.FROM_UID, getUserBean().getUserid());
        paramsMap.put(ApiUserMoneyDetailList.PAGE_NUM, "1");

        ApiUserMoneyDetailList apiUserMoneyDetailList = new ApiUserMoneyDetailList(new HttpOnNextListener<ResMoneyDetailList>() {

            @Override
            public void onNext(ResMoneyDetailList resMoneyDetailList) {
                //这里我们要做的一个操作就是更新adapter；

                if (resMoneyDetailList.getList() != null) {

                    if (resMoneyDetailList.getList().size() > 0) {
                        mPage=2;
                        mMoneyDetailAdapter.replaceData(resMoneyDetailList.getList());
                    }
                   if(isFirst){
                       showSuccess();
                   }else{
                       refreshComplete();
                   }
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

        HttpManager.getInstance().doHttpDeal(apiUserMoneyDetailList);

    }

    @Override
    public void requestNextAdapterData() {

        //这里表示余额的更多；
        //这里是余额的明细；
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiUserMoneyDetailList.FROM_UID, getUserBean().getUserid());
        paramsMap.put(ApiUserMoneyDetailList.PAGE_NUM, mPage + "");
        ApiUserMoneyDetailList apiUserMoneyDetailList = new ApiUserMoneyDetailList(new HttpOnNextListener<ResMoneyDetailList>() {

            @Override
            public void onNext(ResMoneyDetailList resMoneyDetailList) {
                //这里我们要做的一个操作就是更新adapter；

                if (resMoneyDetailList.getList() != null && resMoneyDetailList.getList().size() > 0) {
                    //这里表示有数据；
                    mMoneyDetailAdapter.addData(resMoneyDetailList.getList());
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
        apiUserMoneyDetailList.setShowProgress(false);
        HttpManager.getInstance().doHttpDeal(apiUserMoneyDetailList);
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



}
