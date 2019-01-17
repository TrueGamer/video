package com.huanxi.renrentoutiao.ui.adapter.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.ui.adapter.TaskAdapter;

/**
 * Created by Dinosa on 2018/1/22.
 *
 * 任务的title下面的内容，二级标题；
 */

public class TaskItemBean extends AbstractExpandableItem<TaskItemContentBean> implements MultiItemEntity{




    public boolean isComplete=false;
    /**
     * buttontitle : 晒收入
     * content : 将你的收入分享到朋友圈，拉小伙伴一起来赚钱吧！你还可以获得额外奖励！
     * integral : 30金币
     * title : 晒收入
     */

    private String buttontitle;
    private String content;
    private String integral;
    private String title;
    private int status;
    private String url;
    private String id;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isComplete() {
        return status==1;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getItemType() {
        return TaskAdapter.TYPE_LEVEL_1;
    }

    public String getButtontitle() {
        return buttontitle;
    }

    public void setButtontitle(String buttontitle) {
        this.buttontitle = buttontitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
