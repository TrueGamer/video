package com.huanxi.renrentoutiao.ui.view.video;

import com.huanxi.renrentoutiao.model.bean.l_video.VideoBean;

/**
 * Created by cxf on 2018/7/30.
 */

public class VideoDeleteEvent {
    private VideoBean mVideoBean;

    public VideoDeleteEvent(VideoBean videoBean) {
        mVideoBean = videoBean;
    }

    public VideoBean getVideoBean() {
        return mVideoBean;
    }
}
