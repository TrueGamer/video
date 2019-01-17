package com.huanxi.renrentoutiao.model.bean;

/**
 * Created by Dinosa on 2018/2/8.
 */

public class QuestionBean {

    public String title;
    public String content;

    public QuestionBean(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
