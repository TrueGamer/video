package com.huanxi.renrentoutiao.ui.fragment.base;

import android.view.View;
import android.view.ViewGroup;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.view.loading.MultiStateView;
import com.huanxi.renrentoutiao.ui.view.loading.SimpleMultiStateView;

/**
 * Created by Dinosa on 2018/1/19.
 */

public abstract  class BaseLoadingFrament extends BaseFragment {


    public SimpleMultiStateView mSimpleMultiStateView;

    @Override
    protected View getContentView() {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mSimpleMultiStateView = new SimpleMultiStateView(getActivity());
        mSimpleMultiStateView.setLayoutParams(layoutParams);
        mSimpleMultiStateView.addView(getLoadingContentView());
        initStateView();
        return mSimpleMultiStateView;
    }


    public View getLoadingContentView(){
        return inflatLayout(getLoadingContentLayoutId());
    }

    /**
     * 如果需要重写getLoadingContentView，则无需重写这个方法，否者必须重写这个方法
     * @return
     */
    public int getLoadingContentLayoutId() {
        return 0;
    }


    private void initStateView() {
        if (mSimpleMultiStateView == null) return;
        mSimpleMultiStateView.setEmptyResource(R.layout.view_empty)
                .setRetryResource(R.layout.new_view_retry)
                .setLoadingResource(R.layout.new_view_loading)
                .setNoNetResource(R.layout.view_nonet)
                .build()
                .setonReLoadlistener(new MultiStateView.onReLoadlistener() {
                    @Override
                    public void onReload() {
                        onRetry();
                    }
                });
    }

    /**
     * 点击重试调用的方法；
     */
    protected void onRetry() {

    }


    public void showLoading() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showLoadingView();
        }
    }

    public void showSuccess() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showContent();
        }
    }

    public void showFaild() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showErrorView();
        }
    }

    public void showNoNet() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showNoNetView();
        }
    }

    public void showEmpty(){
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showEmptyView();
        }
    }

    @Override
    public void onDestroy() {

        //这里是需要停止动画的；
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.destory();
        }
        super.onDestroy();
    }
}
