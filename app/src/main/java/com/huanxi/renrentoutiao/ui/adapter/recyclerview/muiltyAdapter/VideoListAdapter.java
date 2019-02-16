package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.base.BaseMuiltyAdapter;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.base.MuiltyBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.baidu.BaiDuAdHolder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.custom.Banner20_3Holder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.custom.BigBannerHolder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.custom.LeftTitleRightImgHolder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.custom.UpTitleDownImgHolder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.custom.UpTitleDownThreeImgHolder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.gdt.GdtBigBannerAdHolder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.ta.TaUpTitleDownImgHolder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.video.VideoListBeanHolder;
import com.huanxi.renrentoutiao.ui.media.hoder.GroupAdViewHolder;
import com.huanxi.renrentoutiao.ui.media.hoder.LargeAdViewHolder;
import com.huanxi.renrentoutiao.ui.media.hoder.SmallAdViewHolder;
import com.huanxi.renrentoutiao.ui.media.hoder.VideoAdViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinosa on 2018/4/10.
 * 这里是新闻的最新的adapter;
 */

public class VideoListAdapter extends BaseMuiltyAdapter {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public VideoListAdapter(List<MultiItemEntity> data,String homeTabId) {
        super(data);
        //这里要注册多种数据类型；
        //这里要注册多种数据类型；
        ArrayList<MuiltyBean> muiltyBeen = new ArrayList<>();

        //item_new_vedio_layout   item_news_article_video
        muiltyBeen.add(new MuiltyBean(new VideoListBeanHolder(homeTabId), R.layout.item_new_vedio_layout));  //视频的逻辑；


        muiltyBeen.add(new MuiltyBean(new BigBannerHolder(), R.layout.item_customer_ad1));  //大banner；
        muiltyBeen.add(new MuiltyBean(new UpTitleDownImgHolder(), R.layout.item_customer_ad2));  //广告上文下图
        muiltyBeen.add(new MuiltyBean(new LeftTitleRightImgHolder(), R.layout.item_customer_ad3));  //左文右图
        muiltyBeen.add(new MuiltyBean(new Banner20_3Holder(), R.layout.item_customer_ad4));  //小banner；
        muiltyBeen.add(new MuiltyBean(new UpTitleDownThreeImgHolder(), R.layout.item_customer_ad5));  //上文下三图

        muiltyBeen.add(new MuiltyBean(new GdtBigBannerAdHolder(), R.layout.item_gdt_up_text_down_img));  //广点通的广告；
        muiltyBeen.add(new MuiltyBean(new TaUpTitleDownImgHolder(), R.layout.item_ad_container));  //推啊的广告；

        // 网盟广告
        muiltyBeen.add(new MuiltyBean(new VideoAdViewHolder(), R.layout.listitem_ad_large_video));
        muiltyBeen.add(new MuiltyBean(new LargeAdViewHolder(), R.layout.listitem_ad_large_pic));
        muiltyBeen.add(new MuiltyBean(new SmallAdViewHolder(), R.layout.listitem_ad_small_pic));
        muiltyBeen.add(new MuiltyBean(new GroupAdViewHolder(), R.layout.listitem_ad_group_pic));

        muiltyBeen.add(new MuiltyBean(new BaiDuAdHolder() , R.layout.item_baidu_ad_layout));

        //每次增加这种绑定关系就ojbk
        //muiltyBeen.add(new MuiltyBean(new Banner20_3Holder(), R.layout.item_new_vedio_layout));  //其他
        register(muiltyBeen);
    }
}