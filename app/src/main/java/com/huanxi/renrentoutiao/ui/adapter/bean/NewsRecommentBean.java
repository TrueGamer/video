package com.huanxi.renrentoutiao.ui.adapter.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.model.bean.NewsItemBean;
import com.huanxi.renrentoutiao.ui.adapter.NewsDetailAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinosa on 2018/2/25.
 */

public class NewsRecommentBean implements MultiItemEntity{

    List<NewsItemBean> mNewsItemBeen=new ArrayList<>();

    public List<NewsItemBean> getNewsItemBeen() {
        return mNewsItemBeen;
    }

    public void setNewsItemBeen(List<NewsItemBean> newsItemBeen) {
        mNewsItemBeen = newsItemBeen;
    }

    @Override
    public int getItemType() {
        return NewsDetailAdapter.TYPE_RECOMMENT;
    }

    //新闻里面的相关推荐
}
