package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.video;

import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.BaseMuiltyAdapterBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.video.VideoListBeanHolder;

/**
 * Created by Dinosa on 2018/4/11.
 */

public class VideoListBean extends BaseMuiltyAdapterBean{

    public String source;
    public String urlmd5;
    public String title;
    public String imgUrl;
    public String item_id;
    public String group_id;
    public String video_id;
    public String publish_time;
    public Long video_duration;

    public VideoListBean(String source, String urlmd5, String title, String imgUrl, String item_id, String group_id, String video_id, String publish_time, Long video_duration) {
        this.source = source;
        this.urlmd5 = urlmd5;
        this.title = title;
        this.imgUrl = imgUrl;
        this.item_id = item_id;
        this.group_id = group_id;
        this.video_id = video_id;
        this.publish_time = publish_time;
        this.video_duration = video_duration;
    }

    @Override
    public int getItemType() {
        return VideoListBeanHolder.class.hashCode();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrlmd5() {
        return urlmd5;
    }

    public void setUrlmd5(String urlmd5) {
        this.urlmd5 = urlmd5;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public Long getVideo_duration() {
        return video_duration;
    }

    public void setVideo_duration(Long video_duration) {
        this.video_duration = video_duration;
    }
}
