package com.huanxi.renrentoutiao.ui.activity.video;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.api.user.ApiUserShare;
import com.huanxi.renrentoutiao.net.api.user.collection.ApiUserCollection;
import com.huanxi.renrentoutiao.net.api.user.comment.ApiSendComment;
import com.huanxi.renrentoutiao.net.api.video.ApiVedioDetail;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.net.bean.ResRequestShare;
import com.huanxi.renrentoutiao.net.bean.video.ResVedioSource;
import com.huanxi.renrentoutiao.presenter.ads.gdt.GDTImgAds;
import com.huanxi.renrentoutiao.presenter.ads.gdt.GdtNativeAds;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.adapter.bean.VideoBean;
import com.huanxi.renrentoutiao.ui.dialog.CommentDialog;
import com.huanxi.renrentoutiao.ui.dialog.ShareDialog;
import com.huanxi.renrentoutiao.ui.fragment.video.VideoDetailFragment;
import com.huanxi.renrentoutiao.ui.view.MyVideoPlayer;
import com.huanxi.renrentoutiao.utils.ImageUtils;
import com.jaeger.library.StatusBarUtil;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;
import com.zhxu.library.utils.SystemUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * Created by Dinosa on 2018/1/25.
 */

public class VideoItemDetailActivityOld extends BaseActivity {

    @BindView(R.id.videoplayer)
    MyVideoPlayer mVideoplayer;

    @BindView(R.id.tv_tip)
    TextView mTvTip;

    @BindView(R.id.skip_view)
    TextView mTvSkipView;

    @BindView(R.id.fl_ad_container)
    FrameLayout mAdContainer;

    @BindView(R.id.fl_ads)
    FrameLayout mFlAds;

    private VideoBean mVideoBean;
    //private VideoItemDetailFragment mCommentsFragment;
    private VideoDetailFragment mCommentsFragment;


    public VideoDetailFragment getVideoDetailFragment(){
        return mCommentsFragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_video_item_deatil;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {

        StatusBarUtil.setColor(this, Color.BLACK);

        mVideoBean = ((VideoBean) getIntent().getSerializableExtra(VIDEO_BEAN));


        VideoDetail videoDetail = new VideoDetail(
                mVideoBean.getContent().getGroup_id()+"",
                mVideoBean.getContent().getItem_id()+"",
                mVideoBean.getContent().getTitle(),
                "video",mVideoBean.getUrlmd5()
                );

        //mCommentsFragment = VideoDetailFragment.getFragment(videoDetail);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_comment,mCommentsFragment)
                .commitAllowingStateLoss();
    }

    @Override
    protected void initData() {
        super.initData();

        mFlAds.setVisibility(View.VISIBLE);
        mVideoplayer.setVisibility(View.INVISIBLE);

        initPlayer();
        //这里我们请求数据
        if(isHasAds()){
            dispalyGDTAds();
        }else{
            startPlay();
        }
/*     mVideoplayer.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子闭眼睛");*/
        //mVideoplayer.thumbImageView.setImageURI(Uri.parse(mVideoBean.getCont().getLarge_image_list().get(0).getUrl()));

   }

    NativeExpressADView mImgAdView;

    GDTImgAds mGDTImgAds;

    /**
     * 这里我们展示一下广点通的广告；
     */
    private void dispalyGDTAds() {

/*
        GdtNativeAds gdtNativeAds = GdtNativeAds.newInstance();
        mImgAdView = gdtNativeAds.getImgAdView();

        if (mImgAdView != null) {
            mImgAdView.render();
            mAdContainer.addView(mImgAdView);
            startCountDown(6);
        }else{
            startPlay();
        }
*/


        mGDTImgAds = new GDTImgAds(null, 1, new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onNoAD(AdError adError) {

                System.out.println("errorCode: "+adError.getErrorCode()+" msg: "+adError.getErrorMsg());
                startPlay();
            }

            @Override
            public void onADLoaded(List<NativeExpressADView> list) {
                //这里个操作就是获取广澳；
                if (list != null && list.size()>0) {
                    NativeExpressADView nativeExpressADView = list.get(0);
                    nativeExpressADView.render();
                    mAdContainer.addView(nativeExpressADView);
                }
            }

            @Override
            public void onRenderFail(NativeExpressADView nativeExpressADView) {


                System.out.println("errorCode: "+"渲染失败");
                startPlay();
            }

            @Override
            public void onRenderSuccess(NativeExpressADView nativeExpressADView) {
                mTvSkipView.setVisibility(View.VISIBLE);
                startCountDown(4);
            }

            @Override
            public void onADExposure(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

            }
        });

    }

    CountDownTimer mCountDownTimer;

    /**
     * 开始一个广告的倒计时的操作
     */
    private void startCountDown(int totalTime) {
        int timeUnit=1000;
        totalTime=totalTime*1000;
        //去做一个更新的操作；
        mCountDownTimer = new CountDownTimer(totalTime,timeUnit) {
            @Override
            public void onTick(long millisUntilFinished) {
                //去做一个更新的操作；
                mTvSkipView.setText("跳过 "+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                //这里我们将修改状态；变成可领取的状态；
                startPlay();
            }
        };
        mCountDownTimer.start();
    }

    @OnClick(R.id.skip_view)
    public void onClickSkip(){
        //停止倒计时；
        mCountDownTimer.cancel();
        startPlay();
    }

    private void startPlay() {

        mVideoplayer.setVisibility(View.VISIBLE);
        if (mFlAds != null) {
            mFlAds.setVisibility(View.INVISIBLE);

            if (mFlAds.getParent() != null) {
                //移除广告容器；
                ((ViewGroup) mFlAds.getParent()).removeView(mFlAds);
            }
        }
        //回收广告；
        GdtNativeAds gdtNativeAds = GdtNativeAds.newInstance();
        if (gdtNativeAds != null) {
            gdtNativeAds.releaseImgAdView(mImgAdView);
        }
        //开始播放的操作；
        getVideoSourceData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        if (mImgAdView != null) {
            GdtNativeAds.newInstance().releaseImgAdView(mImgAdView);
        }

        JZVideoPlayer.releaseAllVideos();
    }

    /**
     * 这里是初始化播放器的一个操作；
     */
    private void initPlayer() {
        mVideoplayer.backButton.setVisibility(View.VISIBLE);
        mVideoplayer.mRetryLayout.setVisibility(View.INVISIBLE);
        mVideoplayer.thumbImageView.setBackgroundColor(Color.GRAY);
        mVideoplayer.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageUtils.loadImage(this,mVideoBean.getContent().getLarge_image_list().get(0).getUrl(),mVideoplayer.thumbImageView);
    }

    //这里我们是请求我们自己的vedio的url
    private void getVideoSourceData() {

        UserBean userBean = getUserBean();
        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put("category","video");
        paramsMap.put("urlmd5",mVideoBean.getUrlmd5());
        if (userBean != null) {
            paramsMap.put("from_uid",userBean.getUserid());
        }
        paramsMap.put("category","video");
        paramsMap.put("video_id",mVideoBean.getContent().getVideo_id());
        paramsMap.put("item_id",mVideoBean.getContent().getItem_id()+"");


        paramsMap.put("device_id", SystemUtils.getIMEI(this));
        paramsMap.put("device_platform","android");
        paramsMap.put("device_type",SystemUtils.getSystemModel());
        paramsMap.put("device_brand",SystemUtils.getDeviceBrand());
        paramsMap.put("os_api", SystemUtils.getSystemApi());
        paramsMap.put("os_version",SystemUtils.getSystemVersion());
        paramsMap.put("uuid",SystemUtils.getIMEI(this));
        paramsMap.put("openudid",SystemUtils.getOpenUid(this));
        paramsMap.put("resolution",SystemUtils.getResolution(this));
        paramsMap.put("dpi",SystemUtils.getDensity(this));

        ApiVedioDetail apiVedioDetail = new ApiVedioDetail(new HttpOnNextListener<ResVedioSource>() {

            @Override
            public void onNext(ResVedioSource resVedioSource) {
                //这里我们要做的一个操作；
                mVideoplayer.setUp(resVedioSource.getUrl().getData().getVideo_list().getVideo_1().getMain_url()
                        , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
                mVideoplayer.backButton.setVisibility(View.VISIBLE);
                mCbCollection.setChecked(resVedioSource.isIscollect());

                mVideoplayer.play();
            }
        },paramsMap,this);

        HttpManager.getInstance().doHttpDeal(apiVedioDetail);
    }


    public static String VIDEO_BEAN = "videoBean";

    /**
     * 这里我们是传入的对应的东西的
     *
     * @return
     */
    public static Intent getIntent(Context context, VideoBean bean) {
        Intent intent = new Intent(context, VideoItemDetailActivityOld.class);
        intent.putExtra(VIDEO_BEAN, bean);
        return intent;
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    //这里是点击底部的按钮实现的；
    @OnClick(R.id.iv_back)
    public void onClickBottomBack(){
        finish();
    }

    @OnClick(R.id.et_conmment)
    public void onClickInputComment() {

        checkLogin(new CheckLoginCallBack() {
            @Override
            public void loginSuccess() {

                CommentDialog commentDialog = new CommentDialog();
                commentDialog.show(VideoItemDetailActivityOld.this, new CommentDialog.OnDialogClickListener() {
                    @Override
                    public void onClickSure(String content) {

                        UserBean userBean = getUserBean();

                        HashMap<String, String> paramsMap = new HashMap<>();
                        paramsMap.put(ApiSendComment.ISSUE_ID, mVideoBean.getUrlmd5());
                        paramsMap.put(ApiSendComment.FROM_UID, userBean.getUserid());
                        paramsMap.put(ApiSendComment.COMMENT_CONTENT, content);

                        //这里做的一个操作就是发送评论；
                        ApiSendComment apiSendComment = new ApiSendComment(new HttpOnNextListener<ResEmpty>() {
                            @Override
                            public void onNext(ResEmpty o) {
                                toast("评论成功");
                                //这里我们要做一个操作；
                                mCommentsFragment.requestAdapterData(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                toast("评论失败");
                            }
                        }, VideoItemDetailActivityOld.this, paramsMap);
                        HttpManager.getInstance().doHttpDeal(apiSendComment);

                    }
                });
            }

            @Override
            public void loginFaild() {

            }
        });
    }


    @OnClick(R.id.iv_message)
    public void onClickSend() {
        //点击消息的部分,这里是需要滚动的
    }

    @BindView(R.id.cb_collection)
    CheckBox mCbCollection;

    @OnClick(R.id.fl_collection)
    public void onClickCollection(){
        checkLogin(new CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                //这里我们要进行一个收藏操作；
                mCbCollection.toggle();

                if(mCbCollection.isChecked()){
                    toast("收藏成功");
                }else{
                    toast("取消收藏");
                }
                UserBean userBean = getUserBean();
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put(ApiUserCollection.FROM_UID,userBean.getUserid());
                paramsMap.put(ApiUserCollection.URLMD5,mVideoBean.getUrlmd5());

                ApiUserCollection apiUserCollection=new ApiUserCollection(new HttpOnNextListener<ResEmpty>() {

                    @Override
                    public void onNext(ResEmpty resEmpty) {
                        //这里要做的一个操作就是成功；
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                },VideoItemDetailActivityOld.this,paramsMap);

                HttpManager.getInstance().doHttpDeal(apiUserCollection);
            }

            @Override
            public void loginFaild() {

            }
        });
    }

    /**
     * 这里是点击分享的操作；
     */
    @OnClick(R.id.iv_share)
    public void onClickShare(){
        //这里是点击分享的操作；
        //这里我们将使用
        checkLogin(new CheckLoginCallBack() {
            @Override
            public void loginSuccess() {
                HashMap<String, String> paramsMap = new HashMap<>();

                paramsMap.put(ApiUserShare.FROM_UID,getUserBean().getUserid());
                paramsMap.put(ApiUserShare.TYPE,ApiUserShare.TYPE_VIDEO);
                paramsMap.put(ApiUserShare.URLMD5,mVideoBean.getUrlmd5());

                ApiUserShare apiUserShare=new ApiUserShare(new HttpOnNextListener<ResRequestShare>(){

                    @Override
                    public void onNext(ResRequestShare resRequestShare) {
                        showShareDialog(resRequestShare.getTitle(),resRequestShare.getDesc(),resRequestShare.getUrl());
                    }
                },VideoItemDetailActivityOld.this,paramsMap);
                HttpManager.getInstance().doHttpDeal(apiUserShare);
            }

            @Override
            public void loginFaild() {

            }
        });
    }

    public void showShareDialog(String title,String content,String url){
        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.show(title, content, url, new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
    }


    /**
     * 获取提示的View;
     * @return
     */
    public TextView getTipView(){
        return mTvTip;
    }

    //这里我们点击将值设置给Fragment;

    public static final String VIDEO_DETAIL="videoDetail";

    public static Intent getIntent(Context context,VideoDetail videoDetail){
        Intent intent = new Intent(context, VideoItemDetailActivityOld.class);
        intent.putExtra(VIDEO_DETAIL, videoDetail);
        return intent;
    }

    public static class VideoDetail implements Serializable {
        String groupId;
        String itemId;
        String title;
        String video;
        String urlMd5;

        public String getUrlMd5() {
            return urlMd5;
        }

        public void setUrlMd5(String urlMd5) {
            this.urlMd5 = urlMd5;
        }

        public VideoDetail(String groupId, String itemId, String title, String video,String urlMd5) {
            this.groupId = groupId;
            this.itemId = itemId;
            this.title = title;
            this.video = video;
            this.urlMd5=urlMd5;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }
    }

}
