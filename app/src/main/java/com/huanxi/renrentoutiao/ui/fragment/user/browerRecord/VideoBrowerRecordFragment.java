package com.huanxi.renrentoutiao.ui.fragment.user.browerRecord;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.user.browerRecord.ApiBrowerRecordNewsList;
import com.huanxi.renrentoutiao.net.api.user.browerRecord.ApiBrowerRecordVideoList;
import com.huanxi.renrentoutiao.net.bean.browerRecord.ResUserVideoBrowerRecord;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.activity.other.MainActivity;
import com.huanxi.renrentoutiao.ui.adapter.VideoListAdapter;
import com.huanxi.renrentoutiao.ui.adapter.bean.VideoBean;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingRecyclerViewFragment;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dinosa on 2018/2/3.
 */

public class VideoBrowerRecordFragment extends BaseLoadingRecyclerViewFragment {

    public int page = 1;

    private VideoListAdapter mHomeTabFragmentAdapter;

    @Override
    public RecyclerView.Adapter getAdapter() {
        if (mHomeTabFragmentAdapter == null) {
            mHomeTabFragmentAdapter = new VideoListAdapter(((BaseActivity) getActivity()), null,new LinkedList<View>(),null);


            //下面的是页面；
            View emptyView = View.inflate(getBaseActivity(), R.layout.fragment_invite_friend_empty, null);

            TextView textView =(TextView) emptyView.findViewById(R.id.tv_empty_desc);

            String html=getEmptyDesc();
            textView.setText(Html.fromHtml(html));

            mHomeTabFragmentAdapter.setEmptyView(emptyView);

            emptyView.findViewById(R.id.ll_invite_friend).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //这里我们要做的一个操作就是显示分享的对话框；
                    //这里是邀请好友的链接操作；
                    Intent intent = new Intent(getBaseActivity(), MainActivity.class);
                    intent.putExtra(MainActivity.TAB_INDEX,MainActivity.VIDEO);
                    startActivity(intent);
                }
            });

        }
        return mHomeTabFragmentAdapter;
    }

    public String getEmptyDesc() {
        return "暂无记录 , <font color='#ff7c34'>去浏览</font>";
    }

    @Override
    public void requestAdapterData(final boolean isFirst) {

        page = 1;
        UserBean userBean = ((BaseActivity) getActivity()).getUserBean();

        if(userBean != null) {
            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put(ApiBrowerRecordVideoList.FROM_UID, userBean.getUserid());
            paramsMap.put(ApiBrowerRecordVideoList.PAGE_SIZE, page + "");


            //这里我们的接口会发生变化；
            ApiBrowerRecordVideoList apiBrowerRecordNewsList = new ApiBrowerRecordVideoList(getRequestResultListener(isFirst), ((BaseActivity) getActivity()), paramsMap);

            HttpManager.getInstance().doHttpDeal(apiBrowerRecordNewsList);
        }
    }

    /**
     * 过滤出我们想要的类型
     *
     * @param resTabBean
     * @return
     */
    private List<VideoBean> getVideoBeanList(ResUserVideoBrowerRecord resTabBean) {
        //这里我们要做的一个操作就是一个过滤的操作；
        ArrayList<VideoBean> newsItemBeen = new ArrayList<>();
        if (resTabBean != null) {
            List<ResUserVideoBrowerRecord.VideoRecordBean> list = resTabBean.getList();
            for (ResUserVideoBrowerRecord.VideoRecordBean newsRecordBean : list) {
                newsItemBeen.add(newsRecordBean.getContent());
            }
        }
        return newsItemBeen;
    }

    @Override
    public void requestNextAdapterData() {
        UserBean userBean = ((BaseActivity) getActivity()).getUserBean();

        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put(ApiBrowerRecordNewsList.FROM_UID, userBean.getUserid());
        paramsMap.put(ApiBrowerRecordNewsList.PAGE_NUM, page + "");

        ApiBrowerRecordVideoList apiHomeNews = new ApiBrowerRecordVideoList(getLoadMoreResultListener(), ((RxAppCompatActivity) getActivity()), paramsMap);
        HttpManager.getInstance().doHttpDeal(apiHomeNews);
    }

    /**
     * 这里是获取请求视频的接口；第一个进来的接口；
     *
     * @param isFirst
     * @return
     */
    public HttpOnNextListener<ResUserVideoBrowerRecord> getRequestResultListener(final boolean isFirst) {
        return new HttpOnNextListener<ResUserVideoBrowerRecord>() {

            @Override
            public void onNext(ResUserVideoBrowerRecord resTabBean) {
                if (resTabBean.getList() != null) {
                    List<VideoBean> dataBeen = getVideoBeanList(resTabBean);
                    mHomeTabFragmentAdapter.replaceData(dataBeen);
                    page++;

                }

                if (isFirst) {
                    showSuccess();

                } else {
                    //getEasyRefreshLayout().refreshComplete();
                    refreshComplete();
                }

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (isFirst) {
                    showFaild();
                } else {
                    //getEasyRefreshLayout().refreshComplete();
                    refreshComplete();
                }
            }
        };
    }


    public HttpOnNextListener<ResUserVideoBrowerRecord> getLoadMoreResultListener() {
        return new HttpOnNextListener<ResUserVideoBrowerRecord>() {

            @Override
            public void onNext(ResUserVideoBrowerRecord resTabBean) {

                if (resTabBean.getList() != null && resTabBean.getList().size() > 0) {
                    //这里我们要做的一个逻辑就是
                    mHomeTabFragmentAdapter.addData(getVideoBeanList(resTabBean));
                    page++;
                } else {
                    if (page != 1) {
                        ((BaseActivity) getActivity()).toast("没有更多数据");
                    }
                }
                //getEasyRefreshLayout().loadMoreComplete();
                loadMoreComplete();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                loadMoreComplete();
            }
        };
    }


    public void clear() {
        mHomeTabFragmentAdapter.getData().clear();
        mHomeTabFragmentAdapter.notifyDataSetChanged();
    }

}
