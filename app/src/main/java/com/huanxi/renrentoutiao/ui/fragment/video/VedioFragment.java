package com.huanxi.renrentoutiao.ui.fragment.video;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.video.ApiVedioList;
import com.huanxi.renrentoutiao.ui.adapter.bean.VideoBean;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseTitleAndLoadingFragment;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;
import com.zhxu.library.utils.SystemUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Dinosa on 2018/1/12.
 */

public class VedioFragment extends BaseTitleAndLoadingFragment {

    @BindView(R.id.rv_home)
    RecyclerView mRvHome;

    @BindView(R.id.rl_refreshLayout)
    EasyRefreshLayout mRlRefreshLayout;

    private VideoListAdapter mVideoListAdapter;



    @Override
    protected void initView() {
        super.initView();
        setTitle("视频");
    }

    @Override
    protected void initData() {
        super.initData();
        mVideoListAdapter = new VideoListAdapter(getActivity(),null);
        //这里请求网络的操作；
        mRvHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvHome.setAdapter(mVideoListAdapter);

        mRlRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                VedioFragment.this.onLoadMore();
            }

            @Override
            public void onRefreshing() {
                VedioFragment.this.onRefreshing();
            }
        });
        mRlRefreshLayout.setLoadMoreModel(LoadModel.NONE);
        getDataFromNet(true);
    }

    private void onRefreshing() {
        getDataFromNet(false);
    }

    private void onLoadMore() {

    }

    @Override
    public View getLoadingContentView() {
        return inflatLayout(R.layout.layout_refresh_recycler_view);
    }

    private void getDataFromNet(final boolean isFirst) {


        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put(ApiVedioList.CATEGORY,"video");

        paramsMap.put("device_id", SystemUtils.getIMEI(getActivity()));
        paramsMap.put("device_platform","android");
        paramsMap.put("device_type",SystemUtils.getSystemModel());
        paramsMap.put("device_brand",SystemUtils.getDeviceBrand());
        paramsMap.put("os_api", SystemUtils.getSystemApi());
        paramsMap.put("os_version",SystemUtils.getSystemVersion());
        paramsMap.put("uuid",SystemUtils.getIMEI(getActivity()));
        paramsMap.put("openudid",SystemUtils.getOpenUid(getActivity()));
        paramsMap.put("resolution",SystemUtils.getResolution(getActivity()));
        paramsMap.put("dpi",SystemUtils.getDensity(getActivity()));


        ApiVedioList apiVedioList=new ApiVedioList(new HttpOnNextListener<List<VideoBean>>() {


            @Override
            public void onNext(List<VideoBean> vedioDataBeen) {
                if(isFirst){
                    showSuccess();
                }else{
                    mRlRefreshLayout.refreshComplete();
                }
                mVideoListAdapter.replaceData(vedioDataBeen);
            }
        },paramsMap, ((RxAppCompatActivity) getActivity()));

        HttpManager.getInstance().doHttpDeal(apiVedioList);
    }


    public static class VideoListAdapter extends BaseQuickAdapter<VideoBean,BaseViewHolder>{


        private final Activity mActivity;

        public VideoListAdapter(Activity activity, @Nullable List<VideoBean> data) {
            super(R.layout.item_vedio_layout, data);
            mActivity = activity;
        }


        @Override
        protected void convert(BaseViewHolder helper, final VideoBean item) {
            try {
                helper.setText(R.id.tv_news_title,item.getContent().getTitle());

                Glide.with(mActivity).load(item.getContent().getLarge_image_list().get(0).getUrl()).into(((ImageView) helper.getView(R.id.iv_image)));

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //// TODO: 2018/4/11
                        //mActivity.startActivity(VideoItemDetailActivity.getIntent(mActivity,item));
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onRetry() {
        super.onRetry();
        //之类我们执行相应的逻辑；
        getDataFromNet(true);
    }

}
