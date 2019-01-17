package com.huanxi.renrentoutiao.ui.fragment.base;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.view.ReadArticleAwaryView;
import com.huanxi.renrentoutiao.utils.SharedPreferencesUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Dinosa on 2018/1/19.
 * <p>
 * 这里是一个显示界面就是一个默认的RecyclerView的界面，
 * 我们对其进行一个抽取出来；
 */

public abstract class BaseLoadingRecyclerViewFragment extends BaseLoadingFrament {

    @BindView(R.id.award_view)
    public ReadArticleAwaryView mReadArticleAwaryView;

    @BindView(R.id.rv_home)
    RecyclerView mRvHome;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;


    @Override
    public int getLoadingContentLayoutId() {
        return R.layout.layout_refresh_recycler_view;
    }

    @Override
    protected void initView() {
        super.initView();
        mRvHome.setLayoutManager(getLayoutManager());
        ((BaseQuickAdapter) getAdapter()).bindToRecyclerView(mRvHome);


        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                BaseLoadingRecyclerViewFragment.this.onRefreshing();
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                BaseLoadingRecyclerViewFragment.this.onLoadMore();
            }
        });

        mRefreshLayout.setDisableContentWhenLoading(true);
        mRefreshLayout.setDisableContentWhenRefresh(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        String reward = SharedPreferencesUtils.getInstance(getBaseActivity()).getReward(getBaseActivity());
        String rewardNum = SharedPreferencesUtils.getInstance(getBaseActivity()).getRewardNum(getBaseActivity());
        if(!TextUtils.isEmpty(reward) && !TextUtils.isEmpty(rewardNum)) {
            String title = "恭喜你获得"+rewardNum+"金币";
            String progressNew = "阅读广告金币随机领取";
            // mReadArticleAwaryView.init(goldNumber,progress);
            mReadArticleAwaryView.setTitleAndProgress(title, progressNew);
            //这里可能会崩溃
            try {
                showReadView();
            } catch (Exception e) {
                e.printStackTrace();
            }
            SharedPreferencesUtils.getInstance(getBaseActivity()).setReward(getBaseActivity() , "");
        }
    }

    //延时的一个subscribetion;
    public Subscription mDelaySubscribetion;

    /**
     * 这里是移动出来的动画；
     */
    public void showReadView() {
        AnimatorSet animatorSet = new AnimatorSet();

        if(mReadArticleAwaryView == null) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mReadArticleAwaryView, "translationY", 0, UIUtil.dip2px(getBaseActivity(), 100));
        ObjectAnimator alphyAnimator = ObjectAnimator.ofFloat(mReadArticleAwaryView, "Alpha", 0, 1);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(mReadArticleAwaryView, "scaleX", 0.5f, 1);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(mReadArticleAwaryView, "ScaleY", 0.5f, 1);

        animatorSet.setDuration(1500);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //延迟几秒发送开始向下移动；
                mDelaySubscribetion = Observable.timer(1, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                //这里我们开启一个新的动画操作；
                                startMoveDownAnimator();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        });
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet.playTogether(objectAnimator, alphyAnimator, scaleXAnimator, scaleYAnimator);
        animatorSet.start();
    }

    /**
     * 开始一个移动到底部的动画；
     */
    private void startMoveDownAnimator() {

        if(mReadArticleAwaryView == null) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mReadArticleAwaryView, "translationY",
                UIUtil.dip2px(getBaseActivity(), 100), UIUtil.dip2px(getBaseActivity(), 200));
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mReadArticleAwaryView, "Alpha", 1, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1500);
        animatorSet.playTogether(objectAnimator, alphaAnimator);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //这里将这个View从界面中移除
//                if (mReadArticleAwaryView != null &&
//                        (ViewGroup) mReadArticleAwaryView.getParent() != null) {
//                    ((ViewGroup) mReadArticleAwaryView.getParent()).removeView(mReadArticleAwaryView);
//                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    @Override
    protected void initData() {
        super.initData();
        requestAdapterData(true);
    }

    public void autoRefresh(){
        mRvHome.scrollToPosition(0);
        mRefreshLayout.autoRefresh(100);
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
    public void onLoadMore() {
        requestNextAdapterData();
    }

    /**
     * 下拉刷新的操作；
     */
    public void onRefreshing() {
        requestAdapterData(false);
    }

    public void loadMoreComplete() {
        //mRlRefreshLayout.loadMoreComplete();
        mRefreshLayout.finishLoadMore(200);
    }


    public void refreshComplete() {
        //mRlRefreshLayout.refreshComplete();
        mRefreshLayout.finishRefresh(200);
    }

    /**
     * 设置RecyclerView的一个布局管理器，默认的是一个线性的布局；
     *
     * @return
     */
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }


    public RecyclerView getRvHome() {
        return mRvHome;
    }


    @Override
    public void onPause() {
        super.onPause();
    /*    mRlRefreshLayout.refreshComplete();
        mRlRefreshLayout.loadMoreComplete();*/
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();

    }

    /**
     * 设置是否有下拉刷新
     * @param isEnable
     */
    public void setRefreshEnable(boolean isEnable){

        mRefreshLayout.setEnableRefresh(isEnable);

    }

    /**
     * 是否有上拉加载
     * @param isEnable
     */
    public void setLoadingEnable(boolean isEnable){

        mRefreshLayout.setEnableLoadMore(isEnable);
    }

    @Override
    public void onDestroy() {
        if (mDelaySubscribetion != null) {
            if (mDelaySubscribetion.isUnsubscribed()) {
                mDelaySubscribetion.unsubscribe();
            }
        }
        super.onDestroy();
    }
}
