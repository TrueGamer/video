package com.huanxi.renrentoutiao.ui.fragment.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.globle.ConstantThreePart;
import com.huanxi.renrentoutiao.net.api.news.ApiHomeNews;
import com.huanxi.renrentoutiao.net.api.news.ApiNewsAndVideoList;
import com.huanxi.renrentoutiao.net.bean.news.HomeTabBean;
import com.huanxi.renrentoutiao.net.bean.news.ResNewsAndVideoBean;
import com.huanxi.renrentoutiao.presenter.news.NewsInfoFlowPresenter;
import com.huanxi.renrentoutiao.ui.activity.other.MainActivity;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.NewsAdapter;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.gdt.GDTImgAds;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.ta.TaLeftTitleRightImgAds;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingRecyclerViewFragment;
import com.huanxi.renrentoutiao.ui.view.ReadArticleAwaryView;
import com.huanxi.renrentoutiao.utils.SharedPreferencesUtils;
import com.huanxi.renrentoutiao.utils.TTAdManagerHolder;
import com.huanxi.renrentoutiao.utils.TToast;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.tongdun.android.shell.db.utils.LogUtil;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by Dinosa on 2018/1/15.
 * 这里是homeTab的Fragment
 *
 * 使用中
 */

public class HomeTabFragmentOld extends BaseLoadingRecyclerViewFragment {

    public static final String TAB_BEAN = "tabBean";

    private HomeTabBean mHomeTabBean;
    protected NewsAdapter mAdapter;

    //这里是下拉刷新多少条的一个逻辑

    public LinkedList<NativeExpressADView> mGdtAdLists = new LinkedList<>();
    private GDTImgAds mGdtImgAds;
    private TaLeftTitleRightImgAds mTaLeftTitleRightImgAds;
    private NewsInfoFlowPresenter mNewsInfoFlowPresenter;

    int mPage = 1;

    private LinkedList<TTFeedAd> mData; // 网盟广告列表
    private TTAdNative mTTAdNative; // 网盟广告

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            Object object = getArguments().getSerializable(TAB_BEAN);
            if (object != null) {
                mHomeTabBean = ((HomeTabBean) object);
            }
        }

    }

    @Override
    protected void initView() {
        super.initView();
        // --- 网盟广告
        mData = new LinkedList<>();
        TTAdManager ttAdManager = TTAdManagerHolder.getInstance(getActivity());
        mTTAdNative = ttAdManager.createAdNative(getActivity());
        //申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.getInstance(getActivity()).requestPermissionIfNecessary(getActivity());
//        loadListAd();

        mNewsInfoFlowPresenter = new NewsInfoFlowPresenter(mGdtImgAds ,mGdtAdLists ,mTaLeftTitleRightImgAds,mTTAdNative,mData);
    }

    private CountDownLatch latch;

    /**
     * 加载feed广告
     */
    private void loadListAd(String type , boolean isFirst) {
        //这里初始化广点通广告的逻辑
        mGdtImgAds = new GDTImgAds(new GDTImgAds.OnAdReceived() {
            @Override
            public void onGdtImgAdReceived(List<NativeExpressADView> mImgAds) {
                LogUtil.d("loadListAd mImgAds "+(mImgAds==null?"null":""+mImgAds.size()));
                if (mImgAds != null) {
                    mGdtAdLists.addAll(mImgAds);
                }
            }
        } , getBaseActivity());
        mNewsInfoFlowPresenter.setGdtImgAds(mGdtImgAds);
        mTaLeftTitleRightImgAds = new TaLeftTitleRightImgAds();

        //feed广告请求类型参数
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(ConstantAd.CSJAD.APP_ID)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(640, 320)
                .setAdCount(10)
                .build();
        //调用feed广告异步请求接口
        mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
            @Override
            public void onError(int code, String message) {
                LogUtil.e("info","loadFeedAd error:"+message);
                if(latch.getCount() > 0) {
                    latch.countDown();
                }
            }
            @Override
            public void onFeedAdLoad(List<TTFeedAd> ads) {
//                if (ads == null || ads.isEmpty()) {
//                    TToast.show(getContext(), "on FeedAdLoaded: ad is null!");
//                    return;
//                }
                mData.addAll(ads);
                Log.i("info" , "ads size=="+mData.size());
                if(latch.getCount() > 0) {
                    latch.countDown();
                }
            }
        });
    }

    @Override
    public int getLoadingContentLayoutId() {
        return R.layout.layout_refresh_recycler_view;
    }

    public void getData(final boolean isShowContent) {

        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put("type", mHomeTabBean.getCode());
        paramsMap.put("qid", ConstantThreePart.ADMAMA_ID);
        paramsMap.put(ApiNewsAndVideoList.PAGE_NUM, "1");

        ApiNewsAndVideoList apiHomeNews = new ApiNewsAndVideoList(new HttpOnNextListener<ResNewsAndVideoBean>() {

            @Override
            public void onNext(ResNewsAndVideoBean resNewsAndVideoBean) {

                try {
                    latch.await(3,TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resNewsAndVideoBean.getList() != null && resNewsAndVideoBean.getList().size() > 0) {
                            mPage = 2;
                            mAdapter.replaceData(mNewsInfoFlowPresenter.filterData(resNewsAndVideoBean.getList()));
                            if (isShowContent) {
                                showSuccess();
                            } else {
                                refreshComplete();
                                if (resNewsAndVideoBean.getList().size() > 0) {

                                    ((MainActivity) getBaseActivity()).getNewsFragment().showRefreshBanner(resNewsAndVideoBean.getList().size());

                                }
                            }
                        } else {
                            showEmpty();
                        }
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (isShowContent) {
                                showFaild();
                            } else {
                                refreshComplete();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        }, ((RxAppCompatActivity) getActivity()), paramsMap);

        HttpManager.getInstance().doHttpDealBackground(apiHomeNews);
    }

    /**
     * 获取HomeTabFragment
     *
     * @param bean 传入的对象；
     * @return 一个HomeTabFragment;
     */
    public static HomeTabFragmentOld getHomeTabFragment(HomeTabBean bean) {
        HomeTabFragmentOld homeTabFragment = new HomeTabFragmentOld();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAB_BEAN, bean);
        homeTabFragment.setArguments(bundle);
        return homeTabFragment;
    }


    public void loadMore() {

        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put("type", mHomeTabBean.getCode());
        paramsMap.put("qid", ConstantThreePart.ADMAMA_ID);
        paramsMap.put("num", "20");
        paramsMap.put(ApiHomeNews.PAGE_NUM, mPage + "");

        ApiNewsAndVideoList apiHomeNews = new ApiNewsAndVideoList(new HttpOnNextListener<ResNewsAndVideoBean>() {

            @Override
            public void onNext(ResNewsAndVideoBean resNewsAndVideoBean) {

                //这里我们要对数据进行一个过滤的操作；
                if (resNewsAndVideoBean.getList() != null && resNewsAndVideoBean.getList().size() > 0) {
                    //这里表示服务器返回有数据；
                    List<MultiItemEntity> multiItemEntities = mNewsInfoFlowPresenter
                            .filterData(resNewsAndVideoBean.getList());

                    if (multiItemEntities != null && multiItemEntities.size() > 0) {
                        mPage++;
                        mAdapter.addData(multiItemEntities);
                    }
                    loadMoreComplete();

                }
                loadMoreComplete();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                loadMoreComplete();
            }
        }, ((RxAppCompatActivity) getActivity()), paramsMap);
        HttpManager.getInstance().doHttpDeal(apiHomeNews);

    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new NewsAdapter(null);
        }
        return mAdapter;
    }

    @Override
    public void requestAdapterData(boolean isFirst) {
        latch = new CountDownLatch(6);
        for(int i = 0; i < 10; i++) {
            loadListAd("refresh", isFirst);
        }
        getData(isFirst);
    }

    @Override
    public void requestNextAdapterData() {
//        loadListAd("more" , false);
        loadMore();
    }

    @Override
    public void onDestroy() {
        //销毁所有的广告的逻辑
        if(mNewsInfoFlowPresenter != null) {
            mNewsInfoFlowPresenter.destory();
        }
        super.onDestroy();
    }


    public String getTabName(){
        return mHomeTabBean.getTitle();
    }

}
