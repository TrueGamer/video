package com.huanxi.renrentoutiao.ui.fragment.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huanxi.renrentoutiao.R;

import butterknife.BindView;

/**
 * Created by Dinosa on 2018/1/19.
 *
 * 这里是一个显示界面就是一个默认的RecyclerView的界面，
 * 我们对其进行一个抽取出来；
 *
 */

public abstract class BaseLoadingRecyclerViewFragmentOld extends BaseLoadingFrament {

    @BindView(R.id.rv_home)
    RecyclerView mRvHome;

    @BindView(R.id.rl_refreshLayout)
    EasyRefreshLayout mRlRefreshLayout;

    @Override
    public int getLoadingContentLayoutId() {
        return R.layout.layout_refresh_recycler_view;
    }

    @Override
    protected void initView() {
        super.initView();
        mRvHome.setLayoutManager(getLayoutManager());
        ((BaseQuickAdapter) getAdapter()).bindToRecyclerView(mRvHome);

        mRlRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                BaseLoadingRecyclerViewFragmentOld.this.onLoadMore();
            }

            @Override
            public void onRefreshing() {
                BaseLoadingRecyclerViewFragmentOld.this.onRefreshing();
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();
        requestAdapterData(true);
    }


    /*
     *提供一个RecyclerView需要的一个适配器；
     */
    public abstract RecyclerView.Adapter getAdapter();

    /**
     * 请求Adapter中的数据；
     */
    public abstract void requestAdapterData(boolean isFirst);

    /**
     * 请求Adapter请求下一页的数据的
     */
    public abstract void requestNextAdapterData();

    @Override
    protected void onRetry() {
        super.onRetry();
        requestAdapterData(true);
    }

    /**
     * 上拉加载
     */
    public  void onLoadMore() {
        requestNextAdapterData();
    }

    /**
     * 下拉刷新的操作；
     */
    public void onRefreshing(){
        requestAdapterData(false);
    }

    public void loadMoreComplete(){
        mRlRefreshLayout.loadMoreComplete();
    }


    public void refreshComplete(){
        mRlRefreshLayout.refreshComplete();
    }

    /**
     * 设置RecyclerView的一个布局管理器，默认的是一个线性的布局；
     * @return
     */
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    public EasyRefreshLayout getEasyRefreshLayout(){
        return mRlRefreshLayout;
    }

    public RecyclerView getRvHome() {
        return mRvHome;
    }


    @Override
    public void onPause() {
        super.onPause();
        mRlRefreshLayout.refreshComplete();
        mRlRefreshLayout.loadMoreComplete();
    }
}
