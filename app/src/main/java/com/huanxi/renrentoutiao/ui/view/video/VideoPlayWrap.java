package com.huanxi.renrentoutiao.ui.view.video;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import com.huanxi.renrentoutiao.ui.activity.video.VideoPlayActivity;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTDrawFeedAd;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.huanxi.renrentoutiao.AppConfig;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.l_video.MusicBean;
import com.huanxi.renrentoutiao.model.bean.l_video.UserBean;
import com.huanxi.renrentoutiao.model.bean.l_video.VideoBean;
import com.huanxi.renrentoutiao.net.http.HttpCallback;
import com.huanxi.renrentoutiao.net.http.HttpUtil;
import com.huanxi.renrentoutiao.ui.view.CompletedView;
import com.huanxi.renrentoutiao.ui.view.MusicAnimLayout;
import com.huanxi.renrentoutiao.utils.FrameAnimUtil;
import com.huanxi.renrentoutiao.utils.ImgLoader;
import com.huanxi.renrentoutiao.utils.ScreenDimenUtil;
import com.huanxi.renrentoutiao.utils.TTAdManagerHolder;
import com.huanxi.renrentoutiao.utils.WordUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxf on 2018/6/5.
 */

public class VideoPlayWrap extends FrameLayout implements View.OnClickListener {

    private String mTag;
    private Context mContext;
    private VideoBean mVideoBean;
    private FrameLayout mContainer;
    private View mCover;
    private ImageView mCoverImg;
    private VideoPlayView mPlayView;
    private View adView;
    private TTDrawFeedAd cachedAd;
    private TTDrawFeedAd curAd;
    private int mScreenWidth;
    private ImageView mAvatar;//头像
    private ImageView mBtnFollow;//关注按钮
    private FrameAnimImageView mBtnZan;//点赞按钮
    private TextView mZanNum;//点赞数
    private TextView mCommentNum;//评论数
    private TextView mShareNum;//分享数
    private TextView mTitle;//标题
    private TextView mName;//昵称
    private TextView mMusicTitle;//音乐标题
    private MusicAnimLayout mMusicAnimLayout;
    private  LinearLayout mRight;
    private LinearLayout musicGroup;
    private Button mBtnAD;
    private boolean mUsing;//是否在使用中
    private ActionListener mActionListener;
    private String mMusicSuffix;
    private static final String SPACE = "            ";
    private static int sFollowAnimHashCode;
    private ValueAnimator mFollowAnimator;

    private TTAdNative mTTAdNative; // 网盟广告


    public VideoPlayWrap(Context context) {
        this(context, null);
    }

    public VideoPlayWrap(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoPlayWrap(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTag = String.valueOf(this.hashCode()) + HttpUtil.GET_VIDEO_INFO;
        mContext = context;
        mScreenWidth = ScreenDimenUtil.getInstance().getScreenWdith();
        TTAdManager ttAdManager = TTAdManagerHolder.getInstance(mContext);
        mTTAdNative = ttAdManager.createAdNative(mContext);
        cachedAd = null;
        loadListAd();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_video_wrap, this, false);
        addView(view);

        mContainer = (FrameLayout) view.findViewById(R.id.container);
        mCover = view.findViewById(R.id.cover);
        mCoverImg = (ImageView) view.findViewById(R.id.coverImg);
        mAvatar = (ImageView) view.findViewById(R.id.avatar);
        mBtnFollow = (ImageView) view.findViewById(R.id.btn_follow);
        mBtnZan = (FrameAnimImageView) view.findViewById(R.id.btn_zan);
        mZanNum = (TextView) view.findViewById(R.id.zan);
        mCommentNum = (TextView) view.findViewById(R.id.comment);
        mShareNum = (TextView) view.findViewById(R.id.share);
        mTitle = (TextView) view.findViewById(R.id.title);
        mName = (TextView) view.findViewById(R.id.name);
        mMusicTitle = (TextView) view.findViewById(R.id.music_title);
        mMusicAnimLayout = (MusicAnimLayout) view.findViewById(R.id.music_anim);
        mRight = (LinearLayout)view.findViewById(R.id.right_sord);
        musicGroup = (LinearLayout)view.findViewById(R.id.music_title_group);
        mBtnAD = null;

        mAvatar.setOnClickListener(this);
        mBtnFollow.setOnClickListener(this);
        view.findViewById(R.id.btn_zan).setOnClickListener(this);
        view.findViewById(R.id.btn_comment).setOnClickListener(this);
        view.findViewById(R.id.btn_share).setOnClickListener(this);
        mMusicSuffix = WordUtil.getString(R.string.music_suffix);
        mFollowAnimator = ValueAnimator.ofFloat(1f, 1.4f, 0.2f);
        mFollowAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                mBtnFollow.setScaleX(v);
                mBtnFollow.setScaleY(v);
            }
        });
        mFollowAnimator.setDuration(1000);
        mFollowAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mFollowAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mBtnFollow.setVisibility(INVISIBLE);
            }
        });
    }

    /**
     * 加载数据
     */
    public void loadData(VideoBean bean) {
        mUsing = true;
        if (bean == null) {
            return;
        }
        mVideoBean = bean;
        ImgLoader.displayBitmap(bean.getThumb(), new ImgLoader.BitmapCallback() {
            @Override
            public void callback(Bitmap bitmap) {
                if (mCoverImg != null && mCover != null && mCover.getVisibility() == View.VISIBLE) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mCoverImg.getLayoutParams();
                    float width = bitmap.getWidth();
                    float height = bitmap.getHeight();
                    if (width >= height) {
                        params.height = (int) (mScreenWidth * height / width);
                    } else {
                        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    }
                    mCoverImg.setLayoutParams(params);
                    mCoverImg.requestLayout();
                    mCoverImg.setImageBitmap(bitmap);
                } else {
                    bitmap.recycle();
                }
            }
        });
        mZanNum.setText(bean.getLikes());
        mCommentNum.setText(bean.getComments());
        mShareNum.setText(bean.getShares());
        int isAttent = bean.getIsattent();
        if (isAttent == 1 || AppConfig.getInstance().getUid().equals(mVideoBean.getUid())) {
            if (mBtnFollow.getVisibility() == VISIBLE) {
                mBtnFollow.setVisibility(INVISIBLE);
            }
        } else {
            if (mBtnFollow.getVisibility() != VISIBLE) {
                mBtnFollow.setVisibility(VISIBLE);
            }
            mBtnFollow.setScaleX(1f);
            mBtnFollow.setScaleY(1f);
            mBtnFollow.setImageResource(R.mipmap.icon_video_unfollow);
        }
        int islike = bean.getIslike();
        if (islike == 1) {
            mBtnZan.setImageResource(R.mipmap.icon_video_zan_12);
        } else {
            mBtnZan.setImageResource(R.mipmap.icon_video_zan_01);
        }

        mTitle.setText(bean.getTitle());
        UserBean u = bean.getUserinfo();
        if (u != null) {
//            ImgLoader.display(u.getAvatar(), mAvatar);
//            mName.setText("@" + u.getUser_nicename());
            ImgLoader.display(bean.getRestaurant_name() , mAvatar);
            mName.setText("@" + bean.getTopic());
            if (mVideoBean.getMusic_id() != 0) {
                MusicBean musicBean = mVideoBean.getMusicinfo();
                if (musicBean != null) {
//                    mMusicAnimLayout.setImageUrl(musicBean.getImg_url());
                    mMusicAnimLayout.setImageUrl(bean.getRestaurant_name());
                    String title = musicBean.getTitle();
                    String tt = title.replace("已删除" , bean.getTopic());
                    mMusicTitle.setText(tt + SPACE + tt + SPACE + tt);
                }
            } else {
//                mMusicAnimLayout.setImageUrl(u.getAvatar());
//                String title = "@" + u.getUser_nicename() + mMusicSuffix;
                mMusicAnimLayout.setImageUrl(bean.getRestaurant_name());
                String title = "@" + bean.getTopic() + mMusicSuffix;
                mMusicTitle.setText(title + SPACE + title + SPACE + title);
            }
        }
        getVideoInfo();
    }

    /**
     * 暂停音乐播放的动画
     */
    public void pauseMusicAnim() {
//        if (mMusicAnimLayout != null) {
//            mMusicAnimLayout.pauseAnim();
//        }
    }

    /**
     * 恢复音乐播放的动画
     */
    public void startMusicAnim() {
        if (mMusicAnimLayout != null) {
            mMusicAnimLayout.startAnim();
        }
    }

    /**
     * 显示背景图
     */
    public void showBg() {
        if (mCover.getVisibility() != VISIBLE) {
            mCover.setVisibility(VISIBLE);
        }
    }

    /**
     * 隐藏背景图
     */
    public void hideBg() {
        if (mCover.getVisibility() == VISIBLE) {
            mCover.setVisibility(INVISIBLE);
        }
    }

    public void setUIVisible(int flag) {
        if (mRight.getVisibility() != flag) {
            mRight.setVisibility(flag);
        }
        if (musicGroup.getVisibility() != flag) {
            musicGroup.setVisibility(flag);
        }
        if (mMusicAnimLayout.getVisibility() != flag) {
            mMusicAnimLayout.setVisibility(flag);
        }
        if (mCommentNum.getVisibility() != flag) {
            mCommentNum.setVisibility(flag);
        }
        ((VideoPlayActivity) mContext).mVideoPlayFragment.setComentVisible(flag);
    }

    public void removePlayView() {
        if (mContainer.getChildCount() > 0) {
            if (mPlayView != null) {
                mContainer.removeView(mPlayView);
                mPlayView = null;
            }
            if (adView != null){
                mContainer.removeView(adView);
                adView = null;
                cachedAd = null;
                loadListAd();
                setUIVisible(VISIBLE);
            }
            if (mBtnAD != null){
                mContainer.removeView(mBtnAD);
                mBtnAD = null;
            }
        }
        showBg();
        if (mMusicAnimLayout != null) {
            mMusicAnimLayout.cancelAnim();
        }
    }

    public void addPlayView(VideoPlayView playView, boolean isAD) {
        if(isAD && addAdView()){
            return;
        }
        mPlayView = playView;
        playView.setPlayWrap(this);
        ViewGroup parent = (ViewGroup) playView.getParent();
        if (parent != null) {
            parent.removeView(playView);
        }
        mContainer.addView(playView);
    }

    public boolean addAdView(){
        if (cachedAd == null){
            loadListAd();
            return false;
        }
        hideBg();
        setUIVisible(INVISIBLE);
        initAdViewAndAction(cachedAd);
        adView = cachedAd.getAdView();
        mTitle.setText(cachedAd.getDescription());
        mName.setText(cachedAd.getTitle());
        ViewGroup parent = (ViewGroup) adView.getParent();
        if (parent != null) {
            parent.removeView(adView);
        }
        mContainer.addView(adView);
        return true;
    }

    public void loadListAd() {
        //这里初始化广点通广告的逻辑
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("926821115") //开发者申请的广告位
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920) //符合广告场景的广告尺寸
                .setAdCount(1) //请求广告数量为1到3条
                .build();
        //加载广告
        mTTAdNative.loadDrawFeedAd(adSlot, new TTAdNative.DrawFeedAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.d("error", message);
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawFeedAdLoad(List<TTDrawFeedAd> ads) {
               // Toast.makeText(mContext, "有广告", Toast.LENGTH_SHORT).show();
                if (ads == null || ads.isEmpty()) {
                    return;
                }
                cachedAd = ads.get(0);

            }
        });
    }
    private void initAdViewAndAction(TTDrawFeedAd ad){
        ad.setActivityForDownloadApp((Activity) mContext);
        Button action = new Button(mContext);
        action.setText(ad.getButtonText());
        mBtnAD = new Button(mContext);
        mBtnAD.setText(ad.getButtonText());

        //其他代码略

        //响应点击区域的设置，分为普通的区域clickViews和创意区域creativeViews
        //clickViews中的view被点击会尝试打开广告落地页；creativeViews中的view被点击会根据广告类型
        //响应对应行为，如下载类广告直接下载，打开落地页类广告直接打开落地页。
        //注意：ad.getAdView()获取的view请勿放入这两个区域中。
        List<View> clickViews = new ArrayList<>();
        clickViews.add(mBtnAD);
        List<View> creativeViews = new ArrayList<>();
        creativeViews.add(action);

        int height = (int) dip2Px(mContext, 50);
        int margin = (int) dip2Px(mContext, 10);
        //noinspection SuspiciousNameCombination
//        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(height * 3, height);
//        lp.gravity = Gravity.END | Gravity.BOTTOM;
//        lp.rightMargin = margin;
//        lp.bottomMargin = margin;
//        mContainer.addView(action, lp);

        FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(height * 3, height);
        lp1.gravity = Gravity.START | Gravity.BOTTOM;
        lp1.rightMargin = margin;
        lp1.bottomMargin = margin;
        mContainer.addView(mBtnAD, lp1);

        ad.registerViewForInteraction(mContainer, clickViews, creativeViews, new TTNativeAd.AdInteractionListener(){
            @Override
            public void onAdClicked(View view, TTNativeAd ad) {
                Toast.makeText(mContext, "onAdClicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdCreativeClick(View view, TTNativeAd ad) {
                Toast.makeText(mContext, "onAdCreativeClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdShow(TTNativeAd ad) {
             //   Toast.makeText(mContext, "onAdShow", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private float dip2Px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dipValue * scale + 0.5f;
    }

    public void play() {
        if (mPlayView != null) {
            mPlayView.play(mVideoBean.getHref());
        }
    }

    public void clearData() {
        mUsing = false;
        HttpUtil.cancel(mTag);
        if (mCoverImg != null) {
            mCoverImg.setImageDrawable(null);
        }
        if (mAvatar != null) {
            mAvatar.setImageDrawable(null);
        }
    }

    /**
     * 获取单个视频信息，主要是该视频关于自己的信息 ，如是否关注，是否点赞等
     */
    public void getVideoInfo() {
        if (mUsing && mVideoBean != null) {
            HttpUtil.getVideoInfo(mVideoBean.getId(), mTag, mGetVideoInfoCallback);
        }
    }

    private HttpCallback mGetVideoInfoCallback = new HttpCallback() {
        @Override
        public void onSuccess(int code, String msg, String[] info) {
            if (code == 0 && info.length > 0) {
                JSONObject obj = JSON.parseObject(info[0]);
                String likes = obj.getString("likes");
                String comments = obj.getString("comments");
                String shares = obj.getString("shares");
                int isattent = obj.getIntValue("isattent");
                int islike = obj.getIntValue("islike");
                if (mVideoBean != null) {
                    mVideoBean.setLikes(likes);
                    mVideoBean.setComments(comments);
                    mVideoBean.setShares(shares);
                    mVideoBean.setIsattent(isattent);
                    mVideoBean.setIslike(islike);
                }

                if (mZanNum != null) {
                    mZanNum.setText(likes);
                }
                if (mCommentNum != null) {
                    mCommentNum.setText(comments);
                }
                if (mShareNum != null) {
                    mShareNum.setText(shares);
                }
                if (isattent == 1 || AppConfig.getInstance().getUid().equals(mVideoBean.getUid())) {
                    if (mBtnFollow.getVisibility() == VISIBLE) {
                        mBtnFollow.setVisibility(INVISIBLE);
                    }
                } else {
                    if (mBtnFollow.getVisibility() != VISIBLE) {
                        mBtnFollow.setVisibility(VISIBLE);
                    }
                    mBtnFollow.setScaleX(1f);
                    mBtnFollow.setScaleY(1f);
                    mBtnFollow.setImageResource(R.mipmap.icon_video_unfollow);
                }
                if (islike == 1) {
                    mBtnZan.setImageResource(R.mipmap.icon_video_zan_12);
                } else {
                    mBtnZan.setImageResource(R.mipmap.icon_video_zan_01);
                }
            }
        }
    };


    /**
     * 修改评论数
     *
     * @param comments
     */
    public void setCommentNum(String comments) {
        if (mVideoBean != null) {
            mVideoBean.setComments(comments);
        }
        if (mCommentNum != null) {
            mCommentNum.setText(comments);
        }
        EventBus.getDefault().post(new NeedRefreshEvent());
    }

    /**
     * 修改点赞数
     *
     * @param isLike 自己是否点赞
     * @param likes  点赞数
     */
    public void setLikes(int isLike, String likes) {
        if (mVideoBean != null) {
            mVideoBean.setIslike(isLike);
            mVideoBean.setLikes(likes);
        }
        if (isLike == 1) {
            mBtnZan.setSource(FrameAnimUtil.getVideoZanAnim())
                    .setFrameScaleType(FrameAnimImageView.FIT_WIDTH)
                    .setDuration(30).startAnim();
        } else {
            mBtnZan.setSource(FrameAnimUtil.getVideoCancelZanAnim())
                    .setFrameScaleType(FrameAnimImageView.FIT_WIDTH)
                    .setDuration(30).startAnim();
        }
        if (mZanNum != null) {
            mZanNum.setText(likes);
        }
        EventBus.getDefault().post(new NeedRefreshEvent());
    }

    /**
     * 修改分享数
     *
     * @param shares
     */
    public void setShares(String shares) {
        if (mVideoBean != null) {
            mVideoBean.setShares(shares);
        }
        if (mShareNum != null) {
            mShareNum.setText(shares);
        }
        EventBus.getDefault().post(new NeedRefreshEvent());
    }


    /**
     * 修改是否关注
     *
     * @param isAttent
     */
    public void setIsAttent(int isAttent) {
        if (mVideoBean != null && mBtnFollow != null) {
            mVideoBean.setIsattent(isAttent);
            if (isAttent == 1) {
                if (sFollowAnimHashCode == this.hashCode()) {
                    sFollowAnimHashCode = 0;
                    //执行关注动画
                    mBtnFollow.setImageResource(R.mipmap.icon_video_follow);
                    mFollowAnimator.start();
                } else {
                    if (mBtnFollow.getVisibility() == VISIBLE) {
                        mBtnFollow.setVisibility(INVISIBLE);
                    }
                }
            } else {
                if (mBtnFollow.getVisibility() != VISIBLE) {
                    mBtnFollow.setVisibility(VISIBLE);
                }
                mBtnFollow.setScaleX(1f);
                mBtnFollow.setScaleY(1f);
                mBtnFollow.setImageResource(R.mipmap.icon_video_unfollow);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (mActionListener != null && mVideoBean != null) {
            switch (v.getId()) {
                case R.id.btn_zan:
                    if (mVideoBean != null && mVideoBean.getIslike() == 0 && !mBtnZan.isAnimating()) {
                        mActionListener.onZanClick(this, mVideoBean);
                    }
                    break;
                case R.id.btn_comment:
                    mActionListener.onCommentClick(this, mVideoBean);
                    break;
                case R.id.btn_share:
                    mActionListener.onShareClick(this, mVideoBean);
                    break;
                case R.id.btn_follow:
                    sFollowAnimHashCode = this.hashCode();
                    mActionListener.onFollowClick(this, mVideoBean);
                    break;
                case R.id.avatar:
                    mActionListener.onAvatarClick(this, mVideoBean);
                    break;
            }
        }
    }

    public VideoBean getVideoBean() {
        return mVideoBean;
    }

    @Override
    protected void onDetachedFromWindow() {
        HttpUtil.cancel(mTag);
        super.onDetachedFromWindow();
    }

    public void release() {
        if (mAvatar != null) {
            mAvatar.setImageDrawable(null);
        }
        if (mCoverImg != null) {
            mCoverImg.setImageDrawable(null);
        }
        if (mBtnZan != null) {
            mBtnZan.release();
        }
        if (mFollowAnimator != null) {
            mFollowAnimator.cancel();
        }
    }


    public interface ActionListener {
        //点击点赞
        void onZanClick(VideoPlayWrap wrap, VideoBean bean);

        //点击评论
        void onCommentClick(VideoPlayWrap wrap, VideoBean bean);

        //点击关注
        void onFollowClick(VideoPlayWrap wrap, VideoBean bean);

        //点击头像
        void onAvatarClick(VideoPlayWrap wrap, VideoBean bean);

        //点击分享
        void onShareClick(VideoPlayWrap wrap, VideoBean bean);
    }

    public void setActionListener(ActionListener listener) {
        mActionListener = listener;
    }

}
