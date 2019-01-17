package com.huanxi.renrentoutiao.ui.adapter.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.model.bean.VideoItemBean;
import com.huanxi.renrentoutiao.presenter.format.JudgeAdsPersenter;
import com.huanxi.renrentoutiao.service.DownloadServiceInterface;
import com.huanxi.renrentoutiao.ui.adapter.AdBean;
import com.huanxi.renrentoutiao.ui.adapter.BaseAdsAdapter;
import com.huanxi.renrentoutiao.ui.adapter.VideoListAdapter;

import java.util.List;

/**
 * Created by Dinosa on 2018/2/6.
 */

public class VideoBean implements DownloadServiceInterface,MultiItemEntity{

    /**
     * content : {"abstract":"女司机高速上憋不住，队友这样打掩护，记录仪拍下尴尬15秒！","action_extra":"{\"channel_id\": 5443492143}","action_list":[{"action":1,"desc":"","extra":{}},{"action":3,"desc":"","extra":{}},{"action":7,"desc":"","extra":{}},{"action":9,"desc":"","extra":{}}],"aggr_type":1,"allow_download":false,"article_sub_type":0,"article_type":0,"article_url":"http://toutiao.com/group/6488441810925388302/","ban_comment":0,"ban_danmaku":false,"behot_time":1517644967,"bury_count":21,"cell_flag":11,"cell_layout_style":1,"cell_type":0,"comment_count":6,"content_decoration":"","cursor":1517644967999,"danmaku_count":6,"digg_count":241,"display_url":"http://toutiao.com/group/6488441810925388302/","filter_words":[{"id":"8:0","is_selected":false,"name":"看过了"},{"id":"9:1","is_selected":false,"name":"内容太水"},{"id":"5:216037031","is_selected":false,"name":"拉黑作者:学车教练"}],"forward_info":{"forward_count":5},"group_flags":32832,"group_id":"6488441810925388302","has_m3u8_video":false,"has_mp4_video":0,"has_video":true,"hot":0,"ignore_web_transform":1,"is_subject":false,"item_id":"6488441810925388302","item_version":0,"keywords":"女司机,队友,记录仪","large_image_list":[{"height":326,"uri":"video1609/45f5000503bb4dafb819","url":"http://p1.pstatp.com/video1609/45f5000503bb4dafb819","url_list":[{"url":"http://p1.pstatp.com/video1609/45f5000503bb4dafb819"},{"url":"http://pb3.pstatp.com/video1609/45f5000503bb4dafb819"},{"url":"http://pb9.pstatp.com/video1609/45f5000503bb4dafb819"}],"width":580}],"level":0,"log_pb":{"impr_id":"20180203160247010008059035620F46"},"media_info":{"avatar_url":"http://p3.pstatp.com/large/36440002bb5ff261789b","follow":false,"is_star_user":false,"media_id":"1575766755822606","name":"学车教练","recommend_reason":"","recommend_type":0,"user_id":"66287511824","user_verified":false,"verified_content":""},"media_name":"学车教练","middle_image":{"height":360,"uri":"list/45f5000503bb4dafb819","url":"http://p1.pstatp.com/list/300x196/45f5000503bb4dafb819.webp","url_list":[{"url":"http://p1.pstatp.com/list/300x196/45f5000503bb4dafb819.webp"},{"url":"http://pb3.pstatp.com/list/300x196/45f5000503bb4dafb819.webp"},{"url":"http://pb9.pstatp.com/list/300x196/45f5000503bb4dafb819.webp"}],"width":640},"publish_time":1510708083,"read_count":288697,"repin_count":861,"rid":"20180203160247010008059035620F46","share_count":388,"share_url":"http://m.toutiaoribao.cn/a6488441810925388302/?iid=24142389236&app=toutiaoribao_news","show_dislike":true,"show_portrait":false,"show_portrait_article":false,"source":"学车教练","source_icon_style":1,"source_open_url":"sslocal://profile?refer=video&uid=66287511824","tag":"news","tag_id":"6488441810925388302","tip":0,"title":"女司机高速上憋不住，队友这样打掩护，记录仪拍下尴尬15秒！","ugc_recommend":{"activity":"","reason":""},"url":"http://toutiao.com/group/6488441810925388302/","user_info":{"avatar_url":"http://p3.pstatp.com/thumb/36440002bb5ff261789b","description":"学车驾考研究领军人物；热衷专研驾驶技术，倡导安全驾驶。","follow":false,"follower_count":0,"name":"学车教练","user_id":"66287511824","user_verified":false},"user_repin":0,"user_verified":0,"verified_content":"","video_detail_info":{"detail_video_large_image":{"height":326,"uri":"video1609/45f5000503bb4dafb819","url":"http://p1.pstatp.com/video1609/45f5000503bb4dafb819","url_list":[{"url":"http://p1.pstatp.com/video1609/45f5000503bb4dafb819"},{"url":"http://pb3.pstatp.com/video1609/45f5000503bb4dafb819"},{"url":"http://pb9.pstatp.com/video1609/45f5000503bb4dafb819"}],"width":580},"direct_play":1,"group_flags":32832,"show_pgc_subscribe":1,"video_id":"c52a6ea258564271b467e8a2e0493dcb","video_preloading_flag":1,"video_type":0,"video_watch_count":336180,"video_watching_count":0},"video_duration":93,"video_id":"c52a6ea258564271b467e8a2e0493dcb","video_style":8}
     * code : I6c4wJQETzvo18OLBEgD9WDRtbtQnM9RR776KChoT8+GL1/mPeUsjZweKjqI0vGFWa5GkrHq1lhkqp7tDSJb5ExXfRSW7jtvetsO+RY4LDhjCZmQp+poIdKI+O0a+IYEhg2PuQ9BjdSfXmlTjJ7jWnap0KKBDKdwRZ4tj9HDD2c=
     * urlmd5 : d8a0a4f9a2cd2a5f881d7dc4bc429155
     */


    public static final String TYPE_CONTENT="content";
    public static final String TYPE_AD_TA="ta";  //这里表示推啊的广告；
    public static final String TYPE_AD_GDT="gdt";  //这里表示广点通；
    public static final String TYPE_BD = "baidu";//百度

    public static final String TYPE_CUSTOM_BANNER="qmtt_tl";  //这里表示banner
    public static final String TYPE_UP_TEXT_DOWN_IMG="qmtt_tlt";  //自定义上文下图
    public static final String TYPE_CUSTOM_TYPE_LEFT_TITLE_RIGHT_IMG="qmtt_lt";  //左文右图


    private VideoItemBean content;
    private String code;
    private String urlmd5;
    private String qmttcontenttype;
    private String type;    //哪个类型的广告；
    private long adid;


    /**
     * id : 3
     * title : 问问
     * content : 驱蚊器无群无
     * imgurl : http://h.hiphotos.baidu.com/baike/pic/item/503d269759ee3d6d12a5540144166d224e4ade9d.jpg
     * url : http://baidu.com
     * downurl : https://hxadapk.oss-cn-beijing.aliyuncs.com/yingyongbao_7182130.apk
     * size : 8124730
     * packename : com.tencent.android.qqdownloader
     * appname : 应用宝1
     * phonetype : 2
     */
    /**
     * {"urlmd5":"425daed3747ff51b645657c1c94a7355",
     * "title":"面对“高智商”嫌疑人，警察上“神器”，简直神了！",
     * "source":"都市侠剧场","large_image_list":[{"height":326,"uri":"video1609\/b3550008a5f1e02a85b9",
     * "url":"http:\/\/p3.pstatp.com\/video1609\/b3550008a5f1e02a85b9",
     * "url_list":[{"url":"http:\/\/p3.pstatp.com\/video1609\/b3550008a5f1e02a85b9"},
     * {"url":"http:\/\/pb9.pstatp.com\/video1609\/b3550008a5f1e02a85b9"},
     * {"url":"http:\/\/pb1.pstatp.com\/video1609\/b3550008a5f1e02a85b9"}],"width":580}],
     * "item_id":"6596210812828779021","group_id":"6596210812828779021",
     * "video_id":"v020047f0000be5745akr6gav01rpneg","publish_time":"1535800009","video_duration":"258"},
     * "urlmd5":"425daed3747ff51b645657c1c94a7355","qmttcontenttype":"content"}
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
//    private int phonetype;

    public boolean isAd(){
        return JudgeAdsPersenter.isAd(qmttcontenttype);
    }

    public boolean isVideo(){
        return "content".equals(qmttcontenttype);
    }
    //这里同样要做一个判断的操作；


    public List<String> getImgurls() {
        return imgurls;
    }

    public void setImgurls(List<String> imgurls) {
        this.imgurls = imgurls;
    }

    /**
     * 是不是广点通的广告
     * @return
     */
    public boolean isGdtAd(){
        return JudgeAdsPersenter.isGdtAd(type);
    }

    /**
     * 是不是百度的广告
     * @return
     */
    public boolean isBaiDu() {
        return JudgeAdsPersenter.isBDAd(type);
    }

    /**
     * 是不是穿山甲的广告
     * @return
     */
    public boolean isCsjAd() {
        return JudgeAdsPersenter.isCsjAd(type);
    }

    /**
     * 是不是推啊的广告；
     * @return
     */
    public  boolean isTaAd(){
        return JudgeAdsPersenter.isTaAd(type);
    }

    /**
     * 判断是不是自定义任务；
     * @return
     */
    public boolean isCustomAd(){

        return JudgeAdsPersenter.isCustomAd(type);
    }

    /**
     * 是否是自定义大的banner广告；
     * @return
     */
    public boolean isCustomBigBannerAd(){
        return JudgeAdsPersenter.isCustomBigBannerAd(type);
    }

    /**
     * 是否是自定义的banner20_3广告；
     * @return
     */
    public boolean isCustomBanner20_3Ad(){
        return JudgeAdsPersenter.isCustomBanner20_3Ad(type);
    }

    /**
     * 是否是自定义大的banner广告；
     * @return
     */
    public boolean isCustomUpTitleDownImg(){
        return JudgeAdsPersenter.isCustomUpTitleDownImg(type);
    }

    /**
     * 左文有图；
     * @return
     */
    public boolean isCustomLeftTitleRightImg(){
        return JudgeAdsPersenter.isCustomLeftTitleRightImg(type);
    }

    /**
     * 左文有图；
     * @return
     */
    public boolean isUpTitleDownMuiltyImg(){
        return JudgeAdsPersenter.isUpTitleDownMuiltyImg(type);
    }


    ///=======上面是对其进行的一个判断逻辑================


    public long getAdid() {
        return adid;
    }

    public void setAdid(long adid) {
        this.adid = adid;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrlmd5() {
        return urlmd5;
    }

    public void setUrlmd5(String urlmd5) {
        this.urlmd5 = urlmd5;
    }

    public VideoItemBean getContent() {
        return content;
    }

    public void setContent(VideoItemBean content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        if(TYPE_CONTENT.equals(qmttcontenttype)){
            //这里表示是内容；
            return VideoListAdapter.VIDEO_ITEM;
        }else{
            //这里表示是广告；
            if(TYPE_AD_TA.equals(type)){
                return BaseAdsAdapter.TA_UP_TEXT_DOWN_IMG;
            }else if(TYPE_CUSTOM_BANNER.equals(type)){
                //这里表示自定义纯图；
                return BaseAdsAdapter.CUSTOMER_BANNNER_AD_1;
            }else if(TYPE_CUSTOM_TYPE_LEFT_TITLE_RIGHT_IMG.equals(type)){
                //这里表示自定义上文下图；
                return BaseAdsAdapter.CUSTOMER_LEFT_TITLE_RIGHT_IMG_AD_8;
            }else if(TYPE_UP_TEXT_DOWN_IMG.equals(type)){
                //自定义上文下图
                return BaseAdsAdapter.CUSTOMER_UP_TITLE_DOWN_IMG_AD_2;
            }else if(TYPE_AD_GDT.equals(type)){
                //这里表示广点通的广告
                return BaseAdsAdapter.GDT_UP_TEXT_DOWN_IMG;
            } else if (TYPE_BD.equals(type)) {
                return BaseAdsAdapter.BAIDU_AD;
            }
        }
        return VideoListAdapter.VIDEO_ITEM;
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

//    public int getPhonetype() {
//        return phonetype;
//    }
//
//    public void setPhonetype(int phonetype) {
//        this.phonetype = phonetype;
//    }


    //==========这里是下载的逻辑============
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

    public AdBean getAdBean(){

        return new AdBean(title,cont,imgurl,url,downurl,size,packename,appname,imgurls);
    }
}
