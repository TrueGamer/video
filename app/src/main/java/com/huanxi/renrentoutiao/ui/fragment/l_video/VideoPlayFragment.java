package com.huanxi.renrentoutiao.ui.fragment.l_video;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSON;
import com.huanxi.renrentoutiao.AppConfig;
import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantUrl;
import com.huanxi.renrentoutiao.interfaces.VideoChangeListener;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.model.bean.l_video.VideoBean;
import com.huanxi.renrentoutiao.net.api.news.ApiEndReadIssure;
import com.huanxi.renrentoutiao.net.api.user.task.ApiCustomTaskEnd;
import com.huanxi.renrentoutiao.net.api.user.task.ApiCustomTaskStart;
import com.huanxi.renrentoutiao.net.api.video.ApiEndPlayIssure;
import com.huanxi.renrentoutiao.net.api.video.ApiStartPlayIssure;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.net.bean.ResReadAwarad;
import com.huanxi.renrentoutiao.net.bean.news.ResAward;
import com.huanxi.renrentoutiao.net.http.HttpCallback;
import com.huanxi.renrentoutiao.net.http.HttpUtil;
import com.huanxi.renrentoutiao.presenter.ads.customer.CustomImageAd;
import com.huanxi.renrentoutiao.ui.activity.TimeCountTipsActivity;
import com.huanxi.renrentoutiao.ui.activity.video.VideoItemDetailActivity;
import com.huanxi.renrentoutiao.ui.activity.video.VideoPlayActivity;
import com.huanxi.renrentoutiao.ui.adapter.VideoPlayAdapter;
import com.huanxi.renrentoutiao.ui.dialog.RedPacketDialog;
import com.huanxi.renrentoutiao.ui.view.CircleImageView;
import com.huanxi.renrentoutiao.ui.view.ReadArticleAwaryView;
import com.huanxi.renrentoutiao.ui.view.RoundProgressBar;
import com.huanxi.renrentoutiao.ui.view.video.LoadingBar;
import com.huanxi.renrentoutiao.ui.view.video.NeedRefreshEvent;
import com.huanxi.renrentoutiao.ui.view.video.VerticalViewPager;
import com.huanxi.renrentoutiao.ui.view.video.VideoDeleteEvent;
import com.huanxi.renrentoutiao.ui.view.video.VideoPlayView;
import com.huanxi.renrentoutiao.ui.view.video.VideoPlayWrap;
import com.huanxi.renrentoutiao.utils.L;
import com.huanxi.renrentoutiao.utils.SharedPreferencesUtils;
import com.huanxi.renrentoutiao.utils.StringUtils;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by cxf on 2018/6/5.
 * 短视频播放的fragment 可以上下滑动
 */

public class VideoPlayFragment extends AbsFragment implements ViewPager.OnPageChangeListener {

    private VerticalViewPager mViewPager;
    private VideoPlayView mPlayView;
    private LoadingBar mLoading;
    private VideoPlayAdapter mAdapter;
    private int mOuterViewPagerPosition;//外层ViewPager的position
    private boolean mHidden;//是否hidden
    private int mPage = 1;//分页加载的页数
    private DataHelper mDataHelper;
    private VideoPlayWrap.ActionListener mActionListener;
    private boolean mPaused;
    private boolean mNeedRefresh;
    private VideoBean mNeedDeleteVideoBean;
    private OnInitDataCallback mOnInitDataCallback;
    //    private static final int DIRECTION_UP = 1;//向上
//    private static final int DIRECTION_DOWN = 2;//向下
//    private int mSrcollDircetion;//滑动方向
//    private float mLastPositionOffset = -1;
    private int mLastPosition;
    private boolean mStartWatch;
    private boolean mEndWatch;
    private FrameLayout fl_pro;
    private CircleImageView img_pro_box;
    private RoundProgressBar mPbReadProgressBar;
    private boolean isCanOpenBox = false;
    private boolean tabSpecs;
    private UserBean userBean;
    private ReadArticleAwaryView mReadArticleAwaryView;
    Subscription mDelaySubscribetion;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_play;
    }

    @Override
    protected void main() {
        if (mContext instanceof VideoPlayActivity) {
            View btnBack = mRootView.findViewById(R.id.btn_back);
            View commentGroup = mRootView.findViewById(R.id.comment_group);
            btnBack.setVisibility(View.VISIBLE);
            commentGroup.setVisibility(View.VISIBLE);
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.btn_back:
                            ((VideoPlayActivity) mContext).onBackPressed();
                            break;
                        case R.id.comment_group:
                            ((VideoPlayActivity) mContext).openCommentWindow();
                            break;
                    }

                }
            };
            btnBack.setOnClickListener(listener);
            commentGroup.setOnClickListener(listener);
        }

        userBean = ((MyApplication) getActivity().getApplication()).getUserBean();
        tabSpecs = SharedPreferencesUtils.getInstance(getContext()).getBoolean(ConstantUrl.IS_SHOW);
        // 金币进度
        fl_pro = (FrameLayout) mRootView.findViewById(R.id.fl_pro);
        img_pro_box = (CircleImageView) mRootView.findViewById(R.id.img_pro_box);
        mPbReadProgressBar = (RoundProgressBar) mRootView.findViewById(R.id.pb_read_progress);
        mReadArticleAwaryView = (ReadArticleAwaryView) mRootView.findViewById(R.id.award_view);
        fl_pro.setVisibility(View.INVISIBLE);
        mPbReadProgressBar.setShowChargingIcon(true);
        mPbReadProgressBar.setShowElectriText(false);

        mLoading = (LoadingBar) mRootView.findViewById(R.id.loading);
        mLoading.setLoading(true);
        mViewPager = (VerticalViewPager) mRootView.findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setOnPageChangeListener(this);
        if (mDataHelper != null) {
            List<VideoBean> list = mDataHelper.getInitVideoList();
            if (list != null && list.size() > 0) {
                initAdapter(list);
                int initPosition = mDataHelper.getInitPosition();
                if (initPosition >= 0 && initPosition < list.size()) {
                    mLastPosition = initPosition;
                    mViewPager.setCurrentItem(initPosition);
                }
            } else {
                mDataHelper.initData(mInitCallback);
            }
            int initPage = mDataHelper.getInitPage();
            if (initPage > 0) {
                mPage = initPage;
            }
        }

        fl_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 点击是开宝箱还是
                if(isCanOpenBox) {

                } else {
                    startActivity(new Intent(getActivity() , TimeCountTipsActivity.class));
                }
            }
        });

        if(userBean != null && !TextUtils.isEmpty(userBean.getUserid())) {
            getCoinTimes();
        }
        EventBus.getDefault().register(this);
    }

    /**
     * 获取金币次数，判断是否显示进度条
     */
    private void getCoinTimes() {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiCustomTaskEnd.FROM_UID,userBean.getUserid());
        paramsMap.put(ApiCustomTaskEnd.TASK_ID, "45");

        ApiCustomTaskStart apiCustomTaskStart = new ApiCustomTaskStart(new HttpOnNextListener<ResAward>() {

            @Override
            public void onNext(ResAward s) {
                requestStartCountDown();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                e.printStackTrace();
            }
        },(VideoPlayActivity)getActivity(),paramsMap);
        HttpManager.getInstance().doHttpDeal(apiCustomTaskStart);

//        HashMap<String, String> paramsMap = new HashMap<>();
//        paramsMap.put(ApiStartPlayIssure.Type , "45");
//        paramsMap.put(ApiStartPlayIssure.FROM_UID ,userBean.getUserid());
//        ApiStartPlayIssure apiStartReadIssure=new ApiStartPlayIssure(new HttpOnNextListener<ResEmpty>() {
//            @Override
//            public void onNext(ResEmpty resEmpty) {
//                requestStartCountDown();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//            }
//        },(VideoPlayActivity)getActivity(),paramsMap);
//        HttpManager.getInstance().doHttpDeal(apiStartReadIssure);
    }

    public CountDownTimer mStayCountDown;
    private void requestStartCountDown() {
        if(tabSpecs) {
            fl_pro.setVisibility(View.VISIBLE);
            final int totalTime = 32 * 1000;
            int timeUnit = 1000;
            mStayCountDown = new CountDownTimer(totalTime, timeUnit) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long finish = millisUntilFinished;
                    float percent = ((float) (totalTime-finish) / (float) totalTime);
                    int progress = (int)( percent * 100);
                    mPbReadProgressBar.setProgress(progress);
                }

                @Override
                public void onFinish() {
                    //这里获取金币
                    if(userBean == null){
                        return;
                    }

                    HashMap<String, String> paramsMap = new HashMap<>();
                    paramsMap.put(ApiCustomTaskEnd.FROM_UID,userBean.getUserid());
                    paramsMap.put(ApiCustomTaskEnd.TASK_ID, "45");

                    ApiCustomTaskEnd apiCustomTaskStart=new ApiCustomTaskEnd(new HttpOnNextListener<ResAward>() {

                        @Override
                        public void onNext(ResAward resAward) {

                            mPbReadProgressBar.setProgress(100);
                            img_pro_box.setImageResource(R.mipmap.icon_read_progress_open);
                            isCanOpenBox = true;
                            // RedPacketDialog.show(VideoItemDetailActivity.this,integral+"",null);
                            show(resAward.getIntegral()+"");

                            getCoinTimes();
                        }

                    },(VideoPlayActivity)getActivity(),paramsMap);

                    HttpManager.getInstance().doHttpDeal(apiCustomTaskStart);

//                    HashMap<String, String> paramsMap = new HashMap<>();
//                    paramsMap.put(ApiEndPlayIssure.Type , "45");
//                    paramsMap.put(ApiEndPlayIssure.FROM_UID,userBean.getUserid());
//                    ApiEndPlayIssure apiStartReadIssure=new ApiEndPlayIssure(new HttpOnNextListener<ResReadAwarad>() {
//                        @Override
//                        public void onNext(ResReadAwarad resReadNewsAward) {
//                            mPbReadProgressBar.setProgress(100);
//                            img_pro_box.setImageResource(R.mipmap.icon_read_progress_open);
//                            isCanOpenBox = true;
////                            resReadAwarad2 = resReadNewsAward;
//                            int integral = resReadNewsAward.getIntegral();
//                            // RedPacketDialog.show(VideoItemDetailActivity.this,integral+"",null);
//                            show(resReadNewsAward.getIntegral()+"",
//                                    resReadNewsAward.getLastcount(),resReadNewsAward.getLastcount_new());
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            super.onError(e);
//                        }
//                    },(VideoPlayActivity)getActivity(),paramsMap);
//                    HttpManager.getInstance().doHttpDeal(apiStartReadIssure);

                }
            };
            mStayCountDown.start();
        }
    }

    public void show(String goldNumber){
        //这里
        String title="恭喜你获得"+goldNumber+"金币";
//        String progressNew="今天剩余"+lastCount+"次,观看视频随机领取";
        String progressNew="观看视频随机领取";
        // mReadArticleAwaryView.init(goldNumber,progress);
        mReadArticleAwaryView.setTitleAndProgress(title,progressNew);
        //这里可能会崩溃
        try {
            showReadView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAdapter(List<VideoBean> list) {
        mAdapter = new VideoPlayAdapter(mContext, list);
        mAdapter.setOnPlayVideoListener(new VideoPlayAdapter.OnPlayVideoListener() {
            @Override
            public void onPlayVideo(VideoBean bean) {
                if (mContext instanceof VideoChangeListener) {
                    ((VideoChangeListener) mContext).changeVideo(bean);
                }
                mStartWatch = false;
                mEndWatch = false;
            }
        });
        mAdapter.setActionListener(mActionListener);
        mPlayView = mAdapter.getVideoPlayView();
        mPlayView.setPlayEventListener(mPlayEventListener);
        mViewPager.setAdapter(mAdapter);
        mAdapter.setViewPager(mViewPager);
    }

    public void setDataHelper(DataHelper helper) {
        mDataHelper = helper;
    }

    private HttpCallback mInitCallback = new HttpCallback() {
        @Override
        public void onSuccess(int code, String msg, String[] info) {
            if (code == 0 && info.length > 0) {
                List<VideoBean> list = JSON.parseArray(Arrays.toString(info), VideoBean.class);
                if (list.size() > 0) {
                    if (mAdapter == null) {
                        initAdapter(list);
                    }
                }
                if (mOnInitDataCallback != null) {
                    mOnInitDataCallback.onInitSuccess();
                }
            }
        }
    };

    private HttpCallback mLoadMoreCallback = new HttpCallback() {
        @Override
        public void onSuccess(int code, String msg, String[] info) {
            if (code == 0 && info.length > 0) {
                List<VideoBean> list = JSON.parseArray(Arrays.toString(info), VideoBean.class);
                if (list.size() > 0) {
                    if (mAdapter != null) {
                        mAdapter.insertList(list);
                    }
                } else {
                    mPage--;
                }
            } else {
                mPage--;
            }
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        if (positionOffset == 0) {
//            mLastPositionOffset = -1;
//        } else {
//            if (mLastPositionOffset == -1) {
//                mLastPositionOffset = positionOffset;
//            } else {
//                if (positionOffset > mLastPositionOffset) {
//                    L.e("#positionOffset----->向上");
//                    mSrcollDircetion = DIRECTION_UP;
//                } else if (positionOffset < mLastPositionOffset) {
//                    L.e("#positionOffset----->向下");
//                    mSrcollDircetion = DIRECTION_DOWN;
//                }
//            }
//        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mLastPosition != position) {
            if (mLastPosition < position) {
                if (mAdapter != null) {
                    int count = mAdapter.getCount();
                    if (count > 2 && position == count - 2) {
                        L.e("#VideoPlayFragment-------->分页加载数据");
                        mPage++;
                        if (mDataHelper != null) {
                            mDataHelper.loadMoreData(mPage, mLoadMoreCallback);
                        }
                    }
                }
            }
            mLastPosition = position;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private VideoPlayView.PlayEventListener mPlayEventListener = new VideoPlayView.PlayEventListener() {
        @Override
        public void onReadyPlay() {
            if (mLoading != null) {
                mLoading.setLoading(true);
            }
        }

        @Override
        public void onVideoSizeChanged(int width, int height) {

        }

        @Override
        public void onError() {

        }

        @Override
        public void onLoading() {
            if (mLoading != null) {
                mLoading.setLoading(true);
            }
        }

        @Override
        public void onPlay() {
            if (mLoading != null) {
                mLoading.setLoading(false);
            }
        }

        @Override
        public void onFirstFrame() {
            if (mOuterViewPagerPosition != 0 || mHidden) {
                if (mPlayView != null) {
                    mPlayView.pausePlay();
                }
            }
            if (!mStartWatch) {
                mStartWatch = true;
                VideoBean videoBean = mAdapter.getCurWrap().getVideoBean();
                if (videoBean != null) {
                    if (!AppConfig.getInstance().isLogin() ||
                            !AppConfig.getInstance().getUid().equals(videoBean.getUid())) {
                        HttpUtil.startWatchVideo(videoBean.getId());
                    }
                }
            }
        }

        @Override
        public void onPlayEnd() {
            if (!mEndWatch) {
                mEndWatch = true;
                VideoBean videoBean = mAdapter.getCurWrap().getVideoBean();
                if (videoBean != null) {
                    HttpUtil.endWatchVideo(videoBean.getId());
                }
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNeedRefreshEvent(NeedRefreshEvent e) {
        mNeedRefresh = true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoDeleteEvent(VideoDeleteEvent e) {
        VideoBean bean = e.getVideoBean();
        if (bean != null) {
            if (mPaused) {
                mNeedDeleteVideoBean = bean;
            } else {
                if (mAdapter != null) {
                    mAdapter.removeItem(bean);
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mPaused = true;
        if (mPlayView != null) {
            mPlayView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPlayView != null) {
            mPlayView.onResume();
        }
        if (mPaused) {
            mPaused = false;
            if (mNeedDeleteVideoBean == null) {
                if (mNeedRefresh && mAdapter != null) {
                    mAdapter.refreshCurrentVideoInfo();
                    mNeedRefresh = false;
                }
            } else {
                if (mAdapter != null) {
                    mAdapter.removeItem(mNeedDeleteVideoBean);
                }
                mNeedDeleteVideoBean = null;
            }
        }
    }

    /**
     * back键返回的时候销毁playView
     */
    public void backDestroyPlayView() {
        if (mPlayView != null) {
            mPlayView.destroy();
            mPlayView = null;
        }
    }

    @Override
    public void onDestroy() {
        HttpUtil.cancel(HttpUtil.START_WATCH_VIDEO);
        HttpUtil.cancel(HttpUtil.END_WATCH_VIDEO);
        if (mLoading != null) {
            mLoading.endLoading();
        }
        if (mPlayView != null) {
            mPlayView.destroy();
            mPlayView = null;
        }
        if (mAdapter != null) {
            mAdapter.release();
        }
        if (mStayCountDown != null) {
            mStayCountDown.cancel();
        }
        if (mDelaySubscribetion != null) {
            if (mDelaySubscribetion.isUnsubscribed()) {
                mDelaySubscribetion.unsubscribe();
            }
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void hiddenChanged(boolean hidden) {
        if (mHidden == hidden) {
            return;
        }
        mHidden = hidden;
        if (mOuterViewPagerPosition == 0 && mPlayView != null) {
            if (hidden) {
                mPlayView.pausePlay();
            } else {
                mPlayView.resumePlay();
            }
        }
    }

    /**
     * 外层ViewPager滑动事件
     */
    public void onOuterPageSelected(int position) {
        mOuterViewPagerPosition = position;
        if (mPlayView != null) {
            if (position == 0) {
                mPlayView.resumePlay();
            } else {
                mPlayView.pausePlay();
            }
        }
    }

    public void refreshVideoAttention(String uid, int isAttetion) {
        if (mAdapter != null) {
            mAdapter.refreshVideoAttention(uid, isAttetion);
        }
    }

    public VideoBean getCurVideoBean() {
        if (mAdapter != null) {
            return mAdapter.getCurVideoBean();
        }
        return null;
    }

    public VideoPlayWrap getCurWrap() {
        if (mAdapter != null) {
            return mAdapter.getCurWrap();
        }
        return null;
    }

    public interface DataHelper {
        //初始化数据
        void initData(HttpCallback callback);

        //加载更多
        void loadMoreData(int p, HttpCallback callback);

        //初始化的position
        int getInitPosition();

        //初始化的视频列表
        List<VideoBean> getInitVideoList();

        //获取初始的页数
        int getInitPage();
    }

    public void setActionListener(VideoPlayWrap.ActionListener listener) {
        mActionListener = listener;
    }

    public interface OnInitDataCallback {
        void onInitSuccess();
    }

    public void setOnInitDataCallback(OnInitDataCallback onInitDataCallback) {
        mOnInitDataCallback = onInitDataCallback;
    }


    /**
     * 这里是移动出来的动画；
     */
    public void showReadView(){


        AnimatorSet animatorSet = new AnimatorSet();
        if(mReadArticleAwaryView == null) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mReadArticleAwaryView, "translationY", 0,
                UIUtil.dip2px(getContext(), 100));
        ObjectAnimator alphyAnimator = ObjectAnimator.ofFloat(mReadArticleAwaryView, "Alpha",
                0, 1);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(mReadArticleAwaryView, "scaleX",
                0.5f, 1);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(mReadArticleAwaryView, "ScaleY",
                0.5f, 1);

        animatorSet.setDuration(1500);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //延迟几秒发送开始向下移动；
                mDelaySubscribetion= Observable.timer(1, TimeUnit.SECONDS)
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

        animatorSet.playTogether(objectAnimator,alphyAnimator,scaleXAnimator,scaleYAnimator);
        animatorSet.start();
    }

    /**
     * 开始一个移动到底部的动画；
     */
    private void startMoveDownAnimator() {

        if(mReadArticleAwaryView == null) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mReadArticleAwaryView,
                "translationY", UIUtil.dip2px(getContext(), 100),UIUtil.dip2px(getContext(), 200));
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mReadArticleAwaryView,
                "Alpha", 1,0);

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(1500);
        animatorSet.playTogether(objectAnimator,alphaAnimator);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //这里将这个View从界面中移除
                if(mReadArticleAwaryView!=null){
                    ((ViewGroup) mReadArticleAwaryView.getParent()).removeView(mReadArticleAwaryView);
                }
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

}
