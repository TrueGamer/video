package com.huanxi.renrentoutiao.ui.fragment.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.view.loading.MultiStateView;
import com.huanxi.renrentoutiao.ui.view.loading.SimpleMultiStateView;

/**
 * Created by Dinosa on 2018/1/25.
 * 这里我们是个title和Loaindg的一个基类；
 */

public abstract class BaseTitleAndLoadingFragment extends BaseTitleFragment {

    private SimpleMultiStateView mSimpleMultiStateView;

    @Override
    public View getBodyView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mSimpleMultiStateView = new SimpleMultiStateView(getActivity());
        mSimpleMultiStateView.setLayoutParams(layoutParams);
        mSimpleMultiStateView.addView(getLoadingContentView());
        initStateView();
        return mSimpleMultiStateView;
    }


    private void initStateView() {
        if (mSimpleMultiStateView == null) return;
        mSimpleMultiStateView.setEmptyResource(R.layout.view_empty)
                .setRetryResource(R.layout.view_retry)
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

    public abstract View getLoadingContentView();
}
