package com.huanxi.renrentoutiao.ui.view.luckwalk;

import java.io.Serializable;

/**
 * Created by ZD on 2017/7/4.
 */

public class LuckyWalkBean implements Serializable {

    /**
     * id : 1
     * title : iPhone7
     * type : 0
     * money : 0
     * url : iPhone7.png
     * schemeId : R2017070411245205351
     * updated : 2017-07-04 11:24:52
     */

    private String id;
    private String title;
    private String type;
    private String money;
    private String url;
    private String schemeId;
    private String updated;
    /**
     * user_id : 480
     * username : test
     * addtime : 2017-07-04 16:49:22
     */

    private String user_id;
    private String username;
    private String addtime;


    public LuckyWalkBean(String id, String title, String money, String url) {
        this.id = id;
        this.title = title;
        this.money = money;
        this.url = url;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }


    @Override
    public boolean equals(Object obj) {
        try {
            LuckyWalkBean luckywalk= (LuckyWalkBean) obj;
            return id.equals(luckywalk.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        return "LuckyWalkBean{" +
                "id='" + id + '\'' +
                '}';
    }
}
