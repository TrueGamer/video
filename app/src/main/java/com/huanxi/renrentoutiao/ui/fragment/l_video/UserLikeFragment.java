package com.huanxi.renrentoutiao.ui.fragment.l_video;

import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.huanxi.renrentoutiao.AppConfig;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.Constants;
import com.huanxi.renrentoutiao.interfaces.OnItemClickListener;
import com.huanxi.renrentoutiao.model.bean.l_video.VideoBean;
import com.huanxi.renrentoutiao.net.http.HttpCallback;
import com.huanxi.renrentoutiao.net.http.HttpUtil;
import com.huanxi.renrentoutiao.ui.activity.video.VideoPlayActivity;
import com.huanxi.renrentoutiao.ui.adapter.l_video.UserLikeAdapter;
import com.huanxi.renrentoutiao.ui.view.video.ItemDecoration;
import com.huanxi.renrentoutiao.ui.view.video.NeedRefreshLikeEvent;
import com.huanxi.renrentoutiao.ui.view.video.RefreshAdapter;
import com.huanxi.renrentoutiao.ui.view.video.RefreshView;
import com.huanxi.renrentoutiao.utils.VideoStorge;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cxf on 2018/6/10.
 */

public class UserLikeFragment extends UserItemFragment implements OnItemClickListener<VideoBean> {

    private RefreshView mRefreshView;
    private UserLikeAdapter mAdapter;
    private boolean mNeedRefresh;
    private boolean mPaused;
    private boolean mUserChanged;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_video_like;
    }

    @Override
    protected void main() {
        super.main();
        mRefreshView = (RefreshView) mRootView.findViewById(R.id.refreshView);
        if (!TextUtils.isEmpty(mUid) && mUid.equals(AppConfig.getInstance().getUid())) {//这是自己
            mRefreshView.setNoDataLayoutId(R.layout.view_no_data_user_like);
        } else {
            mRefreshView.setNoDataLayoutId(R.layout.view_no_data_user_like_2);
        }
        mRefreshView.setDataHelper(new RefreshView.DataHelper<VideoBean>() {

            @Override
            public RefreshAdapter<VideoBean> getAdapter() {
                if (mAdapter == null) {
                    mAdapter = new UserLikeAdapter(mContext);
                    mAdapter.setOnItemClickListener(UserLikeFragment.this);
                }
                return mAdapter;
            }

            @Override
            public void loadData(int p, HttpCallback callback) {
                if (!Constants.NOT_LOGIN_UID.equals(mUid)) {
                    HttpUtil.getLikeVideos(mUid, p, callback);
                }
            }

            @Override
            public List<VideoBean> processData(String[] info) {
                return JSON.parseArray(Arrays.toString(info), VideoBean.class);
            }

            @Override
            public void onRefresh(List<VideoBean> list) {
                VideoStorge.getInstance().put(mHashCode, list);
            }

            @Override
            public void onNoData(boolean noData) {

            }

            @Override
            public void onLoadDataCompleted(int dataCount) {
                if (dataCount <= 0) {
                    mRefreshView.setLoadMoreEnable(false);
                } else {
                    mRefreshView.setLoadMoreEnable(true);
                }
            }
        });
        mRefreshView.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false));
        ItemDecoration decoration = new ItemDecoration(mContext, 0x00000000, 2, 2);
        decoration.setOnlySetItemOffsetsButNoDraw(true);
        mRefreshView.setItemDecoration(decoration);
        if (mIsMainUserCenter) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onItemClick(VideoBean bean, int position) {
        if (mRefreshView != null && bean != null && bean.getUserinfo() != null) {
            VideoPlayActivity.forwardVideoPlay(mContext, mHashCode, position, mRefreshView.getPage(), bean.getUserinfo(), bean.getIsattent());
        }
    }

    @Override
    public void loadData() {
        if (mFirst) {
            mFirst = false;
            if (mRefreshView != null) {
                mRefreshView.initData();
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mIsMainUserCenter) {
            EventBus.getDefault().unregister(this);
        }
        HttpUtil.cancel(HttpUtil.GET_LIKE_VIDEOS);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNeedRefreshLikeEvent(NeedRefreshLikeEvent e) {
        if (mPaused) {
            mNeedRefresh = true;
        } else {
            if (mRefreshView != null) {
                mRefreshView.initData();
            }
        }
    }

    @Override
    public void onLoginUserChanged(String uid) {
        mUid = uid;
        mFirst = true;
//        if (mAdapter != null) {
//            mAdapter.clearData();
//        }
        mUserChanged = true;
    }

    @Override
    public void clearData() {
        if (mAdapter != null) {
            mAdapter.clearData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mPaused = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPaused) {
            mPaused = false;
            if (mUserChanged) {
                mUserChanged = false;
                if (mAdapter != null) {
                    mAdapter.clearData();
                }
            } else if (mNeedRefresh) {
                mNeedRefresh = false;
                if (mRefreshView != null) {
                    mRefreshView.initData();
                }
            }
        }
    }

}
