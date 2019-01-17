package com.huanxi.renrentoutiao.model.bean;

import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.service.DownloadServiceInterface;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.adhub.AdhubNativeBean;
import com.hubcloud.adhubsdk.NativeAdResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinosa on 2018/2/3.
 */

public class NewsItemBean implements MultiItemEntity,DownloadServiceInterface{



    public static final int ONLY_TEXT=0;
    public static final int ONLY_ONE_IMG=1;
    public static final int ONLY_THREE_IMG=2;
    public static final int GDT_NATIVE_AD=3;
    public static final int TA_NATIVE_AD=4;
    public static final int CSJ_NATIVE_AD = 11;
    public static final int BAIDU_AD = 12;
    public static final int ADHUB_NATIVE_AD = 13;

    public static final int CUSTOM_ONLY_IMG=5;      //仅仅只有图
    public static final int CUSTOM_UP_TITLE_DOWN_IMG=6; //上文下图
    public static final int CUSTOM_LEFT_TITLE_RIGHT_IMG=7;//左文右图
    public static final int CUSTOM_BANNER_20_3=8; //20_3banner；
    public static final int CUSTOM_UP_TITLE_DOWN_MUILT_IMG=9; //20_3banner；
//    public static final int CUSTOM_RIGHT_TITLE_LEFT_IMG=10;//左图右文

    public static final String TYPE_GDT_AD ="gdt";
    public static final String TYPE_TA_AD ="ta";
    public static final String TYPE_CSJ = "csj";
    public static final String AD="qmttad";
    public static final String TYPE_BD = "baidu";
    public static final String TYPE_ADHUB = "adhub";

    //这里是自定义的广告的操作：
    public static final String TYPE_CUSTOM_TYPE_IMG ="qmtt_tl";
    public static final String TYPE_CUSTOM_TYPE_UP_TITLE_DOWN_IMG ="qmtt_tlt";
    public static final String TYPE_CUSTOM_BANNER_20_3 ="qmtt_banner";
    public static final String TYPE_CUSTOM_UP_TITLE_DOWN_MUILTY_IMG ="qmtt_mulimg";

    public static final String TYPE_CUSTOM_TYPE_LEFT_TITLE_RIGHT_IMG ="qmtt_lt";
    public static final String TYPE_CUSTOM_TYPE_RIGHT_TITLE_LEFT_IMG ="qmtt_rt";

    public String qmttcontenttype;
    public String ta;

    public NewsItemBean(String qmttcontenttype, String type) {
        this.qmttcontenttype = qmttcontenttype;
        this.type = type;
    }

    public NewsItemBean() {
    }

    private String hotnews;
    private String topic;
    private String rowkey;
    private String picnums;
    private String miniimg02_size;
    private String type;
    private String date;
    private String ispicnews;
    private String urlpv;
    private String source;
    private String miniimg_size;
    private String sourceurl;
    private String praisecnt;
    private String tramplecnt;
    private String pk;
    private ArrayList<MiniimgBean> miniimg;
    private String urlmd5;



           /* "id": 1,
            "title": "11111",
            "cont": "小说小说小说小说小说小说小说",
            "imgurl": "http://h.hiphotos.baidu.com/baike/pic/item/503d269759ee3d6d12a5540144166d224e4ade9d.jpg",
            "url": "http://baidu.com",
            "downurl": "http://image-hxdance.oss-cn-beijing.aliyuncs.com/songcover/hehe广场舞《DJ的歌》.jpg",
            "size": 111,
            "packename": "111",
            "appname": "111",*/

    String id;
    String title;
    String cont;
    String imgurl;
    String url;
    String downurl;
    Long size;
    String packename;
    String appname;
    List<String> imgurls;

    // 穿山甲广告
    TTFeedAd ttFeedAd;
    NativeAdResponse nativeAdResponse;

    public NativeAdResponse getNativeAdResponse() {
        return nativeAdResponse;
    }

    public void setNativeAdResponse(NativeAdResponse nativeAdResponse) {
        this.nativeAdResponse = nativeAdResponse;
    }

    public TTFeedAd getTtFeedAd() {
        return ttFeedAd;
    }

    public void setTtFeedAd(TTFeedAd ttFeedAd) {
        this.ttFeedAd = ttFeedAd;
    }

    public List<String> getImgurls() {
        return imgurls;
    }

    public void setImgurls(List<String> imgurls) {
        this.imgurls = imgurls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPackename() {
        return packename;
    }

    public void setPackename(String packename) {
        this.packename = packename;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getUrlmd5() {
        return urlmd5;
    }

    public void setUrlmd5(String urlmd5) {
        this.urlmd5 = urlmd5;
    }

    public String getHotnews() {
        return hotnews;
    }

    public void setHotnews(String hotnews) {
        this.hotnews = hotnews;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getRowkey() {
        return rowkey;
    }

    public void setRowkey(String rowkey) {
        this.rowkey = rowkey;
    }

    public String getPicnums() {
        return picnums;
    }

    public void setPicnums(String picnums) {
        this.picnums = picnums;
    }

    public String getMiniimg02_size() {
        return miniimg02_size;
    }

    public void setMiniimg02_size(String miniimg02_size) {
        this.miniimg02_size = miniimg02_size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIspicnews() {
        return ispicnews;
    }

    public void setIspicnews(String ispicnews) {
        this.ispicnews = ispicnews;
    }

    public String getUrlpv() {
        return urlpv;
    }

    public void setUrlpv(String urlpv) {
        this.urlpv = urlpv;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMiniimg_size() {
        return miniimg_size;
    }

    public void setMiniimg_size(String miniimg_size) {
        this.miniimg_size = miniimg_size;
    }

    public String getSourceurl() {
        return sourceurl;
    }

    public void setSourceurl(String sourceurl) {
        this.sourceurl = sourceurl;
    }

    public String getPraisecnt() {
        return praisecnt;
    }

    public void setPraisecnt(String praisecnt) {
        this.praisecnt = praisecnt;
    }

    public String getTramplecnt() {
        return tramplecnt;
    }

    public void setTramplecnt(String tramplecnt) {
        this.tramplecnt = tramplecnt;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public List<MiniimgBean> getMiniimg() {
        return miniimg;
    }

    public void setMiniimg(ArrayList<MiniimgBean> miniimg) {
        this.miniimg = miniimg;
    }

    public String getQmttcontenttype() {
        return qmttcontenttype;
    }

    public void setQmttcontenttype(String qmttcontenttype) {
        this.qmttcontenttype = qmttcontenttype;
    }

    public String getTa() {
        return ta;
    }

    public void setTa(String ta) {
        this.ta = ta;
    }

    @Override
    public int getItemType() {

        if(qmttcontenttype!=null && (qmttcontenttype.equals(AD) || qmttcontenttype.equals(TYPE_CUSTOM_TYPE_LEFT_TITLE_RIGHT_IMG))){

            //这里表示广告；
            if(TYPE_GDT_AD.equals(type)){
                //这里是广点通的处理方式
                return GDT_NATIVE_AD;
            } else if(TYPE_CSJ.equals(type)) {
                return CSJ_NATIVE_AD;
            } else if (TYPE_BD.equals(type)) {
                return BAIDU_AD;
            } else if(TYPE_CUSTOM_TYPE_IMG.equals(type)){
                //这里表示自定义单图的广告；
                return CUSTOM_ONLY_IMG;
            } else if(TYPE_CUSTOM_TYPE_UP_TITLE_DOWN_IMG.equals(type)){
                //这里表示上文下图的广告；
                return CUSTOM_UP_TITLE_DOWN_IMG;

            } else if(TYPE_CUSTOM_BANNER_20_3.equals(type)){
                //小banner;
                return CUSTOM_BANNER_20_3;

            }else if(TYPE_CUSTOM_UP_TITLE_DOWN_MUILTY_IMG.equals(type)){
                //上文下三图
                return CUSTOM_UP_TITLE_DOWN_MUILT_IMG;

            }else if(TYPE_CUSTOM_TYPE_LEFT_TITLE_RIGHT_IMG.equals(type)){
                //这里是左文右边图；
                return CUSTOM_LEFT_TITLE_RIGHT_IMG;
            } else if(TYPE_ADHUB.equals(type)) {
                return ADHUB_NATIVE_AD;
            } else {
                //这里推压的处理方式；
                return TA_NATIVE_AD;
            }
//            else if(TYPE_CUSTOM_TYPE_RIGHT_TITLE_LEFT_IMG.equals(type)){
//                //这里是左文右边图；
//                return CUSTOM_RIGHT_TITLE_LEFT_IMG;
//            }

        }else{
            //这里表示新闻

            int imageSize =0;
            try {
                imageSize= Integer.parseInt(miniimg_size);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if(imageSize<0){
                imageSize=0;
            }

            if(imageSize == 0){
                return ONLY_TEXT;
            }else if(imageSize < 3){
                return ONLY_ONE_IMG;
            }else {
                return ONLY_THREE_IMG;
            }
        }
    }

    //===========下载逻辑=================

    @Override
    public String getPackageName() {
        return packename;
    }

    @Override
    public String getAppName() {
        return appname;
    }

    @Override
    public Long getAppSize() {
        return size;
    }

    @Override
    public String getDownloadUrl() {
        return downurl;
    }

    //================================

    public static class MiniimgBean implements Serializable {
        /**
         * imgwidth : 303
         * imgheight : 227
         * src : http://04.imgmini.eastday.com/mobile/20180117/20180117101649_1e234f6bcd64a95ae128d37044eb0bd5_1_mwpm_03200403.jpg
         */

        private String imgwidth;
        private String imgheight;
        private String src;

        public String getImgwidth() {
            return imgwidth;
        }

        public void setImgwidth(String imgwidth) {
            this.imgwidth = imgwidth;
        }

        public String getImgheight() {
            return imgheight;
        }

        public void setImgheight(String imgheight) {
            this.imgheight = imgheight;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }

}
