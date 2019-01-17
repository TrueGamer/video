package com.huanxi.renrentoutiao.model.bean.news;

import java.util.List;

/**
 * Created by Dinosa on 2018/3/28.
 * 这里是新闻的实体对象；
 */

public class NewsBean {

    /**
     * urlmd5 : d00a2c7f4c6117805c68ea2c2222a766
     * url : http://mini.eastday.com/mobile/180320181705206.html?qid=qid02561
     * topic : 麦肯罗弟弟谈哈勒普遭横扫：她应像费德勒学习
     * source : 体育综合
     * date : 2018-03-20 18:17
     * miniimg_size : 1
     * miniimg : [{"imgwidth":"320","imgheight":"240","src":"http://08.imgmini.eastday.com/mobile/20180320/20180320181705_d8e13ff70f901eb12943713a6ebf976b_1_mwpm_03200403.jpg"}]
     * qmttcontenttype : content
     */

    private String urlmd5;
    private String url;
    private String topic;
    private String source;
    private String date;
    private String miniimg_size;
    private String qmttcontenttype;
    private List<MiniimgBean> miniimg;

    public String getUrlmd5() {
        return urlmd5;
    }

    public void setUrlmd5(String urlmd5) {
        this.urlmd5 = urlmd5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMiniimg_size() {
        return miniimg_size;
    }

    public void setMiniimg_size(String miniimg_size) {
        this.miniimg_size = miniimg_size;
    }

    public String getQmttcontenttype() {
        return qmttcontenttype;
    }

    public void setQmttcontenttype(String qmttcontenttype) {
        this.qmttcontenttype = qmttcontenttype;
    }

    public List<MiniimgBean> getMiniimg() {
        return miniimg;
    }

    public void setMiniimg(List<MiniimgBean> miniimg) {
        this.miniimg = miniimg;
    }

    public static class MiniimgBean {
        /**
         * imgwidth : 320
         * imgheight : 240
         * src : http://08.imgmini.eastday.com/mobile/20180320/20180320181705_d8e13ff70f901eb12943713a6ebf976b_1_mwpm_03200403.jpg
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
