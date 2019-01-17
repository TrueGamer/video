package com.huanxi.renrentoutiao.model.bean.news;

/**
 * Created by Dinosa on 2018/3/28.
 * 自定义下载的广告；
 */

public class NewsCustomDownloadAd {


    /**
     * id : 1
     * title : 11111
     * content : 小说小说小说小说小说小说小说
     * imgurl : http://h.hiphotos.baidu.com/baike/pic/item/503d269759ee3d6d12a5540144166d224e4ade9d.jpg
     * url : http://baidu.com
     * downurl : http://image-hxdance.oss-cn-beijing.aliyuncs.com/songcover/hehe广场舞《DJ的歌》.jpg
     * size : 111
     * packename : 111
     * appname : 111
     * type : qmtt_tlt
     * code : qmtt_tlt
     * qmttcontenttype : qmttad
     */

    private int id;
    private String title;
    private String content;
    private String imgurl;

    private String downurl;
    private int size;
    private String packename;
    private String appname;

    private String type;
    private String code;
    private String qmttcontenttype;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
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

    public String getQmttcontenttype() {
        return qmttcontenttype;
    }

    public void setQmttcontenttype(String qmttcontenttype) {
        this.qmttcontenttype = qmttcontenttype;
    }
}
