package com.huanxi.renrentoutiao.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.ui.adapter.NewsDetailAdapter;

/**
 * Created by Dinosa on 2018/2/2.
 */

public class NewsCommentBean  implements MultiItemEntity{

    /**
     * addtime : 2018-02-02 17:36:03
     * avatar : http://h.hiphotos.baidu.com/baike/pic/item/503d269759ee3d6d12a5540144166d224e4ade9d.jpg
     * cont : hahaha
     * id : 29
     * nickname : 15802720217
     */

    private String addtime;
    private String avatar;
    private String content;
    private String id;
    private String nickname;
    private String praisenum;
    private int haspraise; ///大于0表示点赞

    public int getHaspraise() {
        return haspraise;
    }

    public void setHaspraise(int haspraise) {
        this.haspraise = haspraise;
    }

    public String getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(String praisenum) {
        this.praisenum = praisenum;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public int getItemType() {
        return NewsDetailAdapter.TYPE_COMMENT;
    }
}
