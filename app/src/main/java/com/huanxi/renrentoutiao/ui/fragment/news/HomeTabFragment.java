package com.huanxi.renrentoutiao.ui.fragment.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantThreePart;
import com.huanxi.renrentoutiao.model.bean.NewsItemBean;
import com.huanxi.renrentoutiao.net.api.news.ApiHomeNews;
import com.huanxi.renrentoutiao.net.bean.news.HomeTabBean;
import com.huanxi.renrentoutiao.net.bean.news.ResTabBean;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.adapter.HomeTabFragmentAdapter;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingRecyclerViewFragment;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dinosa on 2018/1/15.
 * 这里是homeTab的Fragment
 */

public class HomeTabFragment extends BaseLoadingRecyclerViewFragment {

    public static final String TAB_BEAN="tabBean";

    private HomeTabBean mHomeTabBean;
    protected HomeTabFragmentAdapter mAdapter;


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


    @Override
    public int getLoadingContentLayoutId() {
        return R.layout.layout_refresh_recycler_view;
    }


    protected int mPage=1;

    public void getData(final boolean isShowContent){

        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put("type",mHomeTabBean.getCode());
        paramsMap.put("qid", ConstantThreePart.ADMAMA_ID);
        paramsMap.put(ApiHomeNews.PAGE_NUM,"1");

        ApiHomeNews apiHomeNews = new ApiHomeNews(((RxAppCompatActivity) getActivity()), paramsMap,getRefreshResutlListener(isShowContent));

        HttpManager.getInstance().doHttpDeal(apiHomeNews);
    }

    public HttpOnNextListener<ResTabBean> getRefreshResutlListener(final boolean isShowContent){

        HttpOnNextListener<ResTabBean> httpOnNextListener = new HttpOnNextListener<ResTabBean>() {

            @Override
            public void onNext(ResTabBean resTabBean) {
                if (resTabBean.getList() != null) {
                    List<NewsItemBean> dataBeen = resTabBean.getList();
                    mPage=2;
                    mAdapter.replaceData(dataBeen);
                    if (isShowContent) {
                        showSuccess();
                    } else {
                        refreshComplete();
                        if(resTabBean.getList().size()>0){
                            //mNewsRefreshBanner.show(resTabBean.getList().size());
                        }
                    }
                }else{
                    showEmpty();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (isShowContent) {
                    showFaild();
                } else {
                    refreshComplete();
                }
            }

        };
        return httpOnNextListener;
    }

    /**
     * 获取HomeTabFragment
     * @param bean 传入的对象；
     * @return 一个HomeTabFragment;
     */
    public static HomeTabFragment getHomeTabFragment(HomeTabBean bean){
        HomeTabFragment homeTabFragment = new HomeTabFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAB_BEAN,bean);
        homeTabFragment.setArguments(bundle);
        return homeTabFragment;
    }



    public void loadMore(){

        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put("type",mHomeTabBean.getCode());
        paramsMap.put("qid",ConstantThreePart.ADMAMA_ID);
        paramsMap.put("num","20");
        paramsMap.put(ApiHomeNews.PAGE_NUM,mPage+"");

        ApiHomeNews apiHomeNews=new ApiHomeNews(((RxAppCompatActivity) getActivity()), paramsMap ,getLoadMoreListener());
        HttpManager.getInstance().doHttpDeal(apiHomeNews);

    }

    public HttpOnNextListener<ResTabBean> getLoadMoreListener(){

        return new HttpOnNextListener<ResTabBean>() {

            @Override
            public void onNext(ResTabBean resTabBean) {
                List<NewsItemBean> dataBeen = resTabBean.getList();

                if(dataBeen!=null && dataBeen.size()>0){
                    mPage++;
                    mAdapter.addData(dataBeen);
                }
                loadMoreComplete();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                loadMoreComplete();
            }
        };
    }


    @Override
    public RecyclerView.Adapter getAdapter() {
        if (mAdapter==null) {
            mAdapter = new HomeTabFragmentAdapter(((BaseActivity) getActivity()),null);
        }
        return mAdapter;
    }

    @Override
    public void requestAdapterData(boolean isFirst) {
        getData(isFirst);
    }

    @Override
    public void requestNextAdapterData() {
        loadMore();
    }


}
