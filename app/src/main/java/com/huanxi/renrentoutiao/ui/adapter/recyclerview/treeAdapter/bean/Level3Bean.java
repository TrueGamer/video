package com.huanxi.renrentoutiao.ui.adapter.recyclerview.treeAdapter.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.treeAdapter.QuestionAdapterNew;

/**
 * Created by Dinosa on 2018/4/16.
 */

public class Level3Bean implements MultiItemEntity {

    String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Level3Bean(String content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return QuestionAdapterNew.Level_2;
    }
}
