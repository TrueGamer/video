package com.huanxi.renrentoutiao.net.bean;

import com.huanxi.renrentoutiao.model.bean.AdVideoBean;
import com.huanxi.renrentoutiao.ui.adapter.AdBean;
import com.huanxi.renrentoutiao.ui.adapter.FloatAdBean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dinosa on 2018/2/9.
 */

public class ResSplashAds implements Serializable{
    /**
     * newfubiao : []
     * videofubiao : []
     * splash : {}
     * shipin_content : {}
     * my : []
     * newdetail : []
     * videodetail : []
     * signin : []
     * redbag : []
     * tasklist : []
     * exchange : []
     * friend : []
     */

    private SplashBean splash;
    private VideoDetailAds shipin_content;
    private ArrayList<FloatAdBean> newfubiao;
    private ArrayList<FloatAdBean> videofubiao;
    private ArrayList<AdBean> my;
    private ArrayList<AdBean> newdetail;
    private ArrayList<AdVideoBean> videodetail;
    private ArrayList<AdVideoBean> videodetail2;
    private ArrayList<AdVideoBean> videodetail3;
    private ArrayList<AdVideoBean> videodetail4;
    private ArrayList<AdVideoBean> videodetail5;
    private ArrayList<AdVideoBean> videodetail6;
    private ArrayList<AdVideoBean> videodetail7;
    private ArrayList<AdVideoBean> videodetail8;
    private ArrayList<AdVideoBean> videodetail9;
    private ArrayList<AdVideoBean> videodetail10;
    private ArrayList<AdBean> signin;
    private ArrayList<AdBean> redbag;
    private ArrayList<AdBean> tasklist;
    private ArrayList<AdBean> exchange;
    private ArrayList<AdBean> friend;

    public SplashBean getSplash() {
        return splash;
    }

    public void setSplash(SplashBean splash) {
        this.splash = splash;
    }

    public VideoDetailAds getShipin_content() {
        return shipin_content;
    }

    public void setShipin_content(VideoDetailAds shipin_content) {
        this.shipin_content = shipin_content;
    }

    public ArrayList<FloatAdBean> getNewfubiao() {
        return newfubiao;
    }

    public void setNewfubiao(ArrayList<FloatAdBean> newfubiao) {
        this.newfubiao = newfubiao;
    }

    public ArrayList<FloatAdBean> getVideofubiao() {
        return videofubiao;
    }

    public void setVideofubiao(ArrayList<FloatAdBean> videofubiao) {
        this.videofubiao = videofubiao;
    }

    public ArrayList<AdBean> getMy() {
        return my;
    }

    public void setMy(ArrayList<AdBean> my) {
        this.my = my;
    }

    public ArrayList<AdBean> getNewdetail() {
        return newdetail;
    }

    public void setNewdetail(ArrayList<AdBean> newdetail) {
        this.newdetail = newdetail;
    }

    public ArrayList<AdVideoBean> getVideodetail() {
        return videodetail;
    }

    public void setVideodetail(ArrayList<AdVideoBean> videodetail) {
        this.videodetail = videodetail;
    }

    public ArrayList<AdVideoBean> getVideodetail2() {
        return videodetail2;
    }

    public void setVideodetail2(ArrayList<AdVideoBean> videodetail2) {
        this.videodetail2 = videodetail2;
    }

    public ArrayList<AdBean> getSignin() {
        return signin;
    }

    public void setSignin(ArrayList<AdBean> signin) {
        this.signin = signin;
    }

    public ArrayList<AdBean> getRedbag() {
        return redbag;
    }

    public void setRedbag(ArrayList<AdBean> redbag) {
        this.redbag = redbag;
    }

    public ArrayList<AdBean> getTasklist() {
        return tasklist;
    }

    public void setTasklist(ArrayList<AdBean> tasklist) {
        this.tasklist = tasklist;
    }

    public ArrayList<AdBean> getExchange() {
        return exchange;
    }

    public void setExchange(ArrayList<AdBean> exchange) {
        this.exchange = exchange;
    }

    public ArrayList<AdBean> getFriend() {
        return friend;
    }

    public void setFriend(ArrayList<AdBean> friend) {
        this.friend = friend;
    }

    public ArrayList<AdVideoBean> getVideodetail3() {
        return videodetail3;
    }

    public void setVideodetail3(ArrayList<AdVideoBean> videodetail3) {
        this.videodetail3 = videodetail3;
    }

    public ArrayList<AdVideoBean> getVideodetail4() {
        return videodetail4;
    }

    public void setVideodetail4(ArrayList<AdVideoBean> videodetail4) {
        this.videodetail4 = videodetail4;
    }

    public ArrayList<AdVideoBean> getVideodetail5() {
        return videodetail5;
    }

    public void setVideodetail5(ArrayList<AdVideoBean> videodetail5) {
        this.videodetail5 = videodetail5;
    }

    public ArrayList<AdVideoBean> getVideodetail6() {
        return videodetail6;
    }

    public void setVideodetail6(ArrayList<AdVideoBean> videodetail6) {
        this.videodetail6 = videodetail6;
    }

    public ArrayList<AdVideoBean> getVideodetail7() {
        return videodetail7;
    }

    public void setVideodetail7(ArrayList<AdVideoBean> videodetail7) {
        this.videodetail7 = videodetail7;
    }

    public ArrayList<AdVideoBean> getVideodetail8() {
        return videodetail8;
    }

    public void setVideodetail8(ArrayList<AdVideoBean> videodetail8) {
        this.videodetail8 = videodetail8;
    }

    public ArrayList<AdVideoBean> getVideodetail9() {
        return videodetail9;
    }

    public void setVideodetail9(ArrayList<AdVideoBean> videodetail9) {
        this.videodetail9 = videodetail9;
    }

    public ArrayList<AdVideoBean> getVideodetail10() {
        return videodetail10;
    }

    public void setVideodetail10(ArrayList<AdVideoBean> videodetail10) {
        this.videodetail10 = videodetail10;
    }

    public static class SplashBean implements Serializable{

        public static final String TYPE_GDT="gdt";
        public static final String TYPE_TA="TA";
        public static final String TYPE_CUSTOM="qmtt_tl";
        public static final String TYPE_CSJ="csj";
        public static final String TYPE_BD = "baidu";

        private String type;
        private String id;
        private String imgurl;
        private String url;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "SplashBean{" +
                    "type='" + type + '\'' +
                    ", id='" + id + '\'' +
                    ", imgurl='" + imgurl + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public static class VideoDetailAds implements Serializable{


        private String type;
        private String id;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }






/*    private SplashBean splash;
    private VideoDetailAds shipin_content;
    private ArrayList<AdBean> my;

    private ArrayList<AdBean> tasklist;
    private ArrayList<AdBean> videodetail;
    private ArrayList<AdBean> redbag;
    private ArrayList<AdBean> friend;
    private ArrayList<AdBean> signin;
    private ArrayList<AdBean> exchange;
    private ArrayList<AdBean> newdetail;

    private ArrayList<FloatAdBean> newfubiao;
    private ArrayList<FloatAdBean> videofubiao;


    public ArrayList<FloatAdBean> getNewfubiao() {
        return newfubiao;
    }

    public void setNewfubiao(ArrayList<FloatAdBean> newfubiao) {
        this.newfubiao = newfubiao;
    }

    public ArrayList<FloatAdBean> getVideofubiao() {
        return videofubiao;
    }

    public void setVideofubiao(ArrayList<FloatAdBean> videofubiao) {
        this.videofubiao = videofubiao;
    }

    public List<AdBean> getNewdetail() {
        return newdetail;
    }



    public List<AdBean> getExchange() {
        if (exchange == null) {
            return new ArrayList<>();
        }
        return exchange;
    }


    public List<AdBean> getFriend() {
        if(friend == null){
            return new ArrayList<>();
        }
        return friend;
    }

    public List<AdBean> getSignin() {
        return signin;
    }



    public List<AdBean> getTasklist() {
        if(tasklist==null){
            return new ArrayList<>();
        }
        return tasklist;
    }


    public List<AdBean> getVideodetail() {
        if(videodetail == null){
            return new ArrayList<>();
        }
        return videodetail;
    }


    public List<AdBean> getRedbag() {
        if (redbag == null) {
            return new ArrayList<>();
        }
        return redbag;
    }

    public SplashBean getSplash() {
        return splash;
    }

    public void setSplash(SplashBean splash) {
        this.splash = splash;
    }

    public VideoDetailAds getShipin_content() {
        return shipin_content;
    }

    public void setShipin_content(VideoDetailAds shipin_content) {
        this.shipin_content = shipin_content;
    }


    public static class SplashBean implements Serializable{

        public static final String TYPE_GDT="gdt";
        *//**
         * type : gdt
         * id : 9030838014299060
         *//*

        private String type;
        private String id;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class VideoDetailAds implements Serializable{
        *//**
         * type : gdt
         * id : 7090434044396100
         *//*

        private String type;
        private String id;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public List<AdBean> getMy() {
        if(my==null){
            return new ArrayList<>();
        }
        return my;
    }*/

    @Override
    public String toString() {
        return "ResSplashAds{" +
                "splash=" + splash +
                ", shipin_content=" + shipin_content +
                ", newfubiao=" + newfubiao +
                ", videofubiao=" + videofubiao +
                ", my=" + my +
                ", newdetail=" + newdetail +
                ", videodetail=" + videodetail +
                ", videodetail2=" + videodetail2 +
                ", videodetail3=" + videodetail3 +
                ", videodetail4=" + videodetail4 +
                ", videodetail5=" + videodetail5 +
                ", videodetail6=" + videodetail6 +
                ", videodetail7=" + videodetail7 +
                ", videodetail8=" + videodetail8 +
                ", videodetail9=" + videodetail9 +
                ", videodetail10=" + videodetail10 +
                ", signin=" + signin +
                ", redbag=" + redbag +
                ", tasklist=" + tasklist +
                ", exchange=" + exchange +
                ", friend=" + friend +
                '}';
    }
}
