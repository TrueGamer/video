package com.huanxi.renrentoutiao.net.bean.news;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by Dinosa on 2018/1/15.
 */

public class HomeTabBean implements Serializable,MultiItemEntity{

    public static final int TYPE_MY = 1;
    public static final int TYPE_OTHER = 2;
    public static final int TYPE_MY_CHANNEL = 3;
    public static final int TYPE_OTHER_CHANNEL = 4;


    public int itemtype;

    private String code;       //这里表示渠道的唯一表示，这里我们使用的是code;guoji
    private String title;     //这里表示显示的频道 国际
    /**
     * 0 可移除，1不可移除
     */
    private int channelType;

    /**
     * 0 未选中 1 选中
     */
    private boolean isChannelSelect;

    /**
     * @param channelName  频道的名称；
     */
    public HomeTabBean(String channelName) {
        this.title = channelName;
    }

    /**
     * @param channelName  频道的名称；
     * @param itemtype    频道的类型；
     */
    public HomeTabBean(String channelName,int itemtype) {
        this.title = channelName;
        this.itemtype=itemtype;
    }

    @Override
    public int getItemType() {
        return itemtype;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public boolean isChannelSelect() {
        return isChannelSelect;
    }

    public void setChannelSelect(boolean channelSelect) {
        isChannelSelect = channelSelect;
    }

    public void setItemtype(int itemtype) {
        this.itemtype = itemtype;
    }


    @Override
    public String toString() {
        return "HomeTabBean{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", isChannelSelect=" + isChannelSelect +
                '}';
    }
}
