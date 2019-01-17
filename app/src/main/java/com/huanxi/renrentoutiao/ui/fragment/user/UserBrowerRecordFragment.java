package com.huanxi.renrentoutiao.ui.fragment.user;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingRecyclerViewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinosa on 2018/1/19.
 */

public class UserBrowerRecordFragment extends BaseLoadingRecyclerViewFragment {

    BrowerRecordsAdapter mBrowerRecordsAdapter;

    private List<BrowerRecordsBean> mBrowerRecordsBeen;

    @Override
    public RecyclerView.Adapter getAdapter() {
        if (mBrowerRecordsAdapter == null) {
            mBrowerRecordsAdapter = new BrowerRecordsAdapter(null);
        }
        return mBrowerRecordsAdapter;
    }

    public void clear(){
        mBrowerRecordsAdapter.replaceData(new ArrayList<BrowerRecordsBean>());
    }

    @Override
    public void requestAdapterData(boolean isFirst) {


        mBrowerRecordsBeen = new ArrayList<>();
        mBrowerRecordsBeen.add(new BrowerRecordsBean());

        mBrowerRecordsAdapter.replaceData(mBrowerRecordsBeen);
        mBrowerRecordsAdapter.setEmptyView(inflatLayout(R.layout.view_empty));
        if(isFirst){
            showSuccess();
        }else{
            refreshComplete();
        }
    }

    @Override
    public void requestNextAdapterData() {

        List<BrowerRecordsBean> data = new ArrayList<>();
        data.add(new BrowerRecordsBean());
        data.add(new BrowerRecordsBean());
        data.add(new BrowerRecordsBean());
        data.add(new BrowerRecordsBean());
        data.add(new BrowerRecordsBean());
        data.add(new BrowerRecordsBean());
        data.add(new BrowerRecordsBean());
        data.add(new BrowerRecordsBean());
        data.add(new BrowerRecordsBean());
        data.add(new BrowerRecordsBean());

        mBrowerRecordsAdapter.addData(data);
        loadMoreComplete();
    }



    public class BrowerRecordsAdapter extends BaseQuickAdapter<BrowerRecordsBean,BaseViewHolder> {

        public BrowerRecordsAdapter(@Nullable List<BrowerRecordsBean> data) {
            super(R.layout.item_brower_records_history,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BrowerRecordsBean item) {

            //同时这里我们这里我们要处理点击事件；
        }
    }

    public class BrowerRecordsBean{

    }
}
