package com.huanxi.renrentoutiao.ui.adapter.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.ui.adapter.TaskAdapter;

/**
 * Created by Dinosa on 2018/1/22.
 *
 * 任务item下面的content内容，三级标题；
 */

public class TaskItemContentBean implements MultiItemEntity{

    private   String content;
    private   String buttonContent;
    private   String url;
    private   String title;
    private  String id;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getButtonContent() {
        return buttonContent;
    }

    public void setButtonContent(String buttonContent) {
        this.buttonContent = buttonContent;
    }

    public TaskItemContentBean(String content,String buttonContent) {
        this.content = content;
        this.buttonContent=buttonContent;
    }

    public TaskItemContentBean(String content,String buttonContent,String url,String title,String id) {
        this.content = content;
        this.buttonContent=buttonContent;
        this.url=url;
        this.title=title;
        this.id=id;
    }

    @Override
    public int getItemType() {
        return TaskAdapter.TYPE_LEVEL_2;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
