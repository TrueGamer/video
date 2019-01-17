package com.huanxi.renrentoutiao.presenter;

import android.os.Build;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.globle.ConstantThreePart;
import com.huanxi.renrentoutiao.model.bean.NewsCommentBean;
import com.huanxi.renrentoutiao.model.bean.NewsItemBean;
import com.huanxi.renrentoutiao.model.bean.media.MediaChannelDao;
import com.huanxi.renrentoutiao.net.api.news.ApiEndReadIssure;
import com.huanxi.renrentoutiao.net.api.news.ApiHomeNews;
import com.huanxi.renrentoutiao.net.api.news.ApiNewAdLog;
import com.huanxi.renrentoutiao.net.api.news.ApiNewsCommentList;
import com.huanxi.renrentoutiao.net.api.news.ApiNewsDetail;
import com.huanxi.renrentoutiao.net.api.news.ApiStartReadIssure;
import com.huanxi.renrentoutiao.net.api.share.ApiShareNewsAndVideoContent;
import com.huanxi.renrentoutiao.net.api.user.ApiInviteFriendDesc;
import com.huanxi.renrentoutiao.net.api.user.ApiShareSuccess;
import com.huanxi.renrentoutiao.net.api.user.ApiUserDoLike;
import com.huanxi.renrentoutiao.net.api.user.ApiUserShare;
import com.huanxi.renrentoutiao.net.api.user.collection.ApiUserCollection;
import com.huanxi.renrentoutiao.net.api.user.comment.ApiSendComment;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.net.bean.ResInviteFriendDesc;
import com.huanxi.renrentoutiao.net.bean.ResReadAwarad;
import com.huanxi.renrentoutiao.net.bean.ResRequestShare;
import com.huanxi.renrentoutiao.net.bean.ResSplashAds;
import com.huanxi.renrentoutiao.net.bean.comment.ResNewsCommentList;
import com.huanxi.renrentoutiao.net.bean.news.ResAward;
import com.huanxi.renrentoutiao.net.bean.news.ResNewsDetailBean;
import com.huanxi.renrentoutiao.net.bean.news.ResTabBean;
import com.huanxi.renrentoutiao.ui.activity.news.NewsDetailActivity2;
import com.huanxi.renrentoutiao.ui.adapter.AdBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.adhub.AdhubNativeBean;
import com.huanxi.renrentoutiao.utils.InfoUtil;
import com.huanxi.renrentoutiao.utils.SharedPreferencesUtils;
import com.huanxi.renrentoutiao.utils.TTAdManagerHolder;
import com.huanxi.renrentoutiao.utils.TToast;
import com.hubcloud.adhubsdk.NativeAd;
import com.hubcloud.adhubsdk.NativeAdListener;
import com.hubcloud.adhubsdk.NativeAdResponse;
import com.zhxu.library.exception.HttpTimeException;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import cn.tongdun.android.shell.db.utils.LogUtil;

/**
 * Created by Dinosa on 2018/2/6.
 *
 * 这里是新闻详情页的业务逻辑处理类；
 */

public class NewsDetailPresenter2 {

    private static final String ARTICLE_ID = "articleId";
    private static final String MD5URL = "md5url";
    private static final String NEWSTYPE = "newsType";
    private static final String PAGENUM = "pageNum";

    NewsDetailActivity2 mActivity;

    private String mMd5Url;
    private String mArticleId;
    private String mNewsType;
    private int mPageNum;
    private ApiUserDoLike mApiUserDoLike;
    private NativeAd mNativeAd;
    private LinkedList<NativeAdResponse> mAdhubNativeData;

    public NewsDetailPresenter2(NewsDetailActivity2 activity,LinkedList<NativeAdResponse> adResponses) {
        mActivity = activity;
        mArticleId = mActivity.getIntent().getStringExtra(ARTICLE_ID);
        mMd5Url = mActivity.getIntent().getStringExtra(MD5URL);
        mNewsType = mActivity.getIntent().getStringExtra(NEWSTYPE);
        mPageNum = mActivity.getIntent().getIntExtra(PAGENUM , 0);

        mAdhubNativeData = adResponses;
    }

    private void loadAdhubAd() {
        mNativeAd = new NativeAd(mActivity,ConstantAd.ADHUBAD.NATIVE_ID, 1, new NativeAdListener() {
            @Override
            public void onAdLoaded(NativeAdResponse nativeAdResponse) {
                mAdhubNativeData.add(nativeAdResponse);
            }

            @Override
            public void onAdFailed(int i) {
                LogUtil.e("info","adhub load add fail "+i);
//                throw new IllegalStateException("adhub load add fail "+i);
            }
        });
        mNativeAd.loadAd();
    }

    public String getUrlMd5(){
        return mMd5Url;
    }

    /**
     * 请求分享内容；
     */
    public void requestShareContent(){

        //请先登陆
        //请求获取服务器的分享列表；
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiUserShare.FROM_UID,mActivity.getUserBean().getUserid());
        paramsMap.put(ApiUserShare.TYPE,ApiUserShare.TYPE_ARTICLE);
        paramsMap.put(ApiUserShare.URLMD5,mMd5Url);

        ApiUserShare apiUserShare=new ApiUserShare(new HttpOnNextListener<ResRequestShare>() {
            @Override
            public void onNext(ResRequestShare resRequestShare) {
                //这里我们要做的一个操作就是进行一个分享的操作；
                //展示一个一键分享
                mActivity.showShareDialog(resRequestShare.getTitle(),resRequestShare.getDesc(),resRequestShare.getUrl());
            }
        },mActivity,paramsMap);

        HttpManager.getInstance().doHttpDeal(apiUserShare);
    }

    /**
     * 请求分享成功
     */
    public void requestShareSuccess(){

        //请先登陆
        //请求获取服务器的分享列表；
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiUserShare.FROM_UID,mActivity.getUserBean().getUserid());
        ApiShareSuccess apiUserShare=new ApiShareSuccess(new HttpOnNextListener<ResAward>() {
            @Override
            public void onNext(ResAward resRequestShare) {
                //这里表示分享成功
                mActivity.shareSuccess(resRequestShare);
            }
        },mActivity,paramsMap);

        HttpManager.getInstance().doHttpDeal(apiUserShare);
    }


    /**
     * 这里开始计时操作?
     */
    public  void getBeganStart(){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiStartReadIssure.FROM_UID,mActivity.getUserBean().getUserid());
        paramsMap.put(ApiStartReadIssure.URLMD5,mMd5Url);
        ApiStartReadIssure apiStartReadIssure = new ApiStartReadIssure(new HttpOnNextListener<ResEmpty>() {

            @Override
            public void onNext(ResEmpty resEmpty) {
                mActivity.requestStartCountDown();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (e instanceof HttpTimeException) {
                    HttpTimeException exception = (HttpTimeException) e;
                    //这里表示已经领取过该任务了
                    if(exception.getMessage().equals("您已领取过任务了")){
                        //这里我们是需要切换宝箱的状态了；
//                        mActivity.showOpenBoxLogo();
                        Toast toast = Toast.makeText(mActivity , "您已领取过任务了" , Toast.LENGTH_SHORT);
                        TextView v = (TextView)toast.getView().findViewById(android.R.id.message);
                        v.setTextSize(20);
                        toast.show();
                    }else if(exception.getMessage().equals("已领取")){
//                        mActivity.showOpenBoxLogo();
                        Toast toast = Toast.makeText(mActivity , "已领取" , Toast.LENGTH_SHORT);
                        TextView v = (TextView)toast.getView().findViewById(android.R.id.message);
                        v.setTextSize(20);
                        toast.show();
                    } else if (exception.getMessage().equals("今天已达到最大次数")) {
                        mActivity.hideReadProgress();
                        Toast toast = Toast.makeText(mActivity , "今天已达到最大次数" , Toast.LENGTH_SHORT);
                        TextView v = (TextView)toast.getView().findViewById(android.R.id.message);
                        v.setTextSize(20);
                        toast.show();
                    }
//                   mActivity.hideReadProgress();
                }
            }
        },mActivity,paramsMap);
        /*InfoUtil.getNetIp(new InfoUtil.NetCallback() {
            @Override
            public void onSuccess(String value) {
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put(ApiNewAdLog.TYPE,"0");
                paramsMap.put(ApiNewAdLog.SERVER_NUMBER,SharedPreferencesUtils.getInstance(mActivity).getString(SharedPreferencesUtils.CHANNEL));
                paramsMap.put(ApiNewAdLog.MAC_ADDRESS,InfoUtil.getMacAddress());
                paramsMap.put(ApiNewAdLog.PHONE_BRAND,Build.BRAND);
                paramsMap.put(ApiNewAdLog.PHONE_MODULE,Build.MODEL);
                paramsMap.put(ApiNewAdLog.SYSTEM_VERSION,Build.VERSION.RELEASE);
                paramsMap.put(ApiNewAdLog.IP,value);
                paramsMap.put(ApiNewAdLog.AD_CHANNEL_NUM,ApiNewAdLog.AD_CHANNEL_ADHUB);
//                paramsMap.put(ApiNewAdLog.AD_ID,);
                paramsMap.put(ApiNewAdLog.NEWS_ID,mMd5Url);

                ApiNewAdLog apiStartReadIssure = new ApiNewAdLog(new HttpOnNextListener<String>() {

                    @Override
                    public void onNext(String str) {
                        Log.d("TAG","onNext");
                        mActivity.requestStartCountDown();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (e instanceof HttpTimeException) {
                            HttpTimeException exception = (HttpTimeException) e;
                            //这里表示已经领取过该任务了
                            if(exception.getMessage().equals("您已领取过任务了")){
                                //这里我们是需要切换宝箱的状态了；
//                        mActivity.showOpenBoxLogo();
                                Toast toast = Toast.makeText(mActivity , "您已领取过任务了" , Toast.LENGTH_SHORT);
                                TextView v = (TextView)toast.getView().findViewById(android.R.id.message);
                                v.setTextSize(20);
                                toast.show();
                            }else if(exception.getMessage().equals("已领取")){
//                        mActivity.showOpenBoxLogo();
                                Toast toast = Toast.makeText(mActivity , "已领取" , Toast.LENGTH_SHORT);
                                TextView v = (TextView)toast.getView().findViewById(android.R.id.message);
                                v.setTextSize(20);
                                toast.show();
                            } else if (exception.getMessage().equals("今天已达到最大次数")) {
                                mActivity.hideReadProgress();
                                Toast toast = Toast.makeText(mActivity , "今天已达到最大次数" , Toast.LENGTH_SHORT);
                                TextView v = (TextView)toast.getView().findViewById(android.R.id.message);
                                v.setTextSize(20);
                                toast.show();
                            }
//                   mActivity.hideReadProgress();
                        } else {
                            e.printStackTrace();
                        }
                    }
                },mActivity,paramsMap);
                HttpManager.getInstance().doHttpDeal(apiStartReadIssure);
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
            }
        });*/

    }

    public boolean mHasGetCoin=false;

    /**
     * 结束计时操作
     */
    public void getReadCoin() {
        if(mHasGetCoin){
           return;
        }
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiEndReadIssure.FROM_UID,mActivity.getUserBean().getUserid());
        paramsMap.put(ApiEndReadIssure.URLMD5,mMd5Url);
        ApiEndReadIssure apiStartReadIssure=new ApiEndReadIssure(new HttpOnNextListener<ResReadAwarad>() {
            @Override
            public void onNext(ResReadAwarad resReadNewsAward) {
                Log.i("info" , "resReadNewsAward="+resReadNewsAward.toString());
                mHasGetCoin=true;
                mActivity.readAward(resReadNewsAward);
                //这里要更新进度；
           }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        },mActivity,paramsMap);
        HttpManager.getInstance().doHttpDeal(apiStartReadIssure);
    }

    /**
     * 请求收藏的页面的；
     */
    public void requestCollection(){

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiUserCollection.FROM_UID, mActivity.getUserBean().getUserid());
        paramsMap.put(ApiUserCollection.URLMD5, mMd5Url);

        ApiUserCollection apiUserCollection = new ApiUserCollection(new HttpOnNextListener<ResEmpty>() {

            @Override
            public void onNext(ResEmpty resEmpty) {
            }

        },mActivity, paramsMap);

        HttpManager.getInstance().doHttpDeal(apiUserCollection);
    }

    /**
     * 发送评论的操作；
     */
    public void requestSendComment(String content){

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiSendComment.ISSUE_ID, mMd5Url);
        paramsMap.put(ApiSendComment.FROM_UID, mActivity.getUserBean().getUserid());
        paramsMap.put(ApiSendComment.COMMENT_CONTENT, content);

        //这里做的一个操作就是发送评论；
        ApiSendComment apiSendComment = new ApiSendComment(new HttpOnNextListener<ResEmpty>() {
            @Override
            public void onNext(ResEmpty o) {
                mActivity.toast("评论成功");
                mPage=1;
                getCommentList();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mActivity.toast("评论失败");
            }
        }, mActivity, paramsMap);
        HttpManager.getInstance().doHttpDeal(apiSendComment);
    }


    /**
     * 获取新闻详情；
     */
    public void getNewsDetail(){


        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put(ApiNewsDetail.URL, mArticleId);
        if (mActivity.getUserBean() != null) {
            paramsMap.put(ApiNewsDetail.FROM_UID, mActivity.getUserBean().getUserid());
        }

        ApiNewsDetail mApiNewsDetail = new ApiNewsDetail(new HttpOnNextListener<ResNewsDetailBean>() {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mActivity.showError();
            }
            @Override
            public void onNext(final ResNewsDetailBean resNewsDetailBean) {

                mActivity.updateUI(resNewsDetailBean);

                getNavigationData();

            }
        }, paramsMap, mActivity);

        HttpManager.getInstance().doHttpDeal(mApiNewsDetail);
    }


    int mPage=1;

    /**
     * 获取评论的列表；
     */
    public void getCommentList(){

        //获取评论
        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put(ApiNewsCommentList.ISSUE_ID, mMd5Url);
        paramsMap.put(ApiNewsCommentList.PAGE_NUMBER, mPage + "");

        ApiNewsCommentList apiNewsCommentList = new ApiNewsCommentList(new HttpOnNextListener<ResNewsCommentList>() {

            @Override
            public void onNext(ResNewsCommentList resNewsCommentList) {

                if (resNewsCommentList.getList() != null) {
                    //这里我们要做的一个逻辑就是
                    mActivity.updateCommentList(resNewsCommentList.getList(),mPage==1);

                    if (resNewsCommentList.getList().size() > 0) {
                        mPage++;
                    }
                } else {
                    if(mPage!=1){
                        //这里表示部位第一
                       // mActivity.toast("没有更多数据");
                    }
                    //表示数据为空的操作;
                    mActivity.updateCommentList(new ArrayList<NewsCommentBean>(),mPage==1);
                }
                mActivity.loadMoreCompelte();
            }
        }, mActivity, paramsMap);
        HttpManager.getInstance().doHttpDeal(apiNewsCommentList);
    }

    /**
     * 这里我们要做的一个操作就是获取推荐信息；
     */
    public void getRecomment() {

        // 先请求穿山甲广告, 然后再请求推荐数据
        loadListAd();
        //这里我们要做第一个操作就是拼接内容；
//        getData();
    }

    List<TTFeedAd> mData;

    /**
     * 加载feed广告
     */
    private void loadListAd() {
        mData = new ArrayList<>();
        TTAdManager ttAdManager = TTAdManagerHolder.getInstance(mActivity);
        TTAdNative mTTAdNative = ttAdManager.createAdNative(mActivity);
        TTAdManagerHolder.getInstance(mActivity).requestPermissionIfNecessary(mActivity);

        //feed广告请求类型参数
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("902510857")
                .setSupportDeepLink(true)
                .setImageAcceptedSize(640, 320)
                .setAdCount(1)
                .build();
        //调用feed广告异步请求接口
        mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
            @Override
            public void onError(int code, String message) {
//                getAdhubAd();
                getData();
            }
            @Override
            public void onFeedAdLoad(List<TTFeedAd> ads) {
                Log.i("info" , "ads==="+ads.size());
                if(ads.size()>0) {
                    mData.clear();
                    mData.addAll(ads);
                }
//                getAdhubAd();
                getData();
            }
        });
    }

    private void getAdhubAd() {
        mNativeAd = new NativeAd(mActivity,ConstantAd.ADHUBAD.NATIVE_ID, 1, new NativeAdListener() {
            @Override
            public void onAdLoaded(NativeAdResponse nativeAdResponse) {
                mAdhubNativeData.add(nativeAdResponse);
                getData();
            }

            @Override
            public void onAdFailed(int i) {
                LogUtil.e("info","adhub load add fail "+i);
//                throw new IllegalStateException("adhub load add fail "+i);
                getData();
            }
        });
        mNativeAd.loadAd();
    }


    public void getData(){

        HashMap<String, String> paramsMap = new HashMap<>();

//        paramsMap.put("type","xiaohua");
        paramsMap.put("type",mNewsType);
        paramsMap.put("qid", ConstantThreePart.ADMAMA_ID);
        paramsMap.put(ApiHomeNews.PAGE_NUM, mPageNum+"");

        ApiHomeNews apiHomeNews = new ApiHomeNews(mActivity, paramsMap , new HttpOnNextListener<ResTabBean>() {
            @Override
            public void onNext(ResTabBean resTabBean) {
                //这里表示获取得到了返回的内容；
                List<NewsItemBean> list = resTabBean.getList();
                //这里我们要取出来两条；
                if (list!=null ) {
                    ArrayList<NewsItemBean> multiItemEntities = new ArrayList<>();

                    //以下是为adhub单独写的
                    /*for(int i = 0; i < list.size(); i++) {
                        if(i >= 5) break;
                        NewsItemBean item = list.get(i);
                        if("qmttad".equals(item.getQmttcontenttype())) {
                            NativeAdResponse adResponse = mAdhubNativeData.removeFirst();
                            if(adResponse != null) {
                                NewsItemBean bean = new NewsItemBean("qmttad", "adhub");
                                bean.setNativeAdResponse(adResponse);
//                            bean.setAdResponse(adResponse);
                                multiItemEntities.add(bean);
                                if (mAdhubNativeData.size() < 2) {
                                    loadAdhubAd();
                                    loadAdhubAd();
                                }
                            } else {
                                loadAdhubAd();
                            }
                        } else {
                            multiItemEntities.add(item);
                        }
                    }*/

                    if(list.size()>5){
                        //这里我们要做的一个操作就是；
                        ResSplashAds resAds = mActivity.getMyApplication().getResAds();
                        ArrayList<AdBean> newdetail = resAds.getNewdetail();

                        NewsItemBean newsItemBean1 = new NewsItemBean("qmttad", "ta");
                        //拼接广告的操作；
                        if (newdetail!=null && newdetail.size()>0) {
                            //这里做一个适配的操作；
                            AdBean adBean = newdetail.get(0);

                            newsItemBean1.setQmttcontenttype(NewsItemBean.AD);
//                            newsItemBean1.setType(adBean.getType());
                            newsItemBean1.setType("gdt");

                            newsItemBean1.setId(adBean.getId());
                            newsItemBean1.setTitle(adBean.getTitle());
                            newsItemBean1.setCont(adBean.getCont());
                            newsItemBean1.setImgurl(adBean.getImgurl());
                            newsItemBean1.setUrl(adBean.getUrl());
                            newsItemBean1.setDownurl(adBean.getDownurl());
                            newsItemBean1.setSize(adBean.getSize());
                            newsItemBean1.setPackename(adBean.getPackageName());
                            newsItemBean1.setAppname(adBean.getAppname());
                            newsItemBean1.setImgurls(adBean.getImgurls());
//                            if("csj".equals(adBean.getType())) {
//                                if(mData.size()>0) {
//                                    newsItemBean1.setTtFeedAd(mData.get(0));
//                                } else {
//                                    newsItemBean1.setType("gdt");
//                                }
//                            }
                        }
//                        NewsItemBean newsItemBean2 = new NewsItemBean("qmtt_lt", "gdt");
                        NewsItemBean newsItemBean2 = new NewsItemBean("qmttad", "gdt");
//                        NewsItemBean newsItemBean3 = new NewsItemBean("qmttad", "baidu");
                        NewsItemBean newsItemBean3 = new NewsItemBean("qmttad", "gdt");
//                        NewsItemBean newsItemBean4 = new NewsItemBean("qmttad", "ta");
                        if (mActivity.isHasAds()) {

                            multiItemEntities.add(newsItemBean1);
                            if(!"csj".equals(list.get(0).getType())) {
                                multiItemEntities.add(list.get(0));
                            } else {
                                multiItemEntities.add(new NewsItemBean("qmttad", "gdt"));
                            }

                            if(!"csj".equals(list.get(1).getType())) {
                                multiItemEntities.add(list.get(1));
                            } else {
                                multiItemEntities.add(new NewsItemBean("qmttad", "gdt"));
                            }
                            multiItemEntities.add(newsItemBean2);
                            if(!"csj".equals(list.get(2).getType())) {
                                multiItemEntities.add(list.get(2));
                            } else {
                                multiItemEntities.add(new NewsItemBean("qmttad", "gdt"));
                            }
                            if(!"csj".equals(list.get(3).getType())) {
                                multiItemEntities.add(list.get(3));
                            } else {
                                multiItemEntities.add(new NewsItemBean("qmttad", "gdt"));
                            }
                            multiItemEntities.add(newsItemBean3);
                            //multiItemEntities.add(newsItemBean4);
//                            multiItemEntities.add(newsItemBean4);
                        }else{
                            multiItemEntities.add(list.get(0));
                            multiItemEntities.add(list.get(1));
                            multiItemEntities.add(list.get(2));
                            multiItemEntities.add(list.get(3));
                            multiItemEntities.add(list.get(4));
                        }


                    } else{
                        //这里表示小于2的逻辑
                        if(mActivity.isHasAds()){
//                            NewsItemBean newsItemBean1 = new NewsItemBean("qmttad", "ta");
                            NewsItemBean newsItemBean1 = new NewsItemBean("qmttad", "gdt");
                            NewsItemBean newsItemBean2 = new NewsItemBean("qmttad", "gdt");
//                            NewsItemBean newsItemBean3 = new NewsItemBean("qmttad", "baidu");
                            NewsItemBean newsItemBean3 = new NewsItemBean("qmttad", "gdt");
                            multiItemEntities.add(newsItemBean1);
                            multiItemEntities.addAll(list);
                            multiItemEntities.add(newsItemBean2);
                            multiItemEntities.add(newsItemBean3);
                        }else{
                            multiItemEntities.addAll(list);
                        }
                    }
                    mActivity.updateRecomment(multiItemEntities);
                }
            }
        });

        HttpManager.getInstance().doHttpDeal(apiHomeNews);
    }

    /**
     * 执行点赞的逻辑；
     * @param uid 用户的id
     * @param commentId 评论的id
     */
    public void onClickDoLike(String uid,String commentId){

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiUserDoLike.FROM_UID,uid);
        paramsMap.put(ApiUserDoLike.COMMENT_ID,commentId);

        //这里表示
        mApiUserDoLike = new ApiUserDoLike(new HttpOnNextListener<ResEmpty>() {
            @Override
            public void onNext(ResEmpty resEmpty) {

                mActivity.toast(mApiUserDoLike.getMsg());
                //这里表示
                mPage=1;
                getCommentList();
            }
        },mActivity,paramsMap);

        HttpManager.getInstance().doHttpDeal(mApiUserDoLike);
    }


    //四个分享的接口的逻辑;

    public void getNewsWechatShareContent(){

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiShareNewsAndVideoContent.TYPE, ApiShareNewsAndVideoContent.NEWS_TYPE);
        paramsMap.put(ApiShareNewsAndVideoContent.SHARE_TYPE,ApiShareNewsAndVideoContent.WECHAT_FRIEND);
        paramsMap.put(ApiShareNewsAndVideoContent.FROM_UID,mActivity.getUserBean().getUserid());
        paramsMap.put(ApiShareNewsAndVideoContent.URLMD5,mMd5Url);

        ApiShareNewsAndVideoContent apiShareNewsAndVideoContent=new ApiShareNewsAndVideoContent(new HttpOnNextListener<ResRequestShare>() {

            @Override
            public void onNext(ResRequestShare resRequestShare) {
               // mActivity.shareContentToWechatFriend(resRequestShare.getTitle(),resRequestShare.getDesc(),resRequestShare.getUrl());
            }

        },mActivity,paramsMap);

        HttpManager.getInstance().doHttpDeal(apiShareNewsAndVideoContent);

    }

    public void getNewsWechatCommentShareContent(){

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiShareNewsAndVideoContent.TYPE, ApiShareNewsAndVideoContent.NEWS_TYPE);
        paramsMap.put(ApiShareNewsAndVideoContent.SHARE_TYPE,ApiShareNewsAndVideoContent.WECHAT_FRIEND_COMMENT);
        paramsMap.put(ApiShareNewsAndVideoContent.FROM_UID,mActivity.getUserBean().getUserid());
        paramsMap.put(ApiShareNewsAndVideoContent.URLMD5,mMd5Url);

        ApiShareNewsAndVideoContent apiShareNewsAndVideoContent=new ApiShareNewsAndVideoContent(new HttpOnNextListener<ResRequestShare>() {


            @Override
            public void onNext(ResRequestShare resRequestShare) {

               // mActivity.shareContentToWechatComment(resRequestShare.getTitle(),resRequestShare.getDesc(),resRequestShare.getUrl());
            }

        },mActivity,paramsMap);

        HttpManager.getInstance().doHttpDeal(apiShareNewsAndVideoContent);
    }



    /**
     * 获取Navigation里面的内容；
     */
    public void getNavigationData() {


        ApiInviteFriendDesc apiInviteFriendDesc = new ApiInviteFriendDesc(new HttpOnNextListener<ResInviteFriendDesc>() {

            @Override
            public void onNext(final ResInviteFriendDesc resInviteFriendDesc) {

                mActivity.initRightBottomToast(resInviteFriendDesc);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, mActivity, new HashMap<String, String>());

        HttpManager.getInstance().doHttpDeal(apiInviteFriendDesc);
    }


  /*  //这里要做的一个操作就是显示对应的
                if ("1".equals(resInviteFriendDesc.getShowTextForLogin())) {
        //这里要做的一个逻辑就是显示title
        mLlNavigation.setVisibility(View.VISIBLE);
        //这里要做的另一个逻辑就是；
        if(!mActivity.isLogin()){

            mLlNavigationNoLogin.setVisibility(View.VISIBLE);
            mTvNavigationText.setVisibility(View.GONE);

            mLlNavigationNoLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //去登陆的逻辑
                    Intent intent = new Intent(getBaseActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });

        }else{
            mLlNavigationNoLogin.setVisibility(View.GONE);
            mTvNavigationText.setVisibility(View.VISIBLE);
            mTvNavigationText.setText(resInviteFriendDesc.getTextforlogin().getTitle());
            mTvNavigationText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = WebHelperActivity.getIntent(getBaseActivity(), resInviteFriendDesc.getTextforlogin().getUrl(), "", false);
                    startActivity(intent);
                }
            });
        }

    }else{
        //这里让其隐藏；
        mLlNavigation.setVisibility(View.GONE);
    }*/

}
