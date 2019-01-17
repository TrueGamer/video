package com.huanxi.renrentoutiao.net.bean.news;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinosa on 2018/4/11.
 */

public class ResNewsAndVideoBean {

    public List<HomeInfoBean> list;

    public List<HomeInfoBean> getList() {
        return list;
    }

    public void setList(List<HomeInfoBean> list) {
        this.list = list;
    }

    public class HomeInfoBean{

        /**
         * urlmd5 : 4f718effa5bf6b155a4633a3cab98dd9
         * url : http://mini.eastday.com/mobile/180320174509619.html?qid=qid02561
         * topic : 努力把新时代的“施工图”变成“实景图”
         * source : 中国西藏网
         * date : 2018-03-20 17:45
         * miniimg_size : 3
         * miniimg : [{"imgwidth":"320","imgheight":"240","src":"http://00.imgmini.eastday.com/mobile/20180320/20180320174509_cc7720601fd7b954079666d0815ed3a4_4_mwpm_03200403.jpg"},{"imgwidth":"320","imgheight":"240","src":"http://00.imgmini.eastday.com/mobile/20180320/20180320174509_cc7720601fd7b954079666d0815ed3a4_1_mwpm_03200403.jpg"},{"imgwidth":"320","imgheight":"240","src":"http://00.imgmini.eastday.com/mobile/20180320/20180320174509_cc7720601fd7b954079666d0815ed3a4_2_mwpm_03200403.jpg"}]
         * qmttcontenttype : content
         */
        /**
         * title : 日本的三大黑帮，是哪三大
         * large_image_list : [{"height":326,"uri":"video1609/pgc-image/1521510285287931168229f","url":"http://p1.pstatp.com/video1609/pgc-image/1521510285287931168229f","url_list":[{"url":"http://p1.pstatp.com/video1609/pgc-image/1521510285287931168229f"},{"url":"http://pb3.pstatp.com/video1609/pgc-image/1521510285287931168229f"},{"url":"http://pb9.pstatp.com/video1609/pgc-image/1521510285287931168229f"}],"width":580}]
         * item_id : 6534836922961887752
         * group_id : 6534836922961887752
         * video_id : fc7e0a00637f4d9f9601ee2121cf8df9
         * publish_time : 1521510286
         * video_duration : 67
         */

        private String urlmd5;
        private String topic;
        private String source;
        private String date;
        private int miniimg_size;
        private String qmttcontenttype; // 这里有几种方法：content,qmttad,video,contentbyhtml
        private long adid;
        private List<MiniimgBean> miniimg;

        private int pageNum;
        private String new_type;


        private String title;
        private String item_id;
        private String group_id;
        private String video_id;
        private String publish_time;
        private Long video_duration;
        private String type;
        private List<LargeImageListBean> large_image_list;


        private String id;
        private String cont;
        private String imgurl;
        private String url;
        private String downurl;
        private Long size;
        private String packename;
        private String appname;
        private List<String> imgurls;


        public static final String TYPE_CUSTOMER_BANNER = "qmtt_tl";
        public static final String TYPE_CUSTOMER_TITLE_AND_IMG = "qmtt_tlt";
        public static final String TYPE_CUSTOMER_LEFT_TITLE_RIGHT_IMG_AD_8 = "qmtt_lt";
        public static final String TYPE_CUSTOMER_BANNER_20_3 = "qmtt_banner";
        public static final String TYPE_CUSTOMER_UP_TITLE_DOWN_MUILTY_IMG = "qmtt_mulimg"; //上文，下多图,这里自定义的广告的逻辑

        public List<String> mCustomAdList=null;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public String getNew_type() {
            return new_type;
        }

        public void setNew_type(String new_type) {
            this.new_type = new_type;
        }

        public boolean isWebContent(){
            return "contentbyhtml".equals(qmttcontenttype);
        }

        public boolean isAd(){
            return "qmttad".equals(qmttcontenttype);
        }

        public boolean isVideo(){
            return "video".equals(qmttcontenttype);
        }

//        public boolean isNews(){
//            return "content".equals(qmttcontenttype);
//        }

        public boolean isNews(){
            if("content".equals(qmttcontenttype))
            {
                return true;
            }
            if("contentbyhtml".equals(qmttcontenttype))
            {
                return true;
            }
            return false;
        }
        /**
         * 是否是多图新闻
         * @return
         */
        public  boolean isMuiltImgNews(){
            return miniimg_size>1;
        }

        /**
         * 是不是广点通的广告
         * @return
         */
        public boolean isGdtAd(){
            return "gdt".equals(type);
        }

        /**
         * 是不是推啊的广告；
         * @return
         */
        public  boolean isTaAd(){
            return "ta".equals(type);
        }

        /**
         * 判断是不是自定义任务；
         * @return
         */
        public boolean isCustomAd(){

            mCustomAdList = new ArrayList<>();
            mCustomAdList.add(TYPE_CUSTOMER_BANNER);
            mCustomAdList.add(TYPE_CUSTOMER_TITLE_AND_IMG);
            mCustomAdList.add(TYPE_CUSTOMER_LEFT_TITLE_RIGHT_IMG_AD_8);
            mCustomAdList.add(TYPE_CUSTOMER_BANNER_20_3);
            mCustomAdList.add(TYPE_CUSTOMER_UP_TITLE_DOWN_MUILTY_IMG);

            return mCustomAdList.contains(type);
        }

        /**
         * 穿山甲广告
         * @return
         */
        public boolean isCST() {
            return "csj".equals(type);
        }

        public boolean isBaiDu() {
            return "baidu".equals(type);
        }

        /**
         * 是否是自定义大的banner广告；
         * @return
         */
        public boolean isCustomBigBannerAd(){
            return TYPE_CUSTOMER_BANNER.equals(type);
        }

        /**
         * 是否是自定义的banner20_3广告；
         * @return
         */
        public boolean isCustomBanner20_3Ad(){
            return TYPE_CUSTOMER_BANNER_20_3.equals(type);
        }

        /**
         * 是否是自定义大的banner广告；
         * @return
         */
        public boolean isCustomUpTitleDownImg(){
            return TYPE_CUSTOMER_TITLE_AND_IMG.equals(type);
        }

        /**
         * 左文有图；
         * @return
         */
        public boolean isCustomLeftTitleRightImg(){
            return TYPE_CUSTOMER_LEFT_TITLE_RIGHT_IMG_AD_8.equals(type);
        }

        /**
         * 左文有图；
         * @return
         */
        public boolean isUpTitleDownMuiltyImg(){
            return TYPE_CUSTOMER_UP_TITLE_DOWN_MUILTY_IMG.equals(type);
        }

        public long getAdid() {
            return adid;
        }

        public void setAdid(long adid) {
            this.adid = adid;
        }

        //这里是下载的一个逻辑的一个操作；

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public List<String> getImgurls() {
            return imgurls;
        }

        public void setImgurls(List<String> imgurls) {
            this.imgurls = imgurls;
        }

        //============上面是对业务逻辑进行一个封装的类；===================

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

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

        public int getMiniimg_size() {
            return miniimg_size;
        }

        public void setMiniimg_size(int miniimg_size) {
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public Long getVideo_duration() {
            return video_duration;
        }

        public void setVideo_duration(Long video_duration) {
            this.video_duration = video_duration;
        }

        public List<LargeImageListBean> getLarge_image_list() {
            return large_image_list;
        }

        public void setLarge_image_list(List<LargeImageListBean> large_image_list) {
            this.large_image_list = large_image_list;
        }

    }





    public static class MiniimgBean {
        /**
         * imgwidth : 320
         * imgheight : 240
         * src : http://00.imgmini.eastday.com/mobile/20180320/20180320174509_cc7720601fd7b954079666d0815ed3a4_4_mwpm_03200403.jpg
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

    public static class LargeImageListBean {
        /**
         * height : 326
         * uri : video1609/pgc-image/1521510285287931168229f
         * url : http://p1.pstatp.com/video1609/pgc-image/1521510285287931168229f
         * url_list : [{"url":"http://p1.pstatp.com/video1609/pgc-image/1521510285287931168229f"},{"url":"http://pb3.pstatp.com/video1609/pgc-image/1521510285287931168229f"},{"url":"http://pb9.pstatp.com/video1609/pgc-image/1521510285287931168229f"}]
         * width : 580
         */

        private int height;
        private String uri;
        private String url;
        private int width;
        private List<ResNewsAndVideoBean> url_list;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public List<ResNewsAndVideoBean> getUrl_list() {
            return url_list;
        }

        public void setUrl_list(List<ResNewsAndVideoBean> url_list) {
            this.url_list = url_list;
        }
    }
}
