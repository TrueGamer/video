package com.huanxi.renrentoutiao.ui.fragment.user;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinosa on 2018/1/19.
 */

public class IncomeRankingFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private RankingAdapter mRankingAdapter;

    @Override
    protected View getContentView() {
        mRecyclerView = new RecyclerView(getActivity());
        return mRecyclerView;
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRankingAdapter = new RankingAdapter(null);
        mRecyclerView.setAdapter(mRankingAdapter);

        ArrayList<RankingBean> rankingBeen = new ArrayList<>();
        rankingBeen.add(new RankingBean());
        rankingBeen.add(new RankingBean());
        rankingBeen.add(new RankingBean());
        rankingBeen.add(new RankingBean());
        rankingBeen.add(new RankingBean());
        rankingBeen.add(new RankingBean());
        rankingBeen.add(new RankingBean());
        rankingBeen.add(new RankingBean());
        rankingBeen.add(new RankingBean());
        rankingBeen.add(new RankingBean());
        rankingBeen.add(new RankingBean());
        rankingBeen.add(new RankingBean());
        rankingBeen.add(new RankingBean());
        rankingBeen.add(new RankingBean());
        mRankingAdapter.addData(rankingBeen);
    }

    public class RankingAdapter extends BaseQuickAdapter<RankingBean,BaseViewHolder>{

        public RankingAdapter(@Nullable List<RankingBean> data) {
            super(R.layout.item_income_ranking,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, RankingBean item) {

        }
    }

    public class RankingBean {

    }
}
