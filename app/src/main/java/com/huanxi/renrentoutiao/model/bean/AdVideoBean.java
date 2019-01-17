package com.huanxi.renrentoutiao.model.bean;

import java.io.Serializable;

public class AdVideoBean implements Serializable{

    private String type;
    private String adtype;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdtype() {
        return adtype;
    }

    public void setAdtype(String adtype) {
        this.adtype = adtype;
    }

    @Override
    public String toString() {
        return "AdVideoBean{" +
                "type='" + type + '\'' +
                ", adtype='" + adtype + '\'' +
                '}';
    }
}
