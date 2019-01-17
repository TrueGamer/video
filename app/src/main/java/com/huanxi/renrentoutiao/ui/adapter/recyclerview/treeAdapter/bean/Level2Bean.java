package com.huanxi.renrentoutiao.ui.adapter.recyclerview.treeAdapter.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.treeAdapter.QuestionAdapterNew;

/**
 * Created by Dinosa on 2018/4/16.
 */

public class Level2Bean  extends AbstractExpandableItem<Level3Bean> implements MultiItemEntity {

    String questionContent;

    public Level2Bean(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return QuestionAdapterNew.Level_1;
    }
}
