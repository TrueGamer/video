package com.huanxi.renrentoutiao.ui.adapter;

import android.widget.BaseAdapter;

import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.service.DownloadServiceInterface;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;

import java.util.List;

/**
 * Created by Dinosa on 2018/2/10.
 */

public class AdBean implements MultiItemEntity,DownloadServiceInterface {


    public static final String TYPE_GDT = "gdt";
    public static final String TYPE_TA = "ta";
    public static final String TYPE_CSJ = "csj";
    public static final String TYPE_BD = "baidu";
    //五种自定义广告；
    public static final String TYPE_CUSTOMER_BANNER = "qmtt_tl";
    public static final String TYPE_CUSTOMER_TITLE_AND_IMG = "qmtt_tlt";
    public static final String TYPE_CUSTOMER_LEFT_TITLE_RIGHT_IMG_AD_8 = "qmtt_lt";
    public static final String TYPE_CUSTOMER_BANNER_20_3 = "qmtt_banner";
    public static final String TYPE_CUSTOMER_UP_TITLE_DOWN_MUILTY_IMG = "qmtt_mulimg"; //上文，下多图

    public static final String TYPE_GDT_UP_TEXT_DOWN_IMG = "uptextdownimg";

    public static final String TYPE_TA_BANNER = "banner";
    public static final String TYPE_TA_UP_TEXT_DOWN_IMG = "upimgdowntext";
    /**
     * id : 6
     * title : 22
     * cont : null
     * imgurl : http://h.hiphotos.baidu.com/baike/pic/item/503d269759ee3d6d12a5540144166d224e4ade9d.jpg
     * url : http://baidu.com
     * downurl : null
     * size : null
     * packename : null
     * appname : null
     * type : qmtt_vf
     * code : qmtt_vf
     * phonetype : 1
     */

    private String id;
    private String title;
    private String cont;
    private String imgurl;
    private List<String> imgurls;
    private String url;
    private String downurl;
    private Long size;
    private String packename;
    private String appname;
    private String type;
    private String code;
    private int phonetype;
    private String adtype;

    private TTFeedAd ttFeedAd;

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

    public AdBean(String title, String cont, String imgurl, String url, String downurl, Long size, String packename, String appname, List<String> imgurls) {
        this.title = title;
        this.cont = cont;
        this.imgurl = imgurl;
        this.url = url;
        this.downurl = downurl;
        this.size = size;
        this.packename = packename;
        this.appname = appname;
        this.imgurls=imgurls;
    }

    public AdBean() {
    }

    @Override
    public int getItemType() {

        if(type == null) {
            return BaseAdsAdapter.GDT_UP_TEXT_DOWN_IMG;
        }

        if (type.equals(TYPE_GDT)) {

            if (adtype.equals(TYPE_GDT_UP_TEXT_DOWN_IMG)) {
                return BaseAdsAdapter.GDT_UP_TEXT_DOWN_IMG;
            } else {
                return BaseAdsAdapter.GDT_BANNER;
            }

        } else if(type.equals(TYPE_CSJ)) { // 穿山甲
            return BaseAdsAdapter.CSJ_LARGE;
        } else if (type.equals(TYPE_BD)) { // 百度广告
            return BaseAdsAdapter.BAIDU_AD;
        } else if (type.equals(TYPE_CUSTOMER_BANNER)) {
            //大banner
            return BaseAdsAdapter.CUSTOMER_BANNNER_AD_1;

        } else if (type.equals(TYPE_CUSTOMER_TITLE_AND_IMG)) {
            //上文下图
            return BaseAdsAdapter.CUSTOMER_UP_TITLE_DOWN_IMG_AD_2;

        }else if(type.equals(TYPE_CUSTOMER_BANNER_20_3)){
            //小banner;
            return BaseAdsAdapter.CUSTOMER_BANNER_20_3;
        } else if (type.equals(TYPE_CUSTOMER_LEFT_TITLE_RIGHT_IMG_AD_8)) {
            //左文右图
            return BaseAdsAdapter.CUSTOMER_LEFT_TITLE_RIGHT_IMG_AD_8;
        }else if(type.equals(TYPE_CUSTOMER_UP_TITLE_DOWN_MUILTY_IMG)){
            //上文，下三图；
            return BaseAdsAdapter.CUSTOMER_UP_TITLE_DOWN_MUILT_IMG;
        } else if (type.equals(TYPE_TA)){
            if (adtype.equals(TYPE_TA_BANNER)) {
                return BaseAdsAdapter.TA_BANNER;
            } else if (adtype.equals(TYPE_TA_UP_TEXT_DOWN_IMG)) {
                //上文下图；
                return BaseAdsAdapter.TA_UP_TEXT_DOWN_IMG;
            } else {
                return BaseAdsAdapter.GDT_BANNER;
            }
        } else {
            //这里是一个默认的处理方式；
            return BaseAdsAdapter.GDT_BANNER;
        }
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPhonetype() {
        return phonetype;
    }

    public void setPhonetype(int phonetype) {
        this.phonetype = phonetype;
    }

    public String getAdtype() {
        return adtype;
    }

    public void setAdtype(String adtype) {
        this.adtype = adtype;
    }


    //这里的逻辑是使用downloadService必须实现的接口

    @Override
    public String getPackageName() {
        return getPackename();
    }

    @Override
    public String getAppName() {
        return getAppname();
    }

    @Override
    public Long getAppSize() {
        return getSize();
    }

    @Override
    public String getDownloadUrl() {
        return getDownurl();
    }
}
