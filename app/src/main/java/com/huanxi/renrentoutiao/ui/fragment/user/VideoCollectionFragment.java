package com.huanxi.renrentoutiao.ui.fragment.user;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.huanxi.renrentoutiao.net.api.video.ApiVedioList;
import com.huanxi.renrentoutiao.ui.adapter.VideoListAdapter;
import com.huanxi.renrentoutiao.ui.adapter.bean.VideoBean;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingRecyclerViewFragment;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dinosa on 2018/1/26.
 */

public class VideoCollectionFragment extends BaseLoadingRecyclerViewFragment {
    private VideoListAdapter mVideoListAdapter;

    @Override
    public RecyclerView.Adapter getAdapter() {
        if (mVideoListAdapter == null) {

            mVideoListAdapter = new VideoListAdapter(getActivity(),null,new LinkedList<View>(),null);
        }
        return mVideoListAdapter;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    public void requestAdapterData(final boolean isFirst) {


        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put(ApiVedioList.CATEGORY,"video");

        ApiVedioList apiVedioList=new ApiVedioList(new HttpOnNextListener<List<VideoBean>>() {


            @Override
            public void onNext(List<VideoBean> vedioDataBeen) {
                if(isFirst){
                    showSuccess();
                }else{
                    refreshComplete();
                }
                mVideoListAdapter.replaceData(vedioDataBeen);
            }
        },paramsMap, ((RxAppCompatActivity) getActivity()));

        HttpManager.getInstance().doHttpDeal(apiVedioList);

    }

    @Override
    public void requestNextAdapterData() {
        loadMoreComplete();
    }
}
