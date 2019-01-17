package com.huanxi.renrentoutiao.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.utils.ImageUtils;
import com.qq.e.ads.nativ.MediaListener;
import com.qq.e.ads.nativ.MediaView;
import com.qq.e.ads.nativ.NativeMediaAD;
import com.qq.e.ads.nativ.NativeMediaADData;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.util.AdError;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Dinosa on 2018/3/2.
 */

public class Test3Activity extends BaseTitleActivity {

    @BindView(R.id.gdt_media_view)
    MediaView mMediaView;
    @BindView(R.id.text_count_down)
    TextView mTextCountDown;
    @BindView(R.id.img_poster)
    ImageView mImagePoster;
    @BindView(R.id.native_3img_desc)
    TextView mNative3imgDesc;
    @BindView(R.id.img_1)
    ImageView mImg1;
    @BindView(R.id.img_2)
    ImageView mImg2;
    @BindView(R.id.img_3)
    ImageView mImg3;
    @BindView(R.id.native_3img)
    LinearLayout mNative3img;
    @BindView(R.id.native_3img_title)
    TextView mNative3imgTitle;
    @BindView(R.id.native_3img_ad_container)
    LinearLayout mNative3imgAdContainer;
    @BindView(R.id.custom_container)
    FrameLayout mCustomContainer;
    @BindView(R.id.img_logo)
    ImageView mImgLogo;
    @BindView(R.id.btn_download)
    Button mDownloadButton;
    @BindView(R.id.text_title)
    TextView mTextTitle;
    @BindView(R.id.text_desc)
    TextView mTextDesc;
    @BindView(R.id.ad_info_container)
    RelativeLayout mADInfoContainer;
    @BindView(R.id.text_load_result)
    TextView textLoadResult;
    @BindView(R.id.root)
    RelativeLayout mRoot;


    private NativeMediaADData mAD;                        // 加载的原生视频广告对象，本示例为简便只演示加载1条广告的示例
    private NativeMediaAD mADManager;                     // 原生广告manager，用于管理广告数据的加载，监听广告回调


    public static  final String TAG=Test3Activity.class.getSimpleName();

    @Override
    public int getBodyLayoutId() {
        return R.layout.layout_test3_activity;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        super.initView(rootView, savedInstanceState);
        System.out.println("simpleName: "+Test3Activity.class.getSimpleName());
        initView();
        initNativeVideoAD();
        loadAD();
    }


    private void initView() {
        mMediaView = (MediaView) findViewById(R.id.gdt_media_view);
        mImagePoster = (ImageView) findViewById(R.id.img_poster);
        mADInfoContainer = (RelativeLayout) this.findViewById(R.id.ad_info_container);
        mDownloadButton = (Button) mADInfoContainer.findViewById(R.id.btn_download);
        mTextCountDown = (TextView) findViewById(R.id.text_count_down);
        textLoadResult = (TextView) findViewById(R.id.text_load_result);
    }

    private void loadAD() {
        if (mADManager != null) {
            try {
                mADManager.loadAD(1);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "加载失败", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void initNativeVideoAD() {
        NativeMediaAD.NativeMediaADListener listener = new NativeMediaAD.NativeMediaADListener() {

            /**
             * 广告加载成功
             *
             * @param adList  广告对象列表
             */
            @Override
            public void onADLoaded(List<NativeMediaADData> adList) {
                if (adList.size() > 0) {
                    mAD = adList.get(0);
                    /**
                     * 加载广告成功，开始渲染。
                     */
                    initADUI();

                    if (mAD.getAdPatternType() == AdPatternType.NATIVE_VIDEO) {
                        /**
                         * 如果该条原生广告是一条带有视频素材的广告，还需要先调用preLoadVideo接口来加载视频素材：
                         *    - 加载成功：回调NativeMediaADListener.onADVideoLoaded(NativeMediaADData adData)方法
                         *    - 加载失败：回调NativeMediaADListener.onADError(NativeMediaADData adData, int errorCode)方法，错误码为700
                         */
                        mAD.preLoadVideo();
                        textLoadResult.setText("getAdPatternType() == AdPatternType.NATIVE_VIDEO：" + "这是一条视频广告");
                    } else if (mAD.getAdPatternType() == AdPatternType.NATIVE_2IMAGE_2TEXT) {
                        /**
                         * 如果该条原生广告只是一个普通图文的广告，不带视频素材，那么渲染普通的UI即可。
                         */
                        textLoadResult.setText("getAdPatternType() == AdPatternType.NATIVE_2IMAGE_2TEXT：" + "这是一条两图两文广告");
                    } else if (mAD.getAdPatternType() == AdPatternType.NATIVE_3IMAGE) {
                        textLoadResult.setText("getAdPatternType() == AdPatternType.NATIVE_3IMAGE：" + "这是一条三小图广告");
                    }
                }
            }

            /**
             * 广告加载失败
             *
             * @param adError   广告加载失败的错误内容，错误含义请参考开发者文档第4章。
             */
            @Override
            public void onNoAD(AdError adError) {

                textLoadResult.setText(String.format("广告加载失败，错误码：%d，错误信息：%s", adError.getErrorCode(),
                        adError.getErrorMsg()));
            }

            /**
             * 广告状态发生变化，对于App类广告而言，下载/安装的状态和下载进度可以变化。
             *
             * @param ad    状态发生变化的广告对象
             */
            @Override
            public void onADStatusChanged(NativeMediaADData ad) {
                if (ad != null && ad.equals(mAD)) {
                    updateADUI();   // App类广告在下载过程中，下载进度会发生变化，如果开发者需要让用户了解下载进度，可以更新UI。
                }
            }

            /**
             * 广告处理发生错误，当调用一个广告对象的onExposured、onClicked、preLoadVideo接口时，如果发生了错误会回调此接口，具体的错误码含义请参考开发者文档。
             *
             * @param adData    广告对象
             * @param adError   错误内容，错误含义请参考开发者文档第4章。
             */
            @Override
            public void onADError(NativeMediaADData adData, AdError adError) {
                Log.i(TAG, adData.getTitle() + " onADError, error code: " + adError.getErrorCode()
                        + ", error msg: " + adError.getErrorMsg());
            }

            /**
             * 当调用一个广告对象的preLoadVideo接口时，视频素材加载完成后，会回调此接口，在此回调中可以给广告对象绑定MediaView组件播放视频。
             *
             * @param adData  视频素材加载完成的广告对象，很显然这个广告一定是一个带有视频素材的广告，需要给它bindView并播放它
             */
            @Override
            public void onADVideoLoaded(NativeMediaADData adData) {
                Log.i(TAG, adData.getTitle() + " ---> 视频素材加载完成"); // 仅仅是加载视频文件完成，如果没有绑定MediaView视频仍然不可以播放
                bindMediaView();
            }

            /**
             * 广告曝光时的回调
             *
             * 注意：带有视频素材的原生广告可以多次曝光 按照曝光计费
             * 没带有视频素材的广告只能曝光一次 按照点击计费
             *
             * @param adData  曝光的广告对象
             */
            @Override
            public void onADExposure(NativeMediaADData adData) {
                Log.i(TAG, adData.getTitle() + " onADExposure");
            }

            /**
             * 广告被点击时的回调
             *
             * @param adData  被点击的广告对象
             */
            @Override
            public void onADClicked(NativeMediaADData adData) {
                Log.i(TAG, adData.getTitle() + " onADClicked");
            }
        };

        mADManager = new NativeMediaAD(getApplicationContext(), ConstantAd.GdtAD.APPID, ConstantAd.GdtAD.NEWS_UP_TEXT_DOWN_IMG_AD_1, listener);
    }



    private void updateADUI() {
        if (!mAD.isAPP()) {
            mDownloadButton.setText("浏览");
            return;
        }
        switch (mAD.getAPPStatus()) {
            case 0:
                mDownloadButton.setText("下载");
                break;
            case 1:
                mDownloadButton.setText("启动");
                break;
            case 2:
                mDownloadButton.setText("更新");
                break;
            case 4:
                mDownloadButton.setText(mAD.getProgress() + "%");
                break;
            case 8:
                mDownloadButton.setText("安装");
                break;
            case 16:
                mDownloadButton.setText("下载失败，重新下载");
                break;
            default:
                mDownloadButton.setText("浏览");
                break;
        }
    }

    /**
     * 渲染页面的操作；
     */
    private void initADUI() {
        int patternType = mAD.getAdPatternType();
        if (patternType == AdPatternType.NATIVE_2IMAGE_2TEXT || patternType == AdPatternType.NATIVE_VIDEO) {

            ImageUtils.loadImage(this, mAD.getIconUrl(), mImgLogo);
            ImageUtils.loadImage(this, mAD.getImgUrl(), mImagePoster);

        /*    mAQuery.id(R.id.img_poster).image(mAD.getImgUrl(), false, true, 0, 0, new BitmapAjaxCallback() {
                @Override
                protected void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                    // AQuery框架有一个问题，就是即使在图片加载完成之前将ImageView设置为了View.GONE，在图片加载完成后，这个ImageView会被重新设置为VIEW.VISIBLE。
                    // 所以在这里需要判断一下，如果已经把ImageView设置为隐藏，开始播放视频了，就不要再显示广告的大图。否则将影响到sdk的曝光上报，无法产生收益。
                    // 开发者在用其他的图片加载框架时，也应该注意检查下是否有这个问题。
                    if (iv.getVisibility() == View.VISIBLE) {
                        iv.setImageBitmap(bm);
                    }
                }
            });*/
            mTvTitle.setText(mAD.getTitle());
            mTextDesc.setText(mAD.getDesc());

        } else if (patternType == AdPatternType.NATIVE_3IMAGE) {
       /*     mAQuery.id(R.id.img_1).image(mAD.getImgList().get(0), false, true);
            mAQuery.id(R.id.img_2).image(mAD.getImgList().get(1), false, true);
            mAQuery.id(R.id.img_3).image(mAD.getImgList().get(2), false, true);
            mAQuery.id(R.id.native_3img_title).text((String) mAD.getTitle());
            mAQuery.id(R.id.native_3img_desc).text((String) mAD.getDesc());*/

            ImageUtils.loadImage(this, mAD.getImgList().get(0), mImg1);
            ImageUtils.loadImage(this, mAD.getImgList().get(1), mImg2);
            ImageUtils.loadImage(this, mAD.getImgList().get(2), mImg3);
            mNative3imgTitle.setText(mAD.getTitle());
            mNative3imgDesc.setText(mAD.getDesc());
        }
    }


    /**
     * 将广告实例和MediaView绑定，播放视频。
     *
     * 注意：播放视频前需要将广告的大图隐藏，将MediaView显示出来，否则视频将无法播放，也无法上报视频曝光，无法产生计费。
     */
    private void bindMediaView() {
        if (mAD.isVideoAD()) {
            mImagePoster.setVisibility(View.GONE);
            mMediaView.setVisibility(View.VISIBLE);

            /**
             * bindView(MediaView view, boolean useDefaultController):
             *    - useDefaultController: false，不会调用广点通的默认视频控制条
             *    - useDefaultController: true，使用SDK内置的播放器控制条，此时开发者需要把demo下的res文件夹里面的图片拷贝到自己项目的res文件夹去
             *
             * 在这里绑定MediaView后，SDK会根据视频素材的宽高比例，重新给MediaView设置新的宽高
             */
            mAD.bindView(mMediaView, true);
            mAD.play();

            /** 设置视频播放过程中的监听器 */
            mAD.setMediaListener(new MediaListener() {

                /**
                 * 视频播放器初始化完成，准备好可以播放了
                 *
                 * @param videoDuration 视频素材的总时长
                 */
                @Override
                public void onVideoReady(long videoDuration) {
                    Log.i(TAG, "onVideoReady, videoDuration = " + videoDuration);
                    duration = videoDuration;
                }

                /** 视频开始播放 */
                @Override
                public void onVideoStart() {
                    Log.i(TAG, "onVideoStart");
                    tikTokHandler.post(countDown);
                    mTextCountDown.setVisibility(View.VISIBLE);
                }

                /** 视频暂停 */
                @Override
                public void onVideoPause() {
                    Log.i(TAG, "onVideoPause");
                    mTextCountDown.setVisibility(View.GONE);
                }

                /** 视频自动播放结束，到达最后一帧 */
                @Override
                public void onVideoComplete() {
                    Log.i(TAG, "onVideoComplete");
                    releaseCountDown();
                    mTextCountDown.setVisibility(View.GONE);
                }

                /** 视频播放时出现错误 */
                @Override
                public void onVideoError(AdError adError) {
                    Log.i(TAG, String.format("onVideoError, errorCode: %d, errorMsg: %s",
                            adError.getErrorCode(), adError.getErrorMsg()));
                }

                /** SDK内置的播放器控制条中的重播按钮被点击 */
                @Override
                public void onReplayButtonClicked() {
                    Log.i(TAG, "onReplayButtonClicked");
                }

                /**
                 * SDK内置的播放器控制条中的下载/查看详情按钮被点击
                 * 注意: 这里是指UI中的按钮被点击了，而广告的点击事件回调是在onADClicked中，开发者如需统计点击只需要在onADClicked回调中进行一次统计即可。
                 */
                @Override
                public void onADButtonClicked() {
                    Log.i(TAG, "onADButtonClicked");
                }

                /** SDK内置的全屏和非全屏切换回调，进入全屏时inFullScreen为true，退出全屏时inFullScreen为false */
                @Override
                public void onFullScreenChanged(boolean inFullScreen) {
                    Log.i(TAG, "onFullScreenChanged, inFullScreen = " + inFullScreen);

                    // 原生视频广告默认静音播放，进入到全屏后建议开发者可以设置为有声播放
                    if (inFullScreen) {
                        mAD.setVolumeOn(true);
                    } else {
                        mAD.setVolumeOn(false);
                    }
                }
            });
        }
    }

    private void releaseCountDown() {
        if (countDown != null) {
            tikTokHandler.removeCallbacks(countDown);
        }
    }

    private final Handler tikTokHandler = new Handler();  // 倒计时读取Handler，开发者可以按照自己的设计实现，此处仅供参考




    /**
     * 刷新广告倒计时，本示例提供的思路仅开发者供参考，开发者完全可以根据自己的需求设计不同的样式。
     */
    private static final String TEXT_COUNTDOWN = "广告倒计时：%s ";
    private long currentPosition, oldPosition, duration;
    private Runnable countDown = new Runnable() {
        public void run() {
            if (mAD != null) {
                currentPosition = mAD.getCurrentPosition();
                long position = currentPosition;
                if (oldPosition == position && mAD.isPlaying()) {
                    Log.d(TAG, "玩命加载中...");
                    mTextCountDown.setTextColor(Color.WHITE);
                    mTextCountDown.setText("玩命加载中...");
                } else {
                    Log.d(TAG, String.format(TEXT_COUNTDOWN, Math.round((duration - position) / 1000.0) + ""));
                    mTextCountDown.setText(String.format(TEXT_COUNTDOWN, Math.round((duration - position) / 1000.0) + ""));
                }
                oldPosition = position;
                if (mAD.isPlaying()) {
                    tikTokHandler.postDelayed(countDown, 500); // 500ms刷新一次进度即可
                }
            }
        }

    };

}
