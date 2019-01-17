package com.huanxi.renrentoutiao.presenter.format;

import java.util.ArrayList;

/**
 * Created by Dinosa on 2018/4/11.
 * 判断广告的业务逻辑类；
 */

public class JudgeAdsPersenter {


    public static final String TYPE_CUSTOMER_BANNER = "qmtt_tl";
    public static final String TYPE_CUSTOMER_TITLE_AND_IMG = "qmtt_tlt";
    public static final String TYPE_CUSTOMER_LEFT_TITLE_RIGHT_IMG_AD_8 = "qmtt_lt";
    public static final String TYPE_CUSTOMER_BANNER_20_3 = "qmtt_banner";
    public static final String TYPE_CUSTOMER_UP_TITLE_DOWN_MUILTY_IMG = "qmtt_mulimg"; //上文，下多图,这里自定义的广告的逻辑



    public static ArrayList<String> mCustomAdList;


    static {

        mCustomAdList = new ArrayList<>();
        mCustomAdList.add(TYPE_CUSTOMER_BANNER);
        mCustomAdList.add(TYPE_CUSTOMER_TITLE_AND_IMG);
        mCustomAdList.add(TYPE_CUSTOMER_LEFT_TITLE_RIGHT_IMG_AD_8);
        mCustomAdList.add(TYPE_CUSTOMER_BANNER_20_3);
        mCustomAdList.add(TYPE_CUSTOMER_UP_TITLE_DOWN_MUILTY_IMG);
    }


    public static boolean isAd(String contentType){
        return "qmttad".equals(contentType);
    }


    /**
     * 是不是广点通的广告
     * @return
     */
    public static boolean isGdtAd(String type){
        return "gdt".equals(type);
    }

    /**
     * 是不是百度的广告
     * @return
     */
    public static boolean isBDAd(String type){
        return "baidu".equals(type);
    }

    /**
     * 是不是穿山甲的广告
     * @return
     */
    public static boolean isCsjAd(String type){
        return "csj".equals(type);
    }

    /**
     * 是不是推啊的广告；
     * @return
     */
    public static boolean isTaAd(String type){
        return "ta".equals(type);
    }

    /**
     * 判断是不是自定义任务；
     * @return
     */
    public static boolean isCustomAd(String type){

        return mCustomAdList.contains(type);
    }

    /**
     * 是否是自定义大的banner广告；
     * @return
     */
    public static boolean isCustomBigBannerAd(String type){
        return TYPE_CUSTOMER_BANNER.equals(type);
    }

    /**
     * 是否是自定义的banner20_3广告；
     * @return
     */
    public static boolean isCustomBanner20_3Ad(String type){
        return TYPE_CUSTOMER_BANNER_20_3.equals(type);
    }

    /**
     * 是否是自定义大的banner广告；
     * @return
     */
    public static boolean isCustomUpTitleDownImg(String type){
        return TYPE_CUSTOMER_TITLE_AND_IMG.equals(type);
    }

    /**
     * 左文有图；
     * @return
     */
    public static boolean isCustomLeftTitleRightImg(String type){
        return TYPE_CUSTOMER_LEFT_TITLE_RIGHT_IMG_AD_8.equals(type);
    }

    /**
     * 左文有图；
     * @return
     */
    public static boolean isUpTitleDownMuiltyImg(String type){
        return TYPE_CUSTOMER_UP_TITLE_DOWN_MUILTY_IMG.equals(type);
    }


}
