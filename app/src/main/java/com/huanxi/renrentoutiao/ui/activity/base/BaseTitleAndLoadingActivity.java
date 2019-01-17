package com.huanxi.renrentoutiao.ui.activity.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.view.loading.MultiStateView;
import com.huanxi.renrentoutiao.ui.view.loading.SimpleMultiStateView;

/**
 * Created by Dinosa on 2018/1/19.
 * 这里是Loading和title的一个Activity基类；
 */
public abstract class BaseTitleAndLoadingActivity extends BaseTitleActivity {


    public SimpleMultiStateView mSimpleMultiStateView;

    /**
     * 这里我们要在BodyView里面添加一个SimpleMuiltyTypeView
     * @param inflater
     * @param container
     * @return
     */
    @Override
    public View getBodyView(LayoutInflater inflater, ViewGroup container) {

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mSimpleMultiStateView = new SimpleMultiStateView(this);
        mSimpleMultiStateView.setLayoutParams(layoutParams);
        mSimpleMultiStateView.addView(getLoadingContentView(inflater,container));
        initStateView();
        return mSimpleMultiStateView;

    }

    /**
     * 这里是加载成功之后的内容；
     * @return
     */
    public View getLoadingContentView(LayoutInflater inflater, ViewGroup container){
        View view = inflater.inflate(getLoadingContentLayoutId(), container, false);
        return view;
    }

    /**
     * 这里是获取加载内容layoutId;是一个默认的实现，
     * 如果需要自定义重写{@getLoadingContentView}
     * @return
     */
    public int getLoadingContentLayoutId() {
        return 0;
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


}
