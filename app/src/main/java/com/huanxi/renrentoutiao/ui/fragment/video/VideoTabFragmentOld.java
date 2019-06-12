package com.huanxi.renrentoutiao.ui.fragment.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.db.ta.sdk.TMNaTmView;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.video.ApiVedioList;
import com.huanxi.renrentoutiao.net.bean.news.HomeTabBean;
import com.huanxi.renrentoutiao.net.bean.video.ResVideoList;
import com.huanxi.renrentoutiao.ui.adapter.VideoListAdapter;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingRecyclerViewFragment;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;
import com.zhxu.library.utils.SystemUtils;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Dinosa on 2018/1/15.
 *
 * 封装抽取；未使用
 */

public class VideoTabFragmentOld extends BaseLoadingRecyclerViewFragment {

    private VideoListAdapter mVideoListAdapter;

    LinkedList<View> mTaAdViews=new LinkedList<>();

    public static final String TAB_BEAN="tabBean";

    private HomeTabBean mHomeTabBean;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if(arguments !=null ){
            Object object = getArguments().getSerializable(TAB_BEAN);
            if (object != null) {
                mHomeTabBean= ((HomeTabBean) object);
            }
        }
    }


    private void getDataFromNet(final boolean isFirst) {

        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put(ApiVedioList.CATEGORY,mHomeTabBean.getCode());

        paramsMap.put("device_id", SystemUtils.getIMEI(getActivity()));
        paramsMap.put("device_platform","android");
        paramsMap.put("device_type", SystemUtils.getSystemModel());
        paramsMap.put("device_brand", SystemUtils.getDeviceBrand());
        paramsMap.put("os_api", SystemUtils.getSystemApi());
        paramsMap.put("os_version", SystemUtils.getSystemVersion());
        paramsMap.put("uuid", SystemUtils.getIMEI(getActivity()));
        paramsMap.put("openudid", SystemUtils.getOpenUid(getActivity()));
        paramsMap.put("resolution", SystemUtils.getResolution(getActivity()));
        paramsMap.put("dpi", SystemUtils.getDensity(getActivity()));
        paramsMap.put(ApiVedioList.PAGE_NUM,"1");

        ApiVedioList apiVedioList=new ApiVedioList(new HttpOnNextListener<ResVideoList>() {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showFaild();
            }

            @Override
            public void onNext(ResVideoList vedioDataBeen) {
                if(isFirst){
                    if(vedioDataBeen.getList()!=null && vedioDataBeen.getList().size()>0){

                        showSuccess();
                    }else{

                        showEmpty();
                    }

                }else{
                    refreshComplete();
                }

                if(vedioDataBeen.getList()!=null && vedioDataBeen.getList().size()>0){
                    mPage=2;
                    mVideoListAdapter.replaceData(vedioDataBeen.getList());
                }
            }
        },paramsMap, ((RxAppCompatActivity) getActivity()));

        HttpManager.getInstance().doHttpDeal(apiVedioList);
    }



    @Override
    protected void onRetry() {
        super.onRetry();
        //之类我们执行相应的逻辑；
        getDataFromNet(true);
    }

    ///=========进行一个抽取=================


    @Override
    public RecyclerView.Adapter getAdapter() {
        if (mVideoListAdapter == null) {
            mVideoListAdapter = new VideoListAdapter(getActivity(),null,mTaAdViews,mHomeTabBean.getCode());
        }
        return mVideoListAdapter;
    }

    @Override
    public void requestAdapterData(boolean isFirst) {
        getDataFromNet(isFirst);
    }

    int mPage=1;

    @Override
    public void requestNextAdapterData() {
        //请求更多数据；

        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put(ApiVedioList.CATEGORY,mHomeTabBean.getCode());

        paramsMap.put("device_id", SystemUtils.getIMEI(getActivity()));
        paramsMap.put("device_platform","android");
        paramsMap.put("device_type", SystemUtils.getSystemModel());
        paramsMap.put("device_brand", SystemUtils.getDeviceBrand());
        paramsMap.put("os_api", SystemUtils.getSystemApi());
        paramsMap.put("os_version", SystemUtils.getSystemVersion());
        paramsMap.put("uuid", SystemUtils.getIMEI(getActivity()));
        paramsMap.put("openudid", SystemUtils.getOpenUid(getActivity()));
        paramsMap.put("resolution", SystemUtils.getResolution(getActivity()));
        paramsMap.put("dpi", SystemUtils.getDensity(getActivity()));
        paramsMap.put(ApiVedioList.PAGE_NUM,mPage+"");

        ApiVedioList apiVedioList=new ApiVedioList(new HttpOnNextListener<ResVideoList>() {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                loadMoreComplete();
            }

            @Override
            public void onNext(ResVideoList vedioDataBeen) {

                if(vedioDataBeen.getList()!=null){

                    if(vedioDataBeen.getList().size()>0){
                        mPage++;
                    }
                    mVideoListAdapter.addData(vedioDataBeen.getList());
                }

                loadMoreComplete();
            }
        },paramsMap, ((RxAppCompatActivity) getActivity()));

        HttpManager.getInstance().doHttpDeal(apiVedioList);
    }


    /**
     * 获取HomeTabFragment
     * @param bean 传入的对象；
     * @return 一个HomeTabFragment;
     */
    public static VideoTabFragmentOld getVideoTabFragment(HomeTabBean bean){
        VideoTabFragmentOld homeTabFragment = new VideoTabFragmentOld();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAB_BEAN,bean);
        homeTabFragment.setArguments(bundle);
        return homeTabFragment;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mTaAdViews != null) {
            for (View taAdView : mTaAdViews) {
                TMNaTmView tmView = (TMNaTmView) taAdView.findViewById(R.id.TMNaView);
                tmView.destroy();
            }
        }

    }

}
