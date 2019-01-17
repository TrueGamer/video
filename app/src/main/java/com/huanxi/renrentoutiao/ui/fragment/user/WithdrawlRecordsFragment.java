package com.huanxi.renrentoutiao.ui.fragment.user;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.user.ApiWithdrawlRecords;
import com.huanxi.renrentoutiao.net.bean.money.ResWithdrawRecords;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.adapter.bean.WithdrawRecordBean;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingRecyclerViewFragment;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dinosa on 2018/1/26.
 */

public class WithdrawlRecordsFragment extends BaseLoadingRecyclerViewFragment {

    private RecordsAdapter mRecordsAdapter;

    @Override
    public RecyclerView.Adapter getAdapter() {
        if (mRecordsAdapter == null) {
            mRecordsAdapter = new RecordsAdapter(null);
        }
        return mRecordsAdapter;
    }

    int page=1;

    @Override
    public void requestAdapterData(final boolean isFirst) {
        page=1;
        UserBean userBean = ((BaseActivity) getActivity()).getUserBean();

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiWithdrawlRecords.FROM_UID,userBean.getUserid());

        ApiWithdrawlRecords apiWithdrawlRecords=new ApiWithdrawlRecords(new HttpOnNextListener<ResWithdrawRecords>() {

            @Override
            public void onNext(ResWithdrawRecords resWithdrawRecords) {

                if (resWithdrawRecords.getList()!=null && resWithdrawRecords.getList().size()>0) {
                    //这里表示的内容就是服务器返回的有数据；
                    if (isFirst) {
                        showSuccess();
                    }else{
                        //getEasyRefreshLayout().refreshComplete();
                        //这里我们是刷新数据的；
                        refreshComplete();
                    }
                    page++;
                    mRecordsAdapter.replaceData(resWithdrawRecords.getList());
                }else{
                    if(page==1){
                        showEmpty();
                    }
                }
            }

        }, ((BaseActivity) getActivity()),paramsMap);

        HttpManager.getInstance().doHttpDeal(apiWithdrawlRecords);
    }

    /* 加载下一页
     */
    @Override
    public void requestNextAdapterData() {

        UserBean userBean = ((BaseActivity) getActivity()).getUserBean();

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiWithdrawlRecords.FROM_UID,userBean.getUserid());
        paramsMap.put(ApiWithdrawlRecords.FROM_UID,page+"");

        ApiWithdrawlRecords apiWithdrawlRecords=new ApiWithdrawlRecords(new HttpOnNextListener<ResWithdrawRecords>() {

            @Override
            public void onNext(ResWithdrawRecords resWithdrawRecords) {

                if (resWithdrawRecords.getList()!=null && resWithdrawRecords.getList().size()>0) {
                    //这里表示的内容就是服务器返回的有数据；\
                    page++;
                    mRecordsAdapter.addData(resWithdrawRecords.getList());
                }else{
                    ((BaseActivity) getActivity()).toast("没有更多数据");
                }
                loadMoreComplete();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                loadMoreComplete();
            }
        }, ((BaseActivity) getActivity()),paramsMap);

        HttpManager.getInstance().doHttpDeal(apiWithdrawlRecords);
    }

    public class RecordsAdapter extends BaseQuickAdapter<WithdrawRecordBean,BaseViewHolder>{

        public RecordsAdapter( @Nullable List<WithdrawRecordBean> data) {
            super(R.layout.item_withdraw_record, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, WithdrawRecordBean item) {

            helper.setText(R.id.tv_date,item.getAddtime());

            TextView textView = (TextView)helper.getView(R.id.tv_withdrawals_states);

            if (item.getStatus().equals("-1")) {
                //失败
                textView.setText("失败");
                textView.setTextColor(getResources().getColor(R.color.withdrawals_record_state_failed));
            } else if (item.getStatus().equals("1")) {
                //处理中；
                textView.setText("处理中");
                textView.setTextColor(getResources().getColor(R.color.withdrawals_record_state_doing));

            } else if (item.getStatus().equals("2")) {
                //成功
                textView.setText("成功");
                textView.setTextColor(getResources().getColor(R.color.withdrawals_record_state_success));
            }

        }
    }

}
