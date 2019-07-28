package com.huanxi.renrentoutiao.globle;

/**
 * Created by Dinosa on 2018/2/8.
 * 广告的posId;
 */

public class ConstantAd {


    /**
     * 广点通的广告；
     */
    public static class GdtAD{


        public static final String APPID="1109578541";

        public static final String SPLASH_AD="2020475355431228";  //开屏广告 实际中商用的
        public static final String[] VIDEO_UP_IMG_DOWN_TEXT_AD= {"3030931677224158" , "8030733937086647"}; //上图下文

        public static final String BANNER_AD="5020532607828291";
        public static final String POPUPWINDOW_AD="4000532617424263";//插屏广告

        public static final String NEWS_UP_TEXT_DOWN_IMG_AD_1="3000538637427119";  //上文下图1
        public static final String NEWS_UP_TEXT_DOWN_IMG_AD_2="2080334607220270";  //上文下图2

		public static final String TEXT_LEFT_IMG_RIGHT="5080830779360404";  //左文右图
        public static final String IMG_LEFT_TEXT_RIGHT="6000939719868453";  //左图右文

        public static final String NATIVE_VIDEO_IMG="6020074355430370";  //纯图片  这个不知道尺寸大小

        public static final String[] TWO_TEXT_TWO_IMG = {"8040435997683614" ,
                "6030338967387625" , "2080736937585658"};  //双图双文
        public static final String[] NEWS_UP_TEXT_DOWN_IMG_AD = {"3000538637427119",
                "2080334607220270"};

        public static final String[] AD_CODE = {"3030931677224158" , "8030733937086647" , "5080830779360404"
            , "6000939719868453" , "8040435997683614" , "6030338967387625" , "2080736937585658" , "3000538637427119",
                "2080334607220270"};

        public static final String AD_CODE_2 = "3050250425101232";
        public static final String[] AD_CODE_3 = {"5080830779360404","6000939719868453"};
    }

    /**
     * 推啊的广告
     */
    public static class TuiAAD{

        public static final String APPKEY="3ThfjmMjDSM7z5gk5QBx3nbho1Dy";
        public static final String APP_SECRET="3XXZdAorNpF1FLfvGR2tzxWmoeLbzgwemnKXPZX";

        public static final int ICON_FLOAT=191804;//这个不知道尺寸大小
        public static final int INFO_FLOW_LEFT_TEXT_RIGHT_IMG=191803;//左信息流右边文字
        public static final int INFO_FLOW_UP_TEXT_DOWN_IMG=191802;//上信息流下边文字介绍

        public static final int BANNER=191801;
        public static final int SPLASH_AD=191806;
        public static final int CUSTOM_AD=191804; //这个不知道尺寸大小
        public static final int CUSTOM_AD_MY=191803;//这个不知道尺寸大小
    }

    public static class CSJAD {
        public static final String APP_ID = "5023855";

        public static final String BannerID = "902510218";

        public static final String  SPLASH_AD = "823855880"; //开屏广告
        public static final String VIDEO_AD = "923855521"; // 信息流
        public static final String VIDEO_DETAIL_ID = "923855244"; // 视频前图片

        public static final String NEWS_DETAIL_AD = "902510346";
    }

    /**
     * 百度广告相关配置
     */
    public static class BAIDUAD {
        public static final String APP_ID = "ae477682";

        public static final String[] BANNER = {"5897391" , "5897343" ,"5897414"};

        public static final String SPLASH_AD = "5897390";

    }
}
