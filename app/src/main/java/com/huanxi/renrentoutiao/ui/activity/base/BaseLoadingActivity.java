package com.huanxi.renrentoutiao.ui.activity.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.view.loading.MultiStateView;
import com.huanxi.renrentoutiao.ui.view.loading.SimpleMultiStateView;

/**
 * Created by Dinosa on 2018/2/11.
 */

public abstract class BaseLoadingActivity extends BaseActivity {


    public SimpleMultiStateView mSimpleMultiStateView;


    @Override
    public View getContentView(LayoutInflater inflater, ViewGroup container) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mSimpleMultiStateView = new SimpleMultiStateView(this);
        mSimpleMultiStateView.setLayoutParams(layoutParams);
        mSimpleMultiStateView.addView(getLoadingContentView());
        initStateView();
        return mSimpleMultiStateView;
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
     * 重试的方法；
     */
    public abstract void onRetry();

    protected abstract View getLoadingContentView();



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

}
