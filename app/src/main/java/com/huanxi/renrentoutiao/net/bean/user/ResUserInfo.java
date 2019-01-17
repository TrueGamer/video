package com.huanxi.renrentoutiao.net.bean.user;

import com.huanxi.renrentoutiao.model.bean.UserBean;

/**
 * Created by Dinosa on 2018/2/6.
 */

public class ResUserInfo {

    public int hasbind;
    public UserBean userinfo;

    public int getHasbind() {
        return hasbind;
    }

    public void setHasbind(int hasbind) {
        this.hasbind = hasbind;
    }

    public UserBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserBean userinfo) {
        this.userinfo = userinfo;
    }
}
