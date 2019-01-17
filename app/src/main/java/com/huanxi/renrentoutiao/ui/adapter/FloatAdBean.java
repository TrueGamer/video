package com.huanxi.renrentoutiao.ui.adapter;

import com.huanxi.renrentoutiao.service.DownloadServiceInterface;

/**
 * Created by Dinosa on 2018/3/28.
 */

public class FloatAdBean implements DownloadServiceInterface{

    /**
     * id : 6
     * title : 22
     * content : null
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

    private int id;
    private String title;
    private String content;
    private String imgurl;
    private String url;
    private String downurl;
    private Long size;
    private String packename;
    private String appname;
    private String type;
    private String code;
    private int phonetype;

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


    ///========下载的接口；================

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
}
