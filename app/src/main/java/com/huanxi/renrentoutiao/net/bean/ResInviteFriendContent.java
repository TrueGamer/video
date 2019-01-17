package com.huanxi.renrentoutiao.net.bean;

/**
 * Created by Dinosa on 2018/4/12.
 */

public class ResInviteFriendContent {


    /**
     * img : https:.com/share/share1.jpg
     * erwema : https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe8107576df489a14&redirect_uri=http://www.huanxifin.com/api/index.php/news/weixin/getaccess_token?from_uid=4963&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
     * content : 我在这里提现很多次了，看新闻、视频还能领红包，一天轻轻松松10块钱，快扫码加入头条，一起边玩边赚钱吧！
     *///imagehxtoutiao.oss-cn-beijing.aliyuncs

    private String img;
    private String erwema;
    private String content;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getErwema() {
        return erwema;
    }

    public void setErwema(String erwema) {
        this.erwema = erwema;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
