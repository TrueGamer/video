package com.huanxi.renrentoutiao.ui.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.db.ta.sdk.TmActivity;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.NewsItemBean;
import com.huanxi.renrentoutiao.presenter.ads.gdt.GDTUpTextDownImgPresenter;
import com.huanxi.renrentoutiao.presenter.ads.gdt.GdtNativeAds;
import com.huanxi.renrentoutiao.presenter.ads.ta.TaLeftTextRightImgPresenter;
import com.huanxi.renrentoutiao.presenter.ads.ta.TaNativeAds;
import com.huanxi.renrentoutiao.service.AdDownloadServiceNew;
import com.huanxi.renrentoutiao.ui.activity.WebHelperActivity;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.activity.news.NewsDetailActivity2;
import com.huanxi.renrentoutiao.utils.ImageUtils;
import com.qq.e.ads.nativ.NativeExpressADView;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

/**
 * Created by Dinosa on 2018/4/9.
 * 这里是之前的一个HomeTabFragmentAdapterNew;
 */

public class HomeTabFragmentAdapterOld extends BaseMultiItemQuickAdapter<NewsItemBean, BaseViewHolder> {


    private final OnlyTextViewHolder mOnlyTextViewHolder;
    private final OnlyOneImgViewHolder mOnlyOneImgViewHolder;
    private final ThreeImgViewHolder mThreeImgViewHolder;
    private final BaseActivity mActivity;
    private final GDTUpTextDownImgPresenter mGDTUpTextDownImgPresenter;
    private final TaLeftTextRightImgPresenter mTaLeftTextRightImgPresenter;




    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HomeTabFragmentAdapterOld(BaseActivity activity, List<NewsItemBean> data) {
        super(data);
        mActivity = activity;
        addItemType(NewsItemBean.ONLY_TEXT, R.layout.item_home_only_text);
        addItemType(NewsItemBean.ONLY_ONE_IMG, R.layout.item_home_img_one);
        addItemType(NewsItemBean.ONLY_THREE_IMG, R.layout.item_home_img_three);
        addItemType(NewsItemBean.GDT_NATIVE_AD, R.layout.item_gdt_up_text_down_img);
        addItemType(NewsItemBean.TA_NATIVE_AD, R.layout.item_ta_left_text_right_img);

        addItemType(NewsItemBean.CUSTOM_ONLY_IMG, R.layout.item_customer_ad1);
        addItemType(NewsItemBean.CUSTOM_UP_TITLE_DOWN_IMG, R.layout.item_customer_ad2);
        addItemType(NewsItemBean.CUSTOM_LEFT_TITLE_RIGHT_IMG, R.layout.item_customer_ad3);
        addItemType(NewsItemBean.CUSTOM_BANNER_20_3, R.layout.item_customer_ad4);
        addItemType(NewsItemBean.CUSTOM_UP_TITLE_DOWN_MUILT_IMG, R.layout.item_customer_ad5);

        mOnlyTextViewHolder = new OnlyTextViewHolder();
        mOnlyOneImgViewHolder = new OnlyOneImgViewHolder();
        mThreeImgViewHolder = new ThreeImgViewHolder();
        mGDTUpTextDownImgPresenter = new GDTUpTextDownImgPresenter();
        mTaLeftTextRightImgPresenter = new TaLeftTextRightImgPresenter();
    }

    @Override
    protected void convert(final BaseViewHolder helper, final NewsItemBean item) {

        System.out.println("2333进来了");
        doItemClick(helper, item);

        switch (item.getItemType()) {
            case NewsItemBean.ONLY_TEXT:

                mOnlyTextViewHolder.init(helper, item);
                break;

            case NewsItemBean.ONLY_ONE_IMG:
                mOnlyOneImgViewHolder.init(helper, item);
                break;

            case NewsItemBean.ONLY_THREE_IMG:
                mThreeImgViewHolder.init(helper, item);
                break;
            case NewsItemBean.GDT_NATIVE_AD:
                //这里要做的一个操作就是加载原生的广告；
                doAddGDTView(helper);
                //mGDTUpTextDownImgPresenter.init(helper.itemView);
                break;
            case NewsItemBean.TA_NATIVE_AD:
                //doAddGDTView(helper);
                //mTaLeftTextRightImgPresenter.init(helper.itemView);
                initTaLeftTa(helper);
                break;
            case NewsItemBean.CUSTOM_ONLY_IMG:
                //仅仅只有图片的广告
                initCustomerOnlyImg(helper,item);
                break;
            case NewsItemBean.CUSTOM_UP_TITLE_DOWN_IMG:
                //上文下图的广告；
                initCustomerUpTitleDownImg(helper,item);
                break;
            case NewsItemBean.CUSTOM_LEFT_TITLE_RIGHT_IMG:
                //左文右边图
                initCustomerLeftTitleRightImg(helper,item);
                break;
            case NewsItemBean.CUSTOM_BANNER_20_3:
                initBanner20_3(helper.itemView,item);
                break;
            case NewsItemBean.CUSTOM_UP_TITLE_DOWN_MUILT_IMG:

                initUpTitleDownImgs(helper.itemView,item);
                break;
        }
    }


    public void initUpTitleDownImgs(View helper, final NewsItemBean item){

        TextView view = (TextView) helper.findViewById(R.id.tv_ad_title);

        view.setText(item.getCont());

        ImageView image1 = (ImageView) helper.findViewById(R.id.iv_img1);
        ImageView image2 = (ImageView) helper.findViewById(R.id.iv_img2);
        ImageView image3 = (ImageView) helper.findViewById(R.id.iv_img3);

        try {
            ImageUtils.loadImage(mActivity,item.getImgurls().get(0),image1);
            ImageUtils.loadImage(mActivity,item.getImgurls().get(1),image2);
            ImageUtils.loadImage(mActivity,item.getImgurls().get(2),image3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        helper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(item.getDownurl())) {
                    //这里要做的逻辑就是判断开启任务下载
                    Intent intent = new Intent(mActivity, AdDownloadServiceNew.class);
                    intent.putExtra(AdDownloadServiceNew.DOWNLOAD_INTERFACE_BEAN,item);
                    mActivity.startService(intent);
                }else {
                    //这里表示要进入一个网页的操作；
                    mActivity.startActivity(WebHelperActivity.getIntent(mActivity,item.getUrl(),item.getTitle()));
                }
            }
        });
    }

    /**
     * 自定义20_3广告位置；
     * @param helper
     * @param item
     */
    private void initBanner20_3(View helper, final NewsItemBean item) {


        ImageView view = (ImageView) helper.findViewById(R.id.iv_custom_banner);
        ImageUtils.loadImage(mActivity,item.getImgurl(),view);

        helper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(item.getDownurl())) {
                    //这里要做的逻辑就是判断开启任务下载
                    Intent intent = new Intent(mActivity, AdDownloadServiceNew.class);
                    intent.putExtra(AdDownloadServiceNew.DOWNLOAD_INTERFACE_BEAN,item);
                    mActivity.startService(intent);
                }else {
                    //这里表示要进入一个网页的操作；
                    mActivity.startActivity(WebHelperActivity.getIntent(mActivity,item.getUrl(),item.getTitle()));
                }
            }
        });

    }

    /**
     * 这里是左边文右图的操作；
     * @param helper
     * @param item
     */
    private void initCustomerLeftTitleRightImg(BaseViewHolder helper, final NewsItemBean item) {

        TextView textView = (TextView) helper.getView(R.id.tv_news_title);

        textView.setText(item.getCont());

        ImageView imageView = (ImageView) helper.getView(R.id.iv_news_img);
        ImageUtils.loadImage(mActivity,item.getImgurl(),imageView);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里要做的逻辑就是
                if (!TextUtils.isEmpty(item.getDownurl())) {
                    //这里要做的逻辑就是判断开启任务下载
                    Intent intent = new Intent(mActivity, AdDownloadServiceNew.class);
                    intent.putExtra(AdDownloadServiceNew.DOWNLOAD_INTERFACE_BEAN,item);
                    mActivity.startService(intent);
                }else {
                    //这里表示要进入一个网页的操作；
                    mActivity.startActivity(WebHelperActivity.getIntent(mActivity,item.getUrl(),item.getTitle(),false));
                }
            }
        });
    }

    /**
     * 自定义的上文下图的广告；
     * @param helper
     */
    private void initCustomerUpTitleDownImg(BaseViewHolder helper, final NewsItemBean item) {

        //这里是显示上文下图的操作；
        TextView tvTitle = helper.getView(R.id.tv_title);

        ImageView ivImg = helper.getView(R.id.iv_image);

        ImageUtils.loadImage(mActivity,item.getImgurl(),ivImg);
        tvTitle.setText(item.getCont());

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里我们要做对应的判断逻辑；
                if (!TextUtils.isEmpty(item.getDownurl())) {
                    //这里要做的逻辑就是判断开启任务下载
                    //这里要做的一个操作
                    Intent intent = new Intent(mActivity, AdDownloadServiceNew.class);
                    intent.putExtra(AdDownloadServiceNew.DOWNLOAD_INTERFACE_BEAN,item);
                    mActivity.startService(intent);
                }else {
                    //这里表示要进入一个网页的操作；
                    mActivity.startActivity(WebHelperActivity.getIntent(mActivity,item.getUrl(),item.getTitle(),false));
                }
            }
        });
    }

    /**
     * 自定义的只有图的广告；
     * @param helper
     */
    private void initCustomerOnlyImg(BaseViewHolder helper, final NewsItemBean item) {

        ImageView view = (ImageView) helper.getView(R.id.iv_ad_banner);
        ImageUtils.loadImage(mActivity,item.getImgurl(),view);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(item.getDownurl())) {
                    //这里要做的逻辑就是判断开启任务下载
                    Intent intent = new Intent(mActivity, AdDownloadServiceNew.class);
                    intent.putExtra(AdDownloadServiceNew.DOWNLOAD_INTERFACE_BEAN,item);
                    mActivity.startService(intent);
                }else {
                    //这里表示要进入一个网页的操作；
                    mActivity.startActivity(WebHelperActivity.getIntent(mActivity,item.getUrl(),item.getTitle(),false));
                }

            }
        });

    }


    protected void doItemClick(BaseViewHolder helper, final NewsItemBean item) {


        //这里我们要做的一个操作就是;
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ("contentbyhtml".equals(item.getQmttcontenttype())) {
                    //mActivity.startActivity(NewsDetailRecycleViewActivity.getIntent(mActivity, item.getUrl(), item.getUrlmd5()));
                    //mActivity.startActivity(NewsDetailActivity.getIntent(mActivity,item.getUrl()));
                    //这里跳转到WebView,这里表示是东方头条自己的广告；
                    TmActivity.a(mContext, item.getUrl());
                } else {
                    mActivity.startActivity(NewsDetailActivity2.getIntent(mActivity,item.getUrl()
                            ,item.getUrlmd5() , "xiaohua" , 1));
                }
            }
        });
    }


    private void initTaLeftTa(BaseViewHolder helper) {

        TaNativeAds taNativeAds = TaNativeAds.newInstance();

        View leftTextAdView = taNativeAds.getLeftText();


        ViewGroup itemView = (ViewGroup) helper.itemView;

        if (itemView.getChildCount() > 0) {

            View view = itemView.getChildAt(0);
            //这样写还是有点问题的，比如activity结束后没有办法释放View给Gdt的View的；
            if (view instanceof LinearLayout) {
                taNativeAds.releaseLeftText(view);
            }

            itemView.removeAllViews();
        }

        itemView.addView(leftTextAdView);
    }


    /*
    这里要做的一个操作就是添加广点通的广告；
    */
    private void doAddGDTView(BaseViewHolder helper) {


        GdtNativeAds gdtNativeAds = GdtNativeAds.newInstance();

        NativeExpressADView gdtadView = gdtNativeAds.getAdView();

        if(gdtadView != null){

            ViewGroup itemView = (ViewGroup) helper.itemView;

            gdtadView.setPadding(UIUtil.dip2px(mContext, 12), UIUtil.dip2px(mContext, 12),
                    UIUtil.dip2px(mContext, 10), UIUtil.dip2px(mContext, 10));

            if (itemView.getChildCount() > 0) {

                View view = itemView.getChildAt(0);
                //这样写还是有点问题的，比如activity结束后没有办法释放View给Gdt的View的；
                if (view instanceof NativeExpressADView) {
                    gdtNativeAds.removeADView(((NativeExpressADView) view));
                }

                itemView.removeAllViews();
            }

            itemView.addView(gdtadView, 0);

            gdtadView.render();
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_line, itemView, false);
            itemView.addView(view, 1);
        }
    }

    public class OnlyTextViewHolder {
        public void init(BaseViewHolder holder, NewsItemBean item) {
            holder.setText(R.id.tv_title, item.getTopic());

            holder.setText(R.id.tv_source, item.getSource());
            holder.setText(R.id.tv_time, item.getDate());

        }
    }

    public class OnlyOneImgViewHolder {
        public void init(BaseViewHolder holder, NewsItemBean item) {
            holder.setText(R.id.tv_news_title, item.getTopic());

            ImageUtils.loadImage(mActivity, item.getMiniimg().get(0).getSrc(), (ImageView) holder.getView(R.id.iv_news_img));

            holder.setText(R.id.tv_source, item.getSource());
            holder.setText(R.id.tv_time, item.getDate());
        }
    }

    public class ThreeImgViewHolder {
        public void init(BaseViewHolder holder, NewsItemBean item) {

            holder.setText(R.id.tv_news_title, item.getTopic());

            ImageUtils.loadImage(mActivity, item.getMiniimg().get(0).getSrc(), (ImageView) holder.getView(R.id.iv_img1));
            ImageUtils.loadImage(mActivity, item.getMiniimg().get(1).getSrc(), (ImageView) holder.getView(R.id.iv_img2));
            ImageUtils.loadImage(mActivity, item.getMiniimg().get(2).getSrc(), (ImageView) holder.getView(R.id.iv_img3));

            holder.setText(R.id.tv_source, item.getSource());
            holder.setText(R.id.tv_time, item.getDate());
        }
    }
}
