package com.huanxi.renrentoutiao.ui.fragment.l_video;

import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.Constants;
import com.huanxi.renrentoutiao.interfaces.OnItemClickListener;
import com.huanxi.renrentoutiao.model.bean.l_video.VideoBean;
import com.huanxi.renrentoutiao.net.http.HttpCallback;
import com.huanxi.renrentoutiao.net.http.HttpUtil;
import com.huanxi.renrentoutiao.ui.view.video.RefreshAdapter;
import com.huanxi.renrentoutiao.ui.view.video.RefreshView;
import com.huanxi.renrentoutiao.utils.VideoStorge;
import com.huanxi.renrentoutiao.ui.view.video.ItemDecoration;
import com.huanxi.renrentoutiao.ui.activity.video.VideoPlayActivity;
import com.huanxi.renrentoutiao.ui.adapter.l_video.FollowVideoAdapter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cxf on 2018/6/9.
 */

public class HomeHotFragment extends AbsFragment implements OnItemClickListener<VideoBean> {

    private RefreshView mRefreshView;
    private FollowVideoAdapter mFollowAdapter;
    private boolean mFirst = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_hot;
    }

    @Override
    protected void main() {
        mRefreshView = (RefreshView) mRootView.findViewById(R.id.refreshView);
        mRefreshView.setNoDataLayoutId(R.layout.view_no_data_default);
        mRefreshView.setDataHelper(new RefreshView.DataHelper<VideoBean>() {

            @Override
            public RefreshAdapter<VideoBean> getAdapter() {
                if (mFollowAdapter == null) {
                    mFollowAdapter = new FollowVideoAdapter(mContext);
                    mFollowAdapter.setOnItemClickListener(HomeHotFragment.this);
                }
                return mFollowAdapter;
            }

            @Override
            public void loadData(int p, HttpCallback callback) {
                HttpUtil.getVideoList(p, callback);
            }

            @Override
            public List<VideoBean> processData(String[] info) {
                return JSON.parseArray(Arrays.toString(info), VideoBean.class);
            }

            @Override
            public void onRefresh(List<VideoBean> list) {
                VideoStorge.getInstance().put(Constants.VIDEO_HOT, list);
            }
            @Override
            public void onNoData(boolean noData) {

            }

            @Override
            public void onLoadDataCompleted(int dataCount) {

            }

        });
        mRefreshView.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false));
        ItemDecoration decoration = new ItemDecoration(mContext, 0x00000000, 2, 2);
        decoration.setOnlySetItemOffsetsButNoDraw(true);
        mRefreshView.setItemDecoration(decoration);

        refresh();
    }

    @Override
    public void onItemClick(VideoBean bean, int position) {
        if (mRefreshView != null && bean != null && bean.getUserinfo() != null) {
            VideoPlayActivity.forwardVideoPlay(mContext, Constants.VIDEO_HOT, position, mRefreshView.getPage(), bean.getUserinfo(),bean.getIsattent());
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (mFirst) {
                mFirst = false;
                mRefreshView.initData();
            }
        }
    }

    @Override
    public void onDestroyView() {
        HttpUtil.cancel(HttpUtil.GET_VIDEO_LIST);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if(mRefreshView!=null){
            mRefreshView.setDataHelper(null);
        }
        super.onDestroy();
    }

    /**
     * 刷新数据
     */
    public void refresh() {
        if(mRefreshView != null) {
            mRefreshView.initData();
        }
    }
}
