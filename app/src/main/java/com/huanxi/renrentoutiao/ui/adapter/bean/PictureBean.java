package com.huanxi.renrentoutiao.ui.adapter.bean;

import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.BaseMuiltyAdapterBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.PictureListBeanHolder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.video.VideoListBeanHolder;

import java.util.List;

/**
 * 美图实体类
 */
public class PictureBean extends BaseMuiltyAdapterBean {

    private String title;
    private String ct;
    private String typeName;
    private String itemId;
    private int type;
    private List<PictureImageBean> list;

    public PictureBean(String title, String ct, String typeName, String itemId, int type, List<PictureImageBean> list) {
        this.title = title;
        this.ct = ct;
        this.typeName = typeName;
        this.itemId = itemId;
        this.type = type;
        this.list = list;
    }

    @Override
    public int getItemType() {
        return PictureListBeanHolder.class.hashCode();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<PictureImageBean> getList() {
        return list;
    }

    public void setList(List<PictureImageBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PictureBean{" +
                "title='" + title + '\'' +
                ", ct='" + ct + '\'' +
                ", typeName='" + typeName + '\'' +
                ", itemId='" + itemId + '\'' +
                ", type=" + type +
                ", list=" + list +
                '}';
    }
}
