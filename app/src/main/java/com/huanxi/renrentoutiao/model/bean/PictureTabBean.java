package com.huanxi.renrentoutiao.model.bean;

public class PictureTabBean {

    private String name;
    private int id;
    /**
     * 0 未选中 1 选中
     */
    private boolean isChannelSelect;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChannelSelect() {
        return isChannelSelect;
    }

    public void setChannelSelect(boolean channelSelect) {
        isChannelSelect = channelSelect;
    }
}
